package com.learn.controller;

import com.learn.dto.AuthenticationRequest;
import com.learn.dto.AuthenticationResponse;
import com.learn.service.AutheticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/auth")
public class AuthenticationController {

    @Autowired
    AutheticationService autheticationService;

    @PostMapping(value = "/user")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        String jwtToken = autheticationService.authenticate(request);

        if(null != jwtToken){
            return new ResponseEntity(new AuthenticationResponse(jwtToken), HttpStatus.OK);
        }
        return new ResponseEntity(new AuthenticationResponse("invalid credentials"), HttpStatus.UNAUTHORIZED);
    }
}
