/*
 * #%L
 * marketing-activecampaign
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

package de.metas.marketing.gateway.activecampaign.restapi;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;
import de.metas.marketing.gateway.activecampaign.restapi.exception.RateLimitExceededException;
import de.metas.marketing.gateway.activecampaign.restapi.exception.RateLimitService;
import de.metas.marketing.gateway.activecampaign.restapi.request.ApiRequest;
import de.metas.util.Check;
import de.metas.util.Loggables;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Nullable;
import java.net.URI;

import static de.metas.marketing.gateway.activecampaign.ActiveCampaignConstants.ACTIVE_CAMPAIGN_API_TOKEN_HEADER;

@Service
public class RestService
{
	private static final Logger log = LogManager.getLogger(RestService.class);

	private final ResponseErrorHandler responseErrorHandler;
	private final RateLimitService rateLimitService;

	public RestService(
			@NonNull final ResponseErrorHandler responseErrorHandler,
			@NonNull final RateLimitService rateLimitService)
	{
		this.responseErrorHandler = responseErrorHandler;
		this.rateLimitService = rateLimitService;
	}

	public <T> ResponseEntity<T> performGet(@NonNull final ApiRequest getRequest, final Class<T> clazz)
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

		final HttpEntity<String> request = new HttpEntity<>(getHttpHeaders(getRequest.getApiKey()));

		resourceURI = uriBuilder.build().encode().toUri();

		return performWithRetry(resourceURI, HttpMethod.GET, request, clazz);
	}

	public <T> ResponseEntity<T> performPost(@NonNull final ApiRequest postRequest, @Nullable final Class<T> clazz)
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

		final HttpHeaders httpHeaders = getHttpHeaders(postRequest.getApiKey());

		final HttpEntity<String> request = Check.isBlank(requestBody)
				? new HttpEntity<>(httpHeaders)
				: new HttpEntity<>(requestBody, httpHeaders);

		resourceURI = uriBuilder.build().encode().toUri();

		return performWithRetry(resourceURI, HttpMethod.POST, request, clazz);
	}

	public <T> ResponseEntity<T> performPut(@NonNull final ApiRequest postRequest, @Nullable final Class<T> clazz)
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

		final HttpHeaders httpHeaders = getHttpHeaders(postRequest.getApiKey());

		final HttpEntity<String> request = Check.isBlank(requestBody)
				? new HttpEntity<>(httpHeaders)
				: new HttpEntity<>(requestBody, httpHeaders);

		resourceURI = uriBuilder.build().encode().toUri();

		return performWithRetry(resourceURI, HttpMethod.PUT, request, clazz);
	}

	private <T> ResponseEntity<T> performWithRetry(
			final URI resourceURI,
			final HttpMethod httpMethod,
			final HttpEntity<String> request,
			final Class<T> responseType)
	{
		ResponseEntity<T> response = null;
		boolean retry = true;

		while (retry)
		{
			try
			{
				log.debug("Performing [{}] on [{}] for request [{}]", httpMethod.name(), resourceURI, request);
				retry = false;
				response = createRestTemplate().exchange(resourceURI, httpMethod, request, responseType);
			}
			catch (final RateLimitExceededException rateLimitEx)
			{
				Loggables.withLogger(log, Level.INFO)
						.addLog("*** ERROR: RateLimit reached on ActiveCampaign side! ErrorMsg: {}, rateLimitInfo: {}", rateLimitEx.getErrorMessage(), rateLimitEx);
				rateLimitService.waitForLimitReset(rateLimitEx.getRetryAfterDuration());
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

	@NonNull
	private static HttpHeaders getHttpHeaders(@NonNull final String apiToken)
	{
		final HttpHeaders headers = new HttpHeaders();
		headers.add(ACTIVE_CAMPAIGN_API_TOKEN_HEADER, apiToken);

		return headers;
	}

	@NonNull
	private RestTemplate createRestTemplate()
	{
		return new RestTemplateBuilder()
				.errorHandler(responseErrorHandler)
				.build();
	}
}
