package io.github.tpalucki.dolarapp.operator;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

class FactorialOperator implements Operator {

    @Override
    public void doCalculation(List<BigDecimal> stack) {
        var operandOne = stack.removeLast();

        var resultInt = IntStream.range(1, operandOne.intValue() + 1).reduce(1, (a, b) -> a * b);

        var result = new BigDecimal(resultInt);;
        stack.addLast(result);

    }
}
