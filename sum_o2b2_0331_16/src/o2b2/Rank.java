package o2b2;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class Rank extends JPanel {
	
	JTable table;
	JScrollPane scroll;
	
	SingleTon s = SingleTon.getInstanse();
	
	void rank() {
    	Select_RealStudyTime sP = new Select_RealStudyTime();
    	sP.loadRealStudyTime();
    	
		Object[][] rank_obj = new Object[s.SelectRealserialnum_singleTon.size()][];
		
		for (int i = 0; i < s.SelectRealserialnum_singleTon.size(); i++) {
			rank_obj[0][i] = i+1;
		}
		
		if(table != null)
		{
			System.out.println("table != null");
			remove(table);
	        revalidate();
	        repaint();
		}
		table = new JTable();
		
		table.setRowHeight(25);
        table.setSize(650,500);
        table.setLocation(10,10);
	}
}
