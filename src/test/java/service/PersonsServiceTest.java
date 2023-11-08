package service;

import SafetyNetAlert.controller.exception.*;
import SafetyNetAlert.dto.ChildAlerts;
import SafetyNetAlert.dto.PersonInfo;
import SafetyNetAlert.dto.PersonsEmail;
import SafetyNetAlert.dto.PersonsMobile;
import SafetyNetAlert.model.Firestations;
import SafetyNetAlert.model.MedicalRecords;
import SafetyNetAlert.model.Persons;
import SafetyNetAlert.model.SafetyAlerts;
import SafetyNetAlert.repository.PersonsRepository;
import SafetyNetAlert.service.IPersonService;
import SafetyNetAlert.service.PersonsService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * test: save a new person, delete a person, get and update a person, get all info of a person,
 * get all emails from a city, get all mobiles of persons from an address, get all child by an address.
 * @author Mougni
 *
 */
@SpringBootTest(classes = IPersonService.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class PersonsServiceTest {

    private Persons person;
    private Persons person2;
    private SafetyAlerts safety;

    @InjectMocks
    private PersonsService personService;

    @MockBean
    public PersonsRepository personsRepository;



    @BeforeEach
    public void setUpPerTest(){

        person = new Persons();

        safety = new SafetyAlerts();
        person.setPhone("000-000-000");
        person.setLastName("Boyd");
        person.setFirstName("John");
        person.setCity("Cluver");
        person.setEmail("aoaoa@aaoaoa.com");
        person.setAddress("15 street Cluver");

        person2 = new Persons();
        person2.setPhone("000-000-000");
        person2.setCity("Cluver");
        person2.setEmail("e@e.com");
        person2.setLastName("Boyd");
        person2.setFirstName("Luc");
        person2.setAddress("15 street Cluver");

        safety.setPersons(new ArrayList<>());
        safety.getPersons().add(person);

        safety.getPersons().add(person2);

    }

    //add person (successfull)

    @Test
    public void givenPerson_whenPersonAdded_then202IsReceived(){
        setUpPerTest();
        person.setLastName("testLastName");
        person.setAddress("testAddress");
        person.setCity("testCity");
        person.setZip(97452);
        person.setEmail("test@email.com");
        person.setFirstName("testFirstName");
        person.setPhone("841-874-0000");
        PersonsRepository e = Mockito.mock(PersonsRepository.class);
        Mockito.when(personsRepository.save(person)).thenReturn(person);
        Persons personAdded = personService.savePerson(person);
        verify(personsRepository, Mockito.times(1)).save(person);
        Assert.assertEquals(personAdded, person);
    }

    //add person (unsuccessfull)
    @Test
    public void givenPerson_whenPersonAdded_then404IsReceived(){
        setUpPerTest();
        person.setLastName("testLastName");
        person.setAddress("testAddress");
        person.setCity("testCity");
        person.setZip(97452);
        person.setEmail("test@email.com");
        person.setFirstName("testFirstName");
        person.setPhone("841-874-0000");
        PersonsRepository e = Mockito.mock(PersonsRepository.class);
        Mockito.when(personsRepository.save(person)).thenReturn(null);


        Persons personAdded = personService.savePerson(person);
        verify(personsRepository, Mockito.times(1)).save(person);
        Assert.assertEquals(null, personAdded);
    }

    // get person by id ( user found )
    @Test
    public void givenPerson_whenGetAPerson_then200IsReceived() throws Exception {
        // given
        setUpPerTest();

        PersonsRepository e = Mockito.mock(PersonsRepository.class);
        Mockito.when(personsRepository.findByIds(person.getFirstName(), person.getLastName())).thenReturn(person);


        Persons personFound = personService.getPersons(person.getFirstName(), person.getLastName());
        verify(personsRepository, Mockito.times(1)).findByIds(person.getFirstName(), person.getLastName());
        Assert.assertEquals(person, personFound);


    }

    // get person by id (user not found)
    @Test
    public void givenPerson_whenGetAPerson_then404IsReceived() throws Exception {
        // given

        setUpPerTest();
        Mockito.when(personsRepository.getSafety()).thenReturn(safety);
        person.setLastName("Bod");
        person.setAddress("1509 Culver St");
        person.setCity("Culver");
        person.setZip(97451);
        person.setEmail("jaboyd@email.com");
        person.setFirstName("Patrick");
        person.setPhone("841-874-6512");

        PersonsRepository e = Mockito.mock(PersonsRepository.class);
        Mockito.when(personsRepository.findByIds(person.getFirstName(), person.getLastName())).thenReturn(null);


        Exception thrown = Assert.assertThrows(NotFoundException.class, () -> {
            personService.getPersons(person.getFirstName(), person.getLastName());
        });
        verify(personsRepository, Mockito.times(1)).findByIds(person.getFirstName(), person.getLastName());

        Assert.assertEquals("L'utilisateur n'est pas trouvé avec ce firstname", thrown.getMessage());

    }

    // get all person ( persons found )
    @Test
    public void givenPerson_whenGetAllPerson_then200IsReceived() throws Exception {
        // given
        setUpPerTest();
        person.setLastName("Boyd");
        person.setAddress("1509 Culver St");
        person.setCity("Culver");
        person.setZip(97451);
        person.setEmail("jaboyd@email.com");
        person.setFirstName("John");
        person.setPhone("841-874-6512");

        Persons person1 = new Persons();
        person1.setLastName("Boyd");
        person1.setAddress("1509 Culver St");
        person1.setCity("Culver");
        person1.setZip(97451);
        person1.setEmail("jaboyd@email.com");
        person1.setFirstName("Jacob");
        person1.setPhone("841-874-6512");

        PersonsRepository e = Mockito.mock(PersonsRepository.class);
        List<Persons> list = new ArrayList<Persons>();
        list.add(person);
        list.add(person1);
        Mockito.when(personsRepository.findAll()).thenReturn(list);

        List<Persons> listFound = personService.getAllPersons();
        verify(personsRepository, Mockito.times(1)).findAll();
        Assert.assertEquals(list, listFound);


    }


    // delete person by id ( user found )
    @Test
    public void givenPerson_whenDeleteAPerson_then200IsReceived() throws Exception {
        // given
        setUpPerTest();
        person.setLastName("Boyd");
        person.setAddress("1509 Culver St");
        person.setCity("Culver");
        person.setZip(97451);
        person.setEmail("jaboyd@email.com");
        person.setFirstName("John");
        person.setPhone("841-874-6512");

        PersonsRepository e = Mockito.mock(PersonsRepository.class);
        Mockito.doNothing().when(personsRepository).deleteByIds(person.getFirstName(), person.getLastName());
        Mockito.when(personsRepository.findByIds(person.getFirstName(), person.getLastName())).thenReturn(person);

        personService.deleteAccount(person.getFirstName(), person.getLastName());

        verify(personsRepository, Mockito.times(1)).deleteByIds(person.getFirstName(), person.getLastName());
        Mockito.when(personsRepository.findByIds(person.getFirstName(), person.getLastName())).thenReturn(null);
        Persons personDeleted = personsRepository.findByIds(person.getFirstName(), person.getLastName());
        Assert.assertEquals(null, personDeleted);


    }


    // delete person by id ( user not found )
    @Test
    public void givenPerson_whenDeleteAPerson_then404IsReceived() throws Exception {
        // given
        safety.setPersons(new ArrayList<>());

        Mockito.when(personsRepository.getSafety()).thenReturn(safety);
        setUpPerTest();
        person.setLastName("Byd");
        person.setAddress("1509 Culver St");
        person.setCity("Culver");
        person.setZip(97451);
        person.setEmail("jaboyd@email.com");
        person.setFirstName("Jon");
        person.setPhone("841-874-6512");

        PersonsRepository e = Mockito.mock(PersonsRepository.class);
        Mockito.doNothing().when(personsRepository).deleteByIds(person.getFirstName(), person.getLastName());
        Mockito.when(personsRepository.findByIds(person.getFirstName(), person.getLastName())).thenReturn(null);
        Exception thrown = Assert.assertThrows(NotFoundException.class, () -> {
            personService.deleteAccount(person.getFirstName(), person.getLastName());
        });
        verify(personsRepository, Mockito.times(1)).findByIds(person.getFirstName(), person.getLastName());

        Assert.assertEquals("L'utilisateur n'est pas trouvé avec ce firstname", thrown.getMessage());


    }

    // put person by id ( user found )
    @Test
    public void givenPerson_whenUpdateAPerson_then200IsReceived() throws Exception {
        // given
        safety.setPersons(new ArrayList<>());

        Mockito.when(personsRepository.getSafety()).thenReturn(safety);
        setUpPerTest();
        person.setLastName("Boyd");
        person.setAddress("15 Culver St");
        person.setCity("Culver");
        person.setZip(97451);
        person.setEmail("jaboyd@email.com");
        person.setFirstName("John");
        person.setPhone("841-874-0000");
        safety.getPersons().add(person);

        PersonsRepository e = Mockito.mock(PersonsRepository.class);
        Mockito.when(personsRepository.findByIds(person.getFirstName(), person.getLastName())).thenReturn(person);

        Persons personUpdated = personService.updatePerson(person, person.getFirstName(), person.getLastName());

        verify(personsRepository, Mockito.times(1)).update(person, person.getFirstName(), person.getLastName());
        Assert.assertEquals(person, personUpdated);


    }

    // put person by id ( user not found )
    @Test
    public void givenPerson_whenUpdateAPerson_then404sReceived() throws Exception {
        // given
        safety.setPersons(new ArrayList<>());

        Mockito.when(personsRepository.getSafety()).thenReturn(safety);
        setUpPerTest();
        person.setLastName("Boyd");
        person.setAddress("15 Culver St");
        person.setCity("Culver");
        person.setZip(97451);
        person.setEmail("jaboyd@email.com");
        person.setFirstName("John");
        person.setPhone("841-874-0000");
        safety.getPersons().add(person);

        PersonsRepository e = Mockito.mock(PersonsRepository.class);
        Mockito.when(personsRepository.findByIds(person.getFirstName(), person.getLastName())).thenReturn(null);

        Exception thrown = Assert.assertThrows(NotFoundException.class, () -> {
            personService.updatePerson(person, person.getFirstName(), person.getLastName());
        });

        verify(personsRepository, Mockito.times(1)).findByIds(person.getFirstName(), person.getLastName());
        Assert.assertEquals("L'utilisateur n'est pas trouvé avec ce firstname", thrown.getMessage());

    }

    // tests route supplementaire

    // getPersonInfo (user found)
    @Test
    public void givenPerson_whenGetPersonInfo_then200IsReceived() throws Exception {
        // given
        person.setLastName("Boyd");
        person.setAddress("15 Culver St");
        person.setCity("Culver");
        person.setZip(97451);
        person.setEmail("jaboyd@email.com");
        person.setFirstName("John");
        person.setPhone("841-874-0000");
        MedicalRecords medicalRecords = new MedicalRecords();

        medicalRecords.setBirthdate("01/01/1980");
        medicalRecords.setLastName("Boyd");
        medicalRecords.setFirstName("John");

        MedicalRecords medicalRecords2 = new MedicalRecords();

        medicalRecords2.setBirthdate("31/07/1990");
        medicalRecords2.setLastName("Boyd");
        medicalRecords2.setFirstName("Luc");

        PersonInfo personInfo = new PersonInfo();
        PersonInfo personInfo2 = new PersonInfo();
        List<PersonInfo> listPersonInfo = new ArrayList<PersonInfo>();
        personInfo.setLastName(person.getLastName());
        personInfo.setAddress(person.getAddress());
        personInfo.setAllergies(medicalRecords.getAllergies());
        personInfo.setMedications(medicalRecords.getMedications());
        personInfo.setEmail(person.getEmail());
        listPersonInfo.add(personInfo);

        personInfo2.setLastName(person2.getLastName());
        personInfo2.setAddress(person2.getAddress());
        personInfo2.setAllergies(medicalRecords2.getAllergies());
        personInfo2.setMedications(medicalRecords2.getMedications());
        personInfo2.setEmail(person2.getEmail());
        listPersonInfo.add(personInfo2);

        Mockito.when(personsRepository.findByIds(anyString(), anyString())).thenReturn(person);
        Mockito.when(personsRepository.getSafety()).thenReturn(safety);
        Mockito.when(personsRepository.findPersonsInfo(any(Persons.class))).thenReturn(medicalRecords);

        List<PersonInfo> personsInfo = personService.getPersonsInfo("John", "Boyd");

        assertEquals(listPersonInfo.get(1).getLastName(), personsInfo.get(1).getLastName());
        assertEquals(listPersonInfo.get(0).getLastName(), personsInfo.get(0).getLastName());

        verify(personsRepository, Mockito.times(1)).getSafety();

    }

    // get Persons email (successfull)
    @Test
    public void givenCity_whenGetPersonsEmail_then200IsReceived() throws Exception {
        personService.setPersonsRepository(personsRepository);

        List<String> listEmail = new ArrayList<>();
        PersonsEmail emails = new PersonsEmail();



        listEmail.add(person.getEmail());
        listEmail.add(person2.getEmail());
        emails.setEmail(listEmail);

        Mockito.when(personsRepository.getSafety()).thenReturn(safety);


        PersonsEmail personsEmails = personService.getPersonsEmail("Cluver");

        assertEquals(emails.getEmail(), personsEmails.getEmail());
        verify(personsRepository, Mockito.times(1)).getSafety();

    }

    // get Persons mobile (successfull)
    @Test
    public void givenStation_whenGetPersonsMobile_then200IsReceived() throws Exception {
        personService.setPersonsRepository(personsRepository);

        PersonsMobile mobile = new PersonsMobile();
        List<String> phone = new ArrayList<>();
        Firestations firestations = new Firestations();
        firestations.setAddress("15 street Cluver");

        firestations.setStation(2);
        safety.setFirestations(new ArrayList<>());
        safety.getFirestations().add(firestations);

        phone.add(person.getPhone());
        phone.add(person2.getPhone());
        mobile.setMobile(phone);

        Mockito.when(personsRepository.getSafety()).thenReturn(safety);


        PersonsMobile personsMobile = personService.getPersonsMobile(2);

        assertEquals(mobile.getMobile(), personsMobile.getMobile());
        verify(personsRepository, Mockito.times(1)).getSafety();

    }

    // get child by address (successfull)
    @Test
    public void givenAddress_whenGetChildByAddress_then200IsReceived() throws Exception {
        personService.setPersonsRepository(personsRepository);

        List<Persons> membres = new ArrayList<>();
        List<ChildAlerts> childList = new ArrayList<>();

        MedicalRecords medicalRecords = new MedicalRecords();

        medicalRecords.setBirthdate("01/01/1980");
        medicalRecords.setLastName("Boyd");
        medicalRecords.setFirstName("John");

        MedicalRecords medicalRecords2 = new MedicalRecords();

        medicalRecords2.setBirthdate("11/07/2010");
        medicalRecords2.setLastName("Boyd");
        medicalRecords2.setFirstName("Luc");

        MedicalRecords medicalRecords3 = new MedicalRecords();

        medicalRecords3.setBirthdate("11/07/2013");
        medicalRecords3.setLastName("Boyd");
        medicalRecords3.setFirstName("Loic");
        safety.setMedicalrecords(new ArrayList<>());
        safety.getMedicalrecords().add(medicalRecords);
        safety.getMedicalrecords().add(medicalRecords2);
        safety.getMedicalrecords().add(medicalRecords3);

        LocalDate curDate = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("LL/dd/yyyy", Locale.FRANCE);

        LocalDate birthdate = LocalDate.parse(medicalRecords.getBirthdate(), dtf);
        Period period = Period.between(birthdate, curDate);
        System.out.println("Period : "+period.getYears());
        birthdate = LocalDate.parse(medicalRecords2.getBirthdate(), dtf);
        period = Period.between(birthdate, curDate);
        System.out.println("Period : "+period.getYears());
        childList.add(new ChildAlerts(safety.getPersons().get(1).getLastName(), safety.getPersons().get(1).getFirstName(), period.getYears(), membres));
        membres.add(safety.getPersons().get(0));
        birthdate = LocalDate.parse(medicalRecords3.getBirthdate(), dtf);
        period = Period.between(birthdate, curDate);
        System.out.println("Period : "+period.getYears());

        Mockito.when(personsRepository.getSafety()).thenReturn(safety);

        List<ChildAlerts> childAlertsList = personService.getChildByAddress("15 street Cluver");

        assertEquals(childList.get(0).getFirstName(), childAlertsList.get(0).getFirstName());
        assertEquals(childList.get(0).getAge(), childAlertsList.get(0).getAge());
        assertEquals(childList.get(0).getMembres().size(), childAlertsList.get(0).getMembres().size());
        assertEquals(childList.get(0).getMembres().get(0).getFirstName(), childAlertsList.get(0).getMembres().get(0).getFirstName());
        assertEquals(childList.size(), childAlertsList.size());
        verify(personsRepository, Mockito.times(1)).getSafety();

    }


}
