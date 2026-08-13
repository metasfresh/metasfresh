/*
 * #%L
 * de.metas.vatid
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.vatid.vies;

import com.google.common.collect.ImmutableSet;
import de.metas.tax.api.VATIdentifier;
import de.metas.vatid.VATaxIDCheckResult;
import de.metas.vatid.VATaxIDConfig;
import de.metas.vatid.VATaxIDConfigId;
import de.metas.vatid.VATaxIDOnServiceUnavailableAction;
import de.metas.vatid.VATaxIDStatus;
import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.SocketTimeoutException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withException;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Every case is served by a stubbed {@link RestTemplate} — no test here reaches the live VIES
 * service. That is deliberate and not merely convenient: a test bound to the real service would fail
 * whenever a member state is down, which is one of the very outcomes under test.
 */
class VIESClientTest
{
	private static final String BASE_URL = "https://ec.europa.eu/taxation_customs/vies/rest-api";

	private RestTemplate restTemplate;
	private MockRestServiceServer server;
	private VIESClient client;

	@BeforeEach
	void setUp()
	{
		restTemplate = new RestTemplate();
		server = MockRestServiceServer.bindTo(restTemplate).build();
		client = new VIESClient(restTemplate);
	}

	private static VATaxIDConfig config()
	{
		return VATaxIDConfig.builder()
				.id(VATaxIDConfigId.ofRepoId(1_000_000))
				.formatCheckEnabled(true)
				.viesCheckEnabled(true)
				.restApiBaseURL(BASE_URL)
				.requesterMemberStateCode("DE")
				.requesterNumber("999999999")
				.recheckAfterDays(30)
				.onServiceUnavailable(VATaxIDOnServiceUnavailableAction.ServiceUnavailable)
				.build();
	}

	@Test
	@DisplayName("valid answer -> Valid, keeping the requestIdentifier and the raw response as evidence")
	void validAnswer()
	{
		final String body = "{\"countryCode\":\"DE\",\"vatNumber\":\"123456789\",\"valid\":true,"
				+ "\"requestIdentifier\":\"WAPIAAAAWkVdG8Rn\",\"name\":\"Some Company GmbH\","
				+ "\"address\":\"Somestreet 1, 12345 Somewhere\"}";

		server.expect(requestTo(BASE_URL + "/check-vat-number"))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withSuccess(body, MediaType.APPLICATION_JSON));

		final VATaxIDCheckResult result = client.check(VATIdentifier.of("DE123456789"), config());

		assertThat(result.getStatus()).isEqualTo(VATaxIDStatus.Valid);
		assertThat(result.getRequestIdentifier()).isEqualTo("WAPIAAAAWkVdG8Rn");
		assertThat(result.getRawResponse()).contains("Some Company GmbH");
		server.verify();
	}

	@Test
	@DisplayName("the requester identity is sent when configured - without it VIES returns no requestIdentifier")
	void requesterIdentityIsSent()
	{
		server.expect(requestTo(BASE_URL + "/check-vat-number"))
				.andExpect(method(HttpMethod.POST))
				.andExpect(jsonPath("$.countryCode").value("DE"))
				.andExpect(jsonPath("$.vatNumber").value("123456789"))
				.andExpect(jsonPath("$.requesterMemberStateCode").value("DE"))
				.andExpect(jsonPath("$.requesterNumber").value("999999999"))
				.andRespond(withSuccess("{\"valid\":true,\"requestIdentifier\":\"X1\"}", MediaType.APPLICATION_JSON));

		client.check(VATIdentifier.of("DE123456789"), config());

		server.verify();
	}

	@Test
	@DisplayName("invalid answer -> Invalid, with the raw response kept as the evidence for removing a certificate")
	void invalidAnswer()
	{
		final String body = "{\"countryCode\":\"DE\",\"vatNumber\":\"000000000\",\"valid\":false,"
				+ "\"requestIdentifier\":\"WAPIBBBB1234\"}";

		server.expect(requestTo(BASE_URL + "/check-vat-number"))
				.andRespond(withSuccess(body, MediaType.APPLICATION_JSON));

		final VATaxIDCheckResult result = client.check(VATIdentifier.of("DE000000000"), config());

		assertThat(result.getStatus()).isEqualTo(VATaxIDStatus.Invalid);
		assertThat(result.getRawResponse()).contains("\"valid\":false");
		server.verify();
	}

	@Test
	@DisplayName("member state reported unavailable is read from /check-status, not from the check response")
	void memberStateUnavailable()
	{
		// Shape per the live probe recorded in the issue's investigation notes: a "countries" array of
		// {countryCode, availability}. Greece is down, Germany is not.
		final String body = "{\"vow\":{\"available\":true},\"countries\":["
				+ "{\"countryCode\":\"DE\",\"availability\":\"Available\"},"
				+ "{\"countryCode\":\"EL\",\"availability\":\"Unavailable\"},"
				+ "{\"countryCode\":\"IT\",\"availability\":\"Monitoring disabled\"}]}";

		server.expect(requestTo(BASE_URL + "/check-status"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess(body, MediaType.APPLICATION_JSON));

		final ImmutableSet<String> unavailable = client.getUnavailableCountryCodes(config());

		// Anything that is not exactly "Available" counts as unavailable — an availability word we have
		// not seen before must not be optimistically read as "fine".
		assertThat(unavailable).containsExactlyInAnyOrder("EL", "IT");
		server.verify();
	}

	@Test
	@DisplayName("/check-status failure reports NOTHING unavailable, so it cannot suppress every check")
	void checkStatusFailureReportsNothingUnavailable()
	{
		server.expect(requestTo(BASE_URL + "/check-status"))
				.andRespond(withServerError());

		// Empty, not "everything is down": an unreachable availability endpoint must not silently stop
		// all checking. A genuinely-down member state still surfaces on its individual check.
		assertThat(client.getUnavailableCountryCodes(config())).isEmpty();
		server.verify();
	}

	@Test
	@DisplayName("a check response with no verdict -> ServiceUnavailable, never Invalid")
	void noVerdictInResponse()
	{
		// The real POST /check-vat-number body has no success/error envelope, so "no usable verdict" is
		// the only signal a malformed answer gives. It must not be read as "the VAT-ID is invalid",
		// because only Invalid may cost a partner its tax certificate.
		server.expect(requestTo(BASE_URL + "/check-vat-number"))
				.andRespond(withSuccess("{\"countryCode\":\"DE\",\"vatNumber\":\"123456789\"}", MediaType.APPLICATION_JSON));

		final VATaxIDCheckResult result = client.check(VATIdentifier.of("DE123456789"), config());

		assertThat(result.getStatus()).isEqualTo(VATaxIDStatus.ServiceUnavailable);
		server.verify();
	}

	@Test
	@DisplayName("vow.available=false -> EVERY member state unavailable, even when the country list says Available")
	void serviceWideOutage()
	{
		// The two parts of the payload can disagree: the service reports itself down while the per-country
		// list still claims everything is fine. Reading only the list would let a scheduled run spend its
		// entire budget rediscovering a total outage one VAT-ID at a time.
		final String body = "{\"vow\":{\"available\":false},\"countries\":["
				+ "{\"countryCode\":\"DE\",\"availability\":\"Available\"},"
				+ "{\"countryCode\":\"FR\",\"availability\":\"Available\"}]}";

		server.expect(requestTo(BASE_URL + "/check-status"))
				.andRespond(withSuccess(body, MediaType.APPLICATION_JSON));

		final ImmutableSet<String> unavailable = client.getUnavailableCountryCodes(config());

		assertThat(unavailable).hasSize(28).contains("DE", "FR", "EL", "XI");
		server.verify();
	}

	@Test
	@DisplayName("the requester fields are OMITTED from the wire body when the config does not set them")
	void requesterIdentityOmittedWhenUnconfigured()
	{
		final VATaxIDConfig withoutRequester = VATaxIDConfig.builder()
				.id(VATaxIDConfigId.ofRepoId(1_000_001))
				.formatCheckEnabled(true)
				.viesCheckEnabled(true)
				.restApiBaseURL(BASE_URL)
				.recheckAfterDays(30)
				.onServiceUnavailable(VATaxIDOnServiceUnavailableAction.ServiceUnavailable)
				.build();

		// The negative mirror of requesterIdentityIsSent: absent config must send no key at all, rather
		// than an empty string, which VIES would treat as a supplied-but-blank requester.
		server.expect(requestTo(BASE_URL + "/check-vat-number"))
				.andExpect(jsonPath("$.countryCode").value("DE"))
				.andExpect(jsonPath("$.requesterMemberStateCode").doesNotExist())
				.andExpect(jsonPath("$.requesterNumber").doesNotExist())
				.andRespond(withSuccess("{\"valid\":true}", MediaType.APPLICATION_JSON));

		client.check(VATIdentifier.of("DE123456789"), withoutRequester);

		server.verify();
	}

	@Test
	@DisplayName("no base URL configured -> throws, in BOTH operations, rather than reporting a false outage")
	void missingBaseUrlThrows()
	{
		final VATaxIDConfig noBaseUrl = VATaxIDConfig.builder()
				.id(VATaxIDConfigId.ofRepoId(1_000_002))
				.formatCheckEnabled(true)
				.viesCheckEnabled(true)
				.restApiBaseURL(null)
				.recheckAfterDays(30)
				.onServiceUnavailable(VATaxIDOnServiceUnavailableAction.ServiceUnavailable)
				.build();

		// Deliberately NOT ServiceUnavailable: recorded as an outage, the on-service-unavailable fallback
		// would mask an unfixed misconfiguration forever and every partner would keep its stale status.
		assertThatThrownBy(() -> client.check(VATIdentifier.of("DE123456789"), noBaseUrl))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("RestApiBaseURL");

		assertThatThrownBy(() -> client.getUnavailableCountryCodes(noBaseUrl))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("RestApiBaseURL");

		// What proves no HTTP was attempted is the exception TYPE above, not this verify(): with no
		// expectations registered, MockRestServiceServer throws on any unexpected request, so an attempted
		// call would surface as an AssertionError and fail the isInstanceOf(AdempiereException) check.
		// verify() is kept as a cheap belt-and-braces, but it is a no-op when nothing was expected.
		server.verify();
	}

	@Test
	@DisplayName("HTTP 5xx keeps the error body as evidence")
	void serverErrorKeepsBody()
	{
		server.expect(requestTo(BASE_URL + "/check-vat-number"))
				.andRespond(withServerError().body("{\"message\":\"MS_MAX_CONCURRENT_REQ\"}")
						.contentType(MediaType.APPLICATION_JSON));

		final VATaxIDCheckResult result = client.check(VATIdentifier.of("DE123456789"), config());

		assertThat(result.getStatus()).isEqualTo(VATaxIDStatus.ServiceUnavailable);
		// The check log exists so a dispute can be reconstructed; the error body is often the only
		// explanation of why no verdict was obtained.
		assertThat(result.getRawResponse()).contains("MS_MAX_CONCURRENT_REQ");
		server.verify();
	}

	@Test
	@DisplayName("a country outside VIES (CH/GB/NO) -> NotSupported, without any request")
	void countryOutsideViesScope()
	{
		// The offline validator covers CH, GB and NO; VIES has no data for them. Sending the request
		// anyway would turn a knowably-out-of-scope VAT-ID into a service error.
		final VATaxIDCheckResult result = client.check(VATIdentifier.of("CHE116281838"), config());

		assertThat(result.getStatus()).isEqualTo(VATaxIDStatus.NotSupported);
		server.verify(); // no request expected, and none must have been made
	}

	@Test
	@DisplayName("transport failure -> ServiceUnavailable, returned rather than thrown")
	void transportFailure()
	{
		server.expect(requestTo(BASE_URL + "/check-vat-number"))
				.andRespond(withException(new SocketTimeoutException("connect timed out")));

		final VATaxIDCheckResult result = client.check(VATIdentifier.of("DE123456789"), config());

		// Returned, not thrown: a failed check is a recordable outcome that has to reach the check log.
		assertThat(result.getStatus()).isEqualTo(VATaxIDStatus.ServiceUnavailable);
		server.verify();
	}

	@Test
	@DisplayName("HTTP 5xx -> ServiceUnavailable")
	void serverError()
	{
		server.expect(requestTo(BASE_URL + "/check-vat-number"))
				.andRespond(withServerError());

		final VATaxIDCheckResult result = client.check(VATIdentifier.of("DE123456789"), config());

		assertThat(result.getStatus()).isEqualTo(VATaxIDStatus.ServiceUnavailable);
		server.verify();
	}
}
