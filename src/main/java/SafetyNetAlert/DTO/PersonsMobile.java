package SafetyNetAlert.DTO;

import Config.Generated;
import java.util.ArrayList;
import java.util.List;

/**
 * represents a personsMobile class
 * @author Mougni
 *
 */
@Generated
public class PersonsMobile {

    private List<String> mobile = new ArrayList<>();

    public List<String> getMobile() {
        return mobile;
    }

    public void setMobile(List<String> mobile) {
        this.mobile = mobile;
    }
}
