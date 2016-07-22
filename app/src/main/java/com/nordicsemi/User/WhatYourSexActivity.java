package com.nordicsemi.User;

/*import android.R;*/
/*刚才出现R文件的路径不对的问题，要把它修改成import com.nordicsemi.nrfUARTv2.R;即R文件的路径要和工程名统一**/

import com.nordicsemi.nrfUARTv2.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class WhatYourSexActivity extends Activity implements OnClickListener{
	
	private ImageView getbacksex;
	private Button nextstepsex;
	private TextView sexshow;
	private ImageButton boy_logo_image, girl_logo_image;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.what_your_sex);
		
		getbacksex = (ImageView) findViewById(R.id.get_back_sex);
		nextstepsex = (Button) findViewById(R.id.next_step_sex);
		sexshow = (TextView) findViewById(R.id.sexshow);
		girl_logo_image = (ImageButton) findViewById(R.id.girl_logo_image);
		boy_logo_image = (ImageButton) findViewById(R.id.boy_logo_image);
		
		getbacksex.setOnClickListener(this);
		nextstepsex.setOnClickListener(this);
		
		girl_logo_image.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				sexshow.setText("女");
				v.setBackgroundResource(R.drawable.girl_logo);
				boy_logo_image.setBackgroundResource(R.drawable.boy_logo_black);
//				nextstepsex.setVisibility(View.VISIBLE);
				
				return false;
			}
		});
		boy_logo_image.setOnTouchListener(new OnTouchListener(){     
            @Override    
            public boolean onTouch(View v, MotionEvent event) {     
            	
            		  sexshow.setText("男");
//                    if(event.getAction() == MotionEvent.ACTION_DOWN){     
                            //更改为按下时的背景图片     
                            v.setBackgroundResource(R.drawable.boy_logo);     
//                    }else if(event.getAction() == MotionEvent.ACTION_UP){     
//                            //改为抬起时的图片     
                            girl_logo_image.setBackgroundResource(R.drawable.girl_logo_black);     
//                            nextstepsex.setVisibility(View.VISIBLE);
//                    }     
                    return false;     
            }     
    });   


		
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.get_back_sex:
			this.finish();
			break;
			
		case R.id.boy_logo_image:
			break;
			
		case R.id.girl_logo_image:
			break;
			
		case R.id.next_step_sex:
			startActivity(new Intent(this, WhatYourWeight.class));
			break;
 
		default:
			break;
		}
		
	}

	
	

}
