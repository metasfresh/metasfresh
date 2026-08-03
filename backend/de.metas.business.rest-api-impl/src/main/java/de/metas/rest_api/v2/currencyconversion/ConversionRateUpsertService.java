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

import de.metas.common.rest_api.v2.currencyconversion.JsonRequestConversionRateUpsert;
import de.metas.common.rest_api.v2.currencyconversion.JsonRequestConversionRateUpsertItem;
import de.metas.common.rest_api.v2.currencyconversion.JsonResponseConversionRateUpsert;
import de.metas.common.rest_api.v2.currencyconversion.JsonResponseConversionRateUpsertItem;
import de.metas.common.rest_api.v2.currencyconversion.JsonResponseConversionRateUpsertItem.SyncOutcome;
import de.metas.currency.ConversionTypeMethod;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_Conversion_Rate;
import org.compiere.model.I_C_Currency;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Upserts normalized currency-conversion rates into {@code C_Conversion_Rate}.
 * <p>
 * This is the single-direction path only: each request item is resolved and persisted on its own.
 * Auto-reciprocal / explicit-reverse handling is a separate concern (see Task 3).
 * <p>
 * Each request item is applied independently: a per-record failure (unknown/inactive currency,
 * unknown conversion-type code, {@code from == to}, non-positive rate, {@code validTo < validFrom})
 * yields an {@code ERROR} response item and never aborts the batch nor auto-creates a currency.
 */
@Service
public class ConversionRateUpsertService
{
	private final ICurrencyDAO currencyDAO;

	/** Scale + rounding for the derived {@code DivideRate = 1 / multiplyRate}. */
	private static final int DIVIDE_RATE_SCALE = 12;
	private static final RoundingMode DIVIDE_RATE_ROUNDING = RoundingMode.HALF_UP;

	public ConversionRateUpsertService(@NonNull final ICurrencyDAO currencyDAO)
	{
		this.currencyDAO = currencyDAO;
	}

	@NonNull
	public JsonResponseConversionRateUpsert upsert(@NonNull final JsonRequestConversionRateUpsert request)
	{
		final String adLanguage = Env.getADLanguageOrBaseLanguage();

		final JsonResponseConversionRateUpsert.JsonResponseConversionRateUpsertBuilder responseBuilder = JsonResponseConversionRateUpsert.builder();
		for (final JsonRequestConversionRateUpsertItem item : request.getRequestItems())
		{
			responseBuilder.responseItem(upsertItem(item, adLanguage));
		}
		return responseBuilder.build();
	}

	@NonNull
	private JsonResponseConversionRateUpsertItem upsertItem(
			@NonNull final JsonRequestConversionRateUpsertItem item,
			@NonNull final String adLanguage)
	{
		try
		{
			final SyncOutcome outcome = upsertItem0(item);
			return JsonResponseConversionRateUpsertItem.builder()
					.fromCurrencyCode(item.getFromCurrencyCode())
					.toCurrencyCode(item.getToCurrencyCode())
					.syncOutcome(outcome)
					.build();
		}
		catch (final Exception ex)
		{
			return JsonResponseConversionRateUpsertItem.builder()
					.fromCurrencyCode(item.getFromCurrencyCode())
					.toCurrencyCode(item.getToCurrencyCode())
					.syncOutcome(SyncOutcome.ERROR)
					.error(de.metas.rest_api.utils.v2.JsonErrors.ofThrowable(ex, adLanguage))
					.build();
		}
	}

	@NonNull
	private SyncOutcome upsertItem0(@NonNull final JsonRequestConversionRateUpsertItem item)
	{
		final CurrencyId fromCurrencyId = resolveActiveCurrencyId(item.getFromCurrencyCode());
		final CurrencyId toCurrencyId = resolveActiveCurrencyId(item.getToCurrencyCode());

		final OrgId orgId = resolveOrgId(item.getOrgCode());
		final ClientId clientId = Env.getClientId();
		final LocalDate validFrom = item.getValidFrom();
		final CurrencyConversionTypeId conversionTypeId = resolveConversionTypeId(
				item.getConversionTypeCode(),
				clientId,
				orgId,
				validFrom);

		final BigDecimal multiplyRate = item.getMultiplyRate();

		// Validate the interceptor invariants explicitly so a bad record becomes a friendly
		// per-record error instead of a raw save-path exception.
		validateInvariants(fromCurrencyId, toCurrencyId, multiplyRate, validFrom, item.getValidTo());

		final BigDecimal divideRate = deriveDivideRate(multiplyRate);

		I_C_Conversion_Rate record = findExistingRate(clientId, orgId, fromCurrencyId, toCurrencyId, conversionTypeId, validFrom);
		final SyncOutcome outcome;
		if (record == null)
		{
			record = InterfaceWrapperHelper.newInstance(I_C_Conversion_Rate.class);
			record.setAD_Org_ID(orgId.getRepoId());
			record.setC_Currency_ID(fromCurrencyId.getRepoId());
			record.setC_Currency_ID_To(toCurrencyId.getRepoId());
			record.setC_ConversionType_ID(conversionTypeId.getRepoId());
			record.setValidFrom(TimeUtil.asTimestamp(validFrom));
			outcome = SyncOutcome.CREATED;
		}
		else
		{
			outcome = SyncOutcome.UPDATED;
		}

		record.setMultiplyRate(multiplyRate);
		record.setDivideRate(divideRate);
		record.setValidTo(item.getValidTo() != null ? TimeUtil.asTimestamp(item.getValidTo()) : null);

		InterfaceWrapperHelper.save(record);

		return outcome;
	}

	@NonNull
	private CurrencyId resolveActiveCurrencyId(@NonNull final String isoCode)
	{
		final I_C_Currency currency = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_C_Currency.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Currency.COLUMNNAME_ISO_Code, isoCode)
				.create()
				.first(I_C_Currency.class);

		if (currency == null)
		{
			throw new AdempiereException("@NotFound@ @C_Currency_ID@: " + isoCode)
					.markAsUserValidationError();
		}
		return CurrencyId.ofRepoId(currency.getC_Currency_ID());
	}

	@NonNull
	private OrgId resolveOrgId(@Nullable final String orgCode)
	{
		if (orgCode == null || orgCode.trim().isEmpty())
		{
			return OrgId.ANY; // org 0 (shared)
		}
		try
		{
			return OrgId.ofRepoId(Integer.parseInt(orgCode.trim()));
		}
		catch (final NumberFormatException ex)
		{
			throw new AdempiereException("@Invalid@ @AD_Org_ID@: " + orgCode)
					.markAsUserValidationError();
		}
	}

	@NonNull
	private CurrencyConversionTypeId resolveConversionTypeId(
			@Nullable final String conversionTypeCode,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId,
			@NonNull final LocalDate validFrom)
	{
		if (conversionTypeCode == null || conversionTypeCode.trim().isEmpty())
		{
			return currencyDAO.getDefaultConversionTypeId(clientId, orgId, toInstant(validFrom));
		}

		final ConversionTypeMethod method;
		try
		{
			method = ConversionTypeMethod.forCode(conversionTypeCode.trim());
		}
		catch (final IllegalArgumentException ex)
		{
			throw new AdempiereException("@Invalid@ @C_ConversionType_ID@: " + conversionTypeCode)
					.markAsUserValidationError();
		}
		return currencyDAO.getConversionTypeId(method);
	}

	private static void validateInvariants(
			@NonNull final CurrencyId fromCurrencyId,
			@NonNull final CurrencyId toCurrencyId,
			@NonNull final BigDecimal multiplyRate,
			@NonNull final LocalDate validFrom,
			@Nullable final LocalDate validTo)
	{
		if (fromCurrencyId.equals(toCurrencyId))
		{
			throw new AdempiereException("@C_Currency_ID@ = @C_Currency_ID@").markAsUserValidationError();
		}
		if (multiplyRate.compareTo(BigDecimal.ZERO) <= 0)
		{
			throw new AdempiereException("@MultiplyRate@ <= 0").markAsUserValidationError();
		}
		if (validTo != null && validTo.isBefore(validFrom))
		{
			throw new AdempiereException("@ValidTo@ < @ValidFrom@: " + validTo + " < " + validFrom)
					.markAsUserValidationError();
		}
	}

	@NonNull
	private static BigDecimal deriveDivideRate(@NonNull final BigDecimal multiplyRate)
	{
		return BigDecimal.ONE.divide(multiplyRate, DIVIDE_RATE_SCALE, DIVIDE_RATE_ROUNDING);
	}

	@Nullable
	private I_C_Conversion_Rate findExistingRate(
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId,
			@NonNull final CurrencyId fromCurrencyId,
			@NonNull final CurrencyId toCurrencyId,
			@NonNull final CurrencyConversionTypeId conversionTypeId,
			@NonNull final LocalDate validFrom)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Conversion_Rate.class)
				.addEqualsFilter(I_C_Conversion_Rate.COLUMNNAME_AD_Client_ID, clientId.getRepoId())
				.addEqualsFilter(I_C_Conversion_Rate.COLUMNNAME_AD_Org_ID, orgId.getRepoId())
				.addEqualsFilter(I_C_Conversion_Rate.COLUMNNAME_C_Currency_ID, fromCurrencyId.getRepoId())
				.addEqualsFilter(I_C_Conversion_Rate.COLUMNNAME_C_Currency_ID_To, toCurrencyId.getRepoId())
				.addEqualsFilter(I_C_Conversion_Rate.COLUMNNAME_C_ConversionType_ID, conversionTypeId.getRepoId())
				.addEqualsFilter(I_C_Conversion_Rate.COLUMNNAME_ValidFrom, TimeUtil.asTimestamp(validFrom))
				.create()
				.first(I_C_Conversion_Rate.class);
	}

	private static Instant toInstant(@NonNull final LocalDate localDate)
	{
		return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
	}
}
