package SafetyNetAlert.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import SafetyNetAlert.config.Generated;
import SafetyNetAlert.dto.FireAddress;
import SafetyNetAlert.dto.FirestationByStation;
import SafetyNetAlert.dto.Flood;
import SafetyNetAlert.dto.PersonInfoFireAddress;
import SafetyNetAlert.model.Firestations;
import SafetyNetAlert.model.MedicalRecords;
import SafetyNetAlert.model.Persons;
import SafetyNetAlert.model.SafetyAlerts;
import SafetyNetAlert.repository.FirestationsRepository;
import SafetyNetAlert.repository.MedicalRecordsRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import SafetyNetAlert.controller.exception.*;

/**
 * methods related to firestation that are implemented from firestation interface.
 * @author Mougni
 *
 */
@Service
@Data
@Slf4j
public class FirestationsService implements IFirestationService{

	@Autowired
	FirestationsRepository firestationsRepository;

	@Autowired
	MedicalRecordsRepository medicalRecordsRepository;

	final String DATE_FORMAT ="LL/dd/yyyy";
	final String NotFoundException = "Firestation not found with this address";
	final String ParameterIsNullException = "L'addresse n'est pas trouvé";
	final String ListNotFoundException = "The list of station is empty or not found";


	/**
	 * this method get all the firestations.
	 * @return all the firestations registered.
	 */
	public List<Firestations> getAllFirestations(){
		log.info("Function : getAllFirestations");
		return firestationsRepository.findAll();
	}

	/**
	 * this method save the firestation put in parameter.
	 * @param firestations represents the firestation that has to be saved
	 * @return firestationsAdded that reprensents the firestation saved.
	 */
	public Firestations saveFirestations(Firestations firestations) {
		log.info("Function : saveFirestations");
		Firestations firestationsAdded = firestationsRepository.save(firestations);
		return firestationsAdded;
	}

	/**
	 * this method get the firestation from the address put in parameter.
	 * @param address represents the address of the firestation that has to be found.
	 * @return firestation that reprensents the firestation found.
	 */
    public Firestations getFirestations(final String address) throws NotFoundException {
		log.info("Function : getFirestations");
		Firestations firestation = firestationsRepository.findByIds(address);
    	if(firestation != null){
			return firestation;
		}else{
			throw new NotFoundException(NotFoundException);
		}
	}

	/**
	 * this method update a firestation from the address and the firestation updated put in parameter.
	 * @param address represents the address of the firestation that has to be updated.
	 * @param firestation represents the new version of firestation that has to be replcace the old firestation.
	 * @return currentFirestation that reprensents the firestation updated.
	 */
	public Firestations updateFirestations(String address, Firestations firestation) throws NotFoundException {
		log.info("Function : updateFirestations");
		Firestations e = getFirestations(address);
		Firestations currentFirestation = null;
		if(e != null) {
			currentFirestation = e;

			int number = firestation.getStation();
			if(number != 0) {
				currentFirestation.setStation(number);
			}

			firestationsRepository.update(firestation, address);
			return currentFirestation;
		}else{
			throw new NotFoundException(NotFoundException);
		}
	}


	/**
	 * this method remove a firestation from the address put in parameter.
	 * @param address represents the address of the firestation that has to be removed.
	 * @throws NotFoundException if there is no firestation found with this address.
	 */
	public void deleteFirestation(String address) throws NotFoundException {
		log.info("Function : deleteFirestation");
		Firestations firestations = firestationsRepository.findByIds(address);
		if(firestations != null){
			firestationsRepository.deleteByIds(address);
		}else{
			throw new NotFoundException(NotFoundException);
		}
	}



	// route supplementaires

	/**
	 * this method get all stations from the address and the safetyAlerts put in parameter.
	 * @param address represents the address of a person.
	 * @param safetyAlerts represents where the data of firestation is store.
	 * @return station that reprensents a list of stations.
	 */
	public List<Integer> addStation(SafetyAlerts safetyAlerts, String address) {
		log.info("Function : addStation");
		List<Integer> station = new ArrayList<Integer>();
		for (Firestations firestation : safetyAlerts.getFirestations()) {
			if(firestation.getAddress().contains(address)){
				// get stations
				station.add(firestation.getStation());
				// get persons
			}
		}
		return station;
	}

	/**
	 * this method fill with residents the list that is put in parameter.
	 * @param person represents the person that is needed to check if the address in parameter is the same with the person.
	 * @param safetyAlerts represents where the data is store.
	 * @param address represents the address of resident.
	 * @param medications represents a list of medications of a person.
	 * @param allergies represents a list of allergies of a person.
	 * @param habitants represents a list of residents.
	 */
	public void addHabitant(Persons person, SafetyAlerts safetyAlerts, String address, List<String> medications, List<String> allergies, List<PersonInfoFireAddress> habitants){
		log.info("Function : addHabitant");
		int age = 0;
		if(person.getAddress().contains(address)){
			String lastName = person.getLastName();
			String phone = person.getPhone();
			for (MedicalRecords medical : safetyAlerts.getMedicalrecords()) {
				if(medical.getLastName().contains(lastName) && medical.getFirstName().contains(person.getFirstName())){
					LocalDate curDate = LocalDate.now();
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT, Locale.FRANCE);
					LocalDate birthdate = LocalDate.parse(medical.getBirthdate(), dtf);
					Period period = Period.between(birthdate, curDate);
					age = period.getYears();
					medications.add(medical.getMedications().toString());
					allergies.add(medical.getAllergies().toString());
				}
			}

			habitants.add(new PersonInfoFireAddress(lastName, phone, age, medications, allergies));
		}
	}

	/**
	 * this method get persons from the address that is put in parameter.
	 * @param address represents the address of resident.
	 * @return fireadress that is of type {@link FireAddress}.
	 * @throws ParameterIsNullException if address is null.
	 */
	public FireAddress getPersonsFromAddress(String address) throws ParameterIsNullException {
		log.info("Function : getPersonsFromAddress");
		SafetyAlerts safetyAlerts = firestationsRepository.getSafety();
		List<String> medications = new ArrayList<>();
		List<String> allergies = new ArrayList<String>();
		List<Integer> station;
		FireAddress fireadress = new FireAddress();
		List<PersonInfoFireAddress> habitants = new ArrayList<PersonInfoFireAddress>();
		if(address != null){
			station = addStation(safetyAlerts, address);

			for (Persons person : safetyAlerts.getPersons()) {
// get Persons
				addHabitant(person, safetyAlerts, address, medications, allergies, habitants);
			}

			fireadress.setHabitants(habitants);
			fireadress.setStation(station);

			return fireadress;
		}else{
			throw new ParameterIsNullException("L'addresse n'est pas trouvé");
		}


	}

	/**
	 * this method get the age of a person from the address list that is put in parameter and fill the list with residents.
	 * @param addressList represents a list of address of residents.
	 * @param person represents the person that is needed to check if one of the address in the list in parameter is the same with the person.
	 * @param habitant represents a list of residents of type {@link Persons}.
	 * @param firestationByStation is of type {@link FirestationByStation}.
	 * @return habitant is a list of type {@link Persons}.
	 */
	public List<Persons> getAgeFromPersons(List<String> addressList, Persons person, List<Persons> habitant, FirestationByStation firestationByStation) throws NotFoundException {
		log.info("Function : getAgeFromPersons");
		for(String address : addressList) {
			if (person.getAddress().contains(address)) {

				habitant.add(person);

				LocalDate curDate = LocalDate.now();
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT, Locale.FRANCE);
				MedicalRecords medicalRecords = medicalRecordsRepository.findByIds(person.getFirstName(), person.getLastName());
				LocalDate birthdate = LocalDate.parse(medicalRecords.getBirthdate(), dtf);
				LocalDate localDate = curDate;
				LocalDate majorite = localDate.minusYears( 19 );

				if(birthdate.isAfter(majorite)){
					firestationByStation.setEnfant(firestationByStation.getEnfant()+1);
				}else{
					firestationByStation.setAdulte(firestationByStation.getAdulte()+1);
				}

			}
		}
		return habitant;

	}

	/**
	 * this method get persons from the station number of a firestation that is put in parameter.
	 * @param station represents the station number of a firestation.
	 * @return firestationByStation is of type {@link FirestationByStation}.
	 * @throws NotFoundException if no firestation found with this station
	 */
	public FirestationByStation getPersonsFromStation(int station) throws NotFoundException {
		log.info("Function : getPersonsFromStation");
		SafetyAlerts safetyAlerts = firestationsRepository.getSafety();
		FirestationByStation firestationByStation = new FirestationByStation();
		List<Persons> habitant = new ArrayList<>();
		List<String> addressList = new ArrayList<>();

		if(station != 0){
			for(Firestations firestation : safetyAlerts.getFirestations()){
				if(firestation.getStation() == station && !addressList.contains(firestation.getAddress())){
					addressList.add(firestation.getAddress());
				}
			}
			for(Persons person : safetyAlerts.getPersons()){
				getAgeFromPersons(addressList, person, habitant, firestationByStation);
			}
			firestationByStation.setHabitants(habitant);
			return firestationByStation;

		}else{
			throw new NotFoundException(NotFoundException);
		}

	}

	/**
	 * this method fill of address in a list given in parameter from the station number.
	 * @param safetyAlerts represents where the data is stored.
	 * @param addressList represents a list of address.
	 * @param station represents the station nulber of a firestation.
	 */
	public void addAddressList(SafetyAlerts safetyAlerts, List<String> addressList, int station) {
		log.info("Function : addAddressList");
		for(Firestations firestation : safetyAlerts.getFirestations()) {
			if(firestation.getStation() == station && !addressList.contains(firestation.getAddress())){
				addressList.add(firestation.getAddress());
			}

		}
	}

	/**
	 * this method add to a list of the object flood from the address list that is put in parameter.
	 * @param safetyAlerts represents where the data is stored.
	 * @param addressList represents a list of address of residents.
	 * @param person represents the person that is needed to check if one of the address in the list in parameter is the same with the person.
	 * @param count represents a counter.
	 * @param medications represents a list of medications of a person.
	 * @param allergies represents a list of allergies of a person.
	 * @param floods represents a list of type {@link Flood}.
	 */
	public void addFlood(SafetyAlerts safetyAlerts, List<String> addressList, Persons person, int count, List<String> medications, List<String> allergies, List<Flood> floods){
		log.info("Function : addFlood");
		for(String address : addressList) {
			if (person.getAddress().contains(address)) {
				String address1 = person.getAddress();
				String phone = person.getPhone();
				String lastName = person.getLastName();
				LocalDate curDate = LocalDate.now();
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT, Locale.FRANCE);
				MedicalRecords medicalRecords = safetyAlerts.getMedicalrecords().get(count);
				LocalDate birthdate = LocalDate.parse(medicalRecords.getBirthdate(), dtf);
				Period period = Period.between(birthdate, curDate);
				int age = period.getYears();

				if (medicalRecords.getLastName().contains(lastName)) {
					medications.add(medicalRecords.getMedications().toString());
					allergies.add(medicalRecords.getAllergies().toString());
					List<String> medicalRecordsList = new ArrayList<>();
					medicalRecordsList.add(medicalRecords.getMedications().toString());
					medicalRecordsList.add(medicalRecords.getAllergies().toString());
					floods.add(new Flood(address1, lastName, phone, age, medicalRecordsList));
				}


			}
		}
	}

	/**
	 * this method get the flood from the stations list that is put in parameter.
	 * @param stationsList represents a list of stations of firestations.
	 * @return floods is a list of type {@link Flood}.
	 * @throws NotFoundException if station list is empty or null.
	 */
    public List<Flood> getHearthByStations(List<Integer> stationsList) throws NotFoundException {
		log.info("Function : getHearthByStations");
		SafetyAlerts safetyAlerts = firestationsRepository.getSafety();
		List<String> addressList = new ArrayList<String>();
		List<String> medications = new ArrayList<>();
		List<String> allergies = new ArrayList<String>();

		List<Flood> floods = new ArrayList<>();

		if(stationsList != null || stationsList.isEmpty()){
			for(int station : stationsList){
				addAddressList(safetyAlerts, addressList, station);
				int count = 0;
				for(Persons person : safetyAlerts.getPersons()) {
					addFlood(safetyAlerts, addressList, person, count, medications, allergies , floods);
					count++;
				}
			}
			return floods;
		}else{
			throw new NotFoundException(ListNotFoundException);
		}

    }

}
