package SafetyNetAlert.Model;


import Config.Generated;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * represents a medicalRecords class
 * @author Mougni
 *
 */
@Generated
@Getter
@Setter
public class MedicalRecords {

	private String firstName;
	private String lastName;
	private String birthdate;
	private List<String> medications = new ArrayList<String>();
	private List<String> allergies = new ArrayList<String>();


	
	
}
