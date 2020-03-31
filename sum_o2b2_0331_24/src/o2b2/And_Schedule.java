package o2b2;
import java.security.spec.PSSParameterSpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class And_Schedule {
	static private And_Schedule mSchedule = null;
	String Schedule_data = null;
	ArrayList<String> Arr_Schedule = new ArrayList<String>();

	// 중복된 날짜에 스케줄 학습을 추가하는 경우 그 중복을 처리하기위한 Arr_List
	// 스케줄에 저장되어 있는모든 회원의 시리얼 넘버.
	ArrayList<String> Arr_Serialnum = new ArrayList<String>();
	// 스케줄에 저장되어 있는모든 회원의 학습 시간.
	ArrayList<String> Arr_StudyTime = new ArrayList<String>();
	// 스케줄에 저장되어 있는 모든 회원의 날짜.
	ArrayList<String> Arr_Date = new ArrayList<String>();
	///////////////////////////////////////////////////////
	// 그래프 생성을 위해 쓸 데이터를 저장할 Arr
	ArrayList<String> Arr_G_Schedule = new ArrayList<String>();
	// 그래프 생성을 위해 쓸데이터를 저장할 Arr
	ArrayList<String> Arr_G_RealStudy = new ArrayList<String>();

	String DPH = null; // 클라이언트에서 받아온 회원의 전화번호
	String Schedule = null; // 클라이언트에서 받은 스케줄 정보
	String Serial = null;
	String time = null; // 받은 스케줄에서 추출한 학습시간
	String date = null; // 받은 스케줄에서 추출한 학습날짜
	String subject = null; // 받은 스케줄에서 추출한 학습과목개수
	static int Save_Count = 0; // 중복 학습 날 조건문을 위한 count 매개변수.
	
	//DB 관련
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	PreparedStatement pstmt = null;
	

	private And_Schedule() {

	}

	public static And_Schedule getInstance() {
		if (mSchedule == null)
			mSchedule = new And_Schedule();
		return mSchedule;
	}

	void schedule_start() {
		And_SocketThread mSocketThread = And_SocketThread.get();
		////////////////////////////////////////////////////////////////////////////////////////////////
		Arr_Serialnum.clear();
		Arr_StudyTime.clear();
		Arr_Date.clear();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3309/o2b2";
			conn = (Connection) DriverManager.getConnection(url, "root", "1234");

			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * From ScheduleStudyTime");

			while (rs.next()) {
				Arr_Serialnum.add(rs.getString(1));
				Arr_StudyTime.add(rs.getString(2));
				Arr_Date.add(rs.getString(3));
			}
			System.out.println("DataBase에 저장되어있는 모든 학습 스케줄을 불러오는중 .......");
			for (int i = 0; i < Arr_Serialnum.size(); i++) {
				System.out.println("시리얼 번호 : " + Arr_Serialnum.get(i) + " 학습 시간 : " + Arr_StudyTime.get(i) + " 날짜 : "
						+ Arr_Date.get(i));
			}
			System.out.println("DataBase에 저장되어있는 모든 학습 스케줄을 불러오기 완료!");

		} catch (Exception e) {

		}
		////////////////////////////////////////////////////////////////////////////////////////////////////////////

		System.out.println("스케줄 저장 시작!");
		mSocketThread.write("Schedule button pushed"); // 버튼 이 눌린 조건 받았다고 값을 보냄.

		String readData = null;

		while (true) { // 전화번호 값을 받음
			try {
				readData = mSocketThread.readData();
				if (readData != null) {
					DPH = readData;
					break;
				}
				Thread.sleep(100);
			} catch (Exception e) {

			}
		}
		System.out.println(readData);

		mSocketThread.write("get Phone Num information"); // 회원 전화번호를 받았다고 값을
															// 보냄.

		And_Search_Profile mASP = And_Search_Profile.getInstace();
		mASP.Join_Detect();
		mASP.DB_Select_value();

		Serial = mASP.Detect_SRN(DPH); // 시리얼 번호 저장.

		System.out.println("탐색한 시리얼 넘버 : " + Serial);
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
		
		System.out.println("클라이언트로 부터 받은 스케줄 값 : " + readData);
		mSocketThread.write("get schedule information");
		Schedule = readData;

		String[] btSchedule = Schedule.split("/"); // 안드로이드 앱에서 보낸 스케줄 값을 형식에 맞게
													// 나주기 위해서 스플릿
		date = btSchedule[0];
		subject = btSchedule[1];
		time = btSchedule[2];

		Save_Count = 0;
		
		for (int i = 0; i < Arr_Date.size(); i++) {
			if (date.equals(Arr_Date.get(i)) && Serial.equals(Arr_Serialnum.get(i))) {
				Save_Count++;
				System.out.println("해당 시리얼 번호에 겹치는 날짜가 있습니다!");
				System.out.println("DB time : " + Arr_StudyTime.get(i) + "  Client time : " + time);
				int SumDate = Integer.parseInt(Arr_StudyTime.get(i)) + Integer.parseInt(time);
				System.out.println("두개의 합산한 시간 : " + SumDate);
				// 합산한 값만
				try {
					System.out.println(Integer.toString(SumDate));
				pstmt = conn.prepareStatement("update ScheduleStudyTime set studytime = ? where SerialNum = ? and date = ?");
				pstmt.setString(1, Integer.toString(SumDate));
				pstmt.setString(2, Serial);
				pstmt.setString(3, date);
				pstmt.executeUpdate();
				
				} catch (Exception e) {

				}
			}
		}

		if (Save_Count == 0) {
			System.out.println("날짜  : " + date + " 과목 수  : " + subject + " 학습 시간 : " + time);

			And_DB_Schedule ADS = new And_DB_Schedule();

			ADS.insert(Serial, time, date, subject);

		}
		// 앱에서 받은 date 를 Server mysql 의 date 와 비교해서 없는경우 그냥 저장하고,
		// 있는 경우는 같은 date 의 시간을추출하고 추출한 시간과 클라이언트에서 받은 시간을 더하고,
		// 시리얼번호와 date 를 맞춰 mysql에서 변경할 열을 찾아 학습시간을 update를 해준다.
		JOptionPane.showMessageDialog(null,  "스케쥴 : "+Schedule+" 이 저장되었습니다.");

	}
	
}
