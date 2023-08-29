package SafetyNetAlert.Model;

import Config.Generated;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * represents a safetyAlerts class
 * @author Mougni
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Generated
public class SafetyAlerts {

	List<Persons> persons;
	List<Firestations> firestations;
	List<MedicalRecords> medicalrecords;

	/**
	 * constructor.
	 * @param persons is a list of persons
	 * @param firestations is a list of firestations
	 * @param medicalrecords is a list of medicals records
	 */
	public SafetyAlerts(List<Persons> persons, List<Firestations> firestations, List<MedicalRecords> medicalrecords) {
		this.persons = persons;
		this.firestations = firestations;
		this.medicalrecords = medicalrecords;
	}
	public SafetyAlerts() {

	}

	public List<Persons> getPersons() {
		return persons;
	}
	public void setPersons(List<Persons> persons) {
		this.persons = persons;
	}

	public List<Firestations> getFirestations() {
		return firestations;
	}

	public void setFirestations(List<Firestations> firestations) {
		this.firestations = firestations;
	}

	public List<MedicalRecords> getMedicalrecords() {
		return medicalrecords;
	}

	public void setMedicalrecords(List<MedicalRecords> medicalrecords) {
		this.medicalrecords = medicalrecords;
	}
}
