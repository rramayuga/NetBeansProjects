package clinic.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class AddDiagnosisDialog extends JDialog {
    private JComboBox<String> patientNameComboBox;
    private JTextField doctorNameField;
    private JTextField reasonForConsultationField;
    private JSpinner diagnosisDateSpinner;
    private JTextField findingsField;
    private JTextField recommendationField;

    // List to hold patient names
    private List<String> patientNames = new ArrayList<>();

    public AddDiagnosisDialog(Frame parent) {
        super(parent, "Add Patient Diagnosis", true);
        initializeUI();
        loadPatientNamesFromCSV("patients.csv"); // Load patient names from CSV file
    }

    private void initializeUI() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10)); // Adjusted rows for form fields
        formPanel.add(new JLabel("Patient Name:"));
        patientNameComboBox = new JComboBox<>();
        patientNameComboBox.setEditable(true);
        formPanel.add(patientNameComboBox);

        formPanel.add(new JLabel("Doctor's Name:"));
        doctorNameField = new JTextField();
        formPanel.add(doctorNameField);

        formPanel.add(new JLabel("Reason for Consultation:"));
        reasonForConsultationField = new JTextField();
        formPanel.add(reasonForConsultationField);

        formPanel.add(new JLabel("Date of Diagnosis:"));
        diagnosisDateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(diagnosisDateSpinner, "yyyy-MM-dd");
        diagnosisDateSpinner.setEditor(dateEditor);
        formPanel.add(diagnosisDateSpinner);

        formPanel.add(new JLabel("Diagnosis:"));
        findingsField = new JTextField();
        formPanel.add(findingsField);

        formPanel.add(new JLabel("Recommendation:"));
        recommendationField = new JTextField();
        formPanel.add(recommendationField);

        panel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addButton = new JButton("Add Diagnosis");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Add Button Clicked");
                String patientName = (String) patientNameComboBox.getSelectedItem();
                String doctorName = doctorNameField.getText();
                String reasonForConsultation = reasonForConsultationField.getText();
                String diagnosisDate = new SimpleDateFormat("yyyy-MM-dd").format(diagnosisDateSpinner.getValue());
                String findings = findingsField.getText();
                String recommendation = recommendationField.getText();

                saveDiagnosisToCSV("diagnosis.csv", patientName, doctorName, reasonForConsultation, diagnosisDate, findings, recommendation);

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
                System.out.println("Cancel Button Clicked");
                dispose();
            }
        });

        JButton printButton = new JButton("Print");
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Print Button Clicked");
                printPatientInfo();
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(printButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
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
        });
    }

    private void clearFields() {
        doctorNameField.setText("");
        reasonForConsultationField.setText("");
        diagnosisDateSpinner.setValue(new Date());
        findingsField.setText("");
        recommendationField.setText("");
    }

    private void saveDiagnosisToCSV(String csvFilePath, String patientName, String doctorName, String reasonForConsultation, String diagnosisDate, String findings, String recommendation) {
        try (FileWriter writer = new FileWriter(csvFilePath, true);
             BufferedWriter bw = new BufferedWriter(writer);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(patientName + "," + doctorName + "," + reasonForConsultation + "," + diagnosisDate + "," + findings + "," + recommendation);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error saving diagnosis to CSV file: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void printPatientInfo() {
        String patientName = (String) patientNameComboBox.getSelectedItem();
        String doctorName = doctorNameField.getText();
        String reasonForConsultation = reasonForConsultationField.getText();
        String diagnosisDate = new SimpleDateFormat("yyyy-MM-dd").format(diagnosisDateSpinner.getValue());
        String findings = findingsField.getText();
        String recommendation = recommendationField.getText();

        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setPrintable(new Printable() {
            @Override
            public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
                if (pageIndex > 0) {
                    return NO_SUCH_PAGE;
                }

                Graphics2D g2d = (Graphics2D) g;
                g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

                g.drawString("Patient Information", 100, 100);
                g.drawString("Patient Name: " + patientName, 100, 120);
                g.drawString("Doctor's Name: " + doctorName, 100, 140);
                g.drawString("Reason for Consultation: " + reasonForConsultation, 100, 160);
                g.drawString("Date of Diagnosis: " + diagnosisDate, 100, 180);
                g.drawString("Diagnosis: " + findings, 100, 200);
                g.drawString("Recommendation: " + recommendation, 100, 220);

                return PAGE_EXISTS;
            }
        });

        boolean doPrint = printerJob.printDialog();
        if (doPrint) {
            try {
                printerJob.print();
            } catch (PrinterException e) {
                JOptionPane.showMessageDialog(this, "Print Error: " + e.getMessage(),
                        "Print Error", JOptionPane.ERROR_MESSAGE);
            }
        }
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
