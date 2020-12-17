package com.SpringSecurity.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.SpringSecurity.Pojo.AuthenticationRequest;
import com.SpringSecurity.Pojo.AuthenticationResponse;
import com.SpringSecurity.Pojo.UserDetailsC;
import com.SpringSecurity.Service.MyUserDetailsService;
import com.SpringSecurity.Util.JwtUtil;

@RestController
public class Controller {
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	MyUserDetailsService userDetailService;
	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping("/admin")
	public String getAdmin() {
		return "<h1>Welcome Admin</h1>";
	}

	@GetMapping("/user")
	public String getUser() {
		return "<h1>Welcome User</h1>";
	}

	@GetMapping("/")
	public String getResponse() {
		return "<h1>Welcome everyone</h1>";
	}

	@PostMapping("/authentication")
	public ResponseEntity<?> createAuthentication(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));

		} catch (Exception e) {
			throw new Exception("Incorrect Username and Password");
		}
		final UserDetails userDetails = userDetailService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

}
