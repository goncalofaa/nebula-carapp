package com.nebula.nebulacarapp;

import com.nebula.nebulacarapp.controller.PrivateController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class NebulaCarappApplicationTests {

    @InjectMocks
    private PrivateController privateController;
    @Test
    void clientCallsPrivateStatusReturns200(){
        ResponseEntity<String> clientResponse = privateController.getStatus();
        Assertions.assertAll(
                () -> assertEquals(HttpStatus.OK, clientResponse.getStatusCode()),
                () -> assertEquals("OK", clientResponse.getBody())
        );
    }

}
