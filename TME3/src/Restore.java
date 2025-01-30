import java.io.*;
import java.util.*;

public class Restore {
    private String dumpFile;
    private GreenhouseControls restoredControls;

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

            // Fix the system using Fixable interface
            fixSystem();

            // Resume the system
            System.out.println("Resuming system...");
            restoredControls.run();
        } catch (Exception e) {
            System.err.println("Error during system restore: " + e.getMessage());
        }
    }

    // Print the current state of the restored system
    private void printState() {
        System.out.println("Current system state:");
        System.out.println("Light: " + (restoredControls.isLightOn() ? "On" : "Off"));
        System.out.println("Water: " + (restoredControls.isWaterOn() ? "On" : "Off"));
        System.out.println("Fan: " + (restoredControls.isFanOn() ? "On" : "Off"));
        System.out.println("Thermostat: " + restoredControls.getThermostat());
        System.out.println("Window: " + (restoredControls.isWindowOk() ? "OK" : "Malfunction"));
        System.out.println("Power: " + (restoredControls.isPowerOn() ? "On" : "Off"));
        System.out.println("Error Code: " + restoredControls.getError());
    }

    // Fix the system using Fixable implementations
    private void fixSystem() {
        if (restoredControls.getError() == 1) {
            System.out.println("Fixing window malfunction...");
            GreenhouseControls.FixWindow fixWindow = restoredControls.new FixWindow();
            fixWindow.fix();
        } else if (restoredControls.getError() == 2) {
            System.out.println("Restoring power...");
            GreenhouseControls.PowerOn powerOn = restoredControls.new PowerOn();
            powerOn.fix();
        } else {
            System.out.println("No errors to fix.");
        }
    }
}
