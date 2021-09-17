
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {

        if (n <= 0) {
            throw new IllegalArgumentException("invalid arguments");
        }

        m_grid = new boolean[(n * n)];
        m_side_size = n;
        m_data = new WeightedQuickUnionUF(n * n + 2);

        for (int i = 0; i < size(); i++) {
            m_grid[i] = false;
        }

        for (int i = 0; i < n; i++) {
            m_data.union(i, size());
            m_data.union(i + n * (n - 1), size() + 1);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        check_arguments(row, col);
        if (m_grid[(col - 1) + (row - 1) * m_side_size]) {
            return;
        }

        m_open_sites++;
        m_grid[(col - 1) + (row - 1) * m_side_size] = true;
        add_tiles_to_union((row - 1), (col - 1));
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

        check_arguments(row, col);
        return m_grid[(col - 1) + (row - 1) * m_side_size];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {

        check_arguments(row, col);
        return isOpen(row, col) && m_data.find((col - 1) + (row - 1) * m_side_size) == m_data.find(size());
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return m_open_sites;
    }

    // does the system percolate?
    public boolean percolates() {
        return m_data.find(size()) == m_data.find(size()+1);
    }

    private int size() {
        return m_side_size * m_side_size;
    }

    private void check_arguments(int row, int col) {
        if (col < 1 || col > m_side_size || row < 1 || row > m_side_size) {
            throw new IllegalArgumentException("invalid arguments");
        }
    }

    private void add_tiles_to_union(int row, int col) {

        if (col != 0) {
            if (m_grid[(col - 1) + row * m_side_size]) {
                m_data.union(col + row * m_side_size, (col - 1) + row * m_side_size);
            }
        }

        if (col < m_side_size - 1) {
            if (m_grid[(col + 1) + row * m_side_size]) {
                m_data.union(col + row * m_side_size, (col + 1) + row * m_side_size);
            }
        }

        if (col + (row - 1) * m_side_size >= 0 && m_grid[col + (row - 1) * m_side_size]) {
            m_data.union(col + row * m_side_size, col + (row - 1) * m_side_size);
        }

        if (col + (row + 1) * m_side_size < size() && m_grid[col + (row + 1) * m_side_size]) {
            m_data.union(col + row * m_side_size, col + (row + 1) * m_side_size);
        }
    }

    private final boolean[] m_grid;
    private WeightedQuickUnionUF m_data;
    private int m_side_size = 0;
    private int m_open_sites = 0;

    /*public static void main(String[] args) {
        Percolation test = new Percolation(5);
        test.open(1, 2);
        test.open(2, 2);
        test.open(3, 2);
        test.open(4, 2);
       

        System.out.println(test.isOpen(1, 2));
        System.out.println(test.isFull(4, 2));
        System.out.println(test.percolates());

    }*/

}
