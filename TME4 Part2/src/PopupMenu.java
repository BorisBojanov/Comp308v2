import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The PopupMenu class provides a right-click menu for controlling Greenhouse events.
 * It allows users to start, restart, terminate, suspend, and resume events within the GUI.
 * 
 * @author Boris B
 * @version 1.0 Jan 31, 2025
 */
public class PopupMenu extends JPopupMenu {

    /** Menu item to start events. */
    private final JMenuItem startItem;

    /** Menu item to restart events from a file. */
    private final JMenuItem restartItem;

    /** Menu item to terminate an event with a delay. */
    private final JMenuItem terminateItem;

    /** Menu item to suspend active events. */
    private final JMenuItem suspendItem;

    /** Menu item to resume suspended events. */
    private final JMenuItem resumeItem;

    /**
     * Constructs the popup menu and initializes all menu items with actions.
     *
     * @param greenhouse The GreenhouseControls instance managing the events.
     * @param gui The GreenhouseGUI instance for displaying messages and event logs.
     */
    public PopupMenu(GreenhouseControls greenhouse, GreenhouseGUI gui) {
        // Create menu items
        startItem = new JMenuItem("Start Events");
        restartItem = new JMenuItem("Restart Events");
        terminateItem = new JMenuItem("Terminate Events");
        suspendItem = new JMenuItem("Suspend Events");
        resumeItem = new JMenuItem("Resume Events");

        // Add menu items
        add(startItem);
        add(restartItem);
        add(terminateItem);
        add(suspendItem);
        add(resumeItem);

        // Set actions for each menu item
        startItem.addActionListener(e -> {
            if (!greenhouse.getEventThreads().isEmpty()) {
                System.out.println(" Events are already running!");
                return;
            }
            greenhouse.run(); // Calls the correct method on GreenhouseControls
            updateMenuState(true);
        });

        restartItem.addActionListener(e -> {
            if (gui.getFilePathText().isEmpty()) {
                JOptionPane.showMessageDialog(gui, "No event file loaded.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!greenhouse.getEventThreads().isEmpty()) {
                JOptionPane.showMessageDialog(gui, "Cannot restart while events are running!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            greenhouse.loadEventsFromFile(gui.getFilePathText());
            System.out.println("🔄 Restarting event file: " + gui.getFilePathText());

            startItem.setEnabled(true); // Re-enable start button
        });

        terminateItem.addActionListener(e -> {
            if (greenhouse.getEventThreads().isEmpty()) {
                JOptionPane.showMessageDialog(gui, "No events are running.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String delayStr = JOptionPane.showInputDialog(gui, "Enter delay time in milliseconds:", "Terminate Event", JOptionPane.QUESTION_MESSAGE);

            try {
                long delay = Long.parseLong(delayStr);
                greenhouse.startEvent("Terminate", delay, 1);
                System.out.println("⏳ Terminate event scheduled in " + delay + "ms.");

                updateMenuState(false);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(gui, "Invalid delay time!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        suspendItem.addActionListener(e -> {
            greenhouse.suspendEvents();
            suspendItem.setEnabled(false);
            resumeItem.setEnabled(true);
        });

        resumeItem.addActionListener(e -> {
            greenhouse.resumeEvents();
            suspendItem.setEnabled(true);
            resumeItem.setEnabled(false);
        });

        // Initial menu state
        updateMenuState(false);
    }


    /**
     * Updates the menu item states based on whether events are running.
     *
     * @param isRunning True if events are running, false otherwise.
     */
    public void updateMenuState(boolean isRunning) {
        suspendItem.setEnabled(isRunning);
        startItem.setEnabled(!isRunning);
        terminateItem.setEnabled(isRunning);
        resumeItem.setEnabled(false);
        restartItem.setEnabled(!isRunning);
    }

    /**
     * Attaches the popup menu to the GUI, allowing right-click access.
     *
     * @param gui The GreenhouseGUI instance to attach the menu.
     * @param popupMenu The PopupMenu instance to be attached.
     */
    public static void attachPopup(GreenhouseGUI gui, PopupMenu popupMenu) {
        gui.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showPopup(e);
            }

            private void showPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }
}
