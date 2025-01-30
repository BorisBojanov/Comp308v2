//: innerclasses/GreenhouseControls.java
// This produces a specific application of the
// control system, all in a single class. Inner
// classes allow you to encapsulate different
// functionality for each type of event.
// From 'Thinking in Java, 4th ed.' (c) Bruce Eckel 2005
// www.BruceEckel.com. See copyright notice in CopyRight.txt.

/***********************************************************************
 * Adapated for COMP308 Java for Programmer, 
 *		SCIS, Athabasca University
 *
 * Assignment: TME3
 * @author: Steve Leung
 * @date  : Oct 21, 2005
 *
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import tme3.*;

interface Fixable {
  void fix();
  void log();
}

public class GreenhouseControls extends Controller implements Serializable, Fixable{
  private boolean light = false;
  private boolean water = false;
  private boolean fan = false;
  private String thermostat = "Day";
  private boolean windowok = true;
  private boolean poweron = true;
  private int errorcode = 0; // Error code: 1 = WindowMalfunction, 2 = PowerOut
  private String eventsFile = "examples1.txt";

  // Utility methods for Restore to query the system state
  public boolean isLightOn() {
    return light;
  }

  public boolean isWaterOn() {
    return water;
  }

  public boolean isFanOn() {
    return fan;
  }

  public String getThermostat() {
    return thermostat;
  }

  public boolean isWindowOk() {
    return windowok;
  }

  public boolean isPowerOn() {
    return poweron;
  }

  // Fixable methods
  @Override
  public void fix() {
      if (!windowok) {
          windowok = true;
          System.out.println("Window fixed.");
      }
      if (!poweron) {
          poweron = true;
          System.out.println("Power restored.");
      }
      errorcode = 0; // Reset error code
      log(); // Log the fix
  }
  
  @Override
  public void log() {
      try (PrintWriter writer = new PrintWriter(new FileWriter("fix.log", true))) {
          String timestamp = Calendar.getInstance().getTime().toString();
          writer.printf("%s | Fix applied. Window: %s, Power: %s, Error Code Reset.%n",
                  timestamp, windowok ? "OK" : "Malfunction", poweron ? "On" : "Off");
          System.out.println("Fix logged.");
      } catch (IOException e) {
          System.err.println("Failed to write to fix.log: " + e.getMessage());
      }
  }

  // Event inner classes
  public class LightOn extends Event {
    public LightOn(long delayTime) { super(delayTime); }
    public void action() {
      // Put hardware control code here to
      // physically turn on the light.
      light = true;
    }
    public String toString() { return "Light is on"; }
  }
  public class LightOff extends Event {
    public LightOff(long delayTime) { super(delayTime); }
    public void action() {
      // Put hardware control code here to
      // physically turn off the light.
      light = false;
    }
    public String toString() { return "Light is off"; }
  }
  public class WaterOn extends Event {
    public WaterOn(long delayTime) { super(delayTime); }
    public void action() {
      // Put hardware control code here.
      water = true;
    }
    public String toString() {
      return "Greenhouse water is on";
    }
  }
  public class WaterOff extends Event {
    public WaterOff(long delayTime) { super(delayTime); }
    public void action() {
      // Put hardware control code here.
      water = false;
    }
    public String toString() {
      return "Greenhouse water is off";
    }
  }
  public class ThermostatNight extends Event {
    public ThermostatNight(long delayTime) {
      super(delayTime);
    }
    public void action() {
      // Put hardware control code here.
      thermostat = "Night";
    }
    public String toString() {
      return "Thermostat on night setting";
    }
  }
  public class ThermostatDay extends Event {
    public ThermostatDay(long delayTime) {
      super(delayTime);
    }
    public void action() {
      // Put hardware control code here.
      thermostat = "Day";
    }
    public String toString() {
      return "Thermostat on day setting";
    }
  }
  
  // An example of an action() that inserts a
  // new one of itself into the event list:
  /*jhgcfdx
   * Modify Bell Event so that it will be able to run an arbitrary number of times separated by 2000 msec each. 
   * To facilitate this requirement, you should generate the number of Bell Events specified in the rings parameter in the examples file.
   * Please pay special attention to the possibility that other events might be run in between the various Bell events.
  */
  public class Bell extends Event {
    private int rings;
    public Bell(long delayTime, int rings) { 
      super(delayTime); 
      this.rings = rings;
    }
    
    public void action() {
      if (rings > 0) {
        rings--;
        addEvent(new Bell(2000, rings));
      }
    }
    public String toString() { return "Bing!"; }
  }

  // Create two Event classes called FansOn and FansOff.
  // The action() of these two classes should modify fan to true or false respectively.
  public class FansOn extends Event {
    public FansOn(long delayTime) { super(delayTime); }
    public void action() {
      // Put hardware control code here.
      fan = true;
    }
    public String toString() {
      return "Fans are on";
    }
  }
  public class FansOff extends Event {
    public FansOff(long delayTime) { super(delayTime); }
    public void action() {
      // Put hardware control code here.
      fan = false;
    }
    public String toString() {
      return "Fans are off";
    }
  }
  /*
  Create a WindowMalfunction and PowerOut Events to simulate problems that may occur in a GreenhouseControls. 
  The event should set the following boolean variables as appropriate in GreenhouseControls:

  windowok = false;
  poweron = false;

  After setting the variables, WindowMalfunction or PowerOut should throw an exception specifying the faulty condition. 
  Create a ControllerException class that extends Exception for this purpose.
  */ 
  public class WindowMalfunction extends Event {
    public WindowMalfunction(long delayTime) { super(delayTime); }
    public void action() throws ControllerException {
      if (!windowok) {
          System.out.println("Window malfunction already detected.");
          return;
      }
      windowok = false;
      errorcode = 1; // Set error code for WindowMalfunction
      throw new ControllerException("Window malfunction detected!");
    }

    public String toString() {
        return "Window malfunction occurred";
    }

  }

  public class PowerOut extends Event {
    public PowerOut(long delayTime) { super(delayTime); }
    public void action() throws ControllerException {
      if (!poweron) {
          System.out.println("Power outage already detected.");
          return;
      }
      poweron = false;
      errorcode = 2; // Set error code for PowerOut
      throw new ControllerException("Power outage detected!");
    }

    public String toString() {
        return "Power outage occurred";
    }
  }
  
  // Inner class to restore power
  public class PowerOn implements Fixable {
    @Override
    public void fix() {
        if (!poweron) {
            poweron = true;
            System.out.println("Power restored.");
        } else {
            System.out.println("Power is already on.");
        }
        log(); // Log the fix
    }

    @Override
    public void log() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("fix.log", true))) {
            String timestamp = Calendar.getInstance().getTime().toString();
            writer.printf("%s | Fix applied: Power restored.%n", timestamp);
            System.out.println("Fix logged: Power restored.");
        } catch (IOException e) {
            System.err.println("Failed to write to fix.log: " + e.getMessage());
        }
    }
}

  // Inner class to fix the window
  public class FixWindow implements Fixable {
    @Override
    public void fix() {
        if (!windowok) {
            windowok = true;
            System.out.println("Window fixed.");
        } else {
            System.out.println("Window is already operational.");
        }
        log(); // Log the fix
    }

    @Override
    public void log() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("fix.log", true))) {
            String timestamp = Calendar.getInstance().getTime().toString();
            writer.printf("%s | Fix applied: Window fixed.%n", timestamp);
            System.out.println("Fix logged: Window fixed.");
        } catch (IOException e) {
            System.err.println("Failed to write to fix.log: " + e.getMessage());
        }
    }
  }

  // Inner class to handle errors
  @Override
  public void run() {
      while (eventList.size() > 0) {
          for (Event e : new ArrayList<>(eventList)) {
              if (e.ready()) {
                  try {
                      System.out.println(e);
                      e.action();
                      eventList.remove(e); // Remove event after execution
                  } catch (ControllerException ex) {
                      logError(ex.getMessage());
                      serializeState();
                      System.exit(1); // Exit after handling the error
                  }
              }
          }
      }
  }

  private void logError(String message) {
      try (PrintWriter writer = new PrintWriter(new FileWriter("error.log", true))) {
          String timestamp = Calendar.getInstance().getTime().toString();
          writer.printf("%s | Error Code: %d | %s%n", timestamp, errorcode, message);
          System.out.printf("Error logged: %s | Code: %d%n", message, errorcode);
      } catch (IOException e) {
          System.err.println("Failed to write to error.log: " + e.getMessage());
      }
  }

  private void serializeState() {
      try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("dump.out"))) {
          out.writeObject(this);
          System.out.println("State serialized to dump.out");
      } catch (IOException e) {
          System.err.println("Failed to serialize state: " + e.getMessage());
      }
  }

  public class ControllerException extends RuntimeException {
    public ControllerException(String message) {
      super(message);
    }
  }
 
  // Method to restore from serialized state
  public static GreenhouseControls restoreState(String filename) {
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
      return (GreenhouseControls) in.readObject();
    } catch (IOException | ClassNotFoundException e) {
        System.err.println("Failed to restore state: " + e.getMessage());
        return null;
    }
  }


  /*
   * Modify Restart.action() to start the the system by reading events from a text file.
   * Use Scanner and an appropriate regular expression.
  */
  public class Restart extends Event {
      public Restart(long delayTime, String filename) {
          super(delayTime);
          eventsFile = filename;
      }

      public void action() {
          try (Scanner scanner = new Scanner(new File(eventsFile))) {
              while (scanner.hasNextLine()) {
                  String line = scanner.nextLine().trim();
                  if (line.isEmpty()) continue; // Skip empty lines

                  String[] parts = line.split("[,=]");
                  if (parts.length < 4) {
                      System.out.println("Invalid event format: " + line);
                      continue;
                  }

                  String eventType = parts[1].trim();
                  long time = Long.parseLong(parts[3].trim());
                  Event event = null;

                  switch (eventType) {
                      case "ThermostatNight":
                          event = new ThermostatNight(time);
                          break;
                      case "ThermostatDay":
                          event = new ThermostatDay(time);
                          break;
                      case "LightOn":
                          event = new LightOn(time);
                          break;
                      case "LightOff":
                          event = new LightOff(time);
                          break;
                      case "WaterOff":
                          event = new WaterOff(time);
                          break;
                      case "WaterOn":
                          event = new WaterOn(time);
                          break;
                      case "Bell":
                          if (parts.length >= 6) {
                              int rings = Integer.parseInt(parts[5].trim());
                              event = new Bell(time, rings);
                          } else {
                              System.out.println("Invalid Bell event format: " + line);
                          }
                          break;
                      case "FansOn":
                          event = new FansOn(time);
                          break;
                      case "FansOff":
                          event = new FansOff(time);
                          break;
                      case "WindowMalfunction":
                          event = new WindowMalfunction(time);
                          break;
                      case "PowerOut":
                          event = new PowerOut(time);
                          break;
                      case "Terminate":
                          event = new Terminate(time);
                          break;
                      default:
                          System.out.println("Unknown event type: " + eventType);
                  }
                  if (event != null) {
                      addEvent(event);
                  }
              }
          } catch (FileNotFoundException e) {
              System.err.println("Error: File not found - " + eventsFile);
          } catch (Exception e) { 
              System.err.println("Error parsing file: " + e.getMessage());
          }
      }

    public String toString() {
        return "Restarting system";
    }
}

  public class Terminate extends Event {
    public Terminate(long delayTime) { super(delayTime); }
    public void action() { System.exit(0); }
    public String toString() { return "Terminating";  }
  }

  /**
   * Get the error code
   * @return int errorcode
   */
  public int getError(){
    return errorcode;
  }

  /**
   * @param errorcode
   * @return Fixable object to fix the error
   */
  public Fixable getFixable(int errorcode){
    switch (errorcode) {
      case 1:
        return new FixWindow();
      case 2:
        return new PowerOn();
      default:
        return null;
    }
  }



  public static void printUsage() {
    System.out.println("Correct format: ");
    System.out.println("  java GreenhouseControls -f <filename>, or");
    System.out.println("  java GreenhouseControls -d dump.out");
  }

//---------------------------------------------------------
  public static void main(String[] args) {
    if (args.length < 2) {
        System.out.println("Invalid number of parameters");
        printUsage();
        return;
    }
    try {
        String option = args[0];
        String filename = args[1];

        if ("-f".equals(option)) {
            GreenhouseControls gc = new GreenhouseControls();
            gc.addEvent(gc.new Restart(1000, filename));
            gc.run();
        } else if ("-d".equals(option)) {
            new Restore(filename); // Create a Restore object
        } else {
            System.out.println("Invalid option");
            printUsage();
        }
    } catch (ArrayIndexOutOfBoundsException e) {
        System.out.println("Invalid number of parameters");
        printUsage();
    }
}

} ///:~
