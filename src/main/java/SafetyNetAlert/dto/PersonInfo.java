package SafetyNetAlert.dto;

import SafetyNetAlert.config.Generated;

import java.util.ArrayList;
import java.util.List;

/**
 * represents a personInfo class
 * @author Mougni
 *
 */
@Generated
public class PersonInfo {

    private String lastName;
    private String address;
    private int age;
    private String email;
    private List<String> medications = new ArrayList<String>();
    private List<String> allergies = new ArrayList<String>();

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getMedications() {
        return medications;
    }

    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }
}
