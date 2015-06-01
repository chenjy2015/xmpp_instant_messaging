package com.example.xmppinstantmessaging.application;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Application;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.xmppinstantmessaging.R;
import com.example.xmppinstantmessaging.object.UserInfor;

public class XMPPApplication extends Application {

	public static XMPPConnection mXmppConnection; // 服务器端连接对象
	public static ConnectionConfiguration mConnectionConfig;// 连接配置

	public static String mHost1 = ""; // 服务器偏好设置1
	public static String mHost2 = ""; // 服务器偏好设置2
	public static String mHost = ""; // 服务器偏好设置(地址)
	public static int mPort = 5222; // 服务器连接端口号
	public static String mServiceName = ""; // 服务器名称
	public static ScheduledExecutorService mExecutors; //线程池
	public static final int XMPPCONNECT_SUCCESS = 10001;// 连接成功
	public static final int XMPPCONNECT_FAIL = 10002;// 连接失败
	public static final int XMPPCONNECT_ONE = 10; // 连接服务器第一个偏好设置
	public static final int XMPPCONNECT_TWO = 11; // 连接服务器第二个偏好设置
	public static boolean isConnected;//标识服务器是否已经连接上
	
	public static UserInfor mCurrentUser;
	
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		loadXMLConfig();
		mExecutors = Executors.newScheduledThreadPool(3);// 初始化线程池
		mCurrentUser = new UserInfor();//初始化全局用户信息对象
		startConnectService();
	}

	//关闭服务器连接
	public static void disconnect(){
		if(mXmppConnection.isConnected()){
			mXmppConnection.disconnect();
		}
	}

	// 加载配置信息
	private void loadXMLConfig() {
		XmlPullParser parser = getResources().getXml(R.xml.config);
		try {
			int eventType = parser.getEventType();
			do {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.START_TAG:
					if (parser.getName().equals("host1")) {
						mHost1 = parser.nextText();
					} else if (parser.getName().equals("host2")) {
						mHost2 = parser.nextText();
					} else if (parser.getName().equals("serviceName")) {
						mServiceName = parser.nextText();
					}
					break;
				case XmlPullParser.END_TAG:
					break;
				}

				eventType = parser.next();

			} while (eventType != XmlPullParser.END_DOCUMENT);

		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 连接结果回调
	private static Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			int connectType = msg.arg2;
			int result = msg.arg1;
			String error = (String) msg.obj;
			// 先判断连接服务器类型
			switch (connectType) {
			case XMPPCONNECT_ONE:
				if (result == XMPPCONNECT_SUCCESS) {
					isConnected = true;
				} else {
					startConnectServiceAnother();
					isConnected = false;
				}
				break;

			case XMPPCONNECT_TWO:
				if (result == XMPPCONNECT_SUCCESS) {
					isConnected = true;
				} else {
					isConnected = false;
				}
				break;
			}
		};
	};

	// 连接服务器 1
	public static void startConnectService() {
		mHost = mHost1;// 先连host1,如果连不上,就连host2
		mExecutors.submit(new startConnectThread());
	}

	// 连接服务器2
	public static void startConnectServiceAnother() {
		mHost = mHost2;
		mExecutors.submit(new startConnectAotherThread());
	}

	/***
	 * 启动线程连接服务器 1
	 * 
	 * @author Administrator
	 * 
	 */
	private static class startConnectThread implements Runnable {

		public startConnectThread() {
			// 参数 1服务器地址 2端口号 3服务器名称
			mConnectionConfig = new ConnectionConfiguration(mHost, mPort,
					mServiceName);
			mXmppConnection = new XMPPConnection(mConnectionConfig);
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				mXmppConnection.connect();
				if (mXmppConnection.isConnected()) {
					Log.d("mXmppConnection",
							"host: " + mHost + " \t isConnected :"
									+ mXmppConnection.isConnected());
					Message msg = Message.obtain();
					msg.arg1 = XMPPCONNECT_SUCCESS;
					msg.arg2 = XMPPCONNECT_ONE;
					msg.obj = "success";
					mHandler.handleMessage(msg);
				}

			} catch (Exception e) {
				Log.e("抛异常了", "连另外的server");
				Message msg = Message.obtain();
				msg.arg1 = XMPPCONNECT_FAIL;
				msg.arg2 = XMPPCONNECT_ONE;
				msg.obj = e.getMessage() + "";
				mHandler.handleMessage(msg);
			}
		}

	}

	/***
	 * 启动线程连接服务器 2
	 * 
	 * @author Administrator
	 * 
	 */
	private static class startConnectAotherThread implements Runnable {

		public startConnectAotherThread() {
			mConnectionConfig = new ConnectionConfiguration(mHost, mPort,
					mServiceName);
			mXmppConnection = new XMPPConnection(mConnectionConfig);
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				mXmppConnection.connect();
				if (mXmppConnection.isConnected()) {
					Log.d("mXmppConnection",
							"host: " + mHost + " \t isConnected :"
									+ mXmppConnection.isConnected());
					Message msg = Message.obtain();
					msg.arg1 = XMPPCONNECT_SUCCESS;
					msg.arg2 = XMPPCONNECT_TWO;
					msg.obj = "success";
					mHandler.handleMessage(msg);
				}

			} catch (Exception e) {
				Message msg = Message.obtain();
				msg.arg1 = XMPPCONNECT_FAIL;
				msg.arg2 = XMPPCONNECT_TWO;
				msg.obj = e.getMessage() + "";
				mHandler.handleMessage(msg);
			}
		}
	}
}
