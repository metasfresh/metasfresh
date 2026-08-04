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
import de.metas.common.rest_api.v2.currencyconversion.JsonCurrency;
import de.metas.currency.ConversionRateKey;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_Conversion_Rate;
import org.compiere.model.I_C_Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Thin persistence layer for the currency-conversion REST endpoints: keeps the {@code IQueryBL} query
 * construction and the {@code I_C_Conversion_Rate} save primitives out of the {@code @Service} classes
 * ({@link ConversionRateUpsertService}, {@link NewestConversionRatesService}) and the
 * {@link CurrencyConversionRestController} (persistence primitives belong in a DAO/Repository).
 * <p>
 * Repository Tables: C_Currency, C_Conversion_Rate
 * Repository Cluster: CurrencyConversionRepository (sole owner)
 */
@Repository
public class CurrencyConversionRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final CurrencyRepository currencyRepository;

	@Autowired
	public CurrencyConversionRepository(@NonNull final CurrencyRepository currencyRepository)
	{
		this.currencyRepository = currencyRepository;
	}

	/** No-arg convenience constructor for unit tests (the {@link CurrencyRepository} has no injected state). */
	public CurrencyConversionRepository()
	{
		this(new CurrencyRepository());
	}

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
				.map(CurrencyConversionRepository::toJsonCurrency)
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
	 * Single-direction find on the {@code C_Conversion_Rate} natural key
	 * ({@code AD_Client_ID, AD_Org_ID, C_Currency_ID, C_Currency_ID_To, C_ConversionType_ID, ValidFrom}),
	 * or {@code null} if none exists. The {@code AD_Client_ID} is {@link ClientId#METASFRESH}, matching the
	 * canonical read path's client scoping. {@code ValidFrom} is converted through the rate's org timezone
	 * ({@link ConversionRate#getValidFromTimestamp()}), so lookup and store use the same zone.
	 * <p>
	 * Returns the {@code I_C_Conversion_Rate} model record on purpose: the sole caller
	 * ({@link ConversionRateUpsertService}, same package) mutates + saves it via {@link #save(I_C_Conversion_Rate)}.
	 * The leak is confined to this REST-endpoint package; no other module consumes it.
	 */
	@Nullable
	public I_C_Conversion_Rate findExistingRate(@NonNull final ConversionRate rate)
	{
		final ConversionRateKey key = rate.getKey();
		return queryBL
				.createQueryBuilder(I_C_Conversion_Rate.class)
				.addEqualsFilter(I_C_Conversion_Rate.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH)
				.addEqualsFilter(I_C_Conversion_Rate.COLUMNNAME_AD_Org_ID, key.getOrgId())
				.addEqualsFilter(I_C_Conversion_Rate.COLUMNNAME_C_Currency_ID, key.getFromCurrencyId())
				.addEqualsFilter(I_C_Conversion_Rate.COLUMNNAME_C_Currency_ID_To, key.getToCurrencyId())
				.addEqualsFilter(I_C_Conversion_Rate.COLUMNNAME_C_ConversionType_ID, key.getConversionTypeId())
				.addEqualsFilter(I_C_Conversion_Rate.COLUMNNAME_ValidFrom, rate.getValidFromTimestamp())
				.create()
				.first(I_C_Conversion_Rate.class);
	}

	/** Creates a new, unsaved {@code C_Conversion_Rate} with its natural-key columns (client = {@link ClientId#METASFRESH}) set. */
	@NonNull
	public I_C_Conversion_Rate newRate(@NonNull final ConversionRate rate)
	{
		final ConversionRateKey key = rate.getKey();
		final I_C_Conversion_Rate record = InterfaceWrapperHelper.newInstance(I_C_Conversion_Rate.class);
		InterfaceWrapperHelper.setValue(record, I_C_Conversion_Rate.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH.getRepoId());
		record.setAD_Org_ID(key.getOrgId().getRepoId());
		record.setC_Currency_ID(key.getFromCurrencyId().getRepoId());
		record.setC_Currency_ID_To(key.getToCurrencyId().getRepoId());
		record.setC_ConversionType_ID(key.getConversionTypeId().getRepoId());
		record.setValidFrom(rate.getValidFromTimestamp());
		return record;
	}

	public void save(@NonNull final I_C_Conversion_Rate record)
	{
		InterfaceWrapperHelper.save(record);
	}

	/**
	 * The active {@code C_Conversion_Rate} rows scoped to {@code (SYSTEM, METASFRESH)} client, ordered by
	 * {@code ValidFrom} descending, narrowed by the optional {@code (org, from, to, type)} filters. The
	 * newest-per-combo reduction is done by the caller. The client scope {@code AD_Client_ID IN (SYSTEM, METASFRESH)}
	 * mirrors the canonical read path {@code CurrencyDAO.retrieveRateQuery}. A {@code null} {@code orgId} spans all
	 * orgs; a non-null one narrows to that org (the optional {@code orgCode} GET filter).
	 * <p>
	 * Returns {@code I_C_Conversion_Rate} model rows on purpose: the sole caller
	 * ({@link NewestConversionRatesService}, same package) only reads their columns to build the JSON DTOs.
	 * The leak is confined to this REST-endpoint package; no other module consumes it.
	 */
	@NonNull
	public List<I_C_Conversion_Rate> getConversionRatesOrderedByValidFromDesc(
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
}
