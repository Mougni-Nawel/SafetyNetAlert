package SafetyNetAlert.Repository;

import java.util.List;

import Config.Generated;
import SafetyNetAlert.DTO.FireAddress;
import SafetyNetAlert.DTO.FirestationByStation;
import SafetyNetAlert.DTO.Flood;
import SafetyNetAlert.Model.Firestations;
import SafetyNetAlert.Model.SafetyAlerts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
/**
 * methods related to the repository of firestation
 * @author Mougni
 *
 */
@Repository
@Generated
public class FirestationsRepository implements IRepository {
	
	@Autowired
	MedicalRecordsRepository medicalRecordsRepository;

	/**
	 * this method get a firestation's data from abstract repository.
	 * @return data stored from abstract repository.
	 */
	public SafetyAlerts getSafety(){
		return AbstractRepository.getSafety();
	}

	/**
	 * this method get all firestations from where they are stored.
	 * @return a list of firestations.
	 */
	@Override
	public List<Firestations> findAll() {
		// TODO Auto-generated method stub
		return AbstractRepository.safety.getFirestations();
	}

	/**
	 * this method save the firestation that is put in parameter
	 * @param firestation represents the firestation passed on from the service that has to be saved.
	 * @return firestationAdded represents the firestation saved.
	 */
	@Override
	public <Firestations> Firestations save(Firestations firestation) {
		// TODO Auto-generated method stub
		//return null;
		if(firestation!= null){
			AbstractRepository.safety.getFirestations().add((SafetyNetAlert.Model.Firestations) firestation);
			int index = AbstractRepository.safety.getFirestations().size();
			Firestations firestationAdded = (Firestations) AbstractRepository.safety.getFirestations().get(index--);
			return firestationAdded;
		}else{
			return null;
		}


	}

	/**
	 * this method get a firestation from address that is put in parameter
	 * @param address represents the address of the firestation that has to be found.
	 * @return p that represents the firestation found.
	 */
	//@Override
	public <Firestations> Firestations findByIds(String address) {
		// TODO Auto-generated method stub
		Firestations p = null;
		for(int i = 0; i < AbstractRepository.safety.getFirestations().size(); i++){
			if(AbstractRepository.safety.getFirestations().get(i).getAddress().contains(address)){
				p = (Firestations) AbstractRepository.safety.getFirestations().get(i);
			}
		}
		return p;
	}

	/**
	 * this method remove a firestation from address that is put in parameter
	 * @param address represents the address of the firestation that has to be removed.
	 */
	//@Override
	public <T> void deleteByIds(String address) {
		// TODO Auto-generated method stub
		Firestations firestations = findByIds(address);
		if(firestations != null){
			int index = AbstractRepository.safety.getFirestations().indexOf(firestations);
			AbstractRepository.safety.getFirestations().remove(index);
		}
		
	}

	/**
	 * this method update a firestation from address that is put in parameter.
	 * @param firestation represents the new firestation that has to replace the old one.
	 * @param address represents the address of the firestation that has to be updated.
	 */
	public void update(Firestations firestation, String address) {
		Firestations firestationFound = findByIds(address);
		if(firestationFound != null){
			int index = AbstractRepository.safety.getFirestations().indexOf(firestationFound);
			AbstractRepository.safety.getFirestations().set(index, firestation);
			System.out.println(index);
		}
	}

	public FireAddress getPersonsFromAddress(String address) {
		return null;
	}

	public FirestationByStation getPersonsFromStation(int station) {
		return null;
	}

	public List<Flood> getHearthByStations(List<Integer> stationsList) {
		return null;
	}
}
