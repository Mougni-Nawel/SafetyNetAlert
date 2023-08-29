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

    private List mobile = new ArrayList();

    public List getMobile() {
        return mobile;
    }

    public void setMobile(List mobile) {
        this.mobile = mobile;
    }
}
