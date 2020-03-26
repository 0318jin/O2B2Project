package o2b2;


public class MainFrame {
		
		public static void main(String [] args) {
			
			new MainSetting();
			o2b2.MenuBar mb  = o2b2.MenuBar.getIntance();
			mb.MenuBar();
//			new Graph_Db();
			
			
			And_Server mAnd_Server = new And_Server();
			mAnd_Server.start();
			
			Rasp_Main mRasp_Main = new Rasp_Main();
			mRasp_Main.start();
	
		}
}
