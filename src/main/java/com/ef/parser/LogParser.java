package com.ef.parser;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import com.ef.common.LogEntry;
import com.ef.util.HibernateUtil;
import com.ef.util.Util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

public class LogParser {
    private File logFile;
    
    private static final Logger logger = LogManager.getLogger(LogParser.class.getCanonicalName());
    
    public LogParser(File logFile) {
        this.logFile = logFile;
    }

    
    public void parseAndPersist() throws IOException, ParseException {
        LineIterator lit = FileUtils.lineIterator(logFile);
        Session session = HibernateUtil.getSessionFactory().openSession();
        int count = 0;  // used for batched processing
        
        session.beginTransaction();
        
        while(lit.hasNext()) {
            String line = lit.nextLine();
   
            String[] tokens = line.split("\\|");
            
            if(tokens.length == 0) {
                break;
            }
            
            if(tokens.length != 5) {
                throw new ParseException("Log entry is invalid.");
            }
            
            String requestDate = tokens[0];
            String ip = tokens[1];
            String httpRequest = tokens[2];
            String status = tokens[3];
            String userAgent = tokens[4];

            LogEntry logEntry = new LogEntry();
            
            String reformattedDate = Util.reformatDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), requestDate);

            java.sql.Timestamp requestDateObj = java.sql.Timestamp.valueOf(reformattedDate);
                
            logEntry.setRequestDate(requestDateObj);
            logEntry.setIp(ip);
            logEntry.setHttpRequest(httpRequest);
            logEntry.setStatus(Integer.valueOf(status));
            logEntry.setUserAgent(userAgent);
            
            session.save(logEntry);
            count++;
            
            if(count % 20 == 0) {
                session.flush();
                session.clear();
            }
            
            
        }
        logger.info("Finished parsing and persisting access log file.");
        
        session.getTransaction().commit();
        session.close();
        
    }
    
}
