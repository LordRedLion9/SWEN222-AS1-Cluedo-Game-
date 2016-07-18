
public class Weapon {

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
	
	public WeaponType getType(){
		return type;
	}
	
}
