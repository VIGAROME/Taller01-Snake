package lab1;

/**
 * Immutable class describing integer 2D-points.
 * 
 * @author evensen
 * 
 */
public class Position {
	private final int x;
	private final int y;

	/**
	 * Creates an immutable instance of a 2D integer coordinate.
	 */
	public Position(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return The x value of the coordinate.
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * @return The x value of the coordinate.
	 */
	public int getY() {
		return this.y;
	}

	@Override
	public int hashCode() {
		return 23456789 * this.x + 56789123 * this.y;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Position other = (Position) obj;
		return this.x == other.x && this.y == other.y;
	}
}
