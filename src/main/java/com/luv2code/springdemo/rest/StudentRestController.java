package com.luv2code.springdemo.rest;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springdemo.entity.Student;

@RestController
@RequestMapping("/api")
public class StudentRestController {
	
	
	// define endpoint for "/students" - return list of students
	
	@GetMapping("/students")
	public List<Student> getStudents() {
		List<Student> theStudents = new ArrayList<>();
		
		theStudents.add(new Student("Poornima", "Patel"));
		theStudents.add(new Student("Mario", "Rossi"));
		theStudents.add(new Student("Mary", "Smith"));
		
		return theStudents;
	}
	
	// define endpoint for "/students/{studentId}"
	
	@GetMapping("/students/{studentId}")
	public Student getStudent(@PathVariable int studentId) {
		List<Student> theStudents = new ArrayList<>();
		
		theStudents.add(new Student("Poornima", "Patel"));
		theStudents.add(new Student("Mario", "Rossi"));
		theStudents.add(new Student("Mary", "Smith"));
		
		// check the studentId against list size
		
		if ( (studentId >= theStudents.size()) || (studentId < 0) ) {
			throw new StudentNotFoundException("Student id not found - " + studentId);
		}
		return theStudents.get(studentId);
	}
	
	// Add an exception handler using @ExceptionHandler
	
	@ExceptionHandler
	public ResponseEntity<StudentErrorResponse> handleException (StudentNotFoundException exc) {
		// create a StudentErrorResponse
		
		StudentErrorResponse error = new StudentErrorResponse();
		
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		
		// return Response Entity
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	// Add another exception handler ... to catch any exception (catch all)
	
	@ExceptionHandler
	public ResponseEntity<StudentErrorResponse> handleException (Exception exc) {
		// create a StudentErrorResponse
		
				StudentErrorResponse error = new StudentErrorResponse();
				
				error.setStatus(HttpStatus.BAD_REQUEST.value());
				error.setMessage(exc.getMessage());
				error.setTimeStamp(System.currentTimeMillis());
				
				// return Response Entity
				return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
}
