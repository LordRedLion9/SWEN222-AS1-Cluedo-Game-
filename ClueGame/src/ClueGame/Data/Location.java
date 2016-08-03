package ClueGame.Data;

public class Location implements Clue{
	private LocName name;

	public enum LocName {
		KITCHEN,
		BALL_ROOM,
		CONSERVATORY,
		BILLIARD_ROOM,
		LIBRARY,
		STUDY,
		HALL,
		LOUNGE,
		DINING_ROOM
		
	}

	public Location(LocName name){
		this.name = name;
	}

	public String getType() {
		return name.name();
	}
}
