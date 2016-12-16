package crawler;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Crawler {

	// assign the database
	public static DataHouse db = new DataHouse();

	public static void startingPoint(String URI, String searchFor) throws SQLException, IOException {
		// Check whether the current URI is already in the database
		String sql = "SELECT * FROM links WHERE URI = '" + URI + "'";
		ResultSet results = db.stringQuery(sql);
		if (results.next()) {
			// If it returns true, it means it was in the database and is not
			// worth parsing again
		} else {
			// Start parsing
			
			
		}
	}
}