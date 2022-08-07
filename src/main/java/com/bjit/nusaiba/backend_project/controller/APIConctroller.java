package com.bjit.nusaiba.backend_project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bjit.nusaiba.backend_project.entity.User;
import com.bjit.nusaiba.backend_project.exception.UserNotFoundExeption;
import com.bjit.nusaiba.backend_project.repository.UserRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/students")
public class APIConctroller {
	
	@Autowired
	private UserRepository repository;
	

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Object> get(@RequestParam("email") @Nullable String email,
			@RequestParam("id") @Nullable Long id,
			@RequestParam("name") @Nullable String name) 
					 throws UserNotFoundExeption{
		ResponseEntity<Object> res;
		Object body=null;
		if(id!=null) {
			body=repository.findById(id).orElse(null);
		}else if (email!=null) {
			body=repository.findByEmail(email);
		}else if (name!=null) {
			List<User> users=(List<User>) repository.findByName(name);
			if(!users.isEmpty()) {
				body=users;
			}
		}else {
			List<User> users=(List<User>) repository.findAll();
			if(!users.isEmpty()) {
				body=users;
			}
		}
		res=new ResponseEntity<Object>(body,HttpStatus.OK);
		if(body==null) {
			throw new UserNotFoundExeption("User Not Found");
		}
		
		return res;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> post(@RequestBody User user){
		user.setPass((new BCryptPasswordEncoder()).encode(user.getPass()));
		repository.save(user);
		return new ResponseEntity<>(user,HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Object> put(@RequestParam("id") Long id, @RequestBody User user) throws UserNotFoundExeption{
		User temp= repository.findById(id).orElse(null);
		if(temp==null) {
			throw new UserNotFoundExeption("User Not Found");
		}
		user.setId(id);
		user.setEmail(temp.getEmail());
		user.setPass(temp.getPass());
		user.setRole(temp.getRole());
		repository.save(user);
		return new ResponseEntity<>(user,HttpStatus.ACCEPTED);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@RequestParam("id") Long id) throws UserNotFoundExeption{
		User temp= repository.findById(id).orElse(null);
		if(temp==null) {
			throw new UserNotFoundExeption("User Not Found");
		}
		repository.delete(temp);
		
		return new ResponseEntity<>(temp,HttpStatus.ACCEPTED);
	}
	@RequestMapping("/login")
	public String login(){
		return"/login";
	}

}
