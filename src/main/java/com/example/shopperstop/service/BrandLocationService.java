package com.example.shopperstop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.shopperstop.entity.BrandLocation;
import com.example.shopperstop.entity.City;
import com.example.shopperstop.repositories.BrandLocationRepository;
import com.example.shopperstop.repositories.CityRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BrandLocationService {
	@Autowired
	private BrandLocationRepository brandLocationRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private WebClient webClient;

	public void saveBrandLocations() {
		try {
			List<City> cities = cityRepository.findAll();

			List<BrandLocation> brandLocations = new ArrayList<>();

			for (City city : cities) {
				String url = "https://www.shoppersstop.com/store-finder?q=" + city.getCityName();
				Map<String, Object> responseBody = webClient.get().uri(url).retrieve()
						.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
						}).block();

				log.debug("Response body: {}", responseBody);

				List<Map<String, Object>> results = (List<Map<String, Object>>) responseBody.get("results");
				for (Map<String, Object> result : results) {
					String displayName = (String) result.get("displayName");
					Map<String, Double> geoLocation = (Map<String, Double>) result.get("geoPoint");
					double latitude = geoLocation.get("latitude");
					double longitude = geoLocation.get("longitude");

					// Create a BrandLocation entity with all fields
					BrandLocation brandLocation = BrandLocation.builder().locationName(displayName).latitude(latitude)
							.longitude(longitude).city(city).isValid(true).build();

					log.debug("Brand Location: {}", brandLocation);

					brandLocations.add(brandLocation);
				}
			}

			brandLocationRepository.saveAll(brandLocations);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public List<BrandLocation> getAllBrandLocations() {
		try {
			return brandLocationRepository.findAll();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public List<BrandLocation> getAllBranchLocationsByCityId(Long cityId) {
		try {
			return brandLocationRepository.findByCity(City.builder().cityId(cityId).build());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public String setInValidBrand(Long locationId) {
		try {
			BrandLocation brandLocation = brandLocationRepository.findById(locationId)
					.orElseThrow(() -> new RuntimeException("Invalid Brand Location"));
			brandLocation.setValid(false);
			brandLocationRepository.save(brandLocation);
			return "Location marked as invalid";
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public List<BrandLocation> getInvalidBrandLocations() {
		try {
			List<BrandLocation> invalidBrandLocations = brandLocationRepository.findAllIbValidBrandLocations();
			return invalidBrandLocations;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
