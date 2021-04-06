package com.java.poc.cassandra.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.poc.cassandra.exception.StudentNotFoundException;
import com.java.poc.cassandra.model.Student;
import com.java.poc.cassandra.repository.StudentRepository;

/**
 * @author kanhaiya kumar
 *
 */
@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;

	public Student createNewStudent(Student student) {
		return studentRepository.save(student);
	}

	public Student getStudent(String studentId) {
		Student studentDb = studentRepository.findByStudentId(UUID.fromString(studentId));
		if (studentDb == null) {
			throw new StudentNotFoundException("student doesn't exist!");
		}
		return studentDb;
	}

	public Student updateStudent(String studentId, Student student) {
		Student studentDb = this.getStudent(studentId);
		student.setStudentId(studentDb.getStudentId());
		return studentRepository.save(student);
	}

	public boolean deleteStudent(String studentId) {
		studentRepository.deleteByStudentId(UUID.fromString(studentId));
		return true;
	}
	
	public boolean deleteAll() {
		studentRepository.deleteAll();
		return true;
	}
}
