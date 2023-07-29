package com.example.shopperstop.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "brand_locations")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandLocation {
	
	@Id
	private Long locationId;
	
	@Indexed(unique = true)
	@Field("location_name")
	private String locationName;
	
	private double latitude;
	
	private double longitude;
	
	@DocumentReference
	private City city;
	
	private boolean isValid;
	
	
}
