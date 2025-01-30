  /*
   * Modify Restart.action() to start the the system by reading events from a text file.
   * Use Scanner and an appropriate regular expression.
  */


package tme4.events;


import tme4.*;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.util.Scanner;
import java.io.File;

  public class Restart extends Event {
      private String eventsFile;
      
    public Restart(GreenhouseControls greenhouse,long delayTime, String filename) {
        super(greenhouse, delayTime);
        this.eventsFile = filename;
      }

    @Override
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

                String eventType = parts[1].trim();  // Extract event class name
                long time = Long.parseLong(parts[3].trim());

                try {
                    // Construct the fully qualified class name dynamically
                    String className = "tme4.events." + eventType;
                    Class<?> clazz = Class.forName(className);

                    // Get constructor (assume all events have (GreenhouseControls, long) constructor)
                    Constructor<?> constructor;

                    Event event;
                    if (eventType.equals("Bell") && parts.length >= 6) {
                        int rings = Integer.parseInt(parts[5].trim());
                        constructor = clazz.getConstructor(GreenhouseControls.class, long.class, int.class);
                        event = (Event) constructor.newInstance(greenhouse, time, rings);
                    } else {
                        constructor = clazz.getConstructor(GreenhouseControls.class, long.class);
                        event = (Event) constructor.newInstance(greenhouse, time);
                    }

                    greenhouse.addEvent(event);
                } catch (ClassNotFoundException e) {
                    System.err.println("❌ Error: Unknown event type '" + eventType + "'");
                } catch (NoSuchMethodException e) {
                    System.err.println("❌ Error: Constructor not found for event '" + eventType + "'");
                } catch (Exception e) {
                    System.err.println("❌ Error instantiating event '" + eventType + "': " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found - " + eventsFile);
        } catch (Exception e) {
            System.err.println("Error parsing file: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Restarting system";
    }
}