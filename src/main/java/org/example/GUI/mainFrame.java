package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class mainFrame extends JFrame {
    ImageIcon appIcon = new ImageIcon("D:\\collage\\programming_principles\\Intellij\\JDBC_Course\\Images\\appIcon.png");
    JButton create, update, delete, showAll;

    public mainFrame() {
        // Set frame properties
        setSize(380, 600);
        setTitle("Create New ID");
        setIconImage(appIcon.getImage());
        getContentPane().setBackground(new Color(0x123456));
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        setResizable(false);
        setLocationRelativeTo(null);

        // Project name
        JLabel projectNameLabel = new JLabel("ID Card");
        projectNameLabel.setFont(new Font("Arial", Font.BOLD, 40));
        projectNameLabel.setForeground(Color.WHITE);
        add(projectNameLabel);

        add(Box.createRigidArea(new Dimension(0, 20)));

        // Create buttons
        create = createButton("Create New ID", new Color(173, 216, 230));
        update = createButton("Update ID", new Color(144, 238, 144));
        delete = createButton("Delete ID", new Color(255, 99, 71));
        showAll = createButton("Show All Data", new Color(255, 222, 173));

        // Add buttons
        add(Box.createVerticalStrut(100));
        add(create);
        add(update);
        add(delete);
        add(showAll);

        // Button actions
        create.addActionListener(e -> openNewFrame(new CreateFrame(this)));
        update.addActionListener(e -> openNewFrame(new UpdateFrame(this)));
        delete.addActionListener(e -> openNewFrame(new DeleteFrame(this)));
        showAll.addActionListener(e -> openNewFrame(new ShowAllDataFrame(this)));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 70));
        button.setFocusable(false);
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setBorderPainted(false);

        // Set font size to make the button label slightly bigger
        button.setFont(new Font("Arial", Font.BOLD, 16));  // Adjust the size here

        return button;
    }


    private void openNewFrame(JFrame newFrame) {
        // Get the current location of the main frame
        Point location = this.getLocation();
        // Set the new frame's location slightly offset from the main frame
        newFrame.setLocation(location.x + 10, location.y + 10);
        // Hide the main frame and show the new frame
        this.setVisible(false);
        newFrame.setVisible(true);
    }
}
