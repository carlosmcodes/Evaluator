package edu.csc413.calculator.evaluator;

/**
 * Operand class used to represent an operand
 * in a valid mathematical expression.
 */
public class Operand {

    private String Generaltoken;
    private int Generalvalue;

    /**
     * construct operand from string token.
     */
    public Operand(String token) {
        Generaltoken = token;
    }

    /**
     * construct operand from integer
     */
    public Operand(int value) {
        Generalvalue = value;
    }

    /**
     * return value of operand
     */
    public int getValue() {
        return Generalvalue;
    }

    /**
     * Check to see if given token is a valid
     * operand.
     */
    public static boolean check(String token) {
        try {
            int tokenCheck = Integer.parseInt(token);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
