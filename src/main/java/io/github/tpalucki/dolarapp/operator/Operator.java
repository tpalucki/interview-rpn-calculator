package io.github.tpalucki.dolarapp.operator;

import java.math.BigDecimal;
import java.util.List;

public interface Operator {

    void doCalculation(List<BigDecimal> stack);

}
