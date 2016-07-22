package com.nordicsemi.Myfiles;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.content.Context;

public class FileService {

	private Context context;
	public FileService(Context context) {
		this.context = context;
	}

/**	@从APP写数据出文件中
	@param	accountString
	@param	accountString
 	@throws Exception
	**/
	public void save(String accountString, String passwdString) throws Exception {
		// TODO Auto-generated method stub
		//这里会发生例外，然后用throws Exception抛出到前面的Activity中去实现
		FileOutputStream outStreamaccount = context.openFileOutput("account.txt", context.MODE_PRIVATE); 
		FileOutputStream outStreampasswd = context.openFileOutput("passwd.txt", context.MODE_PRIVATE);
		outStreamaccount.write(accountString.getBytes());
		outStreampasswd.write(passwdString.getBytes());
		outStreamaccount.close();
		outStreampasswd.close();
	}
	
/**	//从文件中读数据进app
 * @param filename文件名
 * @return 文件内容
 * @throws Exception
**/	
	public String read(String filename) throws Exception{
		
		FileInputStream inStream = context.openFileInput(filename);
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		
		byte[] buffer = new byte[1024];
		int len = 0;
		while((len = inStream.read(buffer)) != -1){//数据读出来存到数组buffer中
			outStream.write(buffer, 0, len);//再讲数组buffer中的数据写到内存
		}//通过这个循环，把文件中的数据读到内存中去
		
		//最后将内存的数据保存到数组data中，这里得到的数据相当于前面保存时getBytes()方法得到的数据
		byte[] data = outStream.toByteArray();
		return new String(data);//string是转换成系统默认编码的字符串

	} 
	

}
