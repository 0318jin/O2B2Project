package o2b2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JTextArea;

public class Delete_ScheduleStudyTime {
public static void delete(String serialnum ,String studytime , String date, String subject, JTextArea txtArea) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
	try {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/o2b2", "root", "1234");
		
		String sql = "Delete from schedulestudytime where serialnum = ? and studytime= ? and date= ? and subject= ?";
		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, serialnum);
		pstmt.setString(2, studytime);
		pstmt.setString(3, date);
		pstmt.setString(4, subject);
		
		
		
		int count = pstmt.executeUpdate();
		if (count == 0) {
			System.out.println("변경된 row: "+ count);
			txtArea.append("삭제할 값을 다시한번 확인해 주세요.\n");
		}else {
			System.out.println("데이터 입력 성공");
			txtArea.append("삭제 되었습니다.\n");
		}
		

	} catch (Exception e) {
		txtArea.append("에러입니다 삭제할 값을 다시한번 확인해 주세요.\n");
		e.printStackTrace();

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
