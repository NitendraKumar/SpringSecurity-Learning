package org.nitendra.security.models;

public class AuthenticationResponse {
	private String jwt;

	public AuthenticationResponse() {
	}

	public AuthenticationResponse(String jwt) {
		super();
		this.jwt = jwt;
	}

	public String getJwt() {
		return jwt;
	}

}
