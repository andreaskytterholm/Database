package treningsdagbok;



	import java.sql.*;
	import java.util.Properties;
public class Apparat�velse extends ActiveDomainObject{
	
	private Ovelse ovelse;
	private Apparat apparat;
	private int kilo;
	private int sett;
	
	public Apparat�velse(String ovelse)
	{
		this.ovlese = ovelse;
	}
	
	
	
	public void initialize (Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select navn, epost, brukertype from Bruker where bid=" + bid);
            while (rs.next()) {
                navn =  rs.getString("navn");
                epost = rs.getString("epost");
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
