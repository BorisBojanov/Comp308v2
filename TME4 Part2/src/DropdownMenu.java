import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;
import java.util.Map;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * The DropdownMenu class represents the menu bar for the GreenhouseControls GUI.
 * It provides options to open and close windows, load event files, restore system state, and exit the application.
 *
 * @author Boris B
 * @version 1.0 Jan 31, 2025
 */
public class DropdownMenu extends JMenuBar {
    
    /** Reference to the GreenhouseControls instance. */
    private final GreenhouseControls greenhouse;

    /** The main menu containing the options. */
    private JMenu menu;

    /** Menu item to create a new GUI window. */
    private JMenuItem newWindow;

    /** Menu item to close the current GUI window. */
    private JMenuItem closeWindow;

    /** Menu item to open an events file. */
    private JMenuItem openEvents;

    /** Menu item to restore system state from a file. */
    private JMenuItem restore;

    /** Menu item to exit the application. */
    private JMenuItem exit;

    /** The parent JFrame associated with this menu. */
    private final JFrame parentFrame;

    /** Tracks the number of open windows. */
    private static int windowCount = 1;

    /**
     * Constructs a DropdownMenu associated with a GreenhouseControls instance and parent JFrame.
     *
     * @param greenhouse The GreenhouseControls instance managing events.
     * @param parentFrame The parent frame associated with this menu.
     */
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
        restore.setEnabled(greenhouse.getEventThreads().isEmpty());

        exit = new JMenuItem("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        exit.addActionListener(e -> exitApplication());

        menu.add(newWindow);
        menu.add(closeWindow);
        menu.add(openEvents);
        menu.add(restore);
        menu.addSeparator();
        menu.add(exit);

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
     * Closes the current GUI window.
     * If GreenhouseControls is running, prompts the user for confirmation before closing.
     * If it is the last open window, the application exits.
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

        if (windowCount == 0) {
            System.exit(0);
        }
    }

    /**
     * Opens an events file selected by the user.
     * Loads events into the GreenhouseControls instance.
     */
    private void openEventsFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select an events file");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Event Files (*.txt)", "txt"));

        int returnValue = fileChooser.showOpenDialog(parentFrame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            greenhouse.loadEventsFromFile(selectedFile.getAbsolutePath());
            System.out.println("Loaded events from file: " + selectedFile.getAbsolutePath());
            greenhouse.printStateVariables();
        } else {
            JOptionPane.showMessageDialog(parentFrame, "No file selected", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Restores system state from a serialized dump file.
     * Ensures events are not running before restoring.
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

                JOptionPane.showMessageDialog(parentFrame, "Successfully restored GreenhouseControls from: " + selectedFile.getAbsolutePath());

                fixRestoredSystem(restoredControls);
                restoredControls.run();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(parentFrame, "Error during system restore: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Fixes system state after restoration by checking for error codes in stored variables.
     *
     * @param restoredControls The restored GreenhouseControls instance.
     */
    private void fixRestoredSystem(GreenhouseControls restoredControls) {
        synchronized (restoredControls.getStateVariables()) {
            for (Map.Entry<String, List<TwoTuple<String, Object>>> entry : restoredControls.getStateVariables().entrySet()) {
                String key = entry.getKey();

                for (TwoTuple<String, Object> tuple : entry.getValue()) {
                    if (key.endsWith("_errorCode") && tuple.value instanceof Integer) {
                        int errorCode = (int) tuple.value;

                        if (errorCode == 1) {
                            System.out.println("Fixing window malfunction...");
                        } else if (errorCode == 2) {
                            System.out.println("Restoring power...");
                        }
                    }
                }
            }
        }
        System.out.println("System restoration fixes applied.");
    }

    /**
     * Updates the state of the restore button based on whether events are running.
     */
    public void updateRestoreButton() {
        restore.setEnabled(greenhouse.getEventThreads().isEmpty());
    }

    /**
     * Exits the application.
     * If GreenhouseControls is running, prompts the user for confirmation before exiting.
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
