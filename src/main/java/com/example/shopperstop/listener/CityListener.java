package com.example.shopperstop.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.example.shopperstop.entity.City;
import com.example.shopperstop.service.PrimaryKeyGeneratorService;

@Component
public class CityListener extends AbstractMongoEventListener<City> {
	public static final String SEQUENCE_NAME = "city_sequence";

	@Autowired
	PrimaryKeyGeneratorService pkGeneratorService;

	@Override
	public void onBeforeConvert(BeforeConvertEvent<City> event) {
		if (event.getSource().getCityId() == null) {
			event.getSource().setCityId(pkGeneratorService.generateSequence(CityListener.SEQUENCE_NAME));
		}
	}
}
