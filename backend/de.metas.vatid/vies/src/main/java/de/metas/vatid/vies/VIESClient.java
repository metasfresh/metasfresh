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
import de.metas.vatid.VATaxIDCheckRequestRejectedException;
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

	/**
	 * VIES error codes that mean OUR CONFIGURATION is wrong — not that the service is unwell, and not that
	 * the record being checked is at fault. Membership here is what makes a whole run abort (see
	 * {@link #throwIfRequestSideError(String)}), so the bar is deliberately narrow: the fault must be
	 * IDENTICAL for every target in the selection, so that carrying on could only repeat it.
	 *
	 * <p>Both codes here meet that bar, and both were verified live 2026-08-15:
	 * {@code INVALID_REQUESTER_INFO} comes back on HTTP 200 when the requester number carries the country
	 * prefix (send {@code IE}/{@code 6388047V}, never {@code IE}/{@code IE6388047V}), and
	 * {@code VOW-ERR-11} on HTTP 400 when only one of the two requester fields is set. Both name a field of
	 * {@link VATaxIDConfig} — the requester identity — which is per organisation, not per VAT-ID.
	 *
	 * <p><b>{@code INVALID_INPUT} is deliberately NOT here</b>, although it is equally a rejection of our
	 * request: it is attributable to the RECORD, not to the configuration. {@link #check} sends
	 * {@code value.substring(2)}, so a {@code VATaxID} that is a bare country code ({@code "DE"}) sends an
	 * empty vatNumber, and a malformed-but-non-empty one ({@code "DEXYZ"}) is sent verbatim. The offline
	 * format gate catches both first, but only where {@code IsFormatCheckEnabled} is on, and that is a
	 * per-organisation setting installations may have off. Aborting on it would let ONE bad partner record
	 * stop the entire nightly run for everybody — worse than the repetition the abort exists to prevent,
	 * and pointing the operator at a configuration that is not what is wrong. It therefore degrades to an
	 * ordinary per-target failure: one warn line, the run continues, the envelope reaches the check log.
	 */
	private static final ImmutableSet<String> REQUEST_SIDE_ERRORS = ImmutableSet.of(
			"INVALID_REQUESTER_INFO",
			"VOW-ERR-11");

	/** Stand-in when the envelope says failure but carries no readable code. */
	private static final String UNKNOWN_VIES_ERROR = "UNKNOWN";

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
			final String errorBody = StringUtils.trimBlankToNull(e.getResponseBodyAsString());

			// A non-2xx carries the SAME error envelope a 200 can - VIES answers VOW-ERR-11 with HTTP 400 -
			// so it must be classified here too. Without this, a request-side fault that happens to arrive
			// with an error status would silently degrade to ServiceUnavailable, which is precisely the
			// behaviour this classification exists to prevent.
			throwIfRequestSideError(errorBody);

			// Keep the error body: the check log exists so a dispute can be reconstructed, and a 4xx/5xx
			// body is often the only explanation of why no verdict was obtained.
			logger.warn("VIES check failed for {} with {} - reporting {}",
					value, e.getStatusCode(), VATaxIDStatus.ServiceUnavailable, e);
			return VATaxIDCheckResult.builder()
					.status(VATaxIDStatus.ServiceUnavailable)
					.rawResponse(errorBody)
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
	 * <p>A successful body carries {@code valid}, {@code name}, {@code address}, the
	 * {@code trader*Match} fields and {@code requestIdentifier} (empty unless the requester VAT-ID is
	 * configured). A missing or non-boolean {@code valid} is treated as "no verdict"
	 * ({@code ServiceUnavailable}), never as {@code Invalid}.
	 *
	 * <p><b>VIES also answers HTTP 200 with an ERROR ENVELOPE</b> —
	 * {@code {"actionSucceed":false,"errorWrappers":[{"error":"..."}]}} — verified live on 2026-08-15.
	 * Those are split by cause: a request-side fault (our configuration, e.g.
	 * {@code INVALID_REQUESTER_INFO}) throws, so a run stops and names what to fix instead of writing a
	 * misleading status onto every partner; a service-side fault degrades to
	 * {@code ServiceUnavailable}. Per-member-state outages are reported by
	 * {@link #getUnavailableCountryCodes(VATaxIDConfig)}, not by this payload.
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

		final String viesError = extractErrorCode(json);
		if (viesError != null)
		{
			throwIfRequestSideError(rawResponse);

			logger.warn("VIES reported {} - reporting {}", viesError, VATaxIDStatus.ServiceUnavailable);
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

	/**
	 * Throws when {@code rawResponse} is a VIES error envelope naming a CONFIGURATION fault — one of ours to
	 * fix, not the service's, and not the checked record's (see {@link #REQUEST_SIDE_ERRORS} on why
	 * {@code INVALID_INPUT} is excluded). Recorded instead of thrown it would be indistinguishable from an
	 * outage, and under an {@code OnServiceUnavailable} of {@code Invalid} one bad configuration value would
	 * strip the tax certificate from every VAT-ID in the run.
	 *
	 * <p>Called from BOTH response paths, because VIES uses both: {@code INVALID_REQUESTER_INFO} arrives on
	 * HTTP 200 and {@code VOW-ERR-11} on HTTP 400.
	 *
	 * <p>Raises {@link VATaxIDCheckRequestRejectedException} rather than a bare {@link AdempiereException},
	 * and that type is load-bearing: the calling mass-check service swallows an ordinary exception per target on
	 * purpose, so a bare one would be logged once per target for the whole selection instead of stopping the
	 * run — see that exception's javadoc.
	 */
	private void throwIfRequestSideError(@Nullable final String rawResponse)
	{
		if (Check.isBlank(rawResponse))
		{
			return;
		}

		final String viesError;
		try
		{
			viesError = extractErrorCode(objectMapper.readTree(rawResponse));
		}
		catch (final Exception ignored)
		{
			// Unparseable: not an envelope we can classify, so leave it to the caller's own handling.
			return;
		}

		if (viesError == null || !REQUEST_SIDE_ERRORS.contains(viesError))
		{
			return;
		}

		// No appendParametersToMessage() here, unlike getBaseUrl above: this message is quoted verbatim into
		// the run's abort line, which the operator reads in the process log. Splicing the raw JSON envelope
		// into the middle of that sentence would bury the one instruction it exists to deliver. The envelope
		// stays on the exception as a parameter, so it still reaches AD_Issue and the server log.
		final VATaxIDCheckRequestRejectedException rejected = new VATaxIDCheckRequestRejectedException(
				viesError,
				"VIES rejected the request: " + viesError
						+ ". Check the VAT-ID configuration (requester member state and requester number,"
						+ " which must be the plain number without the country prefix).");
		rejected.setParameter("rawResponse", rawResponse);
		throw rejected;
	}

	/**
	 * @return the first {@code errorWrappers[].error} code when the body is an error envelope
	 * ({@code actionSucceed} present and false), else {@code null}.
	 */
	@Nullable
	private static String extractErrorCode(@NonNull final JsonNode json)
	{
		final JsonNode actionSucceed = json.get("actionSucceed");
		if (actionSucceed == null || !actionSucceed.isBoolean() || actionSucceed.asBoolean())
		{
			return null;
		}

		final JsonNode wrappers = json.get("errorWrappers");
		if (wrappers == null || !wrappers.isArray())
		{
			return UNKNOWN_VIES_ERROR;
		}

		for (final JsonNode wrapper : wrappers)
		{
			final String error = asTextOrNull(wrapper.get("error"));
			if (error != null)
			{
				return error;
			}
		}

		return UNKNOWN_VIES_ERROR;
	}

	@Nullable
	private static String asTextOrNull(@Nullable final JsonNode node)
	{
		return node != null && node.isTextual() ? node.asText() : null;
	}
}
