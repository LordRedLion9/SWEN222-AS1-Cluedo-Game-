package ClueGame.Data;

public class Weapon implements Clue{

	private WeaponType type;
	
	public enum WeaponType{
		CANDLESTICK,
		DAGGER,
		LEADPIPE,
		REVOLVER,
		ROPE,
		SPANNER
	}
	
	public Weapon(WeaponType type){
		this.type = type;
	}
	
	public String getType(){
		return type.name();
	}
	
}
