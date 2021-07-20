/*
 * #%L
 * de.metas.issue.tracking.everhour
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.issue.tracking.everhour.api.rest;


import java.time.Duration;
import ch.qos.logback.classic.Level;
import de.metas.issue.tracking.everhour.api.model.GetRequest;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Loggables;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
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

import java.net.URI;

import static de.metas.issue.tracking.everhour.api.EverhourConstants.CustomHeaders.API_KEY;
import static de.metas.issue.tracking.everhour.api.EverhourConstants.CustomHeaders.VERSION;
import static de.metas.issue.tracking.everhour.api.EverhourConstants.EVERHOUR_API_VERSION;
import static de.metas.issue.tracking.everhour.api.EverhourConstants.EverhourSysConfigs.CONNECTION_TIMEOUT;
import static de.metas.issue.tracking.everhour.api.EverhourConstants.EverhourSysConfigs.MAX_TIME_TO_WAIT_FOR_LIMIT_RESET;
import static de.metas.issue.tracking.everhour.api.EverhourConstants.EverhourSysConfigs.READ_TIMEOUT;
import static java.time.temporal.ChronoUnit.MILLIS;

@Service
public class RestService
{
	private static final Logger log = LogManager.getLogger(RestService.class);

	private final ResponseErrorHandler responseErrorHandler;
	private final ISysConfigBL sysConfigBL;

	public RestService(final ResponseErrorHandler responseErrorHandler, final ISysConfigBL sysConfigBL)
	{
		this.responseErrorHandler = responseErrorHandler;
		this.sysConfigBL = sysConfigBL;
	}

	public <T> ResponseEntity<T> performGet(@NonNull final GetRequest getRequest, final Class<T> clazz)
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
		final HttpEntity<String> request = new HttpEntity<>(buildHttpHeaders(getRequest.getApiKey()));

		resourceURI = uriBuilder.build().encode().toUri();

		return performWithRetry(resourceURI, HttpMethod.GET, request, clazz);
	}

	private HttpHeaders buildHttpHeaders(final String apiKey)
	{
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add(VERSION.getValue(), EVERHOUR_API_VERSION);
		headers.add(API_KEY.getValue(), apiKey);

		return headers;
	}

	private <T> ResponseEntity<T> performWithRetry(final URI resourceURI, final HttpMethod httpMethod,
			                                       final HttpEntity<String> request, final Class<T> responseType)
	{
		ResponseEntity<T> response = null;
		boolean retry = true;

		while (retry)
		{
			log.debug("Performing [{}] on [{}] for request [{}]", httpMethod.name(), resourceURI, request);
			retry = false;

			try
			{
				response = restTemplate().exchange(resourceURI, httpMethod, request, responseType);
			}
			catch (final LimitExceededException limitEx)
			{
				Loggables.withLogger(log, Level.ERROR)
						.addLog("*** ERROR: Limit reached! time to wait for limit reset: {} .", limitEx.getRetryAfter());

				waitForLimitReset(limitEx.getRetryAfter());

				retry = true;
			}
			catch (final Throwable t)
			{
				Loggables.withLogger(log, Level.ERROR)
						.addLog("ERROR while trying to fetch from URI: {}, Error message: {}", resourceURI, t.getMessage(), t);
				throw t;
			}
		}
		return response;
	}

	private void waitForLimitReset(@NonNull final Duration resetAfter)
	{
		final int maxTimeToWaitMs = sysConfigBL.getIntValue(MAX_TIME_TO_WAIT_FOR_LIMIT_RESET.getName(),
				MAX_TIME_TO_WAIT_FOR_LIMIT_RESET.getDefaultValue() );

		if (resetAfter.get(MILLIS) > maxTimeToWaitMs)
		{
			throw new AdempiereException("Limit Reset is too far in the future! aborting!")
					.appendParametersToMessage()
					.setParameter("ResetAfter", resetAfter)
					.setParameter("MaxMsToWaitForLimitReset", maxTimeToWaitMs);
		}
		try
		{
			Loggables.withLogger(log, Level.DEBUG)
					.addLog("*** Waiting for limit reset!Time to wait: {}.", resetAfter);

			Thread.sleep(resetAfter.get(MILLIS));
		}
		catch (final InterruptedException e)
		{
			throw new AdempiereException(e.getMessage(), e);
		}
	}

	private RestTemplate restTemplate()
	{
		return new RestTemplateBuilder()
				.errorHandler(responseErrorHandler)
				.setConnectTimeout(Duration.ofMillis(sysConfigBL.getIntValue(CONNECTION_TIMEOUT.getName(), CONNECTION_TIMEOUT.getDefaultValue())))
				.setReadTimeout(Duration.ofMillis(sysConfigBL.getIntValue(READ_TIMEOUT.getName(),READ_TIMEOUT.getDefaultValue())))
				.build();
	}
}
