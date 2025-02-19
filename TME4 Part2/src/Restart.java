/**
 * The Restart event reads events from a specified file and schedules them for execution.
 * This allows the Greenhouse system to restart with a predefined sequence of events.
 * 
 * @author Boris B
 * @version 1.0 Jan 31, 2025
 */
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.util.Scanner;
import java.io.File;

  public class Restart extends Event {
    /** The file containing the event sequence to be restarted. */
    private String eventsFile;

    /**
     * Constructs a Restart event that loads event sequences from a file.
     *
     * @param greenhouse The GreenhouseControls instance managing the event.
     * @param delayTime The delay before executing the restart.
     * @param filename The name of the file containing event sequences.
     */      
    public Restart(GreenhouseControls greenhouse,long delayTime, String filename) {
        super(greenhouse, delayTime);
        this.eventsFile = filename;
      }

    /**
     * Reads and processes the events file, dynamically creating event instances
     * based on the file contents.
     */
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
                    System.err.println(" Error: Unknown event type '" + eventType + "'");
                } catch (NoSuchMethodException e) {
                    System.err.println(" Error: Constructor not found for event '" + eventType + "'");
                } catch (Exception e) {
                    System.err.println(" Error instantiating event '" + eventType + "': " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found - " + eventsFile);
        } catch (Exception e) {
            System.err.println("Error parsing file: " + e.getMessage());
        }
    }

    /**
     * Returns a string representation of the event.
     *
     * @return A string indicating the Restart event.
     */
    @Override
    public String toString() {
        return "Restarting system";
    }
}