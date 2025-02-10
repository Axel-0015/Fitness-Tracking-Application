package model;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestActivity {
    
    private Activity activity;

    @BeforeEach
    void setup() {
        activity = new Activity("running", 100, "2024-10-1");
    }

    @Test
    public void testConstructor() {
        assertEquals("running", activity.getActivityName());
        assertEquals(100, activity.getDuration());
        assertEquals("2024-10-1", activity.getDate());
        assertEquals(11, activity.getCaloriesBurnedPerMin());
    }

    @Test
    public void testSetCaloriesBurnedPerMinForDifferentActivity() {
        assertEquals(11, activity.getCaloriesBurnedPerMin());
        Activity b = new Activity("basketball", 60, "2024-10-1");
        assertEquals(12, b.getCaloriesBurnedPerMin());
        Activity c = new Activity("swimming", 60, "2024-10-1");
        assertEquals(7, c.getCaloriesBurnedPerMin());
        Activity d = new Activity("badminton", 60, "2024-10-1");
        assertEquals(5, d.getCaloriesBurnedPerMin());

    }

    @Test
    public void testCauculateCaloriesBurned() {
        activity.calculateCaloriesBurned();
        assertEquals(1100,activity.getCaloriesBurned());
    }

    @Test
    public void testCalculateCaloriesBurnedWhenDurationIsZero() {
        Activity a = new Activity("runnning", 0, "2024-10-1");
        a.calculateCaloriesBurned();
        assertEquals(0,a.getCaloriesBurned());
    }

    @Test
    public void testGetActivityName() {
        assertEquals("running", activity.getActivityName());
    }

    @Test
    public void testGetCaloriesBurnedPerMin() {
        assertEquals(11, activity.getCaloriesBurnedPerMin());
    }

    @Test
    public void testGetCaloriesBurned() {
        activity.calculateCaloriesBurned();
        assertEquals(1100, activity.getCaloriesBurned());
    }

    @Test
    public void testGetDuration() {
        assertEquals(100, activity.getDuration());
    }

    @Test
    public void testGetDate() {
        assertEquals("2024-10-1", activity.getDate());
    }





}
