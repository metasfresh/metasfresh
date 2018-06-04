package de.metas.marketing.gateway.cleverreach;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.marketing.gateway.cleverreach.restapi.models.Login;
import lombok.NonNull;

/*
 * #%L
 * marketing-cleverreach
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class CleverReachLowLevelClient
{
	public static  CleverReachLowLevelClient createAndLogin(@NonNull final CleverReachConfig cleverReachConfig)
	{
		final String authToken = login(cleverReachConfig);
		return new CleverReachLowLevelClient(authToken);
	}

	private static final String BASE_URL = "https://rest.cleverreach.com/v2";

	private final String authToken;

	private CleverReachLowLevelClient(@NonNull final String authToken)
	{
		this.authToken = authToken;
	}

	private static String login(@NonNull final CleverReachConfig cleverReachConfig)
	{
		final Login login = Login.builder()
				.client_id(cleverReachConfig.getClient_id())
				.login(cleverReachConfig.getLogin())
				.password(cleverReachConfig.getPassword())
				.build();

		final RestTemplate restTemplate = new RestTemplate();
		final String authToken = restTemplate.postForObject(BASE_URL + "/login", login, String.class);

		return authToken.replaceAll("\"", ""); // the string comes complete within '"' which we need to remove
	}

	public <T> T get(
			@NonNull final ParameterizedTypeReference<T> returnType,
			@NonNull final String urlPathAndParams,
			@NonNull final Object... paramValues)
	{
		final RestTemplate restTemplate = createRestTemplate();
		final HttpEntity<?> entity = new HttpEntity<>(createHeaders());

		final ResponseEntity<T> groups = restTemplate.exchange(
				urlPathAndParams,
				HttpMethod.GET,
				entity,
				returnType,
				paramValues);
		return groups.getBody();
	}

	public <I, O> O post(
			@NonNull final I requestBody,
			@NonNull final ParameterizedTypeReference<O> returnType,
			@NonNull final String url)
	{
		final RestTemplate restTemplate = createRestTemplate();
		final HttpEntity<I> entity = new HttpEntity<>(requestBody, createHeaders());

		final ResponseEntity<O> groups = restTemplate.exchange(
				url,
				HttpMethod.POST,
				entity,
				returnType,
				ImmutableMap.of());
		return groups.getBody();
	}

	public <I, O> O put(
			@NonNull final I requestBody,
			@NonNull final ParameterizedTypeReference<O> returnType,
			@NonNull final String url)
	{
		final RestTemplate restTemplate = createRestTemplate();
		final HttpEntity<I> entity = new HttpEntity<>(requestBody, createHeaders());

		final ResponseEntity<O> groups = restTemplate.exchange(
				url,
				HttpMethod.PUT,
				entity,
				returnType,
				ImmutableMap.of());
		return groups.getBody();
	}


	public void delete(String url)
	{
		final RestTemplate restTemplate = createRestTemplate();
		final HttpEntity<?> entity = new HttpEntity<>(createHeaders());

		restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
	}

	private RestTemplate createRestTemplate()
	{
		final RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder().rootUri(BASE_URL);
		final RestTemplate restTemplate = restTemplateBuilder.build();
		return restTemplate;
	}

	private HttpHeaders createHeaders()
	{
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(ImmutableList.of(MediaType.APPLICATION_JSON));
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + authToken);

		return httpHeaders;
	}
}
