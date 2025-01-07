package org.example.GUI;

import org.example.dao.CitizenDao;
import org.example.dao.CitizenDaoImpl;
import org.example.model.Citizen;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ShowAllDataFrame extends JFrame {
    private final CitizenDao citizenDao;
    private final DefaultTableModel tableModel;

    public ShowAllDataFrame(mainFrame mainFrame) {
        // Initialize DAO
        citizenDao = new CitizenDaoImpl();

        // Set frame properties
        setTitle("Show All Citizens");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set the location of the new frame to match the main frame
        setLocation(mainFrame.getLocation());

        // Create a JTable to display data
        tableModel = new DefaultTableModel(new String[]{
                "ID", "First Name", "Last Name", "Birth Date", "Age", "Gender", "Religion",
                "Marital Status", "Husband Name", "Occupation", "State", "Address",
                "Creating Date", "Expiration Date"}, 0);
        JTable dataTable = new JTable(tableModel);
        dataTable.setFillsViewportHeight(true);  // Makes the table fill the viewport height

        // Set custom column widths
        dataTable.getColumnModel().getColumn(0).setPreferredWidth(100);  // ID
        dataTable.getColumnModel().getColumn(1).setPreferredWidth(100); // First Name
        dataTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Last Name
        dataTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Birth Date
        dataTable.getColumnModel().getColumn(4).setPreferredWidth(50);  // Age
        dataTable.getColumnModel().getColumn(5).setPreferredWidth(70);  // Gender
        dataTable.getColumnModel().getColumn(6).setPreferredWidth(100); // Religion
        dataTable.getColumnModel().getColumn(7).setPreferredWidth(100); // Marital Status
        dataTable.getColumnModel().getColumn(8).setPreferredWidth(100); // Husband Name
        dataTable.getColumnModel().getColumn(9).setPreferredWidth(100); // Occupation
        dataTable.getColumnModel().getColumn(10).setPreferredWidth(100); // State
        dataTable.getColumnModel().getColumn(11).setPreferredWidth(150); // Address
        dataTable.getColumnModel().getColumn(12).setPreferredWidth(100); // Creating Date
        dataTable.getColumnModel().getColumn(13).setPreferredWidth(100); // Expiration Date

        // Create a JScrollPane to make the table scrollable
        JScrollPane scrollPane = new JScrollPane(dataTable);
        add(scrollPane, BorderLayout.CENTER);

        // back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            Point location = this.getLocation();
            this.dispose();
            mainFrame reopenedMainFrame = new mainFrame();
            reopenedMainFrame.setLocation(location.x, location.y);
        });
        add(backButton, BorderLayout.SOUTH);

        // Fetch and display the data
        showAllData();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void showAllData() {
        try {
            // Fetch all citizens data from the database
            List<Citizen> citizenList = citizenDao.findAll();

            // Clear any existing data in the table
            tableModel.setRowCount(0);

            // Populate the table with data
            for (Citizen citizen : citizenList) {
                // Add each citizen's data into a row
                tableModel.addRow(new Object[]{
                        citizen.getId(),
                        citizen.getFirstName(),
                        citizen.getLastName(),
                        citizen.getBirthdate(),
                        citizen.getAge(),
                        citizen.getGender(),
                        citizen.getReligion(),
                        citizen.getMaritalState(),
                        citizen.getHusbandName(),
                        citizen.getOccupation(),
                        citizen.getState(),
                        citizen.getAddress(),
                        citizen.getCreatingDate(),
                        citizen.getExpirationDate()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "An error occurred while fetching data: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
