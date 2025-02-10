package persistence;

import model.Activity;
import model.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonReaderTest extends JsonTest {
    
    @Test
    void testReaderNOnExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            User user = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testReaderEmptyUser() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyUser.json");
        try {
            User user = reader.read();
            assertEquals("Axel", user.getName());
            assertEquals(0, user.getActivities().size());
            assertEquals(0, user.getTotalCaloriesBurned());
            assertEquals(false, user.getIsGoalAchieved());
            assertEquals(0, user.getLongTermGoal());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralUser() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralUser.json");
        try {
            User user = reader.read();
            assertEquals("Axel", user.getName());
            List<Activity> list = user.getActivities();
            assertEquals(2, list.size());
            checkActivity(list.get(0), "running", 60, 11, 660, "2024-10-19");
            checkActivity(list.get(1), "swimming", 60, 7, 420, "2024-10-19");
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
