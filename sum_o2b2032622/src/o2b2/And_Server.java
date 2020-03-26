package o2b2;


import java.net.Socket;

class And_Server extends Thread {
	public void run() {
		super.run();
		try { 
			And_DBManager mDB = And_DBManager.getInstance();
			And_SocketThread mSocketThread = And_SocketThread.get();
			mDB.DB_Join(); // 데이터 베이스 연결
			mSocketThread.join(); // Server Socket 열고 접속까지 대기중...
			
			String readData = null;
			
			while (true) {
				// 데이터베이스 회원수 읽기
				System.out.println("회원 수 : " + mDB.DB_Select_countNum("select count(*) From profile"));
				mDB.DB_Select_value("select * From profile"); // 회원의 아이디와 비밀번호를 ArrayList에 각각 넣어 저장.
				
				System.out.println("----사용자가 어떤 버튼을 누르는지 대기중----");
				
				boolean result = mSocketThread.sock.isConnected();
				
				
				readData = null;

				while (true) {
					try {				
						readData = mSocketThread.readData();
						
						if (readData != null) { // 값을받아 null이 아니면 break;
							System.out.println("Client로 부터 받은 판별값  : " + readData);
							break;
							
							
						}
						Thread.sleep(100);
					} catch (Exception e) {

					}
				}

				System.out.println("버튼 값 받아서 반복문 빠져 나옴!");

				if (readData.equals("1")) { // 로그인 버튼"1"
					System.out.println("로그인 조건문");
					And_Login mlogin = And_Login.getInstance();
					mlogin.login_start();

				} else if (readData.equals("2")) { // 회원가입 버튼"2"
					System.out.println("회원가입 조건문");
					//=========================================================================
					And_SingUp mSingup = And_SingUp.getInstance();
					mSingup.singup_start();

				} else if (readData.equals("3")) { // 스케줄 저장 버튼 "3"
					System.out.println("스케줄 저장 조건문");
					And_Schedule mSchedule = And_Schedule.getInstance();
					mSchedule.schedule_start();
					
				} else if (readData.equals("6")) { // 친구 추가 버튼"6"
					System.out.println("안드로이드에서 받은 버튼 판별 값 : " + readData);
					And_addfr maddfr = And_addfr.getInstance();
					maddfr.addfr_start();
				}else if(readData.equals("7")) {
					System.out.println("서버 연결이 끊겼습니다.");
					System.out.println("사용중이던 소켓을 닫고 새로 여는 중입니다.");
					
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