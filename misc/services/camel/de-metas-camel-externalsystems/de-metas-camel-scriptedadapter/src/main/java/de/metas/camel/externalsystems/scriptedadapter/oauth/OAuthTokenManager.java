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

package de.metas.camel.externalsystems.scriptedadapter.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.common.util.Check;
import de.metas.common.util.time.SystemTime;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.apache.camel.RuntimeCamelException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Optains and caches temporary tokes for later REST-API calls.
 */
@Component
@RequiredArgsConstructor
public class OAuthTokenManager
{
	private static final Duration EXPIRING_DURATION = Duration.ofHours(24);

	@NonNull private final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();
	@NonNull private final RestTemplate restTemplate = new RestTemplate();

	private final Cache<OAuthIdentity, OAuthAccessToken> accessTokensCache = CacheBuilder.newBuilder()
			.expireAfterAccess(EXPIRING_DURATION)
			.maximumSize(1000)
			.build();

	private Instant now() {return SystemTime.asInstant();}

	/**
	 * Return a cached access token or fetch one and assume that it's going to be valid for {@link #EXPIRING_DURATION}.
	 * We can later use the actual validity-duration from the API response when we come across a case where that info is actually provided.
	 */
	public OAuthAccessToken getAccessToken(@NonNull OAuthAccessTokenRequest request)
	{
		final OAuthAccessToken cachedToken = accessTokensCache.getIfPresent(request.getIdentity());
		if (cachedToken != null && !cachedToken.isExpired(now()))
		{
			return cachedToken;
		}

		// Fetch new token
		final OAuthAccessToken newToken = fetchNewAccessToken(request);
		accessTokensCache.put(request.getIdentity(), newToken);

		return newToken;
	}

	/**
	 * If a token is known to be invalid, it can be removed from the cache with this method
	 */
	public void invalidateToken(@NonNull OAuthIdentity identity)
	{
		accessTokensCache.invalidate(identity);
		accessTokensCache.cleanUp();
	}

	private OAuthAccessToken fetchNewAccessToken(@NonNull final OAuthAccessTokenRequest request)
	{
		try
		{
			final HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setAccept(ImmutableList.of(MediaType.APPLICATION_JSON));
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);

			final Map<String, String> formData = createFormData(request);

			final ResponseEntity<String> response = restTemplate.postForEntity(
					request.getIdentity().getTokenUrl(),
					new HttpEntity<>(jsonObjectMapper.writeValueAsString(formData), httpHeaders),
					String.class);

			final JsonNode jsonNode = jsonObjectMapper.readTree(response.getBody());
			final String accessToken = jsonNode.get("bearer").asText();
			return OAuthAccessToken.of(accessToken, now().plus(EXPIRING_DURATION));
		}
		catch (final JsonProcessingException e)
		{
			throw new RuntimeCamelException("Failed to parse OAuth token response", e);
		}
	}

	@NonNull
	private static Map<String, String> createFormData(@NonNull final OAuthAccessTokenRequest request)
	{
		final Map<String, String> formData = new HashMap<>();
		if (Check.isNotBlank(request.getIdentity().getClientId()))
		{
			formData.put("client_id", request.getIdentity().getClientId());
		}
		if (Check.isNotBlank(request.getClientSecret()))
		{
			formData.put("client_secret", request.getClientSecret());
		}
		if (Check.isNotBlank(request.getIdentity().getUsername()))
		{
			formData.put("email", request.getIdentity().getUsername());
		}
		if (Check.isNotBlank(request.getPassword()))
		{
			formData.put("password", request.getPassword());
		}
		return formData;
	}

	@Value
	@Builder
	private static class CacheKey
	{
		@NonNull String tokenUrl;
		@Nullable String clientId;
		@Nullable String username;
	}
}
