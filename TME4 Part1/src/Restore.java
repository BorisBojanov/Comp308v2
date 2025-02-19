import java.io.*;

/**
 * The Restore class is responsible for restoring the state of a GreenhouseControls system
 * from a serialized file. It loads the saved state, prints the current system status,
 * attempts to fix any detected issues, and resumes system operation.
 *
 * @author Boris Bojanov
 * @version 1.0
 */
public class Restore {
    /** The filename containing the serialized GreenhouseControls state. */
    private String dumpFile;

    /** The restored GreenhouseControls object. */
    private GreenhouseControls restoredControls;

    /**
     * Constructs a Restore object and attempts to restore the GreenhouseControls
     * from the specified dump file.
     *
     * @param dumpFile The file containing the serialized GreenhouseControls state.
     */
    public Restore(String dumpFile) {
        this.dumpFile = dumpFile;
        try {
            // Restore the serialized GreenhouseControls object
            restoredControls = GreenhouseControls.restoreState(dumpFile);
            if (restoredControls == null) {
                throw new Exception("Failed to restore GreenhouseControls from file: " + dumpFile);
            }
            System.out.println("Successfully restored GreenhouseControls from " + dumpFile);
            printState();

            // Fix the system based on detected errors
            fixSystem();

            // Resume the system
            System.out.println("Resuming system...");
            restoredControls.run();
        } catch (Exception e) {
            System.err.println("Error during system restore: " + e.getMessage());
        }
    }

    /**
     * Prints the current state of the restored GreenhouseControls system,
     * including the status of various components.
     */
    private void printState() {
        System.out.println("Current system state:");
        System.out.println("Light: " + getState("light", false));
        System.out.println("Water: " + getState("water", false));
        System.out.println("Fan: " + getState("fans", false));
        System.out.println("Thermostat: " + getState("thermostat", "Unknown"));
        System.out.println("Window: " + (getState("window_ok", true) ? "OK" : "Malfunction"));
        System.out.println("Power: " + getState("power", false));
        System.out.println("Error Code: " + getState("error", 0));
    }

    /**
     * Attempts to fix the system based on the detected error code.
     * If an error is detected, the appropriate fix method is called.
     */
    private void fixSystem() {
        int errorCode = getState("error", 0);
        if (errorCode == 1) {
            System.out.println("Fixing window malfunction...");
            restoredControls.fixWindow();
        } else if (errorCode == 2) {
            System.out.println("Restoring power...");
            restoredControls.powerOn();
        } else {
            System.out.println("No errors to fix.");
        }
    }

    /**
     * Helper method to get state variables from GreenhouseControls safely.
     *
     * @param key          The name of the variable.
     * @param defaultValue The default value if the variable is not found.
     * @return The current value of the variable.
     * @param <T>          The expected type of the variable.
     */
    private <T> T getState(String key, T defaultValue) {
        return (T) restoredControls.getVariable(key, defaultValue).value;
    }
}
