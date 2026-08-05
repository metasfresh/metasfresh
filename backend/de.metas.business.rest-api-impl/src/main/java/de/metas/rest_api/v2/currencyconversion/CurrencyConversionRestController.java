/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v2.currencyconversion;

import de.metas.Profiles;
import de.metas.common.rest_api.v2.currencyconversion.JsonCurrency;
import de.metas.common.rest_api.v2.currencyconversion.JsonNewestConversionRate;
import de.metas.common.rest_api.v2.currencyconversion.JsonRequestConversionRateUpsert;
import de.metas.common.rest_api.v2.currencyconversion.JsonResponseConversionRateUpsert;
import de.metas.common.rest_api.v2.currencyconversion.JsonResponseCurrencies;
import de.metas.common.rest_api.v2.currencyconversion.JsonResponseNewestConversionRates;
import de.metas.i18n.Language;
import de.metas.logging.LogManager;
import de.metas.currency.ConversionRateQuery;
import de.metas.rest_api.utils.v2.JsonErrors;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import java.util.List;

/**
 * v2 REST endpoints for the currency-conversion integration:
 * <ul>
 *     <li>{@code PUT .../rates} — batch upsert of normalized conversion rates.</li>
 *     <li>{@code GET .../currencies} — the active currencies the caller should fetch rates for.</li>
 *     <li>{@code GET .../newestRates} — the newest stored rate per {@code (from, to, type)} combo.</li>
 * </ul>
 * <p>
 * Authentication is the standard metasfresh v2 REST auth applied to every {@code ENDPOINT_API_V2}
 * endpoint (unauthenticated callers get {@code 401}); this controller follows the same class-level
 * {@code @RestController} + {@code @RequestMapping(ENDPOINT_API_V2 + ...)} + {@code @Profile(App)}
 * convention as {@code ProductsRestController} and does not add a bespoke auth scheme.
 */
@RestController
@RequestMapping(value = { MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/currencyconversion" })
@Profile(Profiles.PROFILE_App)
@RequiredArgsConstructor
public class CurrencyConversionRestController
{
	private static final Logger logger = LogManager.getLogger(CurrencyConversionRestController.class);

	@NonNull private final ConversionRateUpsertService conversionRateUpsertService;
	@NonNull private final NewestConversionRatesService newestConversionRatesService;
	@NonNull private final JsonConversionRateConverters jsonConverters;

	@ApiOperation("Batch-upsert normalized currency-conversion rates into C_Conversion_Rate.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "All records applied (syncOutcome=SUCCESS); each record reports its own outcome"),
			@ApiResponse(code = 207, message = "Partial success (syncOutcome=PARTIAL_SUCCESS): some records applied, at least one failed; the response reports the per-record outcomes"),
			@ApiResponse(code = 401, message = "You are not authorized to invoke this endpoint"),
			@ApiResponse(code = 403, message = "Accessing a related resource is forbidden"),
			@ApiResponse(code = 422, message = "No records were applied — either every record failed (syncOutcome=ERROR; the response reports the per-record outcomes) or the request could not be processed")
	})
	@PutMapping("/rates")
	public ResponseEntity<?> upsertRates(@RequestBody @NonNull final JsonRequestConversionRateUpsert request)
	{
		final Language adLanguage = Language.getLanguage(Env.getADLanguageOrBaseLanguage());
		try
		{
			// Per-record failures are reported inside the response (as ERROR items) and never abort the batch.
			// The service computes a top-level aggregate syncOutcome over the per-record outcomes, and the HTTP
			// status is derived from that single value so a caller inspecting only the status still knows what
			// happened: SUCCESS (none failed) -> 200; PARTIAL_SUCCESS (some applied, some failed) -> 207
			// Multi-Status; ERROR (nothing applied — every record failed) -> 422. A thrown exception is a
			// catastrophic (non-per-record) failure and also becomes a friendly 422 top-level error.
			// This mirrors the house multi-response mapping in the scripted-adapter's
			// ScriptedImportConversionRestAPIRouteBuilder#handleMultipleResponses (which uses 500 for all-failed;
			// here all-failed is a client-side per-record validation failure, so 422 is used instead of 500).
			final JsonResponseConversionRateUpsert response = conversionRateUpsertService.upsert(request, adLanguage);
			final HttpStatus status;
			switch (response.getSyncOutcome())
			{
				case SUCCESS:
					status = HttpStatus.OK;
					break;
				case PARTIAL_SUCCESS:
					status = HttpStatus.MULTI_STATUS;
					break;
				case ERROR:
				default:
					status = HttpStatus.UNPROCESSABLE_ENTITY;
					break;
			}
			return ResponseEntity.status(status).body(response);
		}
		catch (final Exception ex)
		{
			logger.error(ex.getMessage(), ex);
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.body(JsonErrors.ofThrowable(ex, adLanguage.getAD_Language()));
		}
	}

	@ApiOperation("Retrieve the active currencies (the set the caller should fetch rates for).")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The active currencies were retrieved"),
			@ApiResponse(code = 401, message = "You are not authorized to invoke this endpoint"),
			@ApiResponse(code = 403, message = "Accessing a related resource is forbidden"),
			@ApiResponse(code = 422, message = "The request could not be processed")
	})
	@GetMapping("/currencies")
	public ResponseEntity<?> getCurrencies()
	{
		final Language adLanguage = Language.getLanguage(Env.getADLanguageOrBaseLanguage());
		try
		{
			final List<JsonCurrency> currencies = jsonConverters.getActiveCurrencies();

			return ResponseEntity.ok(JsonResponseCurrencies.builder()
					.currencies(currencies)
					.build());
		}
		catch (final Exception ex)
		{
			logger.error(ex.getMessage(), ex);
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.body(JsonErrors.ofThrowable(ex, adLanguage.getAD_Language()));
		}
	}

	@ApiOperation("Retrieve the newest stored conversion rate per (from, to, type) combo.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The newest rates were retrieved"),
			@ApiResponse(code = 401, message = "You are not authorized to invoke this endpoint"),
			@ApiResponse(code = 403, message = "Accessing a related resource is forbidden"),
			@ApiResponse(code = 422, message = "The request could not be processed")
	})
	@GetMapping("/newestRates")
	public ResponseEntity<?> getNewestRates(
			@ApiParam("3-letter ISO source currency code; omitted spans all source currencies.")
			@RequestParam(value = "fromCurrencyCode", required = false) @Nullable final String fromCurrencyCode,

			@ApiParam("3-letter ISO target currency code; omitted spans all target currencies.")
			@RequestParam(value = "toCurrencyCode", required = false) @Nullable final String toCurrencyCode,

			@ApiParam("Conversion type code (S/P/A/C); omitted spans all conversion types.")
			@RequestParam(value = "conversionTypeCode", required = false) @Nullable final String conversionTypeCode,

			@ApiParam("Org code; omitted spans all orgs (incl. the shared org 0 rows).")
			@RequestParam(value = "orgCode", required = false) @Nullable final String orgCode)
	{
		final Language adLanguage = Language.getLanguage(Env.getADLanguageOrBaseLanguage());
		try
		{
			final ConversionRateQuery query = jsonConverters.toNewestRatesFilter(
					fromCurrencyCode, toCurrencyCode, conversionTypeCode, orgCode);

			final List<JsonNewestConversionRate> rates = newestConversionRatesService.list(query);

			return ResponseEntity.ok(JsonResponseNewestConversionRates.builder()
					.rates(rates)
					.build());
		}
		catch (final Exception ex)
		{
			logger.error(ex.getMessage(), ex);
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.body(JsonErrors.ofThrowable(ex, adLanguage.getAD_Language()));
		}
	}
}
