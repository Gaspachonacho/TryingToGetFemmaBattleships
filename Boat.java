package battleships;

import java.util.*;	

public class Boat {
	final static int destroyerSize = 2;			//Tre stycken
	final static int cruiserSize = 3;			//Två stycken
	final static int battleshipSize = 4;		//Två stycken
	final static int carrierSize = 5;			//En stycken
	String name;
	int size;
	List<Coordinates>  boatCoordinates;
	//Instansierar en lista med specifika båtar
	static List<Boat> boats = Arrays.asList(
//			(new Boat("Carrier", carrierSize, null)), 
//			(new Boat("Battleship 1", battleshipSize, null)), 
//			(new Boat("Battleship 2", battleshipSize, null)),
//			(new Boat("Cruiser 1", cruiserSize, null)),
//			(new Boat("Cruiser 2", cruiserSize, null)),
//			(new Boat("Destroyer 1", destroyerSize, null)), 
			(new Boat("Destroyer 2", destroyerSize, null)), 
			(new Boat("Destroyer 3", destroyerSize, null)));

	public Boat(String name, int size, List<Coordinates> boatCoordinates) {
		this.name = name;
		this.size = size;
		this.boatCoordinates = boatCoordinates;
	}
	
	public String toString() {
			return ("Name: " + this.name + ", Size: " + this.size);	
	}
		
	// Setters and getters down below
	
	public String getName() {
		return name;
	}
	
	public int getSize() {
		return size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public List<Coordinates> getBoatCoordinates() {
		return boatCoordinates;
	}
	
	public static void printBoatList(List<Boat> boatList) {
		System.out.println("\n");
		
		System.out.println("Here are the boats in each player's arsenal:");
		for(Boat boats : boatList) {
			System.out.println(boats);
		}
		
		System.out.println("\n");
	}
}
