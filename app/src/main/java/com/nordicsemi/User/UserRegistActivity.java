package com.nordicsemi.User;

import com.nordicsemi.nrfUARTv2.R;
import com.nordicsemi.nrfUARTv2.R.id;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class UserRegistActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_registing);
	}

	public void register_onClick(View view) {
		if (view == findViewById(R.id.btn_return_left)) {
			Intent intent = new Intent(UserRegistActivity.this, LoginActivity.class);
			startActivity(intent);
			UserRegistActivity.this.finish();
		}
	}
}
