package com.ef.is;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.ef.common.BlockEntry;
import com.ef.util.HibernateUtil;

public class Blocker {
    public static final Logger logger = LogManager.getLogger(Blocker.class);
    
    public Blocker() {
        
    }
    
    public void block(String ip, String comment) {
        logger.info("Blocking " + ip + " Comment: " + comment);
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        session.beginTransaction();
        
        BlockEntry blockEntry = new BlockEntry();
        
        blockEntry.setIp(ip);
        blockEntry.setComment(comment);
        
        try {
            session.save(blockEntry);
            
            session.getTransaction().commit();
            
        }
        catch(Exception e) {
            logger.info(ip + " has already been blocked.");
        }
        finally {
            session.close();
        }
        
        
        logger.info("Finished Blocking...");
    }
}
