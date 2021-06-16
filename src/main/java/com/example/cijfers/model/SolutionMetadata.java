package com.example.cijfers.model;

import java.util.ArrayList;

public class SolutionMetadata {
    private double msUsed;
    private int operationsNeeded;

    public SolutionMetadata(double msUsed, int operationsNeeded) {
        this.msUsed = msUsed;
        this.operationsNeeded = operationsNeeded;
    }

    public double getMsUsed() {
        return msUsed;
    }

    public int getOperationsNeeded() {
        return operationsNeeded;
    }
}
