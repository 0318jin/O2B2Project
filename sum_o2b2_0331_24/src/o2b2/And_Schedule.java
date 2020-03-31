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

	// �ߺ��� ��¥�� ������ �н��� �߰��ϴ� ��� �� �ߺ��� ó���ϱ����� Arr_List
	// �����ٿ� ����Ǿ� �ִ¸�� ȸ���� �ø��� �ѹ�.
	ArrayList<String> Arr_Serialnum = new ArrayList<String>();
	// �����ٿ� ����Ǿ� �ִ¸�� ȸ���� �н� �ð�.
	ArrayList<String> Arr_StudyTime = new ArrayList<String>();
	// �����ٿ� ����Ǿ� �ִ� ��� ȸ���� ��¥.
	ArrayList<String> Arr_Date = new ArrayList<String>();
	///////////////////////////////////////////////////////
	// �׷��� ������ ���� �� �����͸� ������ Arr
	ArrayList<String> Arr_G_Schedule = new ArrayList<String>();
	// �׷��� ������ ���� �������͸� ������ Arr
	ArrayList<String> Arr_G_RealStudy = new ArrayList<String>();

	String DPH = null; // Ŭ���̾�Ʈ���� �޾ƿ� ȸ���� ��ȭ��ȣ
	String Schedule = null; // Ŭ���̾�Ʈ���� ���� ������ ����
	String Serial = null;
	String time = null; // ���� �����ٿ��� ������ �н��ð�
	String date = null; // ���� �����ٿ��� ������ �н���¥
	String subject = null; // ���� �����ٿ��� ������ �н����񰳼�
	static int Save_Count = 0; // �ߺ� �н� �� ���ǹ��� ���� count �Ű�����.
	
	//DB ����
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
			System.out.println("DataBase�� ����Ǿ��ִ� ��� �н� �������� �ҷ������� .......");
			for (int i = 0; i < Arr_Serialnum.size(); i++) {
				System.out.println("�ø��� ��ȣ : " + Arr_Serialnum.get(i) + " �н� �ð� : " + Arr_StudyTime.get(i) + " ��¥ : "
						+ Arr_Date.get(i));
			}
			System.out.println("DataBase�� ����Ǿ��ִ� ��� �н� �������� �ҷ����� �Ϸ�!");

		} catch (Exception e) {

		}
		////////////////////////////////////////////////////////////////////////////////////////////////////////////

		System.out.println("������ ���� ����!");
		mSocketThread.write("Schedule button pushed"); // ��ư �� ���� ���� �޾Ҵٰ� ���� ����.

		String readData = null;

		while (true) { // ��ȭ��ȣ ���� ����
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

		mSocketThread.write("get Phone Num information"); // ȸ�� ��ȭ��ȣ�� �޾Ҵٰ� ����
															// ����.

		And_Search_Profile mASP = And_Search_Profile.getInstace();
		mASP.Join_Detect();
		mASP.DB_Select_value();

		Serial = mASP.Detect_SRN(DPH); // �ø��� ��ȣ ����.

		System.out.println("Ž���� �ø��� �ѹ� : " + Serial);
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
		
		System.out.println("Ŭ���̾�Ʈ�� ���� ���� ������ �� : " + readData);
		mSocketThread.write("get schedule information");
		Schedule = readData;

		String[] btSchedule = Schedule.split("/"); // �ȵ���̵� �ۿ��� ���� ������ ���� ���Ŀ� �°�
													// ���ֱ� ���ؼ� ���ø�
		date = btSchedule[0];
		subject = btSchedule[1];
		time = btSchedule[2];

		Save_Count = 0;
		
		for (int i = 0; i < Arr_Date.size(); i++) {
			if (date.equals(Arr_Date.get(i)) && Serial.equals(Arr_Serialnum.get(i))) {
				Save_Count++;
				System.out.println("�ش� �ø��� ��ȣ�� ��ġ�� ��¥�� �ֽ��ϴ�!");
				System.out.println("DB time : " + Arr_StudyTime.get(i) + "  Client time : " + time);
				int SumDate = Integer.parseInt(Arr_StudyTime.get(i)) + Integer.parseInt(time);
				System.out.println("�ΰ��� �ջ��� �ð� : " + SumDate);
				// �ջ��� ����
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
			System.out.println("��¥  : " + date + " ���� ��  : " + subject + " �н� �ð� : " + time);

			And_DB_Schedule ADS = new And_DB_Schedule();

			ADS.insert(Serial, time, date, subject);

		}
		// �ۿ��� ���� date �� Server mysql �� date �� ���ؼ� ���°�� �׳� �����ϰ�,
		// �ִ� ���� ���� date �� �ð��������ϰ� ������ �ð��� Ŭ���̾�Ʈ���� ���� �ð��� ���ϰ�,
		// �ø����ȣ�� date �� ���� mysql���� ������ ���� ã�� �н��ð��� update�� ���ش�.
		JOptionPane.showMessageDialog(null,  "������ : "+Schedule+" �� ����Ǿ����ϴ�.");

	}
	
}
