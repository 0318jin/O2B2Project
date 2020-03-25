package o2b2;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JButton;
import javax.swing.JFrame;


public class SubMain extends JFrame{
	
	private Image img;
	
    public void paintComponent(Graphics g) {
    	this.img = img;
    	
    	g.drawImage(img, 50, 50, 100, 100, 100, 100, 100, 100, null);
	}

}
