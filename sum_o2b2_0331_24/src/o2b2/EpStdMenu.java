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
import java.util.ArrayList;

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
import javax.swing.table.JTableHeader;
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
	JScrollPane scroll;
	
	static final int TABLE_MENU = 0;
	
	static final int TABLE_SERIALNUM = 0;
	static final int TABLE_PHONENUM = 1;
	static final int TABLE_DATE = 2;
	static final int TABLE_SUBJECT = 3;
    final String[] columnType = {"단말번호", "공부시간(h)", "날짜", "과목수"};
    static boolean DBGTest = false;
    
    // Insert에서 사용하는 필드
    String serialnum = null;
    String studytime = null;
    String date = null;
    String subject = null;
    
	public void init() {


	}

	public JPanel03(JPanelTest win) {
		
		
		SingleTon s = SingleTon.getInstanse();
		
	    ImageIcon img2 = new ImageIcon("image/book5.png");  //이미지 경로
	    JLabel imagelJLabel2 = new JLabel (img2);     
	    imagelJLabel2.setSize(200,200);
	    imagelJLabel2.setLocation(860,80);
	    add(imagelJLabel2);

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

		jButton1 = new JButton(new ImageIcon("image/UP.PNG"));
		jButton1.setSize(250, 60);
		jButton1.setLocation(820, 310);
		add(jButton1);

		jButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JOptionPane.showMessageDialog(null,  "새로고침 되었습니다.");
				jTextArea1.setText("");
				Select_ScheduleStudyTime mS = new Select_ScheduleStudyTime();
				mS.loadScheduleStudytime(jTextArea1);
				uploadJtable();
			}
		});


		jButton2 = new JButton(new ImageIcon("image/inbtn.PNG"));
		jButton2.setSize(250, 60);
		jButton2.setLocation(820, 435);
		add(jButton2);
		//
//		Insert_ScheduleStudyTime Insert_ScheduleStudyTime = new Insert_ScheduleStudyTime();
		jButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
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

					Connection conn = null;
					Statement stmt = null;
					ResultSet rs = null;

					ArrayList<String> test = new ArrayList<String>();

					try {

						Class.forName("com.mysql.jdbc.Driver");

						conn = DriverManager.getConnection("jdbc:mysql://localhost:3309/o2b2", "root", "1234");

						stmt = conn.createStatement();

						String sql = "SELECT * FROM schedulestudytime ORDER BY serialnum asc;";

						rs = stmt.executeQuery(sql);

						while (rs.next()) {
							//레코드의 칼럼은 배열과 달리 0부터 시작하지 않고 1부터 시작한다.
							//데이터베이스에서 가져오는 데이터의 타입에 맞게 getString 또는 getInt 등을 호출한다.
							String serialnum = rs.getString(1);
							s.SelectScheduleserialnum_singleTon.add(rs.getString(1));
							String studytime = rs.getString(2);
							s.SelectSchedulestudytime_singleTon.add(rs.getString(2));
							String date = rs.getString(3);
							s.SelectScheduledate_singleTon.add(rs.getString(3));
							String subject = rs.getString(4);
							s.SelectSchedulesubject_singleTon.add(rs.getString(4));
					
							s.schedulestudytimeval = serialnum + "/" + studytime + "/" + date + "/"+ subject + "\n";
							test.add(s.schedulestudytimeval);
						}
					} catch (Exception e1) {

					}

					String[] b = new String[test.size()];

					for (int i = 0; i < test.size(); i++) {
						b[i] = test.get(i);
					}

					Object selected = JOptionPane.showInputDialog(null, "선택하세요.", "Delete", JOptionPane.QUESTION_MESSAGE,
							null, b, b[0]);

					if (selected == null) {
						JOptionPane.showMessageDialog(null, "선택을 취소하였습니다.");
					} else {
						JOptionPane.showMessageDialog(null, selected + "을 선택하셨습니다.");
					}

					if(selected != null) {
	         	String[] array = String.valueOf(selected).split("/");
	         	String serialNum = array[0];
	       		String studytime = array[1];
	       		String date = array[2];
	       		String subject = array[3];
	       		
	          	Delete_ScheduleStudyTime.delete(serialNum, studytime, date, subject, jTextArea1);
	          	c.removeItem(selected);
	          //UPLoad를 다시 실행하여 테이블을 새로고침 한다
				uploadJtable();
					}
	         }
	      });

		jButton4 = new JButton(new ImageIcon("image/clbtn.PNG"));
		jButton4.setSize(170, 40);
		jButton4.setLocation(65, 400);
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


	}
	
	void uploadJtable() {
    	Select_ScheduleStudyTime sP = new Select_ScheduleStudyTime();
    	sP.loadScheduleStudytime();
        Object[][] real_obj= new Object[s.SelectScheduleserialnum_singleTon.size()][4];
        
		if (s.SelectScheduleserialnum_singleTon.size() > 0) {
			for (int i = 0; i < columnType.length; i++) {
				real_obj[TABLE_MENU][i] = columnType[i];
			}
		}
        

        for (int i = 0; i < s.SelectScheduleserialnum_singleTon.size(); i++) {
        	real_obj[i][TABLE_SERIALNUM]=s.SelectScheduleserialnum_singleTon.get(i);
        	real_obj[i][TABLE_PHONENUM]=s.SelectSchedulestudytime_singleTon.get(i);
        	real_obj[i][TABLE_DATE]=s.SelectScheduledate_singleTon.get(i);
        	real_obj[i][TABLE_SUBJECT]=s.SelectSchedulesubject_singleTon.get(i);
        };

		if(table != null)
		{
			System.out.println("table != null");
			remove(scroll);
	        revalidate();
	        repaint();
		}
        table = new JTable(real_obj,columnType);
        
        JTableHeader Theader = table.getTableHeader();
        Color color = new Color(241,196,21);
        Theader.setBackground(color);
        Theader.setFont(new Font("나눔고딕", Font.BOLD, 25));
        
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
        scroll= new JScrollPane(table);

        scroll.setSize(650,500);
        scroll.setLocation(110,120);
        add(scroll);
        revalidate();
        repaint();
        
        
    }

}
