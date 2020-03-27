package o2b2;
import java.sql.*;
import java.util.ArrayList;

public class And_DBManager {
	static private And_DBManager mDbManager = null;
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	PreparedStatement pstmt = null;
	int id_index = 0;
	int realtime = 0;
	int to = 0;
	String FrSn = "";

	private ArrayList<String> mPH = new ArrayList<String>();
	private ArrayList<String> mPW = new ArrayList<String>();
	private ArrayList<String> mRT = new ArrayList<String>();
	private ArrayList<String> mSN1 = new ArrayList<String>();
	private ArrayList<String> mSN2 = new ArrayList<String>();

	private And_DBManager() {

	}

	public static And_DBManager getInstance() {
		if (mDbManager == null)
			mDbManager = new And_DBManager();
		return mDbManager;
	}

	void DB_Join() { // DB 접속..
		try {
			System.out.println("DB 연결 기다리는 중.....");
			Class.forName("com.mysql.jdbc.Driver");
			conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3309/o2b2", "root", "1234");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	int DB_Select_countNum(String sql) { // DB 회원 테이블의 전체 행 갯수(회원 가입 인원 가져오기)
		SingleTon s = SingleTon.getInstanse();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				s.Profile_count = rs.getInt(1);
			}
		} catch (Exception e) {
		}

		return s.Profile_count;
	}
	
	int DB_RealStudyTime_countNum(String sql) { // DB 실제학습시간 테이블의 전체 행 갯수(실제학습시간 갯수 가져오기)
		SingleTon s = SingleTon.getInstanse();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				s.RealStudyTime_count = rs.getInt(1);
			}
		} catch (Exception e) {
		}

		return s.RealStudyTime_count;
	}
	
	int DB_ScheduleStudyTime_countNum(String sql) { // DB 계획학습시간 테이블의 전체 행 갯수(계획학습시간 갯수 가져오기)
		SingleTon s = SingleTon.getInstanse();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				s.ScheduleStudyTime_count = rs.getInt(1);
			}
		} catch (Exception e) {
		}

		return s.ScheduleStudyTime_count;
	}

	/*
	 * // mID, mPW ArrayList에 테이블의 ID PW 값을 각각 집어넣음.
	 */

	int DB_Select_value(String sql) {
		try {
			mPH.clear(); // 만약 다음에 또 로그인 하려하면 있던곳에 또넣어서 겹칠거라 생각해서 넣어봄,.
			mPW.clear();

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				mSN1.add(rs.getString(1));
				mPH.add(rs.getString(2));
				mPW.add(rs.getString(3));

			}
			// System.out.println(mPH.size()); //에러 확인용 Print문
		} catch (Exception e) {
			return -1;
		}
		return 0;
	}

	int DB_Select_value2(String sql)// 여기다가 realtime있는 데베 집어넣기
	{
		mRT.clear();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				mSN2.add(rs.getString(1));
				mRT.add(rs.getString(2));

			}
			// System.out.println(mPH.size()); //에러 확인용 Print문
		} catch (Exception e) {
			return -1;
		}
		return 0;
	}

	boolean ID_Match(String id) {
		System.out.println("ID_Match 함수 진입");
		// System.out.println("mPH 사이즈 : " + mPH.size()); //에러 확인용 Print문

		for (int i = 0; i < mPH.size(); i++) {
			System.out.println("ID_Match 함수 반복문 진입");
			if (mPH.get(i).equals(id)) { // 클라이언트 ID 와 mID ArrayList 를 같을때까지 돌아
											// 같으면 해당 인덱스 값을 저장.
				id_index = i;
				System.out.println("ID 맞음");
				return true;
			}
		}
		System.out.println("ID 틀림");
		return false;
	}

	boolean PW_Match(String pw) {
		System.out.println("PW_Match 함수 진입");
		if (mPW.get(id_index).equals(pw)) { // DB_ID 측에서 받아온 인덱스 값을 이용해 해당
			System.out.println("PW 맞음");
			return true;
		}
		System.out.println("PW 틀림");
		return false;
	}

	void Insert(String SN, String PN, String PW) {
		try {
			pstmt = conn.prepareStatement("insert into profile values(?,?,?)");

			pstmt.setString(1, SN);
			pstmt.setString(2, PN);
			pstmt.setString(3, PW);

			System.out.println("값이 들어갔습니다.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	boolean DB_find_SN(String fpn) {

		System.out.println("SN판별 함수 진입");
		

		for (int i = 0; i < mPH.size(); i++) {
			System.out.println("SN 찾는 함수 진입");
			if (mPH.get(i).equals(fpn)) { // 클라이언트 ID 와 mID ArrayList 를 같을때까지 돌아

				FrSn = mSN1.get(i);
				System.out.println("친구 SN찾음 : " + FrSn);
				return true;
			}
		}
		System.out.println("친구목록에 친구 없음");
		return false;

	}

	int DB_frtime() {
		try {
			realtime = 0;
			to = 0;
			
			System.out.println("realtime 함수 진입");
			System.out.println("FrSn : " + FrSn);
			for (int i = 0; i < mRT.size(); i++) {
				System.out.println("realtime 더하는 함수 진입");
				if (mSN2.get(i).equals(FrSn)) {
					
					System.out.println("mRT.get(i) :" + mRT.get(i));
					to = Integer.parseInt(mRT.get(i));

					realtime += to;

					System.out.println("realtime : " + realtime);

				}
			}

		} catch (Exception e) {
			return -1;
		}

		return realtime;

	}

	int fr_realtime(String FRP) {

		return 0;
	}

	ArrayList<String> getID() {
		return mPH;
	}

	ArrayList<String> getPW() {
		return mPW;
	}
}
