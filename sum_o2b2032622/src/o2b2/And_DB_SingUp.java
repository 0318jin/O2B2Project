package o2b2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

public class And_DB_SingUp {
	public static void insert(String SN, String PN, String PW) {
		System.out.println("Insert 함수 진입");
		
		System.out.println("시리얼넘버 : " + SN);
		System.out.println("핸드폰 번호 :" + PN);
		System.out.println("비밀번호 : " + PW);
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3309/o2b2";
			conn = (Connection) DriverManager.getConnection(url, "root", "1234");
			
			String sql = "insert into profile values(?,?,?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, SN);
			pstmt.setString(2, PN);
			pstmt.setString(3, PW);
			pstmt.executeUpdate();
			System.out.println("회원가입 정보를 저장하였습니다..");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
