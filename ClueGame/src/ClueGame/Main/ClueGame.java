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
	
	public boolean playing = true;
	public static boolean useTestingLogic;
		
	public ClueGame(){
		fillClueSets();
		
		
		//Setting up players.
		numPly = input.getPlayerCount();
		players = new Player[numPly];
		for (int i = 0; i < numPly; i++){
			players[i] = new Player (i + 1);
			players[i].setCharacter(characters.get(i));
			board.spawnPlayer(players[i]);
		}
		
		activePlayer = players[0];
						
		solution = generateSolution();
		shuffleAndFill();
		
		// Main game loop
		while (playing){
			System.out.println("--------------");
			System.out.println(board.renderBoard());
			System.out.println("--------------");
			System.out.println("It is Player: " + activePlayer.getNumber() + "'s (" + board.getPlayerIcon(activePlayer) + ") turn.");	
			System.out.println("You are playing as " + activePlayer.getCharacter().getType());	
			System.out.println("--------------");
			input.processInput(false);			
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
		int nextPlayer = activePlayer.getNumber();
		while(true){
			if (nextPlayer == numPly)
				nextPlayer = 0;
			if(players[nextPlayer].getActive()){
				activePlayer = players[nextPlayer]; 
				break;
			}
			nextPlayer++;
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
		
		characters.add(new Character(CharName.Mrs_Peacock));
		characters.add(new Character(CharName.Colonel_Mustard));
		characters.add(new Character(CharName.Miss_Scarlet));	
		characters.add(new Character(CharName.Mrs_White));
		characters.add(new Character(CharName.Professor_Plum));
		characters.add(new Character(CharName.The_Reverend_Green));
		
		locations.add(new Location(LocName.BALL_ROOM));//
		locations.add(new Location(LocName.BILLIARD_ROOM));//
		locations.add(new Location(LocName.CONSERVATORY));//
		locations.add(new Location(LocName.DINING_ROOM));//
		locations.add(new Location(LocName.HALL));//
		locations.add(new Location(LocName.KITCHEN));//
		locations.add(new Location(LocName.LIBRARY));
		locations.add(new Location(LocName.LOUNGE));//
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
		
		System.out.println("The solution is: " + toAddChar.getType() + " " + toAddLoc.getType() + " " + toAddWep.getType());
		return new Solution(toAddChar, toAddLoc , toAddWep);
	}
	
	public void shuffleAndFill(){
		ArrayList<Clue> deck = new ArrayList<Clue>();
		deck.addAll(weapons);
		deck.addAll(characters);
		deck.addAll(locations);
		Collections.shuffle(deck);
		Collections.shuffle(deck); //Shuffle twice to be sure
		
		int playerIndex = 0;
		for (int i = 0; i < deck.size(); i++){
			if (playerIndex > numPly - 1){playerIndex = 0;}
			players[playerIndex].addToHand(deck.get(i));
			playerIndex++;
		}
		
	}
	
	public void makeSuggest(LocName loc, Weapon wep, Character cha){
		for (Player p : players){
			if (p.getCharacter().getType().equals(cha.getType())){board.movePlayerToRoom(p, loc);}
		}
		
		System.out.println();
		System.out.println("You are suggesting it was:");
		System.out.println(cha.getType() + " in the "  + loc.name()+ " with the " + wep.getType());
		System.out.println();
		
		int i = activePlayer.getNumber(); //Technically player AFTER active player in terms of array
		
		for (int k = 0 /*player AFTER active player*/; k < numPly - 1; k++){
			if (i == numPly){i = 0;}
			for (Clue c : players[i].getHand()){			
				if (wep.getType().equals(c.getType())){System.out.println("Player " + players[i].getNumber() + " has the " + c.getType()); return;}
				if (cha.getType().equals(c.getType())){System.out.println("Player " + players[i].getNumber() + " has the " + c.getType()); return;}
				if (loc.name().equals(c.getType())){System.out.println("Player " + players[i].getNumber() + " has the " + c.getType()); return;}
			}
			i++;
		}
		
		System.out.println();
		System.out.println("None of the other players can refute you");
		System.out.println();
	}
	
	//TODO: Maybe tidy this up
	public boolean tryVictory(Character tryChar, Location tryLoc, Weapon tryWep){
		
		return ((solution.getChar().getType()).equals(tryChar.getType()) 
				&& (solution.getWep().getType()).equals(tryWep.getType())
				&& (solution.getLoc().getType()).equals(tryLoc.getType()));
			
	}
	
	
	public int rollDice(){
		return new Random().nextInt(6) + 1;
	}

	public boolean makeAccusal(Character cha, Location loc, Weapon wep) {
		
		System.out.println();
		System.out.println("You are accusing: ");
		System.out.println(cha.getType() + " of commiting the crime in the "  + loc.getType()+ " with the " + wep.getType());
		System.out.println();
		
		if (tryVictory(cha, loc, wep)){
			System.out.println("Player " + activePlayer.getNumber() + " is the WINNER!"); 
			playing = false;
			return true;
		} else {
			System.out.println("Sorry player " + activePlayer.getNumber() + ", but that wrong. You are OUT!");
			activePlayer.setActive(false);
			return false;
		}
	}
}
