package com.java.poc.cassandra.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.poc.cassandra.config.KafkaProducer;
import com.java.poc.cassandra.model.Student;
import com.java.poc.cassandra.service.StudentService;
import com.java.poc.cassandra.utils.Utils;

/**
 * @author kanhaiya kumar
 *
 */
@RestController
@RequestMapping("/api")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@Autowired
	private KafkaProducer kafkaProducer;

	@Value("${create.student.kafka.topic}")
	private String createStudentTopic;

	@PostMapping("/student")
	public ResponseEntity<?> createStudent(@RequestBody Student student) {
		student.setStudentId(UUID.randomUUID());
		kafkaProducer.sendMesageToKafka(createStudentTopic, Utils.toJson(student));
		return ResponseEntity.status(HttpStatus.CREATED).body(student.getStudentId());
	}

	@GetMapping("/student/{studentId}")
	public ResponseEntity<?> getStudentFromdb(@PathVariable(required = true) String studentId) {
		Student studentResp = studentService.getStudent(studentId);
		return ResponseEntity.status(HttpStatus.OK).body(studentResp);
	}

	@PutMapping("/student/{studentId}")
	public ResponseEntity<?> updateStudent(@PathVariable(required = true) String studentId,
			@RequestBody Student student) {
		Student studentResp = studentService.updateStudent(studentId, student);
		return ResponseEntity.status(HttpStatus.OK).body(studentResp);
	}

	@DeleteMapping("/student/{studentId}")
	public ResponseEntity<?> updateStudent(@PathVariable(required = true) String studentId) {
		boolean response = studentService.deleteStudent(studentId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@DeleteMapping("/student/delete/all")
	public ResponseEntity<?> updateStudent() {
		boolean response = studentService.deleteAll();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
