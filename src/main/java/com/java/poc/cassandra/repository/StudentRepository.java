package com.java.poc.cassandra.repository;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.java.poc.cassandra.model.Student;


/**
 * @author kanhaiya kumar
 */
@Repository
public interface StudentRepository extends CassandraRepository<Student, UUID> {

	Student findByStudentId(UUID studentId);

	void deleteByStudentId(UUID studentId);

}
