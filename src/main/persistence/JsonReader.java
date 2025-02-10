package persistence;

import model.User;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

//citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

//Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    //EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    //EFFECTS: read user from file and returns it:
    //throws IOException if an error occurs reading data from file
    public User read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseUser(jsonObject);
    }

    //EFFECTS: read source file as string adn returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    //EFFECTS: parse user from JSON object and returns it
    private User parseUser(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int longTermGoal = jsonObject.getInt("longTermGoal");
        User user = new User(name);
        addActivities(user, jsonObject);
        user.setLongTermGoal(longTermGoal);
        user.calculateTotalCaloriesBurned();
        return user;
    }

    //MODIFIES: user
    //EFFECTS: parses activities form JSON object and adds them to user
    private void addActivities(User user, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("activities");
        for (Object json : jsonArray) {
            JSONObject nextActivity = (JSONObject) json;
            addActivity(user, nextActivity);
        }
    }

    //MODIFIES: user
    //EFFECTS: parse activity from JSON object and adds it to user
    private void addActivity(User user, JSONObject jsonObject) {
        String activityName = jsonObject.getString("activityName");
        int duration = jsonObject.getInt("duration");
        String date = jsonObject.getString("date");
        user.addActivity(activityName, duration, date);
    }


}
