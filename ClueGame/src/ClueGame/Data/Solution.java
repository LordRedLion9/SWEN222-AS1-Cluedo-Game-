package ClueGame.Data;

public class Solution {

	private Character chara;
	private Weapon weapon;
	private Location loc;
	
	public Solution(Character chara,Location loc, Weapon weapon){
		this.chara = chara;
		this.weapon = weapon;
		this.loc = loc;
	}
	
	public Character getChar(){return chara;}
	public Weapon getWep(){return weapon;}
	public Location getLoc(){return loc;}
}
