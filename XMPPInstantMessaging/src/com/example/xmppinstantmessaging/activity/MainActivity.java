package com.example.xmppinstantmessaging.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.example.xmppinstantmessaging.R;

public class MainActivity extends Activity {

	private TextView tv;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tv = (TextView) findViewById(R.id.tv);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
