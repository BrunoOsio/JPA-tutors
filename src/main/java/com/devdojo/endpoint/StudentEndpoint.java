package com.devdojo.endpoint;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.devdojo.domain.Student;
import com.devdojo.error.ResourceNotFoundException;
import com.devdojo.repository.StudentRepository;

@RestController
@RequestMapping("/students")
public class StudentEndpoint {

	@Autowired
	private StudentRepository repo;
	
	@GetMapping
	public ResponseEntity<?> listAll() {
		return ResponseEntity.ok().body(repo.findAll());
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<?> getStudentById(@PathVariable Long id) {
		if(!studentExists(id)) {
			throw new ResourceNotFoundException("Não há nenhum estudante com id = " + id);
		}
		return ResponseEntity.of(repo.findById(id));			
	}

	@GetMapping(path = "/findByName/{name}")
	public ResponseEntity<?> getStudentsbyName(@PathVariable String name) {
		Optional<List<Student>> students = repo.findByNameIgnoreCaseContaining(name);
		
		if (students.isEmpty()) {
			throw new ResourceNotFoundException("There are no names which equals to = " + name);
		}
		return ResponseEntity.of(students);
	}

	@PostMapping
	public ResponseEntity<?> insert(@RequestBody Student student) {
		return ResponseEntity.ok(repo.save(student));
	}

	@DeleteMapping(path = "{/id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		if(!studentExists(id)) {
			throw new ResourceNotFoundException("Was not possible to delete the user");
		}
		repo.deleteById(id);	
		return ResponseEntity.ok().build();
	}

	@PutMapping
	public ResponseEntity<?> update(@RequestBody Student student) {
		if(student.getId() == null) {
			throw new ResourceNotFoundException("FATAL ERROR: Id was null");
		}
		repo.save(student);
		return ResponseEntity.ok().build();
	}

	private boolean studentExists(Long id) {
		Optional<Student> student = repo.findById(id);
		if (student.isEmpty()) {
			return false;
		}
		return true;
	}

}
