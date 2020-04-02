package o2b2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class And_DB_Schedule {


	public static void insert(String SN, String ST, String DT, String SB) {
		System.out.println("스케줄 Insert 함수 진입");
		System.out.println("시리얼 넘버 : " + SN);
		System.out.println("예정 학습 시간 : " + ST);
		System.out.println("날짜 : " + DT);
		System.out.println("과목 수 : " + SB);

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
			
			System.out.println("스케줄을 저장 하였습니다.");

		} catch (Exception e) {

		}

	}
}
