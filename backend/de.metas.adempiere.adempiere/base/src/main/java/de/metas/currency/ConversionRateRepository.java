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

import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_Conversion_Rate;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.List;

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

	/**
	 * Single-direction find on the {@code C_Conversion_Rate} natural key
	 * ({@code AD_Client_ID, AD_Org_ID, C_Currency_ID, C_Currency_ID_To, C_ConversionType_ID, ValidFrom}),
	 * or {@code null} if none exists. The {@code AD_Client_ID} is {@link ClientId#METASFRESH}, matching the
	 * canonical read path's client scoping. {@code ValidFrom} is converted through the request's org timezone
	 * ({@link ConversionRateCreateRequest#getValidFromTimestamp()}), so lookup and store use the same zone.
	 */
	@Nullable
	public I_C_Conversion_Rate findExisting(@NonNull final ConversionRateCreateRequest request)
	{
		final ConversionRateKey key = request.getKey();
		return queryBL
				.createQueryBuilderOutOfTrx(I_C_Conversion_Rate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Conversion_Rate.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH)
				.addEqualsFilter(I_C_Conversion_Rate.COLUMNNAME_AD_Org_ID, key.getOrgId())
				.addEqualsFilter(I_C_Conversion_Rate.COLUMNNAME_C_Currency_ID, key.getFromCurrencyId())
				.addEqualsFilter(I_C_Conversion_Rate.COLUMNNAME_C_Currency_ID_To, key.getToCurrencyId())
				.addEqualsFilter(I_C_Conversion_Rate.COLUMNNAME_C_ConversionType_ID, key.getConversionTypeId())
				.addEqualsFilter(I_C_Conversion_Rate.COLUMNNAME_ValidFrom, request.getValidFromTimestamp())
				.create()
				.first(I_C_Conversion_Rate.class);
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
	 * {@code ValidFrom} descending, narrowed by the optional {@code (org, from, to, type)} filters. The
	 * newest-per-combo reduction is done by the caller. The client scope {@code AD_Client_ID IN (SYSTEM, METASFRESH)}
	 * mirrors the canonical read path {@code CurrencyDAO.retrieveRateQuery}. A {@code null} {@code orgId} spans all
	 * orgs; a non-null one narrows to that org (the optional {@code orgCode} GET filter).
	 */
	@NonNull
	public List<I_C_Conversion_Rate> getNewestRatesOrderedByValidFromDesc(
			@Nullable final OrgId orgId,
			@Nullable final CurrencyId fromCurrencyId,
			@Nullable final CurrencyId toCurrencyId,
			@Nullable final CurrencyConversionTypeId conversionTypeId)
	{
		final IQueryBuilder<I_C_Conversion_Rate> queryBuilder = queryBL
				.createQueryBuilder(I_C_Conversion_Rate.class)
				.addOnlyActiveRecordsFilter()
				// METASFRESH client + SYSTEM, mirroring the runtime rate-lookup client scoping
				.addInArrayFilter(I_C_Conversion_Rate.COLUMNNAME_AD_Client_ID, ClientId.SYSTEM, ClientId.METASFRESH);

		if (orgId != null)
		{
			queryBuilder.addEqualsFilter(I_C_Conversion_Rate.COLUMNNAME_AD_Org_ID, orgId);
		}
		if (fromCurrencyId != null)
		{
			queryBuilder.addEqualsFilter(I_C_Conversion_Rate.COLUMNNAME_C_Currency_ID, fromCurrencyId);
		}
		if (toCurrencyId != null)
		{
			queryBuilder.addEqualsFilter(I_C_Conversion_Rate.COLUMNNAME_C_Currency_ID_To, toCurrencyId);
		}
		if (conversionTypeId != null)
		{
			queryBuilder.addEqualsFilter(I_C_Conversion_Rate.COLUMNNAME_C_ConversionType_ID, conversionTypeId);
		}

		return queryBuilder
				.orderByDescending(I_C_Conversion_Rate.COLUMNNAME_ValidFrom)
				.create()
				.list(I_C_Conversion_Rate.class);
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
