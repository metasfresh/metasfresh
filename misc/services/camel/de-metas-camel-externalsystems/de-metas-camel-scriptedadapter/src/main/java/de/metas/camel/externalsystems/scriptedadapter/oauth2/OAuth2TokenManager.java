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

package de.metas.camel.externalsystems.scriptedadapter.oauth2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import lombok.NonNull;
import org.apache.camel.RuntimeCamelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Nullable;
import java.time.Duration;

/**
 * Obtains and caches OAuth2 access tokens via the resource-owner-password grant (grant_type=password).
 * <p>
 * Mirrors {@code OAuthTokenManager} in structure: Guava cache, RestTemplate, 24 h expireAfterAccess TTL.
 * Differences: uses {@code application/x-www-form-urlencoded} form POST and reads {@code access_token}
 * from the response (the {@code expires_in} field in the response is not used; cache TTL is fixed).
 */
@Component
public class OAuth2TokenManager
{
	private static final Logger log = LoggerFactory.getLogger(OAuth2TokenManager.class);

	/** Cache TTL. Mirrors the legacy manager's 24 h expireAfterAccess strategy. */
	private static final Duration DEFAULT_EXPIRING_DURATION = Duration.ofHours(24);

	private final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();
	private final RestTemplate restTemplate = new RestTemplate();

	private final Cache<OAuth2TokenIdentity, String> accessTokensCache = CacheBuilder.newBuilder()
			.expireAfterAccess(DEFAULT_EXPIRING_DURATION)
			.maximumSize(1000)
			.build();

	/**
	 * Returns a cached access token or fetches a new one via resource-owner-password grant.
	 *
	 * @param tokenUrl  the token endpoint URL (required)
	 * @param scope     the OAuth scope (may be {@code null} or blank — omitted from the request when so)
	 * @param clientId  the client identifier (required)
	 * @param username  the resource-owner username (required)
	 * @param password  the resource-owner password (required, never logged)
	 * @return a valid access token string
	 */
	public String getAccessToken(
			@NonNull final String tokenUrl,
			@Nullable final String scope,
			@NonNull final String clientId,
			@NonNull final String username,
			@NonNull final String password)
	{
		final OAuth2TokenIdentity identity = OAuth2TokenIdentity.of(tokenUrl, clientId, username);

		final String cached = accessTokensCache.getIfPresent(identity);
		if (cached != null)
		{
			return cached;
		}

		final String token = fetchAccessToken(tokenUrl, scope, clientId, username, password);
		accessTokensCache.put(identity, token);
		return token;
	}

	/**
	 * Removes the cached token for the given identity key, so the next {@link #getAccessToken} call
	 * fetches a fresh token (e.g. after receiving a 401 from the resource server).
	 */
	public void invalidateToken(
			@NonNull final String tokenUrl,
			@NonNull final String clientId,
			@NonNull final String username)
	{
		final OAuth2TokenIdentity identity = OAuth2TokenIdentity.of(tokenUrl, clientId, username);
		accessTokensCache.invalidate(identity);
		accessTokensCache.cleanUp();
	}

	private String fetchAccessToken(
			@NonNull final String tokenUrl,
			@Nullable final String scope,
			@NonNull final String clientId,
			@NonNull final String username,
			@NonNull final String password)
	{
		log.debug("Fetching OAuth2 access token from tokenUrl={} clientId={} username={}", tokenUrl, clientId, username);

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		final MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
		form.add("grant_type", "password");
		form.add("client_id", clientId);
		form.add("username", username);
		form.add("password", password);
		if (scope != null && !scope.isBlank())
		{
			form.add("scope", scope);
		}

		final ResponseEntity<String> response = restTemplate.postForEntity(
				tokenUrl,
				new HttpEntity<>(form, headers),
				String.class);

		try
		{
			final JsonNode root = jsonObjectMapper.readTree(response.getBody());
			final JsonNode accessTokenNode = root.get("access_token");
			if (accessTokenNode == null || accessTokenNode.isNull())
			{
				throw new RuntimeCamelException("OAuth2 token response from " + tokenUrl + " did not contain 'access_token'");
			}
			return accessTokenNode.asText();
		}
		catch (final RuntimeCamelException e)
		{
			throw e;
		}
		catch (final Exception e)
		{
			throw new RuntimeCamelException("Failed to parse OAuth2 token response from " + tokenUrl, e);
		}
	}

	/**
	 * Cache key: tokenUrl + clientId + username (password is excluded — it is a secret and not part of identity).
	 */
	private record OAuth2TokenIdentity(
			@NonNull String tokenUrl,
			@NonNull String clientId,
			@NonNull String username)
	{
		static OAuth2TokenIdentity of(
				@NonNull final String tokenUrl,
				@NonNull final String clientId,
				@NonNull final String username)
		{
			return new OAuth2TokenIdentity(tokenUrl, clientId, username);
		}
	}
}
