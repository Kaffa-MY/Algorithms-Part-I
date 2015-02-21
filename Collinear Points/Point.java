import java.util.Comparator;

public class Point implements Comparable<Point> {
	// compare points by slope to this point
	public final Comparator<Point> SLOPE_ORDER;

	private final int x;
	private final int y;

	// construct the point (x, y)
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
		SLOPE_ORDER = new SlopeOrder();
	}

	// draw this point
	public void draw() {
		StdDraw.point(x, y);
	}

	// draw the line segment from this point to that point
	public void drawTo(Point that) {
		StdDraw.line(this.x, this.y, that.x, that.y);
	}

	// string representation
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	// is this point lexicographically smaller than that point?
	public int compareTo(Point that) {
		if (this.y < that.y || ((this.y == that.y) && (this.x < that.x))) {
			return -1; // this less than that
		} else if ((this.y == that.y) && (this.x == that.x)) {
			return 0;
		} else {
			return 1;
		}
	}

	// the slope between this point and that point
	public double slopeTo(Point that) {
		if (this.x != that.x)
			return ((double) (that.y - this.y)) / (that.x - this.x);
		else if (that.y == this.y && that.x == this.x)
			return Double.NEGATIVE_INFINITY;
		else
			return Double.POSITIVE_INFINITY;
	}

	private class SlopeOrder implements Comparator<Point> {
		public int compare(Point p1, Point p2) {
			double slope1 = slopeTo(p1);
			double slope2 = slopeTo(p2);
			if (slope1 < slope2)
				return -1;
			else if (slope1 == slope2)
				return 0;
			else
				return 1;
		}
	}

	// public static void main(String[] args) {
	// Point p1 = new Point(0, 0);
	// Point p2 = new Point(2, 2);
	// Point p3 = new Point(3, 3);
	// p1.drawTo(p2);
	// System.out.println(p2.slopeTo(p3));
	// System.out.println("p2, p3 / p1 = " + p1.SLOPE_ORDER.compare(p2, p3));
	// System.out.println("p3, p1 / p2 = " + p2.SLOPE_ORDER.compare(p3, p1));
	// }
}