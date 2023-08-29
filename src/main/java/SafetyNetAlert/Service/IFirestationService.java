package SafetyNetAlert.Service;

import SafetyNetAlert.Controller.Exception.*;
import SafetyNetAlert.DTO.FireAddress;
import SafetyNetAlert.DTO.FirestationByStation;
import SafetyNetAlert.DTO.Flood;
import SafetyNetAlert.Model.Firestations;

import java.util.List;

/**
 * methods related to firestations that has to be implemented in the class service.
 * @author Mougni
 *
 */
public interface IFirestationService {

    public List<Firestations> getAllFirestations();
    public Firestations saveFirestations(Firestations firestations);
    public Firestations getFirestations(final String address) throws FirestationNotFoundByAddressException;
    public Firestations updateFirestations(String address, Firestations firestation) throws FirestationNotFoundByAddressException;
    public void deleteFirestation(String address) throws FirestationNotFoundByAddressException;
    public FireAddress getPersonsFromAddress(String address) throws AddressInParameterIsNullException;
    public FirestationByStation getPersonsFromStation(int station) throws FirestationStationNotFoundException, MedicalRecordLastnameNotFoundException, MedicalRecordFirstnameNotFoundException;
    public List<Flood> getHearthByStations(List<Integer> stationsList) throws FirestationListStationNotFoundException;


}
