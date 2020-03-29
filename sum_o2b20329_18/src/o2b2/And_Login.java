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

				System.out.println("----���̵�, ��й�ȣ �Է��� ��ٸ��� ��----");
				String readData = null;

				while (true) { // ID �Է��� ���������� ��ٸ�.
					try {
						readData = mSocketThread.readData();          

						if (readData != null) { // �����޾� null�� �ƴϸ� break;
							break;
						}
						Thread.sleep(100);
					} catch (Exception e) {

					}
				}

				System.out.println("----���̵�, ��й�ȣ �Է��� �޾� ��������----");
				System.out.println("���� ID �� : " + readData);
				
				if (mDB.ID_Match(readData)) {
					String DPH = readData;
					mSocketThread.write("1"); // ������ 1 �� App client �� ����

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
					System.out.println("���� PW �� : " + readData);
					if (mDB.PW_Match(readData)) {
						
						mSocketThread.write("1");

						And_Search_Profile mASP = And_Search_Profile.getInstace();
						mASP.Join_Detect();
						mASP.DB_Select_value();
						System.out.println(mASP.Detect_SRN(DPH)); // �ø����ȣ 
						Serial = mASP.Detect_SRN(DPH); // �ø��� ��ȣ ����.
						System.out.println("�α����� ȸ���� �ø��� �ѹ� : " + Serial);
						
						And_DB_Chart mADC = And_DB_Chart.getInstance(); // �α����� ȸ���� �����н� �� ������ �� ����
						S_Count = mADC.Schedule_Count(Serial);
						R_Count = mADC.RealStudy_Count(Serial);
						
						///////////////////////////////////������ �����Ͱ� ��û�ް�����
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
						
						
						///////////////////////////////////�����н��ð� �����Ͱ� ��û�ް�����
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
						 �ް�   Ŭ���̾�Ʈ ��û
						 ������ ������ ��Ʈ��
						 �ް�   Ŭ���̾�Ʈ ��û
						 ������ �����н��ð� ��Ʈ��
						 */
						
//						System.out.println("���� �� : " + S_Count);  // ���� �糪 Ȯ��.
//						System.out.println("���� �� : " + R_Count);

						
						
						break;
					} else if (readData.equals("")) { // PW�� �����̸� ���� �� App client �� ����
						mSocketThread.write("");
						break;
					} else {
						mSocketThread.write("0");
						break;// PW�� ���������� 0 �� App client �� ����
					}

				} else if (readData.equals("")) { // ID�� �����̸� ���� �� App client �� ����
					mSocketThread.write("");
					break;
				} else {
					mSocketThread.write("0"); // ID�� ���������� 0 �� App client �� ����    
					break;
				}
			}
			System.out.println("�α��� �Լ� ��������.");
			
		} catch (Exception e) {

		}
	}
}


