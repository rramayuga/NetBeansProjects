/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinic.system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class AddPatientDialog extends JDialog {
    private JTextField nameField, telephoneField, birthdayField, ageField, addressField;
    private JLabel imageLabel;
    private String imagePath;

    public AddPatientDialog(JFrame parent) {
        super(parent, "Add Patient", true);
        setSize(400, 600);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(parent);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel nameLabel = new JLabel("Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(nameLabel, gbc);

        nameField = new JTextField(20);
        gbc.gridx = 1;
        add(nameField, gbc);

        JLabel telephoneLabel = new JLabel("Telephone:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(telephoneLabel, gbc);

        telephoneField = new JTextField(20);
        gbc.gridx = 1;
        add(telephoneField, gbc);

        JLabel birthdayLabel = new JLabel("Birthday:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(birthdayLabel, gbc);

        birthdayField = new JTextField(20);
        gbc.gridx = 1;
        add(birthdayField, gbc);

        JLabel ageLabel = new JLabel("Age:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(ageLabel, gbc);

        ageField = new JTextField(20);
        gbc.gridx = 1;
        add(ageField, gbc);

        JLabel addressLabel = new JLabel("Address:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(addressLabel, gbc);

        addressField = new JTextField(20);
        gbc.gridx = 1;
        add(addressField, gbc);

        JLabel imageUploadLabel = new JLabel("Upload Image:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(imageUploadLabel, gbc);

        JButton uploadButton = new JButton("Upload");
        gbc.gridx = 1;
        add(uploadButton, gbc);

        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(250, 250));
        gbc.gridx = 1;
        gbc.gridy = 6;
        add(imageLabel, gbc);

        JButton addButton = new JButton("Add");
        gbc.gridx = 1;
        gbc.gridy = 7;
        add(addButton, gbc);

        uploadButton.addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(AddPatientDialog.this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                imagePath = file.getAbsolutePath();
                ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT));
                imageLabel.setIcon(imageIcon);
            }
        });

        addButton.addActionListener((ActionEvent e) -> {
            String name1 = nameField.getText().trim();
            String telephone = telephoneField.getText().trim();
            String birthday = birthdayField.getText().trim();
            String ageText = ageField.getText().trim();
            String address = addressField.getText().trim();
            if (name1.isEmpty() || telephone.isEmpty() || birthday.isEmpty() || ageText.isEmpty() || address.isEmpty() || imagePath == null) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields");
            } else {
                try {
                    int age = Integer.parseInt(ageText);
                    Patient patient = new Patient(name1, telephone, birthday, age, address, imagePath);
                    ExcelUtils.addPatientToExcel(patient);
                    JOptionPane.showMessageDialog(null, "Patient added successfully");
                    dispose();
                }catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid age");
                }
            }
        });
    }
}
