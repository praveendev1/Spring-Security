package com.learn.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/org")
public class OrganizationController {

    @GetMapping(path = "/associates/{id}")
    public String getAssociateDetails(@PathVariable(value = "id") int id){
        return String.format(" Associate %d is retrieved", id);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping(path = "/managers/{id}")
    public String getManagerDetails(@PathVariable(value = "id") int id){
        return String.format(" Manager %d is retrieved", id);
    }
}
