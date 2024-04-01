package com.example.tema1;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MathExpressionEvaluatorService {
    public static boolean validateExpression(String expression) {
        return expression.matches("^[0-9+\\-*/.^sqrt()]*$");
    }

    public static double evaluateExpression(String expression) {
        try {
            Expression builtExpression = new ExpressionBuilder(expression).build();

            return builtExpression.evaluate();
        } catch (Exception exception) {
            System.err.println(STR."Error encountered while evaluating expression \{exception.getMessage()}");

            return Double.NaN;
        }
    }
}
