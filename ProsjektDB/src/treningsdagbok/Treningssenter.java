package treningsdagbok;


	import java.sql.*;
	import java.util.*;

public class Treningssenter extends ActiveDomainObject{
	
	private int senterID;
	private String navn;
	private ArrayList<Apparat> apparater;
	private static int IDCount = 0;

	
	
	
	public Treningssenter(String navn) {
		IDCount ++;
		senterID = IDCount;
		this.navn = navn;
		apparater = new ArrayList<Apparat>();
		
	}
	
	
	 public void regApparat (Connection conn, String navn, String beskrivelse) {
	        Apparat apparat = new Apparat (navn, beskrivelse, this.senterID);
	        apparat.initialize (conn);
	        apparater.add(apparat);
	}
	
	public void initialize (Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select navn, epost, brukertype from Bruker where bid=" + bid);
            while (rs.next()) {
                senterID =  rs.getString("senterID");
                navn = rs.getString("navn");
          
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


