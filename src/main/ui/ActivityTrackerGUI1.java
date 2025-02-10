package ui;

import model.Activity;
import model.User;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class ActivityTrackerGUI1 extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/user.json";
    private User user;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    // UI components
    private JTextField nameField;
    private JTextArea displayArea;
    private JTextField durationField;
    private JTextField dateField;
    private JTextField goalField;

    // Buttons
    private JButton addActivityButton;
    private JButton setGoalButton;
    private JButton viewActivitiesButton;
    private JButton viewCaloriesButton;
    private JButton viewDurationButton;
    private JButton viewActivitiesByDateButton;
    private JButton checkGoalButton;
    private JButton clearActivitiesButton;
    private JButton saveButton;
    private JButton loadButton;

    public ActivityTrackerGUI1() {
        // Prompt for user's name
        String userName = JOptionPane.showInputDialog(null,
                "Please enter your name:", "Welcome", JOptionPane.PLAIN_MESSAGE);
        if (userName == null || userName.trim().isEmpty()) {
            System.exit(1); // Exit if no name is provided
        }
        user = new User(userName);

        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);

        setTitle("Fitness Tracker Application - " + user.getName());
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create UI components
        displayArea = new JTextArea();
        displayArea.setEditable(false);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        nameField = new JTextField();
        durationField = new JTextField();
        dateField = new JTextField();
        goalField = new JTextField();

        // Initialize buttons and set ActionCommand
        addActivityButton = new JButton("Add Activity");
        addActivityButton.setActionCommand("ADD_ACTIVITY");
        addActivityButton.addActionListener(this);

        setGoalButton = new JButton("Set Long Term Goal");
        setGoalButton.setActionCommand("SET_GOAL");
        setGoalButton.addActionListener(this);

        viewActivitiesButton = new JButton("View All Activities");
        viewActivitiesButton.setActionCommand("VIEW_ACTIVITIES");
        viewActivitiesButton.addActionListener(this);

        viewCaloriesButton = new JButton("View Calories Burned");
        viewCaloriesButton.setActionCommand("VIEW_CALORIES");
        viewCaloriesButton.addActionListener(this);

        viewDurationButton = new JButton("View Duration");
        viewDurationButton.setActionCommand("VIEW_DURATION");
        viewDurationButton.addActionListener(this);

        viewActivitiesByDateButton = new JButton("View Activities of a Day");
        viewActivitiesByDateButton.setActionCommand("VIEW_BY_DATE");
        viewActivitiesByDateButton.addActionListener(this);

        checkGoalButton = new JButton("Check If Goal is Achieved");
        checkGoalButton.setActionCommand("CHECK_GOAL");
        checkGoalButton.addActionListener(this);

        clearActivitiesButton = new JButton("Clear All Activities");
        clearActivitiesButton.setActionCommand("CLEAR_ACTIVITIES");
        clearActivitiesButton.addActionListener(this);

        saveButton = new JButton("Save Data");
        saveButton.setActionCommand("SAVE_DATA");
        saveButton.addActionListener(this);

        loadButton = new JButton("Load Data");
        loadButton.setActionCommand("LOAD_DATA");
        loadButton.addActionListener(this);

        // Organize components into the window
        inputPanel.add(new JLabel("Activity Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Duration (min):"));
        inputPanel.add(durationField);
        inputPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        inputPanel.add(dateField);
        inputPanel.add(new JLabel("Goal (calories):"));
        inputPanel.add(goalField);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 5, 10, 10));
        buttonPanel.add(addActivityButton);
        buttonPanel.add(setGoalButton);
        buttonPanel.add(viewActivitiesButton);
        buttonPanel.add(viewCaloriesButton);
        buttonPanel.add(viewDurationButton);
        buttonPanel.add(viewActivitiesByDateButton);
        buttonPanel.add(checkGoalButton);
        buttonPanel.add(clearActivitiesButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "ADD_ACTIVITY":
                addActivity();
                break;
            case "SET_GOAL":
                setGoal();
                break;
            case "VIEW_ACTIVITIES":
                viewActivities();
                break;
            case "VIEW_CALORIES":
                viewCalories();
                break;
            case "VIEW_DURATION":
                viewDuration();
                break;
            case "VIEW_BY_DATE":
                viewActivitiesByDate();
                break;
            case "CHECK_GOAL":
                checkGoal();
                break;
            case "CLEAR_ACTIVITIES":
                clearActivities();
                break;
            case "SAVE_DATA":
                saveData();
                break;
            case "LOAD_DATA":
                loadData();
                break;
        }
    }

    private void addActivity() {
        String name = nameField.getText();
        int duration = Integer.parseInt(durationField.getText());
        String date = dateField.getText();

        user.addActivity(name, duration, date);
        displayArea.append("Added activity: " + name + " for " + duration + " minutes on " + date + "\n");

        nameField.setText("");
        durationField.setText("");
        dateField.setText("");
    }

    //MODIFIES: this
    //EFFECTS: set the long time goal with the user's input
    private void setGoal() {
        int goal = Integer.parseInt(goalField.getText());
        user.setLongTermGoal(goal);
        displayArea.append("Long term goal set to: " + goal + " calories\n");
        goalField.setText("");
    }

    //EFFFECTS: view all the activities the user has done
    private void viewActivities() {
        List<Activity> activities = user.getActivities();
        if (activities.isEmpty()) {
            displayArea.append("No activities recorded.\n");
        } else {
            displayArea.append("Your activities:\n");
            for (Activity a : activities) {
                displayArea.append(a.getActivityName() + " for " + a.getDuration() + " minutes on " + a.getDate() + "\n");
            }
        }
    }

    //EFFECTS: view total calories burned 
    private void viewCalories() {
        int calories = user.getTotalCaloriesBurned();
        displayArea.append("Total calories burned: " + calories + " calories\n");
    }

    //EFFECTS: view total duration burned 
    private void viewDuration() {
        int totalDuration = user.calculateTotalDuration();
        displayArea.append("Total duration of all activities: " + totalDuration + " minutes\n");
    }

     //EFFECTS: view all activities done on a certain day
     private void viewActivitiesByDate() {
        String date = JOptionPane.showInputDialog(null, "Enter date (YYYY-MM-DD):");
        List<Activity> activities = user.getActivitiesByDate(date);
        displayArea.append("Activities on " + date + ":\n");
        if (activities.isEmpty()) {
            displayArea.append("No activities recorded on this date.\n");
        } else {
            for (Activity a : activities) {
                displayArea.append(a.getActivityName() + " - " + a.getDuration() + " mins\n");
            }
        }
    }

     //EFFECTS: compare long term goal and user's total calories burned to check if the goal is achieved
     private void checkGoal() {
        user.checkIsMyGoalAchieved();
        if (user.getIsGoalAchieved()) {
            displayArea.append("Congratulations! You have achieved your goal!\n");
            displayGoalAchievementImage();  // Display the image
        } else {
            displayArea.append("Keep going! You haven't achieved your goal yet.\n");
        }
    }

     private void displayGoalAchievementImage() {
        // Create a new JFrame for the image
        JFrame imageFrame = new JFrame("Congratulations! Goal Achieved!");
        imageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        imageFrame.setSize(600, 400);
        imageFrame.setLayout(new BorderLayout());
    
        // Load the image
        ImageIcon imageIcon = new ImageIcon("/Users/axelli/Desktop/images.jpeg"); // Replace with the correct path
        JLabel imageLabel = new JLabel(imageIcon);
    
        // Add the image to the frame
        imageFrame.add(imageLabel, BorderLayout.CENTER);
    
        // Add a congratulatory message
        JLabel messageLabel = new JLabel("Great job! You achieved your goal!", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Serif", Font.BOLD, 20));
        imageFrame.add(messageLabel, BorderLayout.SOUTH);
    
        // Center the frame and make it visible
        imageFrame.setLocationRelativeTo(null);
        imageFrame.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: clear user's activities
    private void clearActivities() {
        user.clearMyActivities();
        displayArea.append("All activities cleared.\n");
    }

    //EFFECTS: saves this user to file
    private void saveData() {
        try {
            jsonWriter.open();
            jsonWriter.write(user);
            jsonWriter.close();
            displayArea.append("Data saved to " + JSON_STORE + "\n");
        } catch (FileNotFoundException ex) {
            displayArea.append("Unable to write to file: " + JSON_STORE + "\n");
        }
    }

    //MODIFIES: this
    //EFFECTS: load user from file
    private void loadData() {
        try {
            user = jsonReader.read();
            displayArea.append("Data loaded from " + JSON_STORE + "\n");
        } catch (IOException ex) {
            displayArea.append("Unable to read from file: " + JSON_STORE + "\n");
        }
    }
}
