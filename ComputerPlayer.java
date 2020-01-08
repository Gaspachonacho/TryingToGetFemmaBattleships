package battleships;

import java.util.LinkedHashMap;
import java.util.List;

import battleships.Boat;
import battleships.Coordinates;
import battleships.Pieces;
import battleships.Player;

public class ComputerPlayer extends Player {

	public ComputerPlayer(int turn, int health, String nickname, int shots,
			LinkedHashMap<Coordinates, Pieces> playerBoard, LinkedHashMap<Coordinates, Pieces> enemyBoard,
			List<Boat> playerBoats) {
		super(turn, shots, nickname, shots, enemyBoard, enemyBoard, playerBoats);
		// TODO Auto-generated constructor stub
	}

	// Datorn skjuter på motståndarens bräde på slumpmässig koordinat
	// Om datorn redan skjutit på denna koordinat skjuter den på ny koordinat
	public boolean aim(Player p) {
		System.out.println("Firing at: " + p.getNickname());
		String botAim = getRandomCoordinate();
		System.out.println(botAim);
		Coordinates botShot = new Coordinates(botAim);
		while (p.getEnemyBoard().get(botShot).equals(miss) || p.getEnemyBoard().get(botShot).equals(hit)) {
			botAim = getRandomCoordinate();
			botShot = new Coordinates(botAim);
			System.out.println("Refiring at: " + p.getNickname());
			System.out.println(botAim);
		}
		return shoot(botShot, botAim);
	}

	// Metod som returnerar en slumpmässig koordinat
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

	public boolean isComputer() {
		return true;
	}

	public void turn() {
			if (this.getTurn() != turnCounter) {
				while (aim(this) == true)
					;
			}
	}

}