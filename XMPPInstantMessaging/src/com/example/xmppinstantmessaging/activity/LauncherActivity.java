package com.example.xmppinstantmessaging.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.example.xmppinstantmessaging.R;

public class LauncherActivity extends Activity {

	RelativeLayout mLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher);
		mLayout = (RelativeLayout) findViewById(R.id.layout);
		startWelcomeAnimation();
	}

	private void startWelcomeAnimation() {
		AnimationSet animaSet = (AnimationSet) AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.welcome_animation);
		animaSet.setAnimationListener( new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				enterActivity();
			}
		});
		mLayout.startAnimation(animaSet);
	}
	
	/**
	 * 进入聊天界面 或者登录界面 根据用户是否已经登录
	 */
	private void enterActivity(){
		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), LoginActivity.class);
		startActivity(intent);
		finish();
	}
}
