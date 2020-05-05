package de.metas.dist.ait.login;

import static com.jayway.restassured.RestAssured.given;

import com.jayway.restassured.response.Cookie;
import com.jayway.restassured.response.ValidatableResponse;
import com.jayway.restassured.specification.RequestSpecification;

import lombok.NonNull;

/*
 * #%L
 * metasfresh-dist-ait
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class LoginClient
{

	private final RequestSpecification spec;

	public LoginClient(@NonNull final RequestSpecification spec)
	{
		this.spec = spec;
	}

	public RequestSpecification getSpec()
	{
		return spec;
	}

	public AvailableLanguages getAvailableLanguages()
	{
		final AvailableLanguages languagess = given() // will do http://localhost:8080 by default
				.spec(spec)
				.when()
				.get("login/availableLanguages")
				.then()
				.statusCode(200)
				.extract().as(AvailableLanguages.class);
		return languagess;
	}

	public boolean isLoggedIn()
	{
		return given() // will do http://localhost:8080 by default
				.spec(spec)
				.when()
				.get("login/isLoggedIn")
				.then()
				.statusCode(200).extract().as(Boolean.class);
	}

	public AuthenticateResponse authenticate(String username, String password)
	{
		final ValidatableResponse invocation = given() // will do http://localhost:8080 by default
				.spec(spec)
				.body(new Credentials(username, password))
				.when()
				.post("login/authenticate")
				.then()
				.statusCode(200);
		
		final String sessionCookie = invocation.extract().cookie("SESSION");
		spec.cookie(new Cookie.Builder("SESSION", sessionCookie).build());
		
		final AuthenticateResponse authenticateResponse = invocation.extract().as(AuthenticateResponse.class);
		return authenticateResponse;
	}

	public void loginComplete(LoginRole loginRole)
	{
		given() // will do http://localhost:8080 by default
				.spec(spec)
				.body(loginRole)
				.when()
				.post("login/loginComplete")
				.then()
				.statusCode(200);

		// TODO Auto-generated method stub
	}
}
