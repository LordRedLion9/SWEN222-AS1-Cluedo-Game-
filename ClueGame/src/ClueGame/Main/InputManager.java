package ClueGame.Main;

import ClueGame.Tests.Tests;
import java.util.*;
import ClueGame.Data.Board;
import ClueGame.Data.Coordinate;
import ClueGame.Data.Location;
import ClueGame.Data.Location.LocName;
import ClueGame.Data.Weapon;
import ClueGame.Data.Weapon.WeaponType;
import ClueGame.Data.Character;
import ClueGame.Data.Character.CharName;

public class InputManager {

	//Ideally this class should do nothing but process input and instruct main game class.
	
	private Scanner scan = new Scanner (System.in);
	private ClueGame game;
	private boolean alreadyMoved = false;;
	
	public InputManager(ClueGame game){
		this.game = game;
	}
	
	public void processInput(boolean cantMoveNow){
		boolean loop = true;
		while (loop){
			
			if(game.activePlayer.inRoom()){ //Player in room
				
				System.out.println("You are in the " + game.activePlayer.getCurrentRoom().name() + ", choose your next move");
				int choice = 0;
				if(cantMoveNow){
					choice = getActionFromList(new String[] {
							"Show Hand",
							"Suggest",
							"Accuse",
							"End turn"});
					choice ++;
					
				}
				else{
					choice = getActionFromList(new String[] {
							"Move",
							"Show Hand",
							"Suggest",
							"Accuse",
							"End turn"});
				}
				
				switch (choice){
				case 0: //Move
					moveCommand();
					return;
				case 1: //Print hand
					printHand();
					break;
				case 2: //Suggest
					makeSuggestion();
					return;
				case 3: //Accuse
					return;
				case 4: //End Turn
					return;
				}	
			}
			else{ //Player not in room
				
				System.out.println("Choose your next move");
				int choice = getActionFromList(new String[] {
						"Move",
						"View Cards",
						"End turn"});
				
				switch (choice){
				case 0: //Move
					moveCommand();
					return;
				case 1: //Print hand
					printHand();
					break;
				case 2: //End turn
					return;
				}	
			}
		}	
	}
	
	private void printHand(){
		System.out.println();
		System.out.println("You have the following clues: ");
		game.activePlayer.printHand();
		System.out.println();
	}
	
	private void makeSuggestion(){
		
		LocName loc = game.activePlayer.getCurrentRoom();
		Weapon wep = new Weapon(WeaponType.CANDLESTICK); //Default for init
		Character cha = new Character(CharName.Mrs_Peacock); //Default for init
		
		System.out.println("Which weapon do you suggest?");
		int choice = getActionFromList(new String[] {
				"Candlestick",
				"Dagger",
				"Lead pipe",
				"Revolver",
				"Rope",
				"Spanner"});
		switch (choice){
		case 0:
			wep = new Weapon(WeaponType.CANDLESTICK);
			break;
		
		case 1:
			wep = new Weapon(WeaponType.DAGGER);
			break;
		case 2:
			wep = new Weapon(WeaponType.LEADPIPE);
			break;
		case 3:
			wep = new Weapon(WeaponType.REVOLVER);
			break;
		case 4:
			wep = new Weapon(WeaponType.ROPE);
			break;
		case 5:
			wep = new Weapon(WeaponType.SPANNER);
			break;
		}
		
		System.out.println("Which character do you suggest?");
		choice = getActionFromList(new String[] {
				"Colonel Mustard",
				"Miss Scarlet",
				"Mrs Peacock",
				"Professor Plum",
				"Mrs White",
				"The Reverend Green"});
		
		switch (choice){
		case 0:
			cha = new Character(CharName.Colonel_Mustard);
			break;
		case 1:
			cha = new Character(CharName.Miss_Scarlet);
			break;
		case 2:
			cha = new Character(CharName.Mrs_Peacock);
			break;
		case 3:
			cha = new Character(CharName.Professor_Plum);
			break;
		case 4:
			cha = new Character(CharName.Mrs_White);
			break;
		case 5:
			cha = new Character(CharName.The_Reverend_Green);
			break;
		}
		
		game.makeSuggest(loc, wep, cha);
		
	}
	
	private void moveCommand(){
		
		int canMove = game.rollDice();
		if(ClueGame.useTestingLogic)
			canMove = 100;
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
				System.out.println("--------------");
				System.out.println(game.board.renderBoard());
				System.out.println("--------------");
				System.out.println("You moved. Moves remaining : " + (canMove-1));
				if(game.activePlayer.inRoom()){
					System.out.println("you have entered the " + game.activePlayer.getCurrentRoom() + "!");
					processInput(true);
					return;
				}
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
			
			if(ClueGame.useTestingLogic){
				return Tests.queue.poll()-1;
			}
			String input = scan.next();
			try{
				int res = Integer.parseInt(input);
				if(res <= list.length)
					return res-1;
			}
			catch (NumberFormatException e){
				continue;
			}
		}
	}	
}










