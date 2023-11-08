package SafetyNetAlert.controller;

import java.util.List;

import SafetyNetAlert.controller.exception.NotFoundException;
import SafetyNetAlert.service.IMedicalRecordService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import SafetyNetAlert.model.MedicalRecords;


/**
 * methods related to the controller of medical Record
 * @author Mougni
 *
 */

@RestController
@RequestMapping("/medicalRecord")
@Api
@Slf4j
public class MedicalRecordsController {

	
	@Autowired
	IMedicalRecordService medicalRecordsService;

	/**
	 * this method get all accounts from medical record service.
	 * @return list of medical record.
	 */
	@GetMapping()
	public List<MedicalRecords> getAllMedicalsRecords(){
		log.info("Function : getAllMedicalsRecords");
		return medicalRecordsService.getAllMedicalRecords();
	}


	/**
	 * this method get a medicalRecord from firstname and lastname from the path /medicalRecord/{firstName}/{lastName}
	 * @param firstName represents the firstname of the medical record that has to be found.
	 * @param lastName represents the lastName of the medical record that has to be found.
	 * @return ResponseEntity ok which represents a http response with code 200.
	 */
	@GetMapping("/{firstName}/{lastName}")
	public ResponseEntity<MedicalRecords> getMedicalRecords(@PathVariable("firstName") String firstName, @PathVariable("lastName") final String lastName) throws NotFoundException {
		log.info("Function : getMedicalRecords");
		MedicalRecords medicalRecords = medicalRecordsService.getMedicalRecords(firstName, lastName);
		return ResponseEntity.ok(medicalRecords);
	}

	/**
	 * this method add a medical record from firstname and lastname from the path /medicalRecord
	 * @param medicalRecords represents the medical record passed on the body of the http request that has to be added.
	 * @return RespsonseEntity created which represents a http response with code 201.
	 */
	@PostMapping()
	public ResponseEntity<MedicalRecords> addMedicalRecords(@RequestBody MedicalRecords medicalRecords) {
		log.info("Function : addMedicalRecords");
		MedicalRecords medicalRecordsAdded = medicalRecordsService.saveMedicalRecords(medicalRecords);
		return new ResponseEntity<>(medicalRecordsAdded, HttpStatus.CREATED);
	}

	/**
	 * this method update a medical record from firstname and lastname from the path /medicalRecord/{firstName}/{lastName}
	 * @param firstName represents the firstname of the medical record that has to be updated.
	 * @param lastName represents the lastName of the medical record that has to be updated.
	 * @param medicalRecords represents the new medical record that has to replace the old one.
	 * @return ResponseEntity accepted which represents a http response with code 202.
	 */
	@PutMapping("/{firstName}/{lastName}")
	public ResponseEntity<HttpStatus> updateMedicalRecords(@PathVariable("firstName") String firstName, @PathVariable("lastName") final String lastName, @RequestBody MedicalRecords medicalRecords) throws NotFoundException {
		log.info("Function : updateMedicalRecords");
		medicalRecordsService.updateMedicalRecords(medicalRecords,firstName, lastName);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	/**
	 * this method remove a medical record from firstname and lastname from the path /medicalRecord/{firstName}/{lastName}
	 * @param firstName represents the firstname of the medical record that has to be removed.
	 * @param lastName represents the lastName of the medical record that has to be removed.
	 * @return ResponseEntity accepted which represents a http response with code 202.
	 */
	@DeleteMapping("/{firstName}/{lastName}")
	public ResponseEntity<HttpStatus> deletePerson(@PathVariable("firstName") String firstName, @PathVariable("lastName") final String lastName) throws NotFoundException  {
		log.info("Function : deletePerson");
		medicalRecordsService.deleteAccount(firstName, lastName);
		return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
	}
}
