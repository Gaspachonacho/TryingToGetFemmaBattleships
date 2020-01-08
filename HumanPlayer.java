package battleships;

import java.util.*;

public class HumanPlayer extends Player {

	public HumanPlayer(int turn, int health, String nickname, int shots, LinkedHashMap<Coordinates, Pieces> playerBoard,
			LinkedHashMap<Coordinates, Pieces> enemyBoard, List<Boat> playerBoats) {
		super(turn, shots, nickname, shots, enemyBoard, enemyBoard, playerBoats);
		// TODO Auto-generated constructor stub
	}

	// Låter spelaren skjuta på motståndarens bräde
	public boolean aim(Player p) {
		System.out.println("Where would you like to shoot " + p.getNickname() + "?");
		String playerAim = scan.next();
		System.out.println();
		// Om koordinaten inte finns på brädet får spelaren skriva ny koordinat
		while (onBoard(playerAim) == false) {
			System.out.println("Please fire on the board");
			playerAim = scan.next();
			System.out.println();
		}
		// Om spelaren redan skjutit på den angivna koordinaten får spelaren skriva ny
		// koordinat
		Coordinates playerShot = new Coordinates(playerAim);
		while (p.getEnemyBoard().get(playerShot).equals(miss) || p.getEnemyBoard().get(playerShot).equals(hit)) {
			System.out.println("You have already fired there, please fire somewhere else");
			playerAim = scan.next();
			playerShot = new Coordinates(playerAim);
		}
		return shoot(playerShot, playerAim);
	}

	public static boolean onBoard(String coordinates) {
		if (coordinates.charAt(0) < 'A' || coordinates.charAt(0) > 'J') {
			return false;
		}
		if (coordinates.charAt(1) < '0' || coordinates.charAt(1) > '9') {
			return false;
		}
		if (String.valueOf(coordinates).length() != 2) {
			return false;
		}
		return true;
	}

	public boolean position(String alignment) {
		if (alignment.equals("h")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isComputer() {
		return false;
	}

	public void turn() {
		// Nästa spelares tur, skriver ut spelbräden

			// Spelaren får fortsätta skjuta på alla andras enemyBoard tills den missar.
			// Hoppar då ur whileloopen som blir false
			if (this.getTurn() != turnCounter) {
				while (aim(this) == true)
					;
			}
		turnCounter++;
		
		if (turnCounter > amountOfPlayers) {
			turnCounter = 1;
			amountOfTurns++;
		}
	}

}