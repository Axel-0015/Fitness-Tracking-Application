package model;

import org.json.JSONObject;

import persistence.Writable;

//citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

//This class represents a fitness activity, 
//such as gym workouts or sports. 
//It stores activity data like duration and calories burned.
public class Activity implements Writable {

    private String activityName;//This is the activity's name.
    private int duration; // time spend on the activity in minutes
    private int caloriesBurnedPerMin;//how much calories is burned per minute during the activity 
    private int caloriesBurned;//how much calories is burned during the activity
    private String date;

    //REQUIRE: duration and caloriesBurnedPerMin must be positive integers
    //EFFECTS: construct an activity with the given value, 
    //and call the setCaloriesBurnedPerMIn method to set the caloriesburnedPerMin
    public Activity(String activityName, int duration, String date) {
        this.activityName = activityName;
        this.duration = duration;   
        this.date = date;
        setCaloriesBurnedPerMin(activityName);
        this.calculateCaloriesBurned();
    }

    //MODIFIES: this
    //EFFECTS: set the caloriesBurnedPerMIn according to the activity name
    private void setCaloriesBurnedPerMin(String activityName) {
        if (activityName.equalsIgnoreCase("running")) {
            this.caloriesBurnedPerMin = 11;
        } else if (activityName.equalsIgnoreCase("badminton")) {
            this.caloriesBurnedPerMin = 5;
        } else if (activityName.equalsIgnoreCase("cycling")) {
            this.caloriesBurnedPerMin = 9;
        } else if (activityName.equalsIgnoreCase("basketball")) {
            this.caloriesBurnedPerMin = 12;
        } else if (activityName.equalsIgnoreCase("swimming")) {
            this.caloriesBurnedPerMin = 7;
        } else {
            System.out.println("Activity not found. Please try again.");
        }
    }

    //MODIFIES: this
    //EFFECTS: set the caloriesBurned with duration times caloriesBurnedPerMin and return it;
    public void calculateCaloriesBurned() {
        this.caloriesBurned = this.duration * this.caloriesBurnedPerMin;
    
    }

    public int getCaloriesBurnedPerMin() {
        return this.caloriesBurnedPerMin;
    }

    public int getCaloriesBurned() {
        return this.caloriesBurned;
    }
    
    public String getActivityName() {
        return this.activityName;
    } 

    public int getDuration() {
        return this.duration;
    }

    public String getDate() {
        return this.date;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("activityName", activityName);
        json.put("caloriesBurned", caloriesBurned);
        json.put("duration", duration);
        json.put("date", date);
        return json;
    }

}
