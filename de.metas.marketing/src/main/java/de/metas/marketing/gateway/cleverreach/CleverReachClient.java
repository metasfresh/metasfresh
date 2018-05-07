package de.metas.marketing.gateway.cleverreach;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

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
	private static final String baseUrl = "https://rest.cleverreach.com/v2";

	public static CleverReachClient createNewClient()
	{
		return new CleverReachClient(login());
	}

	private final String token;

	private CleverReachClient(@NonNull final String token)
	{
		this.token = token;
	}

	private static String login()
	{
		final Login login = Login.builder()
				.client_id("")
				.login("")
				.password("")
				.build();

		final RestTemplate restTemplate = new RestTemplate();
		final String token = restTemplate.postForObject(baseUrl + "/login", login, String.class);
		return token;
	}

	public List<Group> retrieveGroups()
	{
		final RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.AUTHORIZATION, token);

		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		ResponseEntity<List> groups = restTemplate.exchange(baseUrl + "/groups", HttpMethod.GET, entity, List.class);

		return (List<Group>)groups;
	}

}
