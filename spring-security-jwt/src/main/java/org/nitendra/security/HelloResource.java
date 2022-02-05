package org.nitendra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.nitendra.security.models.AuthenticationResponse;
import org.nitendra.security.util.JwtUtil;
import org.nitendra.security.models.AuthenticationRequest;

@RestController
public class HelloResource {
	
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@Autowired
	private MyUserDetailService userDetailsService;
	
	@GetMapping("/hello")
	public String hello() {
		return "Hello";
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthencticationToken(@RequestBody AuthenticationRequest authenticationRequest)
		throws Exception{
		
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), 
							authenticationRequest.getPassword())
			);
		} catch (AuthenticationException e) {
			throw new Exception("Incorrect username or password", e);
		}
		
		final UserDetails userDetail = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		System.out.println(userDetail);
		final String jwt = jwtTokenUtil.generateToken(userDetail);
		System.out.println(jwt);
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

}
