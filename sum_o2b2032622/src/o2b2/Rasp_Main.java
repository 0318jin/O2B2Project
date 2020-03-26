package o2b2;

import java.text.SimpleDateFormat;
import java.util.Calendar;

class Rasp_Main extends Thread {

	public void run() {
		
////	현재 날짜를 가져오는 부분
	SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
	
	Calendar time = Calendar.getInstance();
		
		super.run();
		try {
			while (true) {
				Rasp_Socket_Thread mRasp_Socket_Thread = Rasp_Socket_Thread.get();
				mRasp_Socket_Thread.Join();

				// 값을 읽는 부분 초기화 해 주어야 한다.
				String readData = null;

				while (true) { // 클라이언트에서 값을 받는 while문
					try {
						readData = mRasp_Socket_Thread.readData();

						if (readData != null) {
							System.out.println("readData 값을받아 while 문 빠져나옴");
							break;
						}
						Thread.sleep(100);
					} catch (Exception e) {
					}
				}

				//싱글톤 추가부분================================
				SingleTon s = SingleTon.getInstanse();
				s.readData_singleTon = readData;
				
//				Frame mFrame = new Frame();
//				mFrame.txta1.append(s.readData_singleTon);
			
				System.out.println("클라이언트에서 받아온 값 : " + readData);
				System.out.println("싱글톤 테스트 값 : " + s.readData_singleTon);
				String sendmsg = readData;
				//라즈베리파이에서 들어온 값을 데이터베이스에 저장하는 부분
				String[] array = s.readData_singleTon.split("/");
				String serialnum = array[0];
				System.out.println(serialnum);
				String studytime = array[1];
				System.out.println(studytime);
				String date = format1.format(time.getTime());
				System.out.println(date);
				String subject = "0";
				Insert_RealStudyTime.insert(serialnum, studytime, date, subject);
				
				
				// 클라이언트에 값을 보내는 구분
				mRasp_Socket_Thread.write("send : " + sendmsg);
				
				
//				SocketThread mSocketThread = SocketThread.get();
//				mSocketThread.write("Rasp to Server: " +readData);
			}

		} catch (Exception e) {

		}

	}
}
