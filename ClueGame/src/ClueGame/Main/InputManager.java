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
				//move process
			case "SUGGEST":
				//suggest process
			case "ACCUSE":
				//accuse process
			case "END":
				//end process
			
		}
			
	}
	
}
