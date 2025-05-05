package main.ui;

import main.model.Activity;
import main.model.EventLog;
import main.model.Event;
import main.model.User;
import main.persistence.JsonReader;
import main.persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

//This is the Fitness Tracker Application
public class ActivityTrackerGUI extends JFrame implements ActionListener {
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

    // The constructor runs the Fitness Tracker Application
    public ActivityTrackerGUI() {
        String userName = JOptionPane.showInputDialog(null,
                "Please enter your name:", "Fitness Tracker", JOptionPane.PLAIN_MESSAGE);
        if (userName == null || userName.trim().isEmpty()) {
            System.exit(1);
        }
        user = new User(userName);

        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);

        setTitle("Fitness Tracker Application - " + user.getName());
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setupInputPanel();
        setupDisplayArea();
        setupButtonPanel();


        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                printEventLog();
                System.exit(0);
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // EFFECTS: prints all logged events to the console
    private void printEventLog() {
        for (Event next : EventLog.getInstance()) {
            System.out.println(next.toString() + "\n\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: sets up the input panel for user inputs
    private void setupInputPanel() {
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        nameField = new JTextField();
        durationField = new JTextField();
        dateField = new JTextField();
        goalField = new JTextField();

        inputPanel.add(new JLabel("Activity Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Duration (min):"));
        inputPanel.add(durationField);
        inputPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        inputPanel.add(dateField);
        inputPanel.add(new JLabel("Goal (calories):"));
        inputPanel.add(goalField);

        add(inputPanel, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: sets up the display area for activities
    private void setupDisplayArea() {
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: sets up the button panel with all buttons and action listeners
    private void setupButtonPanel() {
        addActivityButton = createButton("Add Activity", "ADD_ACTIVITY");
        setGoalButton = createButton("Set Long Term Goal", "SET_GOAL");
        viewActivitiesButton = createButton("View All Activities", "VIEW_ACTIVITIES");
        viewCaloriesButton = createButton("View Calories Burned", "VIEW_CALORIES");
        viewDurationButton = createButton("View Duration", "VIEW_DURATION");
        viewActivitiesByDateButton = createButton("View Activities of a Day", "VIEW_BY_DATE");
        checkGoalButton = createButton("Check If Goal is Achieved", "CHECK_GOAL");
        clearActivitiesButton = createButton("Clear All Activities", "CLEAR_ACTIVITIES");
        saveButton = createButton("Save Data", "SAVE_DATA");
        loadButton = createButton("Load Data", "LOAD_DATA");

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

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: creates a button, sets its action command, and adds the action
    // listener
    private JButton createButton(String text, String actionCommand) {
        JButton button = new JButton(text);
        button.setActionCommand(actionCommand);
        button.addActionListener(this);
        return button;
    }

    // MODIFIES:this
    // EFFECTS:link the action to be performed to the button
    @SuppressWarnings("methodlength")
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

    // MODIFIES: this
    // EFFECTS: add the user's activity to the trakcer
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

    // MODIFIES: this
    // EFFECTS: set the long time goal with the user's input
    private void setGoal() {
        int goal = Integer.parseInt(goalField.getText());
        user.setLongTermGoal(goal);
        displayArea.append("Long term goal set to: " + goal + " calories\n");
        goalField.setText("");
    }

    // EFFFECTS: view all the activities the user has done
    private void viewActivities() {
        List<Activity> activities = user.getActivities();
        if (activities.isEmpty()) {
            displayArea.append("No activities recorded.\n");
        } else {
            displayArea.append("Your activities:\n");
            for (Activity a : activities) {
                displayArea.append(a.getActivityName() + " for "
                        + a.getDuration() + " minutes on " + a.getDate() + "\n");
            }
        }
    }

    // EFFECTS: view total calories burned
    private void viewCalories() {
        int calories = user.getTotalCaloriesBurned();
        displayArea.append("Total calories burned: " + calories + " calories\n");
    }

    // EFFECTS: view total duration burned
    private void viewDuration() {
        int totalDuration = user.calculateTotalDuration();
        displayArea.append("Total duration of all activities: " + totalDuration + " minutes\n");
    }

    // EFFECTS: view all activities done on a certain day
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

    // EFFECTS: compare long term goal and user's total calories burned to check if
    // the goal is achieved
    private void checkGoal() {
        user.checkIsMyGoalAchieved();
        if (user.getIsGoalAchieved()) {
            displayArea.append("Congratulations! You have achieved your goal!\n");
            displayGoalAchievementImage();
        } else {
            displayArea.append("Keep going! You haven't achieved your goal yet.\n");
        }
    }

    private void displayGoalAchievementImage() {
        JFrame imageFrame = new JFrame("Congratulations! Goal Achieved!");
        imageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        imageFrame.setSize(600, 400);
        imageFrame.setLayout(new BorderLayout());

        ImageIcon imageIcon = new ImageIcon("./src/main/ui/images.jpeg"); // Replace with the correct path
        JLabel imageLabel = new JLabel(imageIcon);

        imageFrame.add(imageLabel, BorderLayout.CENTER);

        JLabel messageLabel = new JLabel("Great job! You achieved your goal!", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Serif", Font.BOLD, 20));
        imageFrame.add(messageLabel, BorderLayout.SOUTH);

        imageFrame.setLocationRelativeTo(null);
        imageFrame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: clear user's activities
    private void clearActivities() {
        user.clearMyActivities();
        displayArea.append("All activities cleared.\n");
    }

    // EFFECTS: saves this user to file
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

    // MODIFIES: this
    // EFFECTS: load user from file
    private void loadData() {
        try {
            user = jsonReader.read();
            displayArea.append("Data loaded from " + JSON_STORE + "\n");
        } catch (IOException ex) {
            displayArea.append("Unable to read from file: " + JSON_STORE + "\n");
        }
    }

}
