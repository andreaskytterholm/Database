package treningsdagbok;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.Statement;

public class Test {

	
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	public void connect() {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/prosjekt1?user=root&passord=root");
			
		}
		catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
		}
	}
	
	public void sporring() {
		try {
			stmt = conn.createStatement();
			
			String query = "SELECT * FROM OVELSE";
			if(stmt.execute(query)) {
				rs = stmt.getResultSet();
				
			}
			
			while (rs.next()) {
				String kolonne1 = rs.getString(1);
				String kolonne2 = rs.getString(2);
				System.out.println(kolonne1 + " - " + kolonne2);
				
			}
		}catch(SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
		}
	}
	
	
	
	public void insett() {
		try {
			String query = "INSERT INTO OVELSE(ovid, navn) Values ('1', 'hans');";
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
		}catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
		}
	}
	
	
	public static void main(String[] args) {
		Test test = new Test();
		test.connect();
		test.sporring();
		test.insett();
	}
}


