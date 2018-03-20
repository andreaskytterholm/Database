package treningsdagbok;

import java.sql.*;
import java.util.*;

public class Friovelse extends ActiveDomainObject{

	private Ovelse ovelse;
	private String beskrivelse;
	
	
	
	public void initialize (Connection conn) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select navn, epost, brukertype from Bruker where bid=" + bid);
            while (rs.next()) {
                ovelse =  rs.getString("ovelse");
                beskrivelse = rs.getString("beskrivelse");
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
            ResultSet rs = stmt.executeQuery("update Bruker set ovelse="+ovelse+", beskrivelse="+beskrivelse+"where bid="+bid);
        } catch (Exception e) {
            System.out.println("db error during update of bruker="+e);
            return;
        }
    }
}
