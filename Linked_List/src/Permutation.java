/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import edu.princeton.cs.algs4.StdIn;

/**
 *
 * @author Mika
 */
public class Permutation {

    public static void main(String args[]) {
        RandomizedQueue<String> RandomQueue = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            RandomQueue.enqueue(StdIn.readString());
        }

        for (int i = 0; i < Integer.parseInt(args[0]); i++) {
            System.out.println(RandomQueue.dequeue());
        }

    }
}
