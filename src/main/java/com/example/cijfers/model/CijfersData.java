package com.example.cijfers.model;

import java.util.ArrayList;

public class CijfersData {
    private ArrayList<Integer> input;
    private Integer toBeReached;
    private String solution;
    private SolutionMetadata metadata;

    public CijfersData(ArrayList<Integer> input, Integer toBeReached, String solution, SolutionMetadata metadata) {
        this.input = input;
        this.toBeReached = toBeReached;
        this.solution = solution;
        this.metadata = metadata;
    }

    public ArrayList<Integer> getInput() {
        return input;
    }

    public Integer getToBeReached() {
        return toBeReached;
    }

    public String getSolution() {
        return solution;
    }

    public SolutionMetadata getMetadata() {
        return metadata;
    }
}
