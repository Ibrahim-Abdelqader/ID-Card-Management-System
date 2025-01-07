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

public class UpdateFrame extends JFrame {
    // UI Components
    private final JTextField idField, firstNameField, lastNameField, husbandNameField, occupationField, stateField, addressField;
    private final JComboBox<Integer> yearCombo, monthCombo, dayCombo;
    private final JComboBox<Citizen.Gender> genderCombo;
    private final JComboBox<Citizen.Religion> religionCombo;
    private final JComboBox<Citizen.MaritalState> maritalStateCombo;
    private final JPanel husbandNamePanel;
    private final JButton updateButton;

    private final JLabel firstNameErrorLabel, lastNameErrorLabel, occupationErrorLabel,
            husbandNameErrorLabel;
    // DAO for database operations
    private final CitizenDao citizenDao;

    public UpdateFrame(mainFrame mainFrame) {
        // Initialize DAO
        citizenDao = new CitizenDaoImpl();

        // Set frame properties
        setTitle("Update Citizen Details");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Set the location of the new frame to match the main frame
        setLocation(mainFrame.getLocation());

        // Add ID field to check existence
        JPanel idPanel = createLabeledField("Enter ID:", idField = new JTextField(20));
        JButton checkIdButton = new JButton("Find ID");
        checkIdButton.addActionListener(this::handleCheckId);
        idPanel.add(checkIdButton);
        add(idPanel);

        // Add form fields (hidden initially until ID is verified)
        add(createLabeledField("First Name:", firstNameField = new JTextField(20), firstNameErrorLabel = new JLabel()));
        add(createLabeledField("Last Name:", lastNameField = new JTextField(20), lastNameErrorLabel = new JLabel()));

        // Birthdate fields
        JPanel birthdatePanel = new JPanel();
        birthdatePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        birthdatePanel.add(new JLabel("Birthdate:"));
        yearCombo = new JComboBox<>(Utils.getYearRange());
        monthCombo = new JComboBox<>(Utils.getMonthRange());
        dayCombo = new JComboBox<>(Utils.getDayRange());
        birthdatePanel.add(yearCombo);
        birthdatePanel.add(monthCombo);
        birthdatePanel.add(dayCombo);
        add(birthdatePanel);

        // Gender field with action listener
        JPanel genderPanel = createLabeledField("Gender:", genderCombo = new JComboBox<>(Citizen.Gender.values()));
        add(genderPanel);

        // Religion field
        add(createLabeledField("Religion:", religionCombo = new JComboBox<>(Citizen.Religion.values())));

        // Marital State field
        add(createLabeledField("Marital State:", maritalStateCombo = new JComboBox<>(Citizen.MaritalState.values())));

        // Husband Name field (hidden by default)
        husbandNamePanel = createLabeledField("Husband Name:", husbandNameField = new JTextField(20), husbandNameErrorLabel = new JLabel());
        husbandNamePanel.setVisible(false); // Initially hidden
        add(husbandNamePanel);
        genderCombo.addActionListener(e -> Utils.toggleMaritalStateAndHusbandField(genderCombo, maritalStateCombo, husbandNamePanel));

        // Occupation field
        add(createLabeledField("Occupation:", occupationField = new JTextField(20), occupationErrorLabel = new JLabel()));

        // State field
        add(createLabeledField("State:", stateField = new JTextField(20)));

        // Address field
        add(createLabeledField("Address:", addressField = new JTextField(20)));

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Update button
        updateButton = new JButton("Update");
        updateButton.addActionListener(this::handleUpdate);
        buttonPanel.add(updateButton);

        // Disable fields initially
        firstNameField.setEnabled(false);
        lastNameField.setEnabled(false);
        yearCombo.setEnabled(false);
        monthCombo.setEnabled(false);
        dayCombo.setEnabled(false);
        genderCombo.setEnabled(false);
        religionCombo.setEnabled(false);
        maritalStateCombo.setEnabled(false);
        husbandNameField.setEnabled(false);
        occupationField.setEnabled(false);
        stateField.setEnabled(false);
        addressField.setEnabled(false);
        updateButton.setEnabled(false);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            Point location = this.getLocation();
            this.dispose();
            mainFrame reopenedMainFrame = new mainFrame();
            reopenedMainFrame.setLocation(location.x, location.y);
        });
        buttonPanel.add(backButton);

        add(buttonPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }


    // Handle "Check ID" button click
    private void handleCheckId(ActionEvent e) {
        try {
            String id = idField.getText();
            if (citizenDao.isIdExist(id)) {
                Citizen citizen = citizenDao.findById(id);
                // Enable fields
                firstNameField.setEnabled(true);
                lastNameField.setEnabled(true);
                genderCombo.setEnabled(true);
                religionCombo.setEnabled(true);
                maritalStateCombo.setEnabled(true);
                husbandNameField.setEnabled(true);
                occupationField.setEnabled(true);
                stateField.setEnabled(true);
                addressField.setEnabled(true);

                populateFields(citizen);
                updateButton.setEnabled(true);
                JOptionPane.showMessageDialog(this, "ID found! You can now update the data.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (id.isEmpty())
                    JOptionPane.showMessageDialog(this, "Please Enter the ID", "Error", JOptionPane.ERROR_MESSAGE);
                else if (!Utils.isOnlyNums(id))
                    JOptionPane.showMessageDialog(this, "Please Enter valid ID.", "Error", JOptionPane.ERROR_MESSAGE);
                else
                    JOptionPane.showMessageDialog(this, "ID not found in the database.", "Error", JOptionPane.ERROR_MESSAGE);
                firstNameField.setEnabled(false);
                lastNameField.setEnabled(false);
                yearCombo.setEnabled(false);
                monthCombo.setEnabled(false);
                dayCombo.setEnabled(false);
                genderCombo.setEnabled(false);
                religionCombo.setEnabled(false);
                maritalStateCombo.setEnabled(false);
                husbandNameField.setEnabled(false);
                occupationField.setEnabled(false);
                stateField.setEnabled(false);
                addressField.setEnabled(false);
                updateButton.setEnabled(false);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "An Error occurred", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Populate form fields with citizen details
    private void populateFields(Citizen citizen) {
        firstNameField.setText(citizen.getFirstName());
        lastNameField.setText(citizen.getLastName());
        LocalDate birthdate = citizen.getBirthdate();
        yearCombo.setSelectedItem(birthdate.getYear());
        monthCombo.setSelectedItem(birthdate.getMonthValue());
        dayCombo.setSelectedItem(birthdate.getDayOfMonth());
        genderCombo.setSelectedItem(citizen.getGender());
        religionCombo.setSelectedItem(citizen.getReligion());
        maritalStateCombo.setSelectedItem(citizen.getMaritalState());
        husbandNameField.setText(citizen.getHusbandName());
        occupationField.setText(citizen.getOccupation());
        stateField.setText(citizen.getState());
        addressField.setText(citizen.getAddress());
    }

    // Handle "Update" button click for updating data
    private void handleUpdate(ActionEvent e) {
        try {

            // Validate required fields
            if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "First Name and Last Name are required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
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

            boolean errorState = Utils.isAnyErrorMessageDisplayed(firstNameErrorLabel, lastNameErrorLabel, occupationErrorLabel, husbandNameErrorLabel);
            if (errorState) {
                JOptionPane.showMessageDialog(this, "Please Enter valid data.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create and populate the Citizen object with updated data
            Citizen updatedCitizen = new Citizen();
            updatedCitizen.setId(idField.getText()); // Set the same ID
            updatedCitizen.setFirstName(firstNameField.getText());
            updatedCitizen.setLastName(lastNameField.getText());
            updatedCitizen.setBirthdate(LocalDate.of(
                    (Integer) yearCombo.getSelectedItem(),
                    (Integer) monthCombo.getSelectedItem(),
                    (Integer) dayCombo.getSelectedItem()));
            updatedCitizen.setGender((Citizen.Gender) genderCombo.getSelectedItem());
            updatedCitizen.setReligion((Citizen.Religion) religionCombo.getSelectedItem());
            updatedCitizen.setMaritalState((Citizen.MaritalState) maritalStateCombo.getSelectedItem());
            updatedCitizen.setHusbandName(husbandNameField.getText());
            updatedCitizen.setOccupation(occupationField.getText());
            updatedCitizen.setState(stateField.getText());
            updatedCitizen.setAddress(addressField.getText());

            // Perform the update operation
            citizenDao.update(updatedCitizen);

            // Show success message
            JOptionPane.showMessageDialog(this, "Update Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Return to the mainFrame
            Point location = this.getLocation(); // Get the current window location
            this.dispose(); // Close the UpdateFrame
            mainFrame reopenedMainFrame = new mainFrame(); // Create a new instance of mainFrame
            reopenedMainFrame.setLocation(location.x, location.y); // Restore the window location
        } catch (Exception ex) {
            // Show error message if an exception occurs
            JOptionPane.showMessageDialog(this, "An error occurred while updating: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


}
