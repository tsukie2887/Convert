package com.pershing.convert.service.impl;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

@Component
//@ConfigurationProperties(prefix = "sftp")
public class SFTPConnectionFactory {
	private static Logger logger = LoggerFactory.getLogger(FTPServiceImpl.class);
	private static String username;
	private static String password;
	private static String privateKey;
	private static String ip;
	private static int port;
	
	private static final SFTPConnectionFactory factory = new SFTPConnectionFactory();
	private ChannelSftp client;
	private Session session;
	private SFTPConnectionFactory() {}
	
	public static SFTPConnectionFactory getInstance() {
		return factory;
	}
	synchronized public ChannelSftp makeConnection () throws Exception {
		if(client==null||session==null||!client.isConnected()||!session.isConnected()) {
			try {
				JSch jsch = new JSch();
				//if(privateKey != null) {
				//	jsch.addIdentity(privateKey);
				//}
				session = jsch.getSession(username, ip, port);
				session.setPassword(password);
				Properties config = new Properties();
				config.put("StrictHostKeyChecking", "no");
				session.setConfig(config);
				session.setDaemonThread(true);
				session.connect();
				Channel channel = session.openChannel("sftp");
				channel.connect();
				client = (ChannelSftp) channel;
				logger.info("Sftp server connecting sucess.");
			}
			catch(JSchException e) {
				logger.error("Sftp server connecting fail. Error Message: "+ e.getMessage());
			}
		}
		return client;
	}
	
	public void logout() {
		if(client != null) {
			if(client.isConnected()) {
				client.disconnect();
			}
		}
		if(session != null) {
			if(session.isConnected()) {
				session.disconnect();
			}
		}
	}
	public String getUsername() {
		return username;
	}
	@Value("${sftp.username}")
	public void setUsername(String username) {
		SFTPConnectionFactory.username = username;
	}
	public static String getPassword() {
		return password;
	}
	@Value("${sftp.password}")
	public void setPassword(String password) {
		SFTPConnectionFactory.password = password;
	}
	public static String getPrivateKey() {
		return privateKey;
	}
	public static void setPrivateKey(String privateKey) {
		SFTPConnectionFactory.privateKey = privateKey;
	}
	public static String getIp() {
		return ip;
	}
	@Value("${sftp.ip}")
	public void setIp(String ip) {
		SFTPConnectionFactory.ip = ip;
	}
	public static int getPort() {
		return port;
	}
	@Value("${sftp.port}")
	public void setPort(int port) {
		SFTPConnectionFactory.port = port;
	}
	
}
