package controllers.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectorDB
{
	static
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public Connection getConnection(String server, String database, String user, String password) throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://"+server+"/"+database+"?user="+user+"&"+"password="+password);
	}
}
