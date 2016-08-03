package ClueGame.Data;

//class used to hold coordinate data (like a tuple)
public class Coordinate {
		
	public int row, col;
	
	public Coordinate(int row, int col){
		this.row = row;
		this.col = col;
	}
	
	@Override
	public int hashCode() {
		// don't worry, we wont need it
		return 1;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;

		if (col != other.col || row != other.row)
			return false;
		return true;
	}	
	
	public String toString(){
		return "[ROW(y) : " + row + " COL(x) : " + col + "]";
	}
}
