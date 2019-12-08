package br.com.mercadolivre.controller;

import br.com.mercadolivre.contract.SimianContract;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SimianControllerTest {

    @LocalServerPort
    private int port;

    private String simianUrl;

    private String statsUrl;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    public void setup(){
        this.simianUrl = "http://localhost:" + port + "/simian";
        this.statsUrl = "http://localhost:" + port + "/stats";
    }

    @Test
    @Order(1)
    public void shouldPostSimianFirstExample(){

        String[] dna = new String[]{
            "CTGAGA","CTGAGC","TATTGT","AGAGGG","CCCCTA","TCACTG"
        };

        SimianContract simianContract = SimianContract.builder()
                .dna(Arrays.asList(dna))
                .build();

         ResponseEntity<String> responseEntity = testRestTemplate
                .postForEntity(simianUrl, simianContract, String.class);

         assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @Order(2)
    public void shouldPostSimianSecondExample(){

        String[] dna = new String[]{
            "CTGAGA","CTATGC","TATTGT","AGAGGG","CCCCTA","TCACTG"
        };

        SimianContract simianContract = SimianContract.builder()
                .dna(Arrays.asList(dna))
                .build();

        ResponseEntity<String> responseEntity = testRestTemplate
                .postForEntity(simianUrl, simianContract, String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @Order(3)
    public void shouldPostHumanExample(){

        String[] dna = new String[]{
            "ATGCGA","CAGTGC","TTATTT","AGACGG","GCGTCA","TCACTG"
        };

        SimianContract simianContract = SimianContract.builder()
                .dna(Arrays.asList(dna))
                .build();

        ResponseEntity<String> responseEntity = testRestTemplate
                .postForEntity(simianUrl, simianContract, String.class);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @Test
    @Order(4)
    public void shouldGetStats(){

        ResponseEntity<String> responseEntity = testRestTemplate
                .getForEntity(statsUrl, String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}
