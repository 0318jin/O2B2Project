package o2b2;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class And_Dialog {
	void Connect(String Address){
		JOptionPane.showMessageDialog(null,  "안드로이드 App Ip : " + Address + " 로부터 접속했습니다.");
	}
	void Disconnect(String Address) {
		JOptionPane.showMessageDialog(null,  "안드로이드 App Ip : " + Address + " 접속이 끊겼습니다.");
	}
}