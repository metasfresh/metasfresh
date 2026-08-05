/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.currency;

import com.google.common.collect.ImmutableList;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_Conversion_Rate;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Collection;

/**
 * Owns {@code C_Conversion_Rate} persistence for the currency domain: the {@code IQueryBL} query construction and
 * the {@code I_C_Conversion_Rate} save primitives, kept out of the REST-endpoint services so that a caller works
 * against the domain {@link ConversionRateCreateRequest} rather than the raw model record.
 * <p>
 * Conversion-type resolution is <b>delegated</b> to {@link ICurrencyDAO} (kept an implementation detail behind
 * this repository), so callers use the repository and never {@code ICurrencyDAO} directly.
 * <p>
 * Repository Tables: C_Conversion_Rate
 * Repository Cluster: ConversionRateRepository (sole owner)
 */
@Repository
public class ConversionRateRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	/**
	 * Single-direction find on the {@code C_Conversion_Rate} natural key, or {@code null} if none exists — the
	 * find-then-upsert lookup. Runs <b>in-trx</b> (must see a row written earlier in the same batch) and returns the
	 * <b>raw model</b> because the write path ({@link #save}) mutates it in place; deliberately <b>not</b>
	 * {@code addOnlyActiveRecordsFilter} (must find an inactive row to update in place rather than insert a duplicate).
	 * Shares the read with {@link #getByQuery} via {@link #getRecord}.
	 */
	@Nullable
	public I_C_Conversion_Rate findExisting(@NonNull final ConversionRateCreateRequest request)
	{
		return getRecord(ConversionRateQuery.of(request.getKey()));
	}

	/**
	 * The single {@code C_Conversion_Rate} matching the given {@code query}, mapped to a {@link ConversionRate} POJO,
	 * or {@code null} if no row matches. Rejects an empty query: with no filter it would match every row and
	 * {@code first()} would return an arbitrary one — so callers must narrow on at least one field.
	 */
	@Nullable
	public ConversionRate getByQuery(@NonNull final ConversionRateQuery query)
	{
		Check.assume(!ConversionRateQuery.EMPTY.equals(query), "getByQuery requires a non-empty query: {}", query);

		final I_C_Conversion_Rate record = getRecord(query);
		return record != null ? toConversionRate(record) : null;
	}

	/** The single {@code C_Conversion_Rate} for the exact natural {@code key}, mapped to a {@link ConversionRate} POJO, or {@code null}. */
	@Nullable
	public ConversionRate getByKey(@NonNull final ConversionRateKey key)
	{
		return getByQuery(ConversionRateQuery.of(key));
	}

	/**
	 * Deletes the {@code C_Conversion_Rate} rows for the given exact natural {@code keys} (each a no-op if absent).
	 * Finds-then-deletes per key, reusing {@link #getRecord}'s org-zone ValidFrom conversion — intended for bounded
	 * key sets (its current caller is a test cleanup); a large-set bulk purge would warrant a single set-based delete.
	 */
	public void deleteByKeys(@NonNull final Collection<ConversionRateKey> keys)
	{
		for (final ConversionRateKey key : keys)
		{
			final I_C_Conversion_Rate record = getRecord(ConversionRateQuery.of(key));
			if (record != null)
			{
				InterfaceWrapperHelper.delete(record);
			}
		}
	}

	/**
	 * The single {@code C_Conversion_Rate} matching {@code query} — the one read path shared by
	 * {@link #findExisting} (returns the mutable model) and {@link #getByQuery} (maps to a POJO). Client is
	 * not part of the rate's identity (see the client-less natural-key unique index), so this does not filter on it.
	 * The natural key is not DB-unique, so a deterministic order (newest {@code ValidFrom}, most-specific org, PK
	 * last) keeps the pick stable if duplicates ever exist — mirroring the legacy {@code CurrencyDAO.retrieveRateQuery}.
	 */
	@Nullable
	private I_C_Conversion_Rate getRecord(@NonNull final ConversionRateQuery query)
	{
		return createQueryBuilder(query)
				.orderByDescending(I_C_Conversion_Rate.COLUMNNAME_ValidFrom)
				.orderByDescending(I_C_Conversion_Rate.COLUMNNAME_AD_Org_ID)
				.orderByDescending(I_C_Conversion_Rate.COLUMNNAME_C_Conversion_Rate_ID)
				.create()
				.first(I_C_Conversion_Rate.class);
	}

	/**
	 * Builds the {@code C_Conversion_Rate} query for the given {@link ConversionRateQuery}: each non-null field
	 * narrows on its column; a null field is not filtered. The single place the narrowing filters are assembled,
	 * reused by {@link #getRecord} (the read path behind {@code findExisting} / {@code getByQuery}) and
	 * {@link #getNewestRatesOrderedByValidFromDesc}. Client scope is NOT set here. Runs thread-inherited (in-trx):
	 * {@code findExisting} needs to see rows written earlier in the same batch, and the GET reads run identically on
	 * a stateless request (no active write transaction to join).
	 */
	@NonNull
	private IQueryBuilder<I_C_Conversion_Rate> createQueryBuilder(@NonNull final ConversionRateQuery query)
	{
		final IQueryBuilder<I_C_Conversion_Rate> queryBuilder = queryBL.createQueryBuilder(I_C_Conversion_Rate.class);

		if (query.getOrgId() != null)
		{
			queryBuilder.addEqualsFilter(I_C_Conversion_Rate.COLUMNNAME_AD_Org_ID, query.getOrgId());
		}
		if (query.getFromCurrencyId() != null)
		{
			queryBuilder.addEqualsFilter(I_C_Conversion_Rate.COLUMNNAME_C_Currency_ID, query.getFromCurrencyId());
		}
		if (query.getToCurrencyId() != null)
		{
			queryBuilder.addEqualsFilter(I_C_Conversion_Rate.COLUMNNAME_C_Currency_ID_To, query.getToCurrencyId());
		}
		if (query.getConversionTypeId() != null)
		{
			queryBuilder.addEqualsFilter(I_C_Conversion_Rate.COLUMNNAME_C_ConversionType_ID, query.getConversionTypeId());
		}
		if (query.getValidFrom() != null)
		{
			// ValidFrom is stored as a Timestamp at start-of-day in the org's zone; convert through the same zone.
			// orgId is required to resolve that zone — never fall back to the machine default (docs/coding-rules/java-time.md).
			Check.assumeNotNull(query.getOrgId(), "orgId must be set when validFrom is set (needed to resolve the ValidFrom time zone): {}", query);
			final ZoneId orgZoneId = orgDAO.getTimeZone(query.getOrgId());
			queryBuilder.addEqualsFilter(I_C_Conversion_Rate.COLUMNNAME_ValidFrom, TimeUtil.asTimestamp(query.getValidFrom(), orgZoneId));
		}

		return queryBuilder;
	}

	/** Maps a raw {@code I_C_Conversion_Rate} model into the typed {@link ConversionRate} POJO. */
	@NonNull
	private ConversionRate toConversionRate(@NonNull final I_C_Conversion_Rate record)
	{
		final OrgId orgId = OrgId.ofRepoId(record.getAD_Org_ID());
		final ZoneId orgZoneId = orgDAO.getTimeZone(orgId);
		return ConversionRate.builder()
				.orgId(orgId)
				.fromCurrencyId(CurrencyId.ofRepoId(record.getC_Currency_ID()))
				.toCurrencyId(CurrencyId.ofRepoId(record.getC_Currency_ID_To()))
				.conversionTypeId(CurrencyConversionTypeId.ofRepoId(record.getC_ConversionType_ID()))
				.validFrom(TimeUtil.asLocalDate(record.getValidFrom(), orgZoneId))
				.validTo(record.getValidTo() != null ? TimeUtil.asLocalDate(record.getValidTo(), orgZoneId) : null)
				.multiplyRate(record.getMultiplyRate())
				.divideRate(record.getDivideRate())
				.build();
	}

	/**
	 * Upserts the payload of {@code request} onto the {@code C_Conversion_Rate} row identified by {@code record}:
	 * creates a new natural-key row when {@code record} is {@code null}, otherwise mutates the given one. Returns
	 * the saved record.
	 * <p>
	 * The {@code C_Conversion_Rate} interceptor fills the remaining {@code DivideRate}-free defaults and the
	 * {@code ValidTo} far-future default on save.
	 */
	@NonNull
	public I_C_Conversion_Rate save(
			@Nullable final I_C_Conversion_Rate record,
			@NonNull final ConversionRateCreateRequest request)
	{
		final I_C_Conversion_Rate target = record != null ? record : newRate(request);

		target.setMultiplyRate(request.getMultiplyRate());
		target.setDivideRate(request.getDivideRate());
		target.setValidTo(request.getValidToTimestamp());

		InterfaceWrapperHelper.save(target);
		return target;
	}

	/** Creates a new, unsaved {@code C_Conversion_Rate} with its natural-key columns (client = {@link ClientId#METASFRESH}) set. */
	@NonNull
	private I_C_Conversion_Rate newRate(@NonNull final ConversionRateCreateRequest request)
	{
		final ConversionRateKey key = request.getKey();
		final I_C_Conversion_Rate record = InterfaceWrapperHelper.newInstance(I_C_Conversion_Rate.class);
		InterfaceWrapperHelper.setValue(record, I_C_Conversion_Rate.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH.getRepoId());
		record.setAD_Org_ID(key.getOrgId().getRepoId());
		record.setC_Currency_ID(key.getFromCurrencyId().getRepoId());
		record.setC_Currency_ID_To(key.getToCurrencyId().getRepoId());
		record.setC_ConversionType_ID(key.getConversionTypeId().getRepoId());
		record.setValidFrom(request.getValidFromTimestamp());
		return record;
	}

	/**
	 * The active {@code C_Conversion_Rate} rows scoped to {@code (SYSTEM, METASFRESH)} client, ordered by
	 * {@code ValidFrom} descending, narrowed by the query's optional {@code (org, from, to, type)} filters and
	 * mapped to {@link ConversionRate} POJOs. The newest-per-combo reduction is done by the caller. The client scope
	 * {@code AD_Client_ID IN (SYSTEM, METASFRESH)} mirrors the canonical read path {@code CurrencyDAO.retrieveRateQuery}
	 * (so the query's own {@code clientId} is ignored here on purpose). A {@code null} {@code orgId} spans all orgs;
	 * a non-null one narrows to that org (the optional {@code orgCode} GET filter).
	 */
	@NonNull
	public ImmutableList<ConversionRate> getNewestRatesOrderedByValidFromDesc(@NonNull final ConversionRateQuery query)
	{
		// Reuse the shared narrowing filters (org/from/to/type); ValidFrom is not part of the newest-scan narrowing,
		// so the query carries a null validFrom here. Client scope is the fixed (SYSTEM, METASFRESH) below.
		// Out-of-trx: this is a GET-path read with no active write transaction (same as getByQuery).
		final IQueryBuilder<I_C_Conversion_Rate> queryBuilder = createQueryBuilder(
				query.toBuilder().validFrom(null).build())
				.addOnlyActiveRecordsFilter()
				// METASFRESH client + SYSTEM, mirroring the runtime rate-lookup client scoping
				.addInArrayFilter(I_C_Conversion_Rate.COLUMNNAME_AD_Client_ID, ClientId.SYSTEM, ClientId.METASFRESH);

		return queryBuilder
				.orderByDescending(I_C_Conversion_Rate.COLUMNNAME_ValidFrom)
				.create()
				.stream(I_C_Conversion_Rate.class)
				.map(this::toConversionRate)
				.collect(ImmutableList.toImmutableList());
	}

	/** Resolves the {@code C_ConversionType_ID} for the given method (delegates to {@link ICurrencyDAO}). */
	@NonNull
	public CurrencyConversionTypeId getConversionTypeId(@NonNull final ConversionTypeMethod method)
	{
		return currencyDAO.getConversionTypeId(method);
	}

	/** The org's default conversion type at {@code date} (delegates to {@link ICurrencyDAO}). */
	@NonNull
	public CurrencyConversionTypeId getDefaultConversionTypeId(
			@NonNull final ClientId adClientId,
			@NonNull final OrgId adOrgId,
			@NonNull final Instant date)
	{
		return currencyDAO.getDefaultConversionTypeId(adClientId, adOrgId, date);
	}

	/** The {@link ConversionTypeMethod} of the given conversion-type id (delegates to {@link ICurrencyDAO}). */
	@NonNull
	public ConversionTypeMethod getConversionTypeMethodById(@NonNull final CurrencyConversionTypeId id)
	{
		return currencyDAO.getConversionTypeMethodById(id);
	}
}
