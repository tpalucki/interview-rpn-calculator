package io.github.tpalucki.dolarapp.operator;

public class DefaultOperatorProvider implements OperatorProvider {
    @Override
    public Operator operatorForToken(String token) {
        if ("+".equals(token)) {
            return new AddOperator();
        } else if ("-".equals(token)) {
            return new SubtractOperator();
        } else if ("*".equals(token)) {
            return new MultiplyOperator();
        } else if ("/".equals(token)) {
            return new DivideOperator();
        } else if ("!".equals(token)) {
            return new FactorialOperator();
        }
        throw new IllegalArgumentException("Operator unknown");
    }
}
