package com.learn.service;

import com.learn.dto.AuthenticationRequest;
import com.learn.dto.UserDetails;
import com.learn.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AutheticationService {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    public String authenticate(AuthenticationRequest authenticationRequest){

        UserDetails userDetails = getDetails(authenticationRequest);

        if(null != userDetails){
            String jwtToken = jwtTokenProvider.createToken(authenticationRequest.getUsername(), userDetails.getRoles(),
                    userDetails.getDesignation(), userDetails.getLocation(), userDetails.getYearsOfExperince());
            return jwtToken;
        }

        return null;
    }

    private UserDetails getDetails(AuthenticationRequest authenticationRequest){
        if("manager".equalsIgnoreCase(authenticationRequest.getUsername()) && "manager1234".equalsIgnoreCase(authenticationRequest.getPassword())){
            return new UserDetails(Arrays.asList("MANAGER"), authenticationRequest.getUsername(),
                    "Engineering manager", "SLC", "12");
        }

        if("associate".equalsIgnoreCase(authenticationRequest.getUsername()) && "associate1234".equalsIgnoreCase(authenticationRequest.getPassword())){
                return new UserDetails(Arrays.asList("ASSOCIATE"), authenticationRequest.getUsername(),
                        "Software engineer", "India", "7");
            }
        return null;
    }
}
