package Controller;

import SafetyNetAlert.Controller.Exception.*;
import SafetyNetAlert.DTO.ChildAlerts;
import SafetyNetAlert.DTO.PersonInfo;
import SafetyNetAlert.DTO.PersonsEmail;
import SafetyNetAlert.DTO.PersonsMobile;
import SafetyNetAlert.Model.Persons;
import SafetyNetAlert.SafetyApplication;
import SafetyNetAlert.Service.IPersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * test: save a new person, delete a person, get and update a person,
 * get all info of a person, get all emails of persons living in a given city, get all mobile of persons from a firestation number,
 * get all child living in a given address.
 * @author Mougni
 *
 */
@AutoConfigureMockMvc
@EnableWebMvc
@SpringBootTest(classes = SafetyApplication.class)
public class PersonsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private Persons person;

    @MockBean
    private IPersonService personsService;

    private static final Logger logger = LogManager.getLogger(PersonsControllerTest.class);

    @BeforeEach
    public void setUpPerTest(){

        person = new Persons();

    }

    // everything okay when add a persons
    @Test
    public void givenPerson_whenPersonAdded_then202IsReceived() throws Exception {
        setUpPerTest();
        person.setLastName("testLastName");
        person.setAddress("testAddress");
        person.setCity("testCity");
        person.setZip(97452);
        person.setEmail("test@email.com");
        person.setFirstName("testFirstName");
        person.setPhone("841-874-0000");



        mockMvc.perform( MockMvcRequestBuilders
                        .post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"testFirstName\", \"lastName\":\"testLastName\", \"address\":\"testAddress\", \"city\":\"testCity\", \"zip\":97452, \"phone\":\"841-874-0000\", \"email\":\"test@email.com\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());


    }

    //  add nothing when add a persons because no data in body
    @Test
    public void givenPerson_whenPersonAddedWithoutData_then_404IsReceived() throws Exception {

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());


    }

    // get all persons
    @Test
    public void givenPerson_whenGetPersons_then200IsReceived() throws Exception {
        setUpPerTest();


        mockMvc.perform( MockMvcRequestBuilders
                        .get("/person")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // get person by id ( user not existing )
    @Test
    public void givenPerson_whenGetAPerson_then404IsReceived() throws Exception {
        setUpPerTest();

        when(personsService.getPersons("Jon", "Boyd")).thenThrow(PersonsNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/person/Jon&Boyd")
                .accept(MediaType.APPLICATION_JSON)
                        .contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
    }

    // get person by id ( user found )
    @Test
    public void givenPerson_whenGetAPerson_then200IsReceived() throws Exception {
        // given
        setUpPerTest();
        person.setLastName("Boyd");
        person.setAddress("1509 Culver St");
        person.setCity("Culver");
        person.setZip(97451);
        person.setEmail("jaboyd@email.com");
        person.setFirstName("John");
        person.setPhone("841-874-6512");

        // when
        when(personsService.getPersons("John", "Boyd")).thenReturn(person);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/person/John/Boyd")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    // try to delete a person that doesnt exist
    @Test
    public void givenPerson_whenDeleteAPerson_then200IsReceived() throws Exception
    {
        // given
        setUpPerTest();
        // when

        Mockito.doNothing().when(personsService).deleteAccount("John", "Boyd");

        mockMvc.perform(MockMvcRequestBuilders.delete("/person/John/Boyd")
                        .accept(MediaType.APPLICATION_JSON)
                                .contentType("application/json;charset=UTF-8"))
                .andExpect(status().isAccepted())
                .andDo(print());
    }

    @Test
    public void givenPersonThatDontExist_whenDeleteAPerson_then404IsReceived() throws Exception
    {
        // given
        setUpPerTest();
        // when

        doThrow(PersonsNotFoundException.class).when(personsService).deleteAccount("Jon", "Boyd");

        mockMvc.perform(MockMvcRequestBuilders.delete("/person/Jon&Boyd")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType("application/json;charset=UTF-8"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    //put correct
    @Test
    public void givenPerson_whenUpdateAPerson_then200IsReceived() throws Exception {
        // given
        setUpPerTest();
        person.setLastName("Boyd");
        person.setAddress("1509 Culver St");
        person.setCity("Culver");
        person.setZip(97000);
        person.setEmail("jaboyd@email.com");
        person.setFirstName("John");
        person.setPhone("841-874-0000");

        // when
        when(personsService.updatePerson(person, "John", "Boyd")).thenReturn(person);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/person/John/Boyd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(person))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andReturn().getResponse().getContentAsString();
    }

    // put uncorrect
    @Test
    public void givenPerson_whenUpdateAPerson_then404IsReceived() throws Exception {
        // given

        setUpPerTest();

        person.setLastName("Paul");
        person.setAddress("1509 Culver St");
        person.setCity("Culver");
        person.setZip(97000);
        person.setEmail("jaboyd@email.com");
        person.setFirstName("Mo");
        person.setPhone("841-874-0000");

        // when
        doThrow(PersonsNotFoundException.class).when(personsService).updatePerson(any(Persons.class),anyString(), anyString());

        String requestBody = asJsonString(person);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/person/Mo/Paul")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    // test routes supplementaire qui concerne la classe Persons
    @Test
    public void givenPerson_whenGetAPersonInfo_then200IsReceived() throws Exception {
        // given
        setUpPerTest();
        person.setLastName("Boyd");
        person.setAddress("1509 Culver St");
        person.setCity("Culver");
        person.setZip(97451);
        person.setEmail("jaboyd@email.com");
        person.setFirstName("John");
        person.setPhone("841-874-6512");

        List<PersonInfo> list = new ArrayList<PersonInfo>();

        // when
        when(personsService.getPersonsInfo(anyString(), anyString())).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/personInfo/John/Boyd")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void givenCity_whenGetAPersonEmails_then200IsReceived() throws Exception {
        // given
        setUpPerTest();
        person.setLastName("Boyd");
        person.setAddress("1509 Culver St");
        person.setCity("Culver");
        person.setZip(97451);
        person.setEmail("jaboyd@email.com");
        person.setFirstName("John");
        person.setPhone("841-874-6512");

        // when
        when(personsService.getPersonsEmail(anyString())).thenReturn(any(PersonsEmail.class));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/communityEmail/Cluver")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void givenStation_whenGetAPersonMobile_then200IsReceived() throws Exception {

        // when
        when(personsService.getPersonsMobile(anyInt())).thenReturn(any(PersonsMobile.class));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/phoneAlert/2")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void givenAddress_whenGetChildAlert_then200IsReceived() throws Exception {

        List<ChildAlerts> list = new ArrayList<ChildAlerts>();

        // when
        when(personsService.getChildByAddress(anyString())).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/childAlert/1509 Culver St")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }





    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
