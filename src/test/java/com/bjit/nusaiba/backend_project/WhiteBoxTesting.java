package com.bjit.nusaiba.backend_project;




import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
class WhiteBoxTesting {
	@MockBean
	private UserRepository studentRepository;
	User test =new User();
	
	@BeforeAll
	public static void start() {
		System.out.println("Test Started!");
	}
	
	@Test
	public void checkIfNull() {
		assertNull(test.getId());
		
	}
	
	@Test
	public void checkSetterGetter() {
		test.setName("nusaiba");
		assertEquals("nusaiba", test.getName());
		
	}
	
	@Test
    public void checkStudenNametWithMocklto() {

        Optional<User> optStudent = Optional.of( new User("nusaib@gmail.com","$2a$12$AvS3dSnFiBagwDPwN1h8PeivsKkAljoVU3EsiduHyxBTisrPsSsKy","nusaiba","dhaka"));
        when(studentRepository.findById(1L)).thenReturn(optStudent);
        assertTrue(studentRepository.findById(1L).get().getName().contains("nusaiba"));
    }
	
	
	@Test
    public void checkStudentNameWithMockltoIfFalse() {

        Optional<User> student = Optional.of( new User("nusaib@gmail.com","$2a$12$AvS3dSnFiBagwDPwN1h8PeivsKkAljoVU3EsiduHyxBTisrPsSsKy","nusaiba","dhaka"));
        when(studentRepository.findById(1L)).thenReturn(student);
        assertFalse(studentRepository.findById(1L).get().getName().contains("faiaz"));
    }
	
	@Test
    public void checkStudentAddressWithMocklto() {

        Optional<User> student = Optional.of( new User("nusaib@gmail.com","$2a$12$AvS3dSnFiBagwDPwN1h8PeivsKkAljoVU3EsiduHyxBTisrPsSsKy","nusaiba","dhaka"));
        when(studentRepository.findById(1L)).thenReturn(student);
        assertTrue(studentRepository.findById(1L).get().getAddress().contains("dhaka"));
    }
	
	@Test
    public void checkStudentEmailWithMocklto() {

        Optional<User> student = Optional.of( new User("nusaib@gmail.com","$2a$12$AvS3dSnFiBagwDPwN1h8PeivsKkAljoVU3EsiduHyxBTisrPsSsKy","nusaiba","dhaka"));
        when(studentRepository.findById(1L)).thenReturn(student);
        assertTrue(studentRepository.findById(1L).get().getEmail().contains("nusaib@gmail.com"));
    }
	
	@Test
    public void checkStudentPassWithMocklto() {

        Optional<User> student = Optional.of( new User("nusaib@gmail.com","$2a$12$AvS3dSnFiBagwDPwN1h8PeivsKkAljoVU3EsiduHyxBTisrPsSsKy","nusaiba","dhaka"));
        when(studentRepository.findById(1L)).thenReturn(student);
        assertTrue(studentRepository.findById(1L).get().getPass().contains("$2a$12$AvS3dSnFiBagwDPwN1h8PeivsKkAljoVU3EsiduHyxBTisrPsSsKy"));
    }
	
	@Test
    public void checkStudentNumberFromRepository() {
		ArrayList<User> studentList=new ArrayList<>();
        User student = new User("nusaib@gmail.com","$2a$12$AvS3dSnFiBagwDPwN1h8PeivsKkAljoVU3EsiduHyxBTisrPsSsKy","nusaiba","dhaka");
		studentList.add(student);
        when(studentRepository.findAll()).thenReturn( studentList);
        assertEquals(((ArrayList<User>)studentRepository.findAll()).size(),1);
    }

	
	@AfterAll
	public static void end() {
		System.out.println("Test Has Ended");
	}
	


}
