package com.java.poc.cassandra.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.java.poc.cassandra.model.Student;
import com.java.poc.cassandra.service.StudentService;
import com.java.poc.cassandra.utils.Utils;

@Service
public class KafkaConsumer {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

	@Autowired
	private StudentService studentService;

	@KafkaListener(topics = "${create.student.kafka.topic}", groupId = "create-student-group")
	public void consumeCreateStudentMessage(String studentJson) {
		LOGGER.debug("\n\n** MESSAGE IS CONSUMED FOR CREATE STUDENT **\n\n");
		Student student = Utils.getStudentFromJson(studentJson);
		studentService.createNewStudent(student);
	}

}
