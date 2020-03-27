package o2b2;

import java.net.*;
import java.io.*;
import java.sql.*;

public class And_MainServer {

	public static void main(String[] args) {
		And_Server mAnd_Server = new And_Server();
		mAnd_Server.start();
		
		And_DB_Chart mADC = And_DB_Chart.getInstance();
		
	}
}