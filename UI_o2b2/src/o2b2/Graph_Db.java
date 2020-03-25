package o2b2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Graph_Db extends JFrame {
	
	public Graph_Db() {
		super("학습시간 분포표");
		
		buildGUI();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(500,200);
		setSize(600,600);
		
		setVisible(true);
	}
	
	private void buildGUI() {
		setLayout(new BorderLayout());

		Container contentpane = getContentPane();
		ResultPanel resultPanel = new ResultPanel();
        //그래프를 그릴 패널
		
		contentpane.add(resultPanel, BorderLayout.CENTER);
		
	}

	//결과물(그래프)이 나타날 패널
	class ResultPanel extends JPanel{
		SingleTon s = SingleTon.getInstanse();


		public void paint(Graphics g) {
			///
			SingleTon mS =SingleTon.getInstanse();
			ArrayList<Integer> m = new ArrayList<Integer>();
			
			for (int i = 7; i > 0; i--) {
				m.add(Integer.parseInt(mS.SelectRealstudytime_singleTon.get(mS.SelectRealstudytime_singleTon.size() - i)));
			}

			mS.setGraph(m);
			///
			
			g.clearRect(0,0,getWidth(),getHeight());
			g.drawLine(50,  250,  500,  250);
			
			for (int i = 1; i< 11 ; i++) {
				g.drawString(i*10 + "", 25, 255-(20*i));
				g.drawLine(50,  250-(20*i), 500, 250-(20*i));
			}
			
			g.drawLine(50, 20, 50, 250);
			g.setColor(Color.BLUE);
			
			int x_data=70;
			int x_p =65;
			for(int i=0; i< s.mListGraph.size(); i++)
			{
				g.drawString((i+1) + "일차", x_p, 270);
				g.fillRect(x_data, 250 - s.mListGraph.get(i)*2, 10, s.mListGraph.get(i)*2);
				x_data+=40;
				x_p +=40;
			}
		}
	}
}