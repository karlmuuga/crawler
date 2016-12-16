package crawler;

import java.io.IOException;
import java.sql.SQLException;
import lib.TextIO;

public class Main {

	public static void main(String[] args) throws SQLException, IOException {

		// Ask for input
		System.out.println("Hello! \nWhat are you looking for?");
		String searchFor = TextIO.getlnString();

		System.out.println("And where do you want to start?");
		String URI = TextIO.getlnString();

		if (URI.contains("http") || URI.contains("https")) {
			// Everything seems to be fine :)
		} else {
			// Add the necessary prefix
			URI = "http://" + URI;
		}

		// Empty the database table of old URL-s before crawling again
		Crawler.db.boolQuery("TRUNCATE links;");

		// Start
		Crawler.startingPoint(URI, searchFor);
	}
}