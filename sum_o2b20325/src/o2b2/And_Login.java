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
						break;
					} else if (readData.equals("")) { // PW가 공백이면 공백 값 App client 에 전송
						mSocketThread.write("");
					} else {
						mSocketThread.write("0"); // PW가 맞지않으면 0 값 App client 에 전송
					}

				} else if (readData.equals("")) { // ID가 공백이면 공백 값 App client 에 전송
					mSocketThread.write("");
				} else {
					mSocketThread.write("0"); // ID가 맞지않으면 0 값 App client 에 전송    
				}

				Thread.sleep(200);
			}
			System.out.println("로그인 완료");
		} catch (Exception e) {

		}
	}
}


