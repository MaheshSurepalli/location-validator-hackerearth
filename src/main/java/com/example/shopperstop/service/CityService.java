package com.example.shopperstop.service;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shopperstop.entity.City;
import com.example.shopperstop.repositories.CityRepository;

@Service
public class CityService {

	@Autowired
	CityRepository cityRepository;

	public String saveCities() {

		try {
			List<City> cities = new ArrayList<>();
			String url = "https://www.shoppersstop.com/store-finder";
			Document doc = Jsoup.connect(url).get();
			Elements cityOptions = doc.select("#city-name option");
			cityOptions.forEach(city -> cities.add(City.builder().cityName(city.text()).build()));
			cityRepository.saveAll(cities);
			return "Cities saved successfully";
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public List<City> getCities() {

		try {
			return cityRepository.findAll();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
