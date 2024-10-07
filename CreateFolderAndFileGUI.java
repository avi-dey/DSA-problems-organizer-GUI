import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreateFolderAndFileGUI {
    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Folder and File Creator");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Create label and combo box for parent folder selection
        JLabel parentFolderLabel = new JLabel("Select parent folder:");
        parentFolderLabel.setBounds(20, 20, 150, 25);
        frame.add(parentFolderLabel);

        String[] parentFolders = {"Leecode", "GeeksForGeeks", "Codeforces", "CodingNinja"};
        JComboBox<String> parentFolderComboBox = new JComboBox<>(parentFolders);
        parentFolderComboBox.setBounds(150, 20, 200, 25);
        frame.add(parentFolderComboBox);

        // Create label and text field for folder name
        JLabel folderLabel = new JLabel("Enter folder name:");
        folderLabel.setBounds(20, 60, 150, 25);
        frame.add(folderLabel);

        JTextField folderField = new JTextField();
        folderField.setBounds(150, 60, 200, 25);
        frame.add(folderField);

        // Create a button to create folder and file
        JButton createButton = new JButton("Create Folder and File");
        createButton.setBounds(100, 110, 200, 30);
        frame.add(createButton);

        // Label to display status
        JLabel statusLabel = new JLabel("Status: ");
        statusLabel.setBounds(20, 160, 350, 25);
        frame.add(statusLabel);

        // Action listener to "create folder and file" button
        ActionListener createActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String parentFolderName = (String) parentFolderComboBox.getSelectedItem();
                String folderName = folderField.getText();

                if (folderName.isEmpty()) {
                    statusLabel.setText("Status: Please enter a folder name.");
                    return;
                }

                // Create the folder inside the selected parent folder
                File parentFolder = new File(parentFolderName);
                if (!parentFolder.exists()) {
                    parentFolder.mkdir();
                }

                File folder = new File(parentFolder, folderName);
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

                // Close the frame after clicking the button
                frame.dispose();
            }
        };

        // Add action listener to the button
        createButton.addActionListener(createActionListener);

        // Add key listener to the folderField to handle Enter key
        folderField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    createActionListener.actionPerformed(null);
                }
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }
}
