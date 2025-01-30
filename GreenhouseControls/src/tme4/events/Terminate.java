package tme4.events;

import tme4.*;

public class Terminate extends Event {
    public Terminate(GreenhouseControls greenhouse, long delayTime) { 
        super(greenhouse, delayTime); 
    }

    @Override
    public void action() { System.exit(0); }

    @Override
    public String toString() { return "Terminating";  }
  }
