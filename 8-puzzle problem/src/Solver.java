/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import edu.princeton.cs.algs4.MinPQ;
import java.util.Iterator;

/**
 *
 * @author Mika
 */
public class Solver {

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        
        if(initial == null)
        {
            throw new IllegalArgumentException("argument for Solver was null");
        }

        MinPQ<Node> queue = new MinPQ<>();
        MinPQ<Node> twin_queue = new MinPQ<>();

        Node current_node = new Node(initial, null, 0);
        Node twin_current_node = new Node(initial.twin(), null, 0);

        queue.insert(current_node);
        twin_queue.insert(twin_current_node);

        while (!current_node.board.isGoal() && !twin_current_node.board.isGoal()) {
            for (Board neighbor : current_node.board.neighbors()) {
                if (current_node.previous == null || !current_node.previous.board.equals(neighbor)) {
                    queue.insert(new Node(neighbor, current_node, current_node.moves + 1));
                }
            }
            current_node = queue.delMin();

            for (Board neighbor : twin_current_node.board.neighbors()) {
                if (twin_current_node.previous == null || !twin_current_node.previous.board.equals(neighbor)) {
                    twin_queue.insert(new Node(neighbor, twin_current_node, twin_current_node.moves + 1));
                }
            }
            twin_current_node = twin_queue.delMin();
        }

        if (twin_current_node.board.isGoal()) {
            return;
        }

        m_found = true;
        m_moves = new Board[10000];
        while (current_node != null) {
            m_moves[m_move_amount++] = current_node.board;
            current_node = current_node.previous;
        }

        Board[] reverse_array = new Board[m_move_amount];
        int amount = 0;
        for (int i = m_move_amount - 1; i >= 0; i--) {
            reverse_array[amount++] = m_moves[i];
        }
        m_moves = reverse_array;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return m_found;

    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {

        if (!isSolvable()) {
            return -1;
        }

        return m_moves.length - 1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {

        if (!m_found) {
            return null;
        }

        return new Solution(m_moves);

    }

    private static class Node implements Comparable {

        Node(Board board, Node previous, int moves) {
            this.board = board;
            priority = board.manhattan() + moves;
            this.moves = moves;
            this.previous = previous;
        }

        @Override
        public int compareTo(Object o) {
            Node other = (Node) o;

            if (priority > other.priority) {
                return 1;
            } else if (priority < other.priority) {
                return -1;
            } else {
                return 0;
            }
        }

        private final int priority;
        private final int moves;
        private final Board board;
        private final Node previous;
    }

    private static class Solution implements Iterable<Board> {

        Solution(Board[] solution) {
            this.solution = solution;
        }

        @Override
        public Iterator iterator() {
            return new SolutionIterator(solution);
        }

        final Board[] solution;
    }

    private static class SolutionIterator implements Iterator<Board> {

        SolutionIterator(Board[] solution) {
            this.solution = solution;
        }

        @Override
        public boolean hasNext() {
            return current < solution.length && solution[current] != null;
        }

        @Override
        public Board next() {
            return solution[current++];
        }

        final Board[] solution;
        int current;
    }

    private Board[] m_moves;
    private int m_move_amount;
    private boolean m_found = false;

    // test client (see below) 
    public static void main(String[] args) {

        int[][] tiles = new int[3][3];

        tiles[0][0] = 0;
        tiles[0][1] = 1;
        tiles[0][2] = 2;

        tiles[1][0] = 8;
        tiles[1][1] = 3;
        tiles[1][2] = 5;

        tiles[2][0] = 6;
        tiles[2][1] = 7;
        tiles[2][2] = 4;

        Board test_board = new Board(tiles);
        Solver test = new Solver(test_board);

        for (Board solution : test.solution()) {
            System.out.println(solution);
        }

    }
}
