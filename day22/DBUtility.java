package day22;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBUtility 
{
	private static Properties prop;
	static
	{
		try
		{
			prop=new Properties();
			prop.load(new FileInputStream("dbconnection.properties"));
			Class.forName(prop.getProperty("driver"));
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static ThreadLocal<Connection> tlocal=new ThreadLocal<Connection>();
	public synchronized static Connection getConnection()
	{
		Connection conn=tlocal.get();
		if(conn==null)
		{
			try
			{
				conn=DriverManager.getConnection(prop.getProperty("url"),prop.getProperty("username"),prop.getProperty("password"));
				conn.setAutoCommit(false);
				tlocal.set(conn);
			}
			catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			
		}
		return conn;
	}
	public synchronized static void closeConnection(Exception e)
	{
		Connection conn=tlocal.get();
		try
		{
		if(conn!=null)
		{
			if(e==null)
			{
				conn.commit();
			}
			else
			{
				conn.rollback();
				conn.commit();
			}
			tlocal.remove();
			conn.close();
		}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		
	}
	
}
