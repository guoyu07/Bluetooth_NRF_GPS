package com.nordicsemi.Myfiles;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

//public class SmsReceiverToast extends BroadcastReceiver {
//	
//    @Override
//    public void onReceive(Context context, Intent intent) {
//    	
//        // TODO Auto-generated method stub
//        StringBuilder body = new StringBuilder();
//        StringBuilder number = new StringBuilder();  
//        Bundle bundle = intent.getExtras();     
//        
//        if (bundle != null) {     
//            Object[] _pdus = (Object[]) bundle.get("pdus");     
//            SmsMessage[] message = new SmsMessage[_pdus.length];   
//            
//            for (int i = 0; i < _pdus.length; i++) {     
//                message[i] = SmsMessage.createFromPdu((byte[]) _pdus[i]);     
//            }     
//            
//            for (SmsMessage currentMessage : message) {     
//                body.append(currentMessage.getDisplayMessageBody());     
//                number.append(currentMessage.getDisplayOriginatingAddress());     
//            }     
//            String smsBody = body.toString();     
//            String smsNumber = number.toString();
//            Toast.makeText(context, "号码:"+smsNumber+"\n内容:"+smsBody, Toast.LENGTH_SHORT).show();
//        }
//    }
// 
//}

public class SmsReceiverToast extends BroadcastReceiver{

    public static final String TAG = "SmsReceiverToast";
    StringBuilder body = new StringBuilder();
    StringBuilder number = new StringBuilder(); 
    //android.provider.Telephony.Sms.Intents

    public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent){

       if (intent.getAction().equals(SMS_RECEIVED_ACTION))
       {
           SmsMessage[] messages = getMessagesFromIntent(intent);

           for (SmsMessage message : messages)
           {
              Log.i(TAG, message.getOriginatingAddress() + " : " +
                  message.getDisplayOriginatingAddress() + " : " +
                  message.getDisplayMessageBody() + " : " +
                  message.getTimestampMillis());
              	  body.append(message.getDisplayMessageBody());     
                  number.append(message.getDisplayOriginatingAddress());
           }
           
         String smsBody = body.toString();     
         String smsNumber = number.toString();
         Toast.makeText(context, "号码:"+smsNumber+"\n内容:"+smsBody, Toast.LENGTH_LONG).show();//toast显示信息
       }
       
    }

    public final SmsMessage[] getMessagesFromIntent(Intent intent){

        Object[] messages = (Object[]) intent.getSerializableExtra("pdus");

        byte[][] pduObjs = new byte[messages.length][];

        for (int i = 0; i < messages.length; i++)
        {
            pduObjs[i] = (byte[]) messages[i];
        }

        byte[][] pdus = new byte[pduObjs.length][];

        int pduCount = pdus.length;

        SmsMessage[] msgs = new SmsMessage[pduCount];

        for (int i = 0; i < pduCount; i++)
        {
            pdus[i] = pduObjs[i];
            msgs[i] = SmsMessage.createFromPdu(pdus[i]);
        }

        return msgs;

    }

	private void showDialog(Activity activity, String smsString) {
		// TODO Auto-generated method stub
		  new AlertDialog.Builder(activity)  
		  .setTitle("测试AlertDialog.Builder") 
		  .setMessage(smsString) 
		  .setPositiveButton("是", null) 
		  .setNegativeButton("否", null) 
		  .show(); 
	}

}

