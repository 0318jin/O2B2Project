package o2b2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class And_DB_Schedule {


	public static void insert(String SN, String ST, String DT, String SB) {
		System.out.println("������ Insert �Լ� ����");
		System.out.println("�ø��� �ѹ� : " + SN);
		System.out.println("���� �н� �ð� : " + ST);
		System.out.println("��¥ : " + DT);
		System.out.println("���� �� : " + SB);

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3309/o2b2";
			conn = (Connection) DriverManager.getConnection(url, "root", "1234");
			
			String sql = "insert into ScheduleStudyTime values(?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, SN);
			pstmt.setString(2, ST);
			pstmt.setString(3, DT);
			pstmt.setString(4, SB);
			pstmt.executeUpdate();
			
			System.out.println("�������� ���� �Ͽ����ϴ�.");

		} catch (Exception e) {

		}

	}
}
