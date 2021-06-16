package com.example.cijfers.service.calculation;

import java.util.ArrayList;

public class Game {

    public static Expression findNearestSolution(ArrayList<Integer> inputSerie, int toBeReached) {
        Expression[] inputExpressions = new Expression[inputSerie.size()];
        for (int i=0; i<inputSerie.size(); ++i) {
            inputExpressions[i] = Expression.fromInt(inputSerie.get(i));
        }

        return loopAllPermutations(inputExpressions, inputExpressions.length, Expression.fromInt(toBeReached));
    }

    private static Expression loopAllPermutations(Expression[] serie, int n, Expression toBeReached) {
        Expression bestResult = Expression.MINIMUM;
        if(n == 1) {
            Expression result = findNearestSolutionForPermutation(serie, toBeReached);
            bestResult = Expression.closestToTarget(result, bestResult, toBeReached);
        } else {
            for(int i = 0; i < n-1; i++) {
                Expression result = loopAllPermutations(serie, n - 1, toBeReached);
                bestResult = Expression.closestToTarget(result, bestResult, toBeReached);
                if (bestResult.equals(toBeReached))
                    return bestResult;

                if(n % 2 == 0) {
                    swap(serie, i, n-1);
                } else {
                    swap(serie, 0, n-1);
                }
            }
            Expression result = loopAllPermutations(serie, n - 1, toBeReached);
            bestResult = Expression.closestToTarget(result, bestResult, toBeReached);
        }
        return bestResult;
    }

    private static void swap(Expression[] input, int i1, int i2) {
        Expression tmp = input[i1];
        input[i1] = input[i2];
        input[i2] = tmp;
    }


    private static Expression findNearestSolutionForPermutation(Expression[] serie, Expression toBeReached) {
        ArrayList<TreeForm> allTrees = createAllTrees(serie);
        Expression bestResult = Expression.ZERO;
        for (TreeForm treeForm: allTrees) {
            Expression result = findNearestSolutionForTreeForm(null, null, treeForm, 0, toBeReached);
            bestResult = Expression.closestToTarget(result, bestResult, toBeReached);
            if (bestResult.equals(toBeReached))
                return bestResult;
        }
        return bestResult;
    }

    private static Expression findNearestSolutionForTreeForm(Expression resultSoFar, Expression bestResult, TreeForm treeForm, int nextTreePartIndex, Expression toBeReached) {
        if (nextTreePartIndex >= treeForm.size()) {
            return bestResult;
        }

        if (nextTreePartIndex == 0) {
            for (Expression expression : treeForm.get(0).get()) {
                bestResult = expression;
                bestResult = findNearestSolutionForTreeForm(expression, bestResult, treeForm, nextTreePartIndex+1, toBeReached);
            }
            return bestResult;
        }

        for (Expression expression : treeForm.get(nextTreePartIndex).get()) {
            for (Expression.Operation op: Expression.Operation.values()) {
                if (lastTreePart(treeForm, nextTreePartIndex) && operatorCannotMakeItBetter(op, resultSoFar, toBeReached)) {
                    continue;
                }
                Expression result = resultSoFar.f(op, expression);
                if (result != null && result.isNotZero()) {
                    bestResult = Expression.closestToTarget(result, bestResult, toBeReached);
                    bestResult = findNearestSolutionForTreeForm(result, bestResult, treeForm, nextTreePartIndex + 1, toBeReached);
                }
            }
        }
        return bestResult;
    }

    private static boolean lastTreePart(TreeForm treeForm, int nextTreePartIndex) {
        return (nextTreePartIndex == treeForm.size()-1);
    }

    private static boolean operatorCannotMakeItBetter(Expression.Operation op, Expression resultSoFar, Expression toBeReached) {
        if (resultSoFar.smaller(toBeReached)) {
            if ((op == Expression.Operation.DIVIDE_LARGEST_BY_SMALLEST) || (op == Expression.Operation.SUBTRACT_LARGEST_BY_SMALLEST))
                return true;
        } else {
            if ((op == Expression.Operation.ADD) || (op == Expression.Operation.MULTIPLY))
                return true;
        }
        return false;
    }

    private static ArrayList<TreeForm> createAllTrees(Expression[] serie) {
        return createTrees(serie.length, 3, "", serie);
    }

    public static ArrayList<TreeForm> createTrees(int sizeLeft, int maxTreePartSize, String treeFormTopology, Expression[] serie) {
        ArrayList<TreeForm> allTrees = new ArrayList<>();
        if (sizeLeft == 0 && maxTreePartSize == 1) {
            allTrees.add(createTreeForm(treeFormTopology, serie));
        } else {
            for (int treePartSize = maxTreePartSize; treePartSize >= 1; --treePartSize) {
                if (sizeLeft - treePartSize >= 0) {
                    allTrees.addAll(createTrees(sizeLeft - treePartSize, treePartSize, treeFormTopology + treePartSize, serie));
                }
            }
        }
        return allTrees;
    }
    private static TreeForm createTreeForm(String treeFormTopology, Expression[] serie) {
        TreeForm treeForm = new TreeForm();
        int index=0;
        for (int treeSize=treeFormTopology.length()-1; treeSize>=0;  --treeSize) {
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
