package SafetyNetAlert.Service;

import SafetyNetAlert.Controller.Exception.*;
import SafetyNetAlert.DTO.ChildAlerts;
import SafetyNetAlert.DTO.PersonInfo;
import SafetyNetAlert.DTO.PersonsEmail;
import SafetyNetAlert.DTO.PersonsMobile;
import SafetyNetAlert.Model.Persons;
import javax.naming.NameNotFoundException;
import java.util.List;

/**
 * methods related to person that has to be implemented in the class service.
 * @author Mougni
 *
 */
public interface IPersonService {

    public List<Persons> getAllPersons() throws PersonsNotFoundException;
    public Persons savePerson(Persons person);
    public Persons getPersons(final String firstName, final String lastName) throws PersonsNotFoundException;
    public void deleteAccount(String firstName, String lastName) throws PersonsNotFoundException;
    public Persons updatePerson(Persons person, String firstName, String lastName) throws PersonsNotFoundException;
    public List<PersonInfo> getPersonsInfo(String firstName, String lastName) throws PersonsNotFoundException, MedicalRecordsNotFoundException, PersonInParameterIsNullException;
    public PersonsEmail getPersonsEmail(String city) throws PersonsNotFoundException;
    public PersonsMobile getPersonsMobile(int station) throws NameNotFoundException, FirestationNotFoundException, PersonsNotFoundException;
    public List<ChildAlerts> getChildByAddress(String address) throws MedicalRecordsNotFoundException, PersonsNotFoundException, ListIsNullException, AddressInParameterIsNullException;


}
