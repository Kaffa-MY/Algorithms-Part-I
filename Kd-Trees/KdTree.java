import java.util.ArrayList;

public class KdTree {
	private Node root;

	// construct an empty set of points
	public KdTree() {

	}

	// is the set empty?
	public boolean isEmpty() {
		return root == null;
	}

	// number of points in the set
	public int size() {
		return size(root);
	}

	// add the point to the set (if it is not already in the set)
	public void insert(Point2D p) {
		root = insert(root, p);
	}

	// does the set contain point p?
	public boolean contains(Point2D p) {
		Node x = root;
		while (x != null) {
			int cmp = p.compareTo(x.point);
			if (cmp < 0)
				x = x.left;
			else if (cmp > 0)
				x = x.right;
			else if (cmp == 0)
				return true;
		}
		return false;
	}

	// draw all points to standard draw
	public void draw() {
		Queue<Point2D> queue = (Queue<Point2D>) getPointSet();
		for (Point2D point : queue) {
			point.draw();
		}
	}

	// all points that are inside the rectangle
	public Iterable<Point2D> range(RectHV rect) {
		ArrayList<Point2D> list = new ArrayList<Point2D>();
		Queue<Point2D> queue = (Queue<Point2D>) getPointSet();
		for (Point2D point : queue) {
			if (rect.contains(point))
				list.add(point);
		}
		return list;
	}

	// a nearest neighbor in the set to point p; null if the set is empty
	public Point2D nearest(Point2D p) {
		Point2D floorPoint2d = floor(p);
		Point2D ceilingPoint2d = ceiling(p);
		if (floorPoint2d != null && ceilingPoint2d == null)
			return floorPoint2d;
		if (floorPoint2d == null && ceilingPoint2d != null)
			return ceilingPoint2d;
		if (floorPoint2d == null && ceilingPoint2d == null)
			return null;
		double floorDist = floorPoint2d.distanceTo(p);
		double ceilingDist = ceilingPoint2d.distanceTo(p);
		if (floorDist < ceilingDist)
			return floorPoint2d;
		else
			return ceilingPoint2d;
	}

	private Point2D floor(Point2D point) {
		Node x = floor(root, point);
		if (x == null)
			return null;
		return x.point;
	}

	private Point2D ceiling(Point2D point) {
		Node x = ceiling(root, point);
		if (x == null)
			return null;
		return x.point;
	}

	private Node floor(Node x, Point2D p) {
		if (x == null)
			return null;
		int cmp = p.compareTo(x.point);
		if (cmp == 0)
			return x;
		if (cmp < 0)
			return floor(x.left, p);
		Node t = floor(x.right, p);
		if (t != null)
			return t;
		else
			return x;
	}

	private Node ceiling(Node x, Point2D p) {
		if (x == null)
			return null;
		int cmp = p.compareTo(x.point);
		if (cmp == 0)
			return x;
		if (cmp > 0)
			return ceiling(x.right, p);
		Node t = ceiling(x.left, p);
		if (t != null)
			return t;
		else
			return x;
	}

	private Iterable<Point2D> getPointSet() {
		Queue<Point2D> queue = new Queue<Point2D>();
		inorder(root, queue);
		return queue;
	}

	private void inorder(Node x, Queue<Point2D> q) {
		if (x == null)
			return;
		inorder(x.left, q);
		q.enqueue(x.point);
		inorder(x.right, q);
	}

	private Node insert(Node x, Point2D p) {
		if (x == null)
			return new Node(p);
		int cmp = p.compareTo(x.point);
		if (cmp < 0)
			x.left = insert(x.left, p);
		else if (cmp > 0)
			x.right = insert(x.right, p);
		else if (cmp == 0)
			x.point = p;
		x.count = 1 + size(x.left) + size(x.right);
		return x;
	}

	private int size(Node x) {
		if (x == null)
			return 0;
		return x.count;
	}

	private class Node {
		private Point2D point;
		private Node left, right;
		private int count;

		public Node(Point2D p) {
			this.point = p;
		}
	}

	// unit testing of the methods (optional)
	public static void main(String[] args) {
	}
}