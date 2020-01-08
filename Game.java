package battleships;

import java.util.*;

public class Game {

	LinkedList<Player> players = new LinkedList<Player>();
	List<Player> defeatedPlayers = new ArrayList<Player>();
	Scanner scan = new Scanner(System.in);
	int amountOfPlayers = 0;
	int amountOfTurns = 0;
	int turnCounter = 1;
	int totalHealth = 0;
	List<Boat> playerBoats;
	Pieces miss = new Pieces('O');
	Pieces hit = new Pieces('X');
	
	public void initGame() {

		int x = 0;
		
		try {
			System.out.println("How many human players would you like to have?");
			x = scan.nextInt();

		} catch (InputMismatchException error) {
			System.out.println("Ange ett heltal");
			scan.nextLine();
		}

		System.out.println("Amount of human players is " + x);

		for (int i = 1; i <= x; i++) {
			System.out.println("Vad ska spelare " + i + " heta?");
			String spelarnamn = scan.next();
			Player p = new HumanPlayer(i, 0, spelarnamn, 0, null, null, null);
			System.out.println(p);
			players.add(p);
		}

		int y = 0;

		try {
			System.out.println("How many computer players would you like to have?");
			y = scan.nextInt();

		} catch (InputMismatchException error) {
			System.out.println("Ange ett heltal");
			scan.nextLine();
		}

		System.out.println("Amount of pc players is " + x);

		for (int i = 1; i <= y; i++) {
			String datorNamn = "Göran " + (i);
			Player p = new ComputerPlayer(x + i, 0, datorNamn, 0, null, null, null);
			players.add(p);
		}

		for (Player p : players) {
			p.amountOfPlayers = x + y;
		}

		amountOfPlayers = x + y;

		Boat.printBoatList(Boat.boats);

		for (Player p : players) {

			if (p.isComputer()) {
				Board PCBoard = new Board();
				PCBoard.makeBoard();
				p.setEnemyBoard(PCBoard.enemyMap);
				PCBoard.computerPlaceBoats();
				p.setBoats(PCBoard.playerBoatList);
				p.setPlayerBoard(PCBoard.map);
				p.setHealth(PCBoard.health);
			} else {
				System.out.println("Would " + p.getNickname() +  " like to randomize his/her board? y/n");
				String answer = scan.next();
				boolean correctInput = false;

				while (correctInput == false) {
					switch (answer) {
					case "n":
						Board board = new Board();
						board.makeBoard();
						board.placeBoats();
						p.setBoats(board.playerBoatList);
						p.setPlayerBoard(board.map);
						p.setHealth(board.health);
						p.setEnemyBoard(board.enemyMap);
						correctInput = true;
						break;

					case "y":
						Board PCBoard = new Board();
						PCBoard.makeBoard();
						p.setEnemyBoard(PCBoard.enemyMap);
						PCBoard.computerPlaceBoats();
						p.setBoats(PCBoard.playerBoatList);
						p.setPlayerBoard(PCBoard.map);
						p.setHealth(PCBoard.health);
						correctInput = true;
						break;

					default:
						System.out.println("Ange y eller n");
						break;

					}
				}
			}
		}

	}

	public void printBoard() {
		for(Player p : players) {
			//Här skrivs spelarens egna bräde ut 
			if(p.getTurn() == turnCounter) {
				System.out.println(p.getNickname() + "'s Board");
				System.out.println("=======================");
				System.out.println("  | 0 1 2 3 4 5 6 7 8 9");
				System.out.println("--+--------------------");
				char k = 'A';				
				for(Coordinates keys : p.getPlayerBoard().keySet()) {
					if(keys.toString().charAt(1) == '0') {
						System.out.print(k + " | ");
						k++;
					}	
					System.out.print(p.getPlayerBoard().get(keys) + " ");
					if(keys.toString().charAt(1) == '9') {
						System.out.println("");
					}
				}
				System.out.println("\n");
			}
		}
		for(Player p : players) {
			//Här skrivs enemy lines ut. Så man kan spåra vart man redan skjutit 
			if(p.getTurn() != turnCounter) {
				System.out.println(p.getNickname() + "'s lines");
				System.out.println("=======================");
				System.out.println("  | 0 1 2 3 4 5 6 7 8 9");
				System.out.println("--+--------------------");
				char k = 'A';
				for(Coordinates keys : p.getEnemyBoard().keySet()) {
					if(keys.toString().charAt(1) == '0') {
						System.out.print(k + " | ");
						k++;
					}	
					System.out.print(p.getEnemyBoard().get(keys) + " ");
					if(keys.toString().charAt(1) == '9') {
						System.out.println("");
					}
				}
				System.out.println("\n");
			}
		}
	}
	
	public void playGame() {
		initGame();
		printPlayerList();
		while (amountOfPlayers > 1) {
			for (Player p : players) {
				System.out.println(turnCounter);
				System.out.println(p.getNickname() + "'s turn! \n");
				if(p.isComputer() == false) {
				printBoard();
				}
				p.turn();
				System.out.println(p.getShots());
				System.out.println((p.getHealth()*100));
//				p.playerStats();
				turnCounter++;
				if (turnCounter > amountOfPlayers) {
					turnCounter = 1;
					amountOfTurns++;
				}
			}
			
		}
		
		// Lägger till vinnaren i defeatedPlayers så att vi kan enkelt printa all bräden
		// i slutet av spelet.
		for (Player p : players) {
			defeatedPlayers.add(p);
		}

		// Skriver ut de spelare som förlorat och vem som vann
		gameOverPrint();
		for (Player p : players) {
			System.out.println("Congratulations " + p.getNickname() + "!");
			System.out.println("You Won! \n");
		}
	}

	// Skriver ut alla spelares bräden i slutet.
	public void gameOverPrint() {
		for (Player p : defeatedPlayers) {
			System.out.println(p.getNickname() + "'s Board");
			System.out.println("=======================");
			System.out.println("  | 0 1 2 3 4 5 6 7 8 9");
			System.out.println("--+--------------------");
			char k = 'A';
			for (Coordinates keys : p.getPlayerBoard().keySet()) {
				if (keys.toString().charAt(1) == '0') {
					System.out.print(k + " | ");
					k++;
				}
				System.out.print(p.getPlayerBoard().get(keys) + " ");
				if (keys.toString().charAt(1) == '9') {
					System.out.println("");
				}
			}
			System.out.println("\n");
		}
	}

	// Skriver ut namnet på alla spelare
	public void printPlayerList() {
		System.out.println("\n");

		System.out.println("Here are all captains!");
		for (Player players : players) {
			System.out.println(players);
		}

		System.out.println("\n");
	}

	// Returnerar den spelare som vann
	public Player getWinner() {
		for (Player p : players) {
			return p;
		}
		return null;
	}
	
	public void setAmountOfTurns(int amountOfTurns) {
		this.amountOfTurns = amountOfTurns + 1;
	}
	

	// Räknar antal skott skjutna
	public void shotCounter() {
		for(Player p : players) {
			if (p.getTurn() == turnCounter) {
				p.setShots(p.getShots() + 1);
			}
		}
	}

}
