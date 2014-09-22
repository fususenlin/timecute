package com.matrixloop.timecute.utils.email;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.StringUtils;

import com.matrixloop.timecute.app.config.AppConfig;
import com.matrixloop.timecute.utils.log.SLogger;

public class Email {
	
	private static String host;
	private static String port;
	private static String username;
	private static String password;
	
	private static String from = username;
	private static String nickname ;
	
	static {
		Properties p = new Properties();
		try{
			p.load(Email.class.getResourceAsStream(AppConfig.SystemConfig.EMAIL_CONFIG_PATH));
			host = p.getProperty("email_host");
			port = p.getProperty("email_port");
			username = p.getProperty("email_username");
			from = username;
			password = p.getProperty("email_password");
			nickname = p.getProperty("email_nickname");
			
			/*host="smtp.163.com";
			port="25";
			username="timecute_support@163.com";		
			password="l!m@0sheng";
			nickname="时光留书网";
			from = username;*/		
		}catch(IOException e){
			throw new RuntimeException("can't find "+AppConfig.SystemConfig.EMAIL_CONFIG_PATH+" file , system starting failed !", e);
		}catch(Exception e){
			throw new RuntimeException("system initialize failed , when load send mail configurations .", e);
		}finally{
			p = null;
		}
	}
	
	public static String send(List<String> to,List<String> cc,String subject,String content,List<File> attachFiles,boolean isAuth){
		
		Session session = null;
		Properties p = new Properties();
		p.put("mail.smtp.host", host);
		p.put("mail.smtp.port", port);
		p.put("mail.smtp.auth", "true");
		try{
			//session
			if(isAuth){
				Authenticator auth = new EmailAuthenticator();
				session = Session.getDefaultInstance(p,auth);
			}else{
				session = Session.getDefaultInstance(p);
			}
			
			MimeMessage message = new MimeMessage(session);
			//发件人
			Address fromAddress = new InternetAddress(from,nickname);
			message.setFrom(fromAddress);
			//收件人
			if(to != null && to.size() > 0){
				for(String toEmail : to){
					Address toAddress = new InternetAddress(toEmail);
					message.addRecipient(RecipientType.TO, toAddress);
				}
			}
			//抄送人
			if(cc != null && cc.size() > 0){
				for(String ccEmail : cc){
					Address ccAddress = new InternetAddress(ccEmail);
					message.addRecipient(RecipientType.CC, ccAddress);
				}
			}
			
			//发件日期
			message.setSentDate(new Date());
			
			if(!StringUtils.isBlank(subject)){
				message.setSubject(subject);
			}
			
			Multipart mp = new MimeMultipart();
			//文本
			MimeBodyPart textBody = new MimeBodyPart();
			textBody.setContent(content, "text/html;charset=utf-8");
			mp.addBodyPart(textBody);
			
			//附件
			if(attachFiles != null && attachFiles.size() > 0){
				for(File file : attachFiles){
					FileDataSource fds = new FileDataSource(file);
					MimeBodyPart fileBody = new MimeBodyPart();
					fileBody.setDataHandler(new DataHandler(fds));
					fileBody.setFileName(fds.getName());
					mp.addBodyPart(fileBody);
				}
			}	
			message.setContent(mp);
			message.saveChanges();
			
			Transport.send(message);
			
			if(to != null && to.size() > 0){
				for(String toEmail : to){
					SLogger.info("email has send to :" + toEmail + ",");
				}
			}
			if(cc != null && cc.size() > 0){
				for(String ccEmail : cc){
					SLogger.info("and cc :" + ccEmail + ",");
				}
			}
			SLogger.info(" success!");
			return "SUCCESS";	
		}catch(UnsupportedEncodingException e){
			SLogger.error(e,e);
			return "email coding error";
		}catch(MessagingException e){
			SLogger.error(e,e);
			return "email message error";
		}
	}
	
	
	public static class EmailAuthenticator extends Authenticator {
		public EmailAuthenticator() {
			super();
		}
		public EmailAuthenticator(String user, String pwd) {
			super();
			username = user;
			password = pwd;
		}
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}
	}
}
