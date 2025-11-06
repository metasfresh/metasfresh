/*
 * #%L
 * de-metas-camel-scriptedadapter
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.camel.externalsystems.scriptedadapter.convertmsg.from_mf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.common.util.Check;
import lombok.NonNull;
import lombok.Value;
import org.apache.camel.RuntimeCamelException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Optains and caches temporary tokes for later REST-API calls.
 */
@Component
public class OAuthTokenManager
{
	private final Map<String, CachedToken> tokenCache = new ConcurrentHashMap<>();
	private final ObjectMapper mapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

	/**
	 * Return a cached access token or fetch one and assume that it's going to be valid for 24hrs.
	 * We can later use the actual validity-duration from the API response when we come across a case where that info is actually provided.
	 */
	public String getAccessToken(
			@NonNull final String tokenUrl,
			@Nullable final String clientId,
			@Nullable final String clientSecret,
			@Nullable final String username,
			@Nullable final String password)
	{
		final String cacheKey = createCacheKey(tokenUrl, clientId, username);

		final CachedToken cachedToken = tokenCache.get(cacheKey);
		if (cachedToken != null && !cachedToken.isExpired())
		{
			return cachedToken.getAccessToken();
		}

		// Fetch new token
		final String accessToken = fetchNewToken(tokenUrl, clientId, clientSecret, username, password);
		tokenCache.put(cacheKey, new CachedToken(accessToken, Instant.now().plus(24L, ChronoUnit.HOURS)));

		return accessToken;
	}

	/**
	 * If a token is known to be invalid, it can be removed from the cache with this method
	 */
	public void invalidateToken(@NonNull final String tokenUrl,
								@Nullable final String clientId,
								@Nullable final String username)
	{
		final String cacheKey = createCacheKey(tokenUrl, clientId, username);
		tokenCache.remove(cacheKey);
	}

	private String fetchNewToken(
			@NonNull final String tokenUrl,
			@Nullable final String clientId,
			@Nullable final String clientSecret,
			@Nullable final String username,
			@Nullable final String password)
	{
		final RestTemplate restTemplate = new RestTemplate();
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		final MultiValueMap<String, String> formData = createFormData(clientId, clientSecret, username, password);

		final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);
		final ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);

		try
		{
			final JsonNode jsonNode = mapper.readTree(response.getBody());
			return jsonNode.get("access_token").asText();
		}
		catch (final JsonProcessingException e)
		{
			throw new RuntimeCamelException("Failed to parse OAuth token response", e);
		}
	}

	private static @NonNull MultiValueMap<String, String> createFormData(
			final @Nullable String clientId,
			final @Nullable String clientSecret,
			final @Nullable String username,
			final @Nullable String password)
	{
		final MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("grant_type", "password");
		if (Check.isNotBlank(clientId))
		{
			formData.add("client_id", clientId);
		}
		if (Check.isNotBlank(clientSecret))
		{
			formData.add("client_secret", clientSecret);
		}
		if (Check.isNotBlank(username))
		{
			formData.add("username", username);
		}
		if (Check.isNotBlank(password))
		{
			formData.add("password", password);
		}
		return formData;
	}

	private static @NonNull String createCacheKey(@NonNull final String tokenUrl,
												  @Nullable final String clientId,
												  @Nullable final String username)
	{
		return tokenUrl + "_" + clientId + "_" + username;
	}

	@Value
	private static class CachedToken
	{
		String accessToken;
		Instant expiresAt;

		public boolean isExpired()
		{
			return Instant.now().isAfter(expiresAt);
		}
	}
}
