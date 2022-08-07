package com.bjit.nusaiba.backend_project.entity;

import java.io.Serializable;


import com.bjit.nusaiba.backend_project.view.View;
import com.fasterxml.jackson.annotation.JsonView;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	@JsonView(View.Student.class)
	private final String accessToken;
	
	@JsonView(View.Student.class)
	private final String refreshToken;
	
	@JsonView(View.Student.class)
	private String role;
	
	@JsonView(View.Student.class)
	private String username;
	
	@JsonView(View.Student.class)
	private Long id;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public JwtResponse(String access_token, String refresh_token, String role, String username,Long id) {
		super();
		this.accessToken = access_token;
		this.refreshToken = refresh_token;
		this.role = role;
		this.username = username;
		this.id=id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	


}