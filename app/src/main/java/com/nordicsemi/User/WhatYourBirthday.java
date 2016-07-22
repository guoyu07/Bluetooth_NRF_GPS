package com.nordicsemi.User;

import com.nordicsemi.nrfUARTv2.BleActivity;
import com.nordicsemi.nrfUARTv2.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class WhatYourBirthday extends Activity implements OnClickListener{
	
	private ImageView getbackbirthday;
	private Button nextstepbirthday;
	private Button year_up, year_down, month_up, month_down, day_up, day_down;
	private TextView yearshow, monthshow, dayshow;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.what_your_birthday);
		
		getbackbirthday = (ImageView) findViewById(R.id.get_back_birthday);
		nextstepbirthday = (Button) findViewById(R.id.next_step_birthday);
		
		year_down = (Button) findViewById(R.id.year_down);
		year_up = (Button) findViewById(R.id.year_up);
		month_down = (Button) findViewById(R.id.month_down);
		month_up = (Button) findViewById(R.id.month_up);
		day_down = (Button) findViewById(R.id.day_down);
		day_up = (Button) findViewById(R.id.day_up);
		yearshow = (TextView) findViewById(R.id.yearshow);
		monthshow = (TextView) findViewById(R.id.monthshow);
		dayshow = (TextView) findViewById(R.id.dayshow);
		
		getbackbirthday.setOnClickListener(this);
		nextstepbirthday.setOnClickListener(this);
		
		year_down.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				yearshow.setText(Integer.valueOf(yearshow.getText().toString()) - 1 + "");
			}
		});
		month_down.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				monthshow.setText(Integer.valueOf(monthshow.getText().toString()) - 1 + "");
			}
		});
		day_down.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					dayshow.setText(Integer.valueOf(dayshow.getText().toString()) - 1 + "");
			}
		});
		year_up.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				yearshow.setText(Integer.valueOf(yearshow.getText().toString()) + 1 + "");
			}
		});
		month_up.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				monthshow.setText(Integer.valueOf(monthshow.getText().toString()) + 1 + "");
			}
		});
		day_up.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dayshow.setText(Integer.valueOf(dayshow.getText().toString()) + 1 + "");
			}
		});
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.get_back_birthday:
			this.finish();
			break;
		case R.id.next_step_birthday:
			startActivity(new Intent(this, UserRegistActivity.class));
		default:
			break;
		}
		
	}

}
