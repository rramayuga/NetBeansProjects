/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinic.system;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ViewPatientDialog extends JDialog {
    private JTable table;
    private DefaultTableModel model;
    private List<Patient> patients;

    public ViewPatientDialog(Frame parent) {
        super(parent, "View Patients", true);
        initializeUI();
        loadPatients();
    }

    private void initializeUI() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Telephone");
        model.addColumn("Gender");
        model.addColumn("Age");
        model.addColumn("Address");
        model.addColumn("Image Path");

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton editButton = new JButton("Edit Selected Patient");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String patientName = (String) table.getValueAt(selectedRow, 0);
                    Patient selectedPatient = findPatientByName(patientName);
                    if (selectedPatient != null) {
                        showEditPatientDialog(selectedPatient);
                    }
                } else {
                    JOptionPane.showMessageDialog(ViewPatientDialog.this,
                            "Please select a patient to edit.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton deleteButton = new JButton("Delete Selected Patient");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String patientName = (String) table.getValueAt(selectedRow, 0);
                    int confirm = JOptionPane.showConfirmDialog(ViewPatientDialog.this,
                            "Are you sure you want to delete patient: " + patientName + "?",
                            "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        deletePatient(patientName);
                    }
                } else {
                    JOptionPane.showMessageDialog(ViewPatientDialog.this,
                            "Please select a patient to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null);
    }

    private void loadPatients() {
        patients = ExcelUtils.getPatientsFromExcel();
        model.setRowCount(0); // Clear existing rows
        for (Patient patient : patients) {
            Object[] row = {patient.getName(), patient.getTelephone(), patient.getGender(),
                    patient.getAge(), patient.getAddress(), patient.getImagePath()};
            model.addRow(row);
        }
    }

    private Patient findPatientByName(String name) {
        for (Patient patient : patients) {
            if (patient.getName().equals(name)) {
                return patient;
            }
        }
        return null;
    }

    private void showEditPatientDialog(Patient patient) {
        // Implement your edit patient dialog or form here
        // Example: EditPatientDialog editDialog = new EditPatientDialog(patient);
        // editDialog.setVisible(true);
    }

    private void deletePatient(String patientName) {
        ExcelUtils.deletePatientFromExcel(patientName);
        loadPatients(); // Refresh the table after deletion
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ViewPatientDialog dialog = new ViewPatientDialog(new JFrame());
                dialog.setVisible(true);
            }
        });
    }
}
