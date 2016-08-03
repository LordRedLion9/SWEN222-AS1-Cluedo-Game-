package ClueGame.Main;

import java.util.*;
import ClueGame.Data.Board;
import ClueGame.Data.Coordinate;

public class InputManager {

	//Ideally this class should do nothing but process input and instruct main game class.
	
	private Scanner scan = new Scanner (System.in);
	private ClueGame game;
	
	
	public InputManager(ClueGame game){
		this.game = game;
	}
	
	public void processInput(){

		if(game.activePlayer.inRoom()){
			
			System.out.println("You are in the " + game.activePlayer.getCurrentRoom().name() + ", choose your next move");
			int choice = getActionFromList(new String[] {
					"Move",
					"Suggest",
					"Accuse",
					"End turn"});
			
			switch (choice){
			case 0:
				moveCommand();
				break;
			case 1:
				return;
			case 2:
				return;
			case 3:
				return;
			}	
		}
		else{
			
			System.out.println("Choose your next move");
			int choice = getActionFromList(new String[] {
					"Move",
					"End turn"});
			
			switch (choice){
			case 0:
				moveCommand();
				break;
			case 1:
				return;
			}	
		}			
	}
	
	private void moveCommand(){
		
		
		int canMove = game.rollDice();
		System.out.println("You rolled: " + canMove);
		
		while(true){
			
			int choice;
			
			if(game.activePlayer.inRoom()){
				System.out.println("You are in a room, which door do you wish to leave from?");
				choice = getActionFromList(new String[] {
						"North",
						"South",
						"East",
						"West",
						"Stay in this room" });
			}
			else{
				System.out.println("Choose a direction to move");
				choice = getActionFromList(new String[] {
						"North",
						"South",
						"East",
						"West",
						"Stop moving" });
			}
			
			if(choice == 4)
				return;
			
			if (game.board.movePlayer(game.activePlayer, convertInputToVector(choice))){
				System.out.println("You moved. Moves remaining" + canMove);
				canMove--;
			} else {
				System.out.println("You cannot move here!");
			}	 
			
			if(canMove == 0){
				return;
			}	
		}
	}
	
	public int getPlayerCount(){
		System.out.println("How many players do you want?");
		return getActionFromList(new String[] {
				"", // will only show numbers, just like we want!
				"",
				"",
				"",
				"" }) + 1;	
	}
	
	private Coordinate convertInputToVector(int input){
		switch(input){
		case 0:
			return Board.UP;
		case 1:
			return Board.DOWN;
		case 2:
			return Board.RIGHT;
		case 3:
			return Board.LEFT;
		}
		
		return null;
	}
	
	private int getActionFromList(String[] list){
		for (int i = 1; i <= list.length; i++){	
			System.out.println(i + " : " + list[i-1]);
		}
		while(true){
			System.out.println("please enter the number of your choice : ");
			String input = scan.next();
			try{
				int res = Integer.parseInt(input);
				return res-1;
			}
			catch (NumberFormatException e){
				continue;
			}
		}
	}	
}










