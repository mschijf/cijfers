package com.cijfers.service.calculation;

import static java.lang.Math.abs;


public class Expression {
    public enum Operation {
        ADD,
        SUBTRACT_LARGEST_BY_SMALLEST,
        MULTIPLY,
        DIVIDE_LARGEST_BY_SMALLEST
    }

    public static int operationCount = 0;

    int result;
    String expressionString;

    public static final Expression ZERO = fromInt(0);
    public static final Expression MINIMUM = fromInt(-999999999);

    public static Expression fromInt(int a) { return new Expression(a); }

    private Expression(int result) {
        this(result, Integer.toString(result));
    }

    private Expression(int result, String expressionString) {
        this.result = result;
        this.expressionString = expressionString;
    }

    public Expression f(Operation op, Expression other) {
        ++operationCount;
        switch(op) {
            case ADD:
                return add(other);
            case SUBTRACT_LARGEST_BY_SMALLEST:
                return absoluteDifference(other);
            case MULTIPLY:
                return multiply(other);
            case DIVIDE_LARGEST_BY_SMALLEST:
                return divideLargestBySmallest(other);
        }
        throw new ArithmeticException("Unknown Operation");
    }

    public Expression add(Expression other) {
        return new Expression(this.result + other.result, "(" + this.expressionString + " + " + other.expressionString + ")");
    }

    public Expression absoluteDifference(Expression other) {
        if (this.smaller(other)) {
            return new Expression(other.result - this.result, "(" + other.expressionString + " - " + this.expressionString + ")");
        }
        return new Expression(this.result - other.result, "(" + this.expressionString + " - " + other.expressionString + ")");
    }

    public Expression multiply(Expression other) {
        return new Expression(this.result * other.result, this.expressionString + " x " + other.expressionString);
    }

    public Expression divideLargestBySmallest(Expression other) {
        if ((other.result != 0) && (this.result % other.result == 0)) {
            return new Expression(this.result / other.result, "(" + this.expressionString + " / " + other.expressionString + ")");
        } else if ((this.result != 0) && (other.result % this.result == 0)) {
            return new Expression(other.result / this.result, "(" + other.expressionString + " / " + this.expressionString + ")");
        }
        return null;
    }

    public static Expression closestToTarget(Expression a, Expression b, Expression target) {
        if (a == null) return b;
        if (b == null) return a;

        int resultA = abs(a.result-target.result);
        int resultB = abs(b.result-target.result);
        if (resultA == resultB) {
            if (a.expressionString.length() < b.expressionString.length() ) {
                return a;
            } else {
                return b;
            }
        } else if (resultA < resultB) {
            return a;
        } else {
            return b;
        }
    }

    public boolean smaller(Expression other) {
        return (this.result < other.result);
    }
    public boolean equals(Expression other) {
        return (this.result == other.result);
    }

    public boolean isNotZero() {
        return (this.result != 0);
    }

    @Override
    public String toString() {
        return expressionString + " = " + result;
    }

}
