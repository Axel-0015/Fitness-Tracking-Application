package ui;

import model.*;
import java.util.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

//This is the Fitness Tracker Application
public class ActivityTracker {
    private static final String JSON_STORE = "./data/user.json";
    private User user;
    private Scanner in;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    //EFFECTS: runs the activity tracker application
    public ActivityTracker() {
        user = new User("Axel");
        in = new Scanner(System.in);
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        runActivityTracker();
    }


    //MODIFIES: this
    //EFFECTS: processor user's input
    private void runActivityTracker() {
        System.out.println("This is a Fitness Tracker Application");
        System.out.println("Please enter your name to start: ");
        String name = in.nextLine();
        user = new User(name);

        boolean keepGoing = true;

        while (keepGoing) {
            displayMenu();
            String command = in.nextLine();
            if (command.equalsIgnoreCase("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("Thank you for using my Fitness Tracker Application");
        System.out.println("GoodBye");
    }


    //EFFECTS: display a menue of options to the user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t1 -> Add a new activity");
        System.out.println("\t2 -> Set a long term goal");
        System.out.println("\t3 -> View all activities");
        System.out.println("\t4 -> View calories burned");
        System.out.println("\t5 -> View duration");
        System.out.println("\t6 -> view all acitivities of a certain day");
        System.out.println("\t7 -> Check if my goal is achieved");
        System.out.println("\t8 -> Clear all my previous activities");
        System.out.println("\ts -> Save your fitness history to file");
        System.out.println("\tl -> load your fitness history from file");
        System.out.println("\tq -> Quit");
    }


    //EFFECTS: process the user's input
    private void processCommand(String command) {
        if (command.equals("1")) {
            addActivity();
        } else if (command.equals("2")) {
            setLongTermGoal();
        } else if (command.equals("3")) {
            viewAllActivities();
        } else if (command.equals("4")) {
            viewCaloriesBurned();
        } else if (command.equals("5")) {
            viewDuration();
        } else if (command.equals("6")) {
            viewActivitiesByDate();
        } else if (command.equals("7")) {
            checkIfGoalAchieved();
        } else if (command.equals("8")) {
            clearAllActivities();
        } else if (command.equals("s")) {
            saveUser();
        } else if (command.equals("l")) {
            loadUser();
        } else {
            System.out.println("Wrong command. Please enter again.");
        }
    }
    


    //MODIFIES: this
    //EFFECTS: add the user's activity to the trakcer
    private void addActivity() {
        System.out.println("Enter activity name: ");
        String name = in.nextLine();
        System.out.println("Enter duration in minutes: ");
        int duration = Integer.parseInt(in.nextLine());
        System.out.println("Enter date (YYYY-MM-DD): ");
        String date = in.nextLine();

        user.addActivity(name, duration, date);
    }


    //MODIFIES: this
    //EFFECTS: set the long time goal with the user's input
    private void setLongTermGoal() {
        System.out.println("Enter long term goal (calories)");
        int goal = Integer.parseInt(in.nextLine());
        user.setLongTermGoal(goal);
        System.out.println("Long term goal is set to: " + goal);
    }


    //EFFFECTS: view all the activities the user has done
    private void viewAllActivities() {
        if (user.getActivities().size() == 0) {
            System.out.println("You haven't done any activities yet");
        } else {
            System.out.println("Your activities: ");
            List<Activity> list = user.getActivities();
            for (Activity a : list) {
                System.out.println(a.getActivityName() + " for " + a.getDuration() + " minutes on " + a.getDate());
            }
        }
    }

    //EFFECTS: if user enters one call viewtotalcaloresburned 
    //         or if the user enters two then call viewcaloriesbyactivity
    private void viewCaloriesBurned() {
        boolean control = false;
        do {
            System.out.println("Enter 1 to check total calories burned");
            System.out.println("Enter 2 to check calories burned by a certaion activity");
            int num = Integer.parseInt(in.nextLine());
            if (num == 1) {
                viewTotalCaloriesBurned();
            } else if (num == 2) {
                viewCaloriesByActivity();
            } else {
                System.out.println("Wrong input, try again");
                control = true;
            }
        } while (control);
    }


    //EFFECTS:view calories burned in total
    private void viewTotalCaloriesBurned() {
        int totalCalories = user.calculateTotalCaloriesBurned();
        System.out.println("Total calories burned: " + totalCalories);
    }


    //EFFECTS: view calories burned by a certain activity
    private void viewCaloriesByActivity() {
        System.out.print("Enter the activity name to view calories burned: ");
        String activityName = in.nextLine();
        int caloriesBurned = user.calculateCaloriesBurnedByActivity(activityName);
        System.out.println("Total calories burned for " + activityName + ": " + caloriesBurned);
    }

    //EFFECTS: if user enters one call viewtotalduration 
    //         or if the user enters two then call viewdurationbyactivity
    private void viewDuration() {
        boolean control = false;
        do {
            System.out.println("Enter 1 to check total duration for all activities");
            System.out.println("Enter 2 to check total duration for a certain activity");
            int num = Integer.parseInt(in.nextLine());
            if (num == 1) {
                viewTotalDuration();
            } else if (num == 2) {
                viewDurationByActivity();
            } else {
                System.out.println("Wrong input, try again");
                control = true;
            }
        } while (control);
    }


    //EFFECTS: view the total duration of all activities
    private void viewTotalDuration() {
        int totalDuration = user.calculateTotalDuration();
        System.out.println("Total duration of all activities: " + totalDuration + " minutes");
    }


    //EFFECTS: view duration by a certaion activity
    private void viewDurationByActivity() {
        System.out.println("Enter the name of the activity:");
        String activityName = in.nextLine();
        int totalDuration = user.calculateDurationByActivity(activityName);
        System.out.println("Total duration of " + activityName + ": " + totalDuration + " minutes");
    }
    

    //EFFECTS: view all activities done on a certain day
    private void viewActivitiesByDate() {
        System.out.println("Enter date (YYYY-MM-DD)");
        String date = in.nextLine();
        List<Activity> activitiesOnDate = user.getActivitiesByDate(date);

        if (activitiesOnDate.isEmpty()) {
            System.out.println("There is no activity on that date");
        } else {
            System.out.println("Activities on " + date + " :");
            for (Activity a : activitiesOnDate) {
                System.out.println(a.getActivityName() + " for " + a.getDuration() + " minutes.");
            }
        }
    }
    

    //EFFECTS: comapre long term goal and user's total calories burned to check if the goal is achieved
    private void checkIfGoalAchieved() {
        user.checkIsMyGoalAchieved();
        if (user.getIsGoalAchieved()) {
            System.out.println("You have achieved your goal!");
        } else {
            System.out.println("You haven't achieved your goal yet");
        }
    }


    //MODIFIES: this
    //EFFECTS: clear user's activities
    private void clearAllActivities() {
        user.clearMyActivities();
        System.out.println("All activities cleared.");
    }

    //EFFECTS: saves this user to file
    private void saveUser() {
        try {
            jsonWriter.open();
            jsonWriter.write(user);
            jsonWriter.close();
            System.out.println("Saved " + user.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    } 

    //MODIFIES: this
    //EFFECTS: load user from file
    private void loadUser() {
        try {
            user = jsonReader.read();
            System.out.println("Load " + user.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }






}
