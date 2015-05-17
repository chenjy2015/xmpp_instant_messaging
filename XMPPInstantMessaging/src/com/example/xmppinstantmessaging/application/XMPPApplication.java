package com.example.xmppinstantmessaging.application;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.example.xmppinstantmessaging.R;

import android.app.Application;
import android.util.Log;

public class XMPPApplication extends Application {

	public static XMPPConnection mXmppConnection; //服务器端连接对象
	public static ConnectionConfiguration mConnectionConfig;//连接配置
	
	public static String mHost1 = ""; //服务器偏好设置1
	public static String mHost2 = ""; //服务器偏好设置2
	public static String mHost = "";  //服务器偏好设置(地址)
	public static String mServiceName = ""; //服务器名称
	public static ScheduledExecutorService mExecutors;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		loadXMLConfig();
		//参数 1服务器地址 2端口号 3服务器名称 
		mHost = mHost1;
		mConnectionConfig = new ConnectionConfiguration(mHost, 5222, mServiceName);
		mXmppConnection = new XMPPConnection(mConnectionConfig);
		
		mExecutors = Executors.newScheduledThreadPool(3);//初始化线程池
		
	}
	
	//加载配置信息  
	private void loadXMLConfig(){
		XmlPullParser parser = getResources().getXml(R.xml.config);
		try {
			int eventType = parser.getEventType();
			do{
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.START_TAG:
					if(parser.getName().equals("host1")){
						mHost1 = parser.nextText();
					}else if(parser.getName().equals("host2")){
						mHost2 = parser.nextText();
					}else if(parser.getName().equals("serviceName")){
						mServiceName = parser.nextText();
					}
					break;
				case XmlPullParser.END_TAG:
					break;
				}
				
				eventType = parser.next();
				
				
			}while(eventType != XmlPullParser.END_DOCUMENT);
			
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//连接服务器
	private void startConnectService(){
		
	} 
}
