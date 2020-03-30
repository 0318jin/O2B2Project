package o2b2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JTextArea;

public class Delete_RealStudyTime {
public static void delete(String serialnum ,String studytime , String date, String subject, JTextArea txtArea) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
	try {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3309/o2b2", "root", "1234");
		System.out.println("DElete내부 1 : "+serialnum+studytime+date+subject);
		String sql = "Delete from RealStudyTime where SerialNum = ? and studytime= ? and date= ? and subject= ?";
		pstmt = conn.prepareStatement(sql);
		//
		System.out.println("delete 내부2 : "+serialnum+"/"+studytime+"/"+date+"/"+subject+"/");
		//
		pstmt.setString(1, serialnum);
		pstmt.setString(2, studytime);
		pstmt.setString(3, date);
		pstmt.setString(4, subject);
		
		pstmt.executeUpdate();
		
		txtArea.append("삭제 되었습니다.\n");


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
