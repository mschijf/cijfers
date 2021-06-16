package com.example.cijfers.service.inputRules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class RandomCijfersArrayList extends ArrayList<Integer> {
    private static ArrayList<Integer> selection = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,25,50,75,100));
    private static Random rnd = new Random();
    public RandomCijfersArrayList(int n) {
        super();
        for (int i=0; i<n; ++i) {
            this.add(selection.get(rnd.nextInt(selection.size())));
        }
    }
}
