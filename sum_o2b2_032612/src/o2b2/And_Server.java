package o2b2;

class And_Server extends Thread {
	public void run() {
		super.run();
		try {
			And_DBManager mDB = And_DBManager.getInstance();
			mDB.DB_Join();
			System.out.println("회원 수 : " + mDB.DB_Select_countNum("select count(*) From profile"));
			mDB.DB_RealStudyTime_countNum("select count(*) From realstudytime");
			mDB.DB_Select_value("select * From profile");
			
			And_SocketThread mSocketThread = And_SocketThread.get();
			mSocketThread.join();
			
			while (true) {
				System.out.println("----사용자가 어떤 버튼을 누르는지 대기중----");
				String readData = null;

				while (true) {
					try {
						readData = mSocketThread.readData();

						if (readData != null) { // 값을받아 null이 아니면 break;
							break;
						}
						Thread.sleep(100);
					} catch (Exception e) {

					}
				}
				if (readData.equals("1")) { // 로그인 버튼"1"
					And_Login mlogin = And_Login.getInstance();
					mlogin.login_start();
				} else if (readData.contentEquals("2")) { // 회원가입 버튼"2"
//					And_SingUp mSingup = And_SingUp.getInstacne();
					System.out.println("안드로이드에서 받은 버튼 판별 값 : " + readData);
		            And_SingUp mSingup = And_SingUp.getInstance();
		            mSingup.singup_start();
				}

			}

		} catch (Exception e) {

		}
	}
}