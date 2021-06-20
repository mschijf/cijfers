package com.cijfers.service.inputRules;

import java.util.ArrayList;
import java.util.Random;

public class RandomToBeReached extends ArrayList<Integer> {
    private static Random rnd = new Random();

    public static int get() {
        return 100 + rnd.nextInt(900);
    }
}
