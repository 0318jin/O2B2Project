package o2b2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

public class And_DB_addfr {
	public static void insert(String FPN) {
		System.out.println("Insert �Լ� ����");
		
		System.out.println("ģ�� �ڵ��� ��ȣ : " + FPN);
		
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
			System.out.println("���� �����ϴ�.");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
