package com.example.shopperstop.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.example.shopperstop.entity.BrandLocation;
import com.example.shopperstop.service.PrimaryKeyGeneratorService;

@Component
public class BrandLocationListener extends AbstractMongoEventListener<BrandLocation> {
	public static final String SEQUENCE_NAME = "brand_location_sequence";
	
	@Autowired
	PrimaryKeyGeneratorService pkGeneratorService;	
	
	@Override
	public void onBeforeConvert(BeforeConvertEvent<BrandLocation> event) {
		if(event.getSource().getLocationId() == null) {
			event.getSource().setLocationId(pkGeneratorService.generateSequence(BrandLocationListener.SEQUENCE_NAME));
		}
	}
}