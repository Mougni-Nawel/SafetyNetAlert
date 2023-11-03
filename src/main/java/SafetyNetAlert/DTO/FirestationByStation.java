package SafetyNetAlert.DTO;

import Config.Generated;
import SafetyNetAlert.Model.Persons;

import java.util.List;

/**
 * represents a firestationByStation class
 * @author Mougni
 *
 */
@Generated
public class FirestationByStation {

    private List<Persons> habitants;
    private int Adulte;
    private int Enfant;

    public List<Persons> getHabitants() {
        return habitants;
    }

    public void setHabitants(List<Persons> habitants) {
        this.habitants = habitants;
    }

    public int getAdulte() {
        return Adulte;
    }

    public void setAdulte(int adulte) {
        Adulte = adulte;
    }

    public int getEnfant() {
        return Enfant;
    }

    public void setEnfant(int enfant) {
        Enfant = enfant;
    }
}
