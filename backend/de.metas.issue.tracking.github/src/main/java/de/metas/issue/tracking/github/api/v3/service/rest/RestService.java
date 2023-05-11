/*
 * #%L
 * de.metas.issue.tracking.github
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.issue.tracking.github.api.v3.service.rest;

import ch.qos.logback.classic.Level;
import de.metas.issue.tracking.github.api.v3.service.RateLimitService;
import de.metas.issue.tracking.github.api.v3.service.rest.info.Request;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.slf4j.Logger;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Nullable;
import java.net.URI;
import java.time.Duration;

import static de.metas.issue.tracking.github.api.v3.GitHubApiConstants.GITHUB_API_VERSION;
import static de.metas.issue.tracking.github.api.v3.GitHubApiConstants.GithubSysConfig.CONNECTION_TIMEOUT;
import static de.metas.issue.tracking.github.api.v3.GitHubApiConstants.GithubSysConfig.READ_TIMEOUT;

@Service
public class RestService
{
	private static final Logger log = LogManager.getLogger(RestService.class);

	private final RateLimitService rateLimitService;
	private final ResponseErrorHandler responseErrorHandler;
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	public RestService(final RateLimitService rateLimitService,
			           final ResponseErrorHandler responseErrorHandler)
	{
		this.rateLimitService = rateLimitService;
		this.responseErrorHandler = responseErrorHandler;
	}

	public <T> ResponseEntity<T> performGet(@NonNull final Request getRequest, final Class<T> clazz)
	{
		final URI resourceURI;

		final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(getRequest.getBaseURL());

		if (!Check.isEmpty(getRequest.getPathVariables()))
		{
			uriBuilder.pathSegment(getRequest.getPathVariables().toArray(new String[0]));
		}

		if (!Check.isEmpty(getRequest.getQueryParameters()))
		{
			uriBuilder.queryParams(getRequest.getQueryParameters());
		}
		final HttpEntity<String> request = new HttpEntity<>(buildHttpHeaders(getRequest.getOAuthToken()));

		resourceURI = uriBuilder.build().encode().toUri();

		return performWithRetry(resourceURI, HttpMethod.GET, request, clazz);
	}

	public <T> ResponseEntity<T> performPost(@NonNull final Request postRequest, @Nullable final Class<T> clazz)
	{
		final URI resourceURI;

		final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(postRequest.getBaseURL());

		if (!Check.isEmpty(postRequest.getPathVariables()))
		{
			uriBuilder.pathSegment(postRequest.getPathVariables().toArray(new String[0]));
		}

		if (!Check.isEmpty(postRequest.getQueryParameters()))
		{
			uriBuilder.queryParams(postRequest.getQueryParameters());
		}

		final String requestBody = postRequest.getRequestBody();

		final HttpHeaders httpHeaders = buildHttpHeaders(postRequest.getOAuthToken());

		final HttpEntity<String> request = Check.isBlank(requestBody)
				? new HttpEntity<>(httpHeaders)
				: new HttpEntity<>(requestBody, httpHeaders);

		resourceURI = uriBuilder.build().encode().toUri();

		return performWithRetry(resourceURI, HttpMethod.POST, request, clazz);
	}

	private HttpHeaders buildHttpHeaders(final String oAuthToken)
	{
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add(HttpHeaders.ACCEPT, GITHUB_API_VERSION);
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + oAuthToken);

		return headers;
	}

	private <T> ResponseEntity<T> performWithRetry(final URI resourceURI, final HttpMethod httpMethod,
			                                       final HttpEntity<String> request, final Class<T> responseType)
	{
		ResponseEntity<T> response = null;
		boolean retry = true;

		while (retry)
		{
			try
			{
				log.debug("Performing [{}] on [{}] for request [{}]", httpMethod.name(), resourceURI, request);
				retry = false;
				response = restTemplate().exchange(resourceURI, httpMethod, request, responseType);
			}
			catch (final RateLimitExceededException rateLimitEx)
			{
				Loggables.withLogger(log, Level.ERROR)
						.addLog("*** ERROR: RateLimit reached on Github side! ErrorMsg: {}, rateLimitInfo: {}", rateLimitEx.getErrorMessage(), rateLimitEx);
				rateLimitService.waitForLimitReset(rateLimitEx.getRateLimit());
				retry = true;
			}
			catch (final Throwable t)
			{
				log.error("ERROR while trying to fetch from URI: {}, Error message: {}", resourceURI, t.getMessage(), t);
				throw t;
			}
		}
		return response;
	}

	private RestTemplate restTemplate()
	{
		return new RestTemplateBuilder()
				.errorHandler(responseErrorHandler)
				.setConnectTimeout(Duration.ofMillis(sysConfigBL.getIntValue(CONNECTION_TIMEOUT.getName(), CONNECTION_TIMEOUT.getDefaultValue())))
				.setReadTimeout(Duration.ofMillis(sysConfigBL.getIntValue(READ_TIMEOUT.getName(), READ_TIMEOUT.getDefaultValue())))
				.build();
	}
}
