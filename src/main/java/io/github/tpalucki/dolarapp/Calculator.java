package io.github.tpalucki.dolarapp;

import io.github.tpalucki.dolarapp.operator.*;

import java.math.*;
import java.util.*;

class Calculator implements Calculate {
    public static final int REQUIRED_PRECISION = 2;
    private final List<BigDecimal> stack = new LinkedList<>();
    private final OperatorProvider provider = new DefaultOperatorProvider();

    @Override
    public BigDecimal calculate(String expression) {
        Arrays.stream(expression.split(" "))
                .forEach(token -> {
                    if (isNumber(token)) {
                        putOnStack(token);
                    } else {
                        try {
                            calculateForOperator(token);
                        } catch (NoSuchElementException e) {
                            throw new IllegalArgumentException("Expression wrong", e);
                        }
                    }
                });
        var result = stack.removeLast();
        if (!stack.isEmpty()) {
            throw new IllegalArgumentException("Expression wrong");
        }
        return result.setScale(REQUIRED_PRECISION, RoundingMode.HALF_UP);
    }

    private void putOnStack(String token) {
        stack.add(new BigDecimal(token));
    }

    private void calculateForOperator(String token) {
        Operator operator = provider.operatorForToken(token);
        operator.doCalculation(stack);
    }


    private boolean isNumber(String operand) {
        try {
            Double.parseDouble(operand);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
