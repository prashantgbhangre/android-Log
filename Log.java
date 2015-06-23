/*
 * 
 * Log File Call from on Create
 * for file create
 * 
 * Log.createFile(getApplicationContext(),"Folder Name","file name");
 * 
 * for write log
 * 
 * Log.write("Tag", "Log Text");
 * 
 * */

package com.atos.ngbank.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

public class Log {

	public static boolean createFile(Context mContext , String FolderPath , String filename) {
		if (checkIsAvailableForReadAndWrite()) {
			if (checkIsPathExist(FolderPath,filename)) {
				// Path Available
				Toast.makeText(mContext, "Path Exists", Toast.LENGTH_SHORT)
						.show();
				return true;
			} else {
				Toast.makeText(mContext, "sorry !!! path is not created !!!",
						Toast.LENGTH_SHORT).show();
				return false;
			}
		} else {
			Toast.makeText(mContext,
					"Sorry !!! Storage is not available for loging",
					Toast.LENGTH_SHORT).show();
			return false;
		}
	}

	private static boolean checkIsPathExist(String folderName ,String fileName ) {
		if (checkFolder("/"+folderName)) {
			if (checkFile("/"+folderName+"/"+fileName)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private static boolean checkFile(String Data) {
		File myFile = new File(Environment.getExternalStorageDirectory(), Data);
		if (!(myFile.exists())) {
			try {
				myFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			return true;
		}
		else
		{
			myFile.delete();
			checkFile("/NGBANK/log.txt");
		}
		return true;
	}

	private static boolean checkFolder(String Data) {
		File myFolder = new File(Environment.getExternalStorageDirectory(),
				Data);
		if (!(myFolder.exists()) || !(myFolder.isDirectory())) {
			myFolder.mkdir();
			return true;
		}
		return true;
	}

	public static boolean checkIsAvailableForReadAndWrite() {
		String State = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(State)) {
			return true;
		}
		return false;
	}

	public static void write(String tag,String data)
	{
		try {
			DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
			String date = df.format(Calendar.getInstance().getTime());
			FileWriter fWrite = new FileWriter(Environment.getExternalStorageDirectory()+"/NGBANK/log.txt",true);
			fWrite.write(date+"   "+tag+"   "+data+"\n");
			fWrite.flush();
			fWrite.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
