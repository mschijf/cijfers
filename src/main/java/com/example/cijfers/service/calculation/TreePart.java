package com.example.cijfers.service.calculation;

import java.util.ArrayList;

public class TreePart {
    ArrayList<Expression> list;

    public TreePart(Expression a) {
        list = new ArrayList<>();
        list.add(a);
    }

    public TreePart(Expression a, Expression b) {
        list = new ArrayList<>();
        for (Expression.Operation op: Expression.Operation.values()) {
            Expression value = a.f(op, b);
            if (value != null && value.isNotZero())
                list.add(value);
        }
    }

    public TreePart(Expression a, Expression b, Expression c) {
        list = new ArrayList<>();
        for (Expression.Operation op1: Expression.Operation.values()) {
            for (Expression.Operation op2: Expression.Operation.values()) {
                Expression valueRight = b.f(op2, c);
                if (valueRight != null && valueRight.isNotZero()) {
                    Expression value = a.f(op1, valueRight);
                    if (value != null && value.isNotZero())
                        list.add(value);
                }
            }
        }
    }

    public ArrayList<Expression> get() {
        return list;
    }

}
