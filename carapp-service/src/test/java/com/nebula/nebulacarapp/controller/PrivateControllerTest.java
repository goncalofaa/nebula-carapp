package com.nebula.nebulacarapp.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class PrivateControllerTest {

    @InjectMocks
    PrivateController privateController;

    @Test
    void whenPrivateStatusCalled_returnOK(){
        ResponseEntity<String> response = privateController.getStatus();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
