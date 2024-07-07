/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinic.system;
public class Patient {
    private String name;
    private String telephone;
    private String gender;
    private int age;
    private String address;
    private String imagePath;

    public Patient(String name, String telephone, String birthday, int age, String address, String imagePath) {
        this.name = name;
        this.telephone = telephone;
        this.gender = gender;
        this.age = age;
        this.address = address;
        this.imagePath = imagePath;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}
