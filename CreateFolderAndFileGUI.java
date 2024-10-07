import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreateFolderAndFileGUI {
    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Folder and File Creator");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Create label and text field for folder name
        JLabel folderLabel = new JLabel("Enter folder name:");
        folderLabel.setBounds(20, 20, 150, 25);
        frame.add(folderLabel);

        JTextField folderField = new JTextField();
        folderField.setBounds(150, 20, 200, 25);
        frame.add(folderField);

        // Create a button to create folder and file
        JButton createButton = new JButton("Create Folder and File");
        createButton.setBounds(100, 70, 200, 30);
        frame.add(createButton);

        // Label to display status
        JLabel statusLabel = new JLabel("Status: ");
        statusLabel.setBounds(20, 120, 350, 25);
        frame.add(statusLabel);

        // Add action listener to the button
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String folderName = folderField.getText();
                if (folderName.isEmpty()) {
                    statusLabel.setText("Status: Please enter a folder name.");
                    return;
                }

                // Create the folder
                File folder = new File(folderName);
                if (!folder.exists()) {
                    if (folder.mkdir()) {
                        statusLabel.setText("Status: Folder created successfully: " + folder.getAbsolutePath());
                    } else {
                        statusLabel.setText("Status: Failed to create folder.");
                        return;
                    }
                } else {
                    statusLabel.setText("Status: Folder already exists: " + folder.getAbsolutePath());
                }

                // Create the file inside the folder
                File file = new File(folder, "AllSolutions.java");
                try {
                    if (file.createNewFile()) {
                        statusLabel.setText("Status: File created successfully: " + file.getAbsolutePath());
                        // Optionally write something to the file
                        try (FileWriter writer = new FileWriter(file)) {
                            writer.write("// This file contains all solutions that have passed all testcases. \n\n");
                        }
                    } else {
                        statusLabel.setText("Status: File already exists: " + file.getAbsolutePath());
                    }
                } catch (IOException ex) {
                    statusLabel.setText("Status: An error occurred while creating the file: " + ex.getMessage());
                }
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }
}