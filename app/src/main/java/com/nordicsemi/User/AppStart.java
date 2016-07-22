package com.nordicsemi.User;

import com.nordicsemi.nrfUARTv2.R;

import android.os.Bundle;
import android.os.Handler;
import android.provider.Contacts;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;

public class AppStart extends Activity{
	private SharedPreferences sharedPreferences;
	private String is_first;
	private char testnum;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.appstart);

	new Handler().postDelayed(new Runnable(){
		@Override
		public void run(){
				Intent intent = new Intent (AppStart.this, LoginActivity.class);
				startActivity(intent);	
				AppStart.this.finish();
		}
	}, 2000);
   }
}