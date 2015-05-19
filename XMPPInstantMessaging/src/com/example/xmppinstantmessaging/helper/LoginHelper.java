package com.example.xmppinstantmessaging.helper;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import org.jivesoftware.smack.XMPPException;
import android.os.Handler;
import android.util.Log;
import com.example.xmppinstantmessaging.application.XMPPApplication;
import com.example.xmppinstantmessaging.object.UserInfor;

public class LoginHelper {

	private ScheduledExecutorService mExecutors; //线程池
	private Handler mHandler;
	private UserInfor mUser;
	
	public LoginHelper(Handler handler, UserInfor userInfor){
		this.mHandler = handler;
		this.mExecutors = Executors.newScheduledThreadPool(3);
		this.mUser = userInfor;
	}
	
	
	public void Login(){
		mExecutors.execute(new LogingThread());
	}
	
	
	/**
	 * 开启线程登陆
	 * @author Administrator
	 */
	public class LogingThread implements Runnable{

		int count = 0;
		
		@Override
		public void run() {
			
			/**
			 * 为避免xmppConnection没有连接上而报错,故在没连接上时,死循环阻塞线程
			 * 每隔100毫秒,判断是否连接上,判断60秒
			 * 1min后报超时
			 */
			while (count < 10 * 60 
					&& XMPPApplication.mXmppConnection.isConnected() == false) {
				try {
					Thread.sleep(100);// 休眠100毫秒
					count++;
				} catch (InterruptedException e) {
					
				}
			}
			
			//如果过了一分钟还没有连接上 报超时异常
			if(XMPPApplication.mXmppConnection.isConnected() == false){
				Log.e("mXmppConnection.isConnected()", "服务器连接失败！");
			}
			
			//开始登录
			try {
				XMPPApplication.mXmppConnection.login(mUser.getUsername(), mUser.getPassword());
			} catch (XMPPException e) {
				e.printStackTrace();
				Log.e("mXmppConnection.isConnected()", "登录失败！"+e.getMessage());
			}
			
		}
		
	}
}
