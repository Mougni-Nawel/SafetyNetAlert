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
    public Firestations getFirestations(final String address) throws FirestationNotFoundException;
    public Firestations updateFirestations(String address, Firestations firestation) throws FirestationNotFoundException;
    public void deleteFirestation(String address) throws FirestationNotFoundException;
    public FireAddress getPersonsFromAddress(String address) throws AddressInParameterIsNullException;
    public FirestationByStation getPersonsFromStation(int station) throws FirestationStationNotFoundException, MedicalRecordsNotFoundException;
    public List<Flood> getHearthByStations(List<Integer> stationsList) throws FirestationNotFoundException;


}
