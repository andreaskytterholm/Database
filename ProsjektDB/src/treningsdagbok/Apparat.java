package treningsdagbok;


	import java.sql.*;
	import java.util.*;

public class Apparat extends ActiveDomainObject{
	
	private int apparatID;
	private String navn;
	private String beskrivelse;
	private int senterID;
	private static int IDCount = 0;
	
	
	
	public Apparat(String navn, String beskrivelse, int senterID) {
		IDCount ++;
		this.apparatID = IDCount;
		this.navn = navn;
		this.beskrivelse = beskrivelse;
		this.senterID = senterID;
	}
	
	//Naar man skal opprette et apparat kan man bruke regApparat i treningssenter
	
	
	public void initialize (Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select apparatID, apparatnavn, navn, epost, brukertype from Bruker where bid=" + bid);
            while (rs.next()) {
                navn =  rs.getString("navn");
                beskrivelse = rs.getString("beskrivelse");
                type = rs.getInt("brukertype");
            }

        } catch (Exception e) {
            System.out.println("db error during select of bruker= "+e);
            return;
        }

    }
    
    public void refresh (Connection conn) {
        initialize (conn);
    }
    
    public void save (Connection conn) {
        try {
            Statement stmt = conn.createStatement(); 
            ResultSet rs = stmt.executeQuery("update Bruker set navn="+navn+", epost="+epost+", brukertype="+type+"where bid="+bid);
        } catch (Exception e) {
            System.out.println("db error during update of bruker="+e);
            return;
        }
    }

}




