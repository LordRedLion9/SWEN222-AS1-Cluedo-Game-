package ClueGame.Data;

import java.util.ArrayList;
import java.util.List;

import ClueGame.Data.Board.TileType;
import ClueGame.Main.ClueGame;

//class used to hold data relating to rooms and their doors
public class RoomData {
				
	public Location.LocName locName;
	private List<Coordinate> doors;
	private Board board;
	
	
	// automatically find and keep track of the doors to this room
	public RoomData(Board board, Location.LocName locName, Coordinate roomTopLeft, Coordinate roomBottomRight){
		
		this.board = board;
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
	
	/**
	 * 
	 * Returns whether the location given is a door that
	 * belongs to this room
	 * 
	 * @param location the coordinate to check
	 * @return boolean of if coordinate is owned door
	 */
	public boolean ownsDoor(Coordinate location){
		for(Coordinate cord : doors){
			if(cord.equals(location)){
				return true;
			}
		}
		return false;
	} 
	
	
	/**
	 * 
	 * Returns the closest door in the room that
	 * corresponds to the given direction
	 * 
	 * 
	 * @param dir The coordinate to check
	 * @return The coordinate of the closest door
	 */
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
	
	public Coordinate getNextEmptyDoor(ClueGame reference){
		for (Coordinate cord : doors){
			boolean playerFound = false;
			for (Player p : reference.getPlayers()){
				if(p.getPosition().equals(cord))
					playerFound = true;
			}
			if(!playerFound)
				return cord;
		}
		return doors.get(0);
	}
}
