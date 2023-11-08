package SafetyNetAlert.dto;

import SafetyNetAlert.config.Generated;

import java.util.List;

/**
 * represents a personInfoFireAddress class
 * @author Mougni
 *
 */
@Generated
public class PersonInfoFireAddress {

    private String lastName;
    private String phone;
    private int age;
    private List<String> medication;
    private List<String> allergies;

    /**
     * constructor.
     * @param lastName is the lastname of a person
     * @param phone is the phone of a person
     * @param age is the age of a person
     * @param medication is a person medical history that concerns medications
     * @param allergies is a person medical history that concerns allergies
     */
    public PersonInfoFireAddress(String lastName, String phone, int age, List<String> medication, List<String> allergies) {
        this.lastName = lastName;
        this.phone = phone;
        this.age = age;
        this.medication = medication;
        this.allergies = allergies;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getMedication() {
        return medication;
    }

    public void setMedication(List<String> medication) {
        this.medication = medication;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }
}
