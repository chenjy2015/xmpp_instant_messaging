package com.example.xmppinstantmessaging.shared;

import com.example.xmppinstantmessaging.object.UserInfor;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferenceHelper {

	public static final String user_login_history_uri = "user_login_history";
	
	/**
	 * 保存用户登录记录
	 * @param context
	 * @param userInfor
	 */
	public void saveUser(Context context, UserInfor userInfor){
		SharedPreferences sp = context.getSharedPreferences(user_login_history_uri, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("username", userInfor.getUsername());
		editor.putString("password", userInfor.getPassword());
		editor.commit();
	}
	
	
	/**
	 * 提取已登录的用户
	 * @param context
	 * @return
	 */
	public UserInfor getUser(Context context){
		UserInfor userInfor = new UserInfor();
		SharedPreferences sp = context.getSharedPreferences(user_login_history_uri, Context.MODE_PRIVATE);
		String username = sp.getString("username", "");
		String password = sp.getString("password", "");
		userInfor.setUsername(username);
		userInfor.setPassword(password);
		return userInfor;
	}
}
