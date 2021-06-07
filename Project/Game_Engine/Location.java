package Project.Game_Engine;

public class Location implements Cloneable {

	private int x;
	private int y;

	public Location() {

		this.x = 0;
		this.y = 0;
	}

	public Location(int x, int y) {

		this.x = x;
		this.y = y;
	}

	public void setX(int x) {

		this.x = x;
	}

	public int getX() {

		return  x;
	}

	public void setY(int y) {

		this.y = y;
	}

	public int getY() {

		return  y;
	}

	public String toString() {

		return "x : " + x + ", y : " + y;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		//do a shallow copy
		return (Location) super.clone();
	}

	@Override
	public boolean equals(Object o) {

		if (o == null) return false;

		if ( this == o ) return true; //check for self-comparison

		if ( !(o instanceof Location) ) return false;

		Location in = (Location)o;

		//compare the data members
		if(this.x == in.x && this.y == in.y)
			return true;

		return false;
	}
}
