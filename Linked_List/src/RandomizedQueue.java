
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

/**
 *
 * @author Mika
 * @param <Item>
 */
public class RandomizedQueue<Item> implements Iterable {

    // construct an empty randomized queue
    public RandomizedQueue() {
        m_data = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return m_size <= 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return m_size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (m_size >= m_capacity) {
            resize(m_capacity * 2);
        }

        ++m_size;

        int RandomPosition = 0;
        if (m_size > 1) {
            RandomPosition = StdRandom.uniform(0, m_size - 1);
        }

        if (RandomPosition != m_size - 1) {
            m_data[m_size - 1] = m_data[RandomPosition];
        }

        m_data[RandomPosition] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (m_size <= 0) {
            throw new NoSuchElementException();
        }

        Item item = m_data[--m_size];
        m_data[m_size] = null;

        if (m_size > 0 && m_size == m_capacity / 4) {
            resize(m_capacity / 2);
        }

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (m_size <= 0) {
            throw new NoSuchElementException();
        }

        int RandomPosition = 0;
        if (m_size > 1) {
            RandomPosition = StdRandom.uniform(0, m_size - 1);
        }

        return m_data[RandomPosition];
    }

    //return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {
        return new RandomIterator(m_data, m_size);
    }

    private void resize(int new_size) {
        Item[] new_array = (Item[]) new Object[new_size];
        System.arraycopy(m_data, 0, new_array, 0, m_size);
        m_data = new_array;
        m_capacity = new_size;
    }

    private class RandomIterator implements Iterator<Item> {

        RandomIterator(Item[] data, int size) {
            m_data = data;
            m_size = size;

            StdRandom.shuffle(m_data);
        }

        @Override
        public Item next() {
            if (m_current >= m_size) {
                throw new NoSuchElementException();
            }

            return m_data[m_current++];
        }

        @Override
        public boolean hasNext() {
            return m_current < m_size;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        Item[] m_data;
        int m_size = 0;
        int m_current = 0;
    }

    Item[] m_data;
    int m_size = 0;
    int m_capacity = 1;

    public static void main(String args[]) {
        RandomizedQueue<Integer> RandomQueue = new RandomizedQueue<>();

        for (int i = 0; i < 10; i++) {
            RandomQueue.enqueue(i);
        }

        for (Iterator it = RandomQueue.iterator(); it.hasNext();) {
            Object Item = it.next();
            System.out.println(Item + " ");
        }
            
       

    }

}
