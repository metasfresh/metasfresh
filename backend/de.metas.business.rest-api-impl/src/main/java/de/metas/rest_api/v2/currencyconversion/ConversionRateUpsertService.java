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
import de.metas.currency.ConversionRateCreateRequest;
import de.metas.currency.ConversionRateKey;
import de.metas.currency.ConversionRateRepository;
import de.metas.currency.CurrencyConversionRates;
import de.metas.i18n.Language;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.rest_api.utils.v2.JsonErrors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Conversion_Rate;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Upserts normalized currency-conversion rates into {@code C_Conversion_Rate}.
 * <p>
 * Each caller-supplied request item is resolved (by {@link JsonConversionRateConverters}) into a domain
 * {@link ConversionRate} and persisted on its own via the single-direction path. On top of that, both directions
 * are always ensured: for every successfully-resolvable forward item whose reverse {@code (to -> from)} is
 * <b>not</b> present in the same request, the reciprocal ({@code MultiplyRate = 1 / rate}) is auto-written; when
 * the caller supplied the reverse itself, that reverse is honored untouched (never overwritten with a computed
 * reciprocal).
 * <p>
 * Each request item is applied independently: a per-record failure (unknown/inactive currency, unknown
 * conversion-type code, {@code from == to}, non-positive rate, {@code validTo < validFrom}) yields an
 * {@code ERROR} response item and never aborts the batch nor auto-creates a currency.
 * <p>
 * The batch's {@link SyncAdvise} (default {@code CREATE_OR_MERGE}) governs whether an existing row is updated
 * ({@code ifExists}) or a missing row is created ({@code ifNotExists}): a don't-update advise on an existing row
 * yields {@code NOTHING_DONE}; a fail-if-not-exists advise on a missing row yields a per-record error.
 */
@Service
@RequiredArgsConstructor
public class ConversionRateUpsertService
{
	private static final Logger logger = LogManager.getLogger(ConversionRateUpsertService.class);

	@NonNull private final ConversionRateRepository conversionRateRepository;
	@NonNull private final JsonConversionRateConverters jsonConverters;

	@NonNull
	public JsonResponseConversionRateUpsert upsert(
			@NonNull final JsonRequestConversionRateUpsert request,
			@NonNull final Language adLanguage)
	{
		final SyncAdvise syncAdvise = request.getSyncAdvise();

		// First, collect the natural keys the caller explicitly supplied, so that a caller-supplied reverse is
		// honored untouched (never overwritten by a computed reciprocal). Keys are only added for items that
		// resolve cleanly; an unresolvable item simply contributes no key.
		final Set<ConversionRateKey> callerSuppliedKeys = new HashSet<>();
		for (final JsonRequestConversionRateUpsertItem item : request.getRequestItems())
		{
			final ConversionRateKey key = resolveKeyOrNull(item);
			if (key != null)
			{
				callerSuppliedKeys.add(key);
			}
		}

		final JsonResponseConversionRateUpsert.JsonResponseConversionRateUpsertBuilder responseBuilder = JsonResponseConversionRateUpsert.builder();
		for (final JsonRequestConversionRateUpsertItem item : request.getRequestItems())
		{
			responseBuilder.responseItem(upsertItem(item, syncAdvise, adLanguage, callerSuppliedKeys));
		}
		return responseBuilder.build();
	}

	/**
	 * Resolves the natural key of the given request item without side effects, returning {@code null} when it
	 * cannot be resolved (unknown/inactive currency, unknown type code, invalid org). Used only to detect which
	 * reverse directions the caller supplied; the authoritative validation + persistence still happens in
	 * {@link #upsertItem0}.
	 */
	@Nullable
	private ConversionRateKey resolveKeyOrNull(@NonNull final JsonRequestConversionRateUpsertItem item)
	{
		try
		{
			return jsonConverters.fromJson(item).getKey();
		}
		catch (final Exception ex)
		{
			// Best-effort probe: an unresolvable item contributes no caller-supplied key (the authoritative
			// validation + error reporting still happens in upsertItem0). Leave a trace so a wrong-reciprocal
			// investigation is not a diagnostic black hole.
			logger.debug("resolveKeyOrNull: failed to resolve key for item {}; treating as caller-not-supplied", item, ex);
			return null;
		}
	}

	@NonNull
	private JsonResponseConversionRateUpsertItem upsertItem(
			@NonNull final JsonRequestConversionRateUpsertItem item,
			@NonNull final SyncAdvise syncAdvise,
			@NonNull final Language adLanguage,
			@NonNull final Set<ConversionRateKey> callerSuppliedKeys)
	{
		try
		{
			final SyncOutcome outcome = upsertItem0(item, syncAdvise, callerSuppliedKeys);
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
					.error(JsonErrors.ofThrowable(ex, adLanguage.getAD_Language()))
					.build();
		}
	}

	@NonNull
	private SyncOutcome upsertItem0(
			@NonNull final JsonRequestConversionRateUpsertItem item,
			@NonNull final SyncAdvise syncAdvise,
			@NonNull final Set<ConversionRateKey> callerSuppliedKeys)
	{
		final ConversionRateCreateRequest forward = jsonConverters.fromJson(item);

		// Validate the interceptor invariants explicitly so a bad record becomes a friendly per-record error
		// instead of a raw save-path exception.
		validateInvariants(forward);

		final SyncOutcome outcome = saveRate(forward, syncAdvise);

		// Ensure the reverse direction exists. If the caller supplied the reverse itself (same request), honor it
		// untouched; otherwise auto-write the reciprocal — subject to the same advise.
		final ConversionRateKey reverseKey = forward.getReverseKey();
		if (!callerSuppliedKeys.contains(reverseKey))
		{
			final ConversionRateCreateRequest reverse = forward.toBuilder()
					.fromCurrencyId(forward.getToCurrencyId())
					.toCurrencyId(forward.getFromCurrencyId())
					.multiplyRate(CurrencyConversionRates.reciprocal(forward.getMultiplyRate()))
					.build();
			saveRate(reverse, syncAdvise);
		}

		return outcome;
	}

	/**
	 * Single-direction find-existing-then-update-or-insert on the natural key, honoring the batch's
	 * {@link SyncAdvise}. Returns whether the row was created, updated, or left untouched.
	 */
	@NonNull
	private SyncOutcome saveRate(@NonNull final ConversionRateCreateRequest rate, @NonNull final SyncAdvise syncAdvise)
	{
		final I_C_Conversion_Rate existingRecord = conversionRateRepository.findExisting(rate);
		final SyncOutcome outcome;
		if (existingRecord == null)
		{
			if (syncAdvise.isFailIfNotExists())
			{
				throw new AdempiereException("@NotFound@ @C_Conversion_Rate@: "
						+ rate.getFromCurrencyId().getRepoId() + "->" + rate.getToCurrencyId().getRepoId())
						.markAsUserValidationError();
			}
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

		conversionRateRepository.save(existingRecord, rate);

		return outcome;
	}

	private static void validateInvariants(@NonNull final ConversionRateCreateRequest rate)
	{
		if (CurrencyId.equals(rate.getFromCurrencyId(), rate.getToCurrencyId()))
		{
			throw new AdempiereException("@C_Currency_ID@ = @C_Currency_ID@").markAsUserValidationError();
		}
		if (rate.getMultiplyRate().compareTo(BigDecimal.ZERO) <= 0)
		{
			throw new AdempiereException("@MultiplyRate@ <= 0").markAsUserValidationError();
		}
		if (rate.getValidTo() != null && rate.getValidTo().isBefore(rate.getValidFrom()))
		{
			throw new AdempiereException("@ValidTo@ < @ValidFrom@: " + rate.getValidTo() + " < " + rate.getValidFrom())
					.markAsUserValidationError();
		}
	}
}
