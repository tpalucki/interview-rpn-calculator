package io.github.tpalucki.dolarapp.operator;

import java.math.BigDecimal;
import java.util.List;

class SubtractOperator implements Operator {
    @Override
    public void doCalculation(List<BigDecimal> stack) {
        var operandOne = stack.removeLast();
        var operandTwo = stack.removeLast();

        var result = operandTwo.subtract(operandOne);
        stack.addLast(result);
    }
}
