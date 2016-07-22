/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nordicsemi.nrfUARTv2;

import java.text.DateFormat;
import java.util.Date;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//监听事件RadioGroup.OnCheckedChangeListener
public class BleActivity extends Activity implements RadioGroup.OnCheckedChangeListener {
	
    private static final int REQUEST_SELECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private static final int UART_PROFILE_READY = 10;
    public static final String TAG = "nRFUART";
    private static final int UART_PROFILE_CONNECTED = 20;//退出提示
    private int mState = UART_PROFILE_DISCONNECTED;////退出提示
    
    private static final int UART_PROFILE_DISCONNECTED = 21;
    private static final int STATE_OFF = 10;
    boolean flag = true;

    TextView mRemoteRssiVal;
    TextView current_speed_textview1, current_speed_textview2, current_speed_textview3, current_speed_textview4, current_speed_textview5, current_speed_textview6;
    TextView average_speed1, average_speed2, average_speed3, average_speed4, average_speed5, average_speed6;
    TextView max_speed1, max_speed2, max_speed3, max_speed4, max_speed5, max_speed6;
    TextView elevation1, elevation2, elevation3, elevation4, elevation5, elevation6, elevation7;
    TextView mileage1, mileage2, mileage3, mileage4, mileage5, mileage6;
    TextView average_peispeed1, average_peispeed2, average_peispeed3, average_peispeed4, average_peispeed5, average_peispeed6;
    TextView use_time1, use_time2, use_time3, use_time4, use_time5;
    
    TextView rise1, rise2, rise3, rise4, rise5, rise6, rise7;
    TextView fall1, fall2, fall3, fall4, fall5, fall6, fall7;
    TextView grade1, grade2, grade3;
    
    RelativeLayout relativeLayout_rise, relativeLayout_rise_total;
    RadioGroup mRg;
    
    private UartService mService = null;
    private BluetoothDevice mDevice = null;
    private BluetoothAdapter mBtAdapter = null;
    //private ListView messageListView;
    //private ArrayAdapter<String> listAdapter;
    private Button btnConnectDisconnect, btnSend, btnRequestStart, btnRequestStop, btnRequestPause;
    private EditText edtMessage;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_mabiao);
        
        //首先我们需要查找本地活动的蓝牙适配器，获得一个系统默认可用的蓝牙设备
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        // 提示这里会出现没有找到蓝牙硬件或驱动存在的问题
        /*如果没打开蓝牙，会提示蓝牙不可用*/
        if (mBtAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        
      //=====================================================================================UI的初始化（开始）
        //messageListView = (ListView) findViewById(R.id.listMessage);
        //listAdapter = new ArrayAdapter<String>(this, R.layout.message_detail);
        //messageListView.setAdapter(listAdapter);
        //messageListView.setDivider(null);
        btnConnectDisconnect=(Button) findViewById(R.id.btn_select);
//        btnSend= (Button) findViewById(R.id.sendButton);
        //btnRequestStart = (Button) findViewById(R.id.start_requst_Button);
        //btnRequestStop = (Button) findViewById(R.id.stop_requst_Button);
        //btnRequestPause = (Button) findViewById(R.id.pause_requst_Button);
        
        relativeLayout_rise = (RelativeLayout) findViewById(R.id.relativeLayout7);
        relativeLayout_rise_total = (RelativeLayout) findViewById(R.id.relativeLayout4);
        
//        edtMessage = (EditText) findViewById(R.id.sendText);//这里注释了发送输入文本框    	
        //=====================================================================================UI的初始化（结尾）
        
        service_init();//服务初始化
        
        // Handler Disconnect & Connect button
        btnConnectDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBtAdapter.isEnabled()) {//如果没有开启，我们可以通过下面的代码提醒用户启用
                	
                    Log.i(TAG, "onClick - BT not enabled yet");
                    //创建一个Intent对象，用于启动一个activity，来提示用户开启蓝牙  
                    Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableIntent, REQUEST_ENABLE_BT);//开启一个activity提醒有应用要开启蓝牙，这里会有返回
                }
                else {
                	if (btnConnectDisconnect.getText().equals("Connect")){//这里按下connect按钮
                		
                		//Connect button pressed, open DeviceListActivity class, with popup windows that scan for devices
                		//点击connect按钮，弹出DeviceListActivity，选择蓝牙连接后返回BleActivity
            			Intent newIntent = new Intent(BleActivity.this, DeviceListActivity.class);
            			startActivityForResult(newIntent, REQUEST_SELECT_DEVICE);
        			} else {
        				//Disconnect button pressed
        				if (mDevice!=null)
        				{
        					mService.disconnect();
        				}
        			}
                }
            }
        });
        //按下开始按钮，发送数据请求指令给码表
        btnRequestStart.setOnClickListener
        (
        	new View.OnClickListener() 
        	{
				public void onClick(View v) 
				{
					
					btnRequestStart.setVisibility(View.GONE);//点击自己隐藏
					btnRequestPause.setVisibility(View.VISIBLE);//显示
					btnRequestStop.setVisibility(View.VISIBLE);			
					
					byte StartValue[] = new byte[]{0x7C, 0x33, 0x00 ,0x01, 0x01, 0x00, 0x02, 0x00, 0x00, 0x01, 0x00, 0x01};//读取设备数据请求 命令字 02
					mService.writeRXCharacteristic(StartValue);					
				}
        	}
        );
        //按下停止按钮,发送一次数据请求指令
        btnRequestStop.setOnClickListener
        (
    		new View.OnClickListener() 
    		{
        		public void onClick(View v)
        		{
					// TODO Auto-generated method stub
					btnRequestStart.setVisibility(View.VISIBLE);
					btnRequestPause.setVisibility(View.GONE);
					btnRequestStop.setVisibility(View.GONE);
					
					byte StopValue[] = new byte[]{0x7C, 0x33, 0x00 ,0x01, 0x01, 0x00, 0x02, 0x00, 0x00, 0x01, 0x00, 0x01};//读取设备数据请求
					mService.writeRXCharacteristic(StopValue);
			    }
    		}
        );
        //按下暂停按钮，发送一次数据请求指令
        btnRequestPause.setOnClickListener
        (
        		new View.OnClickListener() 
        		{
					public void onClick(View v) 
					{
						if (flag) 
						{
							btnRequestPause.setText("Start");
							flag = false;
						}
						else 
							{
								btnRequestPause.setText("Pause");
								flag = true;								
							}				
						byte PauseValue[] = new byte[]{0x7C, 0x33, 0x00 ,0x01, 0x01, 0x00, 0x02, 0x00, 0x00, 0x01, 0x00, 0x01};//读取设备数据请求
						mService.writeRXCharacteristic(PauseValue);
						Log.i(TAG, "Pause Button is clicked......clicked.........clicked..........clicked");		
					}
        		}
        );
    }
    
	//UART service connected/disconnected----------------串口服务连接和断开
    private ServiceConnection mServiceConnection = new ServiceConnection() {
    	
        public void onServiceConnected(ComponentName className, IBinder rawBinder) {
        		mService = ((UartService.LocalBinder) rawBinder).getService();
        		Log.d(TAG, "onServiceConnected mService= " + mService);
        		if (!mService.initialize()) {
                    Log.e(TAG, "Unable to initialize Bluetooth");
                    finish();
                }
        }

        public void onServiceDisconnected(ComponentName classname) {
       ////     mService.disconnect(mDevice);
        		mService = null;
        }
    };

    private Handler mHandler = new Handler() {
        @Override
        //Handler events that received from UART service
        public void handleMessage(Message msg) {
        }
    };

    //注册一个BroadcastReceiver(广播接收器)对象来接收查找到的蓝牙设备信息
    private final BroadcastReceiver UARTStatusChangeReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            final Intent mIntent = intent;
           //*********************//
            //UartService.ACTION_GATT_CONNECTED---------------显示连接成功
            if (action.equals(UartService.ACTION_GATT_CONNECTED)) {
            	 runOnUiThread(new Runnable() {
                     public void run() {
                         	String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                             Log.d(TAG, "UART_CONNECT_MSG");
                             btnConnectDisconnect.setText("Disconnect");//连接上后按钮Connect变为Disconnect
//                             edtMessage.setEnabled(true);
//                             btnSend.setEnabled(true);
                             
                             //获取每个设备的名称和MAC地址添加到数组适配器myArrayAdapter中
//                             ((TextView) findViewById(R.id.deviceName)).setText(mDevice.getName()+ " - ready");//已经在xml中注释deviceName
                             showMessage(mDevice.getName()+ " - ready");
                             //listAdapter.add("["+currentDateTimeString+"] Connected to: "+ mDevice.getName());//显示连接到指定设备
                        	 //messageListView.smoothScrollToPosition(listAdapter.getCount() - 1);//列表消息会滑动
                             mState = UART_PROFILE_CONNECTED;
                     }
            	 });
            }
           
          //*********************//按Disconnect断开连接
            if (action.equals(UartService.ACTION_GATT_DISCONNECTED)) {
            	 runOnUiThread(new Runnable() {
                     public void run() {
                    	 	 String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                             Log.d(TAG, "UART_DISCONNECT_MSG");
                             btnConnectDisconnect.setText("Connect");
//                             edtMessage.setEnabled(false);//这里要注释，没有注释会停止运行，因为前面已经把edtMessage注释了，这里就不能对edtMessage进行操作
//                             btnSend.setEnabled(false);//理由同上
                             //在左下角显示设备没连接
//                             ((TextView) findViewById(R.id.deviceName)).setText("Not Connected");//已注释deviceName
                             showMessage("Not Connected");//用toast来显示"Not Connected"
                             //在列表中显示出来
                             //listAdapter.add("["+currentDateTimeString+"] Disconnected to: "+ mDevice.getName());
                             
                             mState = UART_PROFILE_DISCONNECTED;
                             mService.close();
                            //setUiState();
                     }
                 });
            }
            
          //*********************//
          //UartService串口服务
            if (action.equals(UartService.ACTION_GATT_SERVICES_DISCOVERED)) {
             	 mService.enableTXNotification(); 
            }
            
          //*********************//接收UartService数据显示在listView上(这里修改成显示在TextView上)
            if (action.equals(UartService.ACTION_DATA_AVAILABLE)) {
            	
                final byte[] rxValue = intent.getByteArrayExtra(UartService.EXTRA_DATA);//接收的数据（字节类型）
//                final int[] rxValue = intent.getIntArrayExtra(UartService.EXTRA_DATA);//接收的数据（字节类型），这个不稳定
                
                //用runOnUiThread更新主线程  
//                runOnUiThread(new Runnable() {//??????????????????????????????????????
//                public void run() {/////////////////

                	//首先找到帧头下标
                	int headnum = 0, i = 0;
                	
                	for(i = 0; i < rxValue.length; i++)
                	{
                		if(rxValue[i] == 0x7C)
                			headnum = i;
                	}
                	
//            		奇校验,这里是校验数据
            		boolean parity = false;  //初始判断标记
                	int sum = 0;
                	
            		int datalen = rxValue[headnum + 8];//get effect data length
            		for (i = headnum; i < headnum + datalen + 12; i++) {//接收数据帧的时候，全部（包括校验位）都要相加和
            			sum += rxValue[i];
            		}
            		
            		//奇偶校验
            		if (sum % 2 == 0){
            			parity = false; //偶数
            		} else {
            			parity = true;	//奇数
            		}
            		
					//已经是奇数，如果valuesum中1的数目为奇数，则parity=true，否则parity=false，表示（偶数）出错
	    				if (parity == false) { 
	//    					Toast.makeText(getApplicationContext(), "数据发送出错，parity == false", Toast.LENGTH_SHORT).show();
	    					Log.w(TAG, "--------------数据校验不是奇数，parity == false----------------");
	    					}
	    				else {
	    						
	    					switch (rxValue[headnum + 6]) {	
	    						case 0x0A:
	    							Log.i(TAG, "Mabiao sending acknowledgement!!!!!!!!!!!!!!!!!!0x0A");//发送应答
	    							break;
	    						case 0x0B:
	    							//这个用get。。。。。的到输入的信息
	    							Log.i(TAG, "Mabiao request to send device parameter!!!!!!!!!!!!!!!!!0x0B");//发送设备参数
	    							break;
	    						case 0x0C:
	    							Log.i(TAG, "Mabiao request to send device status!!!!!!!!!!!!!!!!!!!0x0C");//发送设备状态
	    							break;
								case 0x0D:
									Log.i(TAG, "Mabiao request to send device location information!!!!!!!!!0x0D");//发送位置信息
									break;
								case 0x0E:
									Log.i(TAG, "Mabiao request to send Track statistics!!!!!!!!!!!!!0x0E");//发送轨迹统计
									break;
								case 0x0F:
									Log.i(TAG, "Mabiao request Storage path to upload!!!!!!!!!!!!!!!0x0F");//存储轨迹上传
									break;
								case 0x10:
									Log.i(TAG, "Mabiao Data reception is completed!!!!!!!!!!!!!!!!!!0x10");//数据接收完成状态
									break;
								case 0x11:
									Log.i(TAG, "Mabiao request Start timming!!!!!!!!!!!!!!!!!!0x11");//开始计时
									break;
								case 0x12:
									Log.i(TAG, "Mabiao request Pause timming!!!!!!!!!!!!!!!!!!0x12");//暂停计时
									break;
								case 0x13:
									Log.i(TAG, "Mabiao request Stop timming!!!!!!!!!!!!!!!!!!0x13");//停止计时
									break;
								case 0x14:
									Log.i(TAG, "show current_speed!!!!!!!!!!!!!!!!!!20");//当前速度
									try {
	//									String text = new String(temp, "UTF-8");//将字节型数据转成字符串类型
	//									for (int i = 9 + headnum, j = 0; i < rxValue[8] + headnum + 9; j++, i++) {
	//										byte[] temp = null;
	//										temp[j] = rxValue[i];
	//									}
										current_speed_textview1.setText(""+(rxValue[headnum + 9]-0x30));
										current_speed_textview2.setText(""+(rxValue[headnum + 9 + 1]-0x30));
										current_speed_textview3.setText(""+(rxValue[headnum + 9 + 2]-0x30));
										current_speed_textview4.setText(""+(rxValue[headnum + 9 + 3]-0x30));
	//									current_speed_textview5.setText(""+(rxValue[headnum + 9 + 4]-0x30));//加多了-0x30，这里是小数点
										current_speed_textview6.setText(""+(rxValue[headnum + 9 + 5]-0x30));
										Log.i(TAG, "I have received  current_speed!!!!!!!!!!!!!!!!!!!!!!!20");
									} catch (Exception e) {
										Log.e(TAG, e.toString());
										Log.i(TAG, "get speed error!");
									}
									break;
								case 0x15:
									Log.i(TAG, "show average_speed!!!!!!!!!!!!!!!!!!21");//平均速度
									try {
										average_speed1.setText(""+(rxValue[headnum + 9]-0x30));
										average_speed2.setText(""+(rxValue[headnum + 9 + 1]-0x30));
										average_speed3.setText(""+(rxValue[headnum + 9 + 2]-0x30));
										average_speed4.setText(""+(rxValue[headnum + 9 + 3]-0x30));
										average_speed5.setText(""+(rxValue[headnum + 9 + 4]-0x30));
										average_speed6.setText(""+(rxValue[headnum + 9 + 5]-0x30));
										Log.i(TAG, "I have received  average_speed!!!!!!!!!!!!!!!!!!!!!!!21");
									} catch (Exception e) {
										Log.e(TAG, e.toString());
									}
									break;
								case 0x16:
									Log.i(TAG, "show elevation!!!!!!!!!!!!!!!!!!22");//海拔
									try {
										elevation1.setText(""+(rxValue[headnum + 9]-0x30));
										elevation2.setText(""+(rxValue[headnum + 9 + 1]-0x30));
										elevation3.setText(""+(rxValue[headnum + 9 + 2]-0x30));
										elevation4.setText(""+(rxValue[headnum + 9 + 3]-0x30));
										elevation5.setText(""+(rxValue[headnum + 9 + 4]-0x30));
										elevation6.setText(""+(rxValue[headnum + 9 + 5]-0x30));
										elevation7.setText(""+(rxValue[headnum + 9 + 6]-0x30));
										Log.i(TAG, "I have received  elevation!!!!!!!!!!!!!!!!!!!!!!!22");
									} catch (Exception e) {
										Log.e(TAG, e.toString());
									}
									break;
								case 0x17:
									Log.i(TAG, "show max_speed!!!!!!!!!!!!!!!!!!23");//最大速度
									try {
										max_speed1.setText(""+(rxValue[headnum + 9]-0x30));
										max_speed2.setText(""+(rxValue[headnum + 9 + 1]-0x30));
										max_speed3.setText(""+(rxValue[headnum + 9 + 2]-0x30));
										max_speed4.setText(""+(rxValue[headnum + 9 + 3]-0x30));
										max_speed5.setText(""+(rxValue[headnum + 9 + 4]-0x30));
										max_speed6.setText(""+(rxValue[headnum + 9 + 5]-0x30));
										Log.i(TAG, "I have received  max_speed!!!!!!!!!!!!!!!!!!!!!!!23");
									} catch (Exception e) {
										Log.e(TAG, e.toString());
									}
									break;
								case 0x18:
									Log.i(TAG, " !!!!!!!!!!!!!!!!!!24");//
									break;
								case 0x19:
									Log.i(TAG, " !!!!!!!!!!!!!!!!!!25");//
									break;
								case 0x1A:
									Log.i(TAG, " !!!!!!!!!!!!!!!!!!26");//
									break;
								case 0x1B:
									Log.i(TAG, " !!!!!!!!!!!!!!!!!!27");//
									break;
								case 0x1C:
									Log.i(TAG, " !!!!!!!!!!!!!!!!!!28");//
									break;
								case 0x1D:
									Log.i(TAG, " !!!!!!!!!!!!!!!!!!29");//
									break;
								case 0x1E:
									Log.i(TAG, " !!!!!!!!!!!!!!!!!!30");//
									break;
								case 0x1F:
									Log.i(TAG, "show grade!!!!!!!!!!!!!!!!!!31");//坡度
									try {
										grade1.setText(""+(rxValue[headnum + 9]-0x30));
										grade2.setText(""+(rxValue[headnum + 9 + 1]-0x30));
										grade3.setText(""+(rxValue[headnum + 9 + 2]-0x30));
										Log.i(TAG, "I have received  grade!!!!!!!!!!!!!!!!!!!!!!!32");
									} catch (Exception e) {
										// TODO: handle exception
										Log.e(TAG, e.toString());
									}
									
									break;
								case 0x20:
									Log.i(TAG, "show average_peispeed!!!!!!!!!!!!!!!!!!32");//平均配速
									try {
										average_peispeed1.setText(""+(rxValue[headnum + 9]-0x30));
										average_peispeed2.setText(""+(rxValue[headnum + 9 + 1]-0x30));
										average_peispeed3.setText(""+(rxValue[headnum + 9 + 2]-0x30));
										average_peispeed4.setText(""+(rxValue[headnum + 9 + 3]-0x30));
										average_peispeed5.setText(""+(rxValue[headnum + 9 + 4]-0x30));
										average_peispeed6.setText(""+(rxValue[headnum + 9 + 5]-0x30));
										Log.i(TAG, "I have received  average_peispeed!!!!!!!!!!!!!!!!!!!!!!!32");
									} catch (Exception e) {
										Log.e(TAG, e.toString());
									}
									break;
								case 0x21:
									Log.i(TAG, " !!!!!!!!!!!!!!!!!!33");//
									break;
								case 0x22:
									Log.i(TAG, " !!!!!!!!!!!!!!!!!!34");//
									break;
								case 0x23:
									Log.i(TAG, "show mileage!!!!!!!!!!!!!!!!!!35");//里程
									try {
										mileage1.setText(""+(rxValue[headnum + 9]-0x30));
										mileage2.setText(""+(rxValue[headnum + 9 + 1]-0x30));
										mileage3.setText(""+(rxValue[headnum + 9 + 2]-0x30));
										mileage4.setText(""+(rxValue[headnum + 9 + 3]-0x30));
										mileage5.setText(""+(rxValue[headnum + 9 + 4]-0x30));
										mileage6.setText(""+(rxValue[headnum + 9 + 5]-0x30));
										Log.i(TAG, "I have received  mileage!!!!!!!!!!!!!!!!!!!!!!!37");
									} catch (Exception e) {
										Log.e(TAG, e.toString());
									}
									break;
								case 0x24:
									Log.i(TAG, "show use_time!!!!!!!!!!!!!!!!!!36");//用时
									try {
										use_time1.setText(""+(rxValue[headnum + 9]-0x30));
										use_time2.setText(""+(rxValue[headnum + 9 + 1]-0x30));
										use_time3.setText(""+(rxValue[headnum + 9 + 2]-0x30));
										use_time4.setText(""+(rxValue[headnum + 9 + 3]-0x30));
										use_time5.setText(""+(rxValue[headnum + 9 + 4]-0x30));
										Log.i(TAG, "I have received  use_time!!!!!!!!!!!!!!!!!!!!!!!37");
									} catch (Exception e) {
										Log.e(TAG, e.toString());
									}
									
									break;
								case 0x25:
									Log.i(TAG, "show rise!!!!!!!!!!!!!!!!!!37");//上升
									try {
										rise1.setText(""+(rxValue[headnum + 9]-0x30));
										rise2.setText(""+(rxValue[headnum + 9 + 1]-0x30));
										rise3.setText(""+(rxValue[headnum + 9 + 2]-0x30));
										rise4.setText(""+(rxValue[headnum + 9 + 3]-0x30));
										rise5.setText(""+(rxValue[headnum + 9 + 4]-0x30));
										rise6.setText(""+(rxValue[headnum + 9 + 5]-0x30));
										rise7.setText(""+(rxValue[headnum + 9 + 6]-0x30));
										Log.i(TAG, "I have received  rise!!!!!!!!!!!!!!!!!!!!!!!37");
									} catch (Exception e) {
										Log.e(TAG, e.toString());
									}
									break;
								case 0x26:
									Log.i(TAG, "show fall!!!!!!!!!!!!!!!!!!38");//下降
									try {
										fall1.setText(""+(rxValue[headnum + 9]-0x30));
										fall2.setText(""+(rxValue[headnum + 9 + 1]-0x30));
										fall3.setText(""+(rxValue[headnum + 9 + 2]-0x30));
										fall4.setText(""+(rxValue[headnum + 9 + 3]-0x30));
										fall5.setText(""+(rxValue[headnum + 9 + 4]-0x30));
										fall6.setText(""+(rxValue[headnum + 9 + 5]-0x30));
										fall7.setText(""+(rxValue[headnum + 9 + 6]-0x30));
										Log.i(TAG, "I have received  fall!!!!!!!!!!!!!!!!!!!!!!!38");
									} catch (Exception e) {
										Log.e(TAG, e.toString());
									}
									break;
									
								default:
									break;
								}//switch-case
 					
	    						
	    					}//if~else
                	
					
//	        				这里用来测试回传超过20个字节的时候用的代码（之前是什么数据就回传什么数据，现在校验成功的数据才回传）				
        				callback_send_message(rxValue);	   

//                }//run()

//                });//runOnUiThread()
            }//if(){}
            
            //********************************//
            if (action.equals(UartService.DEVICE_DOES_NOT_SUPPORT_UART)){
            	showMessage("Device doesn't support UART. Disconnecting");
            	mService.disconnect();
            }
        }//onReceive()
    };//new BroadcastReceiver()对象来接收查找到的蓝牙设备信息类到此完成
    

    private void service_init() {
        Intent bindIntent = new Intent(this, UartService.class);
        bindService(bindIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
  
        LocalBroadcastManager.getInstance(this).registerReceiver(UARTStatusChangeReceiver, makeGattUpdateIntentFilter());
    }
    


	//创建intentfilter及广播接收函数
    private static IntentFilter makeGattUpdateIntentFilter() {
    	
        final IntentFilter intentFilter = new IntentFilter();// 注册这个 BroadcastReceiver 
        
        intentFilter.addAction(UartService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(UartService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(UartService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(UartService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(UartService.DEVICE_DOES_NOT_SUPPORT_UART);
        return intentFilter;
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
    	 super.onDestroy();
        Log.d(TAG, "onDestroy()");
        
        try {
        	//使用unregisterReceiver方法反注册这个BroadcastReceiver对象保证资源被正确回收。
        	LocalBroadcastManager.getInstance(this).unregisterReceiver(UARTStatusChangeReceiver);
        } catch (Exception ignore) {
            Log.e(TAG, ignore.toString());
        } 
        unbindService(mServiceConnection);
        mService.stopSelf();
        mService= null;       
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        if (!mBtAdapter.isEnabled()) {
            Log.i(TAG, "onResume - BT not enabled yet");
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
        switch (requestCode) {

        case REQUEST_SELECT_DEVICE:
        	//When the DeviceListActivity return, with the selected device address
            if (resultCode == Activity.RESULT_OK && data != null) {
                String deviceAddress = data.getStringExtra(BluetoothDevice.EXTRA_DEVICE);
                mDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(deviceAddress);
               
                Log.d(TAG, "... onActivityResultdevice.address==" + mDevice + "mserviceValue" + mService);
//                ((TextView) findViewById(R.id.deviceName)).setText(mDevice.getName()+ " - connecting");//这个是显示在textview中的文字
//                Toast.makeText(getApplicationContext(), mDevice.getName()+ " - connecting", Toast.LENGTH_SHORT).show();
//                showMessage(mDevice.getName()+ " - connecting");//或者直接用这个方法实现
                mService.connect(deviceAddress);                           
            }
            break;
        case REQUEST_ENABLE_BT:
            // When the request to enable Bluetooth returns
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Bluetooth has turned on ", Toast.LENGTH_SHORT).show();

            } else {
                // User did not enable Bluetooth or an error occurred
                Log.d(TAG, "BT not enabled");
                Toast.makeText(this, "Problem in BT Turning ON ", Toast.LENGTH_SHORT).show();
                finish();
            }
            break;
        default:
            Log.e(TAG, "wrong request code");
            break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
       
    }

    
    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    
    
//		这里用来测试回传超过20个字节的时候用的代码
    public void callback_send_message(byte[] rxValue) {

		Log.i(TAG, "I have received data 准备回传数据!!!!!!!!!!!!!!!!!!!!!!!");
		int length = rxValue.length;
		byte[] temp1 = new byte[20];
		byte[] temp2 = new byte[20];
		
		if (length > 20) {
			
			for (int i = 0; i < 20; i++) {
				temp1[i]=rxValue[i];
			}
			mService.writeRXCharacteristic(temp1);//调用mService对象中的发送方法writeRXCharacteristic来发送数据
			
			for (int i = 20, j = 0; i < length; i++, j++) {
				temp2[j]=rxValue[i];
			}
			mService.writeRXCharacteristic(temp2);	
			
			Log.i(TAG, "I have send send send send send back back back back back !!!!!!!!!!!回传发送成功!!!!!!!!!!!!");
//			Toast.makeText(getApplicationContext(), "回传发送成功", 1).show();
		}else {
			//mService.writeRXCharacteristic(rxValue);
			//Log.i(TAG, "I have send send send send send back back back back back !!!!!!!!!!!!回传发送成功!!!!!!!!!!!");
//			Toast.makeText(getApplicationContext(), "回传发送成功", 1).show();
		}  
    	
	}

    
    //这里注释退出提示的功能
/*    @Override
    public void onBackPressed() {
        if (mState == UART_PROFILE_CONNECTED) {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
            showMessage("nRFUART's running in background.\n             Disconnect to exit");
        }
        else {
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
    }*/
}
