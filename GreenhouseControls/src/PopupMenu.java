

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PopupMenu extends JPopupMenu {
    private final JMenuItem startItem;
    private final JMenuItem restartItem;
    private final JMenuItem terminateItem;
    private final JMenuItem suspendItem;
    private final JMenuItem resumeItem;

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
                System.out.println("❌ Events are already running!");
                return;
            }
            greenhouse.run(); // ✅ Calls the correct method on GreenhouseControls
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

    public void updateMenuState(boolean isRunning) {
        startItem.setEnabled(!isRunning);
        suspendItem.setEnabled(isRunning);
        terminateItem.setEnabled(isRunning);
        resumeItem.setEnabled(false);
        restartItem.setEnabled(!isRunning);
    }

    // Adds the popup trigger to the main GUI
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
