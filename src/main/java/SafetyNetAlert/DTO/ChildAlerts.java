package SafetyNetAlert.DTO;

import Config.Generated;
import SafetyNetAlert.Model.Persons;
import java.util.List;

/**
 * represents a childAlert class
 * @author Mougni
 *
 */
@Generated
public class ChildAlerts {


    private String lastName;
    private String firstName;
    private int age;
    private List<Persons> membres;

    /**
     * constructor.
     * @param lastName is the lastname of a child
     * @param firstName is the phone of a child
     * @param age is the age of a child
     * @param membres is a list of all the child's family members
     *
     */
    public ChildAlerts(String lastName, String firstName, int age, List<Persons> membres) {
        this.age = age;
        this.membres= membres;
        this.lastName = lastName;
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Persons> getMembres() {
        return membres;
    }

    public void setMembres(List<Persons> membres) {
        this.membres = membres;
    }
}
