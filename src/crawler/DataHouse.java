package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataHouse {

	// Declare the connection as null
	public Connection protocol = null;

	public DataHouse() {

		// Try to connect to database
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://krl.ddns.net:3306/Java";
			System.out.println("Trying to connect MySQL server at krl.ddns.net (my home)");
			long started = System.nanoTime();
			
			// Declare the user credentials, which I humbly call protocol
			protocol = DriverManager.getConnection(url, "Java", "XHBjTP7k4ahTEvjc");
			long elapsed = System.nanoTime() - started;
			System.out.println(
					"The connection to the database has been sucessfully made in " + elapsed + " nanoseconds!\n");
		} catch (SQLException excep) {
			excep.printStackTrace();
		} catch (ClassNotFoundException ecnt) {
			ecnt.printStackTrace();
		}
	}

	// Create a method for queries with data results (SELECT etc)
	public ResultSet stringQuery(String sqlResult) throws SQLException {
		Statement statmnt = protocol.createStatement();
		// Return the results in a set (for example table rows)
		return statmnt.executeQuery(sqlResult);
	}
	
	// Create a method for queries with true/false results
	public boolean boolQuery(String sqlResult) throws SQLException {
		Statement statmnt = protocol.createStatement();
		// Return a true or false value (the query ran or did not)
		return statmnt.execute(sqlResult);
	}

	@Override
	protected void finalize() throws Throwable {
		if (protocol != null || !protocol.isClosed()) {
			// Close the protocol if it has not been already closed
			protocol.close();
		}
	}
}

