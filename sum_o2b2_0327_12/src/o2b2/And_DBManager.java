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

	void DB_Join() { // DB ����..
		try {
			System.out.println("DB ���� ��ٸ��� ��.....");
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

	int DB_Select_countNum(String sql) { // DB ȸ�� ���̺��� ��ü �� ����(ȸ�� ���� �ο� ��������)
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
	
	int DB_RealStudyTime_countNum(String sql) { // DB �����н��ð� ���̺��� ��ü �� ����(�����н��ð� ���� ��������)
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
	
	int DB_ScheduleStudyTime_countNum(String sql) { // DB ��ȹ�н��ð� ���̺��� ��ü �� ����(��ȹ�н��ð� ���� ��������)
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
	 * // mID, mPW ArrayList�� ���̺��� ID PW ���� ���� �������.
	 */

	int DB_Select_value(String sql) {
		try {
			mPH.clear(); // ���� ������ �� �α��� �Ϸ��ϸ� �ִ����� �ǳ־ ��ĥ�Ŷ� �����ؼ� �־,.
			mPW.clear();

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				mSN1.add(rs.getString(1));
				mPH.add(rs.getString(2));
				mPW.add(rs.getString(3));

			}
			// System.out.println(mPH.size()); //���� Ȯ�ο� Print��
		} catch (Exception e) {
			return -1;
		}
		return 0;
	}

	int DB_Select_value2(String sql)// ����ٰ� realtime�ִ� ���� ����ֱ�
	{
		mRT.clear();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				mSN2.add(rs.getString(1));
				mRT.add(rs.getString(2));

			}
			// System.out.println(mPH.size()); //���� Ȯ�ο� Print��
		} catch (Exception e) {
			return -1;
		}
		return 0;
	}

	boolean ID_Match(String id) {
		System.out.println("ID_Match �Լ� ����");
		// System.out.println("mPH ������ : " + mPH.size()); //���� Ȯ�ο� Print��

		for (int i = 0; i < mPH.size(); i++) {
			System.out.println("ID_Match �Լ� �ݺ��� ����");
			if (mPH.get(i).equals(id)) { // Ŭ���̾�Ʈ ID �� mID ArrayList �� ���������� ����
											// ������ �ش� �ε��� ���� ����.
				id_index = i;
				System.out.println("ID ����");
				return true;
			}
		}
		System.out.println("ID Ʋ��");
		return false;
	}

	boolean PW_Match(String pw) {
		System.out.println("PW_Match �Լ� ����");
		if (mPW.get(id_index).equals(pw)) { // DB_ID ������ �޾ƿ� �ε��� ���� �̿��� �ش�
			System.out.println("PW ����");
			return true;
		}
		System.out.println("PW Ʋ��");
		return false;
	}

	void Insert(String SN, String PN, String PW) {
		try {
			pstmt = conn.prepareStatement("insert into profile values(?,?,?)");

			pstmt.setString(1, SN);
			pstmt.setString(2, PN);
			pstmt.setString(3, PW);

			System.out.println("���� �����ϴ�.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	boolean DB_find_SN(String fpn) {

		System.out.println("SN�Ǻ� �Լ� ����");
		

		for (int i = 0; i < mPH.size(); i++) {
			System.out.println("SN ã�� �Լ� ����");
			if (mPH.get(i).equals(fpn)) { // Ŭ���̾�Ʈ ID �� mID ArrayList �� ���������� ����

				FrSn = mSN1.get(i);
				System.out.println("ģ�� SNã�� : " + FrSn);
				return true;
			}
		}
		System.out.println("ģ����Ͽ� ģ�� ����");
		return false;

	}

	int DB_frtime() {
		try {
			realtime = 0;
			to = 0;
			
			System.out.println("realtime �Լ� ����");
			System.out.println("FrSn : " + FrSn);
			for (int i = 0; i < mRT.size(); i++) {
				System.out.println("realtime ���ϴ� �Լ� ����");
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
