package persistence;

import model.Activity;

import static org.junit.jupiter.api.Assertions.assertEquals;

//citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonTest {
    protected void checkActivity(Activity activity, String activityName, int duration,
            int caloriesBurnedPerMin, int caloriesBurned, String date) {
        assertEquals(activityName, activity.getActivityName());
        assertEquals(duration, activity.getDuration());
        assertEquals(caloriesBurnedPerMin, activity.getCaloriesBurnedPerMin());
        assertEquals(caloriesBurned, activity.getCaloriesBurned());
        assertEquals(date, activity.getDate());
    }
}
