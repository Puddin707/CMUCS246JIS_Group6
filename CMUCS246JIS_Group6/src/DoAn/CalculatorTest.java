package DoAn;

/**
 * Test class for the Calculator application
 * Contains test cases to verify calculator functionality
 */
public class CalculatorTest {
    
    /**
     * Main method to run tests
     */
    public static void main(String[] args) {
        System.out.println("Running Calculator Tests...");
        
        // Create instances for testing
        CalculationLogic calculationLogic = new CalculationLogic();
        InputHandler inputHandler = new InputHandler(calculationLogic);
        
        // Test basic operations
        testAddition(calculationLogic);
        testSubtraction(calculationLogic);
        testMultiplication(calculationLogic);
        testDivision(calculationLogic);
        
        // Test input handling
        testInputHandling(inputHandler);
        
        // Test error cases
        testErrorCases(calculationLogic, inputHandler);
        
        System.out.println("All tests completed.");
    }
    
    /**
     * Test addition operation
     */
    private static void testAddition(CalculationLogic calculationLogic) {
        System.out.println("Testing Addition...");
        
        // Test cases
        assert calculationLogic.calculate(5, 3, "+") == 8 : "5 + 3 should equal 8";
        assert calculationLogic.calculate(-5, 3, "+") == -2 : "-5 + 3 should equal -2";
        assert calculationLogic.calculate(0, 0, "+") == 0 : "0 + 0 should equal 0";
        
        System.out.println("Addition tests passed.");
    }
    
    /**
     * Test subtraction operation
     */
    private static void testSubtraction(CalculationLogic calculationLogic) {
        System.out.println("Testing Subtraction...");
        
        // Test cases
        assert calculationLogic.calculate(5, 3, "-") == 2 : "5 - 3 should equal 2";
        assert calculationLogic.calculate(-5, 3, "-") == -8 : "-5 - 3 should equal -8";
        assert calculationLogic.calculate(0, 0, "-") == 0 : "0 - 0 should equal 0";
        
        System.out.println("Subtraction tests passed.");
    }
    
    /**
     * Test multiplication operation
     */
    private static void testMultiplication(CalculationLogic calculationLogic) {
        System.out.println("Testing Multiplication...");
        
        // Test cases
        assert calculationLogic.calculate(5, 3, "*") == 15 : "5 * 3 should equal 15";
        assert calculationLogic.calculate(-5, 3, "*") == -15 : "-5 * 3 should equal -15";
        assert calculationLogic.calculate(0, 5, "*") == 0 : "0 * 5 should equal 0";
        
        System.out.println("Multiplication tests passed.");
    }
    
    /**
     * Test division operation
     */
    private static void testDivision(CalculationLogic calculationLogic) {
        System.out.println("Testing Division...");
        
        // Test cases
        assert calculationLogic.calculate(6, 3, "/") == 2 : "6 / 3 should equal 2";
        assert calculationLogic.calculate(-6, 3, "/") == -2 : "-6 / 3 should equal -2";
        assert calculationLogic.calculate(0, 5, "/") == 0 : "0 / 5 should equal 0";
        
        System.out.println("Division tests passed.");
    }
    
    /**
     * Test input handling
     */
    private static void testInputHandling(InputHandler inputHandler) {
        System.out.println("Testing Input Handling...");
        
        // Reset input handler state
        inputHandler.processInput("C");
        
        // Test numeric input
        assert inputHandler.processInput("5").equals("5") : "Input '5' should display '5'";
        assert inputHandler.processInput(".").equals("5.") : "Input '.' after '5' should display '5.'";
        
        // Test operator input
        inputHandler.processInput("+");
        assert inputHandler.processInput("3").equals("3") : "Input '3' after '+' should display '3'";
        
        // Test equals
        assert inputHandler.processInput("=").equals("8") : "5 + 3 should equal 8";
        
        // Test clear
        assert inputHandler.processInput("C").equals("0") : "Clear should display '0'";
        
        System.out.println("Input handling tests passed.");
    }
    
    /**
     * Test error cases
     */
    private static void testErrorCases(CalculationLogic calculationLogic, InputHandler inputHandler) {
        System.out.println("Testing Error Cases...");
        
        // Test division by zero
        try {
            calculationLogic.calculate(5, 0, "/");
            System.out.println("FAILED: Division by zero should throw exception");
        } catch (ArithmeticException e) {
            System.out.println("Division by zero correctly throws exception: " + e.getMessage());
        }
        
        // Test input handler division by zero
        inputHandler.processInput("C");
        inputHandler.processInput("5");
        inputHandler.processInput("/");
        inputHandler.processInput("0");
        assert inputHandler.processInput("=").equals("Error") : "5 / 0 should display 'Error'";
        
        System.out.println("Error case tests passed.");
    }
}
