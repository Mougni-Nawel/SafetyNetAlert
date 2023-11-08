package SafetyNetAlert.dto;

import SafetyNetAlert.config.Generated;

import java.util.List;

/**
 * represents a personsEmail class
 * @author Mougni
 *
 */
@Generated
public class PersonsEmail {

    private List<String> email;

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

}


