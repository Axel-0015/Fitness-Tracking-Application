package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestUser {

    private User user;

    @BeforeEach
    void setup() {
        user = new User("Axel");
    }

    @Test
    public void testConstructor() {
        assertEquals("Axel", user.getName());
        assertEquals(0, user.getActivities().size());
        assertEquals(0, user.calculateTotalCaloriesBurned());
        assertEquals(0, user.getLongTermGoal());
        assertFalse(user.getIsGoalAchieved());
    }

    @Test
    public void testAddActivityOneTime() {
        user.addActivity("running", 60, "2024-10-1");
        assertEquals("running", user.getActivities().get(0).getActivityName());
        assertEquals(60, user.getActivities().get(0).getDuration());
        assertEquals("2024-10-1", user.getActivities().get(0).getDate());
    }

    @Test
    public void testAddActivityMutipleTime() {
        user.addActivity("running", 60, "2024-10-1");
        user.addActivity("cycling", 60, "2024-10-1");
        user.addActivity("basketball", 60, "2024-10-1");
        assertEquals(3, user.getActivities().size());
        assertEquals("running", user.getActivities().get(0).getActivityName());
        assertEquals(60, user.getActivities().get(0).getDuration());
        assertEquals("2024-10-1", user.getActivities().get(0).getDate());
        assertEquals("cycling", user.getActivities().get(1).getActivityName());
        assertEquals(60, user.getActivities().get(1).getDuration());
        assertEquals("2024-10-1", user.getActivities().get(1).getDate());
        assertEquals("basketball", user.getActivities().get(2).getActivityName());
        assertEquals(60, user.getActivities().get(2).getDuration());
        assertEquals("2024-10-1", user.getActivities().get(2).getDate());
    }

    @Test
    public void testSetLongTermGoal() {
        user.setLongTermGoal(100);
        assertEquals(100, user.getLongTermGoal());
    }

    @Test
    public void testGetCaloriesBurnedByActivity() {
        user.addActivity("running", 60, "2024-10-1");
        user.addActivity("cycling", 60, "2024-10-1");
        user.addActivity("running", 60, "2024-10-1");
        assertEquals(1320, user.calculateCaloriesBurnedByActivity("running"));
    }

    @Test
    public void testCalculateCaloriesBurnedByActivityNoMatch() {
        user.addActivity("running", 60, "2024-10-01");
        assertEquals(0, user.calculateCaloriesBurnedByActivity("cycling"));
    }

    @Test
    public void testCalculateTotalDurationWithNoActivities() {
        assertEquals(0, user.calculateTotalDuration());
    }

    @Test
    public void testCalculateTotalDurationWithMultipleActivities() {
        user.addActivity("running", 30, "2024-10-1");
        user.addActivity("cycling", 45, "2024-10-1");
        user.addActivity("swimming", 60, "2024-10-1");
        assertEquals(135, user.calculateTotalDuration());
    }

    @Test
    public void testCalculateDurationByActivityWithNoActivities() {
        assertEquals(0, user.calculateDurationByActivity("running"));
    }

    @Test
    public void testCalculateDurationByActivityWithActivity() {
        user.addActivity("running", 30, "2024-10-1");
        user.addActivity("cycling", 45, "2024-10-1");
        user.addActivity("running", 20, "2024-10-1");
        assertEquals(50, user.calculateDurationByActivity("running"));
    }

    @Test
    public void testCalculateDurationByActivityActivityNotPresent() {
        user.addActivity("cycling", 45, "2024-10-1");
        assertEquals(0, user.calculateDurationByActivity("running"));
    }

    @Test
    public void testGetActivitiesByDateNoActivitiesOnDate() {
        user.addActivity("running", 30, "2024-10-1");
        user.addActivity("cycling", 45, "2024-10-1");
        List<Activity> activitiesOnDate = user.getActivitiesByDate("2024-10-2");
        assertTrue(activitiesOnDate.isEmpty());
    }

    @Test
    public void testGetActivitiesByDateActivitiesOnSameDate() {
        user.addActivity("running", 30, "2024-10-5");
        user.addActivity("cycling", 45, "2024-10-5");
        List<Activity> activitiesOnDate = user.getActivitiesByDate("2024-10-5");
        assertEquals(2, activitiesOnDate.size());
        assertEquals("running", activitiesOnDate.get(0).getActivityName());
        assertEquals("cycling", activitiesOnDate.get(1).getActivityName());
    }

    @Test
    public void testGetActivitiesByDateActivityOnSpecificDate() {
        user.addActivity("Running", 30, "2024-10-5");
        user.addActivity("Cycling", 45, "2024-10-6");
        List<Activity> activitiesOnDate = user.getActivitiesByDate("2024-10-5");
        assertEquals(1, activitiesOnDate.size());
        assertEquals("Running", activitiesOnDate.get(0).getActivityName());
    }

    

    @Test
    public void testClearMyActivities() {
        user.addActivity("running", 60, "2024-10-1");
        user.addActivity("cycling", 60, "2024-10-1");
        user.addActivity("basketball", 60, "2024-10-1");
        assertEquals(3, user.getActivities().size());
        user.clearMyActivities();
        assertEquals(0, user.getActivities().size());
        assertEquals(0, user.getTotalCaloriesBurned());
        assertEquals(false, user.getIsGoalAchieved());
    }

    @Test
    public void testCalculateTotalCaloriesBurned() {
        user.addActivity("running", 60, "2024-10-1");
        user.addActivity("cycling", 60, "2024-10-01");
        assertEquals(1200, user.calculateTotalCaloriesBurned());
    }



    @Test
    public void testCheckIsMyGoalAchievdIfTrue() {
        user.setLongTermGoal(100);
        user.addActivity("running", 60, "2024-10-1");
        user.checkIsMyGoalAchieved();
        assertTrue(user.getIsGoalAchieved());
    }

    @Test
    public void testCheckIsMyGoalAchievdIfFalse() {
        user.setLongTermGoal(2000);
        user.addActivity("running", 60, "2024-10-1");
        user.checkIsMyGoalAchieved();
        assertFalse(user.getIsGoalAchieved());
    }


    @Test
    public void testCheckIsMyGoalAchievdWhenGoalEqualsToTotalCaloriesBurned() {
        user.setLongTermGoal(660);
        user.addActivity("running", 60, "2024-10-1");
        user.checkIsMyGoalAchieved();
        assertEquals(true, user.getIsGoalAchieved());
    }

    @Test
    public void testGetTotalCaloriesBurned() {
        user.addActivity("running", 60, "2024-10-1");
        user.calculateTotalCaloriesBurned();
        assertEquals(660, user.getTotalCaloriesBurned());
    }
    
}
