package com.nordicsemi.Myfiles;

import java.util.Set;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/*这个类实现的是处理操作系统的事件，它处于一直监听的状态，知道操作系统有事件发生时才会有所响应*/
/* 自定义继承自BroadcastReceiver类,监听系统服务广播的信息 */
public class EX06_01_SMSreceiver extends BroadcastReceiver   
{   
 /*声明静态字符串,并使用android.provider.Telephony.SMS_RECEIVED作为Action为短信的依据*/
private static final String mACTION = "android.provider.Telephony.SMS_RECEIVED";   

/*
 * 实现广播的监听反应必须要重载的函数
 * 这个函数有一个重要的参数Intent，它是系统或用户发送的Intent变量
 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
 */
@Override   
public void onReceive(Context context, Intent intent)   
{   
  // TODO Auto-generated method stub   
  /* 判断传来Intent是否为短信
   * 需要在过滤器中设置
   * */
  if (intent.getAction().equals(mACTION))   
  {   
    /*建构一字符串集集合变量sb*/  
    StringBuilder sb = new StringBuilder();   
    /*接收由Intent传来的数据*/  
    Bundle bundle = intent.getExtras();   
    /*判断Intent是有资料*/  
    if (bundle != null)   
    {  
      Set<String> keysSet=bundle.keySet();  
      for(String keyString:keysSet){  
        Log.d("key", keyString);  
      }  
      /* pdus为 android内建短信参数 identifier 
       * 透过bundle.get("")并传一个包含pdus的对象*/  
      Object[] myOBJpdus = (Object[]) bundle.get("pdus");   
      /*建构短信对象array,并依据收到的对象长度来建立array的大小*/  
      SmsMessage[] messages = new SmsMessage[myOBJpdus.length];    
      for (int i = 0; i<myOBJpdus.length; i++)   
      {    
        messages[i] = SmsMessage.createFromPdu ((byte[]) myOBJpdus[i]);    
      }   
          
      /* 将送来的短信合并自定义信息于StringBuilder当中 */    
      for (SmsMessage currentMessage : messages)   
      {    
        sb.append("接收到来告:\n");    
        /* 来讯者的电话号码 */   
        sb.append(currentMessage.getDisplayOriginatingAddress());    
        sb.append("\n------传来的短信------\n");    
        /* 取得传来讯息的BODY */    
        sb.append(currentMessage.getDisplayMessageBody());    
      }    
    }      
    /* 北Notification(Toase)显示短信信息  */  
    Toast.makeText(context, sb.toString(), Toast.LENGTH_LONG).show();   
       
    /* 返并加Activity */   
//    Intent i = new Intent(context, EX06_01.class);   
//    Bundle bundle2=new Bundle();  
//    bundle2.putString("SMS", sb.toString());  
//    i.putExtra("SMSS", bundle2);  
//    /*设定让加Activity以一个新的task来执行*/  
//    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
//    context.startActivity(i);   
  }   
}   
}   
