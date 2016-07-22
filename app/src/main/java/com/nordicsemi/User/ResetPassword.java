package com.nordicsemi.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nordicsemi.nrfUARTv2.R;

public class ResetPassword extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
    }

    public void reset_pwd_onClick(View view){
        if(view == findViewById(R.id.btn_return_left)){
            Intent intent = new Intent (ResetPassword.this, LoginActivity.class);
            startActivity(intent);
            ResetPassword.this.finish();
        }
    }
}
