package o2b2;


import java.net.Socket;

class And_Server extends Thread {
	public void run() {
		super.run();
		try { 
			And_DBManager mDB = And_DBManager.getInstance();
			And_SocketThread mSocketThread = And_SocketThread.get();
			mDB.DB_Join(); // ������ ���̽� ����
			mSocketThread.join(); // Server Socket ���� ���ӱ��� �����...
			
			String readData = null;
			
			while (true) {
				// �����ͺ��̽� ȸ���� �б�
				System.out.println("ȸ�� �� : " + mDB.DB_Select_countNum("select count(*) From profile"));
				mDB.DB_Select_value("select * From profile"); // ȸ���� ���̵�� ��й�ȣ�� ArrayList�� ���� �־� ����.
				
				System.out.println("----����ڰ� � ��ư�� �������� �����----");
				
				boolean result = mSocketThread.sock.isConnected();
				
				
				readData = null;

				while (true) {
					try {				
						readData = mSocketThread.readData();
						
						if (readData != null) { // �����޾� null�� �ƴϸ� break;
							System.out.println("Client�� ���� ���� �Ǻ���  : " + readData);
							break;
							
							
						}
						Thread.sleep(100);
					} catch (Exception e) {

					}
				}

				System.out.println("��ư �� �޾Ƽ� �ݺ��� ���� ����!");

				if (readData.equals("1")) { // �α��� ��ư"1"
					System.out.println("�α��� ���ǹ�");
					And_Login mlogin = And_Login.getInstance();
					mlogin.login_start();

				} else if (readData.equals("2")) { // ȸ������ ��ư"2"
					System.out.println("ȸ������ ���ǹ�");
					//=========================================================================
					And_SingUp mSingup = And_SingUp.getInstance();
					mSingup.singup_start();

				} else if (readData.equals("3")) { // ������ ���� ��ư "3"
					System.out.println("������ ���� ���ǹ�");
					And_Schedule mSchedule = And_Schedule.getInstance();
					mSchedule.schedule_start();
					
				} else if (readData.equals("6")) { // ģ�� �߰� ��ư"6"
					System.out.println("�ȵ���̵忡�� ���� ��ư �Ǻ� �� : " + readData);
					And_addfr maddfr = And_addfr.getInstance();
					maddfr.addfr_start();
				}else if(readData.equals("7")) {
					System.out.println("���� ������ ������ϴ�.");
					System.out.println("������̴� ������ �ݰ� ���� ���� ���Դϴ�.");
					
					mSocketThread.sock.close();
					mSocketThread.server.close();
					mSocketThread.join();
					
				}
				
				readData = null;

				try {
					Thread.sleep(100);
				} catch (Exception e) {

				}

			}

		} catch (Exception e) {

		}
	}
}