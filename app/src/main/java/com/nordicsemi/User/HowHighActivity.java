package com.nordicsemi.User;

import com.nordicsemi.nrfUARTv2.DeviceListActivity;
import com.nordicsemi.nrfUARTv2.BleActivity;
import com.nordicsemi.nrfUARTv2.R;
import com.nordicsemi.nrfUARTv2.R.layout;
import com.nordicsemi.nrfUARTv2.UartService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HowHighActivity extends Activity implements OnClickListener{

	/*���ﶨ��Ļ���Ҫ�������Ͷ�Ӧ����ImageView��Button*/
	private ImageView getback;
	private Button nextstepheigh, heigh_up, heigh_down;
	private TextView heightshow;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.how_high_activity);



		getback = (ImageView) findViewById(R.id.get_back);
		nextstepheigh = (Button) findViewById(R.id.next_step_heigh);

		heigh_down = (Button) findViewById(R.id.heigh_down);
		heigh_up = (Button) findViewById(R.id.heigh_up);
		heightshow = (TextView) findViewById(R.id.height);

		getback.setOnClickListener(this);
		nextstepheigh.setOnClickListener(this);

		heigh_up.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				heightshow.setText(Integer.valueOf(heightshow.getText().toString()) + 1 + "");//���+������1
//				nextstepheigh.setVisibility(View.VISIBLE);

			}
		});

		heigh_down.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				heightshow.setText(Integer.valueOf(heightshow.getText().toString()) - 1 + "");//���-������1
//				nextstepheigh.setVisibility(View.VISIBLE);

			}
		});

	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.get_back:
			this.finish();
			//�����ǰ����ķ��ز���
			//����ֱ��finish();
			//finish(this);���ﲻҪдthis��finish���治�ܷŶ�����
			break;
		case R.id.next_step_heigh:
			startActivity(new Intent(this, WhatYourSexActivity.class));

			break;

		default:
			break;
		}

	}

}
