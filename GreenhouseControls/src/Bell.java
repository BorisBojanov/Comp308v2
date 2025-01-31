

public class Bell extends Event {
    public int rings;

    public Bell(GreenhouseControls greenhouse, long delayTime) {
        super(greenhouse, delayTime);
        rings = 1;
    }

    public Bell(GreenhouseControls greenhouse, long delayTime, int rings) {
        super(greenhouse, delayTime);
        this.rings = rings;
    }

    @Override
    public void action() {
        if (rings > 0) {
            System.out.println("Bing!");
            rings--;
            if (rings > 0) {
                Bell nextBell = new Bell(greenhouse, delayTime, rings);
                greenhouse.addEvent(nextBell);
            }
        }
    }

    @Override
    public String toString() {
        return "Bell Event - Remaining Rings: " + rings;
    }
}