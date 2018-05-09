package de.metas.marketing.gateway.cleverreach;

import java.util.List;

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

import de.metas.marketing.gateway.cleverreach.restapi.models.Group;
import de.metas.marketing.gateway.cleverreach.restapi.models.Login;
import lombok.NonNull;

/*
 * #%L
 * de.metas.marketing
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

public class CleverReachClient
{
	private static final String BASE_URL = "https://rest.cleverreach.com/v2";

	// @formatter:off
	private static final ParameterizedTypeReference<List<Group>> GROUPS_TYPE = new ParameterizedTypeReference<List<Group>>() {}; // // @formatter:on

	public static CleverReachClient createNewClientAndLogin(@NonNull final CleverReachConfig cleverReachConfig)
	{
		return new CleverReachClient(login(cleverReachConfig));
	}

	private final String token;


	private CleverReachClient(@NonNull final String token)
	{
		this.token = token;
	}

	private static String login(@NonNull final CleverReachConfig cleverReachConfig)
	{
		final Login login = Login.builder()
				.client_id(cleverReachConfig.getClient_id())
				.login(cleverReachConfig.getLogin())
				.password(cleverReachConfig.getPassword())
				.build();

		final RestTemplate restTemplate = new RestTemplate();
		final String token = restTemplate.postForObject(BASE_URL + "/login", login, String.class);

		return token.replaceAll("\"", ""); // the string comes complete with "" which we need to remove
	}

	public List<Group> retrieveGroups()
	{
		final RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
				.rootUri(BASE_URL);

		final RestTemplate restTemplate = restTemplateBuilder.build();

		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(ImmutableList.of(MediaType.APPLICATION_JSON));
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);

		final HttpEntity<?> entity = new HttpEntity<>(httpHeaders);

		final ResponseEntity<List<Group>> groups = restTemplate.exchange(
				BASE_URL + "/groups.json",
				HttpMethod.GET,
				entity,
				GROUPS_TYPE,
				ImmutableMap.of());
		return groups.getBody();
	}

}
