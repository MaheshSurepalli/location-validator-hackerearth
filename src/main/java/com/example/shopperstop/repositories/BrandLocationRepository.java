package com.example.shopperstop.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.shopperstop.entity.BrandLocation;
import com.example.shopperstop.entity.City;

public interface BrandLocationRepository extends MongoRepository<BrandLocation, Long> {
	List<BrandLocation> findByCity(City city);

	@Query(value = "{'isValid': false}")
	List<BrandLocation> findAllIbValidBrandLocations();
}
