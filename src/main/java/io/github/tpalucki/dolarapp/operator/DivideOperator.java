package io.github.tpalucki.dolarapp.operator;

import java.math.*;
import java.util.List;

class DivideOperator implements Operator {

    public static final int PRECISION = 4;

    @Override
    public void doCalculation(List<BigDecimal> stack) {
        var operandOne = stack.removeLast();
        var operandTwo = stack.removeLast();

        var result = operandTwo.divide(operandOne, PRECISION, RoundingMode.HALF_UP);
        stack.addLast(result);
    }
}
