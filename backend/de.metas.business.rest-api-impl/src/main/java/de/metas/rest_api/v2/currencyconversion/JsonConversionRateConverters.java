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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.RestUtils;
import de.metas.common.rest_api.v2.currencyconversion.JsonCurrency;
import de.metas.common.rest_api.v2.currencyconversion.JsonNewestConversionRate;
import de.metas.common.rest_api.v2.currencyconversion.JsonRequestConversionRateUpsertItem;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyConversionRate;
import de.metas.currency.CurrencyConversionUpsertRequest;
import de.metas.currency.ConversionRateQuery;
import de.metas.currency.ConversionRateRepository;
import de.metas.currency.ConversionTypeMethod;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Translates between the currency-conversion JSON DTOs and the domain {@link CurrencyConversionUpsertRequest}
 * (modelled on {@code JsonConverters#fromJson}): all resolution — currency code, org, conversion type, dates —
 * happens here, so the service operates on fully-resolved domain objects. An unknown/inactive currency, org, or
 * conversion-type code raises a {@code markAsUserValidationError} so the item becomes a per-item {@code ERROR}
 * rather than aborting the batch or auto-creating master data.
 */
@Component
@RequiredArgsConstructor
public class JsonConversionRateConverters
{
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final CurrencyRepository currencyRepository;
	@NonNull private final ConversionRateRepository conversionRateRepository;

	@VisibleForTesting
	public static JsonConversionRateConverters newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(JsonConversionRateConverters.class,
				() -> new JsonConversionRateConverters(
						CurrencyRepository.newInstanceForUnitTesting(),
						ConversionRateRepository.newInstanceForUnitTesting()));
	}

	/** The active currencies (ISO-ordered) as {@link JsonCurrency} ({@code name} = {@code Description}). */
	@NonNull
	public ImmutableList<JsonCurrency> getActiveCurrencies()
	{
		return currencyRepository.getActiveCurrenciesOrderedByCode()
				.stream()
				.map(JsonConversionRateConverters::toJsonCurrency)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private static JsonCurrency toJsonCurrency(@NonNull final Currency currency)
	{
		return JsonCurrency.builder()
				.currencyCode(currency.getCurrencyCode().toThreeLetterCode())
				.name(currency.getDescription())
				.build();
	}

	/** Resolves a JSON upsert item into the domain {@link CurrencyConversionUpsertRequest}. */
	@NonNull
	public CurrencyConversionUpsertRequest fromJson(@NonNull final JsonRequestConversionRateUpsertItem item)
	{
		final OrgId orgId = resolveOrgId(item.getOrgCode());
		final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(ClientId.METASFRESH, orgId);
		final ZoneId orgZoneId = orgDAO.getTimeZone(orgId);

		final CurrencyId fromCurrencyId = getActiveCurrencyId(item.getFromCurrencyCode());
		final CurrencyId toCurrencyId = getActiveCurrencyId(item.getToCurrencyCode());
		final CurrencyConversionTypeId conversionTypeId = resolveConversionTypeId(
				item.getConversionTypeCode(),
				clientAndOrgId,
				item.getValidFrom(),
				orgZoneId);

		return CurrencyConversionUpsertRequest.builder()
				.clientAndOrgId(clientAndOrgId)
				.fromCurrencyId(fromCurrencyId)
				.toCurrencyId(toCurrencyId)
				.conversionTypeId(conversionTypeId)
				.validFrom(item.getValidFrom())
				.validTo(item.getValidTo())
				.multiplyRate(item.getMultiplyRate())
				.orgZoneId(orgZoneId)
				.build();
	}

	/**
	 * The id of the single active {@code C_Currency} for the ISO code, or a user-validation error if none.
	 * Uses {@link CurrencyRepository#getActiveCurrencyIdByCurrencyCodeOrNull} (active-only, no auto-create) — not
	 * {@code ICurrencyDAO.getByCurrencyCode}, which auto-creates and ignores inactive: the endpoint must not auto-create.
	 */
	@NonNull
	public CurrencyId getActiveCurrencyId(@NonNull final String isoCode)
	{
		final CurrencyId currencyId = currencyRepository.getActiveCurrencyIdByCurrencyCodeOrNull(CurrencyCode.ofThreeLetterCode(isoCode));
		if (currencyId == null)
		{
			throw new AdempiereException("@NotFound@ @C_Currency_ID@: " + isoCode)
					.markAsUserValidationError();
		}
		return currencyId;
	}

	/**
	 * Resolves the four raw {@code GET /newestRates} params into a typed {@link ConversionRateQuery} at the controller
	 * boundary. Each param is optional (blank -> {@code null} = no narrowing); an unknown currency/type code raises the
	 * same {@code markAsUserValidationError} as the upsert path.
	 */
	@NonNull
	public ConversionRateQuery toNewestRatesFilter(
			@Nullable final String fromCurrencyCode,
			@Nullable final String toCurrencyCode,
			@Nullable final String conversionTypeCode,
			@Nullable final String orgCode)
	{
		return ConversionRateQuery.builder()
				.orgId(resolveOptionalOrgId(orgCode))
				.fromCurrencyId(resolveOptionalCurrencyId(fromCurrencyCode))
				.toCurrencyId(resolveOptionalCurrencyId(toCurrencyCode))
				.conversionTypeId(resolveOptionalConversionTypeId(conversionTypeCode))
				.build();
	}

	@Nullable
	private OrgId resolveOptionalOrgId(@Nullable final String orgCode)
	{
		if (Check.isBlank(orgCode))
		{
			return null;
		}
		return RestUtils.retrieveOrgIdOrDefault(orgCode.trim());
	}

	@Nullable
	private CurrencyId resolveOptionalCurrencyId(@Nullable final String isoCode)
	{
		if (Check.isBlank(isoCode))
		{
			return null;
		}
		return getActiveCurrencyId(isoCode.trim());
	}

	@Nullable
	private CurrencyConversionTypeId resolveOptionalConversionTypeId(@Nullable final String conversionTypeCode)
	{
		if (Check.isBlank(conversionTypeCode))
		{
			return null;
		}
		return conversionRateRepository.getConversionTypeId(parseConversionTypeMethod(conversionTypeCode));
	}

	/** Maps a stored {@link CurrencyConversionRate} to its response DTO. */
	@NonNull
	public JsonNewestConversionRate toJsonNewestConversionRate(@NonNull final CurrencyConversionRate rate)
	{
		return JsonNewestConversionRate.builder()
				.fromCurrencyCode(currencyRepository.getCurrencyCodeById(rate.getFromCurrencyId()).toThreeLetterCode())
				.toCurrencyCode(currencyRepository.getCurrencyCodeById(rate.getToCurrencyId()).toThreeLetterCode())
				.conversionTypeCode(conversionRateRepository.getConversionTypeMethodById(rate.getConversionTypeId()).getCode())
				.validFrom(rate.getValidFrom())
				.multiplyRate(rate.getMultiplyRate())
				.divideRate(rate.getDivideRate())
				.build();
	}

	@NonNull
	private OrgId resolveOrgId(@Nullable final String orgCode)
	{
		// Blank orgCode = the shared cross-org rate (OrgId.ANY). RestUtils.retrieveOrgIdOrDefault falls back to the context org when blank, not ANY, so only its non-blank path is reused.
		if (Check.isBlank(orgCode))
		{
			return OrgId.ANY;
		}
		return RestUtils.retrieveOrgIdOrDefault(orgCode);
	}

	@NonNull
	private CurrencyConversionTypeId resolveConversionTypeId(
			@Nullable final String conversionTypeCode,
			@NonNull final ClientAndOrgId clientAndOrgId,
			@NonNull final LocalDate validFrom,
			@NonNull final ZoneId orgZoneId)
	{
		if (Check.isBlank(conversionTypeCode))
		{
			return conversionRateRepository.getDefaultConversionTypeId(
					clientAndOrgId.getClientId(),
					clientAndOrgId.getOrgId(),
					validFrom.atStartOfDay(orgZoneId).toInstant());
		}

		return conversionRateRepository.getConversionTypeId(parseConversionTypeMethod(conversionTypeCode));
	}

	/** Parses a non-blank conversion-type code into its {@link ConversionTypeMethod}, raising {@code markAsUserValidationError} on an unknown code. */
	@NonNull
	private static ConversionTypeMethod parseConversionTypeMethod(@NonNull final String conversionTypeCode)
	{
		try
		{
			return ConversionTypeMethod.forCode(conversionTypeCode.trim());
		}
		catch (final IllegalArgumentException ex)
		{
			throw new AdempiereException("@Invalid@ @C_ConversionType_ID@: " + conversionTypeCode)
					.markAsUserValidationError();
		}
	}
}
