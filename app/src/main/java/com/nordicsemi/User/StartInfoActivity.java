package com.nordicsemi.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.nordicsemi.nrfUARTv2.R;

public class StartInfoActivity extends Activity /*implements OnClickListener*/ {
	
	private static final float FLING_MIN_DISTANCE = 0;
	private static final float FLING_MIN_VELOCITY = 0;
	private Button startbutton;
	//Activity创建时被调用
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_info_activity);
		/*按住Ctrl后如果直接点start_info_activity就会进去start_info的R文件中查看id，按Ctrl再把鼠标放在上面定一下，点第二个就可以跳到.xml文件了*/
		
		InitView();
	
	}
	
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {  
        // TODO Auto-generated method stub  
        if(e1.getX() - e2.getX() > FLING_MIN_DISTANCE&&Math.abs(velocityX) > FLING_MIN_VELOCITY)  
        {  
            Intent intent = new Intent(StartInfoActivity.this,StartLogoActivity.class);  
            startActivity(intent);  
             Toast.makeText(this, "向左手势", Toast.LENGTH_SHORT).show();   
  
        }  
        else if (e2.getX()-e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) >FLING_MIN_VELOCITY) {  
              
            //切换Activity  
            Intent intent = new Intent(StartInfoActivity.this, HowHighActivity.class);  
            startActivity(intent);  
            Toast.makeText(this, "向右手势", Toast.LENGTH_SHORT).show();  
        }  
          
        return false;  
    }  
	

	private void InitView() {
		// TODO Auto-generated method stub
		startbutton = (Button) findViewById(R.id.start_info_btn);
		
		startbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), HowHighActivity.class));
			}
		});
		/*一个按键的情况是，直接监听后，把鼠标放在setOnClickListener上面，显示黄色的提示，然后点onClick，再在onClick中startActivity跳转就可以了*/
		
	}

//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		switch (v.getId()) {
//		case R.id.start_info_btn:
//			startActivity(new Intent(this, HowHighActivity.class));
//			break;
//
//		default:
//			break;
//		}
//	}
	

}
