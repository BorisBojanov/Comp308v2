import org.junit.jupiter.api.*;

import java.io.File;

class GreenhouseControlsTest {

    private GreenhouseControls greenhouse;

    @BeforeEach
    void setUp() {
        greenhouse = new GreenhouseControls();
    }

    @Test
    @DisplayName("Test Starting an Event")
    void testStartEvent() {
        greenhouse.startEvent("TestEvent", 500);
        assertFalse(greenhouse.getEventThreads().isEmpty(), "Event thread map should not be empty after starting an event.");
    }

    @Test
    @DisplayName("Test Suspending and Resuming Events")
    void testSuspendResumeEvents() {
        greenhouse.startEvent("TestEvent", 500);
        assertFalse(greenhouse.getEventThreads().isEmpty(), "There should be active events.");

        greenhouse.suspendEvents();
        assertFalse(greenhouse.getEventThreads().isEmpty(), "Events should still exist after suspension.");

        greenhouse.resumeEvents();
        assertFalse(greenhouse.getEventThreads().isEmpty(), "Events should still exist after resumption.");
    }

    @Test
    @DisplayName("Test Loading Events from File")
    void testLoadEventsFromFile() {
        greenhouse.loadEventsFromFile("test_events.txt");
        assertFalse(greenhouse.getEventThreads().isEmpty(), "Event thread map should not be empty after loading from file.");
    }

    @AfterEach
    void tearDown() {
        greenhouse = null;
    }
}
