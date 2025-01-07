package org.example.GUI;

import org.example.dao.CitizenDao;
import org.example.dao.CitizenDaoImpl;
import org.example.model.Citizen;
import org.example.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;

import static org.example.utils.Utils.createLabeledField;


public class CreateFrame extends JFrame {
    private final JTextField firstNameField, lastNameField, husbandNameField, occupationField, stateField, addressField;
    private final JComboBox<Integer> yearCombo, monthCombo, dayCombo;
    private final JComboBox<Citizen.Gender> genderCombo;
    private final JComboBox<Citizen.Religion> religionCombo;
    private final JComboBox<Citizen.MaritalState> maritalStateCombo;
    private final JPanel husbandNamePanel;

    private final JLabel firstNameErrorLabel, lastNameErrorLabel, occupationErrorLabel, husbandNameErrorLabel;

    public CreateFrame(mainFrame mainFrame) {
        // Set frame properties
        setTitle("Data Submission Form");
        setSize(500, 700);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));   // ensure each field appears below the previous one
        setLocation(mainFrame.getLocation());               // to maintain the location of each Frame

        // set first and last names fields
        add(createLabeledField("First Name:", firstNameField = new JTextField(20), firstNameErrorLabel = new JLabel()));
        add(createLabeledField("Last Name:", lastNameField = new JTextField(20), lastNameErrorLabel = new JLabel()));

        // set Birthdate fields
        JPanel birthdatePanel = new JPanel();
        birthdatePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        birthdatePanel.add(new JLabel("Birthdate:"));
        yearCombo = new JComboBox<>(Utils.getYearRange());
        monthCombo = new JComboBox<>(Utils.getMonthRange());
        dayCombo = new JComboBox<>(Utils.getDayRange());
        yearCombo.setSelectedItem(null);
        monthCombo.setSelectedItem(null);
        dayCombo.setSelectedItem(null);
        birthdatePanel.add(yearCombo);
        birthdatePanel.add(monthCombo);
        birthdatePanel.add(dayCombo);
        add(birthdatePanel);

        // set Gender field
        JPanel genderPanel = createLabeledField("Gender:", genderCombo = new JComboBox<>(Citizen.Gender.values()));
        add(genderPanel);
        genderCombo.setSelectedItem(null); // Default to no selection

        // set Religion field
        add(createLabeledField("Religion:", religionCombo = new JComboBox<>(Citizen.Religion.values())));
        religionCombo.setSelectedItem(null);

        // set Marital State field
        add(createLabeledField("Marital State:", maritalStateCombo = new JComboBox<>()));
        maritalStateCombo.setSelectedItem(null);

        // set Husband Name field (hidden by default)
        husbandNamePanel = createLabeledField("Husband Name:", husbandNameField = new JTextField(20), husbandNameErrorLabel = new JLabel());
        husbandNamePanel.setVisible(false); // Initially hidden
        add(husbandNamePanel);
        // husband Name will display if the user is Female and married
        genderCombo.addActionListener(e -> Utils.toggleMaritalStateAndHusbandField(genderCombo, maritalStateCombo, husbandNamePanel));

        // set Occupation field
        add(createLabeledField("Occupation:", occupationField = new JTextField(20), occupationErrorLabel = new JLabel()));

        // set State field
        add(createLabeledField("State:", stateField = new JTextField(20)));

        // set Address field
        add(createLabeledField("Address:", addressField = new JTextField(20)));

        // create Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // set Submit button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(this::handleSubmit);
        buttonPanel.add(submitButton);

        // set Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            Point location = this.getLocation();
            this.dispose();
            mainFrame reopenedMainFrame = new mainFrame();
            reopenedMainFrame.setLocation(location.x, location.y);
        });
        buttonPanel.add(backButton);

        add(buttonPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    // close the whole app. if the user click on X button
        setVisible(true);
    }


    private void handleSubmit(ActionEvent e) {
        try {
            // Validate required fields
            if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "First Name and Last Name are required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Birthdate validation
            Integer year = (Integer) yearCombo.getSelectedItem();
            Integer month = (Integer) monthCombo.getSelectedItem();
            Integer day = (Integer) dayCombo.getSelectedItem();

            if (year == null || month == null || day == null) {
                JOptionPane.showMessageDialog(this, "Please select your birthdate.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Gender,Religion and Marital State validation
            if (genderCombo.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Please select a gender.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (religionCombo.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Please select a Religion.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (maritalStateCombo.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Please select a marital state.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Check if husband name is visible and not empty
            if (husbandNamePanel.isVisible() && husbandNameField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Husband Name is required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (occupationField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "occupation is required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (stateField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "State is required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (addressField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Address is required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if the age is 15 or older
            boolean errorState = Utils.isAnyErrorMessageDisplayed(firstNameErrorLabel, lastNameErrorLabel, occupationErrorLabel, husbandNameErrorLabel);
            if (errorState) {
                JOptionPane.showMessageDialog(this, "Please Enter valid data.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            LocalDate birthdate = LocalDate.of(year, month, day);
            int age = LocalDate.now().getYear() - birthdate.getYear();
            if (age < 15) {
                JOptionPane.showMessageDialog(this, "Age must be 15 or older.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create Citizen object and set values
            CitizenDao citizenDao = new CitizenDaoImpl();
            Citizen newPerson = Utils.setCitizen(firstNameField, lastNameField, birthdate, genderCombo, religionCombo, maritalStateCombo, husbandNameField, occupationField, stateField, addressField);

            // Insert the new person into the database
            citizenDao.create(newPerson);

            // Display success message
            JOptionPane.showMessageDialog(this, "Person Created successfully. your ID: "+newPerson.getId(), "Success", JOptionPane.INFORMATION_MESSAGE);

            // Return to the mainFrame
            Point location = this.getLocation(); // Get the current window location
            this.dispose(); // Close the UpdateFrame
            mainFrame reopenedMainFrame = new mainFrame(); // Create a new instance of mainFrame
            reopenedMainFrame.setLocation(location.x, location.y); // Restore the window location

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error occurred while submitting the form: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
