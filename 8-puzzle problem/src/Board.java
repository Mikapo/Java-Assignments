
import java.util.Iterator;
import java.util.Arrays;

import edu.princeton.cs.algs4.StdRandom;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Mika
 */
public class Board {

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {

        m_rows = new int[tiles.length * tiles.length];
        m_columns = new int[tiles.length * tiles.length];
        
        m_tiles = new int[tiles.length][tiles.length];

        int tile = 0;
        for (int a = 0; a < tiles.length; a++) {
            m_size += tiles[a].length;

            for (int b = 0; b < tiles[a].length; b++) {
                m_rows[tile] = a;
                m_columns[tile] = b;
                m_tiles[a][b] = tiles[a][b];
                ++tile;
            }
        }
    }

    private Board(Board initial) {
        m_tiles = Arrays.stream(initial.m_tiles).map(int[]::clone).toArray(int[][]::new);
    }

    // string representation of this board
    @Override
    public String toString() {
        String output;

        output = dimension() + "\n";

        for (int[] row : m_tiles) {
            for (int column : row) {
                output += " " + column;
            }
            output += "\n";
        }

        return output;

    }

    // board dimension n
    public int dimension() {
        return m_tiles.length;
    }

    // number of tiles out of place
    public int hamming() {

        int output = 0;

        for (int a = 0; a < m_tiles.length; a++) {
            for (int b = 0; b < m_tiles[a].length; b++) {

                if (a == m_tiles.length - 1 && b == m_tiles[a].length - 1) {
                    if (m_tiles[a][b] != 0) {
                        ++output;
                    }
                } else if (m_tiles[a][b] != (b + dimension() * a) + 1 && m_tiles[a][b] != 0) {
                    ++output;
                }
            }
        }

        return output;

    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {

        int output = 0;

        for (int a = 0; a < m_tiles.length; a++) {
            for (int b = 0; b < m_tiles[a].length; b++) {

                if (m_tiles[a][b] != (b + dimension() * a) + 1 && m_tiles[a][b] != 0) {

                    int correct_row = m_rows[m_tiles[a][b] - 1];
                    int correct_column = m_columns[m_tiles[a][b] - 1];

                    output += Math.abs(correct_row - a) + Math.abs(correct_column - b);
                }
            }
        }
        return output;

    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    @Override
    public boolean equals(Object y) {
        
        if(y == null)
        {
            return false;
        }

        if (y.getClass() != getClass()) {
            return false;
        }

        Board other = (Board) y;
        if (dimension() != other.dimension()) {
            return false;
        }

        for (int a = 0; a < m_tiles.length; a++) {
            for (int b = 0; b < m_tiles[a].length; b++) {
                if (m_tiles[a][b] != other.m_tiles[a][b]) {
                    return false;
                }
            }
        }

        return true;

    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        Board[] neighbors = new Board[4];
        int amount = 0;

        for (int a = 0; a < m_tiles.length; a++) {
            for (int b = 0; b < m_tiles[a].length; b++) {

                if (m_tiles[a][b] == 0) {

                    if (b != 0) {
                        int[][] temp = Arrays.stream(m_tiles).map(int[]::clone).toArray(int[][]::new);
                        swap(temp, a, b, a, b - 1);
                        neighbors[amount++] = new Board(temp);
                    }
                    if (b < m_tiles[a].length - 1) {
                        int[][] temp = Arrays.stream(m_tiles).map(int[]::clone).toArray(int[][]::new);
                        swap(temp, a, b, a, b + 1);
                        neighbors[amount++] = new Board(temp);
                    }
                    if (a != 0) {
                        int[][] temp = Arrays.stream(m_tiles).map(int[]::clone).toArray(int[][]::new);
                        swap(temp, a, b, a - 1, b);
                        neighbors[amount++] = new Board(temp);
                    }
                    if (a < m_tiles.length - 1) {
                        int[][] temp = Arrays.stream(m_tiles).map(int[]::clone).toArray(int[][]::new);
                        swap(temp, a, b, a + 1, b);
                        neighbors[amount++] = new Board(temp);
                    }
                    break;
                }
            }
        }

        return new neighbors(neighbors);
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

        int a = 0, b = 0;
        boolean found = false;
        for (; a < m_tiles.length; a++) {
            for (; b < m_tiles[a].length; b++) {
                if (m_tiles[a][b] != 0) {
                    found = true;
                    break;
                }
            }

            if (found) {
                break;
            }
        }

        if (a == m_tiles.length) {
            --a;
        }
        if (b == m_tiles.length) {
            --b;
        }

        int c = 0, d = 0;
        found = false;
        for (; c < m_tiles.length; c++) {
            for (; d < m_tiles[c].length; d++) {
                if (m_tiles[c][d] != 0 && (c != a || d != b)) {
                    found = true;
                    break;
                }
            }

            if (c == m_tiles.length) {
                --c;
            }
            if (d == m_tiles.length) {
                --d;
            }

            if (found) {
                break;
            }
        }

        int[][] temp = Arrays.stream(m_tiles).map(int[]::clone).toArray(int[][]::new);
        swap(temp, a, b, c, d);

        return new Board(temp);
    }

    private static void swap(int[][] tiles, int a, int b, int c, int d) {
        int temp = tiles[a][b];
        tiles[a][b] = tiles[c][d];
        tiles[c][d] = temp;
    }

    private static class neighbors implements Iterable<Board> {

        neighbors(Board[] neighbors) {
            boards = neighbors;
        }

        @Override
        public Iterator iterator() {
            return new iterator(boards);
        }

        private static class iterator implements Iterator<Board> {

            iterator(Board[] boards) {
                this.boards = boards;
            }

            @Override
            public boolean hasNext() {
                return current < boards.length && boards[current] != null;
            }

            @Override
            public Board next() {
                return boards[current++];
            }

            final Board[] boards;
            int current = 0;

        }

        final Board[] boards;
    }

    private final int m_tiles[][];
    private int m_size = 0;
    private int[] m_rows;
    private int[] m_columns;

    // unit testing (not graded)
    /*public static void main(String[] args) {

        int[][] tiles = new int[3][3];

        tiles[0][0] = 1;
        tiles[0][1] = 0;
        tiles[0][2] = 3;

        tiles[1][0] = 4;
        tiles[1][1] = 2;
        tiles[1][2] = 5;

        tiles[2][0] = 7;
        tiles[2][1] = 8;
        tiles[2][2] = 6;

        Board test = new Board(tiles);
        System.out.println(test);
        System.out.println("---------------");
        System.out.println(test.twin());

    }*/
}
