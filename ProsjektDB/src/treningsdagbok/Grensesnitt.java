import java.sql.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Grensesnitt{
	static ResultSet result;
	static String query;
	public static void main(String[] args) throws SQLException {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("\n"
					+ "###########################\n"
					+ "Vil du:\n"
					+ "1) Registere apparat\n"
					+ "2) Registere �velse\n"
					+ "3) Registrere trenings�kt\n"
					+ "4) Se n siste trenings�kter\n"
					+ "5) Se resultatlogg i tidsintervall\n"
					+ "6) Lage �velsesgruppe\n"
					+ "7) Finne �velser i samme gruppe\n"
					+ "8) Finne apparat p� senter\n"
					+ "0) Avslutt\n"
					+ "##############################\n");
			int svar = Integer.parseInt(scanner.nextLine());
			if(svar == 0) {
				break;
				
			}else if(svar == 1) { //Test
				System.out.println("Hva er apparatets navn *enter* beskrivelse?");
				String navn = scanner.nextLine();
				String beskrivelse = scanner.nextLine();
				query = "SELECT * FROM Treningssenter";
				result = Driver.Read(query);
				Driver.PrintSet(result);
				System.out.println("Hvilket senterID h�rer apparatet til?");
				String senter = scanner.nextLine();
				query = "INSERT INTO Apparat values(\""+navn+"\",\""+beskrivelse+"\",\""+senter+"\")";
				Driver.Write(query);
				
			}else if(svar == 2){ //Funker kanskje, test
				System.out.println("Hva er �velsens navn?");
				String �velsesNavn = scanner.nextLine();
				System.out.println("Apparat eller Fri?");
				String type =scanner.nextLine();
				if (type.equals("Apparat")) {
					System.out.println("(kilo *komma* sett *komma* apparatID) *enter*");
					String[] input = scanner.nextLine().split(",");
					query = "insert into �velse(�velsesNavn, Kilo, Sett, ApparatID) values(\""+�velsesNavn+"\",\""+input[0]+"\",\""+input[1]+"\",\""+input[2]+"\")";
					Driver.Write(query);
					query = "INSERT INTO �velseP�Apparat values( '"+ �velsesNavn+"',"+Integer.parseInt(input[0])+","+Integer.parseInt(input[1])+",'"+input[2]+"')";
					Driver.Write(query);
				} else {
					System.out.println("Beskrivelse for fri�velse?");
					String beskrivelse = scanner.nextLine();
					query = "insert into �velse(�velsesNavn, �velsesBeskrivelse) values('"+�velsesNavn+"','"+beskrivelse+"')";
					Driver.Write(query);
				}
				
			}else if(svar == 3) { //Usikker p� listing av �velser, men test
				System.out.println("dato(����MMDD), tidspunkt(tt:mm:ss), varighet(tt:mm:ss), personligForm, prestasjon, notat");
				String dato = scanner.nextLine();
				String tidspunkt = scanner.nextLine();
				String varighet = scanner.nextLine();
				int form = Integer.parseInt(scanner.nextLine());
				int prestasjon = Integer.parseInt(scanner.nextLine());
				String notat = scanner.nextLine();
				query = "INSERT INTO Trenings�kt (dato,tidspunkt,varighet,personligForm,prestasjon,notat)"
						+ " values (\""+dato+"\",\""+tidspunkt+"\",\""+varighet+"\","+form+","+prestasjon+",\""+notat+"\")";	
				Driver.Write(query);
				query = "select count(*) from trenings�kt";
				result = Driver.Read(query);
				result.next();
				int �ktid = result.getInt(1);
				System.out.println("�velser separert av komma");
				String[] �velser = scanner.nextLine().split(",");
				for (String �velse : �velser) {
					query = "insert into �velseI�kt values("+�ktid+",\""+�velse+".�vID\")";
					Driver.Write(query);
				}
				
			}else if(svar == 4) { //Test
				System.out.println("skriv inn en n");
				int n = Integer.parseInt(scanner.nextLine());
				query = "SELECT * FROM Trenings�kt ORDER BY dato DESC,tidspunkt DESC LIMIT "+ n;
				result = Driver.Read(query);
				Driver.PrintSet(result);
			}
			else if(svar == 5) { //Test 
				System.out.println("Start: ����MMDD *enter*\n"
						+ "Slutt: ��MMDD");
				String start = scanner.nextLine();
				String slutt = scanner.nextLine();
				
				query = "select personligform,prestasjon,notat, �velseI�kt.�vID,kilo,sett from Trenings�kt\n"
						+"natural join �velseI�kt \n"
						+"left join �velse on �velseI�kt.�vID = �velse.�vID"
						+" where dato between '"+ start+"' and '"+slutt+"';";
				System.out.println(query);
				result = Driver.Read(query);
				Driver.PrintSet(result);
				
			}else if(svar == 6) { //Test
				ArrayList<String> �velseliste = new ArrayList<>();
				System.out.println("Navn p� �velsesgruppe:");
				String GruppeNavn = scanner.nextLine();
				Driver.PrintTable("�velse");
				while(true) {
					System.out.println("Skriv inn �nsket �velse fra listen, \"quit\" for � hoppe ut av loop");
					String ny�velse = scanner.nextLine();
					if (ny�velse.equals("quit")) {
						break;
					}
					else {
						�velseliste.add(ny�velse);
					}
				}
				query = "insert ignore into �velsesGruppe values(\""+GruppeNavn+"\")";
				Driver.Write(query);
				for (String �velse : �velseliste) {
					query = "insert ignore into �velseIGruppe values(\""+�velse+".�vID\",\""+GruppeNavn+"\")";
					Driver.Write(query);
				}
			}
			else if(svar == 7) { //Test
				Driver.PrintTable("�velsesgruppe");
				System.out.println("Hvilken gruppe vil du se �velser fra?");
				String gruppe = scanner.nextLine();
				query = "select �velsesNavn from �velseIGruppe NATURAL JOIN �velse where GruppeNavn=\""+gruppe+"\"";
				result = Driver.Read(query);
				Driver.PrintSet(result);
				
			}
			else if(svar == 8) { //Test
				System.out.println("Skriv ID p� senter du vil se apparater til: ");
				String senterID = scanner.nextLine();
				query = "SELECT * "
						+"FROM Apparat NATURAL JOIN Treningssenter on Apparat.senterID = Treningssenter.senterID"
						+" where Treningssenter.SenterId= \""+senterID+"\"";
				result = Driver.Read(query);
				Driver.PrintSet(result);
			}
			else{
				System.out.println("Ugyldig input: pr�v igjen");
			}
		}
	}
}