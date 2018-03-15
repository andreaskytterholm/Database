package treningsdagbok;

import java.util.Date;
import java.sql.*;
import java.util.*;


public class Treningsokt extends ActiveDomainObject{
	
	private ArrayList<Ovelse> ovelser;
	private int oktID;
	private Date dato;
	private int tidspunkt;
	private int varighet;
	private int prestasjon;
	private int form;
	private String notat;
	private static int IDCount;
	
	
	
	public Treningsokt(Date dato, int tidspunkt, int varighet, int prestasjon, int form, String notat){
		IDCount++;
		this.oktID = IDCount;
		this.dato = dato;
		this.tidspunkt = tidspunkt;
		this.varighet = varighet;
		this.prestasjon = prestasjon;
		this.form = form;
		this.notat = notat;
		this.ovelser = new ArrayList<Ovelse>();
	}
	
	public void addOvelse(Ovelse o) {
		ovelser.add(o);
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

