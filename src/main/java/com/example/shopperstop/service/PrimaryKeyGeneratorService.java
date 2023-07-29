package com.example.shopperstop.service;


import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.shopperstop.entity.PrimarySequence;

@Service
public class PrimaryKeyGeneratorService {

	@Autowired
	MongoOperations mongoOperations;

	public long generateSequence(String sequenceName) {
		PrimarySequence counter = mongoOperations.findAndModify(query(where("_id").is(sequenceName)),
				new Update().inc("sequence", 1), options().returnNew(true).upsert(true), PrimarySequence.class);
		return !Objects.isNull(counter) ? counter.getSequence() : 1;
	}

}