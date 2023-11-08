package SafetyNetAlert.service;

import java.util.List;
import SafetyNetAlert.model.MedicalRecords;
import SafetyNetAlert.repository.MedicalRecordsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import SafetyNetAlert.controller.exception.*;

/**
 * methods related to medical record that are implemented from medical record interface.
 * @author Mougni
 *
 */
@Service
@Slf4j
public class MedicalRecordsService implements IMedicalRecordService{

	@Autowired(required = false)
	MedicalRecordsRepository medicalRecordsRepository;


	final String NotFoundException = "MedicalRecords not found with this name";

	/**
	 * this method get all the medical records.
	 * @return all the medical records registered.
	 */
	public List<MedicalRecords> getAllMedicalRecords(){
		log.info("Function : getAllMedicalRecords");
		return medicalRecordsRepository.findAll();
	}

	/**
	 * this method save the medical records put in parameter.
	 * @param medicalRecords represents the medical record that has to be saved
	 * @return medicalRecordAdded that reprensents the medical record saved.
	 */
	public MedicalRecords saveMedicalRecords(MedicalRecords medicalRecords) {
		log.info("Function : saveMedicalRecords");
		MedicalRecords medicalRecordAdded = medicalRecordsRepository.save(medicalRecords);
		return medicalRecordAdded;
	}

	/**
	 * this method get the medicalRecord from the firstname and lastname put in parameter.
	 * @param firstName represents the firstname registered on the medical record that has to be found.
	 * @param lastName represents the lastname registered on the medical record that has to be found.
	 * @return medicalRecord that reprensents the medical record found.
	 * @throws NotFoundException if there is no medical record found with this name.
	 */
	public MedicalRecords getMedicalRecords(final String firstName, final String lastName) throws NotFoundException {
		log.info("Function : getMedicalRecords");
		MedicalRecords medicalRecord = medicalRecordsRepository.findByIds(firstName, lastName);
		if(medicalRecord != null){
			return medicalRecord;
		}else{
			throw new NotFoundException(NotFoundException);
		}
	}

	/**
	 * this method update a medical record from the firstname and the lastname and the medical record updated put in parameter.
	 * @param firstName represents the firstname registered on the medical record that has to be updated.
	 * @param lastName represents the lastname registered on the medical record that has to be updated.
	 * @param medicalRecords represents the new version of medical record that has to be replace the old medical record.
	 * @return currentMedicalRecords that reprensents the medical record updated.
	 * @throws NotFoundException if there is no medical record found with this name.
	 */
	public MedicalRecords updateMedicalRecords(MedicalRecords medicalRecords, String firstName, String lastName) throws NotFoundException {
		log.info("Function : updateMedicalRecords");
		MedicalRecords e = getMedicalRecords(firstName, lastName);
		MedicalRecords currentMedicalRecords = null;
		if(e != null){
			currentMedicalRecords = e;

			List<String> medications = medicalRecords.getMedications();
			if (medications != null) {
				currentMedicalRecords.setMedications(medications);
			}
			List<String> allergies = medicalRecords.getAllergies();
			if (allergies != null) {
				medicalRecords.setAllergies(allergies);
			}
			String birthday = medicalRecords.getBirthdate();
			if (birthday != null) {
				currentMedicalRecords.setBirthdate(birthday);
			}

			medicalRecordsRepository.update(medicalRecords, firstName, lastName);
			return currentMedicalRecords;
		}else{
			throw new NotFoundException(NotFoundException);
		}

	}

	/**
	 * this method remove a medical record from the firstname and the lastname put in parameter.
	 * @param firstName represents the firstname registered on the medical record that has to be removed.
	 * @param lastName represents the lastname registered on the medical record that has to be removed.
	 * @throws NotFoundException if there is no medicalRecord found with this name.
	 */
	public void deleteAccount(String firstName, String lastName) throws NotFoundException {
		log.info("Function : deleteAccount");
		MedicalRecords medicalRecord = medicalRecordsRepository.findByIds(firstName, lastName);
		if(medicalRecord != null){
			medicalRecordsRepository.deleteByIds(firstName, lastName);
		}else{
			throw new NotFoundException(NotFoundException);
		}
	}

}
