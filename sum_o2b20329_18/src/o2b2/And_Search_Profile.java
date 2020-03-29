package o2b2;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class And_Search_Profile {
	static private And_Search_Profile mASP = null;
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	PreparedStatement pstmt = null;

	int index = 0;

	private ArrayList<String> mSRN = new ArrayList<String>(); // 시리얼 번호
	private ArrayList<String> mPH = new ArrayList<String>(); // 핸드폰 번호

	private And_Search_Profile() {

	}

	public static And_Search_Profile getInstace() {
		if (mASP == null)
			mASP = new And_Search_Profile();
		return mASP;
	}

	void Join_Detect() {
		try {
			System.out.println("해당 회원의 시리얼 넘버를 탐색 시작.");

			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3309/o2b2";
			conn = (Connection) DriverManager.getConnection(url, "root", "1234");

			String sql = "";

		} catch (Exception e) {

		}
	}

	int DB_Select_value() {
		try {
			mSRN.clear();
			mPH.clear();

			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * From profile");

			while (rs.next()) {
				mSRN.add(rs.getString(1));
				mPH.add(rs.getString(2));
			}

		} catch (Exception e) {
			return -1;
		}
		return 0;
	}

	String Detect_SRN(String ph) {
		System.out.println("시리얼 넘버 탐색 시작");

		for (int i = 0; i < mPH.size(); i++) {
			System.out.println("해당 회원의 시리얼 탐색 중.... : "+ mPH.get(i));
			if(mPH.get(i).equals(ph)){
				index = i;
				System.out.println("시리얼 넘버 인덱스값 탐지 완료.");
				break;
			}
		}
		return mSRN.get(index);
	}

}































