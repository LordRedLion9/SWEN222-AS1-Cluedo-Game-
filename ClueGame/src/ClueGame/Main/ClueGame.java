package ClueGame.Main;

import java.util.*;
import ClueGame.Data.*;
import ClueGame.Data.Character;
import ClueGame.Data.Character.CharName;
import ClueGame.Data.Location.LocName;
import ClueGame.Data.Weapon.WeaponType;


public class ClueGame {
	
	//Clue sets
	public List<Weapon> weapons = new ArrayList<Weapon>();
	public List<Character> characters  = new ArrayList<Character>();
	public List<Location> locations = new ArrayList<Location>();
	
	public Solution solution;
	
	public Board board = new Board(this);
	public InputManager input = new InputManager(this);
	
	private Player[] players;
	int numPly;
	public Player activePlayer;
		
	public ClueGame(){
		fillClueSets();
		solution = generateSolution();
		
		//Setting up players.
		numPly = input.getPlayerCount();
		players = new Player[numPly];
		for (int i = 0; i < numPly; i++){
			players[i] = new Player (i + 1);
			board.spawnPlayer(players[i]);
		}
		shuffleAndFill();
		activePlayer = players[0];
		
		// draw the board example (use when needed!)
		System.out.println(board.renderBoard());
		
		// how to move a player example (use when needed!)
		/*
		int diceRollsleft = 5;
		if(board.movePlayer(player, board.DOWN))
			diceRollsleft --;
		if(board.movePlayer(player, board.LEFT))
			diceRollsleft --;
		*/
		
		
		// Main game loop
		while (true){
			System.out.println("--------------");
			System.out.println(board.renderBoard());
			System.out.println("--------------");
			System.out.println("It is Player: " + activePlayer.getNumber() + "'s (" + board.getPlayerIcon(activePlayer) + ") turn.");	
			System.out.println("--------------");
			input.processInput();			
			endTurn();
		}
	}
	
	public static void main(String[] args) {
		new ClueGame();
	}
	
	
	public Player[] getPlayers(){
		return players;
	}
	
	
	
	public void endTurn(){
		System.out.println("Turn over.");
		if (activePlayer.getNumber() == numPly){
			activePlayer = players[0]; //Set back to first player.
		} else {
			activePlayer = players[activePlayer.getNumber()]; //Increase active player
		}
	}
	
	//Fills the sets with the clue objects
	//TODO: Optimise this later.
	public void fillClueSets(){
		weapons.add(new Weapon(WeaponType.CANDLESTICK));
		weapons.add(new Weapon(WeaponType.DAGGER));
		weapons.add(new Weapon(WeaponType.LEADPIPE));
		weapons.add(new Weapon(WeaponType.REVOLVER));
		weapons.add(new Weapon(WeaponType.ROPE));
		weapons.add(new Weapon(WeaponType.SPANNER));
		
		characters.add(new Character(CharName.Colonel_Mustard));
		characters.add(new Character(CharName.Miss_Scarlet));
		characters.add(new Character(CharName.Mrs_Peacock));
		characters.add(new Character(CharName.Mrs_White));
		characters.add(new Character(CharName.Professor_Plum));
		characters.add(new Character(CharName.The_Reverend_Green));
		
		locations.add(new Location(LocName.BALL_ROOM));
		locations.add(new Location(LocName.BILLIARD_ROOM));
		locations.add(new Location(LocName.CONSERVATORY));
		locations.add(new Location(LocName.BILLIARD_ROOM));
		locations.add(new Location(LocName.CONSERVATORY));
		locations.add(new Location(LocName.DINING_ROOM));
		locations.add(new Location(LocName.HALL));
		locations.add(new Location(LocName.KITCHEN));
		locations.add(new Location(LocName.LIBRARY));
		locations.add(new Location(LocName.LOUNGE));
		locations.add(new Location(LocName.STUDY));
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
	
	public void shuffleAndFill(){
		ArrayList<Clue> deck = new ArrayList<Clue>();
		deck.addAll(weapons);
		deck.addAll(characters);
		deck.addAll(locations);
		Collections.shuffle(deck);
		Collections.shuffle(deck); //Shuffle twice to be sure
		
		int playerIndex = 0;
		for (int i = 0; i < deck.size() - 1; i++){
			if (playerIndex > numPly - 1){playerIndex = 0;}
			players[playerIndex].addToHand(deck.get(i));
			playerIndex++;
		}
		
	}
	
	
	//TODO: Maybe tidy this up
	public boolean tryVictory(Character tryChar, Weapon tryWep, Location tryLoc){
		
		return ((solution.getChar().getType()).equals(tryChar.getType()) 
				&& (solution.getWep().getType()).equals(tryWep.getType())
				&& (solution.getLoc().getType()).equals(tryLoc.getType()));
			
	}
	
	
	public int rollDice(){
		return new Random().nextInt(6) + 1;
	}
}
