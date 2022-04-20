package com.jersey.dbconn;



import java.sql.Connection;
import java.sql.DriverManager;

public final class DbConnectionProvider

{
	
	static Connection conn=null;
	private DbConnectionProvider()
	{}
	
	public synchronized static Connection getConnection()
	{
		if(conn==null) {
			try {
				Class.forName(DbConnectionInfo.DRIVER_NAME);
				
				conn=DriverManager.getConnection(DbConnectionInfo.DRIVER_URL,DbConnectionInfo.USER_NAME,DbConnectionInfo.USER_PASS);
			}catch(Exception e) {e.printStackTrace();}
			return conn;
		}
		else {return conn;}
	}

}
