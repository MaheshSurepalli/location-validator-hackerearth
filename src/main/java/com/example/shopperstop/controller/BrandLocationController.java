package com.example.shopperstop.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.shopperstop.entity.BrandLocation;
import com.example.shopperstop.service.BrandLocationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/brand-location/api")
@RequiredArgsConstructor
public class BrandLocationController {
	
	private final BrandLocationService brandLocationService;

	@PostMapping("/save")
	public ResponseEntity<String> saveBrandLocations()  {
		brandLocationService.saveBrandLocations();
		return ResponseEntity.ok("Brand locations saved successfully");
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<BrandLocation>> getAllBrandLocations() {
		List<BrandLocation> brandLocations = brandLocationService.getAllBrandLocations();
		return ResponseEntity.ok(brandLocations);
	}
	
	@GetMapping("/getByCityId")
	public ResponseEntity<List<BrandLocation>> getAllBrandLocationsByCityId(@RequestParam("cityId") Long cityId) {
		List<BrandLocation> brandLocations = brandLocationService.getAllBranchLocationsByCityId(cityId);
		return ResponseEntity.ok(brandLocations);
	}
	
	@GetMapping("/invalid")
	public ResponseEntity<String> setInValidBrand(@RequestParam("locationId") Long locationId) {
		String message = brandLocationService.setInValidBrand(locationId);
		return ResponseEntity.ok(message);
	}
	
	@GetMapping("/invalidLocations")
	public ResponseEntity<List<BrandLocation>> getInvalidLocation() {
		List<BrandLocation> inValidBrandLocations = brandLocationService.getInvalidBrandLocations();
		return ResponseEntity.ok(inValidBrandLocations) ;
	}

}
