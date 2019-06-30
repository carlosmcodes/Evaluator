package edu.csc413.calculator.evaluator;


import edu.csc413.calculator.operators.Operator;

import java.util.Stack;
import java.util.StringTokenizer;

public class Evaluator {
    private Stack<Operand> operandStack;
    private Stack<Operator> operatorStack;
    private Stack<Operator> parenthesis;
    private StringTokenizer tokenizer;
    private static final String DELIMITERS = "+-*^/()";

    public Evaluator() {
        operandStack = new Stack<>();
        operatorStack = new Stack<>();
        parenthesis = new Stack<>();
    }

    int count = 0;

    public int eval(String expression) {
        // gets rid of all whitespace
        expression = expression.replaceAll("\\s+", "");

        String token;

        this.tokenizer = new StringTokenizer(expression, DELIMITERS, true);

        while (this.tokenizer.hasMoreTokens()) {

            token = this.tokenizer.nextToken();
            // check if token is an operand
            if (Operand.check(token)) {
                operandStack.push(new Operand(Integer.parseInt(token)));
                if (parenthesis.size() > 0)
                    count++;
            } else {
                if (!Operator.check(token)) {
                    System.out.println("*****invalid token******");
                    throw new RuntimeException("*****invalid token******");
                }
                Operator newOperator = Operator.getOperator(token);

                if (newOperator.priority() == 4 || newOperator.priority() == 5) {
                    parenthesis.push(newOperator);
                    if (parenthesis.size() >= 2) {
                        if (parenthesis.peek().priority() == 5) {
                            parenthesis.pop();
                            parenthesis.pop();
                            for (int i = 1; i < count; i++) {
                                compute(operandStack, operatorStack);
                            }
                            count = 0;
                        }
                    }
                } else if (operatorStack.size() == 0) {
                    operatorStack.push(newOperator);

                } else if (parenthesis.size() >= 1) {
                    algo(newOperator);

                } else {
                    algo(newOperator);
                }

            }
        }
        while (operatorStack.size() != 0) {
            compute(operandStack, operatorStack);
        }
        return operandStack.pop().getValue();
    }

    private void compute(Stack<Operand> operand, Stack<Operator> operator) {
        Operator oldOpr = operator.pop();
        Operand op2 = operand.pop();
        Operand op1 = operand.pop();
        operand.push(oldOpr.execute(op1, op2));

    }

    private void algo(Operator newOperator) {
        if (operatorStack.peek().priority() <= newOperator.priority()) {
            operatorStack.push(newOperator);
        } else {
            if (parenthesis.peek().priority() == 4 && parenthesis.size() <= 2) {
                if ((operatorStack.peek().priority() <= newOperator.priority())) {
                    compute(operandStack, operatorStack);
                    operatorStack.push(newOperator);
                } else
                    operatorStack.push(newOperator);
            } else {
                compute(operandStack, operatorStack);
                operatorStack.push(newOperator);

            }
        }


    }
}
