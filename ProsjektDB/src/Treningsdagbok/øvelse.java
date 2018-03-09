package Treningsdagbok;

import java.sql.*;
import java.util.*;

public class øvelse {

	private int øvID;
	private String navn;
	
	private static ID_provider;
	
	
	
	public øvelse (int øvID) {
		øvID = ID_provider;
	}
	
	public int getøvID() {
		return øvID;
	}
	
	public void initialize (Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(" SKRIV INN SQL-SPØRRING HER!!!! " + øvID);
			while (rs.next()) {
				navn = rs.getString("navn");
			}
		}
		catch (Exception e) {
			System.out.println("db error during select of øvelse= " +e);
			return;
	}
}
	public void refresh (Connection conn) {
		initialize(conn);
	}
	
	public void save (Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SKRIV INN SQL-SPØRRING/KODE/WHATEVER!!!");
		}
		catch (Exception e) {
			System.out.println("db error during update of øvelse = " +e);
			return;
		}
	}
	
	
	
}


