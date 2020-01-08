package battleships;

import java.io.*;
import java.util.*;
public class Main {
	
	//Deklarerar scanner och en sparad lista för highscores 
	static Scanner scan = new Scanner(System.in);	
	LinkedList<String> highscoreList = new LinkedList<String>();
	
	//Constructor som skapar nytt main-objekt och som kör metoden menu
	public static void main(String[] args) throws IOException {
		Main main = new Main();
		main.menu();
	}
	
	//Metod menu som visar menyn, körs varje gång spelet startar
	public void menu() throws IOException {
		
		//Läser in filen över highscores från en sparad fil
		InputStream highscores = new FileInputStream ("/home/gasgr424/eclipse-workspace/Battleships2/src/battleships/highscores.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(highscores));
	    while (reader.ready()) {
	    	String line = reader.readLine();
	    	if(!line.equals("")) {
	    		highscoreList.add(line);
	    	}
	    }
	    reader.close();
	    
	    //De olika menyvalen där "choice" är användarens val
		int choice = 0;
		
		while (choice != 5) {
			choice = showMenu();
			switch(choice) {
				//Startar nytt spel mot kompisar på samma dator, och ser om man passar in på high score-listan vid spelets slut
				case 1:	
					Game game = new Game();
					game.playGame();
					Player p = game.getWinner();
					String playerName = p.getNickname();
					int shots = p.getShots();
					int scorePlace = checkHighscoreList(shots);
					if(scorePlace > 0) {
						System.out.println("\n" + "Congratulations! You made the highscore list!");
						saving(shots, scorePlace, playerName);
					}
					System.out.println();
				break;
				
				//Startar nytt spel mot bottar
				
				//Skriver ut allt som finns på highscore-listan
				case 2:
					System.out.println();
					for(String s : highscoreList) {
				    	System.out.println(s);
				    }
					System.out.println();
				break;
				
				//Skriver ut spelets regler
				case 3:
					System.out.println("\n");
					System.out.println("Rules:");
					System.out.println("1. Type coordinates with the yAxis coordinate first followed \n"
									+  "by the xAxis coordinate. Should look like this 'A0'\n");
					System.out.println("2. Your boat will be placed, depending on whether \n"
									+  "you placed it horizontally or vertically, from the \n"
									+  "leftmost respectively the upmost coordinate\n");
					System.out.println("3. No scrolling upwards to see the other player's board");
					System.out.println("4. Bots will fire randomly");
					System.out.println("\n");
				break;
			
				//Avslutar spel
				case 4:
					System.out.println("Thank you for playing");
					System.exit(0);
				break;
				
				//Om användaren skriver in en annan siffra eller en bokstav än vad som finns i menyn
				default:
					System.out.println("\nPlease type another number u dumbo\n");
				break;
			}
			
		}
	}
	
	//Visar menyn samt deklarerar variabeln choice som är användarens menyval
	private static int showMenu() {
		System.out.println("         Battleships!       ");
		System.out.println("============================");
		System.out.println("|| 1. Play                ||");
		System.out.println("|| 2. Show Highscores     ||");
		System.out.println("|| 3. Rules               ||");
		System.out.println("|| 4. Quit                ||");
		System.out.println("============================");
			
		int choice = 0;
		
		try {
			choice = scan.nextInt();	
		} 
		catch(InputMismatchException error) {
			scan.nextLine();
		}
		
		return choice;

	}
	
	//Metod som returnerar placeringen på highscore-listan beroende på spelarens resultat
	public int checkHighscoreList(int shots) {
		
		int scorePlace = 1;
		
		for(String s : highscoreList) {
	        String[] splitLine = s.split(":");
	        int recordShots = Integer.parseInt(splitLine[2].trim());
	        if(shots <= recordShots) {
	        	return scorePlace;
	        }
	        
	        scorePlace++;
		}
		return 0;
	}
	
	//Lägger till ny spelare på high score-listan
	public void saving(int shots, int scorePlace, String playerName) throws IOException {
		LinkedList<String> placeholder = new LinkedList<String>();
		int i = scorePlace + 1;
		//Undersöker vilken plats resultatet ska ligga på highscore-listan
		for(String s : highscoreList) {
			if(scorePlace + 48 > s.charAt(0)) { //Eftersom scorePlace är en integer måste vi addera 48 då s.charAt(0) är en char och utgår från ASCII-table
				placeholder.add(s);
				continue;
			}
			String[] splitLine = s.split(":");
			//Lägger in resultatet på den plats den ska ligga på
			if(scorePlace + 48 == s.charAt(0)) {
				placeholder.add(scorePlace + ". Name: " + playerName + ", Missiles Fired: " + shots);
				placeholder.add(i + ". Name:" + splitLine[1] + ":" + splitLine[2]);
				continue;
			}
			//Lägger in resultaten som är kvar och som ska ligga efter det nya resultatet
			i++;
			if(i == 10) break;
			placeholder.add(i + ". Name:" + splitLine[1] + ":" + splitLine[2]);
		}
		
		//Tömmer den nuvarande highscore-listan
		highscoreList.clear();
		
		//Lägger in allt i den tillfälliga listan placeholder till highscore-listan
		highscoreList = placeholder;
	
		//Skriver ut highscore-listan
		for(String s : highscoreList) {
			System.out.println(s);
			
		}
		//Sparar nya highscore-listan 
		OutputStream spara = new FileOutputStream ("/home/gasgr424/eclipse-workspace/Battleships2/src/battleships/highscores.txt", false);
		saveHighscore(spara);
	}
	
	//Metod som används för att spara input till en fil
	public void saveHighscore(OutputStream os) throws IOException {
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(os);
		for(String s : highscoreList) {
			outputStreamWriter.append(s + "\n");
		}
		outputStreamWriter.close();
	}
	
	public String getRandomCoordinate() {
		double xmin = 0;
		double xmax = 9;
		int xCoord = (int) ((Math.random() * ((xmax - xmin) + 1)) + xmin);

		double ymin = 65;
		double ymax = 74;
		char yCoord = (char) ((Math.random() * ((ymax - ymin) + 1)) + ymin);

		String coordinate = "" + yCoord + xCoord;

		return coordinate;
	}
	
}