/**
 *
 */
package de.metas.currency.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_ConversionType;
import org.compiere.model.I_C_ConversionType_Default;
import org.compiere.model.I_C_Conversion_Rate;
import org.compiere.model.I_C_Currency;
import org.compiere.util.TimeUtil;

import com.google.common.collect.ImmutableList;

import de.metas.cache.CCache;
import de.metas.currency.ConversionTypeMethod;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyConversionType;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.ICurrencyDAO;
import de.metas.i18n.IModelTranslationMap;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

/**
 * @author metas-dev <dev@metasfresh.com>
 * @deprecated TODO shall be merged into CurrencyRepository
 */
@Deprecated
public class CurrencyDAO implements ICurrencyDAO
{
	private final CCache<Integer, CurrenciesMap> currenciesCache = CCache.<Integer, CurrenciesMap>builder()
			.tableName(I_C_Currency.Table_Name)
			.build();

	private final CCache<Integer, CurrencyConversionTypesMap> conversionTypesCache = CCache.<Integer, CurrencyConversionTypesMap>builder()
			.tableName(I_C_ConversionType.Table_Name)
			.tableName(I_C_ConversionType_Default.Table_Name)
			.build();

	// private final CCache<ConversionTypeMethod, CurrencyConversionTypeId> conversionTypeIdsByType = CCache.<ConversionTypeMethod, CurrencyConversionTypeId> builder()
	// .tableName(I_C_ConversionType.Table_Name)
	// .build();

	@Override
	public Currency getById(@NonNull final CurrencyId currencyId)
	{
		return getCurrenciesMap().getById(currencyId);
	}

	final CurrenciesMap getCurrenciesMap()
	{
		return currenciesCache.getOrLoad(0, this::retrieveCurrenciesMap);
	}

	private CurrenciesMap retrieveCurrenciesMap()
	{
		final ImmutableList<Currency> currencies = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_C_Currency.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(currencyRecord -> toCurrency(currencyRecord))
				.collect(ImmutableList.toImmutableList());

		return new CurrenciesMap(currencies);
	}

	public static final Currency toCurrency(@NonNull final I_C_Currency record)
	{
		final IModelTranslationMap trlMap = InterfaceWrapperHelper.getModelTranslationMap(record);
		return Currency.builder()
				.id(CurrencyId.ofRepoId(record.getC_Currency_ID()))
				.currencyCode(CurrencyCode.ofThreeLetterCode(record.getISO_Code()))
				.symbol(trlMap.getColumnTrl(I_C_Currency.COLUMNNAME_CurSymbol, record.getCurSymbol()))
				.precision(CurrencyPrecision.ofInt(record.getStdPrecision()))
				.costingPrecision(CurrencyPrecision.ofInt(record.getCostingPrecision()))
				.build();
	}

	@Override
	public Currency getByCurrencyCode(@NonNull final CurrencyCode currencyCode)
	{
		return getCurrenciesMap().getByCurrencyCode(currencyCode);
	}

	@Override
	public CurrencyCode getCurrencyCodeById(@NonNull final CurrencyId currencyId)
	{
		return getById(currencyId).getCurrencyCode();
	}

	@Override
	public CurrencyPrecision getStdPrecision(@NonNull final CurrencyId currencyId)
	{
		return getById(currencyId).getPrecision();
	}

	@Override
	public CurrencyPrecision getCostingPrecision(@NonNull final CurrencyId currencyId)
	{
		return getById(currencyId).getCostingPrecision();
	}

	private CurrencyConversionTypesMap getConversionTypesMap()
	{
		return conversionTypesCache.getOrLoad(0, this::retrieveConversionTypesMap);
	}

	private CurrencyConversionTypesMap retrieveConversionTypesMap()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final ImmutableList<CurrencyConversionTypeRouting> routings = queryBL
				.createQueryBuilderOutOfTrx(I_C_ConversionType_Default.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(routingRecord -> toCurrencyConversionTypeRouting(routingRecord))
				.collect(ImmutableList.toImmutableList());

		final ImmutableList<CurrencyConversionType> types = queryBL
				.createQueryBuilderOutOfTrx(I_C_ConversionType.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(record -> toCurrencyConversionType(record))
				.collect(ImmutableList.toImmutableList());

		return CurrencyConversionTypesMap.builder()
				.routings(routings)
				.types(types)
				.build();
	}

	private static CurrencyConversionTypeRouting toCurrencyConversionTypeRouting(final I_C_ConversionType_Default record)
	{
		return CurrencyConversionTypeRouting.builder()
				.clientId(ClientId.ofRepoId(record.getAD_Client_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.validFrom(TimeUtil.asLocalDate(record.getValidFrom()))
				.conversionTypeId(CurrencyConversionTypeId.ofRepoId(record.getC_ConversionType_ID()))
				.build();
	}

	private static CurrencyConversionType toCurrencyConversionType(final I_C_ConversionType record)
	{
		return CurrencyConversionType.builder()
				.id(CurrencyConversionTypeId.ofRepoId(record.getC_ConversionType_ID()))
				.method(ConversionTypeMethod.forCode(record.getValue()))
				.build();
	}

	@Override
	@NonNull
	public CurrencyConversionTypeId getDefaultConversionTypeId(
			@NonNull final ClientId adClientId,
			@NonNull final OrgId adOrgId,
			@NonNull final LocalDate date)
	{
		return getConversionTypesMap()
				.getDefaultConversionType(adClientId, adOrgId, date)
				.getId();
	}

	@Override
	public CurrencyConversionTypeId getConversionTypeId(@NonNull final ConversionTypeMethod method)
	{
		return getConversionTypesMap().getByMethod(method).getId();
	}

	/**
	 * @return query which is finding the best matching {@link I_C_Conversion_Rate} for given parameters.
	 */
	protected final IQueryBuilder<I_C_Conversion_Rate> retrieveRateQuery(
			@NonNull final CurrencyConversionContext conversionCtx,
			@NonNull final CurrencyId currencyFromId,
			@NonNull final CurrencyId currencyToId)
	{
		final CurrencyConversionTypeId conversionTypeId = conversionCtx.getConversionTypeId();
		final LocalDate conversionDate = conversionCtx.getConversionDate();

		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_C_Conversion_Rate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Conversion_Rate.COLUMN_C_Currency_ID, currencyFromId)
				.addEqualsFilter(I_C_Conversion_Rate.COLUMN_C_Currency_ID_To, currencyToId)
				.addEqualsFilter(I_C_Conversion_Rate.COLUMN_C_ConversionType_ID, conversionTypeId)
				.addCompareFilter(I_C_Conversion_Rate.COLUMN_ValidFrom, Operator.LESS_OR_EQUAL, conversionDate)
				.addCompareFilter(I_C_Conversion_Rate.COLUMN_ValidTo, Operator.GREATER_OR_EQUAL, conversionDate)
				.addInArrayOrAllFilter(I_C_Conversion_Rate.COLUMN_AD_Client_ID, ClientId.SYSTEM, conversionCtx.getClientId())
				.addInArrayOrAllFilter(I_C_Conversion_Rate.COLUMN_AD_Org_ID, OrgId.ANY, conversionCtx.getOrgId())
				//
				.orderBy()
				.addColumn(I_C_Conversion_Rate.COLUMN_AD_Client_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_C_Conversion_Rate.COLUMN_AD_Org_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_C_Conversion_Rate.COLUMN_ValidFrom, Direction.Descending, Nulls.Last)
				.endOrderBy()
				//
				.setLimit(QueryLimit.ONE) // first only
				;
	}

	@Override
	public @Nullable BigDecimal retrieveRateOrNull(
			final CurrencyConversionContext conversionCtx,
			final CurrencyId currencyFromId,
			final CurrencyId currencyToId)
	{
		final List<Map<String, Object>> recordsList = retrieveRateQuery(conversionCtx, currencyFromId, currencyToId)
				.setLimit(QueryLimit.ONE)
				.create()
				.listColumns(I_C_Conversion_Rate.COLUMNNAME_MultiplyRate);

		if (recordsList.isEmpty())
		{
			return null;
		}

		final Map<String, Object> record = recordsList.get(0);
		final BigDecimal rate = (BigDecimal)record.get(I_C_Conversion_Rate.COLUMNNAME_MultiplyRate);
		return rate;
	}
}
