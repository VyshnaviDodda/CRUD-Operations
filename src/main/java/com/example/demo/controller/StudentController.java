package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Student;
import com.example.demo.repo.StudentRepo;

@RestController
public class StudentController 
{
	//Consoling the postman data details
//	@PostMapping("/api/addStudent")
//	
//		public void saveStudent(@RequestBody Student student) 
//	    {
//			System.out.println(student);
//		}
//	
	@Autowired
	StudentRepo studentRepo;
	
	
	
	//Posting the Postman data into Database
	    @PostMapping("/api/addStudent")
		public ResponseEntity<Student> saveStudent(@RequestBody Student student) 
	    {
		    return new ResponseEntity<>(studentRepo.save(student), HttpStatus.CREATED);
		}
	    
	    
	    
	//Getting all the data present in Database
	    @GetMapping("/api/getAllStudents")
	    public ResponseEntity<List<Student>> getAllStudents()
	    {
		    return new ResponseEntity<>(studentRepo.findAll(), HttpStatus.OK);
		}
	    
	    
	    
	//For Getting Single student based on Id(Path Params)
	    @GetMapping("/api/getStudent/{id}")
	    public ResponseEntity<Student> getSingleStudent(@PathVariable long id)
	    {
		   Optional<Student> student = studentRepo.findById((int) id);// to check whether the paricular id is present or not
		   
		   if(student.isPresent()) {
			   return new ResponseEntity<>(student.get(), HttpStatus.OK); 
		   }
		   else {
			   return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		   }
		}
	    
	    
	    
	//For Updating the existing data
	    @PutMapping("/api/editStudent/{id}")
	    public ResponseEntity<Student> editStudent(@PathVariable long id, @RequestBody  Student userStudent)//user updated details
	    {
		   Optional<Student> student = studentRepo.findById((int) id);// to check whether the paricular id is present or not
		   
		   if(student.isPresent())
		   {
			   student.get().setMail(userStudent.getMail());
			   student.get().setName(userStudent.getName());
			   return new ResponseEntity<>(studentRepo.save(student.get()), HttpStatus.OK); 
		   }
		   else 
		   {
			   return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		   }
		}
	    
}
