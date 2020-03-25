package o2b2;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import o2b2.JPanel01.JPanelTest;

class JPanel03 extends JPanel {

	private JButton jButton1;
	private JButton jButton2;
	private JButton jButton3;
	private JButton jButton4;
	private JLabel jlabel1;
	private JTextField jtextfield1;
	private JScrollPane jScrollPane1;
	private JTextArea jTextArea1;
	
	JComboBox<String> c = new JComboBox();
	
	void makeCombo() { // select 박스
		c.setSize(500, 40);
		c.setLocation(200, 385);
		
  	    ImageIcon img1 = new ImageIcon("image/book5.png");  //이미지 경로
	    JLabel imagelJLabel1 = new JLabel (img1);     
	    imagelJLabel1.setSize(200,200);
	    imagelJLabel1.setLocation(10,60);
	    add(imagelJLabel1);
	    
	
SingleTon s =SingleTon.getInstanse();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/o2b2", "root", "1234");
		
			stmt = conn.createStatement();
		
			String sql = "SELECT * FROM schedulestudytime ORDER BY serialnum asc;";
		
			rs = stmt.executeQuery(sql);
		
			while (rs.next()) {
				// 레코드의 칼럼은 배열과 달리 0부터 시작하지 않고 1부터 시작한다.
				// 데이터베이스에서 가져오는 데이터의 타입에 맞게 getString 또는 getInt 등을 호출한다.
				String serialnum = rs.getString(1);
				s.SelectScheduleserialnum_singleTon.add(rs.getString(1));
				String studytime = rs.getString(2);
				s.SelectSchedulestudytime_singleTon.add(rs.getString(2));
				String date = rs.getString(3);
				s.SelectScheduledate_singleTon.add(rs.getString(3));
				String subject = rs.getString(4);
				s.SelectSchedulesubject_singleTon.add(rs.getString(4));
		
				s.schedulestudytimeval = serialnum + "/" + studytime + "/" + date + "/"+ subject + "\n";
				c.addItem(s.schedulestudytimeval);
				
			}
	
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
				if (stmt != null && !stmt.isClosed()) {
					stmt.close();
				}
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		add(c);
		c.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				s.Delete_sst = c.getSelectedItem().toString();
			}
		});
	}

	public void init() {

		// Panel p = new Panel();

	}

	public JPanel03(JPanelTest win) {
		
		
		SingleTon s = SingleTon.getInstanse();
		
		makeCombo();
		// init();

		setLayout(null);

		ImageIcon img = new ImageIcon("image/oba-study2.jpg"); // 이미지 경로
		JLabel imagelJLabel = new JLabel(img);
		imagelJLabel.setSize(200, 60);
		imagelJLabel.setLocation(8, 45);

		add(imagelJLabel);
		setVisible(true); // // 화면에 보이기

		jlabel1 = new JLabel("계획 학습시간");
		jlabel1.setSize(200, 40);
		jlabel1.setLocation(365, 10);
		jlabel1.setForeground(Color.white); // 글 색상
		jlabel1.setFont(jlabel1.getFont().deriveFont(20.0f));
		add(jlabel1);

		jButton1 = new JButton(new ImageIcon("image/UP.PNG"));
		jButton1.setSize(130, 40);
		jButton1.setLocation(40, 240);
		add(jButton1);
		//
		jButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jTextArea1.setText("");
				Select_ScheduleStudyTime mS = new Select_ScheduleStudyTime();
				mS.loadScheduleStudytime(jTextArea1);
			}
		});
		//

		jButton2 = new JButton(new ImageIcon("image/IN.PNG"));
		jButton2.setSize(130, 40);
		jButton2.setLocation(40, 450);
		add(jButton2);
		//
//		Insert_ScheduleStudyTime Insert_ScheduleStudyTime = new Insert_ScheduleStudyTime();
		jButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 텍스트 필드에서 받아온 값을 INSERT시 사용하기 예외처리???????????????????????
				try {
					jTextArea1.setText("");
					s.get_textfield_schedulestudy = jtextfield1.getText();
					System.out.println(s.get_textfield_schedulestudy);
					jTextArea1.append(s.get_textfield_schedulestudy+"\n");
					jtextfield1.setText(null);					
					String[] array = s.get_textfield_schedulestudy.split("/");
					String serialNum = array[0];
					String studytime = array[1];
					String date = array[2];
					String subject = array[3];

					Insert_ScheduleStudyTime.insert(serialNum, studytime, date, subject, jTextArea1);
					c.addItem(s.get_textfield_schedulestudy);
				} catch (Exception e2) {
					// TODO: handle exception
					jTextArea1.append("실패했습니다. 값을 다시 한번 확인해 주세요.");
				}
			}
	      });
		//

		jButton3 = new JButton(new ImageIcon("image/DL.PNG"));
		jButton3.setSize(130, 40);
		jButton3.setLocation(40, 385);
		add(jButton3);
		//
		jButton3.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 
	        	 jTextArea1.setText("");
//	        	 s.Delete_sst = "2,3,2020-02-11,3";
	         	String[] array = s.Delete_sst.split("/");
	         	String serialNum = array[0];
	       		String studytime = array[1];
	       		String date = array[2];
	       		String subject = array[3];
	       		
	          	Delete_ScheduleStudyTime.delete(serialNum, studytime, date, subject, jTextArea1);
	          	c.removeItem(s.Delete_sst);
	         }
	      });
		//

		jButton4 = new JButton(new ImageIcon("image/CL.PNG"));
		jButton4.setSize(130, 40);
		jButton4.setLocation(40, 310);
		add(jButton4);
		//
		jButton4.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 jTextArea1.setText("");

	         }
	      });
		//

		jTextArea1 = new JTextArea();
		jScrollPane1 = new JScrollPane(jTextArea1);
		jScrollPane1.setSize(500, 300);
		jScrollPane1.setLocation(200, 50);
		add(jScrollPane1);

		jtextfield1 = new JTextField();
		jtextfield1.setSize(500, 40);
		jtextfield1.setLocation(200, 450);
		add(jtextfield1);

	}

}

public class EpStdMenu {

}
