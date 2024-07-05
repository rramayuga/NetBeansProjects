package clinic.system;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AdminPanel extends JFrame {
    public AdminPanel() {
        setTitle("Admin Panel");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JButton addPatientButton = new JButton("Add Patient");
        addPatientButton.setBounds(10, 10, 150, 25);
        add(addPatientButton);

        JButton viewPatientsButton = new JButton("View Patients");
        viewPatientsButton.setBounds(10, 45, 150, 25);
        add(viewPatientsButton);

        JButton addDiagnosisButton = new JButton("Add Diagnosis");
        addDiagnosisButton.setBounds(10, 80, 150, 25);
        add(addDiagnosisButton);

        addPatientButton.addActionListener((ActionEvent e) -> {
            AddPatientDialog addPatientDialog = new AddPatientDialog(AdminPanel.this);
            addPatientDialog.setVisible(true);
        });

        viewPatientsButton.addActionListener((ActionEvent e) -> {
            ViewPatientDialog viewPatientDialog = new ViewPatientDialog(AdminPanel.this);
            viewPatientDialog.setVisible(true);
        });

        addDiagnosisButton.addActionListener((ActionEvent e) -> {
            AddDiagnosisDialog addDiagnosisDialog = new AddDiagnosisDialog(AdminPanel.this);
            addDiagnosisDialog.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminPanel adminPanel = new AdminPanel();
            adminPanel.setVisible(true);
        });
    }
}
