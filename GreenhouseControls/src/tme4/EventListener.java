    package tme4;
    
    //Define a callback mechanism so that each event can send messages to a GUI panel
    // Add a listener for GUI output
    

    /**
     * Custom interface to listen for Greenhouse events.
     */
    public interface EventListener {
        void onEventTriggered(String eventDescription);
    }

