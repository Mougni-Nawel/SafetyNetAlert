package controller;

import SafetyNetAlert.controller.exception.*;
import SafetyNetAlert.controller.FirestationsController;
import SafetyNetAlert.dto.FireAddress;
import SafetyNetAlert.dto.FirestationByStation;
import SafetyNetAlert.dto.Flood;
import SafetyNetAlert.model.Firestations;
import SafetyNetAlert.service.IFirestationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * test: save a new firestation, delete a firestation, get and update a firestation,
 * get a list of resident from address, get list of persons from station number, returns a list of households served by firestation.
 * @author Mougni
 *
 */
@SpringBootTest(classes = FirestationsController.class)
@AutoConfigureMockMvc
@EnableWebMvc
public class FirestationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private Firestations firestations;

    @MockBean
    private IFirestationService firestationService;

    private static final Logger logger = LogManager.getLogger(FirestationControllerTest.class);

    @BeforeEach
    public void setUpPerTest(){

        firestations = new Firestations();

    }

    // everything okay when add a firestation
    @Test
    public void givenFirestation_whenFirestationAdded_then202IsReceived() throws Exception {
        setUpPerTest();
        firestations.setAddress("11 street Cluver");

        firestations.setStation(5);



        mockMvc.perform( MockMvcRequestBuilders
                        .post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(firestations))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());


    }

    //  add nothing when add a firestation because no data in body
    @Test
    public void givenFirestation_whenFirestationAddedWithoutData_then_404IsReceived() throws Exception {


        mockMvc.perform( MockMvcRequestBuilders
                        .post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());


    }

    // get all firestation
    @Test
    public void givenFirestation_whenGetFirestation_then200IsReceived() throws Exception {
        setUpPerTest();


        mockMvc.perform( MockMvcRequestBuilders
                        .get("/firestation/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // get firestation by address ( firestation not existing )
    @Test
    public void givenFirestation_whenGetAFirestation_then404IsReceived() throws Exception {
        setUpPerTest();

        when(firestationService.getFirestations("11 street Cluver")).thenThrow(new NotFoundException("Firestation not found with this address"));

        Assertions.assertThatThrownBy(() ->
                mockMvc.perform(MockMvcRequestBuilders
                                .get("/firestation/11 street Cluver")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType("application/json;charset=UTF-8"))
                        .andDo(print())
                        .andExpect(status().isNotFound())
                        .andReturn().getResponse().getContentAsString())
                .hasCause(new NotFoundException("Firestation not found with this address"));


    }

    // get firestation by address ( address found )
    @Test
    public void givenFirestation_whenGetAFirestation_then200IsReceived() throws Exception {
        // given
        setUpPerTest();
        firestations.setAddress("11 street Cluver");

        firestations.setStation(5);


        // when
        when(firestationService.getFirestations("1509 Culver St")).thenReturn(firestations);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/firestation/1509 Culver St")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    // try to delete a firestation that doesnt exist
    @Test
    public void givenFirestation_whenDeleteAFirestation_then200IsReceived() throws Exception
    {
        // given
        setUpPerTest();
        // when

        Mockito.doNothing().when(firestationService).deleteFirestation("1509 Culver St");

        mockMvc.perform(MockMvcRequestBuilders.delete("/firestation/1509 Culver St")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType("application/json;charset=UTF-8"))
                .andExpect(status().isAccepted())
                .andDo(print());
    }

    @Test
    public void givenFirestationThatDontExist_whenDeleteAFirestation_then404IsReceived() throws Exception
    {
        // given
        setUpPerTest();
        // when


        doThrow(new NotFoundException("Firestation not found with this address")).when(firestationService).deleteFirestation("150 Culver St");

        Assertions.assertThatThrownBy(() ->
                mockMvc.perform(MockMvcRequestBuilders.delete("/firestation/150 Culver St")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType("application/json;charset=UTF-8"))
                        .andExpect(status().isNotFound())
                        .andDo(print()))
                .hasCause(new NotFoundException("Firestation not found with this address"));
    }

    //put correct
    @Test
    public void givenPerson_whenUpdateAPerson_then200IsReceived() throws Exception {
        // given
        setUpPerTest();
        firestations.setAddress("11 street Cluver");

        firestations.setStation(5);

        // when
        when(firestationService.updateFirestations("1509 Culver St",firestations)).thenReturn(firestations);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/firestation/1509 Culver St")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(firestations))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andReturn().getResponse().getContentAsString();
    }


    // put uncorrect
    @Test
    public void givenFirestation_whenUpdateAFirestation_then404IsReceived() throws Exception {
        // given

        setUpPerTest();
        firestations.setAddress("19 street Cluver");

        firestations.setStation(5);



        // when
        doThrow(new NotFoundException("Firestation not found with this address")).when(firestationService).updateFirestations(anyString(),any(Firestations.class));

        String requestBody = asJsonString(firestations);

        Assertions.assertThatThrownBy(() ->
                mockMvc.perform(MockMvcRequestBuilders
                                .put("/firestation/11 street Cluver")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                        .andExpect(status().isNotFound())
                        .andDo(print()))
                .hasCause(new NotFoundException("Firestation not found with this address"));


    }

    // routes supplementaire
    @Test
    public void givenAddress_whenGetFireAddress_then200IsReceived() throws Exception {
        // given
        setUpPerTest();



        // when
        when(firestationService.getPersonsFromAddress(anyString())).thenReturn(any(FireAddress.class));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/firestation/fire?address=1509 Culver St")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void givenStation_whenGetFirestationByStation_then200IsReceived() throws Exception {
        // given
        setUpPerTest();



        // when
        when(firestationService.getPersonsFromStation(anyInt())).thenReturn(any(FirestationByStation.class));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/firestation?stationNumber=1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void givenListStations_whenGetFlood_then200IsReceived() throws Exception {
        // given
        setUpPerTest();

        List<Integer> listStation = new ArrayList<Integer>();
        List<Flood> listReturned = new ArrayList<Flood>();

        // when
        when(firestationService.getHearthByStations(listStation)).thenReturn(listReturned);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/firestation/flood/stations?stations=1,4")
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
