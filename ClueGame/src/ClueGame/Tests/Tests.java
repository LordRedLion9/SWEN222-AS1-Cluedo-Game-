package ClueGame.Tests;
import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;

import ClueGame.Data.Coordinate;
import ClueGame.Data.Board;
import ClueGame.Data.Character;
import ClueGame.Data.Weapon;
import ClueGame.Data.Location;
import ClueGame.Main.ClueGame;
import junit.framework.TestCase;

public class Tests  {
	
	public static Queue<Integer> queue;

	
	public @Test void basicMove1() {
		
		setupTest(new int[] {
				1, // 1 player
				1, // move
				1, 1, 1, 1, 1, //Move North 5 times
				5, //End movement
				4 //End game
		});
		ClueGame.useTestingLogic = true;
		
		ClueGame game = new ClueGame(false);
		
		assertTrue("The coordinate of the player is " + game.activePlayer.getPosition().toString() + game.board.renderBoard(), game.activePlayer.getPosition().equals(new Coordinate(19, 7)));
		
	}
	
	public @Test void basicMove2() {
		
		setupTest(new int[] {
				2, // 2 players
				3, //End player 1 turn
				1,// move
				4, 4, 4, 4, 4, //Move West 5 times
				5, //End movement
				4 //End game
		});
		ClueGame.useTestingLogic = true;
		
		ClueGame game = new ClueGame(false);
		
		assertTrue("The coordinate of the player is " + game.players[1].getPosition().toString() + game.board.renderBoard(), game.players[1].getPosition().equals(new Coordinate(19, 18)));
		
	}
	
	public @Test void roomMove1() {
		
		setupTest(new int[] {
				1, // 1 player
				1,// move
				1, 1, 1, 1, 1, 1, 1, 1, 4, 1, //Move
				4, //End movement
				1,
				3,
				5,
				4 //End game
		});
		ClueGame.useTestingLogic = true;
		
		ClueGame game = new ClueGame(false);
		
		assertTrue("The coordinate of the player is " + game.players[0].getPosition().toString() + game.board.renderBoard(), game.players[0].getPosition().equals(new Coordinate(12, 8)));
		
	}
	
	public @Test void roomMove2() {
		
		setupTest(new int[] {
				1, // 1 player
				4 //End game
		});
		ClueGame.useTestingLogic = true;
		
		ClueGame game = new ClueGame(false);
		game.board.movePlayerToRoom(game.activePlayer, Location.LocName.KITCHEN);
		assertTrue("The coordinate of the player is " + game.players[0].getPosition().toString() + game.board.renderBoard(), game.players[0].getPosition().equals(new Coordinate(6, 4)));
		
	}
	
	public @Test void invalidMove1() {
		
		setupTest(new int[] {
				1, // 2 players
				4 //End game
		});
		ClueGame.useTestingLogic = true;
		
		ClueGame game = new ClueGame(false);
		
		assertFalse(game.board.movePlayer(game.players[0], Board.DOWN));
		
	}
	
	public @Test void invalidMove2() {
		
		setupTest(new int[] {
				1, // 2 players
				4 //End game
		});
		ClueGame.useTestingLogic = true;
		
		ClueGame game = new ClueGame(false);
		
		assertFalse(game.board.movePlayer(game.players[0], Board.LEFT));
		
	}
	
	public @Test void invalidMove3() { //Tests if the game disallows players occupying the same space
		
		setupTest(new int[] {
				2, // 2 players
				4 //End game
		});
		ClueGame.useTestingLogic = true;
		
		ClueGame game = new ClueGame(false);
		game.board.movePlayerToRoom(game.players[1], Location.LocName.LOUNGE);
		
		assertTrue("The player is in the " + game.players[1].getCurrentRoom().name() ,game.players[1].getCurrentRoom().name().equals("LOUNGE"));
		game.board.movePlayer(game.players[1], Board.RIGHT);
		game.board.movePlayer(game.players[1], Board.RIGHT);
		game.board.movePlayer(game.players[0], Board.UP);
		game.board.movePlayer(game.players[0], Board.UP);
		game.board.movePlayer(game.players[0], Board.UP);
		game.board.movePlayer(game.players[0], Board.UP);
		game.board.movePlayer(game.players[0], Board.UP);
		assertFalse(game.board.renderBoard(), game.board.movePlayer(game.players[0], Board.UP));
		
		
	}
	
	public @Test void invalidMove4() {
		
		setupTest(new int[] {
				1, // 2 players
				4 //End game
		});
		ClueGame.useTestingLogic = true;
		
		ClueGame game = new ClueGame(false);
		game.board.movePlayer(game.players[0], Board.RIGHT);
		assertFalse(game.board.movePlayer(game.players[0], Board.RIGHT));
		
	}
	
	
	public @Test void rooms1() {
		
		setupTest(new int[] {
				1, // 1 player
				1,// move
				1, 1, 1, 1, 1, 1, 1, 1, 4, 1, //Move
				5, //End movement
				4 //End game
		});
		ClueGame.useTestingLogic = true;
		
		ClueGame game = new ClueGame(false);
		
		assertTrue("The player is in the " + game.players[0].getCurrentRoom().name() ,game.players[0].getCurrentRoom().name().equals("DINING_ROOM"));
		
	}
	
	public @Test void rooms2() {
		
		setupTest(new int[] {
				1, // 1 player
				1,// move
				1, 1, 1, 1, 1, 4, //Move 
				5, //End movement
				4 //End game
		});
		ClueGame.useTestingLogic = true;
		
		ClueGame game = new ClueGame(false);
		
		assertTrue("The player is in the " + game.players[0].getCurrentRoom().name() ,game.players[0].getCurrentRoom().name().equals("LOUNGE"));
		
	}
	
	public @Test void rooms3() {
		
		setupTest(new int[] {
				1, // 1 player
				4 //End game
		});
		ClueGame.useTestingLogic = true;
		
		ClueGame game = new ClueGame(false);
		game.board.movePlayerToRoom(game.activePlayer, Location.LocName.LOUNGE);
		
		assertTrue("The player is in the " + game.players[0].getCurrentRoom().name() ,game.players[0].getCurrentRoom().name().equals("LOUNGE"));
		
	}
	
	public @Test void winning1() {
		setupTest(new int[] {
				1, // 1 player

				4 //End game
		});
		
		ClueGame.useTestingLogic = true;
		
		ClueGame game = new ClueGame(false);
		game.solution = game.generateSolution(new Character(Character.CharName.Colonel_Mustard), new Location(Location.LocName.BALL_ROOM), new Weapon(Weapon.WeaponType.CANDLESTICK));
		
		
		assertTrue(game.makeAccusal(new Character(Character.CharName.Colonel_Mustard), new Location(Location.LocName.BALL_ROOM), new Weapon(Weapon.WeaponType.CANDLESTICK)));
		
	}
	
	public @Test void winning2() {
		setupTest(new int[] {
				1, // 1 player

				4 //End game
		});
		
		ClueGame.useTestingLogic = true;
		
		ClueGame game = new ClueGame(false);
		game.solution = game.generateSolution(new Character(Character.CharName.Miss_Scarlet), new Location(Location.LocName.DINING_ROOM), new Weapon(Weapon.WeaponType.DAGGER));
		
		
		assertTrue(game.makeAccusal(new Character(Character.CharName.Miss_Scarlet), new Location(Location.LocName.DINING_ROOM), new Weapon(Weapon.WeaponType.DAGGER)));
		
	}
	
	public @Test void gameEnd() {
		setupTest(new int[] {
				1, // 1 player

				4 //End game
		});
		
		ClueGame.useTestingLogic = true;
		
		ClueGame game = new ClueGame(false);
		game.solution = game.generateSolution(new Character(Character.CharName.Miss_Scarlet), new Location(Location.LocName.DINING_ROOM), new Weapon(Weapon.WeaponType.DAGGER));
		
		
		assertTrue(game.makeAccusal(new Character(Character.CharName.Miss_Scarlet), new Location(Location.LocName.DINING_ROOM), new Weapon(Weapon.WeaponType.DAGGER)));
		assertTrue(game.isEnded());
	}
	
	public @Test void playerOut() {
		setupTest(new int[] {
				1, // 1 player

				4 //End game
		});
		
		ClueGame.useTestingLogic = true;
		
		ClueGame game = new ClueGame(false);
		game.solution = game.generateSolution(new Character(Character.CharName.Miss_Scarlet), new Location(Location.LocName.DINING_ROOM), new Weapon(Weapon.WeaponType.DAGGER));
		
		
		assertFalse(game.makeAccusal(new Character(Character.CharName.Miss_Scarlet), new Location(Location.LocName.DINING_ROOM), new Weapon(Weapon.WeaponType.REVOLVER)));
		assertFalse(game.activePlayer.getActive());
	}
	
	
	public @Test void hands1() {
		setupTest(new int[] {
				1, // 1 player

				4 //End game
		});
		
		ClueGame.useTestingLogic = true;
		
		ClueGame game = new ClueGame(false);
		game.shuffleAndFill();
		
		assertTrue((game.activePlayer.contains(new Weapon(Weapon.WeaponType.CANDLESTICK))));
		assertTrue((game.activePlayer.contains(new Location(Location.LocName.BALL_ROOM))));
	}
	
	public @Test void hands2() {
		setupTest(new int[] {
				1, // 1 player

				4 //End game
		});
		
		ClueGame.useTestingLogic = true;
		
		ClueGame game = new ClueGame(false);
		game.solution = game.generateSolution(new Character(Character.CharName.Colonel_Mustard), new Location(Location.LocName.BALL_ROOM), new Weapon(Weapon.WeaponType.CANDLESTICK));
		
		assertFalse((game.activePlayer.contains(new Weapon(Weapon.WeaponType.CANDLESTICK))));
		assertFalse((game.activePlayer.contains(new Location(Location.LocName.BALL_ROOM))));
		assertFalse((game.activePlayer.contains(new Character(Character.CharName.Colonel_Mustard))));
	}
	
	public @Test void hands3() {
		setupTest(new int[] {
				1, // 1 player

				4 //End game
		});
		
		ClueGame.useTestingLogic = true;
		
		ClueGame game = new ClueGame(false);
		game.solution = game.generateSolution(new Character(Character.CharName.Miss_Scarlet), new Location(Location.LocName.DINING_ROOM), new Weapon(Weapon.WeaponType.DAGGER));
		
		assertFalse((game.activePlayer.contains(new Weapon(Weapon.WeaponType.DAGGER))));
		assertFalse((game.activePlayer.contains(new Location(Location.LocName.DINING_ROOM))));
		assertFalse((game.activePlayer.contains(new Character(Character.CharName.Miss_Scarlet))));
	}
	
	public @Test void suggestMove() {
		setupTest(new int[] {
				2, // 2 player

				4 //End game
		});
		
		ClueGame.useTestingLogic = true;
		
		ClueGame game = new ClueGame(true);
		game.board.movePlayerToRoom(game.players[0],Location.LocName.LOUNGE);
		game.makeSuggest(game.players[0].getCurrentRoom(), new Weapon(Weapon.WeaponType.CANDLESTICK),  new Character (Character.CharName.Colonel_Mustard));
		assertEquals( Location.LocName.LOUNGE, game.players[1].getCurrentRoom());
	}
	
	public @Test void suggestMove2() {
		setupTest(new int[] {
				3, // 3 player

				4 //End game
		});
		
		ClueGame.useTestingLogic = true;
		
		ClueGame game = new ClueGame(true);
		game.board.movePlayerToRoom(game.players[0],Location.LocName.DINING_ROOM);
		game.makeSuggest(game.players[0].getCurrentRoom(), new Weapon(Weapon.WeaponType.CANDLESTICK),  new Character (Character.CharName.Miss_Scarlet));
		assertEquals(Location.LocName.DINING_ROOM, game.players[2].getCurrentRoom());
	}
	
	private void setupTest(int[] inputs){
		queue = new LinkedList<Integer>();
		for (int i : inputs)
			queue.offer(i);
	}
}
