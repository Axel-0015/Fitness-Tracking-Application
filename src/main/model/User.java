package main.model;

import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

import main.persistence.Writable;

//citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

//This class represents the user of the fitness tracking application. 
//It contains user's information and has functions that allows the user to use the app.
public class User implements Writable {

    private String name;// This is the user's name
    private int longTermGoal;// a long term goal for the user
    private List<Activity> activities;// an activities list to store all the activities
    private int totalCaloriesBurned;// stores the sum of calories burned
    private boolean isGoalAchieved;// store a boolean value to respresent is the user's goal is achieved

    // REQUIRES: age and long term goal has to be postive numbers
    // EFFECTS: construct a user with the given name, initialize an arraylist to
    // store all the activities,
    // set the total calories burned to 0 and isGoalAchieved to false;
    public User(String name) {
        this.name = name;
        this.activities = new ArrayList<>();
        this.totalCaloriesBurned = 0;
        this.longTermGoal = 0;
        this.isGoalAchieved = false;
    }

    // MODIFIES: this
    // EFFECTS: add the activity to the activities list and update the total
    // calories burned
    public void addActivity(String activityName, int duration, String date) {
        Activity activity = new Activity(activityName, duration, date);
        this.activities.add(activity);
        this.totalCaloriesBurned = calculateTotalCaloriesBurned();
        EventLog.getInstance()
                .logEvent(new Event("User added Activity: " + activityName + " for " + duration + " on " + date));
    }

    // MODIFIES: this
    // EFFECTS: set the long term goal to the given amount
    public void setLongTermGoal(int amount) {
        this.longTermGoal = amount;
        EventLog.getInstance().logEvent(new Event("User set goal to " + amount));
    }

    // EFFECTS: calculate the sum of calories burned by a certain activity
    public int calculateCaloriesBurnedByActivity(String activityName) {
        int count = 0;
        for (Activity a : activities) {
            if (a.getActivityName().equalsIgnoreCase(activityName)) {
                a.calculateCaloriesBurned();
                count += a.getCaloriesBurned();
            }
        }
        return count;
    }

    // EFFECTS: calculate the sum of calories burned while doing all the activities
    public int calculateTotalCaloriesBurned() {
        int count = 0;
        for (Activity a : activities) {
            a.calculateCaloriesBurned();
            count += a.getCaloriesBurned();
        }
        this.totalCaloriesBurned = count;
        return count;
    }

    // EFFECTS: calculate and return the total duration of all activities
    public int calculateTotalDuration() {
        int totalDuration = 0;
        for (Activity a : activities) {
            totalDuration += a.getDuration();
        }
        return totalDuration;
    }

    // EFFECTS: calculate and return the duration for a certain activity
    public int calculateDurationByActivity(String activityName) {
        int totalDuration = 0;
        for (Activity a : activities) {
            if (a.getActivityName().equalsIgnoreCase(activityName)) {
                totalDuration += a.getDuration();
            }
        }
        return totalDuration;
    }

    // EFFECTS: return a list of all activities performed on the given date
    public List<Activity> getActivitiesByDate(String date) {
        List<Activity> activitiesOnCertainDate = new ArrayList<>();
        for (Activity activity : this.activities) {
            if (activity.getDate().equals(date)) {
                activitiesOnCertainDate.add(activity);
            }
        }
        EventLog.getInstance().logEvent(new Event("User checks activity on " + date));
        return activitiesOnCertainDate;
    }

    // MODIFIES; this
    // EFFECTS: clear user's activities list
    public void clearMyActivities() {
        this.activities = new ArrayList<>();
        this.totalCaloriesBurned = 0;
        this.isGoalAchieved = false;
        EventLog.getInstance().logEvent(new Event("User clears all the activities"));
    }

    // MODIFIES: this
    // EFFECTS: compare user's goal with user's totalCaloriesBurned, if goal is
    // achieved set isGoalAchieved to true;
    public void checkIsMyGoalAchieved() {
        if (this.calculateTotalCaloriesBurned() >= this.getLongTermGoal()) {
            this.isGoalAchieved = true;
        }
        EventLog.getInstance().logEvent(new Event("User checks if the goal is achieved"));
    }

    // EFFECTS: returns the list of all activities
    public List<Activity> getActivities() {
        EventLog.getInstance().logEvent(new Event("User checks all the activities"));
        return new ArrayList<>(this.activities);
    }

    public int getTotalCaloriesBurned() {
        return this.totalCaloriesBurned;
    }

    public boolean getIsGoalAchieved() {
        return this.isGoalAchieved;
    }

    public String getName() {
        return this.name;
    }

    public int getLongTermGoal() {
        return this.longTermGoal;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("activities", activitiesToJson());
        json.put("longTermGoal", longTermGoal);
        json.put("totalCaloriesBurned", totalCaloriesBurned);
        json.put("isGoalAchieved", isGoalAchieved);
        return json;
    }

    // EFFECTS: returns acitivities done by this user as a JSON array
    private JSONArray activitiesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Activity a : activities) {
            jsonArray.put(a.toJson());
        }

        return jsonArray;
    }

}
