package com.bjit.nusaiba.backend_project.entity;


import java.util.Collection;
import java.util.Collections;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bjit.nusaiba.backend_project.view.View;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@Data
@Entity
@Table(name="user_table", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@JsonView(View.Student.class)
	private Long id;
	
	@Column(name="email")
	@NonNull
	@Email(regexp = "^(.+)@(.+)$", message = "Invalid email")
//	@JsonIgnore
	@JsonView(View.Student.class)
	private String email;
	
	@Column(name="password")
	@NonNull
	@Size(min = 4, message = "password should atleast be of lenght 4")
//	@JsonIgnore
	@JsonView(View.Student.class)
	private String pass;
	
	@Column(name="name")
	@NonNull
	@Pattern(regexp = "^[a-zA-Z\\s.*]*$",message = "cannot contain numbers")
	@Size(min=1,message = "cannot be empty")
	@JsonView(View.Student.class)
	private String name;
	
	@Column(name = "age")
	@NotNull(message = "Age is required")
	@JsonView(View.Admin.class)
	private Integer age;
	
	@Column(name = "address")
	@NonNull
	@Size(min=1, message = "Address cannot be empty!")
	@JsonView(View.Student.class)
	private String address;
	
	
	@Column(name = "role")
//	@JsonIgnore
	@JsonView(View.Student.class)
	private String role;


	
	@Override
//	@JsonIgnore
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}

	@Override
//	@JsonIgnore
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
//	@JsonIgnore
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
//	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
//	@JsonIgnore
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
//	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return Collections.singleton(new SimpleGrantedAuthority(role));
	}

	@Override
//	@JsonIgnore
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.pass;
	}
	
	
}
