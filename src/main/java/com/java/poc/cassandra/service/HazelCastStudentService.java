package com.java.poc.cassandra.service;

import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hazelcast.core.HazelcastInstance;
import com.java.poc.cassandra.config.KafkaProducer;
import com.java.poc.cassandra.exception.StudentNotFoundException;
import com.java.poc.cassandra.model.Student;
import com.java.poc.cassandra.repository.StudentRepository;
import com.java.poc.cassandra.utils.Utils;

@Service
public class HazelCastStudentService {

	private static final Logger LOGGER = LoggerFactory.getLogger(HazelCastStudentService.class);

	@Autowired
	private HazelcastInstance hazelcastInstance;

	@Autowired
	private StudentRepository studentRepository;

	private Map<String, Student> studentHazelCastMap;

	@Autowired
	private KafkaProducer kafkaProducer;

	@Value("${create.student.kafka.topic}")
	private String createStudentTopic;

	@Value("${update.student.kafka.topic}")
	private String updateStudentTopic;

	@PostConstruct
	public void init() {
		studentHazelCastMap = hazelcastInstance.getMap("hazelcastconfiguration");
	}

	// write behind - write in cache and write in cassandra latter
	public String createNewStudent(Student student) {
		student.setStudentId(UUID.randomUUID());
		kafkaProducer.sendMesageToKafka(createStudentTopic, Utils.toJson(student));
		return student.getStudentId().toString();
	}

	public Student getStudent(String studentId) {
		Student studentDb = studentHazelCastMap.get(studentId);
		if (studentDb == null) {
			LOGGER.info("\n\n***STUDENT IS FETCHED FROM DB***\n\n");
			studentDb = studentRepository.findByStudentId(UUID.fromString(studentId));
			if (studentDb == null) {
				throw new StudentNotFoundException("student doesn't exist!");
			}
			studentHazelCastMap.put(studentId, studentDb);
		}
		return studentDb;
	}

	// write behind - write in cache and write in cassandra latter
	public Student updateStudent(String studentId, Student student) {
		Student studentDb = this.getStudent(studentId);
		student.setStudentId(studentDb.getStudentId());
		studentHazelCastMap.put(studentDb.getStudentId().toString(), student);
		kafkaProducer.sendMesageToKafka(updateStudentTopic, Utils.toJson(student));
		return studentHazelCastMap.get(studentId);
	}

	public boolean deleteStudent(String studentId) {
		studentHazelCastMap.remove(studentId);
		studentRepository.deleteByStudentId(UUID.fromString(studentId));
		return true;
	}

	public boolean deleteAll() {
		studentHazelCastMap.clear();
		studentRepository.deleteAll();
		return true;
	}

	public Map<String, Student> getAllStudents() {
		return hazelcastInstance.getMap("hazelcastconfiguration");
	}
}
