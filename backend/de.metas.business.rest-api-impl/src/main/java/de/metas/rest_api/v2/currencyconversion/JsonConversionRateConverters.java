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

import com.google.common.collect.ImmutableList;
import de.metas.RestUtils;
import de.metas.common.rest_api.v2.currencyconversion.JsonCurrency;
import de.metas.common.rest_api.v2.currencyconversion.JsonNewestConversionRate;
import de.metas.common.rest_api.v2.currencyconversion.JsonRequestConversionRateUpsertItem;
import de.metas.rest_api.v2.currencyconversion.NewestConversionRatesService.NewestConversionRatesFilter;
import de.metas.currency.ConversionRateCreateRequest;
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
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_Conversion_Rate;
import org.compiere.model.I_C_Currency;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * The single place that translates between the currency-conversion JSON DTOs and the domain
 * {@link ConversionRateCreateRequest}, modelled on
 * {@code de.metas.rest_api.v2.ordercandidates.impl.JsonConverters#fromJson}: <b>all</b> resolution (currency
 * code -> {@link CurrencyId} via {@link CurrencyRepository}, org, conversion type via
 * {@link ConversionRateRepository}, {@link ClientAndOrgId}, and org-timezone {@code validFrom}/{@code validTo}
 * date conversion) happens here, so the service operates on fully-resolved domain objects only. This REST module
 * never references {@code ICurrencyDAO} directly.
 * <p>
 * An unknown/inactive currency, an unknown org code, or an unknown conversion-type code raises a
 * {@code markAsUserValidationError} exception so the offending request item becomes a per-record {@code ERROR}
 * rather than aborting the batch or auto-creating master data.
 */
@Component
@RequiredArgsConstructor
public class JsonConversionRateConverters
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final CurrencyRepository currencyRepository;
	@NonNull private final ConversionRateRepository conversionRateRepository;

	/**
	 * The active currencies, ordered by ISO code, mapped to {@link JsonCurrency} ({@code currencyCode} =
	 * ISO code, {@code name} = {@code Description}).
	 */
	@NonNull
	public ImmutableList<JsonCurrency> getActiveCurrencies()
	{
		return queryBL
				.createQueryBuilder(I_C_Currency.class)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_C_Currency.COLUMNNAME_ISO_Code)
				.create()
				.stream()
				.map(JsonConversionRateConverters::toJsonCurrency)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private static JsonCurrency toJsonCurrency(@NonNull final I_C_Currency currency)
	{
		return JsonCurrency.builder()
				.currencyCode(currency.getISO_Code())
				.name(currency.getDescription())
				.build();
	}

	/**
	 * Resolves a single JSON upsert item into the fully-resolved domain {@link ConversionRateCreateRequest}. The
	 * client is always {@link ClientId#METASFRESH} (the sole {@code ClientId.METASFRESH} reference of the whole
	 * feature, so that the service never threads a client): the request carries only an org code, never a client.
	 */
	@NonNull
	public ConversionRateCreateRequest fromJson(@NonNull final JsonRequestConversionRateUpsertItem item)
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

		return ConversionRateCreateRequest.builder()
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
	 * Resolves the id of the single <b>active</b> {@code C_Currency} for the given ISO code.
	 * Throws a user-validation error when no active currency matches (an unknown or inactive ISO).
	 * <p>
	 * Delegates to {@link CurrencyRepository#getActiveCurrencyIdByCurrencyCodeOrNull(CurrencyCode)} (active-only,
	 * no auto-create) on purpose — not {@code ICurrencyDAO.getByCurrencyCode}, which auto-creates a missing
	 * currency (in the {@code PlainCurrencyDAO} test double) and does not filter inactive rows: the endpoint must
	 * surface an unknown/inactive ISO as a per-record error and must NOT auto-create a currency.
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
	 * Wraps the four raw {@code GET /newestRates} request params into the fully-resolved, typed
	 * {@link NewestConversionRatesFilter} at the controller boundary, so raw strings never travel into the service.
	 * Each param is optional: a blank/omitted value resolves to {@code null} (no narrowing — spans all). A non-blank
	 * value is resolved to its typed id here; an unknown currency ISO or conversion-type code raises the same
	 * {@code markAsUserValidationError} as the upsert path (surfaced by the controller as a friendly {@code 422}).
	 */
	@NonNull
	public NewestConversionRatesFilter toNewestRatesFilter(
			@Nullable final String fromCurrencyCode,
			@Nullable final String toCurrencyCode,
			@Nullable final String conversionTypeCode,
			@Nullable final String orgCode)
	{
		return NewestConversionRatesFilter.builder()
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

	/**
	 * Maps a stored {@code C_Conversion_Rate} row to its response DTO. {@code ValidFrom} is read back through the
	 * <b>org's</b> zone (matching the store path), so store-and-read use the same org zone consistently.
	 */
	@NonNull
	public JsonNewestConversionRate toJsonNewestConversionRate(@NonNull final I_C_Conversion_Rate rate)
	{
		final CurrencyId fromCurrencyId = CurrencyId.ofRepoId(rate.getC_Currency_ID());
		final CurrencyId toCurrencyId = CurrencyId.ofRepoId(rate.getC_Currency_ID_To());
		final CurrencyConversionTypeId conversionTypeId = CurrencyConversionTypeId.ofRepoId(rate.getC_ConversionType_ID());
		final ZoneId orgZoneId = orgDAO.getTimeZone(OrgId.ofRepoId(rate.getAD_Org_ID()));

		return JsonNewestConversionRate.builder()
				.fromCurrencyCode(currencyRepository.getCurrencyCodeById(fromCurrencyId).toThreeLetterCode())
				.toCurrencyCode(currencyRepository.getCurrencyCodeById(toCurrencyId).toThreeLetterCode())
				.conversionTypeCode(conversionRateRepository.getConversionTypeMethodById(conversionTypeId).getCode())
				.validFrom(TimeUtil.asLocalDate(rate.getValidFrom(), orgZoneId))
				.multiplyRate(rate.getMultiplyRate())
				.divideRate(rate.getDivideRate())
				.build();
	}

	@NonNull
	private OrgId resolveOrgId(@Nullable final String orgCode)
	{
		// A blank orgCode means the shared, cross-org rate: org 0 (OrgId.ANY).
		// RestUtils.retrieveOrgIdOrDefault falls back to the context org (Env.getOrgId()) when blank, not to
		// OrgId.ANY, so only its non-blank path (resolve by AD_Org.Value) is reused here.
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

	/**
	 * Parses a non-blank conversion-type code into its {@link ConversionTypeMethod}, raising the shared
	 * {@code markAsUserValidationError} on an unknown code. The single place the {@code ConversionTypeMethod.forCode}
	 * + user-validation-error logic lives, reused by both the upsert path ({@link #resolveConversionTypeId}) and the
	 * optional newest-rates filter ({@link #resolveOptionalConversionTypeId}).
	 */
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
