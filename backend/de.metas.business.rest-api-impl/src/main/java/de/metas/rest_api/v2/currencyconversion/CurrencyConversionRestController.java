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
import de.metas.rest_api.utils.v2.JsonErrors;
import de.metas.rest_api.v2.currencyconversion.NewestConversionRatesService.NewestConversionRatesFilter;
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

	private final @NonNull ConversionRateUpsertService conversionRateUpsertService;
	private final @NonNull NewestConversionRatesService newestConversionRatesService;
	private final @NonNull JsonConversionRateConverters jsonConverters;

	@ApiOperation("Batch-upsert normalized currency-conversion rates into C_Conversion_Rate.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The batch was processed; the response reports the per-record outcome"),
			@ApiResponse(code = 401, message = "You are not authorized to invoke this endpoint"),
			@ApiResponse(code = 403, message = "Accessing a related resource is forbidden"),
			@ApiResponse(code = 422, message = "The request could not be processed")
	})
	@PutMapping("/rates")
	public ResponseEntity<?> upsertRates(@RequestBody @NonNull final JsonRequestConversionRateUpsert request)
	{
		final Language adLanguage = Language.getLanguage(Env.getADLanguageOrBaseLanguage());
		try
		{
			// Per-record failures are already reported inside the response (as ERROR items) and never abort the
			// batch; the batch as a whole succeeds. A thrown exception here is a catastrophic (non-per-record)
			// failure and becomes a friendly top-level error.
			final JsonResponseConversionRateUpsert response = conversionRateUpsertService.upsert(request, adLanguage);
			return ResponseEntity.ok(response);
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
			final NewestConversionRatesFilter filter = NewestConversionRatesFilter.builder()
					.fromCurrencyCode(fromCurrencyCode)
					.toCurrencyCode(toCurrencyCode)
					.conversionTypeCode(conversionTypeCode)
					.orgCode(orgCode)
					.build();

			final List<JsonNewestConversionRate> rates = newestConversionRatesService.list(filter);

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
