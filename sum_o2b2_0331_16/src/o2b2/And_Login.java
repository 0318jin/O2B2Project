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

public class And_Login {
	static private And_Login mLogin = null;
	
	Integer S_Count = null;
	Integer R_Count = null;
	
	String Serial = null;
	
	private And_Login() {
	}

	public static And_Login getInstance() {
		if (mLogin == null)
			mLogin = new And_Login();
		return mLogin;
	}

	void login_start() {					
		try {
	
			And_DBManager mDB = And_DBManager.getInstance();
			And_SocketThread mSocketThread = And_SocketThread.get();
			mSocketThread.write("Login button pushed.");

			while (true) {
				/*-------------------------ID--------------------------------*/

				System.out.println("----아이디, 비밀번호 입력을 기다리는 중----");
				String readData = null;

				while (true) { // ID 입력을 받을때까지 기다림.
					try {
						readData = mSocketThread.readData();          

						if (readData != null) { // 값을받아 null이 아니면 break;
							break;
						}
						Thread.sleep(100);
					} catch (Exception e) {

					}
				}

				System.out.println("----아이디, 비밀번호 입력을 받아 빠져나옴----");
				System.out.println("받은 ID 값 : " + readData);
				
				if (mDB.ID_Match(readData)) {
					String DPH = readData;
					mSocketThread.write("1"); // 맞으면 1 값 App client 에 전송

					/*-------------------------PW--------------------------------*/
					readData = null;
					while (true) {
						try {
							readData = mSocketThread.readData();

							if (readData != null) {
								break;
							}
							Thread.sleep(100);
						} catch (Exception e) {

						}
					}
					System.out.println("받은 PW 값 : " + readData);
					if (mDB.PW_Match(readData)) {
						
						mSocketThread.write("1");

						And_Search_Profile mASP = And_Search_Profile.getInstace();
						mASP.Join_Detect();
						mASP.DB_Select_value();
						System.out.println(mASP.Detect_SRN(DPH)); // 시리얼번호 
						Serial = mASP.Detect_SRN(DPH); // 시리얼 번호 저장.
						System.out.println("로그인한 회원의 시리얼 넘버 : " + Serial);
						
						And_DB_Chart mADC = And_DB_Chart.getInstance(); // 로그인한 회원의 실제학습 및 스케줄 열 개수
						S_Count = mADC.Schedule_Count(Serial);
						R_Count = mADC.RealStudy_Count(Serial);
						
						///////////////////////////////////스케줄 데이터값 요청받고보내기
						readData = null;
						while (true) {
							try {
								readData = mSocketThread.readData();

								if (readData != null) {
									break;
								}
								Thread.sleep(100);
							} catch (Exception e) {

							}
						}
						System.out.println(readData);
						String sd_Schedul = mADC.Schedule_data();
						mSocketThread.write(sd_Schedul);
						
						
						///////////////////////////////////실제학습시간 데이터값 요청받고보내기
						readData = null;
						while (true) {
							try {
								readData = mSocketThread.readData();

								if (readData != null) {
									break;
								}
								Thread.sleep(100);
							} catch (Exception e) {

							}
						}
						System.out.println(readData);
						
						String sd_realstudy = mADC.Real_data();
						mSocketThread.write(sd_realstudy);
						
						
						/*
						 받고   클라이언트 요청
						 보내고 스케줄 스트링
						 받고   클라이언트 요청
						 보내고 실제학습시간 스트링
						 */
						
//						System.out.println("받은 값 : " + S_Count);  // 저장 됬나 확인.
//						System.out.println("받은 값 : " + R_Count);

						
						
						break;
					} else if (readData.equals("")) { // PW가 공백이면 공백 값 App client 에 전송
						mSocketThread.write("");
						break;
					} else {
						mSocketThread.write("0");
						break;// PW가 맞지않으면 0 값 App client 에 전송
					}

				} else if (readData.equals("")) { // ID가 공백이면 공백 값 App client 에 전송
					mSocketThread.write("");
					break;
				} else {
					mSocketThread.write("0"); // ID가 맞지않으면 0 값 App client 에 전송    
					break;
				}
			}
			System.out.println("로그인 함수 빠져나옴.");
			
		} catch (Exception e) {

		}
	}
}


