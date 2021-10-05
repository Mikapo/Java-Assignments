
import edu.princeton.cs.algs4.In;
import java.util.Comparator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Mika
 */
import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point to
     * standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point. Formally,
     * if the two points are (x0, y0) and (x1, y1), then the slope is (y1 - y0)
     * / (x1 - x0). For completeness, the slope is defined to be +0.0 if the
     * line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical; and
     * Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param other
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point other) {

        if (other == null) {
            throw new NullPointerException();
        }

        if (x == other.x && y == other.y) {
            return Double.NEGATIVE_INFINITY;
        } else if (x == other.x) {
            return Double.POSITIVE_INFINITY;
        } else {
            double Y = y - other.y;
            double X = x - other.x;
            return Y / X == -0 ? 0 : Y / X;
        }
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param other
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument point
     * (x0 = x1 and y0 = y1); a negative integer if this point is less than the
     * argument point; and a positive integer if this point is greater than the
     * argument point
     */
    @Override
    public int compareTo(Point other) {

        if (other == null) {
            throw new NullPointerException();
        }

        if (x == other.x && y == other.y) {
            return 0;
        } else if (y > other.y) {
            return 1;
        } else if (y < other.y) {
            return -1;
        } else if (x > other.x) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * Compares two points by the slope they make with this point. The slope is
     * defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new slope_order();
    }

    /**
     * Returns a string representation of this point. This method is provide for
     * debugging; your program should not rely on the format of the string
     * representation.
     *
     * @return a string representation of this point
     */
    @Override
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    private class slope_order implements Comparator<Point> {

        @Override
        public int compare(Point o1, Point o2) {

            if (o1 == null || o2 == null) {
                throw new NullPointerException();
            }

            if (slopeTo(o1) > slopeTo(o2)) {
                return 1;
            } else if (slopeTo(o1) < slopeTo(o2)) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    /**
     * Unit tests the Point data type.
     *
     * @param args
     */
    public static void main(String[] args) {

        // read the n points from a file
        Point[] points = new Point[29];
        int[] value = {
            5000, 0,
            10000, 0,
            15000, 0,
            20000, 0,
            25000, 0,
            30000, 0,
            1234, 5678,
            10000, 3100,
            15000, 6200,
            20000, 9300,
            25000, 12400,
            30000, 15700,
            27000, 7500,
            26000, 10000,
            20000, 25000,
            19000, 27500,
            18000, 30000,
            0, 0,
            2300, 4100,
            4600, 8200,
            11500, 20500,
            5678, 4321,
            0, 30000,
            0, 25000,
            0, 20000,
            0, 15000,
            0, 11000,
            0, 10000,
            0, 5000
        };

        int a = 0;
        for (int i = 1; i < value.length; i += 2) {
            points[a++] = new Point(value[i - 1], value[i]);
        }

        FastCollinearPoints test = new FastCollinearPoints(points);

        for (LineSegment segment : test.segments()) {
            System.out.println(segment);
        }

    }
}
