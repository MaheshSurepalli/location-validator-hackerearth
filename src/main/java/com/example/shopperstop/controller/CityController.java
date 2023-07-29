package com.example.shopperstop.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shopperstop.entity.City;
import com.example.shopperstop.service.CityService;

@RestController
@RequestMapping("/cities/api")
public class CityController {
    @Autowired
    private CityService cityService;

    @PostMapping("/save")
    public String saveCities() {
        return cityService.saveCities();
    }
    
    @GetMapping("/getAll")
    public List<City> getCities() {
        return cityService.getCities();
    }
}

