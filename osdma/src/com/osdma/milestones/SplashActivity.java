package com.osdma.milestones;

import java.util.Timer;
import java.util.TimerTask;

import com.example.osdma.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		TimerTask timer = new TimerTask(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				finish();
				Intent myIntent = new Intent(SplashActivity.this, MainActivity.class);
				SplashActivity.this.startActivity(myIntent);
			}
			
		};
		
		Timer time = new Timer();
		time.schedule(timer, 2000);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
