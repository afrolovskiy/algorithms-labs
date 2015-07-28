/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.lang.Double;
import java.util.Comparator;

public class Point implements Comparable<Point> {
    public final Comparator<Point> SLOPE_ORDER = new BySlope();

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public double slopeTo(Point that) {
        int dx = that.x - this.x;
        int dy = that.y - this.y;

        if (dx == 0 && dy == 0) {
            // degenerate line (between a point and itself)
            return Double.NEGATIVE_INFINITY;
        }

        if (dy == 0) {
            // horizontal line
            return 0;
        }

        if (dx == 0) {
            // vertical line
            return  Double.POSITIVE_INFINITY;
        }

        return dy / (double) dx;
    }

    private class BySlope implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            double slope1 = slopeTo(p1);
            double slope2 = slopeTo(p2);

            if (slope1 < slope2) {
                return -1;
            }

            if (slope1 > slope2) {
                return 1;
            }

            return 0;
        }
    }

    public int compareTo(Point that) {
        if (this.y < that.y) {
            return -1;
        }

        if (this.y == that.y) {
            if (this.x < that.x) {
                return -1;
            }
            if (this.x == that.x) {
                return 0;
            }
        }

        return 1;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public static void main(String[] args) {
        Point p0, p1, p2;

        // compareTo tests
        // only different y coordinates
        p0 = new Point(0, 0);
        p1 = new Point(0, 10);
        assert p0.compareTo(p1) < 0;
        assert p1.compareTo(p0) > 0;

        // only different x coordinates
        p0 = new Point(0, 0);
        p1 = new Point(10, 0);
        assert p0.compareTo(p1) < 0;
        assert p1.compareTo(p0) > 0;

        // same x and y coordinates
        p0 = new Point(0, 0);
        p1 = new Point(0, 0);
        assert p0.compareTo(p1) == 0;
        assert p1.compareTo(p0) == 0;

        // slopeTo tests
        // horizontal line
        p0 = new Point(0, 0);
        p1 = new Point(10, 0);
        assert p0.slopeTo(p1) == 0;

        // vertical line
        p0 = new Point(0, 0);
        p1 = new Point(0, 10);
        assert p0.slopeTo(p1) == Double.POSITIVE_INFINITY;

        // degenerate line
        p0 = new Point(0, 0);
        p1 = new Point(0, 0);
        assert p0.slopeTo(p1) == Double.NEGATIVE_INFINITY;

        // normal line
        p0 = new Point(0, 0);
        p1 = new Point(1, 1);
        assert p0.slopeTo(p1) == 1.0;


    }
}
