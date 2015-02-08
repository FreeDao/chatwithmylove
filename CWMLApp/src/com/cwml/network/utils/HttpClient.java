package com.cwml.network.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


public class HttpClient {

	public static final String HTTP_POST = "POST";
	
	public static final String HTTP_GET  = "GET";
	

	/**
	 * 访问服务器，向服务器发�?http请求�?
	 * @param url 服务器路�?
	 * @param method 访问方法
	 * @param postParam post的参�?
	 * @param connectTimeout 连接超时时间,单位毫秒
	 * @param readTimeout 读取内容超时时间，单位毫�?
	 * @param proxy 代理地址
	 * @param property 连接的属�?
	 * @return 读取到的字节数组 
	 * @throws NullPointerException 传入参数为空
	 * @throws IOException
	 * @throws ProtocolException
	 */
	public static byte[] connect( 
			URL url, 
			String method, 
			String postParam,
			int connectTimeout, 
			int readTimeout) 
		throws NullPointerException, IOException, ProtocolException {
		
		//创建连接
		HttpURLConnection connection = null;
		connection = (HttpURLConnection) url.openConnection();
		//设置属�?
		connection.setConnectTimeout( connectTimeout );
		connection.setReadTimeout( readTimeout );
		connection.setDoInput( true );
		connection.setDoOutput( true );
		connection.setRequestMethod( method );
		connection.setRequestProperty( "Accept-Charset", "utf-8" );

		//如果是post方式传参，则处理
		if( method == HttpClient.HTTP_POST && postParam != null ) {
			BufferedOutputStream out = new BufferedOutputStream( connection.getOutputStream(), 8192 );
			
			out.write( postParam.getBytes( "utf-8" ) );
			out.flush();
			out.close();
		}
		
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		int result = connection.getResponseCode();
		System.out.println( result );
		//判断是否连接成功
		if ( result == 200 ) {
			//如果连接成功则读取内�?
			BufferedInputStream inStream = new BufferedInputStream( connection.getInputStream(), 8192 );
			byte[] buffer = new byte[1024];
			int len = -1;
			while( ( len = inStream.read( buffer ) ) != -1 ) {
				outStream.write(buffer, 0, len);
			}
			inStream.close();
		}
		outStream.close();
		connection.disconnect();
		return outStream.toByteArray();
	}
	
	/**
	 * 判断网络是否连接
	 * @param context
	 * @return - true 网络连接
	 * 		   - false 网络连接异常
	 */
	public static boolean isConnect( Context context ) {

		try {
			ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
			if( connectivity != null ) {
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if( info != null && info.isConnected() && info.isAvailable() ) {
					if( info.getState() == NetworkInfo.State.CONNECTED ) {
						return true;
					}
				}
			}
		} catch ( Exception e ) {
			Log.v( "error", e.toString() );
		}
		return false;
	}
}