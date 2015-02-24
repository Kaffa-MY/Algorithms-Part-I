import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Brute {
	public static void main(String[] args) {
		final int MAXVALUE = 32768;
		StdDraw.setXscale(0, MAXVALUE);
		StdDraw.setYscale(0, MAXVALUE);
		StdDraw.show(0);

		String filename = args[0];
		In file = new In(filename);
		int counter = file.readInt();
		ArrayList<Point> points = new ArrayList<Point>();
		for (int i = 0; i < counter; i++) {
			int x = file.readInt();
			int y = file.readInt();
			Point p = new Point(x, y);
			points.add(p);
			p.draw();
		}

		Collections.sort(points, new Comparator<Point>() {
			@Override
			public int compare(Point o1, Point o2) {
				// TODO Auto-generated method stub
				return o1.compareTo(o2);
			}
		});

		for (int i = 0; i < counter - 3; i++) {
			for (int j = i + 1; j < counter - 2; j++) {
				double slope1 = points.get(i).slopeTo(points.get(j));
				for (int k = j + 1; k < counter - 1; k++) {
					double slope2 = points.get(j).slopeTo(points.get(k));
					if (slope1 != slope2)
						continue;
					for (int l = k + 1; l < counter; l++) {
						double slope3 = points.get(k).slopeTo(points.get(l));
						if (slope2 != slope3)
							continue;
//						ArrayList<Point> pointsInLine = new ArrayList<Point>();
//						pointsInLine.add(points.get(i));
//						pointsInLine.add(points.get(j));
//						pointsInLine.add(points.get(k));
//						pointsInLine.add(points.get(l));
						StdOut.print(points.get(i).toString() + " -> ");
						StdOut.print(points.get(j).toString() + " -> ");
						StdOut.print(points.get(k).toString() + " -> ");
						StdOut.print(points.get(l).toString() + "\n");
						points.get(i).drawTo(points.get(l));
					}
				}
			}
		}
		StdDraw.show(0);
	}
}