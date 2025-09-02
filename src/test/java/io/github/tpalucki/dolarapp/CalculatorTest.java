package io.github.tpalucki.dolarapp;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private Calculate calculate;

    public static Stream<Arguments> provideExpressionWithAddOperation() {
        return Stream.of(
                Arguments.of("3 3 +", "6.00"),
                Arguments.of("1 2 +", "3.00"),
                Arguments.of("0 5 +", "5.00"),

                Arguments.of("3 2 -", "1.00"),
                Arguments.of("10 2 /", "5.00"),

                Arguments.of("3 4 +", "7.00"),
                Arguments.of("3 4 - 5 +", "4.00"),
                Arguments.of("3 4 + 5 6 + *", "77.00"),

                Arguments.of("0.1 0.2 +", "0.30"),
                Arguments.of("100 3 /", "33.33"),
                Arguments.of("5 3 + 5 /", "1.60"),

                Arguments.of("3", "3.00"),
                Arguments.of("10 6 9 3 + -11 * / * 17 + 5 +", "21.55"),

                Arguments.of("5 !", "120.00")
        );
    }

    public static Stream<Arguments> provideExpressionWithErrors() {
        return Stream.of(
                Arguments.of("3 2 3 +"),
                Arguments.of("3 2"),
                Arguments.of("3 + 2")
        );
    }

    @BeforeEach
    void setUp() {
        calculate = new Calculator();
    }

    @ParameterizedTest
    @MethodSource("provideExpressionWithAddOperation")
    void provideExpressionWithAddOperation(String input, BigDecimal expectedOutput) {
        var result = calculate.calculate(input);

        assertEquals(expectedOutput, result);
    }

    @Test
    void givenExpressionContainsDivisionByZero_thenShouldThrowAnException() {
        String input = "10 0 /";
        assertThrows(ArithmeticException.class, () -> calculate.calculate(input));
    }

    @ParameterizedTest
    @MethodSource("provideExpressionWithErrors")
    void shouldNotPerformOperationOnerrorExpression(String input) {
        assertThrows(IllegalArgumentException.class, () -> calculate.calculate(input));
    }
}