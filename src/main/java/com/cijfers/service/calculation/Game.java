package com.cijfers.service.calculation;

import java.util.ArrayList;

public class Game {
    //fixme: conceptually it doesn't work well, when inputserie has more than 7 numbers.

    private static final boolean STOP_WHEN_SOLUTION_FOUND = false;

    public static Expression findNearestSolution(ArrayList<Integer> inputSerie, int intToBeReached) {
        Expression bestResult = Expression.MINIMUM;
        Expression toBeReached = Expression.fromInt(intToBeReached);
        Expression[] inputExpressions = new Expression[inputSerie.size()];
        for (int i=0; i<inputSerie.size(); ++i) {
            inputExpressions[i] = Expression.fromInt(inputSerie.get(i));
            bestResult = Expression.closestToTarget(inputExpressions[i], bestResult, toBeReached);
        }

        Expression newResult = loopAllPermutations(inputExpressions, inputExpressions.length, toBeReached);
        bestResult = Expression.closestToTarget(newResult, bestResult, toBeReached);
        return bestResult;
    }

    private static Expression loopAllPermutations(Expression[] serie, int n, Expression toBeReached) {
        if(n == 1) {
            return findNearestSolutionForPermutation(serie, toBeReached);
        } else {
            Expression bestResult = Expression.MINIMUM;
            for(int i = 0; i < n-1; i++) {
                Expression tmpResult = loopAllPermutations(serie, n - 1, toBeReached);
                bestResult = Expression.closestToTarget(tmpResult, bestResult, toBeReached);
                if (STOP_WHEN_SOLUTION_FOUND && bestResult.equals(toBeReached))
                    return bestResult;

                if(n % 2 == 0) {
                    swap(serie, i, n-1);
                } else {
                    swap(serie, 0, n-1);
                }
            }
            Expression tmpResult = loopAllPermutations(serie, n - 1, toBeReached);
            bestResult = Expression.closestToTarget(tmpResult, bestResult, toBeReached);
            return bestResult;
        }
    }

    private static void swap(Expression[] input, int i1, int i2) {
        Expression tmp = input[i1];
        input[i1] = input[i2];
        input[i2] = tmp;
    }


    private static Expression findNearestSolutionForPermutation(Expression[] serie, Expression toBeReached) {
        ArrayList<TreeForm> allTrees = createAllTrees(serie);
        Expression bestResult = Expression.MINIMUM;
        for (TreeForm treeForm: allTrees) {
            Expression result = findNearestSolutionForTreeForm(null, treeForm, 0, toBeReached);
            bestResult = Expression.closestToTarget(result, bestResult, toBeReached);
            if (STOP_WHEN_SOLUTION_FOUND && bestResult.equals(toBeReached))
                return bestResult;
        }
        return bestResult;
    }

    private static Expression findNearestSolutionForTreeForm(Expression resultSoFar, TreeForm treeForm, int nextTreePartIndex, Expression toBeReached) {
        if (nextTreePartIndex >= treeForm.size()) {
            return resultSoFar;
        }

        Expression bestResult = resultSoFar;
        if (nextTreePartIndex == 0) {
            for (Expression expression : treeForm.get(0).get()) {
                Expression tmpResult = findNearestSolutionForTreeForm(expression, treeForm, nextTreePartIndex+1, toBeReached);
                bestResult = Expression.closestToTarget(bestResult, tmpResult, toBeReached);
            }
        } else {
            for (Expression expression : treeForm.get(nextTreePartIndex).get()) {
                for (Expression.Operation op : Expression.Operation.values()) {
                    if (lastTreePart(treeForm, nextTreePartIndex) && operatorCannotMakeItBetter(op, resultSoFar, toBeReached)) {
                        continue;
                    }
                    Expression newResult = resultSoFar.f(op, expression);
                    if (newResult != null && newResult.isNotZero()) {
                        Expression tmpResult = findNearestSolutionForTreeForm(newResult, treeForm, nextTreePartIndex + 1, toBeReached);
                        bestResult = Expression.closestToTarget(bestResult, tmpResult, toBeReached);
                    }
                }
            }
        }
        return bestResult;
    }

    private static boolean lastTreePart(TreeForm treeForm, int nextTreePartIndex) {
        return (nextTreePartIndex == treeForm.size()-1);
    }

    private static boolean operatorCannotMakeItBetter(Expression.Operation op, Expression resultSoFar, Expression toBeReached) {
        if (resultSoFar.equals(toBeReached)) {
            return false;
        } else if (resultSoFar.smaller(toBeReached)) {
            return (op == Expression.Operation.DIVIDE_LARGEST_BY_SMALLEST) || (op == Expression.Operation.SUBTRACT_LARGEST_BY_SMALLEST);
        } else {
            return (op == Expression.Operation.ADD) || (op == Expression.Operation.MULTIPLY);
        }
    }

    private static ArrayList<TreeForm> createAllTrees(Expression[] serie) {
        int biggestTreePart = Integer.min(3, serie.length-1);
        return createTrees(Integer.toString(serie.length), serie, biggestTreePart);
    }

    private static ArrayList<TreeForm> createTrees(String treeFormTopology, Expression[] serie, int biggestTreePart ) {
        ArrayList<TreeForm> allTrees = new ArrayList<>();
        int indexBigTreePart = getFirstTooBigTreePart(treeFormTopology, biggestTreePart);
        if (indexBigTreePart < 0) {
            allTrees.add(createTreeForm(treeFormTopology, serie));
        } else {
            char splitChar = treeFormTopology.charAt(indexBigTreePart);
            int splitNumber = splitChar - '0';
            for (int left=splitNumber-1; left >= (splitNumber-left); left--) {
                int right = splitNumber - left;
                String newTreeForm = treeFormTopology.substring(0,indexBigTreePart) + left + right + treeFormTopology.substring(indexBigTreePart+1);
                allTrees.addAll(createTrees(newTreeForm, serie, biggestTreePart));
            }
        }
        return allTrees;
    }

    private static int getFirstTooBigTreePart(String tree, int biggestTreePart ) {
        for (int i=0; i<tree.length(); ++i) {
            if (tree.charAt(i) - '0' > biggestTreePart)
                return i;
        }
        return -1;
    }

    private static TreeForm createTreeForm(String treeFormTopology, Expression[] serie) {
        TreeForm treeForm = new TreeForm();
        int index=0;
        for (int treeSize=0; treeSize < treeFormTopology.length(); ++treeSize) {
            switch(treeFormTopology.charAt(treeSize)) {
                case '3':
                    treeForm.add(new TreePart(serie[index++], serie[index++], serie[index++]));
                    break;
                case '2':
                    treeForm.add(new TreePart(serie[index++], serie[index++]));
                    break;
                case '1':
                    treeForm.add(new TreePart(serie[index++]));
                    break;
            }
        }
        return treeForm;
    }


}
