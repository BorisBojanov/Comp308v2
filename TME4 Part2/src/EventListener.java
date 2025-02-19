/**
 * Interface for listening to event triggers in the GreenhouseControls system.
 * Provides a callback mechanism for notifying when an event occurs.
 * Used primarily for GUI integration.
 *
 * @author Boris B
 * @version 1.0 Jan 31, 2025
 */
public interface EventListener {
    
    /**
     * Callback method triggered when an event occurs.
     *
     * @param eventDescription The description of the triggered event.
     */
    void onEventTriggered(String eventDescription);
}
