package de.metas.ui.web.window.descriptor.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.model.I_M_FreightCost;
import org.adempiere.pricing.api.IPriceListDAO;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.util.CtxName;
import org.compiere.util.CtxNames;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Util;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.Language;
import de.metas.i18n.NumberTranslatableString;
import de.metas.material.dispo.commons.repository.AvailableToPromiseQuery;
import de.metas.product.model.I_M_Product;
import de.metas.quantity.Quantity;
import de.metas.ui.web.document.filter.sql.SqlParamsCollector;
import de.metas.ui.web.material.adapter.AvailableToPromiseAdapter;
import de.metas.ui.web.material.adapter.AvailableToPromiseResultForWebui;
import de.metas.ui.web.material.adapter.AvailableToPromiseResultForWebui.Group;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFetcher;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * Product lookup.
 *
 * It is searching by product's Value, Name, UPC and bpartner's ProductNo.
 *
 * @author metas-dev <dev@metasfresh.com>
 * @task https://github.com/metasfresh/metasfresh/issues/2484
 */
public class ProductLookupDescriptor implements LookupDescriptor, LookupDataSourceFetcher
{
	private static final String SYSCONFIG_ATP_QUERY_ENABLED = //
			"de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor.ATP.QueryEnabled";

	private static final String SYSCONFIG_ATP_DISPLAY_ONLY_POSITIVE = //
			"de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor.ATP.DisplayOnlyPositive";

	private static final Optional<String> LookupTableName = Optional.of(I_M_Product.Table_Name);
	private static final String CONTEXT_LookupTableName = LookupTableName.get();

	private final CtxName param_C_BPartner_ID;
	private final CtxName param_PricingDate;
	private final CtxName param_AvailableStockDate;

	private static final CtxName param_M_PriceList_ID = CtxNames.ofNameAndDefaultValue("M_PriceList_ID", "-1");
	private static final CtxName param_AD_Org_ID = CtxNames.ofNameAndDefaultValue(WindowConstants.FIELDNAME_AD_Org_ID, "-1");

	private final Set<CtxName> ctxNamesNeededForQuery;

	private final AvailableToPromiseAdapter availableToPromiseAdapter;

	private static final String ATTRIBUTE_ASI = "asi";

	@Builder(builderClassName = "BuilderWithStockInfo", builderMethodName = "builderWithStockInfo")
	private ProductLookupDescriptor(
			@NonNull final String bpartnerParamName,
			@NonNull final String pricingDateParamName,
			@NonNull final String availableStockDateParamName,
			@NonNull final AvailableToPromiseAdapter availableToPromiseAdapter)
	{
		this.param_C_BPartner_ID = CtxNames.ofNameAndDefaultValue(bpartnerParamName, "-1");
		this.param_PricingDate = CtxNames.ofNameAndDefaultValue(pricingDateParamName, "NULL");

		this.param_AvailableStockDate = CtxNames.ofNameAndDefaultValue(availableStockDateParamName, "NULL");
		this.availableToPromiseAdapter = availableToPromiseAdapter;

		this.ctxNamesNeededForQuery = ImmutableSet.of(param_C_BPartner_ID, param_M_PriceList_ID, param_PricingDate, param_AvailableStockDate, param_AD_Org_ID);
	}

	@Builder(builderClassName = "BuilderWithoutStockInfo", builderMethodName = "builderWithoutStockInfo")
	private ProductLookupDescriptor(
			@NonNull final String bpartnerParamName,
			@NonNull final String pricingDateParamName)
	{
		this.param_C_BPartner_ID = CtxNames.ofNameAndDefaultValue(bpartnerParamName, "-1");
		this.param_PricingDate = CtxNames.ofNameAndDefaultValue(pricingDateParamName, "NULL");

		this.param_AvailableStockDate = null;
		this.availableToPromiseAdapter = null;

		this.ctxNamesNeededForQuery = ImmutableSet.of(param_C_BPartner_ID, param_M_PriceList_ID, param_PricingDate, param_AD_Org_ID);
	}

	@Override
	public LookupDataSourceContext.Builder newContextForFetchingById(final Object id)
	{
		return LookupDataSourceContext.builder(CONTEXT_LookupTableName).requiresAD_Language().putFilterById(id);
	}

	@Override
	public LookupValue retrieveLookupValueById(final LookupDataSourceContext evalCtx)
	{
		final int id = evalCtx.getIdToFilterAsInt(-1);
		if (id <= 0)
		{
			throw new IllegalStateException("No ID provided in " + evalCtx);
		}

		throw new UnsupportedOperationException();
	}

	@Override
	public LookupDataSourceContext.Builder newContextForFetchingList()
	{
		return LookupDataSourceContext.builder(CONTEXT_LookupTableName)
				.setRequiredParameters(ctxNamesNeededForQuery)
				.requiresAD_Language();
	}

	@Override
	public LookupValuesList retrieveEntities(final LookupDataSourceContext evalCtx)
	{
		final SqlParamsCollector sqlParams = SqlParamsCollector.newInstance();
		final String sql = buildSql(sqlParams, evalCtx);
		if (sql == null)
		{
			return LookupValuesList.EMPTY;
		}

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams.toList());
			rs = pstmt.executeQuery();

			final Map<Integer, LookupValue> valuesById = new LinkedHashMap<>();
			while (rs.next())
			{
				final LookupValue value = loadLookupValue(rs);
				valuesById.putIfAbsent(value.getIdAsInt(), value);
			}

			final LookupValuesList unexplodedLookupValues = LookupValuesList.fromCollection(valuesById.values());

			final Date stockdateOrNull = getEffectiveStockDateOrNull(evalCtx);
			if (stockdateOrNull == null || availableToPromiseAdapter == null)
			{
				return unexplodedLookupValues;
			}
			return explodeRecordsWithStockQuantities(
					unexplodedLookupValues,
					stockdateOrNull);
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, sql, sqlParams.toList());
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	private Date getEffectiveStockDateOrNull(final LookupDataSourceContext evalCtx)
	{
		if (param_AvailableStockDate == null)
		{
			return null;
		}
		final Date stockdateOrNull = param_AvailableStockDate.getValueAsDate(evalCtx);
		return stockdateOrNull;
	}

	private String buildSql(
			@NonNull final SqlParamsCollector sqlParams,
			@NonNull final LookupDataSourceContext evalCtx)
	{
		//
		// Get language
		final String adLanguage = evalCtx.getAD_Language();
		final boolean isBaseLanguage = Language.isBaseLanguage(adLanguage);
		final String trlAlias = isBaseLanguage ? "p" : "trl";

		//
		// Build the SQL filter
		final StringBuilder sqlWhereClause = new StringBuilder();
		final SqlParamsCollector sqlWhereClauseParams = SqlParamsCollector.newInstance();
		appendFilterByIsActive(sqlWhereClause, sqlWhereClauseParams);
		appendFilterBySearchString(sqlWhereClause, sqlWhereClauseParams, evalCtx, trlAlias);
		appendFilterById(sqlWhereClause, sqlWhereClauseParams, evalCtx);
		appendFilterByBPartner(sqlWhereClause, sqlWhereClauseParams, evalCtx);
		appendFilterByPriceList(sqlWhereClause, sqlWhereClauseParams, evalCtx);
		appendFilterByNotFreightCostProduct(sqlWhereClause, sqlWhereClauseParams, evalCtx);
		appendFilterByOrg(sqlWhereClause, sqlWhereClauseParams, evalCtx);

		//
		// SQL: SELECT ... FROM
		final StringBuilder sql = new StringBuilder("SELECT"
				+ "\n p." + I_M_Product_Lookup_V.COLUMNNAME_M_Product_ID
				+ "\n, p." + I_M_Product_Lookup_V.COLUMNNAME_Value
				+ "\n, " + trlAlias + "." + I_M_Product_Lookup_V.COLUMNNAME_Name
				+ "\n, p." + I_M_Product_Lookup_V.COLUMNNAME_UPC
				+ "\n, p." + I_M_Product_Lookup_V.COLUMNNAME_BPartnerProductNo
				+ "\n, p." + I_M_Product_Lookup_V.COLUMNNAME_BPartnerProductName
				+ "\n FROM " + I_M_Product_Lookup_V.Table_Name + " p ");
		if (!isBaseLanguage)
		{
			sql.append("\n INNER JOIN M_Product_Trl trl ON (trl.M_Product_ID=p.M_Product_ID AND trl.AD_Language=").append(sqlParams.placeholder(adLanguage)).append(")");
		}

		//
		// SQL: WHERE
		sql.append("\n WHERE ").append(sqlWhereClause);
		sqlParams.collect(sqlWhereClauseParams);

		//
		// SQL: ORDER BY
		sql.append("\n ORDER BY ")
				.append(trlAlias + "." + I_M_Product_Lookup_V.COLUMNNAME_Name)
				.append(", p." + I_M_Product_Lookup_V.COLUMNNAME_C_BPartner_ID + " DESC NULLS LAST");

		// SQL: LIMIT and OFFSET
		sql.append("\n LIMIT ").append(sqlParams.placeholder(evalCtx.getLimit(100)));
		sql.append("\n OFFSET ").append(sqlParams.placeholder(evalCtx.getOffset(0)));

		return sql.toString();
	}

	private static StringBuilder appendFilterByIsActive(final StringBuilder sqlWhereClause, final SqlParamsCollector sqlWhereClauseParams)
	{
		return sqlWhereClause.append("\n p.").append(I_M_Product_Lookup_V.COLUMNNAME_IsActive).append("=").append(sqlWhereClauseParams.placeholder(true));
	}

	private static void appendFilterBySearchString(final StringBuilder sqlWhereClause, final SqlParamsCollector sqlWhereClauseParams, final LookupDataSourceContext evalCtx, final String trlAlias)
	{
		final String evalCtxFilter = evalCtx.getFilter();
		if (evalCtxFilter == LookupDataSourceContext.FILTER_Any)
		{
			// no filtering, we are matching everything
			return;
		}
		if (Check.isEmpty(evalCtxFilter, true))
		{
			// same, consider it as no filtering
			return;
		}

		final String sqlFilter = convertFilterToSql(evalCtxFilter);
		sqlWhereClause.append("\n AND (")
				.append(" p." + I_M_Product_Lookup_V.COLUMNNAME_Value + " ILIKE ").append(sqlWhereClauseParams.placeholder(sqlFilter))
				.append(" OR ").append(trlAlias).append("." + I_M_Product_Lookup_V.COLUMNNAME_Name + " ILIKE ").append(sqlWhereClauseParams.placeholder(sqlFilter))
				.append(" OR ").append("p." + I_M_Product_Lookup_V.COLUMNNAME_UPC + " ILIKE ").append(sqlWhereClauseParams.placeholder(sqlFilter))
				.append(" OR ").append("p." + I_M_Product_Lookup_V.COLUMNNAME_BPartnerProductNo + " ILIKE ").append(sqlWhereClauseParams.placeholder(sqlFilter))
				.append(" OR ").append("p." + I_M_Product_Lookup_V.COLUMNNAME_BPartnerProductName + " ILIKE ").append(sqlWhereClauseParams.placeholder(sqlFilter))
				.append(")");
	}

	private static void appendFilterById(final StringBuilder sqlWhereClause, final SqlParamsCollector sqlWhereClauseParams, final LookupDataSourceContext evalCtx)
	{
		final Integer idToFilter = evalCtx.getIdToFilterAsInt(-1);
		if (idToFilter != null && idToFilter > 0)
		{
			sqlWhereClause.append("\n AND p.").append(I_M_Product_Lookup_V.COLUMNNAME_M_Product_ID).append(sqlWhereClauseParams.placeholder(idToFilter));
		}
	}

	private void appendFilterByBPartner(final StringBuilder sqlWhereClause, final SqlParamsCollector sqlWhereClauseParams, final LookupDataSourceContext evalCtx)
	{
		final int bpartnerId = param_C_BPartner_ID.getValueAsInteger(evalCtx);
		if (bpartnerId > 0)
		{
			sqlWhereClause.append("\n AND (p." + I_M_Product_Lookup_V.COLUMNNAME_C_BPartner_ID + "=").append(sqlWhereClauseParams.placeholder(bpartnerId))
					.append(" OR p." + I_M_Product_Lookup_V.COLUMNNAME_C_BPartner_ID + " IS NULL)");
		}
	}

	private void appendFilterByPriceList(
			@NonNull final StringBuilder sqlWhereClause,
			@NonNull final SqlParamsCollector sqlWhereClauseParams,
			@NonNull final LookupDataSourceContext evalCtx)
	{
		final int priceListVersionId = getPriceListVersionId(evalCtx);
		if (priceListVersionId <= 0)
		{
			return;
		}

		sqlWhereClause.append("\n AND EXISTS (")
				.append("SELECT 1 FROM " + I_M_ProductPrice.Table_Name + " pp WHERE pp.M_Product_ID=p." + I_M_Product_Lookup_V.COLUMNNAME_M_Product_ID)
				.append(" AND pp.").append(I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID).append("=").append(sqlWhereClauseParams.placeholder(priceListVersionId))
				.append(")");
	}

	private static void appendFilterByNotFreightCostProduct(final StringBuilder sqlWhereClause, final SqlParamsCollector sqlWhereClauseParams, final LookupDataSourceContext evalCtx)
	{
		final Integer adOrgId = param_AD_Org_ID.getValueAsInteger(evalCtx);

		sqlWhereClause.append("\n AND NOT EXISTS (")
				.append("SELECT 1 FROM " + I_M_FreightCost.Table_Name + " fc WHERE fc.M_Product_ID=p." + I_M_Product_Lookup_V.COLUMNNAME_M_Product_ID)
				.append(" AND fc.AD_Org_ID IN (0, ").append(sqlWhereClauseParams.placeholder(adOrgId)).append(")")
				.append(")");
	}

	private static void appendFilterByOrg(final StringBuilder sqlWhereClause, final SqlParamsCollector sqlWhereClauseParams, final LookupDataSourceContext evalCtx)
	{
		final Integer adOrgId = param_AD_Org_ID.getValueAsInteger(evalCtx);
		sqlWhereClause.append("\n AND p.AD_Org_ID IN (0, ").append(sqlWhereClauseParams.placeholder(adOrgId)).append(")");
	}

	private static final String convertFilterToSql(final String filter)
	{
		String sqlFilter = filter.trim();
		if (sqlFilter.contains("%"))
		{
			return sqlFilter;
		}

		if (!sqlFilter.startsWith("%"))
		{
			sqlFilter = "%" + sqlFilter;
		}
		if (!sqlFilter.endsWith("%"))
		{
			sqlFilter = sqlFilter + "%";
		}

		return sqlFilter;
	}

	private static LookupValue loadLookupValue(final ResultSet rs) throws SQLException
	{
		final int productId = rs.getInt(I_M_Product_Lookup_V.COLUMNNAME_M_Product_ID);
		final String value = rs.getString(I_M_Product_Lookup_V.COLUMNNAME_Value);
		final String name = rs.getString(I_M_Product_Lookup_V.COLUMNNAME_Name);
		final String upc = rs.getString(I_M_Product_Lookup_V.COLUMNNAME_UPC);
		final String bpartnerProductNo = rs.getString(I_M_Product_Lookup_V.COLUMNNAME_BPartnerProductNo);
		// final String bpartnerProductName = rs.getString(I_M_Product_Lookup_V.COLUMNNAME_BPartnerProductName); // not displayed

		final String displayName = Joiner.on("_").skipNulls().join(value, name, upc, bpartnerProductNo);

		return IntegerLookupValue.of(productId, displayName);
	}

	private final int getPriceListVersionId(final LookupDataSourceContext evalCtx)
	{
		final int priceListId = param_M_PriceList_ID.getValueAsInteger(evalCtx);
		if (priceListId <= 0)
		{
			return -1;
		}

		final Date date = getEffectivePricingDate(evalCtx);
		final Boolean processed = null;
		final I_M_PriceList_Version plv = Services.get(IPriceListDAO.class).retrievePriceListVersionOrNull(Env.getCtx(), priceListId, date, processed);
		if (plv == null)
		{
			return -1;
		}

		return plv.getM_PriceList_Version_ID();
	}

	private Date getEffectivePricingDate(@NonNull final LookupDataSourceContext evalCtx)
	{
		final Date date = Util.coalesceSuppliers(
				() -> param_PricingDate.getValueAsDate(evalCtx),
				() -> SystemTime.asDayTimestamp());
		return date;
	}

	@Override
	public boolean isCached()
	{
		return true;
	}

	@Override
	public String getCachePrefix()
	{
		return null; // not relevant
	}

	@Override
	public Optional<String> getLookupTableName()
	{
		return LookupTableName;
	}

	@Override
	public LookupDataSourceFetcher getLookupDataSourceFetcher()
	{
		return this;
	}

	@Override
	public boolean isHighVolume()
	{
		return true;
	}

	@Override
	public LookupSource getLookupSourceType()
	{
		return LookupSource.lookup;
	}

	@Override
	public boolean isNumericKey()
	{
		return true;
	}

	@Override
	public boolean hasParameters()
	{
		return true;
	}

	@Override
	public Set<String> getDependsOnFieldNames()
	{
		return CtxNames.toNames(ctxNamesNeededForQuery);
	}

	@Override
	public Optional<WindowId> getZoomIntoWindowId()
	{
		return Optional.empty();
	}

	private final LookupValuesList explodeRecordsWithStockQuantities(
			@NonNull final LookupValuesList productLookupValues,
			@NonNull final Date dateOrNull)
	{
		if (productLookupValues.isEmpty() || !isAvailableStockQueryActivatedInSysConfig())
		{
			return productLookupValues;
		}

		final AvailableToPromiseResultForWebui availableStock = availableToPromiseAdapter.retrieveAvailableStock(AvailableToPromiseQuery.builder()
				.productIds(productLookupValues.getKeysAsInt())
				.storageAttributesKeys(availableToPromiseAdapter.getPredefinedStorageAttributeKeys())
				.date(dateOrNull)
				.build());
		final List<Group> availableStockGroups = availableStock.getGroups();

		// process the query's result into those explodedProductValues
		return createLookupValuesFromAvailableStockGroups(productLookupValues, availableStockGroups);
	}

	private boolean isAvailableStockQueryActivatedInSysConfig()
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		final int clientId = Env.getAD_Client_ID(Env.getCtx());
		final int orgId = Env.getAD_Org_ID(Env.getCtx());

		final boolean stockQueryActivated = sysConfigBL.getBooleanValue(
				SYSCONFIG_ATP_QUERY_ENABLED,
				false, clientId, orgId);
		return stockQueryActivated;
	}

	private LookupValuesList createLookupValuesFromAvailableStockGroups(
			@NonNull final LookupValuesList initialLookupValues,
			@NonNull final List<Group> availableStockGroups)
	{
		final boolean addOnlyPositiveATP = addOnlyPositiveATP();

		final List<LookupValue> explodedProductValues = new ArrayList<>();
		for (final Group availableStockGroup : availableStockGroups)
		{
			if (addOnlyPositiveATP && availableStockGroup.getQty().signum() <= 0)
			{
				continue;
			}

			final int productId = availableStockGroup.getProductId();
			final LookupValue productLookupValue = initialLookupValues.getById(productId);
			final ITranslatableString displayName = createDisplayName(productLookupValue.getDisplayNameTrl(), availableStockGroup);

			final ImmutableMap<String, Object> attributeMap = availableStockGroup.getLookupAttributesMap();

			final IntegerLookupValue integerLookupValue = IntegerLookupValue.builder()
					.id(productId)
					.displayName(displayName)
					.attribute(ATTRIBUTE_ASI, attributeMap)
					.build();
			explodedProductValues.add(integerLookupValue);
		}

		if (explodedProductValues.isEmpty())
		{
			return initialLookupValues; // fallback
		}

		return LookupValuesList.fromCollection(explodedProductValues);
	}

	private boolean addOnlyPositiveATP()
	{
		final Properties ctx = Env.getCtx();

		return Services.get(ISysConfigBL.class).getBooleanValue(
				SYSCONFIG_ATP_DISPLAY_ONLY_POSITIVE,
				true,
				Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx));
	}

	private ITranslatableString createDisplayName(
			@NonNull final ITranslatableString productDisplayName,
			@NonNull final Group availableStockGroup)
	{
		final Quantity qtyOnHand = availableStockGroup.getQty();
		final ITranslatableString qtyValueStr = NumberTranslatableString.of(qtyOnHand.getQty(), DisplayType.Quantity);

		final ITranslatableString uomSymbolStr = availableStockGroup.getUomSymbolStr();

		final ITranslatableString storageAttributeString = availableStockGroup.getStorageAttributesString();

		final ITranslatableString displayName = ITranslatableString.compose("",
				productDisplayName,
				": ", qtyValueStr, " ", uomSymbolStr,
				" (", storageAttributeString, ")");
		return displayName;
	}

	public static ProductAndAttributes toProductAndAttributes(@NonNull final LookupValue lookupValue)
	{
		final ImmutableAttributeSet attributes = ImmutableAttributeSet.ofValuesIndexByAttributeId(lookupValue.getAttribute(ATTRIBUTE_ASI));

		return ProductAndAttributes.builder()
				.productId(lookupValue.getIdAsInt())
				.attributes(attributes)
				.build();
	}

	@Value
	@Builder
	public static class ProductAndAttributes
	{
		private final int productId;

		@Default
		@NonNull
		private final ImmutableAttributeSet attributes = ImmutableAttributeSet.EMPTY;
	}

	private static interface I_M_Product_Lookup_V
	{
		String Table_Name = "M_Product_Lookup_V";

		String COLUMNNAME_IsActive = "IsActive";
		String COLUMNNAME_M_Product_ID = "M_Product_ID";
		String COLUMNNAME_Value = "Value";
		String COLUMNNAME_Name = "Name";
		String COLUMNNAME_UPC = "UPC";
		String COLUMNNAME_BPartnerProductNo = "BPartnerProductNo";
		String COLUMNNAME_BPartnerProductName = "BPartnerProductName";
		String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";
	}
}
