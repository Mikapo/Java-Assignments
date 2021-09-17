/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 *
 * @author Mika
 */
public class RandomWord {
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        String champion = "";
        int i = 1;
        while (!StdIn.isEmpty()) 
        {
            String word = StdIn.readString();
            double p = 1 / i;
            if (StdRandom.bernoulli(p)) 
            {
                champion = word;
            }
            i++;
        }   
        System.out.println(champion);
    }
}
 
