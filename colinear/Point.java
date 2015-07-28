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

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}
