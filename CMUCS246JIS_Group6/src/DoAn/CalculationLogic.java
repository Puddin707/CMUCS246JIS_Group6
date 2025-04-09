package DoAn;


public class CalculationLogic {
    
    /**
     * Perform calculation based on operands and operator
     * @param firstOperand The first operand
     * @param secondOperand The second operand
     * @param operator The operator (+, -, *, /)
     * @return The calculation result
     * @throws ArithmeticException If division by zero is attempted
     */
    public double calculate(double firstOperand, double secondOperand, String operator) throws ArithmeticException {
        switch (operator) {
            case "+":
                return add(firstOperand, secondOperand);
            case "-":
                return subtract(firstOperand, secondOperand);
            case "*":
                return multiply(firstOperand, secondOperand);
            case "/":
                return divide(firstOperand, secondOperand);
            default:
                return secondOperand;
        }
    }
    
    /**
     * Add two numbers
     * @param a First number
     * @param b Second number
     * @return Sum of a and b
     */
    private double add(double a, double b) {
        // Check for potential overflow
        if ((a > 0 && b > Double.MAX_VALUE - a) || 
            (a < 0 && b < -Double.MAX_VALUE - a)) {
            throw new ArithmeticException("Overflow error");
        }
        return a + b;
    }
    
    /**
     * Subtract second number from first
     * @param a First number
     * @param b Second number
     * @return Difference (a - b)
     */
    private double subtract(double a, double b) {
        // Check for potential overflow
        if ((b > 0 && a < -Double.MAX_VALUE + b) || 
            (b < 0 && a > Double.MAX_VALUE + b)) {
            throw new ArithmeticException("Overflow error");
        }
        return a - b;
    }
    
    /**
     * Multiply two numbers
     * @param a First number
     * @param b Second number
     * @return Product of a and b
     */
    private double multiply(double a, double b) {
        // Check for potential overflow
        if (a != 0 && b != 0) {
            double absA = Math.abs(a);
            double absB = Math.abs(b);
            if (absA > Double.MAX_VALUE / absB) {
                throw new ArithmeticException("Overflow error");
            }
        }
        return a * b;
    }
    
    /**
     * Divide first number by second
     * @param a First number (dividend)
     * @param b Second number (divisor)
     * @return Quotient (a / b)
     * @throws ArithmeticException If divisor is zero
     */
    private double divide(double a, double b) throws ArithmeticException {
        // Check for division by zero
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        
        // Check for potential overflow
        if (Math.abs(b) < Double.MIN_NORMAL && a != 0) {
            throw new ArithmeticException("Result too large");
        }
        
        return a / b;
    }
}
