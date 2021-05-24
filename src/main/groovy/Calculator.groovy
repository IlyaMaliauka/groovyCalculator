class Calculator {

    public static final String[] allowedCharacters = "123456789()+-*/"

    static double calculate(String input) {
        checkCalculatorExpression.call(input)
        String output = getRpnExpression.call(input)
        return countResult.call(output)
    }

    static def getRpnExpression = { String input ->
        StringBuilder output = new StringBuilder()
        Stack<Character> operators = new Stack<>()

        for (int i = 0; i < input.length(); i++) {

            if (isDelimiter(input[i]))
                continue

            if (input.charAt(i).isDigit()) { //Если цифра

                while (!isDelimiter(input[i]) && !isOperator(input[i])) {
                    output.append(input[i])
                    i++

                    if (i == input.length()) break
                }

                output.append(" ")
                i--
            }

            if (isOperator(input[i])) {
                if (input[i].toString() == '(')
                    operators.push(input[i])
                else if (input[i].toString() == ')') {

                    char s = operators.pop()

                    while (s.toString() != '(') {
                        output.append(s.toString()).append(' ')
                        s = operators.pop()
                    }
                } else {
                    if (operators.size() > 0)
                        if (getPriority(input[i]) <= getPriority(operators.peek()))
                            output.append(operators.pop().toString() + " ")


                    operators.push(input[i])

                }
            }
        }

        while (operators.size() > 0) {
            output.append(operators.pop()).append(" ")
        }
        return output
    }

    static Closure<Double> countResult = { String input ->
        double result = 0
        Stack<Double> resultStack = new Stack<>()

        for (int i = 0; i < input.length(); i++) {

            if (input.charAt(i).isDigit()) {
                StringBuilder tempResult = new StringBuilder()

                while (!isDelimiter(input[i]) && !isOperator(input[i])) {
                    tempResult.append(input[i])
                    i++
                    if (i == input.length()) break
                }
                resultStack.push(Double.parseDouble(tempResult.toString()))
                i--
            } else if (isOperator(input[i])) {

                double a = resultStack.pop()
                double b = resultStack.pop()

                switch (input[i]) {
                    case '+': result = b + a; break
                    case '-': result = b - a; break
                    case '*': result = b * a; break
                    case '/': result = b / a; break
                }
                resultStack.push(result)
            }
        }
        return resultStack.peek()
    }

    static private boolean isDelimiter(String c) {
        return " =".indexOf(c) != -1
    }

    static private boolean isOperator(String a) {
        return "+-/*^()".indexOf(a) != -1
    }

    static private int getPriority(String s) {
        switch (s) {
            case '(': return 0
            case ')': return 1
            case '+': return 2
            case '-': return 3
            case '*': return 4
            case '/': return 4
            default: return 6
        }
    }

    static def checkCalculatorExpression = { String input ->
        for (int i = 0; i<input.length(); i++) {
            if (!(input[i] in allowedCharacters)) {
                String invalidCharacter = input[i]
                throw new IllegalArgumentException("Invalid calculator sybmol: \"$invalidCharacter\". Allowed symbols are: $allowedCharacters")
            }
        }
    }
}