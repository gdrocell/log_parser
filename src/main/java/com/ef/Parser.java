package com.ef;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.ef.cs.CommandConstants;
import com.ef.is.Inspector;
import com.ef.parser.LogParser;
import com.ef.util.HibernateUtil;
import com.ef.util.Util;


public class Parser {
    public static final Logger logger = LogManager.getLogger(Parser.class);
    
    public static void main(String... args)  {
        
        logger.info("Parser Starting...");
        
        Options options = getParserOptions();
        
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            logger.error("Parse Exception ", e);
            formatter.printHelp("utility-name", options);

            System.exit(1);
        }

        String accessLogPath = cmd.getOptionValue(CommandConstants.ACCESS_LOG_CMD);
        
        if(accessLogPath != null) {
            LogParser logParser = new LogParser(new File(accessLogPath));
            try {
                logParser.parseAndPersist();
            } 
            catch (IOException e) {
                logger.error("Reading access log file ", e);
                System.err.println("Error: Reading access log file.");
            }
            catch(com.ef.parser.ParseException e) {
                logger.error("Parsing access log file ",  e);
                System.err.println("Error: Parsing access log file.");
            }
        }
        
        String startDate = cmd.getOptionValue(CommandConstants.START_DATE_CMD);
        String threshold = cmd.getOptionValue(CommandConstants.THRESHOLD_CMD);
        String duration = cmd.getOptionValue(CommandConstants.DURATION_CMD);
        
        if(startDate != null && threshold != null && duration != null) {
            Inspector inspector = new Inspector();

            try {
                inspector.inspect(startDate, Integer.valueOf(threshold), duration);
            } catch (NumberFormatException e) {
                logger.error("Threshold is an invalid number ",  e);
                System.err.println("Error: Threshold is an invalid number");
            } catch (java.text.ParseException e) {
                logger.error("Start date is in a invalid informat.  Expected format is yyyy-MM-dd.HH:mm:ss",  e);
                System.err.println("Error: Start date is in a invalid informat.  Expected format is yyyy-MM-dd.HH:mm:ss");
            }
        }
        else {
            System.err.println("Missing one or more of the following: startDate, threshold, or duration.");
            logger.error("Missing one or more of the following: startDate, threshold, or duration.");
        }
        
        logger.info("Shutting Down Hibernate...");
        
        HibernateUtil.shutDown();
        
        logger.info("Parser Exiting...");

    }
    
    private static Options getParserOptions() {
        Options options = new Options();
        
        Option startDate = new Option("s", CommandConstants.START_DATE_CMD, true, "The start date to begin searching for requests in the format yyyy-MM-dd.HH:mm:ss");
        Option threshold = new Option("t", CommandConstants.THRESHOLD_CMD, true, "This represents the maximum number of requests to be made before being blocked.");
        Option duration = new Option("d", CommandConstants.DURATION_CMD, true, "This can be set to hourly or daily.");
        Option accessLog = new Option("a", CommandConstants.ACCESS_LOG_CMD, true, "Path to access log.");

        options.addOption(startDate);
        options.addOption(duration);
        options.addOption(threshold);
        options.addOption(accessLog);
        
        return options;
    }
}
