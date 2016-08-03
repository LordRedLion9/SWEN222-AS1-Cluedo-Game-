package ClueGame.Data;

import java.util.ArrayList;
import java.util.List;

import ClueGame.Data.Board.TileType;

//class used to hold data relating to rooms and their doors
public class RoomData {
				
	public Location.LocName locName;
	private List<Coordinate> doors;
	
	
	// automatically find and keep track of the doors to this room
	public RoomData(Board board, Location.LocName locName, Coordinate roomTopLeft, Coordinate roomBottomRight){
		
		this.locName = locName;
		doors = new ArrayList<Coordinate>();
		
		List<Coordinate> tiles = board.getTilesOfType(TileType.ROOMENTRY);
		for(Coordinate cord : tiles){
			if(	(cord.col >= roomTopLeft.col && cord.col <= roomBottomRight.col) && 
				(cord.row >= roomTopLeft.row && cord.row <= roomBottomRight.row)){
				doors.add(cord);
			}	
		}
	}
	
	// returns whether this door belongs to this room or not
	public boolean ownsDoor(Coordinate location){
		for(Coordinate cord : doors){
			if(cord.equals(location)){
				return true;
			}
		}
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
