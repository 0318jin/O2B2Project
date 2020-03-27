package o2b2;

import java.util.ArrayList;

public class And_SingUp {
   static private And_SingUp mSingup = null;
   ArrayList<String> Arr_Singup = new ArrayList<String>();

   private And_SingUp() {

   }

   public static And_SingUp getInstance() {
      if (mSingup == null)
         mSingup = new And_SingUp();
      return mSingup;
   }

   void singup_start() {
      try {

         And_SocketThread mSocketThread = And_SocketThread.get();

         System.out.println("회원가입 시작!");
         mSocketThread.write("Sinp Up button pushed");

         String readData = null;

         while (true) {
            try {
               readData = mSocketThread.readData();
               if (readData != null) { // 값을받아 null이 아니면 break;
                  Arr_Singup.add(readData);
                  break;
               }
               Thread.sleep(100);
            } catch (Exception e) {

            }
         }
         mSocketThread.write("get Serial Num");

         readData = null;

         while (true) {
            try {
               readData = mSocketThread.readData();
               if (readData != null) { // 값을받아 null이 아니면 break;
                  Arr_Singup.add(readData);
                  break;
               }
               Thread.sleep(100);
            } catch (Exception e) {

            }
         }
         mSocketThread.write("get Phone Num");

         readData = null;

         while (true) {
            try {
               readData = mSocketThread.readData();
               if (readData != null) { // 값을받아 null이 아니면 break;
                  Arr_Singup.add(readData);
                  break;
               }
               Thread.sleep(100);
            } catch (Exception e) {

            }

         }
         mSocketThread.write("get PassWord Num");
         Thread.sleep(200);
         System.out.println("회원가입 완료");
         for (int i = 0; i < Arr_Singup.size(); i++) {
            System.out.println("Android Client 에서 받은 값 : " + Arr_Singup.get(i));
         }
//         And_DBManager mDB = And_DBManager.getInstance();
//         mDB.Insert(Arr_Singup.get(0), Arr_Singup.get(1), Arr_Singup.get(2));
         
         And_DB_SingUp.insert(Arr_Singup.get(0), Arr_Singup.get(1), Arr_Singup.get(2));;

      } catch (Exception e) {
      }

   }
}