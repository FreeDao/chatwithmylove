package com.cwml.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

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
