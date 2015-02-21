import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Fast {
	private static void sort(ArrayList<Point> list, Point org) {
		Collections.sort(list, new Comparator<Point>() {
			@Override
			public int compare(Point o1, Point o2) {
				// TODO Auto-generated method stub
				return org.SLOPE_ORDER.compare(o1, o2);
			}
		});
	}

	private static void drawLine(ArrayList<Point> list, Point rp) {
		double current_slope = Double.NaN;
		ArrayList<Point> line = new ArrayList<Point>();
		line.add(rp);
		for (int i = 0; i < list.size(); i++) {
			Point current_point = list.get(i);
			if (current_slope == rp.slopeTo(current_point)) {
				line.add(current_point);
			} else {
				if (line.size() >= 3) {
					Collections.sort(line, new Comparator<Point>() {

						@Override
						public int compare(Point o1, Point o2) {
							// TODO Auto-generated method stub
							return o1.compareTo(o2);
						}
					});
					for (int j = 0; j < line.size() - 1; j++)
						StdOut.print(line.get(j) + " -> ");
					StdOut.println(line.get(line.size() - 1));
					line.get(0).drawTo(line.get(line.size() - 1));
				}
				current_slope = rp.slopeTo(current_point);
				line.clear();
				line.add(rp);
				line.add(current_point);
			}
		}
	}

	public static void main(String[] args) {
		final int MAXVALUE = 65535;
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
		
		for (int i = 0; i < counter; i++) {
			sort(points, points.get(i));
			drawLine(points, points.get(i));
		}

		StdDraw.show(0);
	}
}