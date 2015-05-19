package com.example.xmppinstantmessaging.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.xmppinstantmessaging.R;
import com.example.xmppinstantmessaging.application.XMPPApplication;

public class MainActivity extends Activity {

	private TextView tv;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        init();
    }
    
    private void init(){
    	if(XMPPApplication.mCurrentUser.isLogin()){
    		tv.setText("欢迎进入易聊室！");
    	}else{
    		tv.setText("您还未登录！");
    	}
    }

    
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	XMPPApplication.disconnect();
    }
}
