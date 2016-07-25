package ClueGame.Main;

import java.util.*;
import ClueGame.Data.Board;

public class InputManager {

	private Scanner scan = new Scanner (System.in);
	private ClueGame game;
	private Board board;
	
	public InputManager(ClueGame game, Board board){
		this.game = game;
		this.board = board;
	}
	
	public void processNewInput(){
		String input = scan.next();
		processInput(input);
	}

	public void processInput(String input){
		//Debug
		System.out.println(input);
		
		switch (input){
			case "MOVE":
				moveCommand();
			case "SUGGEST":
				//suggest process
			case "ACCUSE":
				//accuse process
			case "END":
				//end process
			
		}
			
	}
	
	private void moveCommand(){
		int canMove = game.rollDice();
		System.out.println("You rolled: " + canMove);
		for (int i = 0; i < canMove; i++){
			
			System.out.println("Step: " + i);
			String input = scan.next("Which direction will you move?");
			
			//TODO: Implement board piece moving function please Jack;
			 if (input == "N" || input == "S" || input == "E" || input == "W"){
				 //Board method to check whether they can move that direction
				 //if (!board.canMove(input)){ Error Handling Here }
				 //board.move(input)
				 System.out.println(input);
			 } else {
				 System.out.println("That is an incorrect Input");
				 i--;
			 }
			
		}
	}
	
}
