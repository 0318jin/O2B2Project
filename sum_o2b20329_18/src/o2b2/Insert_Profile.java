package o2b2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JTextArea;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

public class Insert_Profile {
	public static void insert(String serialnum, String phonenum, String password1, JTextArea txtArea) {

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3309/o2b2", "root", "1234");

			String sql = "INSERT INTO profile VALUES (?,?,?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, serialnum);
			pstmt.setString(2, phonenum);
			pstmt.setString(3, password1);
			
			
			
			int count = pstmt.executeUpdate();
			if (count == 0) {
				System.out.println("데이터 입력 실패");
				txtArea.append("추가할 값을 다시한번 확인해 주세요.\n");

			} else {
				System.out.println("데이터 입력 성공");
				txtArea.append("추가되었습니다. \n");
			}
		
		} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
			txtArea.append("이미 사용자 데이터가 존재합니다.\n");
			e.printStackTrace();

		}catch (Exception e) {
			txtArea.append("입력값을 다시한번 확인해 주세요.\n");
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
