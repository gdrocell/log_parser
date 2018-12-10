package com.ef.is;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;


import com.ef.util.HibernateUtil;
import com.ef.util.Util;

public class Inspector {
    public static final Logger logger = LogManager.getLogger(Inspector.class);
    
    public Inspector() {
        
    }

    public void inspect(String startDate, int threshold, String duration) throws ParseException {
        
        logger.info("Begin Inspecting...");
        
        Date currStart = Util.toDate("yyyy-MM-dd.HH:mm:ss", startDate);
        Date currEnd = null;
        
        if(duration.equals("hourly")) {
            currEnd = Util.addOneHour(currStart);
        }
        else {
            currEnd = Util.addOneDay(currStart);
        }
        
        Session session = HibernateUtil.getSessionFactory().openSession();

        String query = "SELECT `ip`, count(`ip`) as `numRequests` FROM `log_entries` \r\n" + 
                "     WHERE `requestDate` >= TIMESTAMP(?) AND `requestDate` < TIMESTAMP(?)\r\n" + 
                "     GROUP BY `ip` \r\n" + 
                "     HAVING `numRequests` >= ?;";
        

        SQLQuery sqlQuery = session.createSQLQuery(query);
        String reformattedStart = Util.reformatDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), currStart);
        String reformattedEnd =  Util.reformatDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), currEnd);
                
        List<Object[]> records =  sqlQuery.setString(0, reformattedStart)
        .setString(1, reformattedEnd)
        .setLong(2, threshold)
        .list();
        
        logger.info("Record Count: " + records.size());
        Blocker blocker = new Blocker();
        
        for(Object[] attributes : records) {
            String ip = (String)attributes[0];
            BigInteger requestCount = (BigInteger)attributes[1];
            
            String comment = "Blocked " + ip + " for making " + requestCount + " requests " +
                             "between " + reformattedStart + " and " + reformattedEnd +
                             " with duration set to " + duration + " and threshold set to " + threshold + ".";
            
            blocker.block(ip, comment);
            
            System.out.println("Blocked " + ip + " for making " + requestCount + " requests.");
        }

        logger.info("Finished Inspecting...");
        
    }
}
