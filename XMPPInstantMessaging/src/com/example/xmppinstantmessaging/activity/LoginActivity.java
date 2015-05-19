package com.example.xmppinstantmessaging.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xmppinstantmessaging.R;
import com.example.xmppinstantmessaging.application.XMPPApplication;
import com.example.xmppinstantmessaging.config.Config;
import com.example.xmppinstantmessaging.helper.LoginHelper;
import com.example.xmppinstantmessaging.object.UserInfor;

public class LoginActivity extends Activity implements View.OnClickListener {

	private EditText mEd_user;
	private EditText mEd_psw;
	private Button mLogin;
	private LoginHelper loginHelper; // 登录辅助类
	private UserInfor mUserInfor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mEd_user = (EditText) findViewById(R.id.ed_user);
		mEd_psw = (EditText) findViewById(R.id.ed_psw);
		mLogin = (Button) findViewById(R.id.login);
		mLogin.setOnClickListener(this);
		mUserInfor = new UserInfor();
	}

	private String checkOut() {

		StringBuffer sb = new StringBuffer();
		String username = mEd_user.getText().toString().trim();
		String password = mEd_psw.getText().toString().trim();
		if (username == null || username.equals("")) {
			sb.append("用户名   ");
		} else {
			mUserInfor.setUsername(username);
		}
		if (password == null || password.equals("")) {
			sb.append("密码");
		} else {
			mUserInfor.setPassword(password);
		}
		return sb.toString();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login:
			String checkOutStr = checkOut();
			if (checkOutStr.equals("")) {
				login();
			} else {
				Toast.makeText(getApplicationContext(), checkOutStr + "不能为空",
						Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}

	//登陆
	public void login() {
		loginHelper = new LoginHelper(mHanlder, mUserInfor);
		loginHelper.Login();
	}

	
	/**
	 * Message 参数:
	 * arg1  登录状态
	 * obj  返回内容
	 */
	public Handler mHanlder = new Handler() {

		@Override
		public void handleMessage(android.os.Message msg) {
			int status = msg.arg1; 
			String str = (String) msg.obj;
			switch (status) {
			case Config.Login.LOGIN_STATUS_SUCCESS:  //登陆成功！
				XMPPApplication.mCurrentUser.setLogin(true);
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), MainActivity.class);
				startActivity(intent);
				break;

			case Config.Login.LOGIN_STATUS_FAIL:     //登陆失败!
				
				break;
			}
		};
	};
}
