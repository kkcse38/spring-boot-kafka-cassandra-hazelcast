package com.java.poc.cassandra.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.poc.cassandra.model.Student;
import com.java.poc.cassandra.service.HazelCastStudentService;

@RestController
@RequestMapping("/hazelcast")
public class HazelCastController {

	@Autowired
	private HazelCastStudentService hazelCastStudentService;

	@PostMapping("/write-data")
	public ResponseEntity<?> writeDataToHazelCast(@RequestBody Student student) {
		String studentId = hazelCastStudentService.createNewStudent(student);
		return ResponseEntity.status(HttpStatus.CREATED).body(studentId);
	}

	@GetMapping("/read-data")
	public ResponseEntity<?> readDataFromHazelCast(@RequestParam(required = true) String studentId) {
		Student student = hazelCastStudentService.getStudent(studentId);
		return ResponseEntity.status(HttpStatus.OK).body(student);
	}

	@PutMapping("/update-data")
	public ResponseEntity<?> updateDataToHazelCast(@RequestParam(required = true) String studentId,
			@RequestBody Student student) {
		Student studentResp = hazelCastStudentService.updateStudent(studentId, student);
		return ResponseEntity.status(HttpStatus.OK).body(studentResp);
	}

	@DeleteMapping("/delete-data")
	public ResponseEntity<?> deleteDataToHazelCast(@RequestParam(required = true) String studentId) {
		boolean deleteRep = hazelCastStudentService.deleteStudent(studentId);
		return ResponseEntity.status(HttpStatus.OK).body(deleteRep);
	}

	@DeleteMapping("/delete-all-data")
	public ResponseEntity<?> deleteAll() {
		boolean deleteRep = hazelCastStudentService.deleteAll();
		return ResponseEntity.status(HttpStatus.OK).body(deleteRep);
	}
}
