package com.nordicsemi.User;

import com.nordicsemi.nrfUARTv2.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ExitFromSettings extends Activity {
	//private MyDialog dialog;
	private LinearLayout layout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exit_dialog_from_settings);
		//dialog=new MyDialog(this);
		layout=(LinearLayout)findViewById(R.id.exit_layout2);
		layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "锟斤拷示锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷獠匡拷乇沾锟斤拷冢锟?", 
						Toast.LENGTH_SHORT).show();	
			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event){
		finish();
		return true;
	}
	
	public void exitbutton1(View v) {  
    	this.finish();    	
      }  
	public void exitbutton0(View v) {  
    	this.finish();
    	MainUserUI.instance.finish();//锟截憋拷Main 锟斤拷锟紸ctivity
      }  
	
}
