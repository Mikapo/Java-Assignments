
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Mika
 * @param <Item>
 */
public class Deque<Item> implements Iterable<Item> {

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return m_top == null || m_end == null;
    }

    // return the number of items on the deque
    public int size() {
        return m_size;
    }

    // add the item to the front
    public void addFirst(Item item) {

        if (item == null) {
            throw new IllegalArgumentException();
        }

        ++m_size;

        if (m_top == null) {
            m_top = new Node(item);
            m_end = m_top;
        } else {
            Node old_top = m_top;
            m_top = new Node(item);
            old_top.previous = m_top;
            m_top.next = old_top;
        }
    }

    // add the item to the back
    public void addLast(Item item) {

        if (item == null) {
            throw new IllegalArgumentException();
        }

        ++m_size;

        if (m_top == null) {
            m_top = new Node(item);
            m_end = m_top;
        } else {
            Node old_end = m_end;
            m_end = new Node(item);
            old_end.next = m_end;
            m_end.previous = old_end;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {

        if (m_top == null) {
            throw new NoSuchElementException();
        }

        --m_size;

        Item item = m_top.item;
        m_top = m_top.next;

        if (m_top != null) {
            m_top.previous = null;
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {

        if (m_end == null) {
            throw new NoSuchElementException();
        }

        --m_size;

        Item item = m_end.item;
        m_end = m_end.previous;

        if (m_end != null) {
            m_end.next = null;
        }

        return item;
    }

    // return an iterator over items in order from front to back
    @Override
    public Iterator<Item> iterator() {
        return new Deque_Iterator(m_top);
    }

    private class Deque_Iterator implements Iterator<Item> {

        Deque_Iterator(Node start) {
            current_node = start;
        }

        @Override
        public Item next() {

            if (current_node == null) {
                throw new NoSuchElementException();
            }

            Item item = current_node.item;
            current_node = current_node.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return current_node != null;
        }

        Node current_node;
    }

    private class Node {

        Node(Item new_item) {
            item = new_item;
        }

        Item item;
        Node next = null;
        Node previous = null;
    }

    private Node m_top = null;
    private Node m_end = null;
    private int m_size = 0;

    // unit testing (required)
    public static void main(String[] args) {

        Deque<Integer> deque = new Deque<>();

        deque.addFirst(1);
        deque.addLast(2);
        deque.addFirst(5);
        deque.addFirst(6);
        System.out.println(deque.isEmpty());
        deque.removeFirst();
        deque.removeLast();
        System.out.println(deque.size());

        for (int value : deque) {
            System.out.println(value);
        }

    }
}
