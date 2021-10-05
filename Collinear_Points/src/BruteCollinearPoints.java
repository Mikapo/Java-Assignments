/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Arrays;

/**
 *
 * @author Mika
 */
public class BruteCollinearPoints {

    public BruteCollinearPoints(Point[] points) {

        m_lines = new LineSegment[points.length];

        Point[] temp_points = points.clone();
        Arrays.sort(temp_points);

        for (int a = 0; a < points.length; a++) {
            if (points[a] == null) {
                throw new IllegalArgumentException();
            }

            FindCollinearPoints(a, temp_points);
        }

    }
    // finds all line segments containing 4 points

    private void FindCollinearPoints(int a, Point[] points) {
        for (int b = 0; b < points.length; b++) {
            if (b == a) {
                continue;
            }
            if (points[a].compareTo(points[b]) == 0) {
                throw new IllegalArgumentException();
            }
            for (int c = 0; c < points.length; c++) {
                if (c == b || c == a) {
                    continue;
                }

                if (points[a] == null || points[b] == null || points[c] == null) {
                    throw new IllegalArgumentException();
                }

                if (points[a].slopeTo(points[b]) == points[a].slopeTo(points[c])) {
                    for (int d = 0; d < points.length; d++) {

                        if (points[a] == null || points[b] == null || points[c] == null || points[c] == null) {
                            throw new IllegalArgumentException();
                        }

                        if (d == c || d == b || d == a) {
                            continue;
                        }

                        if (points[a].slopeTo(points[b]) == points[a].slopeTo(points[d])) {

                            if (points[a].compareTo(points[b]) < 0 || points[a].compareTo(points[c]) < 0 || points[a].compareTo(points[d]) < 0) {
                                continue;
                            }

                            if (points[b].compareTo(points[c]) < 0 && points[b].compareTo(points[d]) < 0) {
                                m_lines[m_amount++] = new LineSegment(points[a], points[b]);
                            } else if (points[c].compareTo(points[b]) < 0 && points[c].compareTo(points[d]) < 0) {
                                m_lines[m_amount++] = new LineSegment(points[a], points[c]);
                            } else {
                                m_lines[m_amount++] = new LineSegment(points[a], points[d]);
                            }

                            return;
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return m_amount;
    }
    // the number of line segments

    public LineSegment[] segments() {
        LineSegment[] temp_lines = new LineSegment[m_amount];
        System.arraycopy(m_lines, 0, temp_lines, 0, temp_lines.length);
        return temp_lines;
    }
    // the line segments

    private final LineSegment[] m_lines;
    private int m_amount = 0;
}
