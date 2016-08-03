package ClueGame.Data;

import ClueGame.Main.*;

import java.util.*;
import java.lang.*;
import ClueGame.Data.Location.LocName;

public class Board {
	
	/*
	 * 0 = empty tile (you can move on these)
	 * 1 = room tile (you cannot more on these)
	 * 2 = room entry tile (used to enter room)
	 * 3 = start tile (players will start on one of these)
	 * 4 = board edge (players cannot move on these at any time)
	 */
	
	// keep track of the main class
	private ClueGame main;
	
	private final int[][] tiles =  new int[][] {
		{4,4,4,4,4,4,4,4,4,3,1,4,4,4,3,4,4,4,4,4,4,4,4,4},
		{4,1,1,1,1,1,4,0,0,0,1,1,1,1,0,0,0,4,1,1,1,1,1,4},
		{4,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,4},
		{4,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,4},
		{4,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,0,0,2,1,1,1,1,4},
		{4,1,1,1,1,1,0,0,2,1,1,1,1,1,1,2,0,0,0,1,1,1,1,4},
		{4,1,1,1,2,1,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,3},
		{4,0,0,0,0,0,0,0,1,2,1,1,1,1,2,1,0,0,0,0,0,0,0,4},
		{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,4},
		{4,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,2,1,1,1,1,4},
		{4,1,1,1,1,1,1,1,0,0,4,4,4,4,4,0,0,0,1,1,1,1,1,4},
		{4,1,1,1,1,1,1,1,0,0,4,4,4,4,4,0,0,0,1,1,1,1,1,4},
		{4,1,1,1,1,1,1,2,0,0,4,4,4,4,4,0,0,0,1,1,1,1,2,4},
		{4,1,1,1,1,1,1,1,0,0,4,4,4,4,4,0,0,0,0,0,0,0,0,4},
		{4,1,1,1,1,1,1,1,0,0,4,4,4,4,4,0,0,0,1,1,2,1,1,4},
		{4,1,1,1,1,1,2,1,0,0,4,4,4,4,4,0,0,1,1,1,1,1,1,4},
		{4,0,0,0,0,0,0,0,0,0,4,4,4,4,4,0,0,2,1,1,1,1,1,4},
		{3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,4},
		{4,0,0,0,0,0,0,0,0,1,1,2,2,1,1,0,0,0,1,1,1,1,1,4},
		{4,1,1,1,1,1,2,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,3},
		{4,1,1,1,1,1,1,0,0,1,1,1,1,1,2,0,0,0,0,0,0,0,0,4},
		{4,1,1,1,1,1,1,0,0,1,1,1,1,1,1,0,0,2,1,1,1,1,1,4},
		{4,1,1,1,1,1,1,0,0,1,1,1,1,1,1,0,0,1,1,1,1,1,1,4},
		{4,1,1,1,1,1,1,0,0,1,1,1,1,1,1,0,0,1,1,1,1,1,1,4},
		{4,4,4,4,4,4,4,3,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4}
	};
	
	// the char values used to represent players on the board in ASCII
	private final char[] playerASCIIValues = new char[] {'#', '@', '%', '&', '*', '?'};
	
	
	// coordinates that can be used to keep track of a movement direction	
	public static final Coordinate UP = 	new Coordinate(-1, 0);
	public static final Coordinate DOWN = 	new Coordinate(1, 0);
	public static final Coordinate LEFT = 	new Coordinate(0, -1);
	public static final Coordinate RIGHT = 	new Coordinate(0, 1);
	
	
	// keep track of which door tiles on 
	private RoomData[] roomData = new RoomData[] {
			new RoomData(this, LocName.BALL_ROOM, 		new Coordinate(1,8), 	new Coordinate(7, 15)),
			new RoomData(this, LocName.KITCHEN, 		new Coordinate(0,1), 	new Coordinate(6, 5)),
			new RoomData(this, LocName.CONSERVATORY, 	new Coordinate(0,18), 	new Coordinate(5, 23)),
			new RoomData(this, LocName.DINING_ROOM, 	new Coordinate(9,0), 	new Coordinate(15, 7)),
			new RoomData(this, LocName.BILLIARD_ROOM,	new Coordinate(8,18), 	new Coordinate(12, 23)),
			new RoomData(this, LocName.LIBRARY,	 		new Coordinate(14,17), 	new Coordinate(18, 23)),
			new RoomData(this, LocName.LOUNGE, 			new Coordinate(19,0), 	new Coordinate(23, 6)),
			new RoomData(this, LocName.HALL, 			new Coordinate(18,9), 	new Coordinate(23, 14)),
			new RoomData(this, LocName.STUDY, 			new Coordinate(20,17), 	new Coordinate(23, 23)),
	};
		
	
	
	public Board(ClueGame main){	
		this.main = main;
	}
	
	
	// place a player on the board in an available start tile
	public void spawnPlayer(Player player){
		
		List<Coordinate> startOptions = getTilesOfType(TileType.START);
		boolean hasSpawned = false;
		
		for(Coordinate cord : startOptions){
			
			boolean placeTaken = false;
			
			for(Player checkPlayer : main.getPlayers()){
				if(checkPlayer != null && checkPlayer.getPosition().equals(cord)){
					placeTaken = true;
				}		
			}
			
			if(!placeTaken){
				player.setPosition(cord);
				hasSpawned = true;
			}
		}
		
		if(!hasSpawned){
			// should never happen, assuming that a valid number of players were chosen
			throw new RuntimeException("failed to spawn player");
		}
	}
	
	
	// move a player in a given direction (use direction constants!)
	public boolean movePlayer(Player player, Coordinate dir){
		
		Coordinate newCord = getCordInDirection(player.getPosition(), dir);	
		TileType tile = getTileAtCord(newCord);
		
		System.out.println("old " + player.getPosition().toString());
		System.out.println("new " + newCord.toString());
		System.out.println("dir " + dir.toString());

		if(!isValidCord(newCord)){
			System.out.println("ERR not valid");
			// we are leaving the board!
			return false;
		}
		else if(playerAtCord(newCord) != null){
			System.out.println("ERR player at cord");
			// another player is in this spot
			return false;
		}
		else if(player.inRoom()){
			
			// we are leaving a room
			player.setPosition(getRoomData(player.getCurrentRoom()).getDoorInDir(dir));
			player.setPosition(getCordInDirection(player.getPosition(), dir));
			player.setCurrentRoom(null);
			return true;
		}
		else if(tile == TileType.EDGE || tile == TileType.ROOM){
			System.out.println("ERR not valid tile type");
			// we can't move to this tile
			return false;
		}
		else if (tile == TileType.ROOMENTRY){
			
			// we are entering a room
			for(RoomData room : roomData){
				if(room.ownsDoor(newCord)){
					player.setCurrentRoom(room.locName);
					player.setPosition(newCord);
					return true;
				}
			}
			return false;
		}
		else{
			
			// we are moving to an empty tile
			player.setPosition(newCord);
			return true;
		}
	}
	
	public char getPlayerIcon(Player p){
		return playerASCIIValues[p.getNumber()-1];
	}
	
	
	// generate the string to display the board in ASCII
	public String renderBoard(){
		
		String textBoard = "";
		
		for(int row = 0; row < tiles.length; row++){
			for(int col = 0; col < tiles[row].length; col++){
				
				Coordinate cord = new Coordinate(row, col);
				
				Player containedPlayer = playerAtCord(cord);
				if(containedPlayer != null)
					textBoard += playerASCIIValues[containedPlayer.getNumber()-1]  + " ";
				else
					textBoard += tiles[row][col] + " ";
				
			}
			textBoard += "\n";
		}
		return textBoard;
	}
	
	
	// get the relevant RoomData for a given location
	private RoomData getRoomData(LocName loc){
		for(RoomData room : roomData){
			if(room.locName == loc)
				return room;
		}
		return null;
	}
	
	
	// get all tiles on the board of a given type
	public List<Coordinate> getTilesOfType(TileType tile){
		
		List<Coordinate> foundTiles = new ArrayList<Coordinate>();
		for(int row = 0; row < tiles.length; row++){
			for(int col = 0; col < tiles[row].length; col++){
				if(tiles[row][col] == tile.ordinal()){
					foundTiles.add(new Coordinate(row, col));
				}
			}
		}
		return foundTiles;
	}
	
	
	// get the tile at the given position
	private TileType getTileAtCord(Coordinate cord){
		
		for(int row = 0; row < tiles.length; row++){
			for(int col = 0; col < tiles[row].length; col++){
				if(row == cord.row && col == cord.col){
					return TileType.values()[tiles[row][col]];
				}
			}
		}
		System.out.println("ERR failed to find tile");
		return(TileType.EDGE);
	}
	
	
	// get the player at the given position
	private Player playerAtCord(Coordinate cord){
		
		for(Player checkPlayer : main.getPlayers()){
			if(checkPlayer != null && checkPlayer.getPosition().equals(cord)){
				return checkPlayer;
			}		
		}
		return null;
	}
	
	
	// returns the cord in the chosen direction (use the direction constants!)
	private Coordinate getCordInDirection(Coordinate pos, Coordinate dir){
		return new Coordinate(pos.row + dir.row, pos.col + dir.col);
	}	
	
	// make sure a cord is within the playable board;
	 private boolean isValidCord(Coordinate cord){
	 	return (
	 			cord.row >= 0 && cord.row <= 24 &&
	 			cord.col >= 0 && cord.col <= 24
	 			);
	 }
	
	
	enum TileType{
		EMPTY, 
		ROOM,
		ROOMENTRY,
		START,
		EDGE	
	}
	
}
