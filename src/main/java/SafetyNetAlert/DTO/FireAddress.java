package SafetyNetAlert.DTO;

import Config.Generated;
import java.util.List;

/**
 * represents a fireAddress class
 * @author Mougni
 *
 */
@Generated
public class FireAddress {

    private List<PersonInfoFireAddress> habitants;
    private List<Integer> station;

    public List<PersonInfoFireAddress> getHabitants() {
        return habitants;
    }

    public void setHabitants(List<PersonInfoFireAddress> habitants) {
        this.habitants = habitants;
    }

    public List<Integer> getStation() {
        return station;
    }

    public void setStation(List<Integer> station) {
        this.station = station;
    }

}

