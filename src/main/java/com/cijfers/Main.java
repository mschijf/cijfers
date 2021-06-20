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
//        ArrayList<Integer> serie = new ArrayList<>(Arrays.asList(1,2));
//        int toBeReached = 3;

        for (Integer number : serie)
            System.out.print(number + " ");
        System.out.println(" ==> " + toBeReached);

        System.out.println("Start");
        Instant start = Instant.now();
        Expression result =  Game.findNearestSolution(serie, toBeReached);
        System.out.println(result);
        System.out.println("Klaar na " + (Duration.between(start, Instant.now()).toMillis()/1000.0) + " sec.");
        System.out.println("   en na " + Expression.operationCount + " operaties");
    }
}

