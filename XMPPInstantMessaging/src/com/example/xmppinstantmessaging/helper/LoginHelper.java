package com.example.xmppinstantmessaging.helper;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import org.jivesoftware.smack.XMPPException;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.example.xmppinstantmessaging.application.XMPPApplication;
import com.example.xmppinstantmessaging.config.Config;
import com.example.xmppinstantmessaging.object.UserInfor;

public class LoginHelper {

	private ScheduledExecutorService mExecutors; //线程池
	private Handler mHandler;
	private UserInfor mUser;
	
	public LoginHelper(Handler handler){
		this.mHandler = handler;
		this.mExecutors = Executors.newScheduledThreadPool(3);
	}
	
	public void setUserInfor( UserInfor userInfor){
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
				String username = mUser.getUsername();
				String password = mUser.getPassword();
				Log.e("mXmppConnection.isConnected()", "登录信息："+"username:"+username+" \tpassword:"+password);
				XMPPApplication.mXmppConnection.login(mUser.getUsername(), mUser.getPassword());
				XMPPApplication.mCurrentUser.setLogin(true);
				notifyLoginStatus(Config.Login.LOGIN_STATUS_SUCCESS);
			} catch (XMPPException e) {
				e.printStackTrace();
				Log.e("mXmppConnection.isConnected()", "登录失败！"+e.getMessage());
				notifyLoginStatus(Config.Login.LOGIN_STATUS_FAIL);
			}
			
		}
	}
	
	/**
	 * 取消登录
	 */
	public void cancelLogin(){
		//先断开服务器连接 然后重连服务器
		XMPPApplication.disconnect();
		XMPPApplication.startConnectService();
	}
	
	
	/**
	 * 通知主界面登录状态
	 */
	private void notifyLoginStatus(int status){
		Message msg = Message.obtain();
		switch (status) {
		case Config.Login.LOGIN_STATUS_SUCCESS:
			msg.arg1 = Config.Login.LOGIN_STATUS_SUCCESS;
			break;

		case Config.Login.LOGIN_STATUS_FAIL:
			msg.arg1 = Config.Login.LOGIN_STATUS_FAIL;
			break;
		}
		mHandler.sendMessage(msg);
	}
}
