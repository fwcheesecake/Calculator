import java.util.Stack;

class Calculus {
    private static int Prec(char ch) {
        return switch (ch) {
            case '+', '-' -> 1;
            case '*', '/', '%' -> 2;
            case '^' -> 3;
            default -> -1;
        };
    }

    public static String infixToPostfix(String exp) {
        String result = "";

        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < exp.length(); ++i) {
            for(; i < exp.length() &&
                        (Character.isDigit(exp.charAt(i)) || exp.charAt(i) == '.'); i++)
                result += exp.charAt(i);
            if(result.length() > 0)
                if(Character.isDigit(result.charAt(result.length() - 1)))
                    result += " ";
            if(i == exp.length())
                break;

            char c = exp.charAt(i);
            if(c == ' ') {
            }
            else if (c == '(')
                stack.push(c);
            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(')
                    result += stack.pop() + " ";
                stack.pop();
            } else {
                while (!stack.isEmpty() && Prec(c) <= Prec(stack.peek()))
                    result += stack.pop() + " ";
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            if(stack.peek() == '(')
                return "Syntax error";
            result += " " + stack.pop();
        }
        return result;
    }

    public static String doOperations(String postfix) {
        String[] exp = postfix.split("\\s");
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