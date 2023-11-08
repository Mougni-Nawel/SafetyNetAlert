package SafetyNetAlert.service;

import SafetyNetAlert.controller.exception.*;
import SafetyNetAlert.dto.ChildAlerts;
import SafetyNetAlert.dto.PersonInfo;
import SafetyNetAlert.dto.PersonsEmail;
import SafetyNetAlert.dto.PersonsMobile;
import SafetyNetAlert.model.Persons;

import java.util.List;

/**
 * methods related to person that has to be implemented in the class service.
 * @author Mougni
 *
 */
public interface IPersonService {

    public List<Persons> getAllPersons() throws NotFoundException;
    public Persons savePerson(Persons person);
    public Persons getPersons(final String firstName, final String lastName) throws NotFoundException;
    public void deleteAccount(String firstName, String lastName) throws NotFoundException;
    public Persons updatePerson(Persons person, String firstName, String lastName) throws NotFoundException;
    public List<PersonInfo> getPersonsInfo(String firstName, String lastName) throws NotFoundException, ParameterIsNullException;
    public PersonsEmail getPersonsEmail(String city) throws NotFoundException;
    public PersonsMobile getPersonsMobile(int station) throws NotFoundException;
    public List<ChildAlerts> getChildByAddress(String address) throws NotFoundException,ParameterIsNullException;


}
