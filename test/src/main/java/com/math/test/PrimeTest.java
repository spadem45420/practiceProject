package com.math.test;

import java.util.LinkedList;
import java.util.List;

public class PrimeTest {

	public static List<Integer> create(int max) {
		//LinkedList
        LinkedList<Integer> primes = new LinkedList<Integer>();
        
        //prime 2, 3, 5
        primes.add(2); primes.add(3); primes.add(5);
        
        //add 6n + 1, 6n + 5
        for(int i = 1; i <= (max - 5) / 6; i++) {
            primes.add(6 * i + 1); primes.add(6 * i + 5);
        }
        
        //??
        if(primes.getLast() + 2 <= max) {
            primes.add(primes.getLast() + 2);
        }
        
        //List all LinkedList
        for(Integer i : primes) {
        	System.out.printf("%4d", i);
        }
        System.out.println("");

        //Exclude not prime number
        for(int i = 2; i < primes.size(); i++) {
            Integer p = primes.get(i);
            for(int j = 2; j * p <= primes.getLast(); j++) {
            	
            	//Removing not prime value
            	boolean b = primes.remove(Integer.valueOf(j * p));
            	
            	System.out.println("j * p = " + j * p + " | " + b);
            }
        }
        return primes;
    }
    
    public static void main(String[] args) {
        for(Integer p : create(100)) {
            System.out.printf("%4d", p);
        }
    }

}
