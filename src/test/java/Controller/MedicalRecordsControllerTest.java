package Controller;

import SafetyNetAlert.Controller.Exception.*;
import SafetyNetAlert.Controller.MedicalRecordsController;
import SafetyNetAlert.Model.MedicalRecords;
import SafetyNetAlert.Service.IMedicalRecordService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * test: save a new medical record, delete a medical record, get and update a medical record.
 * @author Mougni
 *
 */
@SpringBootTest(classes = MedicalRecordsController.class)
@AutoConfigureMockMvc
@EnableWebMvc
public class MedicalRecordsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private MedicalRecords medicalRecords;
    @MockBean
    private IMedicalRecordService medicalRecordService;

    private static final Logger logger = LogManager.getLogger(MedicalRecordsControllerTest.class);

    @BeforeEach
    public void setUpPerTest(){

        medicalRecords = new MedicalRecords();

    }

    // everything okay when add a MedicalRecord
    @Test
    public void givenMedicalRecord_whenMedicalRecordAdded_then202IsReceived() throws Exception {
        setUpPerTest();
        medicalRecords.setBirthdate("01/01/1980");
        medicalRecords.setLastName("Boyd");
        medicalRecords.setFirstName("John");



        mockMvc.perform( MockMvcRequestBuilders
                        .post("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(medicalRecords))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());


    }

    //  add nothing when add a MedicalRecord because no data in body
    @Test
    public void givenMedicalRecord_whenMedicalRecordAddedWithoutData_then_404IsReceived() throws Exception {
        //setUpPerTest();



        mockMvc.perform( MockMvcRequestBuilders
                        .post("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());


    }


    // get all MedicalRecord
    @Test
    public void whenGetAllMedicalRecord_then200IsReceived() throws Exception {
        setUpPerTest();


        mockMvc.perform( MockMvcRequestBuilders
                        .get("/medicalRecord")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


    // get medicalRecord by firstname and lastname ( medicalRecord not existing )
    @Test
    public void givenFirstNameAndLastName_whenGetAMedicalRecord_then404IsReceived() throws Exception {
        setUpPerTest();

        when(medicalRecordService.getMedicalRecords("Jon", "Boyd")).thenThrow(MedicalRecordsNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/medicalRecord/Jon&Boyd")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isNotFound())
                //.andExpect(MockMvcResultMatchers.jsonPath("$.person.firstName").value("John"))
                .andReturn().getResponse().getContentAsString();
    }

    // get medicalRecord by name ( name found )
    @Test
    public void givenName_whenGetAMedicalRecord_then200IsReceived() throws Exception {
        // given
        setUpPerTest();
        medicalRecords.setBirthdate("01/01/1980");
        medicalRecords.setLastName("Boyd");
        medicalRecords.setFirstName("John");


        // when
        when(medicalRecordService.getMedicalRecords("John", "Boyd")).thenReturn(medicalRecords);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/medicalRecord/John&Boyd")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                //.andExpect(MockMvcResultMatchers.jsonPath("$.person.firstName").value("John"))
                .andReturn().getResponse().getContentAsString();
    }


    // try to delete a medicalRecord that doesnt exist
    @Test
    public void givenName_whenDeleteMedicalRecord_then200IsReceived() throws Exception
    {
        // given
        setUpPerTest();
        // when

        //Mockito.doThrow(NameNotFoundException.class).when(personsService).deleteAccount("John", "Boyd");
        Mockito.doNothing().when(medicalRecordService).deleteAccount("John", "Boyd");

        mockMvc.perform(MockMvcRequestBuilders.delete("/medicalRecord/John&Boyd")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType("application/json;charset=UTF-8"))
                .andExpect(status().isAccepted())
                .andDo(print());
    }

    @Test
    public void givenNameThatDontExist_whenDeleteAMedicalRecord_then404IsReceived() throws Exception
    {
        // given
        setUpPerTest();
        // when

        //Mockito.doThrow(NameNotFoundException.class).when(personsService).deleteAccount("John", "Boyd");
        doThrow(MedicalRecordsNotFoundException.class).when(medicalRecordService).deleteAccount("Jon", "Boyd");

        mockMvc.perform(MockMvcRequestBuilders.delete("/medicalRecord/Jon&Boyd")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType("application/json;charset=UTF-8"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    //put correct
    @Test
    public void givenMedicalRecord_whenUpdateAMedicalRecord_then200IsReceived() throws Exception {
        // given
        setUpPerTest();

        medicalRecords.setBirthdate("01/01/1980");
        medicalRecords.setLastName("Boyd");
        medicalRecords.setFirstName("John");

        // when
        when(medicalRecordService.updateMedicalRecords(medicalRecords, "John", "Boyd")).thenReturn(medicalRecords);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/medicalRecord/John&Boyd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(medicalRecords))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted())
                //.andExpect(MockMvcResultMatchers.jsonPath("$.person.firstName").value("John"))
                .andReturn().getResponse().getContentAsString();
    }


    // put uncorrect
    @Test
    public void givenMedicalRecord_whenUpdateAMedicalRecord_then404IsReceived() throws Exception {
        // given

        setUpPerTest();
        medicalRecords.setBirthdate("01/01/1980");
        medicalRecords.setLastName("Boyd");
        medicalRecords.setFirstName("Jon");



        // when
        //doThrow(NameNotFoundException.class).when(personsService).getPersons("Jon", "Paul");
        //Mockito.when(personsService.updatePerson(person, "Jon", "Paul")).thenThrow(NameNotFoundException.class);
        doThrow(MedicalRecordsNotFoundException.class).when(medicalRecordService).updateMedicalRecords(any(MedicalRecords.class), anyString(), anyString());

        String requestBody = asJsonString(medicalRecords);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/medicalRecord/Jon&Boyd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
