package SafetyNetAlert.dto;

import SafetyNetAlert.config.Generated;

import java.util.List;

/**
 * represents a flood class
 * @author Mougni
 *
 */
@Generated
public class Flood {

    private String address;
    private String lastName;
    private String phone;
    private int age;
    private List<String> antécedentMedicaux;

    /**
     * constructor.
     * @param address of a person
     * @param lastName is the lastname of a person
     * @param phone is the phone of a person
     * @param age is the age of a person
     * @param antécedentMedicaux is a person medical history
     *
     */
    public Flood(String address, String lastName, String phone, int age, List<String> antécedentMedicaux) {
        this.address = address;
        this.lastName = lastName;
        this.phone = phone;
        this.age = age;
        this.antécedentMedicaux = antécedentMedicaux;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public List<String> getAntécedentMedicaux() {
        return antécedentMedicaux;
    }

    public void setAntécedentMedicaux(List<String> antécedentMedicaux) {
        this.antécedentMedicaux = antécedentMedicaux;
    }
}
