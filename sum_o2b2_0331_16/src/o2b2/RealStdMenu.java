package o2b2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Menu;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
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

class JPanel04 extends JPanel {

	private JButton jButton1;
	private JButton jButton2;
	private JButton jButton3;
	private JButton jButton4;

	private JLabel jlabel1;

	private JTextField jtextfield1;

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
    final String[] columnType = {"�ܸ���ȣ", "���νð�(��)", "��¥", "�����"};
    static boolean DBGTest = false;
    
    // Insert���� ����ϴ� �ʵ�
    String serialnum = null;
    String studytime = null;
    String date = null;
    String subject = null;

	public JPanel04(JPanelTest win) {

		SingleTon s = SingleTon.getInstanse();
		
		 ImageIcon img2 = new ImageIcon("image/book3.png");  //�̹��� ���
	    JLabel imagelJLabel2 = new JLabel (img2);     
	    imagelJLabel2.setSize(200,200);
	    imagelJLabel2.setLocation(860,80);
	    add(imagelJLabel2);
		
		ImageIcon img = new ImageIcon("image/oba-study2.jpg"); // �̹��� ���
		JLabel imagelJLabel = new JLabel(img);
		imagelJLabel.setSize(170, 60);
		imagelJLabel.setLocation(1000, 650);

		add(imagelJLabel);
		
		ImageIcon img1 = new ImageIcon("image/real1.png");  //�̹��� ���
	    JLabel imagelJLabel1 = new JLabel (img1);     
	    imagelJLabel1.setSize(300,60);
	    imagelJLabel1.setLocation(280,45);
	    
	    add(imagelJLabel1);
		setVisible(true); // ȭ�鿡 ���̱�

		setLayout(null);
		///////////////	â ���Խ� �ٷ� ǥ�� �߰� �ϴ� �κ�.
		uploadJtable();
		
		
		jButton1 = new JButton(new ImageIcon("image/UP.PNG"));
		jButton1.setSize(250, 60);
		jButton1.setLocation(820, 310);
		//
		jButton1.setBorderPainted(false);
	     //JButton�� Border(�ܰ���)�� �����ش�.   
		jButton1.setContentAreaFilled(false);
	     //JButton�� ���뿵�� ä��� ����
		jButton1.setFocusPainted(false);
		add(jButton1);
		//
		jButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JOptionPane.showMessageDialog(null,  "���ΰ�ħ �Ǿ����ϴ�.");
//				jTextArea1.setText("");
				uploadJtable();
			}
		});
		//

		jButton2 = new JButton(new ImageIcon("image/inbtn.PNG"));
		jButton2.setSize(250, 60);
		jButton2.setLocation(820, 435);
		add(jButton2);
		//
		jButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
                    while (true) {
                       serialnum = JOptionPane.showInputDialog("�ø��� �ѹ��� �Է��ϼ���.");
                       if (serialnum.contentEquals("")) {
                          JOptionPane.showMessageDialog(null, "�����Դϴ�.");
                          continue;
                       }
                       while (true) {
                          studytime = JOptionPane.showInputDialog("���νð��� �Է��ϼ���.");
                          if (studytime.contentEquals("")) {
                             JOptionPane.showMessageDialog(null, "�����Դϴ�.");
                             continue;
                          }
                          break;
                       }
                       while (true) {
                          date = JOptionPane.showInputDialog("��¥�� �Է��ϼ���. \n ex) 2020-02-02");
                          if (date.contentEquals("")) {
                             JOptionPane.showMessageDialog(null, "�����Դϴ�.");
                             continue;
                          }
                          break;
                       }
                       while (true) {
                           subject = JOptionPane.showInputDialog("���� ���� �Է��ϼ���.");
                           if (subject.contentEquals("")) {
                              JOptionPane.showMessageDialog(null, "�����Դϴ�.");
                              continue;
                           }
                           break;
                        }
                       break;
                    }
                    String sumsum = serialnum+"/"+studytime+"/"+date+"/"+subject;
                    System.out.println(sumsum);
                    
    				Insert_RealStudyTime.insert(serialnum, studytime, date, subject, jTextArea1);
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

					String sql = "SELECT * FROM realstudytime ORDER BY serialnum asc;";

					rs = stmt.executeQuery(sql);

					while (rs.next()) {
						// ���ڵ��� Į���� �迭�� �޸� 0���� �������� �ʰ� 1���� �����Ѵ�.
						// �����ͺ��̽����� �������� �������� Ÿ�Կ� �°� getString �Ǵ� getInt ���� ȣ���Ѵ�.
						String serialnum = rs.getString(1);
						s.SelectRealserialnum_singleTon.add(rs.getString(1));
						String studytime = rs.getString(2);
						s.SelectRealstudytime_singleTon.add(rs.getString(2));
						String date = rs.getString(3);
						s.SelectRealdate_singleTon.add(rs.getString(3));
						String subject = rs.getString(4);
						s.SelectRealsubject_singleTon.add(rs.getString(4));

						s.realstudytimeval = serialnum + "/" + studytime + "/" + date + "/" + subject + "\n";
						
						System.out.println("�����͵�޴�: "+ s.realstudytimeval);
						
						test.add(s.realstudytimeval);
					}

				} catch (Exception e1) {

				}

				String[] b = new String[test.size()];

				for (int i = 0; i < test.size(); i++) {
					b[i] = test.get(i);
				}

				Object selected = JOptionPane.showInputDialog(null, "�����ϼ���.", "test", JOptionPane.QUESTION_MESSAGE,
						null, b, b[0]);

				if (selected == null) {
					JOptionPane.showMessageDialog(null, "������ ����Ͽ����ϴ�.");
				} else {
					JOptionPane.showMessageDialog(null, selected + "�� �����ϼ̽��ϴ�.");
				}

				if(selected != null) {
					String[] array = String.valueOf(selected).split("/");
					String serialNum = array[0];
					String studytime = array[1];
					String date = array[2];
					String subject = array[3];
					System.out.println(serialNum+"/"+studytime+"/"+date+"/"+subject+"/");
					Delete_RealStudyTime.delete(serialNum, studytime, date, subject, jTextArea1);
					c.removeItem(selected);
					
					uploadJtable();
				}
			}
		});
		//

		jButton4 = new JButton(new ImageIcon("image/clbtn.PNG"));
		jButton4.setSize(170, 40);
		jButton4.setLocation(65, 400);
		//add(jButton4);
		jButton4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jTextArea1.setText("");
			}
		});
		//
		///////�Ⱥ��̰� Location�ű�
		jTextArea1 = new JTextArea();
		jScrollPane1 = new JScrollPane(jTextArea1);
		jScrollPane1.setSize(300, 320);
		jScrollPane1.setLocation(2300, 120);
		jTextArea1.setFont(new Font("�������",Font.BOLD,20));
		add(jScrollPane1);

		jtextfield1 = new JTextField();
		jtextfield1.setSize(300, 40);
		jtextfield1.setLocation(700, 680);
		jtextfield1.setFont(new Font("�������",Font.BOLD,20));
	}
		void uploadJtable() {
	    	Select_RealStudyTime sP = new Select_RealStudyTime();
	    	sP.loadRealStudyTime();
	        Object[][] real_obj= new Object[s.SelectRealserialnum_singleTon.size()][4];

	        System.out.println("columnType.length : "+columnType.length);
	        ////
			if (s.SelectRealserialnum_singleTon.size() > 0) {
				for (int i = 0; i < columnType.length; i++) {
					real_obj[TABLE_MENU][i] = columnType[i];
				}
			}
	        

	        for (int i = 0; i < s.SelectRealserialnum_singleTon.size(); i++) {
	        	real_obj[i][TABLE_SERIALNUM]=s.SelectRealserialnum_singleTon.get(i);
	        	real_obj[i][TABLE_PHONENUM]=s.SelectRealstudytime_singleTon.get(i);
	        	real_obj[i][TABLE_DATE]=s.SelectRealdate_singleTon.get(i);
	        	real_obj[i][TABLE_SUBJECT]=s.SelectRealsubject_singleTon.get(i);
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
	        Theader.setFont(new Font("�������", Font.BOLD, 25));
	        table.setRowHeight(25);
	        table.setSize(650,500);
	        table.setLocation(110,120);
	        //
	     // DefaultTableCellHeaderRenderer ���� (��� ������ ����)
	        DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
	        // DefaultTableCellHeaderRenderer�� ������ ��� ���ķ� ����
	        tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);      
	        // ������ ���̺��� ColumnModel�� ������
	        TableColumnModel tcmSchedule = table.getColumnModel();
	        // �ݺ����� �̿��Ͽ� ���̺��� ��� ���ķ� ����
	        for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
	        tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
	        }

	        table.setFont(new Font("�������",Font.BOLD,24));
	        scroll= new JScrollPane(table);
	        scroll.getRowHeader();
	        scroll.setSize(650,500);
	        scroll.setLocation(110,120);
	        add(scroll);

	        revalidate();
	        repaint();
	        
	        
	    }
		
		

}

