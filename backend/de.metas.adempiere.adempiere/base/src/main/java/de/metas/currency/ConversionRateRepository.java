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

import com.google.common.annotations.VisibleForTesting;
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
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
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
 * Owns {@code C_Conversion_Rate} persistence so callers work against the domain
 * {@link CurrencyConversionUpsertRequest} rather than the raw model record. Conversion-type resolution is
 * delegated to {@link ICurrencyDAO}, kept behind this repository.
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

	@VisibleForTesting
	public static ConversionRateRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(ConversionRateRepository.class, ConversionRateRepository::new);
	}

	/**
	 * The {@code C_Conversion_Rate} for the request's natural key, or {@code null} — the find half of the upsert.
	 * Returns the mutable model (for {@link #update}) and includes inactive rows, so an inactive match is updated in
	 * place rather than duplicated.
	 */
	@Nullable
	public I_C_Conversion_Rate findExisting(@NonNull final CurrencyConversionUpsertRequest request)
	{
		return getRecord(ConversionRateQuery.of(request.getKey()));
	}

	/**
	 * The single {@code C_Conversion_Rate} matching {@code query} as a {@link CurrencyConversionRate}, or {@code null}.
	 * Rejects an empty query (would match every row).
	 */
	@Nullable
	public CurrencyConversionRate getByQuery(@NonNull final ConversionRateQuery query)
	{
		Check.assume(!ConversionRateQuery.EMPTY.equals(query), "getByQuery requires a non-empty query: {}", query);

		final I_C_Conversion_Rate record = getRecord(query);
		return record != null ? toConversionRate(record) : null;
	}

	/** The {@code C_Conversion_Rate} for the exact natural {@code key} as a {@link CurrencyConversionRate}, or {@code null}. */
	@Nullable
	public CurrencyConversionRate getByKey(@NonNull final ConversionRateKey key)
	{
		return getByQuery(ConversionRateQuery.of(key));
	}

	/**
	 * Deletes the {@code C_Conversion_Rate} rows for the given exact natural {@code keys} (no-op per absent key).
	 * One SELECT+DELETE per key — for bounded sets (its caller is a test cleanup); a large purge would want a set-based delete.
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
	 * The single {@code C_Conversion_Rate} for an exact natural key, shared by {@link #findExisting} (mutable model)
	 * and {@link #getByQuery} (POJO). The full key matches at most one row, so a plain {@code first()} suffices.
	 */
	@Nullable
	private I_C_Conversion_Rate getRecord(@NonNull final ConversionRateQuery query)
	{
		return createQueryBuilder(query)
				.create()
				.first(I_C_Conversion_Rate.class);
	}

	/** Builds the {@code C_Conversion_Rate} query: each non-null {@link ConversionRateQuery} field narrows on its column. */
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
			// ValidFrom is a start-of-day Timestamp in the org's zone; convert through that zone, never the machine default (docs/coding-rules/java-time.md).
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
	 * Inserts a new {@code C_Conversion_Rate} for {@code request} (natural-key columns + payload), returned as a
	 * {@link CurrencyConversionRate}. The insert half of the upsert; pair with {@link #update}.
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
	 * Applies {@code request}'s payload onto {@code existingRecord} and saves it, returned as a
	 * {@link CurrencyConversionRate}. The update half of the upsert; pair with {@link #create}.
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

	/** Writes {@code request}'s payload ({@code MultiplyRate}, {@code DivideRate}, {@code ValidTo}) onto {@code record}. */
	private void applyPayload(
			@NonNull final I_C_Conversion_Rate record,
			@NonNull final CurrencyConversionUpsertRequest request)
	{
		record.setMultiplyRate(request.getMultiplyRate());
		record.setDivideRate(request.getDivideRate());
		record.setValidTo(request.getValidToTimestamp());
	}

	/**
	 * The newest active {@code C_Conversion_Rate} per {@code (from, to, type)}, narrowed by the query's optional
	 * {@code (org, from, to, type)} filters. The newest-per-combo reduction runs DB-side (one row per combo returned,
	 * never the full set — guards against an all-rows load / OOME).
	 * <p>
	 * Raw SQL because the reduction needs PostgreSQL {@code DISTINCT ON}, which {@code IQueryBuilder} cannot express.
	 * Precedent: {@code de.metas.order.stats.purchase_max_price.PurchaseLastMaxPriceProvider#computeNow}.
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

		// DISTINCT ON keys must lead the ORDER BY; the trailing ValidFrom/AD_Org_ID/PK DESC picks newest / most-specific-org / highest-PK per combo.
		sql.append(" ORDER BY cr.C_Currency_ID, cr.C_Currency_ID_To, cr.C_ConversionType_ID,"
				+ " cr.ValidFrom DESC, cr.AD_Org_ID DESC, cr.C_Conversion_Rate_ID DESC");

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
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

	/** Maps one native-result row into a {@link CurrencyConversionRate} (mirrors {@link #toConversionRate(I_C_Conversion_Rate)}). */
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
