package battleships;

public class Coordinates {
	
	String text;
	
	//Constructor som sätter variabeln text till den String text som skickas in
	public Coordinates(String text) {
		this.text = text;
	}
	
	//Returnerar koordinat
	public String toString() {
		return text;
	}
	
	//Skapar hashcode av koordinat
	public int hashCode() {
		return text.hashCode();
	}
	
	//Undersöker om det som skickas in är en koordinat 
	//Undersöker om det finns en del av en båt på den koordinat som skickats in
	public boolean equals(Object obj) {
		if(!(obj instanceof Coordinates)) {
			return false;
		}
		Coordinates coordinates = (Coordinates) obj;
		if(this.text.equals(coordinates.text)) {
			return true;
		}
			return false;
	}
	
}