package ClueGame.Main;

import java.util.*;
import ClueGame.Data.*;
import ClueGame.Data.Character;


public class ClueGame {
	
	//Clue sets
	public List<Weapon> weapons = new ArrayList<Weapon>();
	public List<Character> characters  = new ArrayList<Character>();
	public List<Location> locations = new ArrayList<Location>();
	
	public Solution solution;
	
	public Board board = new Board();
	public InputManager input = new InputManager(this);
	
	public Player[] players;
	int numPly;
	public Player activePlayer;
	
	private boolean endTurn = false;
	
	public ClueGame(){
		fillClueSets();
		solution = generateSolution();
		
		//Setting up players.
		System.out.print("How many Players?: ");
		String numPlyStr = input.getInput();
		//if (numPlyStr.) Do input checking later.
		numPly = Integer.parseInt(numPlyStr);
		players = new Player[numPly];
		for (int i = 0; i < numPly; i++){
			players[i] = new Player (i + 1);
		}
		
		activePlayer = players[0];
		
		
		// Main game loop
		while (true){
			System.out.println("It is Player: " + activePlayer.getNumber() + "'s turn.");
			endTurn = false;
			while (!endTurn){				
				input.processNewInput();
			}
			
			
			System.out.println("Exiting loop");
		}
	}
	
	public boolean movePlayer(Player p, String dir){
		Character c = p.getCharacter();
		//return board.move(c, dir);
		return true; //Debug
	}
	
	
	
	public void endTurn(){
		System.out.println("Ending turn..");
		endTurn = true;
		if (activePlayer.getNumber() == numPly){
			activePlayer = players[0]; //Set back to first player.
		} else {
			activePlayer = players[activePlayer.getNumber()]; //Increase active player
		}
	}
	
	//Fills the sets with the clue objects
	//TODO: Optimise this later.
	public void fillClueSets(){
		weapons.add(new Weapon(Weapon.WeaponType.CANDLESTICK));
		weapons.add(new Weapon(Weapon.WeaponType.DAGGER));
		weapons.add(new Weapon(Weapon.WeaponType.LEADPIPE));
		weapons.add(new Weapon(Weapon.WeaponType.REVOLVER));
		weapons.add(new Weapon(Weapon.WeaponType.ROPE));
		weapons.add(new Weapon(Weapon.WeaponType.SPANNER));
		
		characters.add(new Character(Character.CharName.Colonel_Mustard));
		characters.add(new Character(Character.CharName.Miss_Scarlet));
		characters.add(new Character(Character.CharName.Mrs_Peacock));
		characters.add(new Character(Character.CharName.Mrs_White));
		characters.add(new Character(Character.CharName.Professor_Plum));
		characters.add(new Character(Character.CharName.The_Reverend_Green));
		
		locations.add(new Location(Location.LocName.BALL_ROOM));
		locations.add(new Location(Location.LocName.BILLIARD_ROOM));
		locations.add(new Location(Location.LocName.CONSERVATORY));
		locations.add(new Location(Location.LocName.BILLIARD_ROOM));
		locations.add(new Location(Location.LocName.CONSERVATORY));
		locations.add(new Location(Location.LocName.DINING_ROOM));
		locations.add(new Location(Location.LocName.HALL));
		locations.add(new Location(Location.LocName.KITCHEN));
		locations.add(new Location(Location.LocName.LIBRARY));
		locations.add(new Location(Location.LocName.LOUNGE));
		locations.add(new Location(Location.LocName.STUDY));
	}
	
	//Generates the enveloped solution
	public Solution generateSolution(){
		Location toAddLoc;
		Character toAddChar;
		Weapon toAddWep;
		
		int rand = new Random().nextInt(characters.size());
		toAddChar = characters.remove(rand);
		
		rand = new Random().nextInt(locations.size());
		toAddLoc = locations.remove(rand);
		
		rand = new Random().nextInt(weapons.size());
		toAddWep = weapons.remove(rand);
		
		return new Solution(toAddChar, toAddWep, toAddLoc);
	}
	
	//TODO: Maybe tidy this up
	public boolean tryVictory(Character tryChar, Weapon tryWep, Location tryLoc){
		
		return (solution.getChar().getName() == tryChar.getName() 
				&& solution.getWep().getType() == tryWep.getType()
				&& solution.getLoc().getName() == tryLoc.getName());
			
	}
	
	
	public int rollDice(){
		return new Random().nextInt(6) + 1;
	}
}
