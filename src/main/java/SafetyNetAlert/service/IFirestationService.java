package SafetyNetAlert.service;

import SafetyNetAlert.controller.exception.*;
import SafetyNetAlert.dto.FireAddress;
import SafetyNetAlert.dto.FirestationByStation;
import SafetyNetAlert.dto.Flood;
import SafetyNetAlert.model.Firestations;

import java.util.List;

/**
 * methods related to firestations that has to be implemented in the class service.
 * @author Mougni
 *
 */
public interface IFirestationService {

    public List<Firestations> getAllFirestations();
    public Firestations saveFirestations(Firestations firestations);
    public Firestations getFirestations(final String address) throws NotFoundException;
    public Firestations updateFirestations(String address, Firestations firestation) throws NotFoundException;
    public void deleteFirestation(String address) throws NotFoundException;
    public FireAddress getPersonsFromAddress(String address) throws ParameterIsNullException;
    public FirestationByStation getPersonsFromStation(int station) throws NotFoundException;
    public List<Flood> getHearthByStations(List<Integer> stationsList) throws NotFoundException;


}
