package battleships;

import java.util.*;

public abstract class Player extends Game {

	int shots;
	int turn;
	int health;
	String nickname;
	LinkedHashMap<Coordinates, Pieces> playerBoard;
	LinkedHashMap<Coordinates, Pieces> enemyBoard;

	public Player(int turn, int health, String nickname, int shots, LinkedHashMap<Coordinates, Pieces> playerBoard,
			LinkedHashMap<Coordinates, Pieces> enemyBoard, List<Boat> playerBoats) {
		this.turn = turn;
		this.health = health;
		this.nickname = nickname;
		this.shots = shots;
		this.playerBoats = playerBoats;
		this.playerBoard = playerBoard;
		this.enemyBoard = enemyBoard;
	}

	public abstract boolean isComputer();

	public abstract void turn();

	public abstract boolean aim(Player p);

	// Skickar in spelarens namn och koordinat som den skjuter på
	public boolean shoot(Coordinates shot, String aim) {
		List<Coordinates> newCoordinates = new ArrayList<Coordinates>();
		List<Boat> newBoats = new ArrayList<Boat>();

		// Undersöker om spelaren träffade en båt på vald koordinat
		boolean isHit = false;
		for (Boat boats : this.getBoats()) {
			for (Coordinates coordinates : boats.getBoatCoordinates()) {

				// Vid träff - blir ett kryss på vald koordinat, ett liv försvinner
				if (coordinates.equals(shot)) {
					isHit = true;
					System.out.println("Hit! \n");
					this.getPlayerBoard().put(shot, hit);
					this.getEnemyBoard().put(shot, hit);
					this.setHealth(this.getHealth() - 1);
					newCoordinates.add(shot);
					shotCounter();
				}
			}

			// För varje träffad koordinat, tas del av båt bort
			for (Coordinates coord : newCoordinates) {
				boats.getBoatCoordinates().remove(coord);
				boats.setSize(boats.getSize() - 1);
			}

			// När en båts storlek är 0 berättar programmet att spelarens specifika båt
			// sjunkit
			if (boats.getSize() == 0) {
				newBoats.add(boats);
				System.out.println("You sunk " + this.getNickname() + "'s " + boats.getName() + "\n");
			}

			if (isHit == true) {
				break;
			}
		}

		// Tar bort de båtar som sjunkit från listan så att mängden iterationer blir
		// färre i framtiden
		for (Boat boats : newBoats) {
			this.getBoats().remove(boats);
		}

		// Om en spelares liv är 0 förlorar spelaren och den tas bort från spelet
		if (this.getHealth() == 0) {
			System.out.println(this.getNickname() + "'s Fleet has been destroyed! \n");
			players.remove(this);
			defeatedPlayers.add(this);
			amountOfPlayers--;
			return false;
		}

		// Om spelaren skjuter, men inte träffar, markeras missen med ett O, oavsett
		// miss eller träff,
		// räknas antal skott mha shotCounter()
		if (isHit == false) {
			System.out.println("Miss! \n");
			this.getPlayerBoard().put(shot, miss);
			this.getEnemyBoard().put(shot, miss);
			shotCounter();
			return false;
		}

		return true;

	}
	
	// Räknar hur många gånger varje spelare hur mycket av båtarna som är kvar samt
	// varje spelares träffsäkerhet
	public void playerStats() {
		if (amountOfTurns > 0) {
			int hitQuota = (100 - (100 * (amountOfPlayers - 1) * amountOfTurns) / this.getShots());
			float healthDenominator = (float) (this.totalHealth);
			float healthNominator = (float) (this.getHealth() * 100);
			int healthQuota = (int) (healthNominator / healthDenominator);
			System.out.println(this.getNickname() + "'s Accuracy: " + hitQuota + "%");
			System.out.println(this.getNickname() + "'s Health: " + healthQuota + "%\n");
			System.out.println();
		}
	}

	public String getNickname() {
		return nickname;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getHealth() {
		return health;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public int getShots() {
		return shots;
	}

	public void setShots(int shots) {
		this.shots = shots;
	}

	public List<Boat> getBoats() {
		return playerBoats;
	}

	public void setBoats(List<Boat> b) {
		this.playerBoats = b;
	}

	public LinkedHashMap<Coordinates, Pieces> getPlayerBoard() {
		return playerBoard;
	}

	public void setPlayerBoard(LinkedHashMap<Coordinates, Pieces> playerBoard) {
		this.playerBoard = playerBoard;
	}

	public LinkedHashMap<Coordinates, Pieces> getEnemyBoard() {
		return enemyBoard;
	}

	public void setEnemyBoard(LinkedHashMap<Coordinates, Pieces> enemyBoard) {
		this.enemyBoard = enemyBoard;
	}

	public String toString() {
		return ("Name: " + this.nickname + ", Health: " + this.health + ", Turn: " + this.turn);
	}
}
