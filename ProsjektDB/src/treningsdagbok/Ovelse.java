package treningsdagbok;

import java.sql.*;
import java.util.*;
import com.mysql.jdbc.Connection;

public class Ovelse extends ActiveDomainObject{

	private int ovID;
	private String navn;
	private static int IDCount;
	
	
	
	public Ovelse (String navn) {
		this.ovID = IDCount;
		this.navn = navn;
	}
	
	public int getovID() {
		return ovID;
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


