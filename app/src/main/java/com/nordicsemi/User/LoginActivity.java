package com.nordicsemi.User;

import com.nordicsemi.Myfiles.FileService;
import com.nordicsemi.nrfUARTv2.BleActivity;
import com.nordicsemi.nrfUARTv2.R;


import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
	}

	public void onClick_Login(View view){
		if(view == findViewById(R.id.new_user)){
			Intent intent = new Intent (LoginActivity.this, UserRegistActivity.class);
			startActivity(intent);
			LoginActivity.this.finish();
		}
		if(view == findViewById(R.id.forget_password)){
			Intent intent = new Intent (LoginActivity.this, ResetPassword.class);
			startActivity(intent);
			LoginActivity.this.finish();
		}
		if(view == findViewById(R.id.btn_login)){
			//得到两个文本输入框的id
			EditText accountEditText = (EditText) findViewById(R.id.account_editext);
			EditText passwdeEditText = (EditText) findViewById(R.id.password_edit);
			//得到两个文本输入框的内容
			String accountString = accountEditText.getText().toString();
			String passwdString = passwdeEditText.getText().toString();

			FileService service = new FileService(getApplicationContext());
			//被抛出的例外在这里实现（try{}catch捕获例外异常）
			try {
				service.save(accountString, passwdString);
				Toast.makeText(getApplicationContext(), "登录成功", 1).show();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Toast.makeText(getApplicationContext(), "saved faile!!!!", 1).show();
				e.printStackTrace();
			}

			Intent intent = new Intent (LoginActivity.this, MainUserUI.class);
			startActivity(intent);
			LoginActivity.this.finish();

		}
	}
}
