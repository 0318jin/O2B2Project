package o2b2;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;

//import AssistantsGUI.MenuBarAction.myActionListener;


public class MenuBar extends JFrame{
	static private MenuBar menubar = null;
	
	private MenuBar() {
		
	}
	
	public static MenuBar getIntance() {
		if(menubar == null)
			menubar = new MenuBar();
		return menubar;
	}

	void MenuBar() {
		setTitle("Study Assistants 관리자 페이지");
		createMenu(); // 메뉴 생성, 프레임에 삽입
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setSize(800,600);
		setLocation(550, 200);
		setVisible(true);
		
	}
	
	JMenuBar MenuBar1 = new JMenuBar();   // 메뉴바의 내용
	
	JMenu screenMenu1 = new JMenu("File"); 
	JMenu screenMenu2 = new JMenu("회원정보");
	JMenu screenMenu3 = new JMenu("사용자");
	JMenu screenMenu4 = new JMenu("데이터");
	

 
	void createMenu() {

		JMenuItem[] mListItem1 = new JMenuItem[3];  // 메뉴별 내용 배열로
		JMenuItem[] mListItem2 = new JMenuItem[2];
		JMenuItem[] mListItem3 = new JMenuItem[2];
		JMenuItem[] mListItem4 = new JMenuItem[2];

		mListItem1[0] = new JMenuItem("SOCKET ON");
		mListItem1[0].setName("SOCKET ON");
		mListItem1[1] = new JMenuItem("Home");
		mListItem1[1].setName("Home");
		mListItem1[2] = new JMenuItem("Exit");
		mListItem1[2].setName("Exit");
		
		mListItem2[0] = new JMenuItem("정보");
		mListItem2[0].setName("정보");
		mListItem2[1] = new JMenuItem("관리자");
		mListItem2[1].setName("관리자");

		mListItem3[0] = new JMenuItem("계획 학습시간");
		mListItem3[0].setName("계획 학습시간");
		mListItem3[1] = new JMenuItem("실제 학습시간");
		mListItem3[1].setName("실제 학습시간");
		
		mListItem4[0] = new JMenuItem("그래프");
		mListItem4[0].setName("그래프");
		mListItem4[1] = new JMenuItem("평가");
		mListItem4[1].setName("평가");
		
		//MenuBarAction mb = new MenuBarAction();
		
		for(int i=0; i< mListItem1.length; i++) {
			screenMenu1.add(mListItem1[i]);
			mListItem1[i].addActionListener(new myActionListener());
		}
		
		for(int i=0; i< mListItem2.length; i++) {
			screenMenu2.add(mListItem2[i]);
			mListItem2[i].addActionListener(new myActionListener());
		}
		
		for(int i=0; i< mListItem3.length; i++) {
			screenMenu3.add(mListItem3[i]);
			mListItem3[i].addActionListener(new myActionListener());
		}
		
		for(int i=0; i< mListItem4.length; i++) {
			screenMenu4.add(mListItem4[i]);
			mListItem4[i].addActionListener(new myActionListener());
		}
		

		
		setJMenuBar(MenuBar1);  // menubar 보이기
		
		MenuBar1.add(screenMenu1);
		MenuBar1.add(screenMenu2);
		MenuBar1.add(screenMenu3);
		MenuBar1.add(screenMenu4);
		///
		MenuBar1.setVisible(false);
		
		///
		homemain jp6 = new homemain(null);
		jp6.setBackground(new Color(50, 133, 187));
		getContentPane().removeAll();
		getContentPane().add(jp6);
		revalidate();
		repaint();
		
	}
	
	void setVisible() {
		MenuBar1.setVisible(true);
	}
	class myActionListener implements ActionListener{  // 메뉴바에서 메뉴를 고를때 액션

		
		
		@Override
		public void actionPerformed(ActionEvent e) {
<<<<<<< HEAD
			JMenuItem m = (JMenuItem)e.getSource();

//			MenuBarAction mb = new MenuBarAction();
//			mb.BtnMenuBarACT(e);

			//System.out.print("getID : " + m.getName());  // 아이디 프린트 하기
			
			
=======
			JMenuItem m = (JMenuItem)e.getSource();			
>>>>>>> cb75a622b72300560e6336086be7e100c56d8c03

			//////////////////////// file 메뉴바 /////////////////////////
			if(m.getName().equals("SOCKET ON"))
			{
				JPanel01 jp1 = new JPanel01(null);  // 중요
					//
					
		            getContentPane().removeAll();
		            getContentPane().add(jp1);
		            jp1.setBackground(new Color(50, 133, 187));
		        
		            revalidate();
		            repaint();
			}
			
			if(m.getName().equals("Exit"))
			{
					System.exit(0);
			}

			if(m.getName().equals("정보"))
			{
					JPanel02 jp2 = new JPanel02(null);
					jp2.setBackground(new Color(50, 133, 187));
		            getContentPane().removeAll();
		            getContentPane().add(jp2);
		            revalidate();
		            repaint();
			}
			
			/////////////////////////////// 사용자 메뉴바 ///////////////////////////////
			if(m.getName().equals("계획 학습시간"))
			{
					JPanel03 jp3 = new JPanel03(null);
					jp3.setBackground(new Color(50, 133, 187));
		            getContentPane().removeAll();
		            getContentPane().add(jp3);
		            revalidate();
		            repaint();
			}
			
			if(m.getName().equals("실제 학습시간"))
			{
					JPanel04 jp4 = new JPanel04(null);
					jp4.setBackground(new Color(50, 133, 187));
		            getContentPane().removeAll();
		            getContentPane().add(jp4);
		            revalidate();
		            repaint();
			}
			
			if(m.getName().equals("그래프"))
			{	
					try {
//						Select_RealStudyTime srt = new Select_RealStudyTime();
						ResultPanel jp5 = new ResultPanel();
						jp5.setBackground(new Color(50, 133, 187));
						getContentPane().removeAll();
			            getContentPane().add(jp5);
			            revalidate();
			            repaint();
			            
					} catch (Exception e2) {
						// TODO: handle exception
					}

			}
			
			if(m.getName().equals("Home"))
			{
					homemain jp6 = new homemain(null);
					jp6.setBackground(new Color(50, 133, 187));
					getContentPane().removeAll();
					getContentPane().add(jp6);
					revalidate();
					repaint();
			}
			
//			if(m.getName().equals("관리자"))
//			{
//					JPanel07 jp7 = new JPanel07(null);
//					jp7.setBackground(new Color(50, 133, 187));
//					getContentPane().removeAll();
//					getContentPane().add(jp7);
//					revalidate();
//					repaint();
//			}
					
		}
	}		
}

