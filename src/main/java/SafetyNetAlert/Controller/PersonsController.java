package SafetyNetAlert.Controller;

import java.util.List;

import SafetyNetAlert.Controller.Exception.*;
import SafetyNetAlert.DTO.ChildAlerts;
import SafetyNetAlert.DTO.PersonInfo;
import SafetyNetAlert.DTO.PersonsEmail;
import SafetyNetAlert.DTO.PersonsMobile;
import SafetyNetAlert.Model.Persons;
import SafetyNetAlert.Service.IPersonService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.naming.NameNotFoundException;


/**
 * methods related to the controller of person
 * @author Mougni
 *
 */

@RestController
@RequestMapping
@Api(value = "hello", description = "Sample hello world application")
@Slf4j
public class PersonsController {

	@Autowired
	IPersonService personsService;

	/**
	 * this method get all accounts from persons service.
	 * @return list of persons.
	 */
	@GetMapping("/person")
	public List<Persons> getAllAccount() throws PersonsNotFoundException {
		log.info("Function : getAllAccount");
		return personsService.getAllPersons();
	}

	/**
	 * this method get a person from firstname and lastname from the path /person/{firstName}/{lastName}
	 * @param firstName represents the firstname of the person that has to be found.
	 * @param lastName represents the lastName of the person that has to be found.
	 * @return ResponseEntity ok which represents a http response with code 200.
	 * @throws PersonsNotFoundException if the firstname and lastname given is not recorded with persons having this name.
	 */
	@GetMapping("/person/{firstName}/{lastName}")
	public ResponseEntity<Persons> getPerson(@PathVariable("firstName") final String firstName, @PathVariable("lastName") final String lastName) throws PersonsNotFoundException {
		log.info("Function : getPerson");
		Persons person = personsService.getPersons(firstName,lastName);
		return ResponseEntity.ok(person);
	}

	/**
	 * this method add a person from the person added in the body from the path /person
	 * @param person represents the person passed on the body of the http request that has to be added.
	 * @return RespsonseEntity created which represents a http response with code 201.
	 */
	@PostMapping(value = "/person", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Persons> addPerson(@RequestBody Persons person) {
		log.info("Function : addPerson");
		Persons personAdded = personsService.savePerson(person);
		return new ResponseEntity<>(personAdded, HttpStatus.CREATED);
	}

	/**
	 * this method update a person from firstname and lastname from the path /person/{firstName}/{lastName}
	 * @param firstName represents the firstname of the person that has to be updated.
	 * @param lastName represents the lastName of the person that has to be updated.
	 * @param person represents the new person that has to replace the old one.
	 * @return ResponseEntity accepted which represents a http response with code 202.
	 * @throws PersonsNotFoundException if the firstname and lastname given is not recorded with persons having this name.
	 */
	@PutMapping(value = "/person/{firstName}/{lastName}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Persons> updatePersons(@PathVariable("firstName") final String firstName, @PathVariable("lastName") final String lastName, @RequestBody Persons person) throws PersonsNotFoundException {
		log.info("Function : updatePersons");
		Persons personUpdated = personsService.updatePerson(person,firstName, lastName);
		return new ResponseEntity<>(personUpdated, HttpStatus.ACCEPTED);
	}

	/**
	 * this method remove a person from firstname and lastname from the path /person/{firstName}/{lastName}
	 * @param firstName represents the firstname of the person that has to be removed.
	 * @param lastName represents the lastName of the person that has to be removed.
	 * @return ResponseEntity accepted which represents a http response with code 202.
	 * @throws PersonsNotFoundException if the firstname and lastname given is not recorded with persons having this name.
	 */
	@DeleteMapping("/person/{firstName}/{lastName}")
	public ResponseEntity<HttpStatus> deletePerson(@PathVariable("firstName") final String firstName, @PathVariable("lastName") final String lastName) throws PersonsNotFoundException {
		log.info("Function : deletePerson");
		personsService.deleteAccount(firstName, lastName);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);

	}

	// routes supplementaires

	/**
	 * this method get a person from firstname and lastname from the path /personInfo/{firstName}/{lastName}
	 * @param firstName represents the firstname of the personInfo that has to be found.
	 * @param lastName represents the lastName of the personInfo that has to be found.
	 * @return ResponseEntity ok which represents a http response with code 200.
	 * @throws PersonsNotFoundException if the firstname and lastname given is not recorded with person having this name.
	 */
	@GetMapping("/personInfo/{firstName}/{lastName}")
	public ResponseEntity<List<PersonInfo>> getPersonInfo(@PathVariable("firstName") final String firstName, @PathVariable("lastName") final String lastName) throws PersonsNotFoundException, PersonInParameterIsNullException, MedicalRecordsNotFoundException {
		log.info("Function : getPersonInfo");
		List<PersonInfo> persons = personsService.getPersonsInfo(firstName,lastName);
		return ResponseEntity.ok(persons);
	}

	/**
	 * this method get a communityEmail from the city from the path /communityEmail/{city}
	 * @param city represents the city of the communityEmail object that has to be found.
	 * @return ResponseEntity ok which represents a http response with code 200 with the emails of the persons found.
	 * @throws PersonsNotFoundException if the city given is not recorded with persons living in this city.
	 */
	@GetMapping("/communityEmail/{city}")
	public ResponseEntity<PersonsEmail> getPersonEmails(@PathVariable("city") final String city) throws PersonsNotFoundException {
		log.info("Function : getPersonEmails");
		PersonsEmail persons = personsService.getPersonsEmail(city);
		return ResponseEntity.ok(persons);
	}

	/**
	 * this method get a phoneAlert from the station from the path /phoneAlert/{station}
	 * @param station represents the station of the phoneAlert object that has to be found.
	 * @return ResponseEntity ok which represents a http response with code 200.
	 * @throws NameNotFoundException if the station given is not recorded with persons living near this station.
	 */
	@GetMapping("/phoneAlert/{station}")
	public ResponseEntity<PersonsMobile> getPersonsMobile(@PathVariable("station") final int station) throws NameNotFoundException, PersonsNotFoundException, FirestationNotFoundException {
		log.info("Function : getPersonsMobile");
		PersonsMobile persons = personsService.getPersonsMobile(station);
		return ResponseEntity.ok(persons);
	}

	/**
	 * this method get a list of childAlert from the address from the path /childAlert/{address}
	 * @param address represents the address of the childAlert object that has to be found.
	 * @return ResponseEntity ok which represents a http response with code 200.
	 * @throws PersonsNotFoundException if the address given is not recorded with persons living in this address.
	 * @throws MedicalRecordsNotFoundException if the medical record is not found.
	 */
	@GetMapping("/childAlert/{address}")
	public ResponseEntity<List<ChildAlerts>> getChild(@PathVariable("address") final String address) throws PersonsNotFoundException, MedicalRecordsNotFoundException, AddressInParameterIsNullException, ListIsNullException {
		log.info("Function : getChild");
		List<ChildAlerts> childAlerts = personsService.getChildByAddress(address);
		return ResponseEntity.ok(childAlerts);
	}

}
