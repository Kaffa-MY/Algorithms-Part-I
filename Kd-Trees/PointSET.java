public class PointSET {
	private SET<Point2D> pointSet;

	// construct an empty set of points
	public PointSET() {
		pointSet = new SET<Point2D>();
	}

	// is the set empty?
	public boolean isEmpty() {
		return pointSet.isEmpty();
	}

	// number of points in the set
	public int size() {
		return pointSet.size();
	}

	// add the point to the set (if it is not already in the set)
	public void insert(Point2D p) {
		pointSet.add(p);
	}

	// does the set contain point p?
	public boolean contains(Point2D p) {
		return pointSet.contains(p);
	}

	// draw all points to standard draw
	public void draw() {
		for (Point2D point2d : pointSet) {
			StdDraw.point(point2d.x(), point2d.y());
		}
	}

	// all points that are inside the rectangle
	public Iterable<Point2D> range(RectHV rect) {
		SET<Point2D> set = new SET<Point2D>();
		for (Point2D point2d : pointSet) {
			if (rect.contains(point2d))
				set.add(point2d);
		}
		return set;
	}

	// a nearest neighbor in the set to point p; null if the set is empty
	public Point2D nearest(Point2D p) {
		if (pointSet.isEmpty())
			return null;
		Point2D res = pointSet.max();
		for (Point2D point : pointSet) {
			if (p.distanceTo(res) > p.distanceTo(point))
				res = point;
		}
		return res;
	}

	// unit testing of the methods (optional)
	public static void main(String[] args) {
	}
}