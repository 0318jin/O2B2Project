package o2b2;

import java.sql.*;
import java.util.ArrayList;

public class And_DBManager {
	static private And_DBManager mDbManager = null;
	Connection conn = null;
	Statement stmt = null;
	//
	PreparedStatement pstmt = null;
	//
	ResultSet rs = null;
	int id_index = 0;

	private ArrayList<String> mPH = new ArrayList<String>();
	private ArrayList<String> mPW = new ArrayList<String>();

	private And_DBManager() {

	}

	public static And_DBManager getInstance() {
		if (mDbManager == null)
			mDbManager = new And_DBManager();
		return mDbManager;
	}

	void DB_Join() { // DB 접속..
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3307/o2b2", "root", "1234");
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

	/*
	 * // mID, mPW ArrayList에 테이블의 ID PW 값을 각각 집어넣음.
	 */

	int DB_Select_value(String sql) {
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				mPH.add(rs.getString(2));
				mPW.add(rs.getString(3));

			}
			// System.out.println(mPH.size()); //에러 확인용 Print문
		} catch (Exception e) {
			return -1;
		}
		return 0;
	}

	boolean ID_Match(String id) {
		System.out.println("isID 함수 진입");
		// System.out.println("mPH 사이즈 : " + mPH.size()); //에러 확인용 Print문
		for (int i = 0; i < mPH.size(); i++) {
			System.out.println("ID_Match 함수 진입");
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
	///////////////////////////////////////    회원가입    ////////////////////////////////////
	boolean Singup_Match(String id) {
		System.out.println("matchID 함수 진입");
		for (int i = 0; i < mPH.size(); i++) {
			if (mPH.get(i).equals(id)) { // 클라이언트 ID 와 mID ArrayList 를 같을때까지 돌아

				System.out.println("ID 겹침");
				return true;
			}
		}
		return false;
	}

	ArrayList<String> getID() {
		return mPH;
	}

	ArrayList<String> getPW() {
		return mPW;
	}
}
