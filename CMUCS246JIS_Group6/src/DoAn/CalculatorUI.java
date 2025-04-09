package DoAn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * UI class for the Calculator application
 * Handles the user interface components and layout
 */
public class CalculatorUI extends JFrame implements KeyListener {
    private JTextField displayField;
    private CalculationLogic calculationLogic;
    private InputHandler inputHandler;
    
    // Color scheme
    private final Color DISPLAY_BG = new Color(240, 240, 240);
    private final Color BUTTON_BG = new Color(250, 250, 250);
    private final Color OPERATOR_BG = new Color(230, 230, 250);
    private final Color EQUALS_BG = new Color(173, 216, 230);
    private final Color CLEAR_BG = new Color(255, 228, 225);
    
    /**
     * Constructor for the Calculator UI
     */
    public CalculatorUI() {
        // Initialize the calculation logic and input handler
        calculationLogic = new CalculationLogic();
        inputHandler = new InputHandler(calculationLogic);
        
        // Set up the frame
        setTitle("Java Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(320, 450);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Create UI components
        initComponents();
        
        // Add key listener for keyboard support
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }
    
    /**
     * Initialize UI components
     */
    private void initComponents() {
        // Set up the display field
        displayField = new JTextField("0");
        displayField.setFont(new Font("Arial", Font.BOLD, 28));
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setEditable(false);
        displayField.setBackground(DISPLAY_BG);
        displayField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Create button panel with grid layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 8, 8));
        buttonPanel.setBackground(new Color(220, 220, 220));
        
        // Create buttons
        String[] buttonLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "C", "CE", "", ""
        };
        
        // Add buttons to panel
        for (String label : buttonLabels) {
            if (label.isEmpty()) {
                JPanel emptyPanel = new JPanel();
                emptyPanel.setBackground(new Color(220, 220, 220));
                buttonPanel.add(emptyPanel);
            } else {
                JButton button = createStyledButton(label);
                button.addActionListener(new ButtonClickListener());
                buttonPanel.add(button);
            }
        }
        
        // Set up layout
        setLayout(new BorderLayout(10, 10));
        add(displayField, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        
        // Add padding
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        getContentPane().setBackground(new Color(220, 220, 220));
    }
    
    /**
     * Create a styled button with appropriate colors and effects
     * @param label Button label
     * @return Styled JButton
     */
    private JButton createStyledButton(String label) {
        JButton button = new JButton(label);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setFocusPainted(false);
        
        // Set button colors based on function
        if (label.equals("=")) {
            button.setBackground(EQUALS_BG);
        } else if (label.equals("C") || label.equals("CE")) {
            button.setBackground(CLEAR_BG);
        } else if (label.equals("+") || label.equals("-") || 
                   label.equals("*") || label.equals("/")) {
            button.setBackground(OPERATOR_BG);
        } else {
            button.setBackground(BUTTON_BG);
        }
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(button.getBackground().darker());
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (label.equals("=")) {
                    button.setBackground(EQUALS_BG);
                } else if (label.equals("C") || label.equals("CE")) {
                    button.setBackground(CLEAR_BG);
                } else if (label.equals("+") || label.equals("-") || 
                           label.equals("*") || label.equals("/")) {
                    button.setBackground(OPERATOR_BG);
                } else {
                    button.setBackground(BUTTON_BG);
                }
            }
        });
        
        return button;
    }
    
    /**
     * Inner class to handle button clicks
     */
    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            processCommand(command);
        }
    }
    
    /**
     * Process command from button click or keyboard
     * @param command The command to process
     */
    private void processCommand(String command) {
        String result = inputHandler.processInput(command);
        displayField.setText(result.isEmpty() ? "0" : result);
        
        // Request focus to ensure keyboard events are captured
        requestFocusInWindow();
    }
    
    // KeyListener implementation for keyboard support
    @Override
    public void keyTyped(KeyEvent e) {
        char keyChar = e.getKeyChar();
        
        // Handle numeric keys and operators
        if (Character.isDigit(keyChar) || keyChar == '.' || 
            keyChar == '+' || keyChar == '-' || 
            keyChar == '*' || keyChar == '/') {
            processCommand(String.valueOf(keyChar));
        } 
        // Handle equals (Enter key)
        else if (keyChar == '\n' || keyChar == '=') {
            processCommand("=");
        }
        // Handle clear (Escape key)
        else if (keyChar == 27) {
            processCommand("C");
        }
        // Handle backspace (as CE)
        else if (keyChar == '\b') {
            processCommand("CE");
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        // Not used, but required by KeyListener interface
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        // Not used, but required by KeyListener interface
    }
}
