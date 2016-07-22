package com.nordicsemi.User;

import java.util.Timer;
import java.util.TimerTask;

import com.nordicsemi.nrfUARTv2.BleActivity;
import com.nordicsemi.nrfUARTv2.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class StartLogoActivity extends Activity implements OnClickListener {
	
	/*监听时定义*/
	private Button btnJoinSporta,btnLongin,blebtn;
	private Handler handler;
	
	//Activity创建时被调用，@Override表示重写方法（功能	）
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.start_logo_activity);
		/*使用start_logo_activity.xml文件定义的界面布局*/
		
		/*方法定义，初始化控件，相当于封装一个程序块*/
		initview();
		
//		/*下面用调用定时器*/
//		Timer time = new Timer();
//		/*新的任务*/
//		TimerTask timerTask = new TimerTask() {
//			/*这个任务做什么run，@Override就是重写方法（功能	）*/
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				Message message = Message.obtain(handler, 1);/*发送标识符*/
//				handler.sendMessage(message);/*//处理message
//*/			}
//		};
//		time.schedule(timerTask, 1);//1秒
//		/*处理的方法*/
//		handler = new Handler(){
//			@Override
//			public void handleMessage(Message msg) {
//				//TODO Auto-generated method stub
//				super.handleMessage(msg);
//				switch (msg.what) {
//				case 1:
//					btnJoinSporta.setVisibility(View.VISIBLE);/*延时后才显示两个按钮*/
//					btnLongin.setVisibility(View.VISIBLE);
//					break;
//
//				default:
//					break;
//				}
//			}
//		};
		
		try {
			Thread.sleep(1000);
			btnJoinSporta.setVisibility(View.VISIBLE);/*延时后才显示两个按钮*/
			btnLongin.setVisibility(View.VISIBLE);	
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	private void initview() {
		// TODO Auto-generated method stub
		/*找到ID*/
		btnJoinSporta = (Button) findViewById(R.id.start_btn_join_sporta);
		btnLongin = (Button) findViewById(R.id.start_btn_login);
		blebtn = (Button) findViewById(R.id.btn_to_ble);
		/*设置监听器（具有监听属性）*/
		btnJoinSporta.setOnClickListener(this);
		btnLongin.setOnClickListener(this);
		blebtn.setOnClickListener(this);
	}
	
	/*上面已经设置监听属性，有点击事件就会调用里面的处理方法*/
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.start_btn_join_sporta:
			startActivity(new Intent(this, StartInfoActivity.class));//点击按钮跳到另外一个Activity
			break;
			
		case R.id.start_btn_login:
			startActivity(new Intent(this, LoginActivity.class));
			break;	
			
		case R.id.btn_to_ble:
			startActivity(new Intent(this, BleActivity.class));
//			Toast toast = Toast.makeText(this, "跳到码表主页面", Toast.LENGTH_SHORT);
//			toast.show();
			break;				

		default:
			break;
		}
	/*上面的startActivity();也可以像下面一样写*/	
//		Intent intent = new Intent(this, StartInfo.class);
//		startActivity(intent);
	}
	
	
	@Override
	public void onBackPressed() {
	        new AlertDialog.Builder(this)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle(R.string.popup_title)
	        .setMessage(R.string.popup_message)
	        .setPositiveButton(R.string.popup_yes, new DialogInterface.OnClickListener()
	            {
	                @Override
	                public void onClick(DialogInterface dialog, int which) {
		                finish();
	            }
	        })
	        .setNegativeButton(R.string.popup_no, null)
	        .show();
	    }
	}

