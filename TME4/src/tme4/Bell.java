package tme4;


import Event;

// Bell: Rings the bell a specified number of times
public class Bell extends Event {
    private int rings;

    public Bell(long delayTime, int rings) {
        super(delayTime);
        this.rings = rings;
    }

    @Override
    public void action() {
        if (rings > 0) {
            System.out.println("Bing!");
            rings--;
            // Re-add the Bell event with a reduced number of rings
            Event newBell = new Bell(2000, rings);
            newBell.start(); // Start the new thread for the next ring
        }
    }

    @Override
    public String toString() {
        return "Bell event with " + rings + " rings remaining";
    }
}