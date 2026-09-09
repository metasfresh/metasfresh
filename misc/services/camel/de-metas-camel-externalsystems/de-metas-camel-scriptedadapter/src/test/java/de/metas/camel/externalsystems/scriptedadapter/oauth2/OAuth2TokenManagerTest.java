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

import com.sun.net.httpserver.HttpServer;
import org.apache.camel.RuntimeCamelException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for {@link OAuth2TokenManager} using an in-process {@link HttpServer} as the stub token endpoint.
 */
class OAuth2TokenManagerTest
{
	private HttpServer httpServer;
	private String tokenUrl;

	/** Holds the response body the stub will return for the next request. */
	private final AtomicReference<String> stubResponse = new AtomicReference<>(
			"{\"access_token\":\"tok-1\",\"token_type\":\"Bearer\",\"expires_in\":3600}");

	/** Tracks how many times the /token endpoint was called. */
	private final AtomicInteger hitCount = new AtomicInteger(0);

	/** Stores the last raw request body received by the stub. */
	private final AtomicReference<String> lastRequestBody = new AtomicReference<>("");

	@BeforeEach
	void startStub() throws IOException
	{
		httpServer = HttpServer.create(new InetSocketAddress(0), 0);
		httpServer.createContext("/token", exchange -> {
			hitCount.incrementAndGet();
			final byte[] bodyBytes = exchange.getRequestBody().readAllBytes();
			lastRequestBody.set(new String(bodyBytes, StandardCharsets.UTF_8));

			final byte[] responseBytes = stubResponse.get().getBytes(StandardCharsets.UTF_8);
			exchange.getResponseHeaders().set("Content-Type", "application/json");
			exchange.sendResponseHeaders(200, responseBytes.length);
			try (OutputStream os = exchange.getResponseBody())
			{
				os.write(responseBytes);
			}
		});
		httpServer.start();

		final int port = httpServer.getAddress().getPort();
		tokenUrl = "http://localhost:" + port + "/token";
	}

	@AfterEach
	void stopStub()
	{
		if (httpServer != null)
		{
			httpServer.stop(0);
		}
	}

	// ---- helpers ----

	private Map<String, String> parseFormBody(final String raw)
	{
		return Arrays.stream(raw.split("&"))
				.map(kv -> kv.split("=", 2))
				.collect(Collectors.toMap(
						kv -> URLDecoder.decode(kv[0], StandardCharsets.UTF_8),
						kv -> kv.length > 1 ? URLDecoder.decode(kv[1], StandardCharsets.UTF_8) : ""));
	}

	// ---- tests ----

	@Test
	void happyPath_returnsAccessTokenAndPostsCorrectFormFields()
	{
		final OAuth2TokenManager manager = new OAuth2TokenManager();

		final String token = manager.getAccessToken(tokenUrl, "my-scope", "my-client", "alice", "secret");

		assertThat(token).isEqualTo("tok-1");
		assertThat(hitCount.get()).isEqualTo(1);

		final Map<String, String> form = parseFormBody(lastRequestBody.get());
		assertThat(form).containsEntry("grant_type", "password");
		assertThat(form).containsEntry("client_id", "my-client");
		assertThat(form).containsEntry("username", "alice");
		assertThat(form).containsEntry("password", "secret");
		assertThat(form).containsEntry("scope", "my-scope");
	}

	@Test
	void scopeOmitted_whenScopeIsNullOrBlank()
	{
		final OAuth2TokenManager managerNull = new OAuth2TokenManager();
		managerNull.getAccessToken(tokenUrl, null, "my-client", "alice", "secret");
		final Map<String, String> formNull = parseFormBody(lastRequestBody.get());
		assertThat(formNull).doesNotContainKey("scope");

		// reset
		hitCount.set(0);
		lastRequestBody.set("");

		final OAuth2TokenManager managerBlank = new OAuth2TokenManager();
		managerBlank.getAccessToken(tokenUrl, "   ", "my-client", "alice", "secret");
		final Map<String, String> formBlank = parseFormBody(lastRequestBody.get());
		assertThat(formBlank).doesNotContainKey("scope");
	}

	@Test
	void caching_twoCallsHitStubOnlyOnce()
	{
		final OAuth2TokenManager manager = new OAuth2TokenManager();

		final String first = manager.getAccessToken(tokenUrl, "s", "c", "u", "p");
		final String second = manager.getAccessToken(tokenUrl, "s", "c", "u", "p");

		assertThat(hitCount.get()).isEqualTo(1);
		assertThat(first).isEqualTo(second).isEqualTo("tok-1");
	}

	@Test
	void invalidate_nextCallHitsStubAgain()
	{
		final OAuth2TokenManager manager = new OAuth2TokenManager();

		// first call — caches tok-1
		final String first = manager.getAccessToken(tokenUrl, "s", "c", "u", "p");
		assertThat(first).isEqualTo("tok-1");
		assertThat(hitCount.get()).isEqualTo(1);

		// change stub response
		stubResponse.set("{\"access_token\":\"tok-2\",\"token_type\":\"Bearer\",\"expires_in\":3600}");

		// invalidate, then call again
		manager.invalidateToken(tokenUrl, "c", "u");
		final String second = manager.getAccessToken(tokenUrl, "s", "c", "u", "p");

		assertThat(second).isEqualTo("tok-2");
		assertThat(hitCount.get()).isEqualTo(2);
	}

	@Test
	void missingAccessToken_throwsExceptionNamingTokenUrl()
	{
		stubResponse.set("{\"token_type\":\"Bearer\"}");
		final OAuth2TokenManager manager = new OAuth2TokenManager();

		assertThatThrownBy(() -> manager.getAccessToken(tokenUrl, null, "c", "u", "p"))
				.isInstanceOf(RuntimeCamelException.class)
				.hasMessageContaining(tokenUrl);
	}
}
