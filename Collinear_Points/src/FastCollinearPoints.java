
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Mika
 */
public class FastCollinearPoints {

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

        m_segments = new LineSegment[points.length];

        Point[] temp_points = points.clone();

        for (Point point : points) {

            Arrays.sort(temp_points, point.slopeOrder());

            double current_slope = Double.NEGATIVE_INFINITY;
            int point_amount = 2;

            if (new Point(5000, 0).compareTo(point) == 0) {
                System.out.println("debug");
            }

            for (int i = 0; i < temp_points.length; i++) {

                //check if slope is same as previous one
                if (current_slope == point.slopeTo(temp_points[i])) {
                    ++point_amount;
                    continue;
                }

                //checks if line is large enough
                if (point_amount > 3) {

                    Point[] points_in_line = new Point[point_amount - 1];
                    for (int a = 0; a < point_amount - 1; a++) {
                        points_in_line[a] = temp_points[i - a - 1];
                    }

                    add_line_if_first_point_in_line(point, points_in_line);
                }

                //start checking lines again with new slope
                point_amount = 2;
                current_slope = point.slopeTo(temp_points[i]);

            }

            if (point_amount > 3) {

                Point[] points_in_line = new Point[point_amount - 1];
                for (int a = 0; a < point_amount - 1; a++) {
                    points_in_line[a] = temp_points[temp_points.length - a - 1];
                }
                add_line_if_first_point_in_line(point, points_in_line);
            }

        }
    }

    private void add_line_if_first_point_in_line(Point current_point, Point[] other_points) {

        for (Point other_point : other_points) {
            if (current_point.compareTo(other_point) > -1) {
                return;
            }
        }

        Point highest_point = current_point;
        for(Point other_point : other_points)
        {
            if(highest_point.compareTo(other_point) < 1)
                highest_point = other_point;
        }
        
        m_segments[m_amount++] = new LineSegment(current_point, highest_point);
        
    }

    // the number of line segments 
    public int numberOfSegments() {
        return m_amount;
    }

    // the line segments       
    public LineSegment[] segments() {
        LineSegment[] temp_segments = new LineSegment[m_amount];
        System.arraycopy(m_segments, 0, temp_segments, 0, temp_segments.length);
        return temp_segments;
    }

    private final LineSegment[] m_segments;
    private int m_amount = 0;
}
