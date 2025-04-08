package DoAn;

/**
 * InputHandler class for the Calculator application
 * Processes user input and interacts with the calculation logic
 */
public class InputHandler {
    private CalculationLogic calculationLogic;
    private StringBuilder currentInput;
    private String currentOperator;
    private double firstOperand;
    private boolean startNewInput;
    private boolean hasResult;
    private boolean errorState;
    
    /**
     * Constructor for the InputHandler
     * @param calculationLogic The calculation logic to use
     */
    public InputHandler(CalculationLogic calculationLogic) {
        this.calculationLogic = calculationLogic;
        this.currentInput = new StringBuilder("0");
        this.currentOperator = "";
        this.firstOperand = 0;
        this.startNewInput = true;
        this.hasResult = false;
        this.errorState = false;
    }
    
    /**
     * Process input from the UI
     * @param input The input string from button press
     * @return The string to display
     */
    public String processInput(String input) {
        // Reset error state if any button is pressed
        if (errorState) {
            errorState = false;
            clearAll();
        }
        
        // Handle different types of input
        if (isNumeric(input) || input.equals(".")) {
            return handleNumericInput(input);
        } else if (isOperator(input)) {
            return handleOperatorInput(input);
        } else if (input.equals("=")) {
            return calculateResult();
        } else if (input.equals("C")) {
            return clearAll();
        } else if (input.equals("CE")) {
            return clearEntry();
        }
        
        return currentInput.toString();
    }
    
    /**
     * Handle numeric input (digits and decimal point)
     * @param input The numeric input
     * @return The updated display string
     */
    private String handleNumericInput(String input) {
        // Start new input after operator or result
        if (startNewInput || hasResult) {
            currentInput = new StringBuilder();
            startNewInput = false;
            hasResult = false;
        }
        
        // Handle decimal point
        if (input.equals(".") && currentInput.toString().contains(".")) {
            return currentInput.toString();
        }
        
        // Handle initial decimal point
        if (input.equals(".") && currentInput.length() == 0) {
            currentInput.append("0.");
        } else {
            // Prevent leading zeros
            if (currentInput.toString().equals("0") && !input.equals(".")) {
                currentInput = new StringBuilder();
            }
            currentInput.append(input);
        }
        
        // Limit input length to prevent overflow
        if (currentInput.length() > 15) {
            currentInput.deleteCharAt(currentInput.length() - 1);
        }
        
        return currentInput.toString();
    }
    
    /**
     * Handle operator input (+, -, *, /)
     * @param operator The operator input
     * @return The updated display string
     */
    private String handleOperatorInput(String operator) {
        // If we already have an operator and a first operand
        if (!currentOperator.isEmpty() && !startNewInput) {
            calculateResult();
        }
        
        // Store the first operand and operator
        if (currentInput.length() > 0) {
            try {
                firstOperand = Double.parseDouble(currentInput.toString());
            } catch (NumberFormatException e) {
                errorState = true;
                return "Error";
            }
        }
        
        currentOperator = operator;
        startNewInput = true;
        hasResult = false;
        
        return currentInput.toString();
    }
    
    /**
     * Calculate the result of the operation
     * @return The result as a string
     */
    private String calculateResult() {
        if (currentOperator.isEmpty() || startNewInput) {
            return currentInput.toString();
        }
        
        double secondOperand;
        try {
            secondOperand = Double.parseDouble(currentInput.toString());
        } catch (NumberFormatException e) {
            errorState = true;
            return "Error";
        }
        
        try {
            double result = calculationLogic.calculate(firstOperand, secondOperand, currentOperator);
            currentInput = new StringBuilder(formatResult(result));
            firstOperand = result; // Store result as first operand for chaining operations
            currentOperator = "";
            startNewInput = true;
            hasResult = true;
        } catch (ArithmeticException e) {
            currentInput = new StringBuilder("Error");
            currentOperator = "";
            startNewInput = true;
            errorState = true;
        }
        
        return currentInput.toString();
    }
    
    /**
     * Clear all input and state
     * @return Empty string
     */
    private String clearAll() {
        currentInput = new StringBuilder("0");
        currentOperator = "";
        firstOperand = 0;
        startNewInput = true;
        hasResult = false;
        errorState = false;
        return "0";
    }
    
    /**
     * Clear current entry
     * @return Empty string or current input
     */
    private String clearEntry() {
        if (startNewInput) {
            return currentInput.toString();
        } else {
            currentInput = new StringBuilder("0");
            return "0";
        }
    }
    
    /**
     * Check if input is numeric
     * @param input The input to check
     * @return True if numeric
     */
    private boolean isNumeric(String input) {
        return input.matches("[0-9]");
    }
    
    /**
     * Check if input is an operator
     * @param input The input to check
     * @return True if operator
     */
    private boolean isOperator(String input) {
        return input.equals("+") || input.equals("-") || 
               input.equals("*") || input.equals("/");
    }
    
    /**
     * Format the result to avoid unnecessary decimal places
     * @param result The result to format
     * @return Formatted result string
     */
    private String formatResult(double result) {
        // Check if result is too large or small
        if (Double.isInfinite(result) || Double.isNaN(result) || 
            Math.abs(result) > 1e15) {
            errorState = true;
            return "Error";
        }
        
        // Format result to remove unnecessary decimal zeros
        if (result == (long) result) {
            return String.format("%d", (long) result);
        } else {
            // Limit decimal places to 10 for readability
            String formatted = String.format("%.10f", result);
            // Remove trailing zeros
            formatted = formatted.replaceAll("0*$", "").replaceAll("\\.$", "");
            return formatted;
        }
    }
}
