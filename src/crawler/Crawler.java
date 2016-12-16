package crawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import crawler.DataHouse;

public class Crawler {

	// assign the database
	public static DataHouse db = new DataHouse();

	public static void startingPoint(String URI, String searchFor) throws SQLException, IOException {
		// Check whether the current URI is already in the database
		String sql = "SELECT * FROM links WHERE URI = '" + URI + "'";
		ResultSet results = db.stringQuery(sql);
		if (results.next()) {
			/* If it returns true, it means it was in the database and is not
		 	worth parsing again */
		} else {
			// Store the URI into the database table in order not to parse it again
			sql = "INSERT INTO  `links` " + "(`URI`) VALUES " + "(?);";
			PreparedStatement stmt = db.protocol.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, URI);
			stmt.execute();

			// Try to receive the document
			try{
				Document doco = Jsoup.connect(URI).get();
				if (doco.text().contains(searchFor)) {
					System.out.println("Yay! " + URI + " contains useful information!");

					// Get the time
					Date date = new Date();

					// Prepare the SQL query
					String needleQuery = "INSERT INTO needles VALUES (?, ?, ?, ?)";
					PreparedStatement preparedStatement = db.protocol.prepareStatement(needleQuery);
					// The database will auto increment the integer
					preparedStatement.setString(1, null);
					preparedStatement.setString(2, URI);
					preparedStatement.setString(3, searchFor);
					// Convert the date to string and append to query
					preparedStatement.setString(4, date.toString());

					// Insert the finding into the database
					preparedStatement.executeUpdate();
				}

				// Store all of the links in an Elements array
				Elements questions = doco.select("a[href]");

				// for each link, call out the same method again recursively
				for (Element link : questions) {
					// Cut out the unnecessary header (#) if any
					String[] split = link.attr("abs:href").split("#");

					// Start crawling all over again
					startingPoint(split[0], searchFor);
				}
			} catch (MalformedURLException e) {
			    // The given URL is not a true one
				System.out.println("Please check your URL!");
			} catch (IOException e) {
			    // We cannot connect to the server
				System.out.println("Please check your connection!");
			}
			
			
		}
	}
}