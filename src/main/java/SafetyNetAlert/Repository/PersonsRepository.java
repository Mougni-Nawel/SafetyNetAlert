package SafetyNetAlert.Repository;

import java.util.List;

import Config.Generated;
import SafetyNetAlert.DTO.ChildAlerts;
import SafetyNetAlert.DTO.PersonsMobile;
import SafetyNetAlert.Model.MedicalRecords;
import SafetyNetAlert.Model.Persons;
import SafetyNetAlert.Model.SafetyAlerts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import SafetyNetAlert.Controller.Exception.*;

/**
 * methods related to the repository of person
 * @author Mougni
 *
 */
@Repository
@Generated
public class PersonsRepository implements IRepository{

	@Autowired
	FirestationsRepository firestationsRepository;

	@Autowired
	MedicalRecordsRepository medicalRecordsRepository;

	/**
	 * this method get a person's data from abstract repository.
	 * @return data stored from abstract repository.
	 */
	public SafetyAlerts getSafety(){
		return AbstractRepository.getSafety();
	}

	/**
	 * this method get all persons from where the data is stored.
	 * @return list of persons.
	 */
	@Override
	public List<Persons> findAll() throws PersonsNotFoundException {
		// TODO Auto-generated method stub
		return AbstractRepository.safety.getPersons();
	}

	/**
	 * this method save a person from person that is put in parameter.
	 * @param person represents the person passed from the service that has to be saved.
	 * @return persons that represents the person saved.
	 */
	public Persons save(Persons person) {
		// TODO Auto-generated method stub
		if(person != null) {
			AbstractRepository.safety.getPersons().add((Persons) person);
			int index = AbstractRepository.safety.getPersons().size();
			Persons persons = (Persons) AbstractRepository.safety.getPersons().get(index--);
			System.out.println(persons);
			return persons;
		}else{
			return null;
		}


	}

	/**
	 * this method get a person from firstname and lastname that is put in parameter.
	 * @param firstName represents the firstname of the person that has to be found.
	 * @param lastName represents the lastName of the person that has to be found.
	 * @return p that represents the person found.
	 */
	//@Override
	public <Persons> Persons findByIds(String firstName, String lastName) {
		// TODO Auto-generated method stub
		Persons p = null;
		for(int i = 0; i < AbstractRepository.safety.getPersons().size(); i++){
			if(AbstractRepository.safety.getPersons().get(i).getFirstName().contains(firstName) && AbstractRepository.safety.getPersons().get(i).getLastName().contains(lastName)){
				p = (Persons) AbstractRepository.safety.getPersons().get(i);
			}
		}


		return p;

	}

	/**
	 * this method remove a person from firstname and lastname that is put in parameter.
	 * @param firstName represents the firstname of the person that has to be removed.
	 * @param lastName represents the lastName of the person that has to be removed.
	 */
	//@Override
	public <T> void deleteByIds(String firstName, String lastName) {
		// TODO Auto-generated method stub
		Persons person = findByIds(firstName, lastName);
		if(person != null){
			int index = AbstractRepository.safety.getPersons().indexOf(person);
			AbstractRepository.safety.getPersons().remove(index);
		}


	}


	/**
	 * this method update a person from firstname and lastname and person that is put in parameter.
	 * @param firstName represents the firstname of the person that has to be updated.
	 * @param lastName represents the lastName of the person that has to be updated.
	 * @param person represents the new person that has to replace the old one.
	 */
	public <Persons> void update(Persons person, String firstName, String lastName) {
		// TODO Auto-generated method stub
		Persons personFound = findByIds(firstName, lastName);
		if(personFound != null){
			int index = AbstractRepository.safety.getPersons().indexOf(personFound);
			AbstractRepository.safety.getPersons().set(index, (SafetyNetAlert.Model.Persons) person);
			System.out.println(index);
		}

	}

	/**
	 * this method get the info of the person that is put in parameter.
	 * @param p represents the person that we have to find the info from.
	 * @return m that represents info of the person found.
	 */
	public MedicalRecords findPersonsInfo(Persons p) throws MedicalRecordLastnameNotFoundException, MedicalRecordFirstnameNotFoundException {
		MedicalRecords m = medicalRecordsRepository.findByIds(p.getFirstName(), p.getLastName());
		return m;
	}

//	public PersonsEmail getAllEmail(String city) {
//		return null;
//	}

	public PersonsMobile getAllMobile(int station) {
		return null;
	}

	public List<ChildAlerts> getChildByAddress(String address) {
		return null;
	}

	@Override
	public <T> T save(T firestations) {
		return null;
	}



}
