package org.example.GUI;

import org.example.dao.CitizenDao;
import org.example.dao.CitizenDaoImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DeleteFrame extends JFrame {
    private final JTextField idField;
    private final CitizenDao citizenDao;

    public DeleteFrame(mainFrame mainFrame) {
        // Initialize DAO
        citizenDao = new CitizenDaoImpl();

        // Set frame properties
        setTitle("Delete Citizen");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set the location of the new frame to match the main frame
        setLocation(mainFrame.getLocation());

        // ID Panel
        JPanel idPanel = new JPanel(new FlowLayout());
        idPanel.add(new JLabel("Enter ID:"));
        idField = new JTextField(15);
        idPanel.add(idField);
        add(idPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton showButton = new JButton("Show");
        JButton deleteButton = new JButton("Delete");
        JButton backButton = new JButton("Back");

        showButton.addActionListener(this::handleShow);
        deleteButton.addActionListener(this::handleDelete);
        backButton.addActionListener(e -> {
            Point location = this.getLocation();
            this.dispose();
            mainFrame reopenedMainFrame = new mainFrame();
            reopenedMainFrame.setLocation(location.x, location.y);
        });

        buttonPanel.add(showButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void handleShow(ActionEvent e) {
        String id = idField.getText();
        try {
            // Check if ID exists
            if (citizenDao.isIdExist(id)) {
                // Fetch the citizen's data
                String[] citizen = citizenDao.getNameByID(id); // Expecting [firstName, lastName]
                JOptionPane.showMessageDialog(this,
                        "First Name: " + citizen[0] + "\nLast Name: " + citizen[1],
                        "Citizen Details", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "ID not found in the database. Please enter a valid ID.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "An error occurred while retrieving the data: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDelete(ActionEvent e) {
        String id = idField.getText();
        try {
            // Check if ID exists
            if (citizenDao.isIdExist(id)) {
                // Delete the citizen's data
                citizenDao.deleteByID(id);
                JOptionPane.showMessageDialog(this,
                        "Citizen with ID " + id + " has been deleted successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);

                // Clear the ID field
                idField.setText("");
            } else {
                JOptionPane.showMessageDialog(this,
                        "ID not found in the database. Please enter a valid ID.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "An error occurred while deleting the data: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
