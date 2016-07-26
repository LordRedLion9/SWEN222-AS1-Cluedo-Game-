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
		{4,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,4},
		{4,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,4},
		{4,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,4},
		{4,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,4},
		{4,0,0,0,0,0,0,0,2,1,1,1,1,1,1,2,0,0,0,0,0,0,0,4},
		{4,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,4},
		{4,0,0,0,0,0,0,0,1,2,1,1,1,1,2,1,0,0,0,0,0,0,0,4},
		{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
		{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
		{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
		{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
		{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
		{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
		{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
		{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
		{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
		{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
		{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
		{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
		{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
		{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
		{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
		{4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4}
	};
	
	// the char values used to represent players on the board in ASCII
	private final char[] playerASCIIValues = new char[] {'#', '@', '%', '&', '*', '?'};
	
	// keep track of which door tiles on 
	private RoomData[] roomData = new RoomData[] {
			new RoomData(LocName.BALL_ROOM, new Coordinate(0,1), new Coordinate(10, 10))
	};
		
	// inner class used to hold data relating to rooms and their doors
	class RoomData{
			
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
	

	public Board(ClueGame main){	
		this.main = main;
	}
	
	
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
	
	
	private List<Coordinate> getTilesOfType(TileType tile){
		
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
	
	
	private Player playerAtCord(Coordinate cord){
		
		for(Player checkPlayer : main.getPlayers()){
			if(checkPlayer != null && checkPlayer.getPosition().equals(cord)){
				return checkPlayer;
			}		
		}
		return null;
	}
	
	
	enum TileType{
		EMPTY, 
		ROOM,
		ROOMENTRY,
		START,
		EDGE	
	}
	
}
