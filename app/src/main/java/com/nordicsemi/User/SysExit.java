package com.nordicsemi.User;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

public class SysExit {
	
	//建立一个public static的list用来放activity 
    public static List activityList = new ArrayList(); 
    
    
      //finish所有list中的activity，结束前面所有的Activity
    public static void exit(){    
    	
        int siz=activityList.size();     
        for(int i=0;i<siz;i++){        
            if(activityList.get(i)!=null){            
                ((Activity) activityList.get(i)).finish();        
                }     
            } 
    }
    
    //自己定义一个延时程序
    private void delay(int ms){
		try {
            Thread.currentThread();
			Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } 
	 }	
	
	
	
	

}
