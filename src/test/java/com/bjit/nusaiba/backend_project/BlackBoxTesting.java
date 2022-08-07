package com.bjit.nusaiba.backend_project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.bjit.nusaiba.backend_project.entity.User;
import com.bjit.nusaiba.backend_project.repository.UserRepository;

@SpringBootTest
class BlackBoxTesting {
	@MockBean
	private UserRepository studentRepository;
	
	
	@BeforeAll
	public static void start() {
		System.out.println("Test Started!");
	}
	
	@Test
    public void checkIfRepositoryNull() {

        assertNull(studentRepository);
    }
	
	@Test
    public void checkIfStudentValueNull() {

        assertNull(studentRepository.findById(1l));
    }
	
	@Test
    public void checkValueAfterAdd1() {
		Optional<User> student = Optional.of( new User("nusaiba","$2a$12$AvS3dSnFiBagwDPwN1h8PeivsKkAljoVU3EsiduHyxBTisrPsSsKy","nusaib@gmail.com","dhaka"));
	    when(studentRepository.findById(1L)).thenReturn(student);
        assertTrue(studentRepository.findById(1l).get().getName().equals("nusaiba"));
    }
	
	
	@Test
    public void checkValueAfterAdd2() {
		Optional<User> student = Optional.of( new User("nusaib@gmail.com","$2a$12$AvS3dSnFiBagwDPwN1h8PeivsKkAljoVU3EsiduHyxBTisrPsSsKy","nusaiba","dhaka"));
	    when(studentRepository.findById(1L)).thenReturn(student);
        assertTrue(studentRepository.findById(1l).get().getName().equals("nusaiba"));
    }
	
	@Test
    public void checkValueAfterAdd3() {
		Optional<User> student = Optional.of( new User("nusaib@gmail.com","$2a$12$AvS3dSnFiBagwDPwN1h8PeivsKkAljoVU3EsiduHyxBTisrPsSsKy","dhaka","nusaiba"));
	    when(studentRepository.findById(1L)).thenReturn(student);
        assertTrue(studentRepository.findById(1l).get().getAddress().equals("dhaka"));
    }
	
	@Test
    public void checkValueAfterAdd4() {
		Optional<User> student = Optional.of( new User("nusaib@gmail.com","dhaka","$2a$12$AvS3dSnFiBagwDPwN1h8PeivsKkAljoVU3EsiduHyxBTisrPsSsKy","nusaiba"));
	    when(studentRepository.findById(1L)).thenReturn(student);
        assertTrue(studentRepository.findById(1l).get().getAddress().equals("dhaka"));
    }
	
	@Test
    public void checkValueAfterAdd5() {
		Optional<User> student = Optional.of( new User("nusaib@gmail.com","$2a$12$AvS3dSnFiBagwDPwN1h8PeivsKkAljoVU3EsiduHyxBTisrPsSsKy","nusaiba","dhaka"));
	    when(studentRepository.findById(1L)).thenReturn(student);
        assertTrue(studentRepository.findById(1l).get().getAddress().equals("dhaka"));
    }
	
	@Test
    public void checkStudentNumberFromRepository() {
		ArrayList<User> studentList=new ArrayList<>();
        User student = new User("nusaib@gmail.com","$2a$12$AvS3dSnFiBagwDPwN1h8PeivsKkAljoVU3EsiduHyxBTisrPsSsKy","nusaiba","dhaka");
		studentList.add(student);
        when(studentRepository.findAll()).thenReturn( studentList);
        assertEquals(((ArrayList<User>)studentRepository.findAll()).size(),1);
    }
	
	@Test
    public void checkStudentNumberFromRepository2() {
		ArrayList<User> studentList=new ArrayList<>();
        User student = new User("nusaib@gmail.com","$2a$12$AvS3dSnFiBagwDPwN1h8PeivsKkAljoVU3EsiduHyxBTisrPsSsKy","nusaiba","dhaka");
		studentList.add(student);
        when(studentRepository.findAll()).thenReturn( studentList);
        assertEquals(((ArrayList<User>)studentRepository.findAll()).size(),2);
    }
	
	@Test
    public void checkStudentNumberFromRepository3() {
		ArrayList<User> studentList=new ArrayList<>();
        User student = new User("nusaib@gmail.com","$2a$12$AvS3dSnFiBagwDPwN1h8PeivsKkAljoVU3EsiduHyxBTisrPsSsKy","nusaiba","dhaka");
		studentList.add(student);
        when(studentRepository.findAll()).thenReturn( studentList);
        assertEquals(((ArrayList<User>)studentRepository.findAll()).size(),3);
    }
	
	@AfterAll
	public static void end() {
		System.out.println("Test Has Ended");
	}
}
