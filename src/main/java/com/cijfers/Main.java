package com.cijfers;

import com.cijfers.service.calculation.Expression;
import com.cijfers.service.calculation.Game;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> serie = new ArrayList<>(Arrays.asList(3, 100, 10, 2, 5, 9));
        int toBeReached = 766;
//        ArrayList<Integer> serie = new ArrayList<>(Arrays.asList(10,5,10,10,4,100));
//        int toBeReached = 979;

        for (Integer number : serie)
            System.out.print(number + " ");
        System.out.println(" ==> " + toBeReached);

        System.out.println("Start");
        Instant start = Instant.now();
        Expression result =  Game.findNearestSolution(serie, toBeReached);
        System.out.println(result);
        System.out.println("Klaar na " + (Duration.between(start, Instant.now()).toMillis()/1000.0) + " sec.");
        System.out.println("   en na " + Expression.operationCount + " operaties");

//        int[] serie = {1, 2, 3};
//        loopAllPermutations(serie, serie.length);
    }


    private static boolean print = true;
    private static void loopAllPermutations(int[] serie, int n) {
        if(n == 1) {
            if (print) {
                for (int i = 0; i < serie.length; ++i) {
                    System.out.print(serie[i]);
                }
                System.out.println();
            }
            print = !print;
        } else {
            for(int i = 0; i < n-1; i++) {
                loopAllPermutations(serie, n - 1);
                if (n % 2 == 0) {
                    swap(serie, i, n - 1);
                } else {
                    swap(serie, 0, n - 1);
                }
            }
            loopAllPermutations(serie, n - 1);
        }
    }
    private static void swap(int[] input, int i1, int i2) {
        int tmp = input[i1];
        input[i1] = input[i2];
        input[i2] = tmp;
    }


}

