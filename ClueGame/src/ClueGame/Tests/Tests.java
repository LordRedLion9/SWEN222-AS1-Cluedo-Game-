package ClueGame.Tests;
import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;

import ClueGame.Data.Coordinate;
import ClueGame.Main.ClueGame;
import junit.framework.TestCase;

public class Tests  {
	
	public static Queue<Integer> queue;

	
	public @Test void basicMove() {
		
		setupTest(new int[] {
				1, // 1 player
				1, // move
				1, 1, 1, 1, 1
		});
		ClueGame.useTestingLogic = true;
		ClueGame game = new ClueGame();
		assertTrue(game.activePlayer.getPosition() == new Coordinate(19, 7));
		
	}
	
	private void setupTest(int[] inputs){
		queue = new LinkedList<Integer>();
		for (int i : inputs)
			queue.offer(i);
	}
}
