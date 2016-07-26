package ClueGame.Data;

import java.util.List;

//class used to hold data relating to rooms and their doors
public class RoomData {
				
	public Location.LocName locName;
	private List<Coordinate> doors;
	
	public RoomData(Location.LocName locName, Coordinate roomTopLeft, Coordinate roomBottomRight){
		//FIXME incomplete
	}
	
	public boolean ownsDoor(Coordinate location){
		//FIXME incomplete
		return false;
	} 
}
