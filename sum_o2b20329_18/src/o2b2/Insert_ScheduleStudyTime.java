package o2b2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JTextArea;

public class Insert_ScheduleStudyTime {
	public static void insert(String serialnum, String studytime, String date, String subject, JTextArea txtArea) {

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3309/o2b2", "root", "1234");

			String sql = "INSERT INTO ScheduleStudyTime VALUES (?,?,?,?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, serialnum);
			pstmt.setString(2, studytime);
			pstmt.setString(3, date);
			pstmt.setString(4, subject);

			
			
			int count = pstmt.executeUpdate();
			if (count == 0) {
				System.out.println("데이터 입력 실패");
				txtArea.append("추가할 값을 다시한번 확인해 주세요.\n");
			} else {
				System.out.println("데이터 입력 성공");
				txtArea.append("추가되었습니다. \n");
			}

		} catch (Exception e) {

			e.printStackTrace();
			txtArea.append("에러입니다 추가할 값을 다시한번 확인해 주세요.\n");
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
