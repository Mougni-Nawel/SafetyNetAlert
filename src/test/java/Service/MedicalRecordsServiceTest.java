package Service;

import SafetyNetAlert.Controller.Exception.*;
import SafetyNetAlert.Model.MedicalRecords;
import SafetyNetAlert.Repository.MedicalRecordsRepository;
import SafetyNetAlert.Service.IMedicalRecordService;
import SafetyNetAlert.Service.MedicalRecordsService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * test: save a new medical record, delete a medical record, get and update a medical record, get all firestations.
 * @author Mougni
 *
 */
@SpringBootTest(classes = IMedicalRecordService.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class MedicalRecordsServiceTest {

    @Autowired
    private MockMvc mockmvc;
    private MedicalRecords medicalRecords;
    @InjectMocks
    private MedicalRecordsService medicalRecordsService;

    @MockBean
    private MedicalRecordsRepository medicalRecordsRepository;

    @BeforeEach
    public void setUpPerTest(){

        medicalRecords = new MedicalRecords();

    }

    //add medicalRecords (successfull)

    @Test
    public void givenMedicalRecord_whenMediccalRecordAdded_then202IsReceived(){
        setUpPerTest();

        medicalRecords.setBirthdate("01/01/1980");
        medicalRecords.setLastName("Boyd");
        medicalRecords.setFirstName("Jon");


        MedicalRecordsRepository e = Mockito.mock(MedicalRecordsRepository.class);
        Mockito.when(medicalRecordsRepository.save(medicalRecords)).thenReturn(medicalRecords);
        // when(personsRepository.save((Persons) any(Persons.class))).thenReturn(person);

        //personService = new PersonsService();
        MedicalRecords medicalRecordAdded = medicalRecordsService.saveMedicalRecords(medicalRecords);
        verify(medicalRecordsRepository, Mockito.times(1)).save(medicalRecords);
        Assert.assertEquals(medicalRecordAdded, medicalRecords);
    }

    //add medicalRecords (unsuccessfull)
    @Test
    public void givenMedicalRecord_whenMedicalRecordAdded_then404IsReceived(){
        setUpPerTest();
        medicalRecords.setBirthdate("01/01/1980");
        medicalRecords.setLastName("Boyd");
        medicalRecords.setFirstName("Jon");

        MedicalRecordsRepository e = Mockito.mock(MedicalRecordsRepository.class);
        //Mockito.when(e.save(firestations)).thenReturn(null);
        Mockito.when(medicalRecordsRepository.save(any(MedicalRecords.class))).thenReturn(null);


        //when(personsRepository.save(null)).thenReturn(null);
        //personService = new PersonsService();
        MedicalRecords medicalRecordAdded = medicalRecordsService.saveMedicalRecords(medicalRecords);
        verify(medicalRecordsRepository, Mockito.times(1)).save(medicalRecords);
        Assert.assertEquals(null, medicalRecordAdded);
    }

    // get all medicalRecords ( medicalRecords found )
    @Test
    public void _whenGetALLMedicalRecord_then200IsReceived() throws Exception {


        medicalRecords.setBirthdate("01/01/1980");
        medicalRecords.setLastName("Boyd");
        medicalRecords.setFirstName("John");

        MedicalRecords medicalRecords1 = new MedicalRecords();

        medicalRecords1.setBirthdate("08/01/1989");
        medicalRecords1.setLastName("Boyd");
        medicalRecords1.setFirstName("Jacob");

        MedicalRecordsRepository e = Mockito.mock(MedicalRecordsRepository.class);
        List<MedicalRecords> list = new ArrayList<MedicalRecords>();
        list.add(medicalRecords);
        list.add(medicalRecords1);
        Mockito.when(medicalRecordsRepository.findAll()).thenReturn(list);
        //when(personsRepository.save((Persons) any(Persons.class))).thenReturn(0);


        //when(personsRepository.save(null)).thenReturn(null);
        //personService = new PersonsService();
        List<MedicalRecords> listFound = medicalRecordsService.getAllMedicalRecords();
        verify(medicalRecordsRepository, Mockito.times(1)).findAll();
        Assert.assertEquals(listFound, list);


    }



    // get medicalRecords by name ( medicalRecords found )
    @Test
    public void givenName_whenGetAMedicalRecord_then200IsReceived() throws Exception {
        // given
        setUpPerTest();
        medicalRecords.setBirthdate("01/01/1980");
        medicalRecords.setLastName("Boyd");
        medicalRecords.setFirstName("John");


        MedicalRecordsRepository e = Mockito.mock(MedicalRecordsRepository.class);
        Mockito.when(medicalRecordsRepository.findByIds(medicalRecords.getFirstName(), medicalRecords.getLastName())).thenReturn(medicalRecords);
        //when(personsRepository.save((Persons) any(Persons.class))).thenReturn(0);


        //when(personsRepository.save(null)).thenReturn(null);
        //personService = new PersonsService();
        MedicalRecords medicalRecordsFound = medicalRecordsService.getMedicalRecords(medicalRecords.getFirstName(), medicalRecords.getLastName());
        verify(medicalRecordsRepository, Mockito.times(1)).findByIds(medicalRecords.getFirstName(), medicalRecords.getLastName());
        Assert.assertEquals(medicalRecords, medicalRecordsFound);


    }

    // get medicalRecords by name (medicalRecords not found)
    @Test
    public void givenName_whenGetAMedicalRrecord_then404IsReceived() throws Exception {
        // given
        setUpPerTest();
        medicalRecords.setBirthdate("01/01/1980");
        medicalRecords.setLastName("Boyd");
        medicalRecords.setFirstName("John");

        MedicalRecordsRepository e = Mockito.mock(MedicalRecordsRepository.class);
        Mockito.when(medicalRecordsRepository.findByIds(medicalRecords.getFirstName(), medicalRecords.getLastName())).thenReturn(null);
        //when(personsRepository.save((Persons) any(Persons.class))).thenReturn(0);



        //when(personsRepository.save(null)).thenReturn(null);
        //personService = new PersonsService();
        Exception thrown = Assert.assertThrows(MedicalRecordsNotFoundException.class, () -> {
            //Code under test
            medicalRecordsService.getMedicalRecords(medicalRecords.getFirstName(), medicalRecords.getLastName());
        });
        verify(medicalRecordsRepository, Mockito.times(1)).findByIds(medicalRecords.getFirstName(), medicalRecords.getLastName());
        // Assert.assertEquals(null, personFound);

        Assert.assertEquals("MedicalRecords not found with this name", thrown.getMessage());

    }

    // delete medicalRecords by name ( medicalRecords found )
    @Test
    public void givenName_whenDeleteAMedicalRecord_then200IsReceived() throws Exception {
        // given
        setUpPerTest();
        medicalRecords.setBirthdate("01/01/1980");
        medicalRecords.setLastName("Boyd");
        medicalRecords.setFirstName("John");

        MedicalRecordsRepository e = Mockito.mock(MedicalRecordsRepository.class);
        Mockito.doNothing().when(medicalRecordsRepository).deleteByIds(medicalRecords.getFirstName(), medicalRecords.getLastName());
        Mockito.when(medicalRecordsRepository.findByIds(medicalRecords.getFirstName(), medicalRecords.getLastName())).thenReturn(medicalRecords);

        medicalRecordsService.deleteAccount(medicalRecords.getFirstName(), medicalRecords.getLastName());

        verify(medicalRecordsRepository, Mockito.times(1)).deleteByIds(medicalRecords.getFirstName(), medicalRecords.getLastName());
        Mockito.when(medicalRecordsRepository.findByIds(medicalRecords.getFirstName(), medicalRecords.getLastName())).thenReturn(null);
        MedicalRecords medicalRecordsDeleted = medicalRecordsRepository.findByIds(medicalRecords.getFirstName(), medicalRecords.getLastName());
        Assert.assertEquals(null, medicalRecordsDeleted);


    }


    // delete medicalRecords by name ( medicalRecords not found )
    @Test
    public void givenName_whenDeleteAMedicalRecord_then404IsReceived() throws Exception {
        // given
        setUpPerTest();
        medicalRecords.setBirthdate("01/01/1980");
        medicalRecords.setLastName("Bod");
        medicalRecords.setFirstName("Jon");

        MedicalRecordsRepository e = Mockito.mock(MedicalRecordsRepository.class);
        Mockito.doNothing().when(medicalRecordsRepository).deleteByIds(medicalRecords.getFirstName(), medicalRecords.getLastName());
        Mockito.when(medicalRecordsRepository.findByIds(medicalRecords.getFirstName(), medicalRecords.getLastName())).thenReturn(null);
        Exception thrown = Assert.assertThrows(MedicalRecordsNotFoundException.class, () -> {
            //Code under test
            medicalRecordsService.deleteAccount(medicalRecords.getFirstName(), medicalRecords.getLastName());
        });
        //personService.deleteAccount(person.getFirstName(), person.getLastName());
        verify(medicalRecordsRepository, Mockito.times(1)).findByIds(medicalRecords.getFirstName(), medicalRecords.getLastName());

        Assert.assertEquals("MedicalRecords not found with this name", thrown.getMessage());


    }

    // put medicalRecords by name ( medicalRecords found )
    @Test
    public void givenFirestation_whenUpdateAFirestation_then200IsReceived() throws Exception {
        // given
        setUpPerTest();
        medicalRecords.setBirthdate("01/01/1980");
        medicalRecords.setLastName("Boyd");
        medicalRecords.setFirstName("John");

        MedicalRecordsRepository e = Mockito.mock(MedicalRecordsRepository.class);
        //Mockito.doNothing().when(personsRepository).update(person, person.getFirstName(), person.getLastName());
        Mockito.when(medicalRecordsRepository.findByIds(medicalRecords.getFirstName(), medicalRecords.getLastName())).thenReturn(medicalRecords);

        MedicalRecords medicalRecordsUpdated = medicalRecordsService.updateMedicalRecords(medicalRecords, medicalRecords.getFirstName(), medicalRecords.getLastName());

        verify(medicalRecordsRepository, Mockito.times(1)).update(medicalRecords, medicalRecords.getFirstName(), medicalRecords.getLastName());
        //Mockito.when(personsRepository.findByIds(person.getFirstName(), person.getLastName())).thenReturn(null);
        //Persons personDeleted = personsRepository.findByIds(person.getFirstName(), person.getLastName());
        Assert.assertEquals(medicalRecords, medicalRecordsUpdated);


    }

    // put medicalRecords by name ( medicalRecords not found )
    @Test
    public void givenName_whenUpdateAMedicalRecord_then404sReceived() throws Exception {
        // given
        setUpPerTest();
        medicalRecords.setBirthdate("01/01/1980");
        medicalRecords.setLastName("Bod");
        medicalRecords.setFirstName("Jon");

        MedicalRecordsRepository e = Mockito.mock(MedicalRecordsRepository.class);
        //Mockito.doNothing().when(personsRepository).update(person, person.getFirstName(), person.getLastName());
        Mockito.when(medicalRecordsRepository.findByIds(medicalRecords.getFirstName(), medicalRecords.getLastName())).thenReturn(null);
        //Persons personUpdated = personService.updatePerson(person, person.getFirstName(), person.getLastName());
        Exception thrown = Assert.assertThrows(MedicalRecordsNotFoundException.class, () -> {
            //Code under test
            medicalRecordsService.updateMedicalRecords(medicalRecords, medicalRecords.getFirstName(), medicalRecords.getLastName());
        });

        verify(medicalRecordsRepository, Mockito.times(1)).findByIds(medicalRecords.getFirstName(), medicalRecords.getLastName());
        //Mockito.when(personsRepository.findByIds(person.getFirstName(), person.getLastName())).thenReturn(null);
        //Persons personDeleted = personsRepository.findByIds(person.getFirstName(), person.getLastName());
        Assert.assertEquals("MedicalRecords not found with this name", thrown.getMessage());

    }


}
