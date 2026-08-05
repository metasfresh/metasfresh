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
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_Conversion_Rate;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Owns {@code C_Conversion_Rate} persistence for the currency domain: the {@code IQueryBL} query construction and
 * the {@code I_C_Conversion_Rate} save primitives, kept out of the REST-endpoint services so that a caller works
 * against the domain {@link CurrencyConversionUpsertRequest} rather than the raw model record.
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
	 * <b>raw model</b> so the caller (service) can hand it to {@link #update}, which mutates it in place; deliberately
	 * <b>not</b> {@code addOnlyActiveRecordsFilter} (must find an inactive row to update in place rather than insert a
	 * duplicate). Shares the read with {@link #getByQuery} via {@link #getRecord}.
	 */
	@Nullable
	public I_C_Conversion_Rate findExisting(@NonNull final CurrencyConversionUpsertRequest request)
	{
		return getRecord(ConversionRateQuery.of(request.getKey()));
	}

	/**
	 * The single {@code C_Conversion_Rate} matching the given {@code query}, mapped to a {@link CurrencyConversionRate} POJO,
	 * or {@code null} if no row matches. Rejects an empty query: with no filter it would match every row and
	 * {@code first()} would return an arbitrary one — so callers must narrow on at least one field.
	 */
	@Nullable
	public CurrencyConversionRate getByQuery(@NonNull final ConversionRateQuery query)
	{
		Check.assume(!ConversionRateQuery.EMPTY.equals(query), "getByQuery requires a non-empty query: {}", query);

		final I_C_Conversion_Rate record = getRecord(query);
		return record != null ? toConversionRate(record) : null;
	}

	/** The single {@code C_Conversion_Rate} for the exact natural {@code key}, mapped to a {@link CurrencyConversionRate} POJO, or {@code null}. */
	@Nullable
	public CurrencyConversionRate getByKey(@NonNull final ConversionRateKey key)
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
	 * The single {@code C_Conversion_Rate} for an <b>exact</b> natural key — the one read path shared by
	 * {@link #findExisting} (returns the mutable model) and {@link #getByQuery} (maps to a POJO). Both callers
	 * narrow on the full natural key, so at most one row can match (the client-less natural-key unique index
	 * guarantees a single row per {@code (validFrom, from, to, type, org)}); a plain {@code first()} suffices and
	 * no ORDER BY is needed. Client is not part of the rate's identity (see that same unique index), so this does
	 * not filter on it.
	 */
	@Nullable
	private I_C_Conversion_Rate getRecord(@NonNull final ConversionRateQuery query)
	{
		return createQueryBuilder(query)
				.create()
				.first(I_C_Conversion_Rate.class);
	}

	/**
	 * Builds the {@code C_Conversion_Rate} query for the given {@link ConversionRateQuery}: each non-null field
	 * narrows on its column; a null field is not filtered. The single place the narrowing filters are assembled,
	 * reused by {@link #getRecord} (the read path behind {@code findExisting} / {@code getByQuery}).
	 * Client scope is NOT set here. Runs thread-inherited (in-trx):
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

	/** Maps a raw {@code I_C_Conversion_Rate} model into the typed {@link CurrencyConversionRate} POJO. */
	@NonNull
	private CurrencyConversionRate toConversionRate(@NonNull final I_C_Conversion_Rate record)
	{
		final OrgId orgId = OrgId.ofRepoId(record.getAD_Org_ID());
		final ZoneId orgZoneId = orgDAO.getTimeZone(orgId);
		return CurrencyConversionRate.builder()
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
	 * Inserts a new {@code C_Conversion_Rate} for {@code request}: builds a fresh record with its natural-key
	 * columns set (org / from / to / type / validFrom), applies the payload, saves it, and returns the persisted
	 * row mapped to a {@link CurrencyConversionRate} POJO. The insert half of the caller-owned upsert (pair with
	 * {@link #update}); the caller (service) owns the create-vs-update decision via {@link #findExisting}.
	 * <p>
	 * {@code AD_Client_ID} is intentionally <b>not</b> set here: the PO layer stamps it from the session context on
	 * save, and client is not part of the rate's identity (client-less natural-key unique index).
	 * <p>
	 * The {@code C_Conversion_Rate} interceptor fills the remaining defaults and the {@code ValidTo} far-future
	 * default on save.
	 */
	@NonNull
	public CurrencyConversionRate create(@NonNull final CurrencyConversionUpsertRequest request)
	{
		final ConversionRateKey key = request.getKey();
		final I_C_Conversion_Rate record = InterfaceWrapperHelper.newInstance(I_C_Conversion_Rate.class);
		record.setAD_Org_ID(key.getOrgId().getRepoId());
		record.setC_Currency_ID(key.getFromCurrencyId().getRepoId());
		record.setC_Currency_ID_To(key.getToCurrencyId().getRepoId());
		record.setC_ConversionType_ID(key.getConversionTypeId().getRepoId());
		record.setValidFrom(request.getValidFromTimestamp());

		applyPayload(record, request);
		InterfaceWrapperHelper.save(record);
		return toConversionRate(record);
	}

	/**
	 * Applies the payload of {@code request} onto the given already-existing {@code existingRecord}, saves it, and
	 * returns the persisted row mapped to a {@link CurrencyConversionRate} POJO. The update half of the caller-owned
	 * upsert (pair with {@link #create}); the caller (service) resolves {@code existingRecord} via
	 * {@link #findExisting} and owns the create-vs-update decision.
	 */
	@NonNull
	public CurrencyConversionRate update(
			@NonNull final I_C_Conversion_Rate existingRecord,
			@NonNull final CurrencyConversionUpsertRequest request)
	{
		applyPayload(existingRecord, request);
		InterfaceWrapperHelper.save(existingRecord);
		return toConversionRate(existingRecord);
	}

	/**
	 * Writes the request payload ({@code MultiplyRate}, {@code DivideRate}, {@code ValidTo}) onto {@code record} —
	 * shared by {@link #create} and {@link #update}.
	 */
	private void applyPayload(
			@NonNull final I_C_Conversion_Rate record,
			@NonNull final CurrencyConversionUpsertRequest request)
	{
		record.setMultiplyRate(request.getMultiplyRate());
		record.setDivideRate(request.getDivideRate());
		record.setValidTo(request.getValidToTimestamp());
	}

	/**
	 * The newest active {@code C_Conversion_Rate} row per {@code (from, to, type)} combo, narrowed by the query's
	 * optional {@code (org, from, to, type)} filters and mapped to {@link CurrencyConversionRate} POJOs. The
	 * newest-per-combo reduction is done <b>DB-side</b> so the DB returns exactly one row per combo — never the full
	 * row set (guards against the all-rows load / OOME). Client is <b>intentionally not filtered</b>: it is not part
	 * of the rate's identity (the client-less natural-key unique index guarantees one row per
	 * {@code (validFrom, from, to, type, org)} regardless of client), so no client scope is needed. A {@code null}
	 * {@code orgId} spans all orgs; a non-null one narrows to that org (the optional {@code orgCode} GET filter).
	 * <p>
	 * <b>Raw SQL is used deliberately here.</b> The DB-side newest-per-combo reduction needs PostgreSQL
	 * {@code DISTINCT ON}, which the {@code IQueryBuilder} API cannot express; the {@code DISTINCT ON} keys
	 * {@code (from, to, type)} match the leading {@code ORDER BY} columns, and the trailing
	 * {@code ORDER BY ValidFrom DESC, AD_Org_ID DESC, C_Conversion_Rate_ID DESC} picks the newest / most-specific-org /
	 * highest-PK row per combo — reproducing the former Java {@code ComboKey=(from,to,type)} first-wins reduction
	 * exactly. Precedent for a native-SQL read in this codebase:
	 * {@code de.metas.order.stats.purchase_max_price.PurchaseLastMaxPriceProvider#computeNow}.
	 */
	@NonNull
	public ImmutableList<CurrencyConversionRate> getNewestRatesOrderedByValidFromDesc(@NonNull final ConversionRateQuery query)
	{
		final List<Object> sqlParams = new ArrayList<>();
		final StringBuilder sql = new StringBuilder(
				"SELECT DISTINCT ON (cr.C_Currency_ID, cr.C_Currency_ID_To, cr.C_ConversionType_ID)"
						+ " cr.AD_Org_ID, cr.C_Currency_ID, cr.C_Currency_ID_To, cr.C_ConversionType_ID,"
						+ " cr.ValidFrom, cr.ValidTo, cr.MultiplyRate, cr.DivideRate"
						+ " FROM C_Conversion_Rate cr"
						+ " WHERE cr.IsActive='Y'");

		if (query.getOrgId() != null)
		{
			sql.append(" AND cr.AD_Org_ID = ?");
			sqlParams.add(query.getOrgId().getRepoId());
		}
		if (query.getFromCurrencyId() != null)
		{
			sql.append(" AND cr.C_Currency_ID = ?");
			sqlParams.add(query.getFromCurrencyId().getRepoId());
		}
		if (query.getToCurrencyId() != null)
		{
			sql.append(" AND cr.C_Currency_ID_To = ?");
			sqlParams.add(query.getToCurrencyId().getRepoId());
		}
		if (query.getConversionTypeId() != null)
		{
			sql.append(" AND cr.C_ConversionType_ID = ?");
			sqlParams.add(query.getConversionTypeId().getRepoId());
		}

		// DISTINCT ON keys (from, to, type) MUST be the leading ORDER BY columns; the trailing ValidFrom DESC,
		// AD_Org_ID DESC, PK DESC picks the newest / most-specific-org / highest-PK row per combo.
		sql.append(" ORDER BY cr.C_Currency_ID, cr.C_Currency_ID_To, cr.C_ConversionType_ID,"
				+ " cr.ValidFrom DESC, cr.AD_Org_ID DESC, cr.C_Conversion_Rate_ID DESC");

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			// Out-of-trx (thread-inherited): a GET-path read with no active write transaction (same as getByQuery).
			pstmt = DB.prepareStatement(sql.toString(), ITrx.TRXNAME_ThreadInherited);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();

			final ImmutableList.Builder<CurrencyConversionRate> result = ImmutableList.builder();
			while (rs.next())
			{
				result.add(toConversionRate(rs));
			}
			return result.build();
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, sql.toString(), sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	/**
	 * Maps one row of the {@link #getNewestRatesOrderedByValidFromDesc} native result set into a typed
	 * {@link CurrencyConversionRate}, mirroring {@link #toConversionRate(I_C_Conversion_Rate)}: typed ids via
	 * {@code ofRepoId}, and {@code ValidFrom}/{@code ValidTo} converted through the row's org time zone
	 * ({@code ValidTo} is nullable).
	 */
	@NonNull
	private CurrencyConversionRate toConversionRate(@NonNull final ResultSet rs) throws SQLException
	{
		final OrgId orgId = OrgId.ofRepoId(rs.getInt("AD_Org_ID"));
		final ZoneId orgZoneId = orgDAO.getTimeZone(orgId);
		final Timestamp validTo = rs.getTimestamp("ValidTo");
		return CurrencyConversionRate.builder()
				.orgId(orgId)
				.fromCurrencyId(CurrencyId.ofRepoId(rs.getInt("C_Currency_ID")))
				.toCurrencyId(CurrencyId.ofRepoId(rs.getInt("C_Currency_ID_To")))
				.conversionTypeId(CurrencyConversionTypeId.ofRepoId(rs.getInt("C_ConversionType_ID")))
				.validFrom(TimeUtil.asLocalDate(rs.getTimestamp("ValidFrom"), orgZoneId))
				.validTo(validTo != null ? TimeUtil.asLocalDate(validTo, orgZoneId) : null)
				.multiplyRate(rs.getBigDecimal("MultiplyRate"))
				.divideRate(rs.getBigDecimal("DivideRate"))
				.build();
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
