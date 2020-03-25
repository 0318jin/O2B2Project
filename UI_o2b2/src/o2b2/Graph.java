package o2b2;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

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
		g.drawLine(50,  400,  700,  400);
		
		for (int i = 1; i< 11 ; i++) {
			g.drawString(i*2 + "", 25, 400-(35*i));
			g.drawLine(50,  400-(35*i), 700, 400-(35*i));
		}
		
		g.drawLine(50, 20, 50, 400);
		g.setColor(Color.BLUE);
		
		int x_data=70;
		int x_p =65;
		for(int i=0; i< s.mListGraph.size(); i++)
		{
			g.drawString((i+1) + "ÀÏÂ÷", x_p, 430);
			g.fillRect(x_data, 400 - s.mListGraph.get(i)*20, 10, s.mListGraph.get(i)*20);
			x_data+=100;
			x_p +=100;
		}
	}
}


public class Graph {

}
