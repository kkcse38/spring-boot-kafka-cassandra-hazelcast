package com.java.poc.cassandra.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.java.poc.cassandra.exception.StudentNotFoundException;
import com.java.poc.cassandra.model.Student;
import com.java.poc.cassandra.repository.StudentRepository;

/**
 * @author kanhaiya kumar
 *
 */
@Service
@CacheConfig(cacheNames = { "student" })
public class StudentService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

	@Autowired
	private StudentRepository studentRepository;

	public Student createNewStudent(Student student) {
		return studentRepository.save(student);
	}

	@Cacheable
	public Student getStudent(String studentId) {
		LOGGER.info("\n\n***STUDENT IS FETCHED FROM DB***\n\n");
		Student studentDb = studentRepository.findByStudentId(UUID.fromString(studentId));
		if (studentDb == null) {
			throw new StudentNotFoundException("student doesn't exist!");
		}
		return studentDb;
	}

	@CachePut(key = "#studentId")
	public Student updateStudent(String studentId, Student student) {
		Student studentDb = studentRepository.findByStudentId(UUID.fromString(studentId));
		if (studentDb == null) {
			throw new StudentNotFoundException("student doesn't exist to update!");
		}
		student.setStudentId(studentDb.getStudentId());
		return studentRepository.save(student);
	}

	@CacheEvict(key = "#studentId")
	public boolean deleteStudent(String studentId) {
		studentRepository.deleteByStudentId(UUID.fromString(studentId));
		return true;
	}

	@CacheEvict(allEntries = true)
	public boolean deleteAll() {
		studentRepository.deleteAll();
		return true;
	}
}
