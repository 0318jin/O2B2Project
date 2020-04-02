package o2b2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class And_DB_Chart {
	static private And_DB_Chart mADC = null;
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	PreparedStatement pstmt = null;

	int index = 0;

	private ArrayList<String> S_Study = new ArrayList<String>(); // ������ �н� �ð� ����
																	// Arr

	private ArrayList<String> R_Study = new ArrayList<String>(); // ���� �н� �ð�
																	// ����Arr

	int Schedule_count = 0; // ������ ���� ����
	int RealStudy_count = 0; // �����н��ð� ���� ����

	String Send_Schedule = null; // �ȵ���̵�� ���� ������ �ð�
	String Send_Real = null; // �ȵ���̵�� ���� ���� �н� �ð�

	private And_DB_Chart() {

	}

	public static And_DB_Chart getInstance() {
		if (mADC == null)
			mADC = new And_DB_Chart();
		return mADC;
	}

	int Schedule_Count(String SerialNum) { // ������ ���̺��� ���� ����
		Schedule_count = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3309/o2b2";
			conn = (Connection) DriverManager.getConnection(url, "root", "1234");

			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from ScheduleStudyTime where SerialNum like " + SerialNum);
			while (rs.next()) {
				Schedule_count = rs.getInt(1);
			}
			System.out.println("�α����� ȸ���� ������ ���� : " + Schedule_count);

		} catch (Exception e) {

		}

		return Schedule_count;
	}

	int RealStudy_Count(String SerialNum) { // ���� �н� ���̺��� ���� ����
		RealStudy_count = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3309/o2b2";
			conn = (Connection) DriverManager.getConnection(url, "root", "1234");

			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from RealStudyTime where SerialNum like " + SerialNum);
			while (rs.next()) {
				RealStudy_count = rs.getInt(1);
			}
			System.out.println("�α����� ȸ���� �����н��ð� ���� : " + RealStudy_count);
		} catch (Exception e) {

		}

		return RealStudy_count;
	}

	String Schedule_data() { // �α����� ȸ���� �ø���ѹ��� �޾� ������ ���� Arr�� ����
		String SHD = null;
		try {
			S_Study.clear();
			System.out.println("������ ������.");

			And_Login mA = And_Login.getInstance();

			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3309/o2b2";
			conn = (Connection) DriverManager.getConnection(url, "root", "1234");

			stmt = conn.createStatement();
			rs = stmt.executeQuery("select* from ScheduleStudyTime where SerialNum like " + mA.Serial);
			while (rs.next()) {
				S_Study.add(rs.getString(2));
			}
			for (int i = 0; i < S_Study.size(); i++) {
				System.out.print(S_Study.get(i) + ", ");
			}
			System.out.println("");

			//////////////////// Arr �� �� ���ڿ���
			//////////////////// ��ġ��////////////////////////////////////////////////
			StringBuilder Send_Schedule = new StringBuilder();
			////////////////////////////////////////////////////////////////////////////////////////
			if (Schedule_count == 0) {
				for (int i = 0; i < 7; i++) {
					Send_Schedule.append("0/");
				}
				SHD = Send_Schedule.toString();
				SHD = SHD.substring(0, SHD.length() - 1);
				System.out.println("������ ���ڿ� :" + SHD);
			}

			else if (Schedule_count < 7) {
				for (int i = 0; i < Schedule_count; i++) {
					Send_Schedule.append(S_Study.get(i) + "/");
				}
				for (int i = Schedule_count; i < 7; i++) {
					Send_Schedule.append("0/");
				}
				SHD = Send_Schedule.toString();
				SHD = SHD.substring(0, SHD.length() - 1);
				System.out.println("������ ���ڿ� :" + SHD);
				////////////////////////////////////////////////
			} else {
				for (int i = Schedule_count - 7; i < Schedule_count; i++) {
					Send_Schedule.append(S_Study.get(i) + "/");
				}
				SHD = Send_Schedule.toString();
				SHD = SHD.substring(0, SHD.length() - 1);
				System.out.println("������ ���ڿ� :" + SHD);

			}
		} catch (Exception e) {
			System.out.println("die1");
		}
		return SHD;
	}

	String Real_data() { // �α����� ȸ���� �ø���ѹ��� �޾� ���� �н��ð� ���� Arr�� ����
		String RD = null;
		try {
			R_Study.clear();
			System.out.println("���� �н��ð� ������.");

			And_Login mA = And_Login.getInstance();

			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3309/o2b2";
			conn = (Connection) DriverManager.getConnection(url, "root", "1234");

			stmt = conn.createStatement();
			rs = stmt.executeQuery("select* from RealStudyTime where SerialNum like " + mA.Serial);
			while (rs.next()) {
				R_Study.add(rs.getString(2));
			}

			for (int i = 0; i < R_Study.size(); i++) {
				System.out.print(R_Study.get(i) + ", ");
			}
			System.out.println("");

			//////////////////// Arr �� �� ���ڿ���
			//////////////////// ��ġ��////////////////////////////////////////////////
			StringBuilder Send_Real = new StringBuilder();

			if (RealStudy_count == 0) {
				for (int i = 0; i < 7; i++) {
					Send_Real.append("0/");
				}
				RD = Send_Real.toString();
				RD = RD.substring(0, RD.length() - 1);
				System.out.println("������ ���ڿ� :" + RD);
			}

			else if (RealStudy_count < 7) {
				for (int i = 0; i < RealStudy_count; i++) {
					Send_Real.append(R_Study.get(i) + "/");
				}
				for (int i = RealStudy_count; i < 7; i++) {
					Send_Real.append("0/");
				}
				RD = Send_Real.toString();
				RD = RD.substring(0, RD.length() - 1);
				System.out.println("������ ���ڿ� :" + RD);
			} else {
				for (int i = RealStudy_count - 7; i < RealStudy_count; i++) {
					Send_Real.append(R_Study.get(i) + "/");
				}
				RD = Send_Real.toString();
				RD = RD.substring(0, RD.length() - 1);
				System.out.println("������ ���ڿ� :" + RD);
			}

		} catch (Exception e) {
			System.out.println("die2");
		}
		return RD;
	}
}
