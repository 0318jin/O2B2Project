package o2b2;

import java.net.*;
import java.io.*;
import java.sql.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ExecutionException;

public class And_addfr {
	static private And_addfr mAddfr = null;
	ArrayList<String> Arr_Addfr = new ArrayList<String>();
	String sendTime = null;


	private And_addfr() {

	}

	public static And_addfr getInstance() {
		if (mAddfr == null)
			mAddfr = new And_addfr();
		return mAddfr;
	}

	void addfr_start() {
		try {
			
			And_DBManager mDB = And_DBManager.getInstance();
			And_SocketThread mSocketThread = And_SocketThread.get();
			mDB.DB_Select_value2("select * From RealStudyTime");
			
			System.out.println("친구추가 시작!");
			mSocketThread.write("add fr button pushed");

			String readData = null;

			while (true) {
				try {
					readData = mSocketThread.readData();
		
					if (readData != null) { // 값을받아 null이 아니면 break;
						Arr_Addfr.add(readData);
						break;
					}
					Thread.sleep(100);
				} catch (Exception e) {

				}
			}
			//mSocketThread.write("get fr PhoneNum");   
			System.out.println("받은  친구 핸드폰번호 : " + readData);
			
			if (mDB.DB_find_SN(readData)) {
				sendTime= null;
				sendTime= Integer.toString(mDB.DB_frtime());
				
				System.out.println("realtime : "+sendTime);
				mSocketThread.write(sendTime); //친구목록에 있으면 1값 App에 전송
				
			}
			else mSocketThread.write("-1");

			
			Thread.sleep(200);
			System.out.println("친구추가 완료");
			for (int i = 0; i < Arr_Addfr.size(); i++) {
				System.out.println("Android Client 에서 받은 값 : " + Arr_Addfr.get(i));
				And_DB_addfr.insert(Arr_Addfr.get(i));;
				
				
				//System.out.println("이게되야된다"+readData);
			}
			
			
	
		} catch (Exception e) {
		}

	}
}