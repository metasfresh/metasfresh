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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.logging.LogManager;
import de.metas.tax.api.VATIdentifier;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import de.metas.vatid.VATaxIDCheckResult;
import de.metas.vatid.VATaxIDConfig;
import de.metas.vatid.VATaxIDOnlineChecker;
import de.metas.vatid.VATaxIDStatus;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Nullable;
import java.util.Locale;

/**
 * Checks a VAT-ID against the EU VIES REST API.
 *
 * <p>The requester identity from {@link VATaxIDConfig} is sent whenever it is configured. That is not
 * authentication: without it VIES returns an empty {@code requestIdentifier}, and that identifier is
 * the only archivable proof that a check was made.
 *
 * <p>Follows {@code CreditPassClient} for its {@link RestTemplate} shape only. VIES speaks JSON, so
 * none of creditpass's XML stack ({@code jackson-dataformat-xml}, {@code woodstox-core},
 * {@code stax2-api}, {@code XmlMapper}, {@code APPLICATION_XML}) is pulled in here.
 */
@RequiredArgsConstructor
public class VIESClient implements VATaxIDOnlineChecker
{
	private static final Logger logger = LogManager.getLogger(VIESClient.class);

	private static final String PATH_CHECK_VAT_NUMBER = "/check-vat-number";
	private static final String PATH_CHECK_STATUS = "/check-status";

	/** The one availability value that means "can answer"; anything else is treated as unavailable. */
	private static final String AVAILABILITY_AVAILABLE = "Available";

	/** Shared because {@link ObjectMapper} is thread-safe for reads and needs no per-client state. */
	private static final ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * The member states VIES can answer for: EU-27 plus XI (Northern Ireland, post-Brexit).
	 *
	 * <p>Deliberately NOT derived from {@code EUVatIdValidator}'s prefix set, which is a different
	 * question: that one also covers CH, GB and NO because their formats are checkable offline, but
	 * VIES has no data for them. Reusing it would send requests VIES cannot answer and turn a
	 * knowably-out-of-scope VAT-ID into a service error. Note {@code EL}, not {@code GR}, is the VIES
	 * code for Greece.
	 */
	private static final ImmutableSet<String> VIES_MEMBER_STATE_CODES = ImmutableSet.of(
			"AT", "BE", "BG", "CY", "CZ", "DE", "DK", "EE", "EL", "ES", "FI", "FR", "HR", "HU",
			"IE", "IT", "LT", "LU", "LV", "MT", "NL", "PL", "PT", "RO", "SE", "SI", "SK", "XI");

	@NonNull private final RestTemplate restTemplate;

	@Override
	public VATaxIDCheckResult check(@NonNull final VATIdentifier vatId, @NonNull final VATaxIDConfig config)
	{
		// Locale.ROOT, not the JVM default: under a Turkish default locale an 'i' would upper-case to
		// 'İ' and silently alter the identifier sent to VIES.
		final String value = vatId.getAsString().toUpperCase(Locale.ROOT);

		// Out of VIES's scope is a definite answer, not a failure - short-circuit without a request.
		final String countryCode = value.length() >= 2 ? value.substring(0, 2) : "";
		if (!VIES_MEMBER_STATE_CODES.contains(countryCode))
		{
			return VATaxIDCheckResult.builder()
					.status(VATaxIDStatus.NotSupported)
					.build();
		}

		final String baseUrl = getBaseUrl(config);
		final String vatNumber = value.substring(2);

		try
		{
			return toResult(post(baseUrl, countryCode, vatNumber, config));
		}
		catch (final HttpStatusCodeException e)
		{
			// Keep the error body: the check log exists so a dispute can be reconstructed, and a 4xx/5xx
			// body is often the only explanation of why no verdict was obtained.
			logger.warn("VIES check failed for {} with {} - reporting {}",
					value, e.getStatusCode(), VATaxIDStatus.ServiceUnavailable, e);
			return VATaxIDCheckResult.builder()
					.status(VATaxIDStatus.ServiceUnavailable)
					.rawResponse(StringUtils.trimBlankToNull(e.getResponseBodyAsString()))
					.build();
		}
		catch (final RestClientException e)
		{
			// Returned, not rethrown: a failed check is a recordable outcome that has to reach the log.
			logger.warn("VIES check failed for {} - reporting {}", value, VATaxIDStatus.ServiceUnavailable, e);
			return VATaxIDCheckResult.builder()
					.status(VATaxIDStatus.ServiceUnavailable)
					.build();
		}
	}

	/**
	 * Reports the country codes {@code GET /check-status} currently lists as not available.
	 *
	 * <p>Response shape, per a live probe of the service on 2026-08-10:
	 * {@code {"vow":{"available":true},"countries":[{"countryCode":"DE","availability":"Available"},…]}}.
	 * Both parts are honoured: {@code vow.available == false} is a service-wide outage and yields EVERY
	 * member state, while per-country {@code availability} other than {@code Available} yields just that
	 * country. Anything other than {@code Available} counts as unavailable rather than enumerating the
	 * service's other availability words - a value we have not seen before must not be read as "fine".
	 *
	 * <p>A failure here returns an EMPTY set, not "everything is down" - see the SPI contract. An
	 * unreachable availability endpoint must not suppress every check; the individual check will report
	 * {@code ServiceUnavailable} on its own if the member state really cannot answer.
	 */
	@Override
	public ImmutableSet<String> getUnavailableCountryCodes(@NonNull final VATaxIDConfig config)
	{
		final String baseUrl = getBaseUrl(config);

		final String rawResponse;
		try
		{
			rawResponse = restTemplate.getForObject(baseUrl + PATH_CHECK_STATUS, String.class);
		}
		catch (final RestClientException e)
		{
			logger.warn("VIES {} failed - reporting nothing as unavailable", PATH_CHECK_STATUS, e);
			return ImmutableSet.of();
		}

		if (Check.isBlank(rawResponse))
		{
			return ImmutableSet.of();
		}

		try
		{
			final JsonNode root = objectMapper.readTree(rawResponse);

			// Service-wide outage: reported by its own flag, independently of the per-country list, which
			// can still claim every state is Available. Without this the scheduled run would spend its
			// whole budget discovering the outage one VAT-ID at a time - the very thing this endpoint is
			// consulted to avoid.
			final JsonNode vow = root.get("vow");
			final JsonNode vowAvailable = vow != null ? vow.get("available") : null;
			if (vowAvailable != null && vowAvailable.isBoolean() && !vowAvailable.asBoolean())
			{
				logger.warn("VIES reports itself unavailable (vow.available=false) - treating all member states as unavailable");
				return VIES_MEMBER_STATE_CODES;
			}

			final JsonNode countries = root.get("countries");
			if (countries == null || !countries.isArray())
			{
				return ImmutableSet.of();
			}

			final ImmutableSet.Builder<String> unavailable = ImmutableSet.builder();
			for (final JsonNode country : countries)
			{
				final String countryCode = asTextOrNull(country.get("countryCode"));
				final String availability = asTextOrNull(country.get("availability"));
				if (countryCode != null && !AVAILABILITY_AVAILABLE.equalsIgnoreCase(availability))
				{
					unavailable.add(countryCode.toUpperCase(Locale.ROOT));
				}
			}
			return unavailable.build();
		}
		catch (final Exception e)
		{
			logger.warn("Unusable VIES {} response - reporting nothing as unavailable", PATH_CHECK_STATUS, e);
			return ImmutableSet.of();
		}
	}

	/**
	 * A missing base URL is an unfixed configuration error, so it is raised rather than recorded as
	 * {@code ServiceUnavailable}: recorded, the on-service-unavailable fallback would mask it
	 * indefinitely and every partner would silently keep its last status.
	 */
	private static String getBaseUrl(@NonNull final VATaxIDConfig config)
	{
		final String baseUrl = StringUtils.trimBlankToNull(config.getRestApiBaseURL());
		if (baseUrl == null)
		{
			throw new AdempiereException("No RestApiBaseURL configured")
					.appendParametersToMessage()
					.setParameter("VATaxID_Config_ID", config.getId());
		}
		return baseUrl;
	}

	private String post(
			@NonNull final String baseUrl,
			@NonNull final String countryCode,
			@NonNull final String vatNumber,
			@NonNull final VATaxIDConfig config)
	{
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(ImmutableList.of(MediaType.APPLICATION_JSON));

		final ImmutableMap.Builder<String, String> body = ImmutableMap.builder();
		body.put("countryCode", countryCode);
		body.put("vatNumber", vatNumber);
		// Sent whenever configured - see the class javadoc on why the identifier matters.
		if (config.getRequesterMemberStateCode() != null)
		{
			body.put("requesterMemberStateCode", config.getRequesterMemberStateCode());
		}
		if (config.getRequesterNumber() != null)
		{
			body.put("requesterNumber", config.getRequesterNumber());
		}

		final ResponseEntity<String> response = restTemplate.exchange(
				baseUrl + PATH_CHECK_VAT_NUMBER,
				HttpMethod.POST,
				new HttpEntity<>(body.build(), headers),
				String.class);

		return response.getBody();
	}

	/**
	 * Maps a {@code POST /check-vat-number} response body onto a result, keeping the body as evidence.
	 *
	 * <p>Per the 2026-08-10 live probe the body carries {@code valid}, {@code name}, {@code address},
	 * the {@code trader*Match} fields ({@code NOT_PROCESSED} when no trader data was supplied) and
	 * {@code requestIdentifier} (empty unless the requester VAT-ID is configured). There is no
	 * success/error envelope: a missing or non-boolean {@code valid} is therefore treated as "no
	 * verdict" ({@code ServiceUnavailable}), never as {@code Invalid}. Per-member-state outages are
	 * reported by {@link #getUnavailableCountryCodes(VATaxIDConfig)}, not by this payload.
	 */
	private VATaxIDCheckResult toResult(@Nullable final String rawResponse)
	{
		if (Check.isBlank(rawResponse))
		{
			return VATaxIDCheckResult.builder().status(VATaxIDStatus.ServiceUnavailable).build();
		}

		final JsonNode json;
		try
		{
			json = objectMapper.readTree(rawResponse);
		}
		catch (final Exception e)
		{
			// An unparseable body means no answer, not an invalid VAT-ID.
			logger.warn("Unusable VIES response - reporting {}", VATaxIDStatus.ServiceUnavailable, e);
			return VATaxIDCheckResult.builder()
					.status(VATaxIDStatus.ServiceUnavailable)
					.rawResponse(rawResponse)
					.build();
		}

		final String requestIdentifier = asTextOrNull(json.get("requestIdentifier"));

		final JsonNode valid = json.get("valid");
		if (valid == null || !valid.isBoolean())
		{
			// No verdict at all - not an invalid VAT-ID.
			return VATaxIDCheckResult.builder()
					.status(VATaxIDStatus.ServiceUnavailable)
					.requestIdentifier(requestIdentifier)
					.rawResponse(rawResponse)
					.build();
		}

		return VATaxIDCheckResult.builder()
				.status(valid.asBoolean() ? VATaxIDStatus.Valid : VATaxIDStatus.Invalid)
				.requestIdentifier(requestIdentifier)
				.rawResponse(rawResponse)
				.build();
	}

	@Nullable
	private static String asTextOrNull(@Nullable final JsonNode node)
	{
		return node != null && node.isTextual() ? node.asText() : null;
	}
}
