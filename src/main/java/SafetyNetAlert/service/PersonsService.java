package SafetyNetAlert.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import SafetyNetAlert.controller.exception.*;
import SafetyNetAlert.dto.ChildAlerts;
import SafetyNetAlert.dto.PersonInfo;
import SafetyNetAlert.dto.PersonsEmail;
import SafetyNetAlert.dto.PersonsMobile;
import SafetyNetAlert.model.Firestations;
import SafetyNetAlert.model.MedicalRecords;
import SafetyNetAlert.model.Persons;
import SafetyNetAlert.model.SafetyAlerts;
import SafetyNetAlert.repository.MedicalRecordsRepository;
import SafetyNetAlert.repository.PersonsRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * methods related to person that are implemented from person interface.
 * @author Mougni
 *
 */
@Service
@Data
@Slf4j
public class PersonsService implements IPersonService{

	@Autowired
	public PersonsRepository personsRepository;

	@Autowired
	public MedicalRecordsRepository medicalRecordsRepository;

	@Autowired
	public MedicalRecordsService medicalRecordsService;

	private static final Logger logger = LogManager.getLogger(PersonsService.class);


	final String FirstnameNotFoundException = "L'utilisateur n'est pas trouvé avec ce firstname";
	final String NotFoundException = "L'utilisateur n'est pas trouvé";
	final String MedicalRecordNotFoundException = "The given Medical Record is not found";
	final String LastnameNotFoundException = "L'utilisateur n'est pas trouvé avec ce firstname";
	final String ParameterIsNullException = "Le parametre est null";
	final String ListIsNullException = "La liste est empty";


	/**
	 * this method get all the persons.
	 * @return all the persons registered.
	 */
	public List<Persons> getAllPersons() throws NotFoundException {
		log.info("Function : getAllPersons");
		return personsRepository.findAll();
	}

	/**
	 * this method save the person put in parameter.
	 * @param person represents the person that has to be saved
	 * @return the person saved.
	 */
	public Persons savePerson(Persons person) {
		log.info("Function : savePerson");
        return personsRepository.save(person);

	}

	/**
	 * this method get the person from the firstName and the lastName put in parameter.
	 * @param firstName represents the firstName of the person that has to be found.
	 * @param lastName represents the lastName of the person that has to be found.
	 * @return person that reprensents the person found.
	 * @throws NotFoundException if there is no person found with this firstName or name.
	 * TODO return
	 */
	public Persons getPersons(final String firstName, final String lastName) throws NotFoundException {
		log.info("Function : getPersons");
        Persons person = personsRepository.findByIds(firstName, lastName);
		if(person != null){
			return person;
		}else{
			SafetyAlerts safetyAlerts = personsRepository.getSafety();
			if(!safetyAlerts.getPersons().contains(firstName)){
				throw new NotFoundException(FirstnameNotFoundException);
			}else if(!safetyAlerts.getPersons().contains(lastName)){
				throw new NotFoundException(LastnameNotFoundException);
			}

		}
        return person;
    }

	/**
	 * this method remove a person from the firstName and the lastName put in parameter.
	 * @param firstName represents the firstName of the person that has to be removed.
	 * @param lastName represents the lastName of the person that has to be removed.
	 * @throws NotFoundException if there is no person found with this name.
	 */
	public void deleteAccount(String firstName, String lastName) throws NotFoundException {
		// TODO Auto-generated method stub
		log.info("Function : deleteAccount");
		Persons person = (Persons) personsRepository.findByIds(firstName, lastName);
		if(person != null){
			personsRepository.deleteByIds(firstName, lastName);
		}else{
			SafetyAlerts safetyAlerts = personsRepository.getSafety();
			if(!safetyAlerts.getPersons().contains(firstName)){
				throw new NotFoundException(FirstnameNotFoundException);
			}else if(!safetyAlerts.getPersons().contains(lastName)){
				throw new NotFoundException(LastnameNotFoundException);
			}
			throw new NotFoundException(NotFoundException);
		}

		
	}

	/**
	 * this method update a person from the firstName and the lastName and the person updated put in parameter.
	 * @param firstName represents the firstName of the person that has to be updated.
	 * @param lastName represents the lastName of the person that has to be updated.
	 * @param person represents the new version of person that has to replace the old person.
	 * @return currentPerson that reprensents the person updated.
	 * @throws NotFoundException if there is no person found with this name.
	 */
	public Persons updatePerson(Persons person, String firstName, String lastName) throws NotFoundException {
		log.info("Function : updatePerson");
		Persons e = personsRepository.findByIds(firstName, lastName);
		Persons currentPerson = null;
		if (e != null) {
			currentPerson = e;

			String adress = person.getAddress();
			if (adress != null) {
				currentPerson.setAddress(adress);
			}
			String city = person.getCity();
			if (city != null) {
				currentPerson.setCity(city);
			}
			int zip = person.getZip();
			if (zip != 0) {
				currentPerson.setZip(zip);
			}
			String phone = person.getPhone();
			if (phone != null) {
				currentPerson.setPhone(phone);
			}
			String email = person.getEmail();
			if (email != null) {
				currentPerson.setEmail(email);
			}
			personsRepository.update(person, firstName, lastName);
			return currentPerson;
		}else{
			SafetyAlerts safetyAlerts = personsRepository.getSafety();
			if(!safetyAlerts.getPersons().contains(firstName)){
				throw new NotFoundException(FirstnameNotFoundException);
			}else if(!safetyAlerts.getPersons().contains(lastName)){
				throw new NotFoundException(LastnameNotFoundException);
			}
			throw new NotFoundException(NotFoundException);
		}


	}

	// routes supplémentaire

	/**
	 * this method get all the info of a person from the medicalRecord and person put in parameter.
	 * @param person represents a person.
	 * @param medicalRecords represents the medicalRecord of a person.
	 * @return personInfo that represent all the info of a person include his medications and allergies.
	 * @throws ParameterIsNullException if the person in parameter is null.
	 */
	public PersonInfo addPersonInfo(Persons person, MedicalRecords medicalRecords) throws ParameterIsNullException {
		log.info("Function : addPersonInfo");
		if(person != null) {
			PersonInfo personInfo = new PersonInfo();
			personInfo.setAddress(person.getAddress());
			personInfo.setLastName(person.getLastName());
			personInfo.setAllergies(medicalRecords.getAllergies());
			personInfo.setEmail(person.getEmail());
			personInfo.setMedications(medicalRecords.getMedications());
			LocalDate curDate = LocalDate.now();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("LL/dd/yyyy", Locale.FRANCE);
			LocalDate birthdate = LocalDate.parse(medicalRecords.getBirthdate(), dtf);
			Period period = Period.between(birthdate, curDate);
			personInfo.setAge(period.getYears());

			return personInfo;
		}else{
			throw new ParameterIsNullException(ParameterIsNullException);
		}
	}

	/**
	 * this method return a list of persons info from the firstName and lastName put in parameter.
	 * @param firstName represents the firstName a person.
	 * @param lastName represents the lastName of a person.
	 * @return persons that represent all the info of a persons that has the same lastName.
	 * @throws NotFoundException if the medicalRecord in parameter is null.
	 */
	public List<PersonInfo> getPersonsInfo(String firstName, String lastName) throws NotFoundException, ParameterIsNullException {
		log.info("Function : getPersonsInfo");
		SafetyAlerts safetyAlerts = personsRepository.getSafety();
		Persons p = personsRepository.findByIds(firstName, lastName);
			List<PersonInfo> persons = new ArrayList<>();
			for(Persons person : safetyAlerts.getPersons()) {
				if(person.getLastName().contains(p.getLastName())) {
					MedicalRecords medicalRecords = personsRepository.findPersonsInfo(person);
					if(medicalRecords != null) {
						persons.add(addPersonInfo(p, medicalRecords));
					}else{
						throw new ParameterIsNullException(ParameterIsNullException);
					}
				}

			}
			return persons;



	}

	/**
	 * this method get all the emails of persons that are living in the same city put in parameter.
	 * @param city represents the city where we have to get all the emails ofn persons from.
	 * @return p that represent all the emails of persons that are living in the city given in parameter.
	 * @throws NotFoundException if the list of person is empty.
	 */
	public PersonsEmail getPersonsEmail(String city) throws NotFoundException {
		log.info("Function : getPersonsEmail");
		SafetyAlerts safetyAlerts = personsRepository.getSafety();
		PersonsEmail p = new PersonsEmail();
			List<String> listEmail = new ArrayList<>();
			for(Persons person : safetyAlerts.getPersons()){
				if(safetyAlerts.getPersons().isEmpty()){
					throw new NotFoundException(ListIsNullException);
				}
				if(person.getCity().contains(city)) {
					listEmail.add(person.getEmail());
				}
			}
			p.setEmail(listEmail);
			return p;


	}

	/**
	 * this method get all the person's mobile that are living near the station put in parameter.
	 * @param station represents the station of a firestation.
	 * @return p that represent a list of all the mobile of persons living near that station.
	 * @throws NotFoundException if the list of firestations is empty.
	 * @throws NotFoundException if the list of persons is empty.
	 */
	public PersonsMobile getPersonsMobile(int station) throws NotFoundException {
		log.info("Function : getPersonsMobile");
		SafetyAlerts safetyAlerts = personsRepository.getSafety();
		PersonsMobile p = new PersonsMobile();
        List<String> mobileList = new ArrayList<>();
        List<String> addressList = new ArrayList<>();
        for(Firestations firestation : safetyAlerts.getFirestations()){
			if(safetyAlerts.getFirestations().isEmpty()){
				throw new NotFoundException(ListIsNullException);
			}
            if(firestation.getStation() == station){
                addressList.add(firestation.getAddress());
            }

        }
        for(Persons person : safetyAlerts.getPersons()){
			if(safetyAlerts.getPersons().isEmpty()){
				throw new NotFoundException(ListIsNullException);
			}
            for(String address : addressList) {
                if (person.getAddress().contains(address)) {
                    mobileList.add(person.getPhone());
                }
            }
        }
        p.setMobile(mobileList);
        return p;

    }

	/**
	 * this method get all the info of a person from the medicalRecord and person put in parameter.
	 * @param medicalRecord represents the medicalRecord of a person.
	 * @return period.getYears() represent the age of a person from the birthdate on the medicalRecord.
	 * @throws NotFoundException if medicalRecord given in parameter is null.
	 */
	public int addAge(MedicalRecords medicalRecord) throws NotFoundException {
		log.info("Function : addAge");
		if(medicalRecord != null) {
			LocalDate curDate = LocalDate.now();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("LL/dd/yyyy", Locale.FRANCE);
			LocalDate birthdate = LocalDate.parse(medicalRecord.getBirthdate(), dtf);
			Period period = Period.between(birthdate, curDate);
			return period.getYears();
		}else{
			throw new NotFoundException(MedicalRecordNotFoundException);
		}
	}

	/**
	 * this method get all a person's family member.
	 * @param safetyAlerts represents the data stored.
	 * @param nameChild represents the lastName of a person.
	 * @param firstName represents the firstName of a person.
	 * @return membersList is a list that represent the person's family member.
	 */
	public List<Persons> addMembre(SafetyAlerts safetyAlerts, String nameChild, String firstName){
		log.info("Function : addMembre");
		List<Persons> membresList = new ArrayList<>();
		for (Persons membre : safetyAlerts.getPersons()) {
			if (membre.getLastName().contains(nameChild) && membre.getFirstName() != firstName) {
				membresList.add(membre);
			}
		}
		return membresList;
	}

	/**
	 * this method  put in parameter.
	 * @param personsList represents a list of persons.
	 * @param ageList represents a list of age of persons.
	 * @param membresList represents a list of a person's family member.
	 * @return enfantsList is a list of child.
	 * @throws ParameterIsNullException if the lists given in parameter are null.
	 */
	public List<ChildAlerts> addChild(List<Persons> personsList, List<Integer> ageList, List<Persons> membresList) throws ParameterIsNullException {
		log.info("Function : addChild");
		List<ChildAlerts> enfantsList = new ArrayList<>();
		int count = 0;
		if(personsList == null || ageList == null || membresList == null){
			throw new ParameterIsNullException(ParameterIsNullException);
		}
		for (Persons persons : personsList) {
			enfantsList.add(new ChildAlerts(persons.getLastName(), persons.getFirstName(), ageList.get(count), membresList));
			count++;
		}
		return enfantsList;
	}

	/**
	 * this method get all the child and their family members from the address put in parameter.
	 * @param address represents an address.
	 * @return a list of child and their family members.
	 * @throws ParameterIsNullException if the address in parameter is null.
	 * @throws NotFoundException if the list of persons is empty.
	 * @throws NotFoundException if the list of medical record is empty.
	 */
	public List<ChildAlerts> getChildByAddress(String address) throws NotFoundException, ParameterIsNullException {
		log.info("Function : getChildByAddress");
		SafetyAlerts safetyAlerts = personsRepository.getSafety();
		List<Integer> ageList = new ArrayList<>();
		List<Persons> membresList = new ArrayList<>();

		List<Persons> personsList = new ArrayList<>();
		String nameChild = null;
		String firstName = null;
		int majorite = 18;
		if (address != null) {
			int count = 0;
			if(safetyAlerts.getPersons().isEmpty()){
				throw new NotFoundException(ListIsNullException);
			}
			for (Persons person : safetyAlerts.getPersons()) {

				if (person.getAddress().contains(address)) {
					if(safetyAlerts.getMedicalrecords().isEmpty()){
						throw new NotFoundException(ListIsNullException);
					}
					MedicalRecords medicalRecord = safetyAlerts.getMedicalrecords().get(count);


					int age = addAge(medicalRecord);
					System.out.println("Age : "+ medicalRecord.getBirthdate());
					if (age <= majorite) {
						System.out.println("Persons : "+ person.getFirstName());
						ageList.add(age);
						personsList.add(person);
						nameChild = person.getLastName();
						firstName = person.getFirstName();

					}
					if (nameChild != null) {
						membresList = addMembre(safetyAlerts, nameChild, firstName);
					}

				}
				count++;
			}

			return addChild(personsList, ageList, membresList);

		} else {
			throw new ParameterIsNullException(ParameterIsNullException);
		}
	}


}
