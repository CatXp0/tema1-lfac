package com.example.tema1;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import java.util.ArrayDeque;
import java.util.Deque;

public class MathExpressionEvaluatorService {
    public static boolean validateExpression(String expression) {
        return expression.matches("^[0-9+\\-*/.^sqrt()]*$");
    }

    public static double evaluateExpressionWithExp4j(String expression) {
        try {
            Expression builtExpression = new ExpressionBuilder(expression).build();

            return builtExpression.evaluate();
        } catch (Exception exception) {
            System.err.println(STR."Error encountered while evaluating expression - \{exception.getMessage()}");

            return Double.NaN;
        }
    }

    public static double evaluateExpressionWithShuntingYardAlgorithm(String expression) {
        // shunting yard algorithm will be used https://aquarchitect.github.io/swift-algorithm-club/Shunting%20Yard/
        // convert to postfix notation
        Deque<String> postfix = infixToPostfix(expression);
        // evaluate postfix expression
        return evaluatePostfix(postfix);
    }

    private static Deque<String> infixToPostfix(String expression) {
        Deque<String> operationsStack = new ArrayDeque<>();
        Deque<String> outputQueue = new ArrayDeque<>();

        // notam cu s/p radacina patrata si ridicarea la putere
        expression = expression.replaceAll("sqrt\\(([^)]+)\\)", "s$1");
        expression = expression.replaceAll("([\\d.]+)\\^(\\d+)", "p$1,$2");

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c) || c == 's' || c == 'p') {
                // daca e un numar sau notatia pt putere/radacina patrata, il bagam in output queue
                StringBuilder number = new StringBuilder(c == 's' ? "s" : (c == 'p' ? "p" : String.valueOf(c)));
                while (i + 1 < expression.length() && (Character.isDigit(expression.charAt(i + 1)) || expression.charAt(i + 1) == '.' || expression.charAt(i + 1) == ',')) {
                    number.append(expression.charAt(++i));
                }

                outputQueue.add(number.toString());
            } else if (c == '(') {
                // daca e o paranteza deschisa, o bagam in stackul de operatii
                operationsStack.push(String.valueOf(c));
            } else if (c == ')') {
                // daca este o paranteza inchisa, cat timp cat nu avem o paranteza deschisa in top of stack
                // dam pop la operatorii din stack in output queue
                while (!operationsStack.isEmpty() && !operationsStack.peek().equals("(")) {
                    outputQueue.add(operationsStack.pop());
                }
                // altfel, ii dam pop din stack
                operationsStack.pop();
            } else if (isOperator(c)) {
                // daca este operator, cat timp avem un operator at the top of stack cu precedenta mai mare
                // dam pop la operatorii din stack in output queue
                while (!operationsStack.isEmpty() && precedence(operationsStack.peek().charAt(0)) >= precedence(c)) {
                    outputQueue.add(operationsStack.pop());
                }
                // altfel, ii bagam in stackul de operatii
                operationsStack.push(String.valueOf(c));
            }
        }

        // cat timp mai sunt operatori in stack, ii bagam in output queue
        while (!operationsStack.isEmpty()) {
            outputQueue.add(operationsStack.pop());
        }

        return outputQueue;
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private static int precedence(char operator) {
        return switch (operator) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            default -> -1;
        };
    }

    private static double evaluatePostfix(Deque<String> postfix) {
        Deque<Double> stack = new ArrayDeque<>();

        for (String token : postfix) {
            if (token.matches("[+\\-*/]")) {
                double secondOperand = stack.pop();
                double firstOperand = stack.pop();

                double result = switch (token) {
                    case "+" -> firstOperand + secondOperand;
                    case "-" -> firstOperand - secondOperand;
                    case "*" -> firstOperand * secondOperand;
                    case "/" -> {
                        if (secondOperand == 0) {
                            throw new ArithmeticException("Division by zero");
                        }

                        yield firstOperand / secondOperand;
                    }
                    default -> 0;
                };

                stack.push(result);
            } else if (token.startsWith("s")) {
                double operand = Double.parseDouble(token.substring(1));

                stack.push(Math.sqrt(operand));
            } else if (token.startsWith("p")) {
                String[] parts = token.substring(1).split(",");

                stack.push(Math.pow(Double.parseDouble(parts[0]), Double.parseDouble(parts[1])));
            } else {
                stack.push(Double.parseDouble(token));
            }
        }

        return stack.pop();
    }
}
