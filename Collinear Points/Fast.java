import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Fast {
	final private static double zero = 0.0;
	final private static int MAXVALUE = 32768;

	private static void sort(ArrayList<Point> list, final Point org) {
		Collections.sort(list, new Comparator<Point>() {
			@Override
			public int compare(Point o1, Point o2) {
				// TODO Auto-generated method stub
				return org.SLOPE_ORDER.compare(o1, o2);
			}
		});
	}

	private static Map<String, ArrayList<Point>> getLines(
			ArrayList<Point> list, Point referencePoint) {
		Map<String, ArrayList<Point>> pointsInLine = new HashMap<String, ArrayList<Point>>();
		double currentSlope;
		Point currentPoint;
		String currentKey;
		for (int i = 0; i < list.size(); i++) {
			currentPoint = list.get(i);
			currentSlope = referencePoint.slopeTo(currentPoint);
			if (currentSlope == zero)
				currentKey = zero + "";
			else
				currentKey = currentSlope + "";
			// currentKey = String.valueOf(currentSlope);
			// currentKey = referencePoint.toString() + currentKey;
			if (currentPoint.compareTo(referencePoint) == 0)
				continue;
			if (pointsInLine.keySet().contains(currentKey)) {
				if (!pointsInLine.get(currentKey).contains(currentPoint))
					pointsInLine.get(currentKey).add(currentPoint);
			} else {
				ArrayList<Point> points = new ArrayList<Point>();
				points.add(referencePoint);
				points.add(currentPoint);
				pointsInLine.put(currentKey, points);
			}
		}
		return pointsInLine;
	}

	public static void main(String[] args) {
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

		Map<String, Boolean> hasDraw = new HashMap<String, Boolean>();
		Map<String, ArrayList<Point>> lines = null;
		for (int i = 0; i < counter; i++) {
			sort(points, points.get(i));
			lines = getLines(points, points.get(i));
			Iterator<String> iterator = lines.keySet().iterator();
			ArrayList<Point> pointsInLine = null;
			while (iterator.hasNext()) {
				pointsInLine = lines.get(iterator.next());
				if (pointsInLine.size() >= 4) {
					Collections.sort(pointsInLine, new Comparator<Point>() {
						@Override
						public int compare(Point o1, Point o2) {
							// TODO Auto-generated method stub
							return o1.compareTo(o2);
						}
					});
					// double slope = pointsInLine.get(pointsInLine.size() - 1)
					// .slopeTo(pointsInLine.get(0));
					String keyPoints;
					// if (slope == zero)
					// keySlope = zero + "";
					// else
					// keySlope = String.valueOf(slope);
					keyPoints = pointsInLine.get(0).toString()
							+ pointsInLine.get(pointsInLine.size() - 1);
					if (hasDraw.keySet().contains(keyPoints))
						continue;
					for (int j = 0; j < pointsInLine.size() - 1; j++)
						StdOut.print(pointsInLine.get(j).toString() + " -> ");
					StdOut.println(pointsInLine.get(pointsInLine.size() - 1)
							.toString());
					pointsInLine.get(0).drawTo(
							pointsInLine.get(pointsInLine.size() - 1));
					hasDraw.put(keyPoints, true);
				}
			}
		}
		StdDraw.show(0);
	}
}