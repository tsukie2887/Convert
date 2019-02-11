package com.pershing.convert.service.impl;

import java.io.*;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.pershing.convert.service.FTPService;

@Service
public class FTPServiceImpl implements FTPService{

    @Value("${sftp.ip}")
    private String sftpIp;
    @Value("${sftp.port}")
    private int sftpPort;
    @Value("${sftp.username}")
    private String sftpUsername;
    @Value("${sftp.password}")
    private String sftpPassword;
    @Value("${sftp.basepath}")
    private String sftpBasepath;
    private static int uploadRetry;
    private static int uploadSleep;
    private static String basepath;
    private static String convertedDir;
    private static String originalDir;
    
    @Value("${sourceDir}")
	private String sourceDir;
	@Value("${targetDir}")
	private String targetDir;
	@Value("${outputFormat}")
	private String outputFormat;
	
	private static Logger logger = LoggerFactory.getLogger(FTPServiceImpl.class);
	Session session = null;
    Channel channel = null;
  
    @Override
	public boolean uploadOriginal( String inputDir1, String inputDir2 )throws Exception {

		return upLoad(inputDir1,originalDir)&&upLoad(inputDir2,originalDir);
	}

	@Override
	public boolean uploadConverted(String outputDir)throws Exception {

		return upLoad(outputDir, convertedDir);
	}
    
	synchronized static private boolean upLoad(String path, String directory) throws Exception{
       boolean result = false;
       int i = 0;
       while(i <= uploadRetry && !result ) {
    	   ChannelSftp sftp = SFTPConnectionFactory.getInstance().makeConnection();
    	   StringBuilder tmpPath = new StringBuilder();
    	   try {
    		   sftp.cd(basepath);
    		   sftp.cd(directory);
    	   }
    	   catch(SftpException e) {
    		   logger.error("Directory creating fail. Error message: "+e.getMessage()); 
    	   }
    	   try {
    		   tmpPath.append(basepath);
    		   tmpPath.append(directory);
    		   sftp.put(path,tmpPath.toString());
    		   if(i==0) {
    			   logger.info("Upload sucess.");
    		   }
    		   else {
    			   logger.info("Re-upload sucess.");
    		   }
    		   result = true;
    	   }
    	   catch(Exception e) {
    		   i++;
    		   logger.error("Upload fail, Retry...");
    		   if(i > uploadRetry) {
    			   logger.error("Retry limit approached, upload end. Error message: "+e.getMessage());
    		   }
    		   try {
    			   TimeUnit.MILLISECONDS.sleep(uploadSleep);
    		   }
    		   catch(InterruptedException e1) {
    			   e1.printStackTrace();
    		   }
    	   }
       }
       return result;       
    }
    @Value("${sftp.uploadRetry}")
    private void setuploadRetry(int uploadretry) {
    	FTPServiceImpl.uploadRetry = uploadretry;
    }
    @Value("${sftp.uploadSleep}")
    private void setuploadSleep(int uploadsleep) {
    	FTPServiceImpl.uploadSleep = uploadsleep;
    }
    @Value("${sftp.basePath}")
    private void setbasepath(String basePath) {
    	FTPServiceImpl.basepath = basePath;
    }
    @Value("${sftp.originalDir}")
    private void setoriginalDir(String dir) {
    	FTPServiceImpl.originalDir = dir;
    }
    @Value("${sftp.convertedDir}")
    private void setconvertedDir(String dir) {
    	FTPServiceImpl.convertedDir = dir;
    }
}


