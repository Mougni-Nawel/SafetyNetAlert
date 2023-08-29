package SafetyNetAlert.DTO;

import Config.Generated;

import java.util.List;

/**
 * represents a firestationByStation class
 * @author Mougni
 *
 */
@Generated
public class FirestationByStation {

    private List habitants;
    private int Adulte;
    private int Enfant;

    public List getHabitants() {
        return habitants;
    }

    public void setHabitants(List habitants) {
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
