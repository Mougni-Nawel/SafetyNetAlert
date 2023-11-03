package SafetyNetAlert.Service;

import SafetyNetAlert.Controller.Exception.*;
import SafetyNetAlert.Model.MedicalRecords;
import javax.naming.NameNotFoundException;
import java.util.List;

/**
 * methods related to medical record that has to be implemented in the class service.
 * @author Mougni
 *
 */
public interface IMedicalRecordService {

    public List<MedicalRecords> getAllMedicalRecords();
    public MedicalRecords saveMedicalRecords(MedicalRecords medicalRecords);
    public MedicalRecords getMedicalRecords(final String firstName, final String lastName) throws MedicalRecordsNotFoundException;
    public MedicalRecords updateMedicalRecords(MedicalRecords medicalRecords, String firstName, String lastName) throws NameNotFoundException, MedicalRecordsNotFoundException;
    public void deleteAccount(String firstName, String lastName) throws NameNotFoundException, MedicalRecordsNotFoundException;

}
