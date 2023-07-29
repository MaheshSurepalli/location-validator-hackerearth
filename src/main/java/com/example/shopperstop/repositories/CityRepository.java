package com.example.shopperstop.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.shopperstop.entity.City;

public interface CityRepository extends MongoRepository<City, Long> {

}
