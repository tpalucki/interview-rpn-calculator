package io.github.tpalucki.dolarapp.operator;

public interface OperatorProvider {

    Operator operatorForToken(String token);

}
