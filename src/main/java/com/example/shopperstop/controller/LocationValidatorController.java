package com.example.shopperstop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.shopperstop.service.CityService;

@Controller
@RequestMapping("location-validator")
public class LocationValidatorController {

	@Autowired
	private CityService cityService;

	@GetMapping
	public String showValidatorPage(ModelMap model) {
		model.addAttribute("cities", cityService.getCities());
		return "index";
	}
}
