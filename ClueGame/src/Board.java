import java.util.*;
import java.lang.*;

public class Board {
	
	/*
	 * 0 = empty tile (you can move on these)
	 * 1 = room tile (you cannot more on these)
	 * 2 = room entry tile (used to enter room)
	 * 3 = start tile (players will start on one of these)
	 * 4 = board edge (players cannot move on these at any time)
	 */
	
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
	
	// keep track of which door tiles on 
	private RoomData[] roomData = new RoomData[] {
			new RoomData(new Coordinate(0,1), new Coordinate(10, 10))
	};
	
	// inner class used to hold coordinate data (like a tuple)
	class Coordinate{
		public int row, col;
		public Coordinate(int row, int col){
			this.row = row;
			this.col = col;
		}
	}
	
	// inner class used to hold data relating to rooms and their doors
	class RoomData{
			
		private List<Coordinate> doors;
		
		public RoomData(Coordinate roomTopLeft, Coordinate roomBottomRight){
			//FIXME incomplete
		}
		
		public boolean ownsDoor(Coordinate location){
			//FIXME incomplete
			return false;
		}
	}

	public Board(){		
	}
}
