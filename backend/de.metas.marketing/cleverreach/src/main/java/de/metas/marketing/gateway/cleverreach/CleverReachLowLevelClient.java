package de.metas.marketing.gateway.cleverreach;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.marketing.gateway.cleverreach.restapi.models.ErrorResponse;
import de.metas.marketing.gateway.cleverreach.restapi.models.Login;
import de.metas.util.JSONObjectMapper;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.MDC;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Objects;

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
	public static CleverReachLowLevelClient createAndLogin(@NonNull final CleverReachConfig cleverReachConfig)
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
		final String url = BASE_URL + "/login";
		final Login login = Login.builder()
				.client_id(cleverReachConfig.getClient_id())
				.login(cleverReachConfig.getLogin())
				.password(cleverReachConfig.getPassword())
				.build();

		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(ImmutableList.of(MediaType.APPLICATION_JSON));
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		try (final MDCCloseable ignored = MDC.putCloseable("httpMethod", "POST");
				final MDCCloseable ignored1 = MDC.putCloseable("url", url);
				final MDCCloseable ignored2 = MDC.putCloseable("login", login.withoutPassword().toString()))
		{
			final RestTemplate restTemplate = new RestTemplate();
			final String authToken = Objects.requireNonNull(restTemplate.postForObject(
					url,
					new HttpEntity<>(login, httpHeaders),
					String.class));

			return authToken.replaceAll("\"", ""); // the string comes complete within '"' which we need to remove
		}
		catch (final HttpClientErrorException e)
		{
			throw convertException(e)
					.setParameter("httpMethod", "POST")
					.setParameter("url", url)
					.setParameter("requestBody", login.withoutPassword());
		}
	}

	public <T> T get(
			@NonNull final ParameterizedTypeReference<T> returnType,
			@NonNull final String urlPathAndParams,
			@NonNull final Object... paramValues)
	{
		try (final MDCCloseable ignored = MDC.putCloseable("httpMethod", "GET");
				final MDCCloseable ignored1 = MDC.putCloseable("urlPathAndParams", urlPathAndParams);
				final MDCCloseable ignored2 = MDC.putCloseable("paramValues", Arrays.toString(paramValues)))
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
		catch (final RuntimeException e)
		{
			throw convertException(e)
					.setParameter("httpMethod", "GET")
					.setParameter("urlPathAndParams", urlPathAndParams)
					.setParameter("paramValues", Arrays.toString(paramValues));
		}
	}

	public <I, O> O post(
			@NonNull final I requestBody,
			@NonNull final ParameterizedTypeReference<O> returnType,
			@NonNull final String url)
	{
		try (final MDCCloseable ignored = MDC.putCloseable("httpMethod", "POST");
				final MDCCloseable ignored1 = MDC.putCloseable("url", url);
				final MDCCloseable ignored2 = MDC.putCloseable("requestBody", requestBody.toString()))
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
		catch (final RuntimeException e)
		{
			throw convertException(e)
					.setParameter("httpMethod", "POST")
					.setParameter("url", url)
					.setParameter("requestBody", requestBody);
		}
	}

	public <I, O> O put(
			@NonNull final I requestBody,
			@NonNull final ParameterizedTypeReference<O> returnType,
			@NonNull final String url)
	{
		try (final MDCCloseable ignored = MDC.putCloseable("httpMethod", "PUT");
				final MDCCloseable ignored1 = MDC.putCloseable("url", url);
				final MDCCloseable ignored2 = MDC.putCloseable("requestBody", requestBody.toString()))
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
		catch (final RuntimeException e)
		{
			throw convertException(e)
					.setParameter("httpMethod", "PUT")
					.setParameter("url", url)
					.setParameter("requestBody", requestBody);
		}
	}

	public void delete(@NonNull final String url)
	{
		try (final MDCCloseable ignored = MDC.putCloseable("httpMethod", "DELETE");
				final MDCCloseable ignored1 = MDC.putCloseable("url", url))
		{
			final RestTemplate restTemplate = createRestTemplate();
			final HttpEntity<?> entity = new HttpEntity<>(createHeaders());

			restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
		}
		catch (final RuntimeException e)
		{
			throw convertException(e)
					.setParameter("httpMethod", "DELETE")
					.setParameter("url", url);
		}
	}

	private RestTemplate createRestTemplate()
	{
		return new RestTemplateBuilder().rootUri(BASE_URL).build();
	}

	private HttpHeaders createHeaders()
	{
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(ImmutableList.of(MediaType.APPLICATION_JSON));
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + authToken);

		return httpHeaders;
	}

	private static AdempiereException convertException(@NonNull final RuntimeException rte)
	{
		if (rte instanceof HttpClientErrorException)
		{
			final HttpClientErrorException hcee = (HttpClientErrorException)rte;

			final JSONObjectMapper<ErrorResponse> mapper = JSONObjectMapper.forClass(ErrorResponse.class);
			final ErrorResponse errorResponse = mapper.readValue(hcee.getResponseBodyAsString());

			return new AdempiereException(errorResponse.getError().getMessage(), hcee)
					.markAsUserValidationError()
					.appendParametersToMessage()
					.setParameter("code", errorResponse.getError().getCode())
					.setParameter("message", errorResponse.getError().getMessage());
		}

		return AdempiereException.wrapIfNeeded(rte)
				.appendParametersToMessage();
	}
}
