package treningsdagbok;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class Main{

	
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Properties p = new Properties();
			p.put("user", "root");
			p.put("password", "root");           
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/prosjekt1?autoReconnect=true&useSSL=false",p);
		} catch (Exception e)
		{
			throw new RuntimeException("Unable to connect", e);
		}
	}
	
	public void sporring() {
		try {
			stmt = conn.createStatement();
			
			String query = "SELECT * FROM ØVELSE";
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
	
	
	
	public void insettØvelse(String navn, String beskrivelse, String senter) {
		
		try {
			String query = "INSERT INTO ØVELSE(Øvid, navn) Values (navn, beskrivelse, senter);";
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
		}catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
		}
	}
	
	
	public static void main(String[] args) {
		
		Main test = new Main();
		
		//what do you want to do?
		//1 = insert, 2 = find data, 3 = exit
		
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		System.out.println("what do you want to do?");
		System.out.println("1 = insert, 2 = find data, 3 = exit");
		int n = reader.nextInt();
		
		
		
		if (n == 1){
			System.out.println("Hva ønsker du å registrere?");
			System.out.println("1 = apparat, 2 = øvelse, 3 = treningsøkt, 4 = øvelsesgrupper");
			int m = reader.nextInt();
			
			if (m == 1) {
				test.insett();
			}
			
		}
		
		reader.close();
		
		
		
		// --1: Hva ønsker du å registrere?
		// --1: 1 = apparat, 2 = øvelse, 3 = treningsøkt, 4 = øvelsesgrupper
		
		// --1--1: Hva heter apparatet?
		// --1--1: Legg inn en beskrivelse
		// --1--1: Hvilket senter står apparatet på? (skriv inn id)
		
		// --1--2:
		Main test = new Main();
		System.out.println("funket");
		test.connect();
		System.out.println("workis");
		test.insett();
		System.out.println("nice");
		test.sporring();
		System.out.println("heihei");
	
	}
}
