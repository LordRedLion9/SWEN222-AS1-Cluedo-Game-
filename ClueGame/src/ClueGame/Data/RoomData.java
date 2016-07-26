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
	
	
	// returns the door in the given direction
	public Coordinate getDoorInDir(Coordinate dir){
		
		Coordinate best = doors.get(0);
		for (Coordinate cord : doors){
			
			if( (dir.col > 0 && cord.col > best.col) || 
				(dir.col < 0 && cord.col < best.col) ||
				(dir.row > 0 && cord.row > best.row) ||
				(dir.row < 0 && cord.row < best.row)){
				best = cord;
			}	
		}
		return best;
	} 
}
