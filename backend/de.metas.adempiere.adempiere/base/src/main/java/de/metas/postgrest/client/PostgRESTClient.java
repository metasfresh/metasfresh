/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.postgrest.client;

import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.postgrest.config.PostgRESTConfig;
import de.metas.postgrest.config.PostgRESTConfigRepository;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;

@Component
public class PostgRESTClient
{
	private static final Logger log = LogManager.getLogger(PostgRESTClient.class);

	private final PostgRESTConfigRepository configRepository;

	public PostgRESTClient(final PostgRESTConfigRepository configRepository)
	{
		this.configRepository = configRepository;
	}

	public String performGet(@NonNull final GetRequest getRequest)
	{
		final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getRequest.getBaseURL());

		log.debug("*** performGet(): for request {}", getRequest);

		if (!Check.isEmpty(getRequest.getPathVariables()))
		{
			builder.pathSegment(getRequest.getPathVariables().toArray(new String[0]));
		}

		if (!Check.isEmpty(getRequest.getQueryParameters()))
		{
			builder.queryParams(getRequest.getQueryParameters());
		}

		final HttpEntity<String> request = new HttpEntity<>( buildHttpHeaders() );

		final URI uri = builder.build().encode().toUri();

		final ResponseEntity<String> responseEntity = restTemplate().exchange(uri, HttpMethod.GET, request, String.class);

		final boolean responseWithErrors = !responseEntity.getStatusCode().is2xxSuccessful();

		if (responseWithErrors)
		{
			throw new AdempiereException("Something went wrong when retrieving from postgREST!, response body:" + responseEntity.getBody() );
		}

		log.debug("*** PostgRESTClient.performGet(), response: {}", responseEntity.getBody());

		return responseEntity.getBody();
	}

	private HttpHeaders buildHttpHeaders()
	{
		final HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
		return headers;
	}

	private RestTemplate restTemplate()
	{
		final OrgId orgId = Env.getOrgId();

		final PostgRESTConfig config = configRepository.getConfigFor(orgId);

		return new RestTemplateBuilder()
				.setConnectTimeout(config.getConnectionTimeout())
				.setReadTimeout(config.getReadTimeout())
				.build();
	}
}
