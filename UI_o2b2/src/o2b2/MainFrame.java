package o2b2;

public class MainFrame {
		
		public static void main(String [] args) {
			

			new MainSetting();
<<<<<<< HEAD
			new MenuBar();
//			new Graph_Db();
			
			
=======
			o2b2.MenuBar mb  = o2b2.MenuBar.getIntance();
			mb.MenuBar();

>>>>>>> cb75a622b72300560e6336086be7e100c56d8c03
			And_Server mAnd_Server = new And_Server();
			mAnd_Server.start();
			
			Rasp_Main mRasp_Main = new Rasp_Main();
			mRasp_Main.start();
	
		}
}
