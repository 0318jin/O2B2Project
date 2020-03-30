package o2b2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

public class And_DB_addfr {
	public static void insert(String FPN) {
		System.out.println("Insert 함수 진입");
		
		System.out.println("친구 핸드폰 번호 : " + FPN);
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3309/o2b2?useSSL=false";
			conn = (Connection) DriverManager.getConnection(url, "root", "1234");
			
//			String sql = "insert into profile values(?,?)";
//			pstmt = conn.prepareStatement(sql);
//			
//			pstmt.setString(4, FPN);
//			pstmt.executeUpdate();
			System.out.println("값이 들어갔습니다.");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
