/**
Added keyboard shortcuts for all menu items:

Ctrl + N → New Window
Ctrl + W → Close Window
Ctrl + O → Open Events
Ctrl + R → Restore
Ctrl + Q → Exit

- Shortcuts work globally within the application.
- Users can quickly navigate menus without a mouse.
*/


package tme4.gui;

import tme4.GreenhouseControls;
import tme4.Event;
import javax.swing.*;
import java.awt.*;

public class GreenhouseGUI extends JFrame implements Event.EventListener {
    private final GreenhouseControls greenhouse;
    private final JTextArea eventLog;
    private final JTextField filePathField;
    private final DropdownMenu dropdownMenu;

    public GreenhouseGUI() {
        greenhouse = new GreenhouseControls();
        dropdownMenu = new DropdownMenu(greenhouse, this); // Pass JFrame reference
        
        Event.setEventListener(this);  // ✅ This must be set to receive events!

        // setupUI(); // Move all UI-related setup to a separate method for clarity.
    
        // Set up GUI properties
        setTitle("Greenhouse Controls");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Add menu bar
        setJMenuBar(dropdownMenu);

        // File Input Section
        JPanel filePanel = new JPanel();
        filePanel.setLayout(new BorderLayout());

        filePathField = new JTextField("Enter file path here...");
        JButton loadButton = new JButton("Load Events");

        filePanel.add(filePathField, BorderLayout.CENTER);
        filePanel.add(loadButton, BorderLayout.EAST);
        add(filePanel, BorderLayout.NORTH);


        // Event Log Display
        eventLog = new JTextArea();
        eventLog.setEditable(false);
        add(new JScrollPane(eventLog), BorderLayout.CENTER);


        // Control Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3));

        JButton startButton = new JButton("Start Events");
        JButton suspendButton = new JButton("Suspend Events");
        JButton resumeButton = new JButton("Resume Events");
        JButton terminateButton = new JButton("Terminate Events");
        JButton restartButton = new JButton("Restart Events");

        buttonPanel.add(startButton);
        buttonPanel.add(suspendButton);
        buttonPanel.add(resumeButton);
        buttonPanel.add(terminateButton);
        buttonPanel.add(restartButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Create Popup Menu
        PopupMenu popupMenu = new PopupMenu(greenhouse, this);
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
        loadButton.addActionListener(e -> greenhouse.loadEventsFromFile(filePathField.getText()));
        restartButton.addActionListener(e -> {
            if (filePathField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "No event file loaded.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            if (!greenhouse.getEventThreads().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Cannot restart while events are running!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            greenhouse.loadEventsFromFile(filePathField.getText());
            System.out.println("🔄 Restarting event file: " + filePathField.getText());
    
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

        // Register GUI as event listener
        Event.setEventListener(this);
        
    }

    @Override
    public void onEventTriggered(String eventDescription) {
        System.out.println("🔔 Event received in GUI: " + eventDescription);  // ✅ Debugging statement
        SwingUtilities.invokeLater(() -> {
            eventLog.append("Event Triggered: " + eventDescription + "\n");
            System.out.println("✅ Appended to JTextArea: " + eventDescription); // ✅ Debugging statement
        });
    }

    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GreenhouseGUI gui = new GreenhouseGUI();
            gui.setVisible(true);
        });
    }

    public String getFilePath() {
        String filePath = filePathField.getText();
        return filePath;
    }
}
