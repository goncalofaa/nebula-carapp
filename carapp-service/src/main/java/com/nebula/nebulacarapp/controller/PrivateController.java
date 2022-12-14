package com.nebula.nebulacarapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private")
public class PrivateController {

    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }

}
