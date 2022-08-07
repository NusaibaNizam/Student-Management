package com.bjit.nusaiba.backend_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bjit.nusaiba.backend_project.confuguration.JwtTokenUtill;
import com.bjit.nusaiba.backend_project.entity.JwtRequest;
import com.bjit.nusaiba.backend_project.entity.JwtResponse;
import com.bjit.nusaiba.backend_project.entity.User;
import com.bjit.nusaiba.backend_project.service.UserDetailService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtill jwtTokenUtil;

	@Autowired
	private UserDetailService userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		final User userDetails = (User) userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		authenticate(userDetails, authenticationRequest.getPassword());



		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token,jwtTokenUtil.generateRefreshToken(userDetails),userDetails.getRole(),userDetails.getName(),userDetails.getId()));
	}

	private void authenticate(UserDetails userDetails, String pass) throws Exception {
		try {
			UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(userDetails.getUsername(), pass ,userDetails.getAuthorities());
			authenticationManager.authenticate(token);
			
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}