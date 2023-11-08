package service;

import SafetyNetAlert.controller.exception.*;
import SafetyNetAlert.dto.FireAddress;
import SafetyNetAlert.dto.FirestationByStation;
import SafetyNetAlert.dto.Flood;
import SafetyNetAlert.dto.PersonInfoFireAddress;
import SafetyNetAlert.model.Firestations;
import SafetyNetAlert.model.MedicalRecords;
import SafetyNetAlert.model.Persons;
import SafetyNetAlert.model.SafetyAlerts;
import SafetyNetAlert.repository.FirestationsRepository;
import SafetyNetAlert.repository.MedicalRecordsRepository;
import SafetyNetAlert.service.FirestationsService;
import SafetyNetAlert.service.IFirestationService;
import lombok.extern.log4j.Log4j2;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

/**
 * test: when we add a new firestation, search for a firestation, get all firestations,
 * deelete a firestation, update a firestation, get child by address, get a person by station,
 * get flood by a station list.
 * @author Mougni
 *
 */
@SpringBootTest(classes = IFirestationService.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@Log4j2
public class FirestationServiceTest {

    private Firestations firestations;
    private SafetyAlerts safety;
    @InjectMocks
    private FirestationsService firestationsService;

    @MockBean
    public FirestationsRepository firestationsRepository;
    @MockBean
    public MedicalRecordsRepository medicalRecordRepository;

    MedicalRecords medicalRecords;
    MedicalRecords medicalRecords2;

    @BeforeEach
    public void setUpPerTest(){

        firestations = new Firestations();


        firestations.setAddress("19 street Cluver");

        firestations.setStation(5);

        Firestations firestations1 = new Firestations();
        firestations1.setAddress("19 street Cluver");

        firestations1.setStation(2);

        safety = new SafetyAlerts();
        safety.setFirestations(new ArrayList<>());
        safety.getFirestations().add(firestations);
        safety.getFirestations().add(firestations1);

        Persons person = new Persons();

        person.setPhone("000-000-000");
        person.setLastName("Boyd");
        person.setFirstName("John");
        person.setCity("Cluver");
        person.setEmail("aoaoa@aaoaoa.com");
        person.setAddress("19 street Cluver");

        Persons person2 = new Persons();
        person2.setPhone("000-000-000");
        person2.setCity("Cluver");
        person2.setEmail("e@e.com");
        person2.setLastName("Boyd");
        person2.setFirstName("Luc");
        person2.setAddress("19 street Cluver");
        safety.setPersons(new ArrayList<>());
        safety.getPersons().add(person);

        safety.getPersons().add(person2);

        medicalRecords = new MedicalRecords();

        medicalRecords.setBirthdate("01/01/1980");
        medicalRecords.setLastName("Boyd");
        medicalRecords.setFirstName("John");

        medicalRecords2 = new MedicalRecords();

        medicalRecords2.setBirthdate("11/07/2010");
        medicalRecords2.setLastName("Boyd");
        medicalRecords2.setFirstName("Luc");



    }

    //add firestation (successfull)

    @Test
    public void givenFirestation_whenFirestationAdded_then202IsReceived(){
        setUpPerTest();

        firestations.setAddress("19 street Cluver");

        firestations.setStation(5);


        FirestationsRepository e = Mockito.mock(FirestationsRepository.class);
        Mockito.when(firestationsRepository.save(firestations)).thenReturn(firestations);
        Firestations firestationAdded = firestationsService.saveFirestations(firestations);
        verify(firestationsRepository, Mockito.times(1)).save(firestations);
        Assert.assertEquals(firestationAdded, firestations);
    }

    //add firestation (unsuccessfull)
    @Test
    public void givenPerson_whenPersonAdded_then404IsReceived(){
        setUpPerTest();
        firestations.setAddress("19 street Cluver");

        firestations.setStation(5);
        FirestationsRepository e = Mockito.mock(FirestationsRepository.class);
        Mockito.when(firestationsRepository.save(any(Firestations.class))).thenReturn(null);


        Firestations firestationAdded = firestationsService.saveFirestations(firestations);
        verify(firestationsRepository, Mockito.times(1)).save(firestations);
        Assert.assertEquals(null, firestationAdded);
    }

    // get firestation by id ( firestation found )
    @Test
    public void givenFirestation_whenGetAFirestation_then200IsReceived() throws Exception {
        // given
        setUpPerTest();
        firestations.setAddress("19 street Cluver");

        firestations.setStation(5);

        FirestationsRepository e = Mockito.mock(FirestationsRepository.class);
        Mockito.when(firestationsRepository.findByIds(firestations.getAddress())).thenReturn(firestations);
        Firestations firestationFound = firestationsService.getFirestations(firestations.getAddress());
        verify(firestationsRepository, Mockito.times(1)).findByIds(firestations.getAddress());
        Assert.assertEquals(firestations, firestationFound);


    }

    // get firestation by address (firestation not found)
    @Test
    public void givenFirestation_whenGetAFirestation_then404IsReceived() throws Exception {
        // given
        setUpPerTest();
        firestations.setAddress("19 street Cluver");

        firestations.setStation(5);

        FirestationsRepository e = Mockito.mock(FirestationsRepository.class);
        Mockito.when(firestationsRepository.findByIds(firestations.getAddress())).thenReturn(null);
        Exception thrown = Assert.assertThrows(NotFoundException.class, () -> {
            firestationsService.getFirestations(firestations.getAddress());
        });
        verify(firestationsRepository, Mockito.times(1)).findByIds(firestations.getAddress());

        Assert.assertEquals("Firestation not found with this address", thrown.getMessage());

    }

    // get all firestation ( firestation found )
    @Test
    public void givenFirestation_whenGetAllFirestation_then200IsReceived() throws Exception {
        // given
        setUpPerTest();
        firestations.setAddress("19 street Cluver");

        firestations.setStation(5);

        Firestations firestations1 = new Firestations();
        firestations1.setAddress("12 street Cluver");

        firestations1.setStation(2);

        FirestationsRepository e = Mockito.mock(FirestationsRepository.class);
        List<Firestations> list = new ArrayList<Firestations>();
        list.add(firestations);
        list.add(firestations1);
        Mockito.when(firestationsRepository.findAll()).thenReturn(list);

        List<Firestations> listFound = firestationsService.getAllFirestations();
        verify(firestationsRepository, Mockito.times(1)).findAll();
        Assert.assertEquals(list, listFound);


    }

    // delete firestation by address ( address found )
    @Test
    public void givenFirestation_whenDeleteAFirestation_then200IsReceived() throws Exception {
        // given
        setUpPerTest();
        firestations.setAddress("19 street Cluver");

        firestations.setStation(5);

        FirestationsRepository e = Mockito.mock(FirestationsRepository.class);
        Mockito.doNothing().when(firestationsRepository).deleteByIds(firestations.getAddress());
        Mockito.when(firestationsRepository.findByIds(firestations.getAddress())).thenReturn(firestations);

        firestationsService.deleteFirestation(firestations.getAddress());

        verify(firestationsRepository, Mockito.times(1)).deleteByIds(firestations.getAddress());
        Mockito.when(firestationsRepository.findByIds(firestations.getAddress())).thenReturn(null);
        Firestations firestationsDeleted = firestationsRepository.findByIds(firestations.getAddress());
        Assert.assertEquals(null, firestationsDeleted);


    }


    // delete firestation by address ( address not found )
    @Test
    public void givenFirestation_whenDeleteAFirestation_then404IsReceived() throws Exception {
        // given
        setUpPerTest();
        firestations.setAddress("19 street Cluver");

        firestations.setStation(5);

        FirestationsRepository e = Mockito.mock(FirestationsRepository.class);
        Mockito.doNothing().when(firestationsRepository).deleteByIds(firestations.getAddress());
        Mockito.when(firestationsRepository.findByIds(firestations.getAddress())).thenReturn(null);
        Exception thrown = Assert.assertThrows(NotFoundException.class, () -> {
            firestationsService.deleteFirestation(firestations.getAddress());
        });
        verify(firestationsRepository, Mockito.times(1)).findByIds(firestations.getAddress());

        Assert.assertEquals("Firestation not found with this address", thrown.getMessage());


    }

    // put firestation by address ( firestation found )
    @Test
    public void givenFirestation_whenUpdateAFirestation_then200IsReceived() throws Exception {
        // given
        setUpPerTest();
        firestations.setAddress("19 street Cluver");

        firestations.setStation(5);

        FirestationsRepository e = Mockito.mock(FirestationsRepository.class);
        Mockito.when(firestationsRepository.findByIds(firestations.getAddress())).thenReturn(firestations);

        Firestations firestationsUpdated = firestationsService.updateFirestations(firestations.getAddress(), firestations);

        verify(firestationsRepository, Mockito.times(1)).update(firestations, firestations.getAddress());
        Assert.assertEquals(firestations, firestationsUpdated);


    }

    // put firestation by address ( firestation not found )
    @Test
    public void givenFirestation_whenUpdateAFirestation_then404sReceived() throws Exception {
        // given
        setUpPerTest();
        firestations.setAddress("19 street Cluver");

        firestations.setStation(5);
        FirestationsRepository e = Mockito.mock(FirestationsRepository.class);
        Mockito.when(firestationsRepository.findByIds(firestations.getAddress())).thenReturn(null);
        Exception thrown = Assert.assertThrows(NotFoundException.class, () -> {
            firestationsService.updateFirestations(firestations.getAddress(), firestations);
        });

        verify(firestationsRepository, Mockito.times(1)).findByIds(firestations.getAddress());
        Assert.assertEquals("Firestation not found with this address", thrown.getMessage());

    }

    // get child by address (successfull)
    @Test
    public void givenAddress_whenGetFireAddress_then200IsReceived() throws Exception {
        firestationsService.setFirestationsRepository(firestationsRepository);
        FireAddress fireAddress = new FireAddress();
        safety.setMedicalrecords(new ArrayList<>());
        safety.getMedicalrecords().add(medicalRecords);
        safety.getMedicalrecords().add(medicalRecords2);


        List<Integer> station = new ArrayList<Integer>();
        station.add(safety.getFirestations().get(0).getStation());
        station.add(safety.getFirestations().get(1).getStation());
        List<PersonInfoFireAddress> listPersonsInfoFire = new ArrayList<>();

        LocalDate curDate = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("LL/dd/yyyy", Locale.FRANCE);

        LocalDate birthdate = LocalDate.parse(safety.getMedicalrecords().get(0).getBirthdate(), dtf);
        Period period = Period.between(birthdate, curDate);
        int age = period.getYears();
        birthdate = LocalDate.parse(safety.getMedicalrecords().get(1).getBirthdate(), dtf);
        Period period1 = Period.between(birthdate, curDate);
        int age1 = period1.getYears();

        listPersonsInfoFire.add(new PersonInfoFireAddress(safety.getPersons().get(0).getLastName(), safety.getPersons().get(0).getPhone(),
                age, safety.getMedicalrecords().get(0).getMedications(),safety.getMedicalrecords().get(0).getAllergies()));
        listPersonsInfoFire.add(new PersonInfoFireAddress(safety.getPersons().get(1).getLastName(), safety.getPersons().get(1).getPhone(),
                age1, safety.getMedicalrecords().get(1).getMedications(),safety.getMedicalrecords().get(1).getAllergies()));

        fireAddress.setHabitants(listPersonsInfoFire);
        fireAddress.setStation(station);


        Mockito.when(firestationsRepository.getSafety()).thenReturn(safety);


        FireAddress fireAddressActual = firestationsService.getPersonsFromAddress("19 street Cluver");


        assertEquals(fireAddress.getHabitants().size(), fireAddressActual.getHabitants().size());
        assertEquals(fireAddress.getHabitants().get(0).getLastName(), fireAddressActual.getHabitants().get(0).getLastName());
        assertEquals(fireAddress.getHabitants().get(0).getAge(), fireAddressActual.getHabitants().get(0).getAge());
        assertEquals(fireAddress.getHabitants().get(1).getAge(), fireAddressActual.getHabitants().get(1).getAge());
        assertEquals(fireAddress.getHabitants().get(1).getLastName(), fireAddressActual.getHabitants().get(1).getLastName());

        verify(firestationsRepository, Mockito.times(1)).getSafety();

    }

    // get person by station (successfull)
    @Test
    public void givenStation_whenGetFirestationByStation_then200IsReceived() throws Exception {
        FireAddress fireAddress = new FireAddress();

        firestations.setAddress("19 street Cluver");

        firestations.setStation(5);

        Firestations firestations1 = new Firestations();
        firestations1.setAddress("1 street Zopa");

        firestations1.setStation(5);

        safety = new SafetyAlerts();
        safety.setFirestations(new ArrayList<>());
        safety.getFirestations().add(firestations);
        safety.getFirestations().add(firestations1);



        Persons person = new Persons();

        person.setPhone("000-000-000");
        person.setLastName("Paes");
        person.setFirstName("John");
        person.setCity("Zopa");
        person.setEmail("aoaoa@aaoaoa.com");
        person.setAddress("1 street Zopa");

        Persons person2 = new Persons();
        person2.setPhone("000-000-000");
        person2.setCity("Cluver");
        person2.setEmail("e@e.com");
        person2.setLastName("Boyd");
        person2.setFirstName("Luc");
        person2.setAddress("19 street Cluver");
        safety.setPersons(new ArrayList<>());
        safety.getPersons().add(person);

        safety.getPersons().add(person2);

        safety.setMedicalrecords(new ArrayList<>());
        safety.getMedicalrecords().add(medicalRecords);
        safety.getMedicalrecords().add(medicalRecords2);

        LocalDate birthdate;
        List<PersonInfoFireAddress> listPersonsInfoFire = new ArrayList<>();

        LocalDate curDate = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("LL/dd/yyyy", Locale.FRANCE);

        birthdate = LocalDate.parse(safety.getMedicalrecords().get(0).getBirthdate(), dtf);
        Period period = Period.between(birthdate, curDate);
        int age = period.getYears();
        birthdate = LocalDate.parse(safety.getMedicalrecords().get(0).getBirthdate(), dtf);
        Period period1 = Period.between(birthdate, curDate);
        int age1 = period1.getYears();

        listPersonsInfoFire.add(new PersonInfoFireAddress(safety.getPersons().get(0).getLastName(), safety.getPersons().get(0).getPhone(),
                age, safety.getMedicalrecords().get(0).getMedications(),safety.getMedicalrecords().get(0).getAllergies()));
        listPersonsInfoFire.add(new PersonInfoFireAddress(safety.getPersons().get(1).getLastName(), safety.getPersons().get(1).getPhone(),
                age1, safety.getMedicalrecords().get(1).getMedications(),safety.getMedicalrecords().get(1).getAllergies()));

        fireAddress.setHabitants(listPersonsInfoFire);


        Mockito.when(firestationsRepository.getSafety()).thenReturn(safety);
        Mockito.when(medicalRecordRepository.findByIds(anyString(), anyString())).thenReturn(medicalRecords, medicalRecords2);

        FirestationByStation expected = new FirestationByStation();
        List<Persons> habitants = new ArrayList<>();
        habitants.add(safety.getPersons().get(0));
        habitants.add(safety.getPersons().get(1));
        expected.setHabitants(habitants);
        expected.setAdulte(1);
        expected.setEnfant(1);

        FirestationByStation firestationByStationActual = firestationsService.getPersonsFromStation(5);



        assertEquals(expected.getHabitants().get(0), firestationByStationActual.getHabitants().get(0));
        assertEquals(expected.getHabitants().size(), firestationByStationActual.getHabitants().size());
        assertEquals(expected.getAdulte(), firestationByStationActual.getAdulte());
        assertEquals(expected.getEnfant(), firestationByStationActual.getEnfant());
        verify(firestationsRepository, Mockito.times(1)).getSafety();

    }

    // get list flood by station list (successfull)
    @Test
    public void givenStationList_whenGetFlood_then200IsReceived() throws Exception {
        firestationsService.setFirestationsRepository(firestationsRepository);
        safety.setMedicalrecords(new ArrayList<>());
        safety.getMedicalrecords().add(medicalRecords);
        safety.getMedicalrecords().add(medicalRecords2);

        Mockito.when(firestationsRepository.getSafety()).thenReturn(safety);

        List<Flood> expected = new ArrayList<>();
        List<String> address= new ArrayList<String>();
        List<String> medications = new ArrayList<>();
        List<String> allergies = new ArrayList<String>();
        List<Integer> liststation = new ArrayList<>();
        liststation.add(5);
        liststation.add(0);


        for(int i = 0; i < safety.getFirestations().size(); i++) {
            address.add(safety.getFirestations().get(i).getAddress());
            String address1 = safety.getPersons().get(i).getAddress();
            String phone = safety.getPersons().get(i).getPhone();
            String lastName = safety.getPersons().get(i).getLastName();

            LocalDate curDate = LocalDate.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("LL/dd/yyyy", Locale.FRANCE);
            LocalDate birthdate = LocalDate.parse(safety.getMedicalrecords().get(i).getBirthdate(), dtf);
            Period period = Period.between(birthdate, curDate);
            int age = period.getYears();

            medications.add(safety.getMedicalrecords().get(i).getMedications().toString());
            allergies.add(safety.getMedicalrecords().get(i).getAllergies().toString());
            List m = new ArrayList<>();
            m.add(safety.getMedicalrecords().get(i).getMedications());
            m.add(safety.getMedicalrecords().get(i).getAllergies());

            expected.add(new Flood(address1, lastName, phone, age, m));
        }

        List<Flood> actual = firestationsService.getHearthByStations(liststation);

        assertEquals(expected.get(0).getAge(), actual.get(0).getAge());
        assertEquals(expected.get(1).getAge(), actual.get(1).getAge());
        verify(firestationsRepository, Mockito.times(1)).getSafety();

    }


}
