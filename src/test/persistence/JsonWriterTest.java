package persistence;

import model.Activity;
import model.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            User user = new User("Axel");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOEception was excepted");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyUser() {
        try {
            User user = new User("Axel");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyUser.json");
            writer.open();
            writer.write(user);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyUser.json");
            user = reader.read();
            assertEquals("Axel", user.getName());
            assertEquals(0, user.getActivities().size());
            assertEquals(0, user.getTotalCaloriesBurned());
            assertEquals(false, user.getIsGoalAchieved());
            assertEquals(0, user.getLongTermGoal());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralUser() {
        try {
            User user = new User("Axel");
            user.addActivity("running", 60, "2024-10-19");
            user.addActivity("swimming", 60, "2024-10-19");
            user.setLongTermGoal(1000);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralUser.json");
            writer.open();
            writer.write(user);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralUser.json");
            user = reader.read();
            assertEquals("Axel", user.getName());
            List<Activity> list = user.getActivities();
            assertEquals(2, list.size());
            checkActivity(list.get(0), "running", 60, 11, 660, "2024-10-19");
            checkActivity(list.get(1), "swimming", 60, 7, 420, "2024-10-19");
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
