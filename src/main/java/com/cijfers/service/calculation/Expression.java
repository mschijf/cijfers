package com.cijfers.service.calculation;

import static java.lang.Math.abs;


public class Expression {
    public enum Operation {
        ADD("+"),
        SUBTRACT_LARGEST_BY_SMALLEST("-"),
        MULTIPLY("x"),
        DIVIDE_LARGEST_BY_SMALLEST("/");

        private final String asString;

        Operation(String asString) {
            this.asString = asString;
        }
    }

    public static int operationCount = 0;

    private final Integer constant;
    private final Expression left;
    private final Expression right;
    private final Operation op;
    private final int result;
    private final int opsCount;

    public static Expression fromInt(int a) { return new Expression(a); }

    private Expression(int constant) {
        this.constant = constant;
        this.left = null;
        this.right = null;
        this.op = null;
        this.result = constant;
        this.opsCount = 0;
    }

    private Expression(Expression left, Operation op, Expression right) {
        this.constant = null;
        this.left = left;
        this.right = right;
        this.op = op;
        this.result = f(op, left.result, right.result);
        this.opsCount = left.opsCount + right.opsCount + 1;
    }

    private int f(Operation op, int left, int right) {
        switch(op) {
            case ADD:
                return left + right;
            case SUBTRACT_LARGEST_BY_SMALLEST:
                return left > right ? left-right : right-left;
            case MULTIPLY:
                return left * right;
            case DIVIDE_LARGEST_BY_SMALLEST:
                if (right != 0  && left % right == 0)
                    return left / right;
                else if (left != 0  && right % left == 0)
                    return right / left;
                else
                    return 0;
        }
        throw new ArithmeticException("Unknown Operation");
    }

    public Expression f(Operation op, Expression other) {
        ++operationCount;
        return new Expression(this, op, other);
    }

    public static Expression closestToTarget(Expression a, Expression b, Expression target) {
        if (a == null) return b;
        if (b == null) return a;

        int resultA = abs(a.result-target.result);
        int resultB = abs(b.result-target.result);
        if (resultA == resultB) {
            if (a.opsCount < b.opsCount ) {
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

    public static final Expression ZERO = fromInt(0);
    public static final Expression MINIMUM = fromInt(-999999999);
    public static final Expression MAXIMUM = fromInt(999999999);

    public int toInt() {
        return result;
    }

    @Override
    public String toString() {
        return expressionToString(null, false) + " = " + result;
    }

    private String expressionToString(Expression parent, boolean isLeft) {
        if (constant != null)
            return Integer.toString(constant);
        String middle = " " + op.asString + " ";
        String exprStr;
        if (op == Operation.DIVIDE_LARGEST_BY_SMALLEST || op == Operation.SUBTRACT_LARGEST_BY_SMALLEST) {
            if (left.result < right.result) {
                exprStr = right.expressionToString(this, true) + middle + left.expressionToString(this, false);
            } else {
                exprStr = left.expressionToString(this, true) + middle + right.expressionToString(this, false);
            }
        } else {
            if (left.result < right.result) {
                exprStr = left.expressionToString(this, true) + middle + right.expressionToString(this, false);
            } else {
                exprStr = right.expressionToString(this, true) + middle + left.expressionToString(this, false);
            }
        }
        if (parenthesisNeeded(parent, isLeft)) {
            return "(" + exprStr + ")";
        } else {
            return exprStr;
        }
    }

    private boolean parenthesisNeeded(Expression parent, boolean isLeft) {
        if (parent == null)
            return false;
        if (op == Operation.MULTIPLY)
            return false;
        if (op == Operation.DIVIDE_LARGEST_BY_SMALLEST) {
            if (isLeft && parent.op != Operation.MULTIPLY)
                return false;
            if (!isLeft && parent.op != Operation.DIVIDE_LARGEST_BY_SMALLEST)
                return false;
            return true;
        }
        if (op == Operation.ADD) {
            if (parent.op == Operation.ADD)
                return false;
            if (isLeft && parent.op == Operation.DIVIDE_LARGEST_BY_SMALLEST)
                return false;
            return true;
        }
        if (op == Operation.SUBTRACT_LARGEST_BY_SMALLEST) {
            if (parent.op == Operation.ADD)
                return false;
            if (isLeft && op == Operation.SUBTRACT_LARGEST_BY_SMALLEST)
                return false;
            return true;
        }
        return true;
    }
}
