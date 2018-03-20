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
					+ "2) Registere øvelse\n"
					+ "3) Registrere treningsøkt\n"
					+ "4) Se n siste treningsøkter\n"
					+ "5) Se resultatlogg i tidsintervall\n"
					+ "6) Lage øvelsesgruppe\n"
					+ "7) Finne øvelser i samme gruppe\n"
					+ "8) Finne apparat på senter\n"
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
				System.out.println("Hvilket senterID hører apparatet til?");
				String senter = scanner.nextLine();
				query = "INSERT INTO Apparat values(\""+navn+"\",\""+beskrivelse+"\",\""+senter+"\")";
				Driver.Write(query);
				
			}else if(svar == 2){ //Trenger litt arbeid
				System.out.println("Hva er øvelsens navn?");
				String ØvelsesNavn = scanner.nextLine();
				
				///query = "insert ignore into øvelse values('"+ØvelsesNavn+"')";
//				Driver.Write(query);
				System.out.println("Apparat eller Fri?");
				String type =scanner.nextLine();
				if (type.equals("Apparat")) {
					System.out.println("(kilo *komma* sett *komma* apparatID) *enter*");
					String[] input = scanner.nextLine().split(",");
					query = "insert ignore into Øvelse values(\""+ØvelsesNavn+"\",NULL,\""+input[0]+"\",\""+input[1]+"\",\""+input[2]+"\")";
					Driver.Write(query);
					query = "INSERT INTO ØvelsePåApparat values( '"+ ØvelsesNavn+"',"+Integer.parseInt(input[0])+","+Integer.parseInt(input[1])+",'"+input[2]+"')";
					Driver.Write(query);
				} else {
					System.out.println("Beskrivelse for øvelse uten apparat?");
					String beskrivelse = scanner.nextLine();
					query = "insert into Øvelse values('"+ØvelsesNavn+"','"+beskrivelse+"')";
					Driver.Write(query);
				}
				
			}else if(svar == 3) { //Se på registrering av øvelser
				System.out.println("dato(ÅÅÅÅMMDD), tidspunkt(tt:mm:ss), varighet(tt:mm:ss), personligForm, prestasjon, notat");
				String dato = scanner.nextLine();
				String tidspunkt = scanner.nextLine();
				String varighet = scanner.nextLine();
				int form = Integer.parseInt(scanner.nextLine());
				int prestasjon = Integer.parseInt(scanner.nextLine());
				String notat = scanner.nextLine();
				query = "INSERT INTO Treningsøkt (dato,tidspunkt,varighet,personligForm,prestasjon,notat)"
						+ " values (\""+dato+"\",\""+tidspunkt+"\",\""+varighet+"\","+form+","+prestasjon+",\""+notat+"\")";	
				Driver.Write(query);
				query = "select count(*) from treningsøkt";
				result = Driver.Read(query);
				result.next();
				int øktid = result.getInt(1);
				System.out.println("Øvelser separert av komma");
				String[] øvelser = scanner.nextLine().split(",");
				for (String øvelse : øvelser) {
					query = "insert into øvelserfortreningsøkt values("+øktid+",\""+øvelse+"\")";
					Driver.Write(query);
				}
				
			}else if(svar == 4) { //Funker
				System.out.println("skriv inn en n");
				int n = Integer.parseInt(scanner.nextLine());
				query = "SELECT * FROM treningsøkt ORDER BY dato DESC,tidspunkt DESC LIMIT "+ n;
				result = Driver.Read(query);
				Driver.PrintSet(result);
			}
			else if(svar == 5) { //Se på kobling med øvelse for å finne navn 
				System.out.println("Start: ÅÅÅÅMMDD *enter*\n"
						+ "Slutt: ÅÅMMDD");
				String start = scanner.nextLine();
				String slutt = scanner.nextLine();
				
				query = "select personligform,prestasjon,notat, ØvelseIØkt.ØvID,kilo,sett from treningsøkt\n"
						+"natural join øvelserfortreningsøkt \n"
						+"left join øvelsemedapparat on øvelserfortreningsøkt.navn = øvelsemedapparat.navn"
						+" where dato between '"+ start+"' and '"+slutt+"';";
				System.out.println(query);
				result = Driver.Read(query);
				Driver.PrintSet(result);
				
			}else if(svar == 6) { //Se over for vår egen funksjonalitet
				ArrayList<String> øvelseliste = new ArrayList<>();
				System.out.println("Hvilken muskelgruppe?");
				String muskelgruppeNavn = scanner.nextLine();
				Driver.PrintTable("øvelse");
				while(true) {
					System.out.println("Skriv inn ønsket øvelse fra listen, \"quit\" for å hoppe ut av loop");
					String nyØvelse = scanner.nextLine();
					if (nyØvelse.equals("quit")) {
						break;
					}
					else {
						øvelseliste.add(nyØvelse);
					}
				}
				query = "insert ignore into øvelsesgruppe values(\""+muskelgruppeNavn+"\")";
				Driver.Write(query);
				for (String øvelse : øvelseliste) {
					query = "insert ignore into gruppeforøvelse values(\""+øvelse+"\",\""+muskelgruppeNavn+"\")";
					Driver.Write(query);
				}
			}
			else if(svar == 7) { //Se over hvordan våre øvelsesgrupper funker
				Driver.PrintTable("øvelsesgruppe");
				System.out.println("Hvilken gruppe vil du se øvelser fra?");
				String gruppe = scanner.nextLine();
				query = "select øvelsesnavn from gruppeforøvelse where gruppenavn=\""+gruppe+"\"";
				result = Driver.Read(query);
				Driver.PrintSet(result);
				
			}
			else if(svar == 8) { //Test
				System.out.println("Skriv ID på senter du vil se apparater til: ");
				String senterID = scanner.nextLine();
				query = "SELECT * "
						+"FROM Apparat NATURAL JOIN Treningssenter on Apparat.senterID = Treningssenter.senterID"
						+" where Treningssenter.SenterId= \""+senterID+"\"";
				result = Driver.Read(query);
				Driver.PrintSet(result);
			}
			else{
				System.out.println("Ugyldig input: prøv igjen");
			}
		}
	}
}