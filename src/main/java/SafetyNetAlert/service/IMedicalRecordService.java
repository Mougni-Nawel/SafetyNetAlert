package SafetyNetAlert.service;

import SafetyNetAlert.controller.exception.*;
import SafetyNetAlert.model.MedicalRecords;

import java.util.List;

/**
 * methods related to medical record that has to be implemented in the class service.
 * @author Mougni
 *
 */
public interface IMedicalRecordService {

    public List<MedicalRecords> getAllMedicalRecords();
    public MedicalRecords saveMedicalRecords(MedicalRecords medicalRecords);
    public MedicalRecords getMedicalRecords(final String firstName, final String lastName) throws NotFoundException;
    public MedicalRecords updateMedicalRecords(MedicalRecords medicalRecords, String firstName, String lastName) throws NotFoundException;
    public void deleteAccount(String firstName, String lastName) throws NotFoundException;

}
