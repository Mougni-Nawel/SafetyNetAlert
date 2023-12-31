package SafetyNetAlert.repository;

import java.util.List;

import SafetyNetAlert.config.Generated;
import SafetyNetAlert.controller.exception.*;
import SafetyNetAlert.model.MedicalRecords;
import SafetyNetAlert.model.SafetyAlerts;
import org.springframework.stereotype.Repository;

/**
 * methods related to the repository of medicalRecord
 * @author Mougni
 *
 */
@Repository
@Generated
public class MedicalRecordsRepository implements IRepository{

	/**
	 * this method get a medical record's data from abstract repository.
	 * @return data stored from abstract repository.
	 */
	public SafetyAlerts getSafety(){
		return AbstractRepository.getSafety();
	}

	/**
	 * this method get all medical record from where the data is stored.
	 * @return list of medical record.
	 */
	@Override
	public List<MedicalRecords> findAll() {
		// TODO Auto-generated method stub
		return AbstractRepository.safety.getMedicalrecords();
	}

	/**
	 * this method save a medical record from firstname and lastname that is put in parameter.
	 * @param medicalRecords represents the medical record passed from the service that has to be added.
	 * @return medicalRecordAdded that represents the medical record saved.
	 */
	@Override
	public <MedicalRecords> MedicalRecords save(MedicalRecords medicalRecords) {
		// TODO Auto-generated method stub
		if(medicalRecords != null) {
			AbstractRepository.safety.getMedicalrecords().add((SafetyNetAlert.model.MedicalRecords) medicalRecords);
			//return AbstractRepository.safety.getMedicalrecords();
			int index = AbstractRepository.safety.getMedicalrecords().size();
			MedicalRecords medicalRecordAdded = (MedicalRecords) AbstractRepository.safety.getMedicalrecords().get(index--);
			return medicalRecordAdded;
		}else {
			return null;
		}
	}

	/**
	 * this method get a medicalRecord from firstname and lastname that is put in parameter
	 * @param firstName represents the firstname of the medical record that has to be found.
	 * @param lastName represents the lastName of the medical record that has to be found.
	 * @return p that represents the medical record found.
	 */
	public MedicalRecords findByIds(String firstName, String lastName) throws NotFoundException {

		SafetyAlerts safetyAlerts = getSafety();
		// TODO Auto-generated method stub
		MedicalRecords p = null;
		for(SafetyNetAlert.model.MedicalRecords medicalRecord : safetyAlerts.getMedicalrecords()){
			if(medicalRecord.getFirstName().contains(firstName) && medicalRecord.getLastName().contains(lastName)){
				p = medicalRecord;
				if(!medicalRecord.getFirstName().contains(firstName)){
					throw new NotFoundException("MedicalRecords not found with this firstname");
				}else if(!medicalRecord.getLastName().contains(lastName)){
					throw new NotFoundException("MedicalRecords not found with this lastname");
				}
			}
		}
		return p;
	}

	/**
	 * this method remove a medical record from firstname and lastname that is put in parameter.
	 * @param firstName represents the firstname of the medical record that has to be removed.
	 * @param lastName represents the lastName of the medical record that has to be removed.
	 */
	public void deleteByIds(String firstName, String lastName) throws NotFoundException {
		// TODO Auto-generated method stub
		MedicalRecords medicalRecords = findByIds(firstName, lastName);
		if(medicalRecords != null){
			int index = AbstractRepository.safety.getMedicalrecords().indexOf(medicalRecords);
			AbstractRepository.safety.getMedicalrecords().remove(index);
		}
	}

	/**
	 * this method update a medical record from firstname and lastname that is put in parameter.
	 * @param firstName represents the firstname of the medical record that has to be updated.
	 * @param lastName represents the lastName of the medical record that has to be updated.
	 * @param medicalRecords represents the new medical record that has to replace the old one.
	 */
	public void update(MedicalRecords medicalRecords, String firstName, String lastName) throws NotFoundException {
		MedicalRecords medicalRecordsFound = findByIds(firstName, lastName);
		if(medicalRecordsFound != null){
			int index = AbstractRepository.safety.getMedicalrecords().indexOf(medicalRecordsFound);
			AbstractRepository.safety.getMedicalrecords().set(index, (MedicalRecords) medicalRecords);
			System.out.println(index);
		}
	}
}
