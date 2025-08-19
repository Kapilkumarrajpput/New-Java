import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class StudentRecordSystemV2 {
    static File file = new File("students.txt");

    public static void main(String[] args) {
        JFrame frame = new JFrame("Student Record System");

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(30, 30, 100, 30);
        JTextField nameField = new JTextField();
        nameField.setBounds(100, 30, 150, 30);

        JLabel rollLabel = new JLabel("Roll No:");
        rollLabel.setBounds(30, 70, 100, 30);
        JTextField rollField = new JTextField();
        rollField.setBounds(100, 70, 150, 30);

        JButton addBtn = new JButton("Add");
        addBtn.setBounds(30, 120, 80, 30);

        JButton viewBtn = new JButton("View All");
        viewBtn.setBounds(120, 120, 100, 30);

        JButton searchBtn = new JButton("Search");
        searchBtn.setBounds(230, 120, 90, 30);

        JButton updateBtn = new JButton("Update");
        updateBtn.setBounds(30, 160, 90, 30);

        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setBounds(130, 160, 90, 30);

        JTextArea displayArea = new JTextArea();
        displayArea.setBounds(30, 210, 300, 200);
        displayArea.setEditable(false);

        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(rollLabel);
        frame.add(rollField);
        frame.add(addBtn);
        frame.add(viewBtn);
        frame.add(searchBtn);
        frame.add(updateBtn);
        frame.add(deleteBtn);
        frame.add(displayArea);

        frame.setSize(400, 500);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ➕ Add Student
        addBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String roll = rollField.getText().trim();
            if (name.isEmpty() || roll.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields.");
                return;
            }

            try (FileWriter fw = new FileWriter(file, true)) {
                fw.write(roll + "," + name + "\n");
                JOptionPane.showMessageDialog(frame, "Student added!");
                nameField.setText("");
                rollField.setText("");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error writing file.");
            }
        });

        // 📋 View All Students
        viewBtn.addActionListener(e -> {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    sb.append("Roll: ").append(parts[0]).append(" | Name: ").append(parts[1]).append("\n");
                }
                displayArea.setText(sb.toString());
            } catch (IOException ex) {
                displayArea.setText("Error reading file.");
            }
        });

        // 🔍 Search Student
        searchBtn.addActionListener(e -> {
            String searchRoll = rollField.getText().trim();
            if (searchRoll.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Enter Roll No to search.");
                return;
            }

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                boolean found = false;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts[0].equals(searchRoll)) {
                        displayArea.setText("Record Found:\nRoll: " + parts[0] + " | Name: " + parts[1]);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    displayArea.setText("No record found for Roll No: " + searchRoll);
                }
            } catch (IOException ex) {
                displayArea.setText("Error reading file.");
            }
        });

        // 🔄 Update Student
        updateBtn.addActionListener(e -> {
            String rollToUpdate = rollField.getText().trim();
            String newName = nameField.getText().trim();
            if (rollToUpdate.isEmpty() || newName.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Enter Roll No and new Name to update.");
                return;
            }

            try {
                File tempFile = new File("temp.txt");
                BufferedReader br = new BufferedReader(new FileReader(file));
                BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));

                String line;
                boolean updated = false;

                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts[0].equals(rollToUpdate)) {
                        bw.write(rollToUpdate + "," + newName + "\n");
                        updated = true;
                    } else {
                        bw.write(line + "\n");
                    }
                }

                br.close();
                bw.close();

                file.delete();
                tempFile.renameTo(file);

                if (updated)
                    JOptionPane.showMessageDialog(frame, "Record updated.");
                else
                    JOptionPane.showMessageDialog(frame, "Roll No not found.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error updating file.");
            }
        });

        // ❌ Delete Student
        deleteBtn.addActionListener(e -> {
            String rollToDelete = rollField.getText().trim();
            if (rollToDelete.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Enter Roll No to delete.");
                return;
            }

            try {
                File tempFile = new File("temp.txt");
                BufferedReader br = new BufferedReader(new FileReader(file));
                BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));

                String line;
                boolean deleted = false;

                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts[0].equals(rollToDelete)) {
                        deleted = true;
                        continue; // skip writing this line
                    }
                    bw.write(line + "\n");
                }

                br.close();
                bw.close();

                file.delete();
                tempFile.renameTo(file);

                if (deleted)
                    JOptionPane.showMessageDialog(frame, "Record deleted.");
                else
                    JOptionPane.showMessageDialog(frame, "Roll No not found.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error deleting record.");
            }
        });
    }
}