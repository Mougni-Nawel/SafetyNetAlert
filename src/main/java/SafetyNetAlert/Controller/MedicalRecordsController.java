package SafetyNetAlert.Controller;

import java.util.List;

import SafetyNetAlert.Service.IMedicalRecordService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import SafetyNetAlert.Model.MedicalRecords;


/**
 * methods related to the controller of medical Record
 * @author Mougni
 *
 */

//@JsonFormat
@RestController
@RequestMapping("/medicalRecord")
@Api
public class MedicalRecordsController {

//	@Autowired
//	MedicalRecordsRepository medicalRecordsRepository;
	
	@Autowired
	IMedicalRecordService medicalRecordsService;

	/**
	 * this method get all accounts from medical record service.
	 * @return list of medical record.
	 */
	@GetMapping()
	public List<MedicalRecords> getAllMedicalsRecords(){
		return medicalRecordsService.getAllMedicalRecords();
	}


	/**
	 * this method get a medicalRecord from firstname and lastname from the path /medicalRecord/{firstName}/{lastName}
	 * @param firstName represents the firstname of the medical record that has to be found.
	 * @param lastName represents the lastName of the medical record that has to be found.
	 * @return ResponseEntity ok which represents a http response with code 200.
	 */
	@GetMapping("/{firstName}&{lastName}")
	public ResponseEntity<Object> getMedicalRecords(@PathVariable("firstName") final String firstName, @PathVariable("lastName") final String lastName){
		//return medicalRecordsService.getMedicalRecords(firstName,lastName);
		try{
			MedicalRecords medicalRecords = medicalRecordsService.getMedicalRecords(firstName, lastName);
			return ResponseEntity.ok(medicalRecords);
		} catch (Exception e){
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * this method add a medical record from firstname and lastname from the path /medicalRecord
	 * @param medicalRecords represents the medical record passed on the body of the http request that has to be added.
	 * @return RespsonseEntity created which represents a http response with code 201.
	 */
	@PostMapping()
	public ResponseEntity<MedicalRecords> addMedicalRecords(@RequestBody MedicalRecords medicalRecords) {
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
	@PutMapping("/{firstName}&{lastName}")
	public ResponseEntity<HttpStatus> updateMedicalRecords(@PathVariable("firstName") final String firstName, @PathVariable("lastName") final String lastName, @RequestBody MedicalRecords medicalRecords){
		try {
			medicalRecordsService.updateMedicalRecords(medicalRecords,firstName, lastName);
			return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
		}catch (Exception e){
			return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * this method remove a medical record from firstname and lastname from the path /medicalRecord/{firstName}/{lastName}
	 * @param firstName represents the firstname of the medical record that has to be removed.
	 * @param lastName represents the lastName of the medical record that has to be removed.
	 * @return ResponseEntity accepted which represents a http response with code 202.
	 */
	@DeleteMapping("/{firstName}&{lastName}")
	public ResponseEntity<HttpStatus> deletePerson(@PathVariable("firstName") final String firstName, @PathVariable("lastName") final String lastName) {
		try {
			medicalRecordsService.deleteAccount(firstName, lastName);
			return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
		}catch (Exception e){
			return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
		}
	}
}
