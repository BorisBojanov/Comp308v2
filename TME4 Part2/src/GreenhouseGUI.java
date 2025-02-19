/**
 * The GreenhouseGUI class provides a graphical user interface for controlling Greenhouse events.
 * It includes buttons for managing event execution, a dropdown menu for additional controls,
 * and a log display for event tracking.
 * 
 * @author Boris B
 * @version 1.0 Jan 31, 2025
 */

import javax.swing.*;
import java.awt.*;

public class GreenhouseGUI extends JFrame implements EventListener {

    /** The GreenhouseControls instance associated with this GUI. */
    private GreenhouseControls greenhouse;

    /** Displays event logs in the GUI. */
    private JTextArea eventLog;

    /** Text field for entering the event file path. */
    private JTextField filePath;

    /** Dropdown menu for user controls. */
    private DropdownMenu dropdownMenu;

    /** Panel for file selection components. */
    private JPanel filePanel;

    /** Panel for action buttons. */
    private JPanel buttonPanel;

    /** Button to start events. */
    private JButton startButton;

    /** Button to suspend active events. */
    private JButton suspendButton;

    /** Button to resume suspended events. */
    private JButton resumeButton;

    /** Button to terminate an event with a delay. */
    private JButton terminateButton;

    /** Button to restart events from a file. */
    private JButton restartButton;

    /** Popup menu for additional actions. */
    private PopupMenu popupMenu;

    /**
     * Constructs the Greenhouse GUI and initializes all components.
     */
    public GreenhouseGUI() {
        greenhouse = new GreenhouseControls();
        dropdownMenu = new DropdownMenu(greenhouse, this); // Pass JFrame reference to DropdownMenu
        // Register this GUI as the event listener
        Event.setEventListener(this); 

        // setupUI(); // Move all UI-related setup to a separate method for clarity.
        setupUI();
        
        // Event Log Display-------
        eventLog = new JTextArea();
        eventLog.setEditable(false);

        add(new JScrollPane(eventLog), BorderLayout.CENTER);
        //-------------------------

        // Control Buttons on bottom of window
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3));

        startButton = new JButton("Start Events");
        suspendButton = new JButton("Suspend Events");
        resumeButton = new JButton("Resume Events");
        terminateButton = new JButton("Terminate Events");
        restartButton = new JButton("Restart Events");

        buttonPanel.add(startButton);
        buttonPanel.add(suspendButton);
        buttonPanel.add(resumeButton);
        buttonPanel.add(terminateButton);
        buttonPanel.add(restartButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Create Popup Menu
        popupMenu = new PopupMenu(greenhouse, this);
        PopupMenu.attachPopup(this, popupMenu);

        // Button Actions
        suspendButton.addActionListener(e -> {
            greenhouse.suspendEvents();
            suspendButton.setEnabled(false);
            resumeButton.setEnabled(true);
        });

        resumeButton.addActionListener(e -> {
            greenhouse.resumeEvents();
            suspendButton.setEnabled(true);
            resumeButton.setEnabled(false);
        });

        startButton.addActionListener(e -> greenhouse.executeLoadedEvents());
                
        restartButton.addActionListener(e -> {
            if (filePath.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "No event file loaded.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            if (!greenhouse.getEventThreads().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Cannot restart while events are running!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            greenhouse.loadEventsFromFile(filePath.getText());
            System.out.println(" Restarting event file: " + filePath.getText());
    
            startButton.setEnabled(true);
            popupMenu.updateMenuState(false);
        });
        
        terminateButton.addActionListener(e -> {
            if (greenhouse.getEventThreads().isEmpty()) {
                JOptionPane.showMessageDialog(this, "No events are running.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            String delayStr = JOptionPane.showInputDialog(this, "Enter delay time in milliseconds:", "Terminate Event", JOptionPane.QUESTION_MESSAGE);
    
            try {
                long delay = Long.parseLong(delayStr);
                greenhouse.startEvent("Terminate", delay, 0);
                System.out.println("⏳ Terminate event scheduled in " + delay + "ms.");
    
                startButton.setEnabled(false);
                suspendButton.setEnabled(false);
                resumeButton.setEnabled(false);
                restartButton.setEnabled(true);
                popupMenu.updateMenuState(false);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid delay time!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        // Register this GUI as the event listener
        Event.setEventListener(this); 
    } // End of Constructor

    /**
     * Handles event notifications and updates the event log display.
     *
     * @param eventDescription The description of the triggered event.
     */
    @Override
    public void onEventTriggered(String eventDescription) {
        System.out.println(" Event received in GUI: " + eventDescription);  // ✅ Debugging statement
        SwingUtilities.invokeLater(() -> {
            eventLog.append("Event Triggered: " + eventDescription + "\n");
            System.out.println(" Appended to JTextArea: " + eventDescription); // ✅ Debugging statement
        });
    }

    /**
     * Retrieves the text from the file path input field.
     *
     * @return The event file path entered by the user.
     */
    public String getFilePathText() {
        String filePathText = filePath.getText();
        return filePathText;
    }

    /**
     * Sets up the GUI components and layout.
     */
    private void setupUI() {
        // Set up GUI properties
        this.setTitle("Greenhouse Controls");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Add menu bar
        setJMenuBar(dropdownMenu);

        // File Input Section
        filePanel = new JPanel();
        filePanel.setLayout(new BorderLayout());
    }

    /**
     * Launches the Greenhouse GUI application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GreenhouseGUI gui = new GreenhouseGUI();
            gui.setVisible(true);

        });
    }

}
