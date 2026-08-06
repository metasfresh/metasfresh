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
import de.metas.common.rest_api.v2.currencyconversion.JsonResponseConversionRateUpsert.BatchSyncOutcome;
import de.metas.common.rest_api.v2.currencyconversion.JsonResponseConversionRateUpsertItem;
import de.metas.common.rest_api.v2.currencyconversion.JsonResponseConversionRateUpsertItem.SyncOutcome;
import com.google.common.annotations.VisibleForTesting;
import de.metas.currency.CurrencyConversionUpsertRequest;
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
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Conversion_Rate;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Upserts normalized currency-conversion rates into {@code C_Conversion_Rate}. Each request item is resolved (by
 * {@link JsonConversionRateConverters}) into a {@link CurrencyConversionUpsertRequest} and persisted independently —
 * a per-item failure yields an {@code ERROR} item and never aborts the batch nor auto-creates a currency.
 * <p>
 * Both directions are always ensured: for a forward item whose reverse {@code (to -> from)} is not in the same
 * request, the reciprocal is auto-written; a caller-supplied reverse is honored untouched. The batch's
 * {@link SyncAdvise} governs update-if-exists / create-if-missing (don't-update -> {@code NOTHING_DONE};
 * fail-if-not-exists on a missing row -> per-item error).
 */
@Service
@RequiredArgsConstructor
public class ConversionRateUpsertService
{
	private static final Logger logger = LogManager.getLogger(ConversionRateUpsertService.class);

	@NonNull private final ConversionRateRepository conversionRateRepository;
	@NonNull private final JsonConversionRateConverters jsonConverters;

	/** Test-only factory: asserts unit-test mode and wires the collaborators via each bean's own {@code newInstanceForUnitTesting}. */
	@VisibleForTesting
	@NonNull
	public static ConversionRateUpsertService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(ConversionRateUpsertService.class,
				() -> new ConversionRateUpsertService(
						ConversionRateRepository.newInstanceForUnitTesting(),
						JsonConversionRateConverters.newInstanceForUnitTesting()));
	}

	@NonNull
	public JsonResponseConversionRateUpsert upsert(
			@NonNull final JsonRequestConversionRateUpsert request,
			@NonNull final Language adLanguage)
	{
		final SyncAdvise syncAdvise = request.getSyncAdvise();

		// Collect caller-supplied keys first, so a caller-supplied reverse is honored untouched (not overwritten by a computed reciprocal). Unresolvable items contribute no key.
		final Set<ConversionRateKey> callerSuppliedKeys = new HashSet<>();
		for (final JsonRequestConversionRateUpsertItem item : request.getRequestItems())
		{
			final ConversionRateKey key = resolveKeyOrNull(item);
			if (key != null)
			{
				callerSuppliedKeys.add(key);
			}
		}

		final List<JsonResponseConversionRateUpsertItem> responseItems = new ArrayList<>();
		for (final JsonRequestConversionRateUpsertItem item : request.getRequestItems())
		{
			responseItems.add(upsertItem(item, syncAdvise, adLanguage, callerSuppliedKeys));
		}

		return JsonResponseConversionRateUpsert.builder()
				.responseItems(responseItems)
				.syncOutcome(computeAggregate(responseItems))
				.build();
	}

	/** Aggregates per-item outcomes into the top-level {@link BatchSyncOutcome} (an item fails iff its outcome is {@code ERROR}). */
	@NonNull
	private static BatchSyncOutcome computeAggregate(@NonNull final List<JsonResponseConversionRateUpsertItem> responseItems)
	{
		if (responseItems.isEmpty())
		{
			return BatchSyncOutcome.SUCCESS;
		}

		final long failedCount = responseItems.stream()
				.filter(item -> item.getSyncOutcome() == SyncOutcome.ERROR)
				.count();

		if (failedCount == 0)
		{
			return BatchSyncOutcome.SUCCESS;
		}
		else if (failedCount == responseItems.size())
		{
			return BatchSyncOutcome.ERROR;
		}
		else
		{
			return BatchSyncOutcome.PARTIAL_SUCCESS;
		}
	}

	/**
	 * The natural key of the item without side effects, or {@code null} if unresolvable — used only to detect which
	 * reverse directions the caller supplied (authoritative validation happens in {@link #upsertItem0}).
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
			// Best-effort probe; authoritative validation + error reporting happens in upsertItem0. Trace for wrong-reciprocal investigations.
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
		final CurrencyConversionUpsertRequest forward = jsonConverters.fromJson(item);

		// Validate explicitly so a bad record is a friendly per-item error, not a raw save-path exception.
		validateInvariants(forward);

		final SyncOutcome outcome = saveRate(forward, syncAdvise);

		// Ensure the reverse direction: honor a caller-supplied reverse untouched, else auto-write the reciprocal (same advise).
		final ConversionRateKey reverseKey = forward.getReverseKey();
		if (!callerSuppliedKeys.contains(reverseKey))
		{
			final CurrencyConversionUpsertRequest reverse = forward.toBuilder()
					.fromCurrencyId(forward.getToCurrencyId())
					.toCurrencyId(forward.getFromCurrencyId())
					.multiplyRate(CurrencyConversionRates.reciprocal(forward.getMultiplyRate()))
					.build();
			saveRate(reverse, syncAdvise);
		}

		return outcome;
	}

	/**
	 * Single-direction upsert on the natural key, honoring {@link SyncAdvise}: decides create / update /
	 * {@code NOTHING_DONE} / FAIL — the policy stays here in the service; the repo just persists via
	 * {@link ConversionRateRepository#create} / {@link ConversionRateRepository#update}.
	 */
	@NonNull
	private SyncOutcome saveRate(@NonNull final CurrencyConversionUpsertRequest rate, @NonNull final SyncAdvise syncAdvise)
	{
		final I_C_Conversion_Rate existingRecord = conversionRateRepository.findExisting(rate);
		if (existingRecord == null)
		{
			if (syncAdvise.isFailIfNotExists())
			{
				throw new AdempiereException("@NotFound@ @C_Conversion_Rate@: "
						+ rate.getFromCurrencyId().getRepoId() + "->" + rate.getToCurrencyId().getRepoId())
						.markAsUserValidationError();
			}
			conversionRateRepository.create(rate);
			return SyncOutcome.CREATED;
		}

		if (!syncAdvise.getIfExists().isUpdate())
		{
			return SyncOutcome.NOTHING_DONE;
		}
		conversionRateRepository.update(existingRecord, rate);
		return SyncOutcome.UPDATED;
	}

	private static void validateInvariants(@NonNull final CurrencyConversionUpsertRequest rate)
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
