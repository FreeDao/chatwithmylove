package com.cwml.utils;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

public class Utils {

	public static boolean isNull(String str){
		return (str == null) || str.equals("");
	}
	
	public static String getCurrentTime(){
		 return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).toString();
	}
	   
	    /**
	   
	    * 功能：提取文件名的后缀
	   
	    *
	   
	    * @param fileName
	   
	    * @return
	   
	    */
	   
	    private static  String getExtention(String fileName) {
		    int pos = fileName.lastIndexOf(".");
		    return fileName.substring(pos + 1);
	   
	    }
}
