package org.example.utils;

import org.example.model.Citizen;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class Utils {
    public static boolean isOnlyLetters(String str) {
        char c;
        if (str == null || str.isEmpty()) {
            return false; // Null or empty strings are not valid
        }
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (!Character.isLetter(c)) { // Check if character is not a letter
                return false; // Return false if a non-letter character is found
            }
        }
        return true; // All characters are letters
    }

    public static boolean isOnlyNums(String str) {
        if (str == null || str.isEmpty()) {
            return false; // Null or empty strings are not numeric
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false; // If a character is not a digit, return false
            }
        }
        return true; // All characters are digits
    }

    public static JPanel createLabeledField(String labelText, JComponent field) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(labelText));
        panel.add(field);
        return panel;
    }

    public static JPanel createLabeledField(String labelText, JTextField field, JLabel errorLabel) {
        JPanel panel = createLabeledField(labelText, field);

        // Customize the error label
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);
        panel.add(errorLabel);

        // Add a key listener for validation
        field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                String text = field.getText();
                if (!Utils.isOnlyLetters(text)) { // If the text contains any digits
                    errorLabel.setText("Invalid data entry!");
                    errorLabel.setVisible(true);
                } else {
                    errorLabel.setVisible(false);
                }
            }
        });

        return panel;
    }

    public static void toggleMaritalStateAndHusbandField(JComboBox<Citizen.Gender> genderCombo, JComboBox<Citizen.MaritalState> maritalStateCombo, JPanel husbandNamePanel) {
        // Get selected gender
        Citizen.Gender selectedGender = (Citizen.Gender) genderCombo.getSelectedItem();

        // Clear and reset marital state combo box
        maritalStateCombo.removeAllItems();
        husbandNamePanel.setVisible(false); // Initially hide the husband name field

        // Populate marital state options based on selected gender
        if (selectedGender == Citizen.Gender.FEMALE) {
            maritalStateCombo.addItem(Citizen.MaritalState.SINGLE);
            maritalStateCombo.addItem(Citizen.MaritalState.MARRIED);
            maritalStateCombo.addItem(Citizen.MaritalState.DIVORCED);
            maritalStateCombo.addItem(Citizen.MaritalState.WIDOWED);
        } else if (selectedGender == Citizen.Gender.MALE) {
            maritalStateCombo.addItem(Citizen.MaritalState.SINGLE);
            maritalStateCombo.addItem(Citizen.MaritalState.MARRIED);
        }

        maritalStateCombo.setSelectedItem(null); // Default marital state to empty

        // Add an ActionListener for maritalStateCombo
        maritalStateCombo.addActionListener(e -> {
            // Get selected marital state
            Citizen.MaritalState selectedMaritalState = (Citizen.MaritalState) maritalStateCombo.getSelectedItem();
            Citizen.Gender newSelectedGender = (Citizen.Gender) genderCombo.getSelectedItem();
            // Show husband name field only if gender is FEMALE and marital state is MARRIED
            husbandNamePanel.setVisible(newSelectedGender == Citizen.Gender.FEMALE && selectedMaritalState == Citizen.MaritalState.MARRIED);
        });
    }

    public static boolean isAnyErrorMessageDisplayed(JLabel errorLabel1, JLabel errorLabel2, JLabel errorLabel3, JLabel errorLabel4) {
        // Check if any of the five error labels is visible
        return errorLabel1.isVisible() || errorLabel2.isVisible() || errorLabel3.isVisible() || errorLabel4.isVisible();
    }

    public static Integer[] getYearRange() {
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        Integer[] years = new Integer[101];
        for (int i = 0; i <= 100; i++) {
            years[i] = currentYear - i;
        }
        return years;
    }

    public static Integer[] getMonthRange() {
        Integer[] months = new Integer[12];
        for (int i = 1; i <= 12; i++) {
            months[i - 1] = i;
        }
        return months;
    }

    public static Integer[] getDayRange() {
        Integer[] days = new Integer[31];
        for (int i = 1; i <= 31; i++) {
            days[i - 1] = i;
        }
        return days;
    }

    public static Citizen setCitizen(JTextField firstNameField, JTextField lastNameField, LocalDate birthdate, JComboBox<Citizen.Gender> genderCombo, JComboBox<Citizen.Religion> religionCombo, JComboBox<Citizen.MaritalState> maritalStateCombo, JTextField husbandNameField, JTextField occupationField, JTextField stateField, JTextField addressField) {
        Citizen newPerson = new Citizen();
        newPerson.setFirstName(firstNameField.getText());
        newPerson.setLastName(lastNameField.getText());
        newPerson.setBirthdate(birthdate);
        newPerson.setId();
        newPerson.setGender((Citizen.Gender) genderCombo.getSelectedItem());
        newPerson.setReligion((Citizen.Religion) religionCombo.getSelectedItem());
        newPerson.setMaritalState((Citizen.MaritalState) maritalStateCombo.getSelectedItem());

        // If female and married, add husband name, otherwise set it as NULL
        String husbandName = newPerson.getGender() == Citizen.Gender.FEMALE ? husbandNameField.getText() : "NULL";
        newPerson.setHusbandName(husbandName);

        newPerson.setOccupation(occupationField.getText());
        newPerson.setState(stateField.getText());
        newPerson.setAddress(addressField.getText());
        return newPerson;
    }

}
