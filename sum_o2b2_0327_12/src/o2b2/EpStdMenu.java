package o2b2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterIOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import o2b2.JPanel01.JPanelTest;

class JPanel03 extends JPanel {

	private JButton jButton1;
	private JButton jButton2;
	private JButton jButton3;
	private JButton jButton4;
	private JLabel jlabel1;
	private JTextField jtextfield1;
	private JTextField jtextfield2;
	private JTextField jtextfield3;
	private JTextField jtextfield4;
	
	private JScrollPane jScrollPane1;
	private JTextArea jTextArea1;
	
	SingleTon s =SingleTon.getInstanse();
	
	JComboBox<String> c = new JComboBox();
	JTable table;
	
	static final int TABLE_MENU = 0;
	
	static final int TABLE_SERIALNUM = 0;
	static final int TABLE_PHONENUM = 1;
	static final int TABLE_DATE = 2;
	static final int TABLE_SUBJECT = 3;
    final String[] columnType = {"시리얼넘버", "공부시간", "날짜", "과목수"};
    static boolean DBGTest = false;
    
    // Insert에서 사용하는 필드
    String serialnum = null;
    String studytime = null;
    String date = null;
    String subject = null;
    
	void makeCombo() { // select 박스
		c.setSize(300, 40);
		c.setLocation(300, 680);
		c.setFont(new Font("나눔고딕",Font.BOLD,20));
//		c.addItem("data1"); // 데이터값 넣기

		
  	    ImageIcon img1 = new ImageIcon("image/book5.png");  //이미지 경로
	    JLabel imagelJLabel1 = new JLabel (img1);     
	    imagelJLabel1.setSize(200,200);
	    imagelJLabel1.setLocation(860,80);
	    add(imagelJLabel1);
	    

		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3309/o2b2", "root", "1234");
		
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

////

	public void init() {

		// Panel p = new Panel();

	}

	public JPanel03(JPanelTest win) {
		
		
		SingleTon s = SingleTon.getInstanse();
		
		makeCombo();
		// init();

		setLayout(null);

		uploadJtable();
		
		ImageIcon img = new ImageIcon("image/oba-study2.jpg"); // 이미지 경로
		JLabel imagelJLabel = new JLabel(img);
		imagelJLabel.setSize(170, 60);
		imagelJLabel.setLocation(1000, 650);
		
		ImageIcon img1 = new ImageIcon("image/exp1.png");  //이미지 경로
	    JLabel imagelJLabel1 = new JLabel (img1);     
	    imagelJLabel1.setSize(300,60);
	    imagelJLabel1.setLocation(280,45);
	    
	    add(imagelJLabel1);

		add(imagelJLabel);
		setVisible(true); // // 화면에 보이기

//		jlabel1 = new JLabel("계획 학습시간");
//		jlabel1.setSize(200, 40);
//		jlabel1.setLocation(365, 10);
//		jlabel1.setForeground(Color.white); // 글 색상
//		jlabel1.setFont(jlabel1.getFont().deriveFont(20.0f));
//		add(jlabel1);

		jButton1 = new JButton(new ImageIcon("image/UP.PNG"));
		jButton1.setSize(250, 60);
		jButton1.setLocation(820, 310);
		add(jButton1);
		//
		jButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jTextArea1.setText("");
				Select_ScheduleStudyTime mS = new Select_ScheduleStudyTime();
				mS.loadScheduleStudytime(jTextArea1);
				uploadJtable();
			}
		});
		//

		jButton2 = new JButton(new ImageIcon("image/inbtn.PNG"));
		jButton2.setSize(250, 60);
		jButton2.setLocation(820, 435);
		add(jButton2);
		//
//		Insert_ScheduleStudyTime Insert_ScheduleStudyTime = new Insert_ScheduleStudyTime();
		jButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
//				기존 insert
//				try {
//					jTextArea1.setText("");
//				
//					s.get_textfield_schedulestudy = jtextfield1.getText();
//				
//					System.out.println(s.get_textfield_schedulestudy);
//					
//					jTextArea1.append(s.get_textfield_schedulestudy+"\n");
//					
//					jtextfield1.setText(null);
//					
//					String[] array = s.get_textfield_schedulestudy.split("/");
//					
//					String serialNum = array[0];
//					
//					String studytime = array[1];
//					
//					String date = array[2];
//
//					String subject = array[3];
//
//
//					Insert_ScheduleStudyTime.insert(serialNum, studytime, date, subject, jTextArea1);
//
//					c.addItem(s.get_textfield_schedulestudy);
//					//UPLoad를 다시 실행하여 테이블을 새로고침 한다
//					uploadJtable();
//
//				} catch (Exception e2) {
//					// TODO: handle exception
//					e2.printStackTrace();
//					jTextArea1.append("실패했습니다. 값을 다시 한번 확인해 주세요.");
//					JOptionPane.showMessageDialog(null, "실패했습니다. 값을 확인해주세요", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
//				}
				
				try {
                    while (true) {
                       serialnum = JOptionPane.showInputDialog("시리얼 넘버를 입력하세요.");
                       if (serialnum.contentEquals("")) {
                          JOptionPane.showMessageDialog(null, "공백입니다.");
                          continue;
                       }
                       while (true) {
                          studytime = JOptionPane.showInputDialog("공부시간을 입력하세요.");
                          if (studytime.contentEquals("")) {
                             JOptionPane.showMessageDialog(null, "공백입니다.");
                             continue;
                          }
                          break;
                       }
                       while (true) {
                          date = JOptionPane.showInputDialog("날짜를 입력하세요.\n ex) 2020-02-02");
                          if (date.contentEquals("")) {
                             JOptionPane.showMessageDialog(null, "공백입니다.");
                             continue;
                          }
                          break;
                       }
                       while (true) {
                           subject = JOptionPane.showInputDialog("과목 수를 입력하세요.");
                           if (subject.contentEquals("")) {
                              JOptionPane.showMessageDialog(null, "공백입니다.");
                              continue;
                           }
                           break;
                        }
                       break;
                    }
                    String sumsum = serialnum+"/"+studytime+"/"+date+"/"+subject;
                    System.out.println(sumsum);
                    
    				Insert_ScheduleStudyTime.insert(serialnum, studytime, date, subject, jTextArea1);
    				c.addItem(sumsum);

                    uploadJtable();
                    
                 } catch (Exception k) {

                 }
				
			}
	      });
		//

		jButton3 = new JButton(new ImageIcon("image/DL.PNG"));
		jButton3.setSize(250, 60);
		jButton3.setLocation(820, 560);
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
	          //UPLoad를 다시 실행하여 테이블을 새로고침 한다
				uploadJtable();
	         }
	      });
		//

		jButton4 = new JButton(new ImageIcon("image/clbtn.PNG"));
		jButton4.setSize(170, 40);
		jButton4.setLocation(65, 400);
		//add(jButton4);
		//
		jButton4.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 jTextArea1.setText("");

	         }
	      });
		//
		////////		안보이게 Location옮김
		jTextArea1 = new JTextArea();
		jScrollPane1 = new JScrollPane(jTextArea1);
		jScrollPane1.setSize(300, 320);
		jScrollPane1.setLocation(2300, 120);
		jTextArea1.setFont(new Font("나눔고딕",Font.BOLD,20));
		add(jScrollPane1);

		jtextfield1 = new JTextField();
		jtextfield1.setSize(300, 40);
		jtextfield1.setLocation(700, 680);
		jtextfield1.setFont(new Font("나눔고딕",Font.BOLD,20));
//		add(jtextfield1);
		
//		jtextfield2 = new JTextField();
//		jtextfield2.setSize(150, 40);
//		jtextfield2.setLocation(505, 650);
//		add(jtextfield2);
//		
//		jtextfield3 = new JTextField();
//		jtextfield3.setSize(150, 40);
//		jtextfield3.setLocation(710, 650);
//		add(jtextfield3);
//		
//		jtextfield4 = new JTextField();
//		jtextfield4.setSize(150, 40);
//		jtextfield4.setLocation(945, 650);
//		add(jtextfield4);

	}
	
	void uploadJtable() {
    	Select_ScheduleStudyTime sP = new Select_ScheduleStudyTime();
//    	sP.initValue();
    	sP.loadScheduleStudytime();
        //Object[][] pro_obj= new Object[s.serialnum_singleTon.size()+1][3];
        Object[][] real_obj= new Object[s.SelectScheduleserialnum_singleTon.size()+2][4];
//        table = new JTable(pro_obj,columnType);

        int count =0;

        System.out.println("columnType.length : "+columnType.length);
        for(int i=0; i<columnType.length; i++)
        {
        	real_obj[TABLE_MENU][i] = columnType[i];
        }

        for (int i = 0; i < s.SelectScheduleserialnum_singleTon.size(); i++) {
        	real_obj[i+1][TABLE_SERIALNUM]=s.SelectScheduleserialnum_singleTon.get(i);
        	real_obj[i+1][TABLE_PHONENUM]=s.SelectSchedulestudytime_singleTon.get(i);
        	real_obj[i+1][TABLE_DATE]=s.SelectScheduledate_singleTon.get(i);
        	real_obj[i+1][TABLE_SUBJECT]=s.SelectSchedulesubject_singleTon.get(i);
        	//count++;
//        	System.out.println("s.serialnum_singleTon.get(i) : " + s.SelectRealserialnum_singleTon.get(i));
        };
//        count++;
//        if(DBGTest == false) {
//	        for(int i=0; i<columnType.length; i++)
//	        {
//	        	pro_obj[count][i] = columnType[i];
//	        }
//	        DBGTest = true;
//        }
		if(table != null)
		{
			System.out.println("table != null");
			remove(table);
	        revalidate();
	        repaint();
		}
        table = new JTable(real_obj,columnType);
//		JTable table = new JTable(pro_obj,columnType);
        table.setRowHeight(25);
        table.setSize(650,500);
        table.setLocation(110,120);
        //
     // DefaultTableCellHeaderRenderer 생성 (가운데 정렬을 위한)
        DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
        // DefaultTableCellHeaderRenderer의 정렬을 가운데 정렬로 지정
        tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);      
        // 정렬할 테이블의 ColumnModel을 가져옴
        TableColumnModel tcmSchedule = table.getColumnModel();
        // 반복문을 이용하여 테이블을 가운데 정렬로 지정
        for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
        tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
        }
        //
        table.setFont(new Font("나눔고딕",Font.BOLD,24));
        add(table);
        revalidate();
        repaint();
        
        
    }

}

public class EpStdMenu {

}
