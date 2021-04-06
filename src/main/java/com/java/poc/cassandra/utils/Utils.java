package com.java.poc.cassandra.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.poc.cassandra.model.Student;

public class Utils {

	private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	public static String toJson(Object object) {
		try {
			return OBJECT_MAPPER.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	public static Student getStudentFromJson(String string) {
		try {
			return OBJECT_MAPPER.readValue(string, Student.class);
		} catch (JsonMappingException e) {
			LOGGER.error(e.getMessage());
		} catch (JsonProcessingException e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}

}
