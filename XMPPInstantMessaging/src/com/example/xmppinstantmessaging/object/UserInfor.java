package com.example.xmppinstantmessaging.object;

/**
 * 用户信息
 * 
 * @author chenjy
 * 
 */

public class UserInfor {

	String username; // 用户名称
	String password; // 密码
	String nickname; // 昵称
	boolean isLogin; // 登录状态

	public UserInfor() {

	}

	public UserInfor(String username, String password, String nickname,
			boolean isLogin) {
		super();
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.isLogin = isLogin;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

}
