/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinic.system;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PatientTableModel extends AbstractTableModel {
    private final List<Patient> patients;
    private final String[] columnNames = {"Name", "Telephone", "Birthday", "Age", "Address"};

    public PatientTableModel(List<Patient> patients) {
        this.patients = patients;
    }

    @Override
    public int getRowCount() {
        return patients.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Patient patient = patients.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> patient.getName();
            case 1 -> patient.getTelephone();
            case 2 -> patient.getBirthday();
            case 3 -> patient.getAge();
            case 4 -> patient.getAddress();
            default -> null;
        };
    }

    public Patient getPatientAt(int rowIndex) {
        return patients.get(rowIndex);
    }
}
