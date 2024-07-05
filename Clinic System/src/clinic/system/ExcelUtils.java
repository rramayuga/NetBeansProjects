/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinic.system;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {
    private static final String FILE_PATH = "patients.csv";

    public static void addPatientToExcel(Patient patient) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            StringBuilder sb = new StringBuilder();
            sb.append(patient.getName()).append(",");
            sb.append(patient.getTelephone()).append(",");
            sb.append(patient.getBirthday()).append(",");
            sb.append(patient.getAge()).append(",");
            sb.append(patient.getAddress()).append(",");
            sb.append(patient.getImagePath()).append("\n");
            writer.write(sb.toString());
            System.out.println("Patient added successfully to CSV.");
        } catch (IOException e) {
        }
    }

    public static List<Patient> getPatientsFromExcel() {
        List<Patient> patients = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 6) {
                    String name = data[0].trim();
                    String telephone = data[1].trim();
                    String birthday = data[2].trim();
                    int age = Integer.parseInt(data[3].trim());
                    String address = data[4].trim();
                    String imagePath = data[5].trim();
                    Patient patient = new Patient(name, telephone, birthday, age, address, imagePath);
                    patients.add(patient);
                }
            }
        } catch (IOException | NumberFormatException e) {
        }
        return patients;
    }

    public static void updatePatientInExcel(String patientName, Patient updatedPatient) {
        List<Patient> patients = getPatientsFromExcel();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Patient patient : patients) {
                if (patient.getName().equals(patientName)) {
                    // Update patient information with new data
                    writer.write(updatedPatient.getName() + ",");
                    writer.write(updatedPatient.getTelephone() + ",");
                    writer.write(updatedPatient.getBirthday() + ",");
                    writer.write(updatedPatient.getAge() + ",");
                    writer.write(updatedPatient.getAddress() + ",");
                    writer.write(updatedPatient.getImagePath() + "\n");
                } else {
                    // Write existing patient data if not updated
                    writer.write(patient.getName() + ",");
                    writer.write(patient.getTelephone() + ",");
                    writer.write(patient.getBirthday() + ",");
                    writer.write(patient.getAge() + ",");
                    writer.write(patient.getAddress() + ",");
                    writer.write(patient.getImagePath() + "\n");
                }
            }
            System.out.println("Patient updated successfully in CSV.");
        } catch (IOException e) {
        }
    }
    public static void deletePatientFromExcel(String patientName) {
        List<Patient> patients = getPatientsFromExcel();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Patient patient : patients) {
                if (!patient.getName().equals(patientName)) {
                    // Write existing patient data except the one to be deleted
                    writer.write(patient.getName() + ",");
                    writer.write(patient.getTelephone() + ",");
                    writer.write(patient.getBirthday() + ",");
                    writer.write(patient.getAge() + ",");
                    writer.write(patient.getAddress() + ",");
                    writer.write(patient.getImagePath() + "\n");
                }
            }
            System.out.println("Patient deleted successfully from CSV.");
        } catch (IOException e) {
        }
    }
}
