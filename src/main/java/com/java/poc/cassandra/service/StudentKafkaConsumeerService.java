package com.java.poc.cassandra.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.poc.cassandra.model.Student;
import com.java.poc.cassandra.repository.StudentRepository;

@Service
public class StudentKafkaConsumeerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentKafkaConsumeerService.class);

	@Autowired
	private StudentRepository studentRepository;

	public Student createNewStudent(Student student) {
		LOGGER.info("\n\n** STUDENT IS CREATED **\n\n");
		return studentRepository.save(student);
	}

	public Student updateStudent(Student student) {
		LOGGER.info("\n\n** STUDENT IS UPDATED **\n\n");
		return studentRepository.save(student);
	}

}
