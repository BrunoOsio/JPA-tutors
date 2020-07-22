package com.devdojo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devdojo.domain.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{
	
	public Optional<List<Student>> findByNameIgnoreCaseContaining(String name);
	

}
