package ClueGame.Main;

import java.util.*;
import ClueGame.Data.Board;

public class InputManager {

	//Ideally this class should do nothing but process input and instruct main game class.
	
	private Scanner scan = new Scanner (System.in);
	private ClueGame game;
	private Board board;
	
	public InputManager(ClueGame game, Board board){
		this.game = game;
		this.board = board;
	}
	
	public String getInput(){
		return scan.next();	
	}
	
	public void processNewInput(){
		String input = getInput();
		//Maybe do formatting here?
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
				game.endTurn();
			
		}
			
	}
	
	private void moveCommand(){
		int canMove = game.rollDice();
		System.out.println("You rolled: " + canMove);
		
		
		for (int i = 0; i < canMove; i++){	
			System.out.println("Step: " + i);
			System.out.print("Which direction will you move?: ");
			String input = scan.next();
			
			//TODO: Implement board piece moving functions please Jack;
			 if (input.equals("N")|| input.equals("S")|| input.equals("E")|| input.equals("W")){
				 
				 //Board method to check whether they can move that direction
				 //if (!board.canMove(input)){ Error Handling Here }
				 //board.move(input)
				 
				 //Better solution would be InputManager -> ClueGame -> Board , instead of InputManager -> Board
				 
				 System.out.println("You moved " + input);
			 } else {
				 System.out.println(input + " is an Unrecognised Input");
				 i--;
			 }
			
		}
	}
	
}
