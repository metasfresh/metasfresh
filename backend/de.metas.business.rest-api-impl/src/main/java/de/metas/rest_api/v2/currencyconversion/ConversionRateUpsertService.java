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

import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.rest_api.v2.currencyconversion.JsonRequestConversionRateUpsert;
import de.metas.common.rest_api.v2.currencyconversion.JsonRequestConversionRateUpsertItem;
import de.metas.common.rest_api.v2.currencyconversion.JsonResponseConversionRateUpsert;
import de.metas.common.rest_api.v2.currencyconversion.JsonResponseConversionRateUpsertItem;
import de.metas.common.rest_api.v2.currencyconversion.JsonResponseConversionRateUpsertItem.SyncOutcome;
import de.metas.currency.ConversionTypeMethod;
import de.metas.currency.ICurrencyDAO;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_Conversion_Rate;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;

/**
 * Upserts normalized currency-conversion rates into {@code C_Conversion_Rate}.
 * <p>
 * Each caller-supplied request item is resolved and persisted on its own via the single-direction
 * path. On top of that, both directions are always ensured: for every successfully-resolvable
 * forward item whose reverse {@code (to -> from)} is <b>not</b> present in the same request, the
 * reciprocal ({@code MultiplyRate = 1 / rate}) is auto-written; when the caller supplied the reverse
 * itself, that reverse is honored untouched (never overwritten with a computed reciprocal).
 * <p>
 * Each request item is applied independently: a per-record failure (unknown/inactive currency,
 * unknown conversion-type code, {@code from == to}, non-positive rate, {@code validTo < validFrom})
 * yields an {@code ERROR} response item and never aborts the batch nor auto-creates a currency.
 * <p>
 * The batch's {@link SyncAdvise} (default {@code CREATE_OR_MERGE}) governs whether an existing row is
 * updated ({@code ifExists}) or a missing row is created ({@code ifNotExists}): a don't-update advise on
 * an existing row yields {@code NOTHING_DONE}; a fail-if-not-exists advise on a missing row yields a
 * per-record error.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ConversionRateUpsertService
{
	@NonNull private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	@NonNull private final CurrencyConversionRepository currencyConversionRepository;

	/** Scale + rounding for the derived {@code DivideRate = 1 / multiplyRate}. */
	private static final int DIVIDE_RATE_SCALE = 12;
	private static final RoundingMode DIVIDE_RATE_ROUNDING = RoundingMode.HALF_UP;

	@NonNull
	public JsonResponseConversionRateUpsert upsert(
			@NonNull final ClientId clientId,
			@NonNull final JsonRequestConversionRateUpsert request)
	{
		final String adLanguage = Env.getADLanguageOrBaseLanguage();
		final SyncAdvise syncAdvise = request.getSyncAdvise();

		// First, collect the natural keys the caller explicitly supplied, so that a caller-supplied
		// reverse is honored untouched (never overwritten by a computed reciprocal). Keys are only
		// added for items that resolve cleanly; an unresolvable item simply contributes no key.
		final Set<NaturalKey> callerSuppliedKeys = new HashSet<>();
		for (final JsonRequestConversionRateUpsertItem item : request.getRequestItems())
		{
			final NaturalKey key = naturalKeyOrNull(clientId, item);
			if (key != null)
			{
				callerSuppliedKeys.add(key);
			}
		}

		final JsonResponseConversionRateUpsert.JsonResponseConversionRateUpsertBuilder responseBuilder = JsonResponseConversionRateUpsert.builder();
		for (final JsonRequestConversionRateUpsertItem item : request.getRequestItems())
		{
			responseBuilder.responseItem(upsertItem(clientId, item, syncAdvise, adLanguage, callerSuppliedKeys));
		}
		return responseBuilder.build();
	}

	/**
	 * Resolves the natural key of the given request item without side effects, returning {@code null}
	 * when it cannot be resolved (unknown/inactive currency, unknown type code, invalid org). Used only
	 * to detect which reverse directions the caller supplied; the authoritative validation + persistence
	 * still happens in {@link #upsertItem0}.
	 */
	@Nullable
	private NaturalKey naturalKeyOrNull(
			@NonNull final ClientId clientId,
			@NonNull final JsonRequestConversionRateUpsertItem item)
	{
		try
		{
			final CurrencyId fromCurrencyId = currencyConversionRepository.findActiveCurrencyIdByIsoCode(item.getFromCurrencyCode());
			final CurrencyId toCurrencyId = currencyConversionRepository.findActiveCurrencyIdByIsoCode(item.getToCurrencyCode());
			final OrgId orgId = resolveOrgId(item.getOrgCode());
			final CurrencyConversionTypeId conversionTypeId = resolveConversionTypeId(
					item.getConversionTypeCode(),
					clientId,
					orgId,
					item.getValidFrom());
			return new NaturalKey(orgId, fromCurrencyId, toCurrencyId, conversionTypeId, item.getValidFrom());
		}
		catch (final Exception ex)
		{
			// Best-effort probe: an unresolvable item contributes no caller-supplied key (the
			// authoritative validation + error reporting still happens in upsertItem0). Leave a trace
			// so a wrong-reciprocal investigation is not a diagnostic black hole.
			log.debug("naturalKeyOrNull: failed to resolve key for item {}; treating as caller-not-supplied", item, ex);
			return null;
		}
	}

	@NonNull
	private JsonResponseConversionRateUpsertItem upsertItem(
			@NonNull final ClientId clientId,
			@NonNull final JsonRequestConversionRateUpsertItem item,
			@NonNull final SyncAdvise syncAdvise,
			@NonNull final String adLanguage,
			@NonNull final Set<NaturalKey> callerSuppliedKeys)
	{
		try
		{
			final SyncOutcome outcome = upsertItem0(clientId, item, syncAdvise, callerSuppliedKeys);
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
	private SyncOutcome upsertItem0(
			@NonNull final ClientId clientId,
			@NonNull final JsonRequestConversionRateUpsertItem item,
			@NonNull final SyncAdvise syncAdvise,
			@NonNull final Set<NaturalKey> callerSuppliedKeys)
	{
		final CurrencyId fromCurrencyId = currencyConversionRepository.findActiveCurrencyIdByIsoCode(item.getFromCurrencyCode());
		final CurrencyId toCurrencyId = currencyConversionRepository.findActiveCurrencyIdByIsoCode(item.getToCurrencyCode());

		final OrgId orgId = resolveOrgId(item.getOrgCode());
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

		final BigDecimal divideRate = reciprocal(multiplyRate);

		final SyncOutcome outcome = saveRate(
				clientId, orgId, fromCurrencyId, toCurrencyId, conversionTypeId, validFrom,
				multiplyRate, divideRate,
				item.getValidTo(), syncAdvise);

		// Ensure the reverse direction exists. If the caller supplied the reverse itself (same
		// request), honor it untouched; otherwise auto-write the reciprocal — subject to the same advise.
		final NaturalKey reverseKey = new NaturalKey(orgId, toCurrencyId, fromCurrencyId, conversionTypeId, validFrom);
		if (!callerSuppliedKeys.contains(reverseKey))
		{
			final BigDecimal reciprocalMultiplyRate = reciprocal(multiplyRate);
			final BigDecimal reciprocalDivideRate = reciprocal(reciprocalMultiplyRate);
			saveRate(
					clientId, orgId, toCurrencyId, fromCurrencyId, conversionTypeId, validFrom,
					reciprocalMultiplyRate, reciprocalDivideRate,
					item.getValidTo(), syncAdvise);
		}

		return outcome;
	}

	/**
	 * Single-direction find-existing-then-update-or-insert on the natural key, honoring the given
	 * {@link SyncAdvise}. Returns whether the row was created, updated, or left untouched.
	 */
	@NonNull
	private SyncOutcome saveRate(
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId,
			@NonNull final CurrencyId fromCurrencyId,
			@NonNull final CurrencyId toCurrencyId,
			@NonNull final CurrencyConversionTypeId conversionTypeId,
			@NonNull final LocalDate validFrom,
			@NonNull final BigDecimal multiplyRate,
			@NonNull final BigDecimal divideRate,
			@Nullable final LocalDate validTo,
			@NonNull final SyncAdvise syncAdvise)
	{
		I_C_Conversion_Rate record = currencyConversionRepository.findExistingRate(
				clientId, orgId, fromCurrencyId, toCurrencyId, conversionTypeId, validFrom);
		final SyncOutcome outcome;
		if (record == null)
		{
			if (syncAdvise.isFailIfNotExists())
			{
				throw new AdempiereException("@NotFound@ @C_Conversion_Rate@: " + fromCurrencyId.getRepoId() + "->" + toCurrencyId.getRepoId())
						.markAsUserValidationError();
			}
			record = currencyConversionRepository.newRate(orgId, fromCurrencyId, toCurrencyId, conversionTypeId, validFrom);
			outcome = SyncOutcome.CREATED;
		}
		else
		{
			if (!syncAdvise.getIfExists().isUpdate())
			{
				return SyncOutcome.NOTHING_DONE;
			}
			outcome = SyncOutcome.UPDATED;
		}

		record.setMultiplyRate(multiplyRate);
		record.setDivideRate(divideRate);
		record.setValidTo(validTo != null ? TimeUtil.asTimestamp(validTo) : null);

		currencyConversionRepository.save(record);

		return outcome;
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

	/**
	 * {@code 1 / rate} at scale {@value #DIVIDE_RATE_SCALE}, {@link #DIVIDE_RATE_ROUNDING}. Used both to
	 * derive a row's {@code DivideRate} from its {@code MultiplyRate} and to compute the reverse
	 * direction's {@code MultiplyRate} (the reciprocal).
	 */
	@NonNull
	private static BigDecimal reciprocal(@NonNull final BigDecimal rate)
	{
		return BigDecimal.ONE.divide(rate, DIVIDE_RATE_SCALE, DIVIDE_RATE_ROUNDING);
	}

	private static Instant toInstant(@NonNull final LocalDate localDate)
	{
		return localDate.atStartOfDay(ZoneOffset.UTC).toInstant();
	}

	/**
	 * The in-request natural key used to detect whether the caller supplied a given direction.
	 * Mirrors the persistence natural key columns except {@code AD_Client_ID} (the whole batch runs
	 * under one session client).
	 */
	@RequiredArgsConstructor
	@EqualsAndHashCode
	private static final class NaturalKey
	{
		@NonNull private final OrgId orgId;
		@NonNull private final CurrencyId fromCurrencyId;
		@NonNull private final CurrencyId toCurrencyId;
		@NonNull private final CurrencyConversionTypeId conversionTypeId;
		@NonNull private final LocalDate validFrom;
	}
}
