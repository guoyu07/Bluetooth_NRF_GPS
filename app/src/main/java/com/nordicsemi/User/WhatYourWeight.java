package com.nordicsemi.User;

import com.nordicsemi.nrfUARTv2.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class WhatYourWeight extends Activity implements OnClickListener{
	
	private ImageView getbackweight;
	private Button nextstepweight, weight_up, weight_down;
	private TextView showweight;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.what_your_weight);
		
		getbackweight = (ImageView) findViewById(R.id.get_back_weight);
		nextstepweight = (Button) findViewById(R.id.next_step_weight);
		
		weight_down = (Button) findViewById(R.id.weight_down);
		weight_up = (Button) findViewById(R.id.weight_up);
		showweight = (TextView) findViewById(R.id.showweight);
		
		getbackweight.setOnClickListener(this);
		nextstepweight.setOnClickListener(this);
		
		
		weight_down.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showweight.setText(Integer.valueOf(showweight.getText().toString()) - 1 + "");
//				nextstepweight.setVisibility(View.VISIBLE);
				
			}
		});
		weight_up.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showweight.setText(Integer.valueOf(showweight.getText().toString()) + 1 + "");
//				nextstepweight.setVisibility(View.VISIBLE);
			}
		});
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.get_back_weight:
			this.finish();
			break;
		case R.id.next_step_weight:
			startActivity(new Intent(this, WhatYourBirthday.class));

		default:
			break;
		}
		
		
		
	}

}
