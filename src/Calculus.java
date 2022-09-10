import java.util.Stack;

/**
 * The HelloWorld program implements an application that
 * simply displays "Hello World!" to the standard output.
 *
 * @author  Antonio Reyna Espinoza
 * @version 1.0
 * @since   20022-09-06
 */
class Calculus {
    /**
     * This method takes an operator as a character and returns
     * its precedence in an integer value
     * @param ch This is operator from which the precedence is calculated
     * @return This returns the precedence from an operator
     */
    private static int Prec(char ch) {
        return switch (ch) {
            case '+', '-' -> 1;
            case '*', '/', '%' -> 2;
            case '^' -> 3;
            default -> -1;
        };
    }

    /**
     * This method takes an expression as a string in infix form
     * and transforms it to postfix form in order to perform the
     * operations easily
     * @param exp This is the expression to be converted
     * @return This returns the expression converted to postfix
     */
    public static String infixToPostfix(String exp) {
        StringBuilder result = new StringBuilder();

        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < exp.length(); ++i) {
            for(; i < exp.length() &&
                        (Character.isDigit(exp.charAt(i)) || exp.charAt(i) == '.'); i++)
                result.append(exp.charAt(i));
            if(result.length() > 0)
                if(Character.isDigit(result.charAt(result.length() - 1)))
                    result.append(" ");
            if(i == exp.length())
                break;

            char c = exp.charAt(i);
            switch (c) {
                case ' ':
                    break;
                case '(':
                    stack.push(c);
                    break;
                case ')':
                    while (!stack.isEmpty() && stack.peek() != '(')
                        result.append(stack.pop()).append(" ");
                    stack.pop();
                    break;
                default:
                    while (!stack.isEmpty() && Prec(c) <= Prec(stack.peek()))
                        result.append(stack.pop()).append(" ");
                    stack.push(c);
                    break;
            }
        }

        while (!stack.isEmpty()) {
            if(stack.peek() == '(')
                return "Syntax error";
            result.append(" ").append(stack.pop());
        }
        return result.toString();
    }

    /**
     * This method is used to iterate over the postfix
     * expression and calculate the final result
     * @param postfix This is the expression in postfix form
     * @return This returns the last value in the stack
     */
    public static String doOperations(String postfix) {
        String[] exp = postfix.split("\\s*");
        Stack<String> stack = new Stack<>();

        for(String term : exp) {
            if(term.isEmpty())
                continue;
            if(Character.isDigit(term.charAt(0)))
                stack.push(term);
            else {
                String b = stack.pop();
                String a = stack.pop();
                stack.push(String.valueOf(operation(a, b, term)));
            }
        }
        return stack.pop();
    }

    /**
     * This method is used to perform each operation individually
     * @param aS This is the first operand of the expression
     * @param bS This is the second operand of the experssion
     * @param operator This is the operator of the expression
     * @return This returns the result of a simple expression
     */
    private static double operation(String aS, String bS, String operator) {
        double a = Double.parseDouble(aS);
        double b = Double.parseDouble(bS);
        switch (operator) {
            case "+" -> {
                return a + b;
            }
            case "-" -> {
                return a - b;
            }
            case "*" -> {
                return a * b;
            }
            case "/" -> {
                return a / b;
            }
            case "%" -> {
                return a % b;
            }
            case "^" -> {
                return Math.pow(a, b);
            }
        }
        return a * b;
    }
}