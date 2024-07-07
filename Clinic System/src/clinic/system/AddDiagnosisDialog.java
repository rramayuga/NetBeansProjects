package clinic.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class AddDiagnosisDialog extends JDialog {
    private JComboBox<String> patientNameComboBox;
    private JTextField findingsField;
    private JTextField dateField;
    private JTextField actionNeededField;

    // List to hold patient names
    private List<String> patientNames = new ArrayList<>();

    public AddDiagnosisDialog(Frame parent) {
        super(parent, "Add Patient Diagnosis", true);
        initializeUI();
        loadPatientNamesFromCSV("patients.csv"); // Load patient names from CSV file
    }

    private void initializeUI() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Patient Name:"));
        patientNameComboBox = new JComboBox<>();
        panel.add(patientNameComboBox);

        panel.add(new JLabel("Findings:"));
        findingsField = new JTextField();
        panel.add(findingsField);

        panel.add(new JLabel("Date of Diagnosis:"));
        dateField = new JTextField();
        panel.add(dateField);

        panel.add(new JLabel("Action Needed:"));
        actionNeededField = new JTextField();
        panel.add(actionNeededField);

        JButton addButton = new JButton("Add Diagnosis");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String patientName = (String) patientNameComboBox.getSelectedItem();
                String findings = findingsField.getText();
                String date = dateField.getText();
                String actionNeeded = actionNeededField.getText();

                // Example: Save diagnosis information to database or file
                // DiagnosisUtils.addDiagnosis(patientName, findings, date, actionNeeded);

                JOptionPane.showMessageDialog(AddDiagnosisDialog.this,
                        "Diagnosis added successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);

                clearFields();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        panel.add(addButton);
        panel.add(cancelButton);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null);

        // Listen for changes in the patient name text field for dynamic filtering
        addPatientNameFilterListener();
    }

    private void loadPatientNamesFromCSV(String csvFilePath) {
        // Read patient names from CSV file and populate the patientNames list
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 0) {
                    patientNames.add(data[0].trim()); // Assuming patient name is in the first column
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error loading patient names from CSV file: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Add patient names to the combo box
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(patientNames.toArray(new String[0]));
        patientNameComboBox.setModel(model);
    }

    private void addPatientNameFilterListener() {
        JTextField patientNameField = (JTextField) patientNameComboBox.getEditor().getEditorComponent();
        patientNameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterPatientNames(patientNameField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterPatientNames(patientNameField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterPatientNames(patientNameField.getText());
            }
        });
    }

    private void filterPatientNames(String filter) {
        DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) patientNameComboBox.getModel();
        model.removeAllElements();

        for (String name : patientNames) {
            if (name.toLowerCase().contains(filter.toLowerCase())) {
                model.addElement(name);
            }
        }

        patientNameComboBox.setModel(model);
        patientNameComboBox.setPopupVisible(model.getSize() > 0);
    }

    private void clearFields() {
        findingsField.setText("");
        dateField.setText("");
        actionNeededField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Add Diagnosis Test");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                AddDiagnosisDialog dialog = new AddDiagnosisDialog(frame);
                dialog.setVisible(true);
            }
        });
    }
}

/*
Doctor's Name
Reason for consultation
\\\Date of Diagnosis to Date of Consultation
Findings to Diagnosis
DATE FIRST
Action Needed to Recommendation
DATES SHOULD HAVE CALENDAR
*/
