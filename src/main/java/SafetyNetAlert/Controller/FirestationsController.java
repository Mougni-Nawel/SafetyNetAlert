package SafetyNetAlert.Controller;

import SafetyNetAlert.Controller.Exception.*;
import SafetyNetAlert.DTO.FireAddress;
import SafetyNetAlert.DTO.FirestationByStation;
import SafetyNetAlert.DTO.Flood;
import SafetyNetAlert.Model.Firestations;
import SafetyNetAlert.Service.IFirestationService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.NameNotFoundException;
import java.util.List;


/**
 * methods related to the controller of firestation
 * @author Mougni
 *
 */

@RestController
@RequestMapping("/firestation")
@Api
@Slf4j
public class FirestationsController {

	@Autowired
	IFirestationService firestationsService;

	/**
	 * this method get all firestations from firestation service.
	 * @return list of firestations.
	 */
	@GetMapping
	public List<Firestations> getAllFirestations(){
		log.info("Function : getAllFirestations");
		return firestationsService.getAllFirestations();
	}

	/**
	 * this method get a firestation from address from the path /firestation/{address}
	 * @param address represents the address of the firestation that has to be found.
	 * @return ResponseEntity ok which represents a http response with code 200.
	 */
	@GetMapping("/{address}")
	public ResponseEntity<Object> getFirestations(@PathVariable("address") final String address) throws FirestationNotFoundException {
		log.info("Function : getFirestations");
		Firestations firestation = firestationsService.getFirestations(address);
		return ResponseEntity.ok(firestation);
	}

	/**
	 * this method add a firestation from the path /firestation
	 * @param firestations represents the firestation passed on the body of the http request that has to be added.
	 * @return RespsonseEntity created which represents a http response with code 201.
	 */
	@PostMapping()
	public ResponseEntity<Firestations> addPerson(@RequestBody Firestations firestations) {
		log.info("Function : addPerson");
		Firestations firestationAdded = firestationsService.saveFirestations(firestations);
		return new ResponseEntity<>(firestationAdded, HttpStatus.CREATED);
}

	/**
	 * this method update a firestation from address from the path /firestation/{address}
	 * @param address represents the address of the firestation that has to be updated.
	 * @param firestation represents the new firestation that has to replace the old one.
	 * @return ResponseEntity accepted which represents a http response with code 202.
	 */
	@PutMapping("/{address}")
	public ResponseEntity<HttpStatus> updateFirestations(@PathVariable("address") final String address, @RequestBody Firestations firestation) throws FirestationNotFoundException {
		log.info("Function : updateFirestations");
		firestationsService.updateFirestations(address, firestation);
		return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
	}

	/**
	 * this method remove a firestation from address from the path /firestation/{address}
	 * @param address represents the address of the firestation that has to be removed.
	 * @return ResponseEntity accepted which represents a http response with code 202.
	 */
	@DeleteMapping("/{address}")
	public ResponseEntity<HttpStatus> deleteFirestation(@PathVariable("address") final String address) throws FirestationNotFoundException {
		log.info("Function : deleteFirestation");
		firestationsService.deleteFirestation(address);
		return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
	}

	// routes supplementaires

	/**
	 * this method get a fire from address from the path /firestation/fire/{address}
	 * @param address represents the address of the fire object that has to be found.
	 * @return ResponseEntity ok which represents a http response with code 200 with fireAddress result.
	 * @throws NameNotFoundException if the address given is not recorded with persons living in this address.
	 */
	@GetMapping("/fire/{address}")
	public ResponseEntity<FireAddress> getPersonInfo(@PathVariable("address") final String address) throws AddressInParameterIsNullException {
		log.info("Function : getPersonInfo");
		FireAddress fireAddress = firestationsService.getPersonsFromAddress(address);
		return ResponseEntity.ok(fireAddress);
	}

	/**
	 * this method get a stationNumber from station from the path /firestation/stationNumber/{station}
	 * @param station represents the station of the stationNumber object that has to be found.
	 * @return ResponseEntity ok which represents a http response with code 200 with firestationByStation result.
	 * @throws NameNotFoundException if the station given is not recorded with persons living near this station.
	 */
	@GetMapping("/stationNumber/{station}")
	public ResponseEntity<FirestationByStation> getPersonsByStation(@PathVariable("station") final int station) throws FirestationStationNotFoundException, MedicalRecordsNotFoundException {
		log.info("Function : getPersonsByStation");
		FirestationByStation firestationByStation = firestationsService.getPersonsFromStation(station);
		return ResponseEntity.ok(firestationByStation);
	}

	/**
	 * this method get a flood from a list of stations from the path /firestation/flood/{list_of_stations}
	 * @param stationsList represents a list of station number of the flood object that has to be found.
	 * @return ResponseEntity ok which represents a http response with code 200 with the flood result.
	 * @throws NameNotFoundException if the list of stations given is not recorded with persons living in this near these stations.
	 */
	@GetMapping("/flood/{list_of_stations}")
	public ResponseEntity<List<Flood>> getHearthByStations(@PathVariable("list_of_stations") final List<Integer> stationsList) throws FirestationNotFoundException {
		log.info("Function : getHearthByStations");
		List<Flood> flood = firestationsService.getHearthByStations(stationsList);
		return ResponseEntity.ok(flood);
	}

	

}
