package com.bjit.nusaiba.backend_project.repository;


import org.springframework.data.repository.CrudRepository;


import com.bjit.nusaiba.backend_project.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByEmail(String email);
	Iterable<User> findByName(String name);
	
}
