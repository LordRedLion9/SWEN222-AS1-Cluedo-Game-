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
	public InputManager input = new InputManager(this, board);
	
	
	
	public ClueGame(){
		fillClueSets();
		solution = generateSolution();
		
		// Main game loop
		while (true){
			input.processNewInput();
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
