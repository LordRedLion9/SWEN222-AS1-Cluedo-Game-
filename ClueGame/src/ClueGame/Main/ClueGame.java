package ClueGame.Main;

import java.util.*;
import java.util.concurrent.TimeUnit;

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
	
	public Player[] players;
	int numPly; //Number of players
	public Player activePlayer;
	
	public boolean playing = true;
	public static boolean useTestingLogic;
		
	public ClueGame(boolean generate){
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
		
		
		if (generate){
			solution = generateSolution(); //Make enveloped solution
			shuffleAndFill(); //Deal cards to players
		}
		
		
		// Main game loop
		while (playing){
			System.out.println("--------------");
			System.out.println(board.renderBoard());
			System.out.println("--------------");
			System.out.println("It is Player: " + activePlayer.getNumber() + "'s [" + board.getPlayerIcon(activePlayer) + "] turn.");	
			System.out.println("You are playing as " + activePlayer.getCharacter().getType());	
			System.out.println("--------------");
			input.processInput(false);	
			endTurn();
			
		}
	}
	
	public static void main(String[] args) {
		new ClueGame(true);
	}
	
	
	public Player[] getPlayers(){
		return players;
	}
	
	public void endGame(){
		playing = false;
	}
	
	/**
	 * 
	 * End the current players turn and
	 * determines the next player
	 * 
	 */
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
	
	/**
	 * 
	 * Fills the clue arrays with
	 * new instances of the clues.
	 * 
	 */
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
	
	/**
	 * Takes clues out of the clues array and
	 * generates the enveloped solution for the game.
	 * 
	 * 
	 * @return The Solution object containing the solution clues
	 */
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
		
		//Uncomment for debug
		//System.out.println("The solution is: " + toAddChar.getType() + " " + toAddLoc.getType() + " " + toAddWep.getType());
		return new Solution(toAddChar, toAddLoc , toAddWep);
	}
	
	public Solution generateSolution(Character toAddChar, Location toAddLoc, Weapon toAddWep){
		weapons.remove(0);
		locations.remove(0);
		characters.remove(1);
		return new Solution(toAddChar, toAddLoc , toAddWep);
	}
	
	/**
	 * Shuffles all the clue arrays into one, shuffles it, and
	 * deals them out evenly to the players.
	 * 
	 */
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
	
	/**
	 * Takes three clue item and makes a cluedo suggestion.
	 * Scans all other players hands (clockwise) to see if their hands contain
	 * any of the suggested clues, and outputs if they do.
	 * 
	 * 
	 * @param loc The suggested Location 
	 * @param wep The suggested Weapon
	 * @param cha The suggested Character
	 */
	public void makeSuggest(LocName loc, Weapon wep, Character cha){
		for (Player p : players){
			if (p.getCharacter().getType().equals(cha.getType())){board.movePlayerToRoom(p, loc);}
		}
		
		System.out.println();
		System.out.println("You are suggesting it was:");
		System.out.println(cha.getType() + " in the "  + loc.name()+ " with the " + wep.getType());
		System.out.println();
		
		int i = activePlayer.getNumber(); //Technically this is the player AFTER active player in terms of array numbering
		try {
			if (!useTestingLogic){TimeUnit.SECONDS.sleep(1);}
			for (int k = 0; k < numPly - 1; k++){
				if (i == numPly){i = 0;}
				for (Clue c : players[i].getHand()){			
					if (wep.getType().equals(c.getType())){System.out.println("Player " + players[i].getNumber() + " has the " + c.getType()); if (!useTestingLogic){ TimeUnit.SECONDS.sleep(2);} return;}
					if (cha.getType().equals(c.getType())){System.out.println("Player " + players[i].getNumber() + " has the " + c.getType()); if (!useTestingLogic) {TimeUnit.SECONDS.sleep(2);} return;}
					if (loc.name().equals(c.getType())){System.out.println("Player " + players[i].getNumber() + " has the " + c.getType());  if (!useTestingLogic) {TimeUnit.SECONDS.sleep(2);} return;}
				}
				i++;
			}
			
			System.out.println();
			System.out.println("None of the other players can refute your suggestion");
			System.out.println();
			if (!useTestingLogic){ TimeUnit.SECONDS.sleep(2);}
		}catch (Exception e){System.out.println("Whoops! Somthing went wrong there");}
	}
	
	/**
	 * Checks to see if paramater clues match the ones in the solution
	 * 
	 * 
	 * @param tryChar The suggested Character
	 * @param tryLoc The suggested Location
	 * @param tryWep The suggested Weapon
	 * @return boolean of whether all three parameters match the solution
	 */
	public boolean tryVictory(Character tryChar, Location tryLoc, Weapon tryWep){
		
		return ((solution.getChar().getType()).equals(tryChar.getType()) 
				&& (solution.getWep().getType()).equals(tryWep.getType())
				&& (solution.getLoc().getType()).equals(tryLoc.getType()));
			
	}
	
	/**
	 * 
	 * Returns a dice roll.
	 * 
	 * @return integer from 1 to 6
	 */
	public int rollDice(){
		return new Random().nextInt(6) + 1;
	}

	/**
	 * Takes 3 suggested clues, and checks to see of they match the solution.
	 * If so, then the active player is the winner.
	 * If not, the current player has lost and can no longer take their turn.
	 * 
	 * 
	 * @param cha The accused Character
	 * @param loc The accused Location
	 * @param wep The accused Weapon
	 * @return boolean if whether accusal was correct
	 * @throws InterruptedException 
	 */
	public boolean makeAccusal(Character cha, Location loc, Weapon wep){
		
		System.out.println();
		System.out.println("You are accusing: ");
		System.out.println(cha.getType() + " of commiting the crime in the "  + loc.getType()+ " with the " + wep.getType());
		
		try{
			if (!useTestingLogic){ //Waiting during testing is annoying
				TimeUnit.SECONDS.sleep(1);
				System.out.println("...");
				TimeUnit.SECONDS.sleep(1);
				System.out.println("...");
				TimeUnit.SECONDS.sleep(1);
			}
		
		
			if (tryVictory(cha, loc, wep)){
				System.out.println("Player " + activePlayer.getNumber() + " is the WINNER!"); 
				playing = false;
				return true;
			} else {
				System.out.println("Sorry player " + activePlayer.getNumber() + ", but that wrong. You are OUT!");
				TimeUnit.SECONDS.sleep(3);
				activePlayer.setActive(false);
				return false;
			}
		
		}catch (Exception e){System.out.println("Whoops! Somthing went wrong there"); return false;}
	}
}
