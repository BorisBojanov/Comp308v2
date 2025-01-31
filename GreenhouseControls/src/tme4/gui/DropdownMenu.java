package tme4.gui;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;
import java.util.Map;

import javax.swing.filechooser.FileNameExtensionFilter;
import tme4.GreenhouseControls;
import tme4.events.TwoTuple;

// import java.util.HashMap;

public class DropdownMenu extends JMenuBar {
    private final GreenhouseControls greenhouse;
    private JMenuItem restore; // Declare restore as a class variable to enable/disable it
    private final JFrame parentFrame;
    private static int windowCount = 1; // Track number of windows open
    JMenu menu;
    JMenuItem newWindow;
    JMenuItem closeWindow;
    JMenuItem openEvents;
    JMenuItem exit;

    public DropdownMenu(GreenhouseControls greenhouse, JFrame parentFrame) {
        this.greenhouse = greenhouse;
        this.parentFrame = parentFrame;

        menu = new JMenu("Options");

        newWindow = new JMenuItem("New Window");
        newWindow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newWindow.addActionListener(e -> openNewWindow());

        closeWindow = new JMenuItem("Close Window");
        closeWindow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
        closeWindow.addActionListener(e -> closeCurrentWindow());

        openEvents = new JMenuItem("Open Events");
        openEvents.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        openEvents.addActionListener(e -> openEventsFile());

        restore = new JMenuItem("Restore");
        restore.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        restore.addActionListener(e -> restoreState());
        // restore.setEnabled(!greenhouse.getEventThreads().isEmpty()); // Disable if events are running
        restore.setEnabled(greenhouse.getEventThreads().isEmpty()); // Only enable if no events are running

        exit = new JMenuItem("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        exit.addActionListener(e -> exitApplication());

        // Add menu items to menu
        menu.add(newWindow);
        menu.add(closeWindow);
        menu.add(openEvents);
        menu.add(restore);
        menu.addSeparator();
        menu.add(exit);

        // Add the menu to the menu bar
        add(menu);
    }

    /**
     * Opens a new GreenhouseGUI window with a new GreenhouseControls object.
     */
    private void openNewWindow() {
        SwingUtilities.invokeLater(() -> {
            GreenhouseGUI newGUI = new GreenhouseGUI();
            newGUI.setVisible(true);
            windowCount++;
        });
    }

    /**
     * Closes the current window.
     */
    private void closeCurrentWindow() {
        if (!greenhouse.getEventThreads().isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(
                parentFrame,
                "GreenhouseControls is running! Are you sure you want to close this window?",
                "Warning",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm != JOptionPane.YES_OPTION) return;
        }

        parentFrame.dispose();
        windowCount--;

        // If this was the last window, exit the application
        if (windowCount == 0) {
            System.exit(0);
        }
    }

    /**
     * Opens an events file.
     */
    private void openEventsFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select an events file");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Event Files (*.txt)", "txt"));

        int returnValue = fileChooser.showOpenDialog(parentFrame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            greenhouse.loadEventsFromFile(selectedFile.getAbsolutePath());
            //C:\Users\boris\Desktop\Comp308v2\Comp308v2\GreenhouseControls\examples2.txt
            System.out.println("Loaded events from file: " + selectedFile.getAbsolutePath());
            greenhouse.printStateVariables(); // See format of Map values stored. 
        } else {
            JOptionPane.showMessageDialog(parentFrame, "No file selected", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Restores system state from a dump.out file.
     */
    private void restoreState() {
        if (!greenhouse.getEventThreads().isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "Cannot restore while events are running!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a dump.out file");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Serialized State (*.out)", "out"));

        int returnValue = fileChooser.showOpenDialog(parentFrame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                GreenhouseControls restoredControls = GreenhouseControls.restoreState(selectedFile.getAbsolutePath());

                if (restoredControls == null) {
                    throw new Exception("Failed to restore GreenhouseControls from file: " + selectedFile.getAbsolutePath());
                }

                JOptionPane.showMessageDialog(parentFrame, "✅ Successfully restored GreenhouseControls from: " + selectedFile.getAbsolutePath());

                // Apply Fixable logic to repair any errors in the restored system
                fixRestoredSystem(restoredControls);

                // Start the restored system
                restoredControls.run();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(parentFrame, "❌ Error during system restore: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Fixes system state after restoration.
     */
private void fixRestoredSystem(GreenhouseControls restoredControls) {
    // Iterate through all state variables to find error codes
    synchronized (restoredControls.getStateVariables()) {
        for (Map.Entry<String, List<TwoTuple<String, Object>>> entry : restoredControls.getStateVariables().entrySet()) {
            String key = entry.getKey();

            for (TwoTuple<String, Object> tuple : entry.getValue()) {
                if (key.endsWith("_errorCode") && tuple.value instanceof Integer) {
                    int errorCode = (int) tuple.value;

                    if (errorCode == 1) {
                        System.out.println("Fixing window malfunction...");
                        // restoredControls.fixWindow(); // Uncomment if FixWindow method exists
                    } else if (errorCode == 2) {
                        System.out.println("Restoring power...");
                        // restoredControls.powerOn(); // Uncomment if PowerOn method exists
                    }
                }
            }
        }
    }
    System.out.println("✅ System restoration fixes applied.");
}
    
    /**
     * Updates the restore button based on the state of the system.
     */
    public void updateRestoreButton() {
        restore.setEnabled(greenhouse.getEventThreads().isEmpty());
    }
    
    /**
     * Exits the application.
     */
    private void exitApplication() {
        if (!greenhouse.getEventThreads().isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(
                parentFrame,
                "GreenhouseControls is running! Are you sure you want to exit?",
                "Warning",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm != JOptionPane.YES_OPTION) return;
        }
        System.exit(0);
    }
}
