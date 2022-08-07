package com.bjit.nusaiba.backend_project.advice;

import java.sql.Timestamp;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

import com.bjit.nusaiba.backend_project.exception.UserNotFoundExeption;
import com.bjit.nusaiba.backend_project.view.View;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@ControllerAdvice(annotations = RestController.class)
class APIControllerAdvice extends AbstractMappingJacksonResponseBodyAdvice{
	@Autowired
	private ObjectMapper objectMapper;
	
	@ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<Object> allExeceptions(Exception e) {
		ObjectNode bodyOfResponse=objectMapper.createObjectNode();
    	bodyOfResponse.put("timestamp", (new Timestamp(System.currentTimeMillis())).toString());
    	bodyOfResponse.put("status",HttpStatus.BAD_REQUEST.value());
    	bodyOfResponse.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
    	bodyOfResponse.put("message", e.getMessage());
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bodyOfResponse);
    }
	
	@ExceptionHandler(value = UserNotFoundExeption.class)
    @ResponseBody
    public ResponseEntity<Object> userNotFoundExeceptions(UserNotFoundExeption e) {
		ObjectNode bodyOfResponse=objectMapper.createObjectNode();
    	bodyOfResponse.put("timestamp", (new Timestamp(System.currentTimeMillis())).toString());
    	bodyOfResponse.put("status",HttpStatus.NOT_FOUND.value());
    	bodyOfResponse.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
    	bodyOfResponse.put("message", e.getMessage());
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(bodyOfResponse);
    }

	@Override
	protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType,
			MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {
		Class<?> viewClass = View.Student.class;
		
		if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().getAuthorities() != null) {
            Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

            if (authorities.stream().anyMatch(o -> o.getAuthority().equals("student"))) {
                viewClass = View.Student.class;
            }
            if (authorities.stream().anyMatch(o -> o.getAuthority().equals("admin"))) {
                viewClass = View.Admin.class;
            }
        }
        bodyContainer.setSerializationView(viewClass);	
	}

	
	
	
	
}
