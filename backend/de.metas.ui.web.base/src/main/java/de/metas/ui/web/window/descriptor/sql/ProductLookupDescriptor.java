package de.metas.ui.web.window.descriptor.sql;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimaps;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseQuery;
import de.metas.material.dispo.commons.repository.atp.BPartnerClassifier;
import de.metas.material.event.commons.AttributesKey;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.ProductId;
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
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.mm.attributes.api.impl.AttributeSetDescriptionBuilderCommand;
import org.adempiere.model.I_M_FreightCost;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MLookupFactory.LanguageInfo;
import org.compiere.util.CtxName;
import org.compiere.util.CtxNames;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.getModelTranslationMap;

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
 * <p>
 * It is searching by product's Value, Name, UPC and bpartner's ProductNo.
 *
 * @author metas-dev <dev@metasfresh.com>
 * @task https://github.com/metasfresh/metasfresh/issues/2484
 */
public class ProductLookupDescriptor implements LookupDescriptor, LookupDataSourceFetcher
{
	private static final String SYSCONFIG_ATP_QUERY_ENABLED = //
			"de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor.ATP.QueryEnabled";

	private static final String SYSCONFIG_DISPLAY_ATP_ONLY_IF_POSITIVE = //
			"de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor.ATP.DisplayOnlyPositive";

	private static final String SYSCONFIG_DisableFullTextSearch = //
			"de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor.DisableFullTextSearch";

	private static final Optional<String> LookupTableName = Optional.of(I_M_Product.Table_Name);
	private static final String CONTEXT_LookupTableName = LookupTableName.get();

	private static final String COLUMNNAME_ProductDisplayName = "ProductDisplayName";

	private final CtxName param_C_BPartner_ID;
	private final CtxName param_PricingDate;
	private final CtxName param_AvailableStockDate;

	@Getter
	private boolean hideDiscontinued = false;

	private static final CtxName param_M_PriceList_ID = CtxNames.ofNameAndDefaultValue("M_PriceList_ID", "-1");
	private static final CtxName param_AD_Org_ID = CtxNames.ofNameAndDefaultValue(WindowConstants.FIELDNAME_AD_Org_ID, "-1");

	private final Set<CtxName> ctxNamesNeededForQuery;

	private final AvailableToPromiseAdapter availableToPromiseAdapter;

	private static final String ATTRIBUTE_ASI = "asi";

	private final boolean excludeBOMProducts;

	@Getter
	private final int searchStringMinLength;

	@Builder(builderClassName = "BuilderWithStockInfo", builderMethodName = "builderWithStockInfo")
	private ProductLookupDescriptor(
			@NonNull final String bpartnerParamName,
			@NonNull final String pricingDateParamName,
			@NonNull final String availableStockDateParamName,
			@NonNull final AvailableToPromiseAdapter availableToPromiseAdapter,
			boolean hideDiscontinued,
			final boolean excludeBOMProducts)
	{
		param_C_BPartner_ID = CtxNames.ofNameAndDefaultValue(bpartnerParamName, "-1");
		param_PricingDate = CtxNames.ofNameAndDefaultValue(pricingDateParamName, "NULL");

		this.hideDiscontinued = hideDiscontinued;

		param_AvailableStockDate = CtxNames.ofNameAndDefaultValue(availableStockDateParamName, "NULL");
		this.availableToPromiseAdapter = availableToPromiseAdapter;

		this.excludeBOMProducts = excludeBOMProducts;

		ctxNamesNeededForQuery = ImmutableSet.of(param_C_BPartner_ID, param_M_PriceList_ID, param_PricingDate, param_AvailableStockDate, param_AD_Org_ID);

		final IADTableDAO adTablesRepo = Services.get(IADTableDAO.class);
		searchStringMinLength = adTablesRepo.getTypeaheadMinLength(org.compiere.model.I_M_Product.Table_Name);
	}

	@Builder(builderClassName = "BuilderWithoutStockInfo", builderMethodName = "builderWithoutStockInfo")
	private ProductLookupDescriptor(
			@NonNull final String bpartnerParamName,
			@NonNull final String pricingDateParamName,
			boolean hideDiscontinued,
			final boolean excludeBOMProducts)
	{
		param_C_BPartner_ID = CtxNames.ofNameAndDefaultValue(bpartnerParamName, "-1");
		param_PricingDate = CtxNames.ofNameAndDefaultValue(pricingDateParamName, "NULL");
		this.hideDiscontinued = hideDiscontinued;

		param_AvailableStockDate = null;
		availableToPromiseAdapter = null;

		this.excludeBOMProducts = excludeBOMProducts;

		ctxNamesNeededForQuery = ImmutableSet.of(param_C_BPartner_ID, param_M_PriceList_ID, param_PricingDate, param_AD_Org_ID);

		final IADTableDAO adTablesRepo = Services.get(IADTableDAO.class);
		searchStringMinLength = adTablesRepo.getTypeaheadMinLength(org.compiere.model.I_M_Product.Table_Name);
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
		if (!isStartSearchForString(evalCtx.getFilter()))
		{
			return LookupValuesList.EMPTY;
		}

		final SqlParamsCollector sqlParams = SqlParamsCollector.newInstance();
		final String sql = buildSql(sqlParams, evalCtx);

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

			final ZonedDateTime stockdateOrNull = getEffectiveStockDateOrNull(evalCtx);
			if (stockdateOrNull == null || availableToPromiseAdapter == null)
			{
				return unexplodedLookupValues;
			}

			final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(param_C_BPartner_ID.getValueAsInteger(evalCtx));

			final String adLanguage = evalCtx.getAD_Language();

			return explodeRecordsWithStockQuantities(
					unexplodedLookupValues,
					bpartnerId,
					stockdateOrNull,
					adLanguage);
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

	private boolean isStartSearchForString(final String filter)
	{
		final int searchMinLength = getSearchStringMinLength();
		if (searchMinLength <= 0)
		{
			return true;
		}

		if (filter == null || filter.isEmpty())
		{
			return false;
		}

		if (filter == LookupDataSourceContext.FILTER_Any)
		{
			return false;
		}

		return filter.trim().length() >= searchMinLength;
	}

	private ZonedDateTime getEffectiveStockDateOrNull(final LookupDataSourceContext evalCtx)
	{
		return param_AvailableStockDate != null
				? param_AvailableStockDate.getValueAsZonedDateTime(evalCtx)
				: null;
	}

	private String buildSql(
			@NonNull final SqlParamsCollector sqlParams,
			@NonNull final LookupDataSourceContext evalCtx)
	{
		//
		// Build the SQL filter
		final StringBuilder sqlWhereClause = new StringBuilder();
		final SqlParamsCollector sqlWhereClauseParams = SqlParamsCollector.newInstance();
		appendFilterByIsActive(sqlWhereClause, sqlWhereClauseParams);
		if (this.isHideDiscontinued())
		{
			appendFilterByDiscontinued(sqlWhereClause, sqlWhereClauseParams);
		}
		appendFilterBySearchString(sqlWhereClause, sqlWhereClauseParams, evalCtx.getFilter(), isFullTextSearchEnabled());
		appendFilterById(sqlWhereClause, sqlWhereClauseParams, evalCtx);
		appendFilterByBPartner(sqlWhereClause, sqlWhereClauseParams, evalCtx);
		appendFilterByPriceList(sqlWhereClause, sqlWhereClauseParams, evalCtx);
		appendFilterByNotFreightCostProduct(sqlWhereClause, sqlWhereClauseParams, evalCtx);
		appendFilterByOrg(sqlWhereClause, sqlWhereClauseParams, evalCtx);
		appendFilterBOMProducts(sqlWhereClause, sqlWhereClauseParams, evalCtx);

		//
		// SQL: SELECT ... FROM
		final String sqlDisplayName = MLookupFactory.getLookup_TableDirEmbed(
				LanguageInfo.ofSpecificLanguage(evalCtx.getAD_Language()),
				org.compiere.model.I_M_Product.COLUMNNAME_M_Product_ID, // columnName
				null, // baseTable
				"p." + I_M_Product_Lookup_V.COLUMNNAME_M_Product_ID);
		final StringBuilder sql = new StringBuilder("SELECT"
				+ "\n p." + I_M_Product_Lookup_V.COLUMNNAME_M_Product_ID
				+ "\n, (" + sqlDisplayName + ") AS " + COLUMNNAME_ProductDisplayName
				+ "\n, p." + I_M_Product_Lookup_V.COLUMNNAME_UPC
				+ "\n, p." + I_M_Product_Lookup_V.COLUMNNAME_C_BPartner_ID
				+ "\n, p." + I_M_Product_Lookup_V.COLUMNNAME_BPartnerProductNo
				+ "\n, p." + I_M_Product_Lookup_V.COLUMNNAME_BPartnerProductName
				+ "\n, p." + I_M_Product_Lookup_V.COLUMNNAME_AD_Org_ID
				+ "\n, p." + I_M_Product_Lookup_V.COLUMNNAME_IsActive
				+ "\n, p." + I_M_Product_Lookup_V.COLUMNNAME_Discontinued
				+ "\n, p." + I_M_Product_Lookup_V.COLUMNNAME_IsBOM
				+ "\n, p." + I_M_Product_Lookup_V.COLUMNNAME_Value
				+ "\n, p." + I_M_Product_Lookup_V.COLUMNNAME_Name
				+ "\n FROM " + I_M_Product_Lookup_V.Table_Name + " p ");
		sql.insert(0, "SELECT * FROM (").append(") p");

		//
		// SQL: WHERE
		sql.append("\n WHERE ").append(sqlWhereClause);
		sqlParams.collect(sqlWhereClauseParams);

		//
		// SQL: ORDER BY
		sql.append("\n ORDER BY ")
				.append("p." + COLUMNNAME_ProductDisplayName)
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

	private static StringBuilder appendFilterByDiscontinued(final StringBuilder sqlWhereClause, final SqlParamsCollector sqlWhereClauseParams)
	{
		return sqlWhereClause.append("\n AND p.").append(I_M_Product_Lookup_V.COLUMNNAME_Discontinued).append("=").append(sqlWhereClauseParams.placeholder(false));
	}

	private static void appendFilterBySearchString(
			final StringBuilder sqlWhereClause,
			final SqlParamsCollector sqlWhereClauseParams,
			final String filter,
			final boolean fullTextSearchEnabled)
	{
		if (filter == LookupDataSourceContext.FILTER_Any)
		{
			// no filtering, we are matching everything
			return;
		}
		if (Check.isEmpty(filter, true))
		{
			// same, consider it as no filtering
			return;
		}

		final String sqlFilter = convertFilterToSql(filter);

		if (fullTextSearchEnabled)
		{
			sqlWhereClause.append("\n AND (")
					.append(" ").append("p." + COLUMNNAME_ProductDisplayName + " ILIKE ").append(sqlWhereClauseParams.placeholder(sqlFilter))
					.append(" OR ").append("p." + I_M_Product_Lookup_V.COLUMNNAME_UPC + " ILIKE ").append(sqlWhereClauseParams.placeholder(sqlFilter))
					.append(" OR ").append("p." + I_M_Product_Lookup_V.COLUMNNAME_BPartnerProductNo + " ILIKE ").append(sqlWhereClauseParams.placeholder(sqlFilter))
					.append(" OR ").append("p." + I_M_Product_Lookup_V.COLUMNNAME_BPartnerProductName + " ILIKE ").append(sqlWhereClauseParams.placeholder(sqlFilter))
					.append(")");

		}
		else
		{
			sqlWhereClause.append("\n AND (")
					.append(" p." + I_M_Product_Lookup_V.COLUMNNAME_Value + " ILIKE ").append(sqlWhereClauseParams.placeholder(sqlFilter))
					.append(" OR p." + I_M_Product_Lookup_V.COLUMNNAME_Name + " ILIKE ").append(sqlWhereClauseParams.placeholder(sqlFilter))
					.append(")");
		}
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
		final PriceListVersionId priceListVersionId = getPriceListVersionId(evalCtx);
		if (priceListVersionId == null)
		{
			return;
		}

		final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);
		final List<PriceListVersionId> allPriceListVersionIds = priceListsRepo.getPriceListVersionIdsUpToBase(priceListVersionId, getEffectivePricingDate(evalCtx));

		sqlWhereClause.append("\n AND EXISTS (")
				.append("SELECT 1 FROM " + I_M_ProductPrice.Table_Name + " pp WHERE pp.M_Product_ID=p." + I_M_Product_Lookup_V.COLUMNNAME_M_Product_ID)
				.append(" AND pp.").append(I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID).append(" IN ").append(DB.buildSqlList(allPriceListVersionIds, sqlWhereClauseParams::collectAll))
				.append(" AND pp.IsActive=").append(sqlWhereClauseParams.placeholder(true))
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

	private void appendFilterBOMProducts(final StringBuilder sqlWhereClause, final SqlParamsCollector sqlWhereClauseParams, final LookupDataSourceContext evalCtx)
	{
		if (!excludeBOMProducts)
		{
			return;
		}

		sqlWhereClause.append("\n AND p." + I_M_Product_Lookup_V.COLUMNNAME_IsBOM + "=" + sqlWhereClauseParams.placeholder(false));
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

		final String name = rs.getString(COLUMNNAME_ProductDisplayName);
		final String bpartnerProductNo = rs.getString(I_M_Product_Lookup_V.COLUMNNAME_BPartnerProductNo);
		final String displayName = Joiner.on("_").skipNulls().join(name, bpartnerProductNo);

		final boolean active = StringUtils.toBoolean(rs.getString(I_M_Product_Lookup_V.COLUMNNAME_IsActive));

		return IntegerLookupValue.builder()
				.id(productId)
				.displayName(TranslatableStrings.anyLanguage(displayName))
				.active(active)
				.build();
	}

	private final PriceListVersionId getPriceListVersionId(final LookupDataSourceContext evalCtx)
	{
		final PriceListId priceListId = PriceListId.ofRepoIdOrNull(param_M_PriceList_ID.getValueAsInteger(evalCtx));
		if (priceListId == null)
		{
			return null;
		}

		final ZonedDateTime date = getEffectivePricingDate(evalCtx);
		return Services.get(IPriceListDAO.class).retrievePriceListVersionIdOrNull(priceListId, date);
	}

	private ZonedDateTime getEffectivePricingDate(@NonNull final LookupDataSourceContext evalCtx)
	{
		return CoalesceUtil.coalesceSuppliers(
				() -> param_PricingDate.getValueAsZonedDateTime(evalCtx),
				() -> SystemTime.asZonedDateTime());
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
	public void cacheInvalidate()
	{
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
			@Nullable final BPartnerId bpartnerId,
			@NonNull final ZonedDateTime dateOrNull,
			@NonNull final String adLanguage)
	{
		if (productLookupValues.isEmpty() || !isAvailableStockQueryActivatedInSysConfig())
		{
			return productLookupValues;
		}

		final AvailableToPromiseQuery query = AvailableToPromiseQuery.builder()
				.productIds(productLookupValues.getKeysAsInt())
				.storageAttributesKeyPatterns(availableToPromiseAdapter.getPredefinedStorageAttributeKeys())
				.date(dateOrNull)
				.bpartner(BPartnerClassifier.specificOrNone(bpartnerId))
				.build();
		final AvailableToPromiseResultForWebui availableStock = availableToPromiseAdapter.retrieveAvailableStock(query);
		final List<Group> availableStockGroups = availableStock.getGroups();

		return explodeLookupValuesByAvailableStockGroups(
				productLookupValues,
				availableStockGroups,
				isDisplayATPOnlyIfPositive(),
				adLanguage);
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

	@VisibleForTesting
	static LookupValuesList explodeLookupValuesByAvailableStockGroups(
			@NonNull final LookupValuesList initialLookupValues,
			@NonNull final List<Group> availableStockGroups,
			final boolean displayATPOnlyIfPositive,
			@NonNull final String adLanguage)
	{
		if (initialLookupValues.isEmpty())
		{
			return initialLookupValues;
		}
		if (availableStockGroups.isEmpty())
		{
			return initialLookupValues;
		}

		final ImmutableListMultimap<ProductId, Group> groupsByProductId = Multimaps.index(availableStockGroups, Group::getProductId);
		final ArrayList<ProductWithATP> productWithATPs = new ArrayList<>();

		for (final LookupValue productLookupValue : initialLookupValues)
		{
			final ProductId productId = productLookupValue.getIdAs(ProductId::ofRepoId);
			final ImmutableList<Group> groups = groupsByProductId.get(productId);

			productWithATPs.addAll(createProductWithATPs(productLookupValue, groups, displayATPOnlyIfPositive));
		}

		return productWithATPs.stream()
				.map(productWithATP -> createProductLookupValue(productWithATP, adLanguage))
				.collect(LookupValuesList.collect());
	}

	private static List<ProductWithATP> createProductWithATPs(
			@NonNull final LookupValue productLookupValue,
			@NonNull final ImmutableList<Group> atpGroups,
			final boolean displayATPOnlyIfPositive)
	{
		final ArrayList<ProductWithATP> result = new ArrayList<>();

		ProductWithATP productWithATP_ALL = null;
		ProductWithATP productWithATP_OTHERS = null;
		for (final Group atpGroup : atpGroups)
		{
			final ProductWithATP productWithATP = ProductWithATP.builder()
					.productId(productLookupValue.getIdAs(ProductId::ofRepoId))
					.productDisplayName(productLookupValue.getDisplayNameTrl())
					// .displayQtyAndAttributes(displayATP)
					.qtyATP(atpGroup.getQty())
					.attributesType(atpGroup.getType())
					.attributes(atpGroup.getAttributes())
					.build();

			result.add(productWithATP);

			if (productWithATP.getAttributesType() == Group.Type.ALL_STORAGE_KEYS)
			{
				productWithATP_ALL = productWithATP;
			}
			else if (productWithATP.getAttributesType() == Group.Type.OTHER_STORAGE_KEYS)
			{
				productWithATP_OTHERS = productWithATP;
			}
		}

		//
		// If OTHERS has the same Qty as ALL, remove OTHERS because it's pointless
		if (productWithATP_ALL != null
				&& productWithATP_OTHERS != null
				&& Objects.equals(productWithATP_OTHERS.getQtyATP(), productWithATP_ALL.getQtyATP()))
		{
			result.remove(productWithATP_OTHERS);
			productWithATP_OTHERS = null;
		}

		//
		// Remove non-positive quantities if asked
		if (displayATPOnlyIfPositive)
		{
			result.removeIf(productWithATP -> productWithATP.getQtyATP().signum() <= 0);
		}

		//
		// Make sure we have at least one entry for each product
		if (result.isEmpty())
		{
			result.add(ProductWithATP.builder()
					.productId(productLookupValue.getIdAs(ProductId::ofRepoId))
					.productDisplayName(productLookupValue.getDisplayNameTrl())
					.qtyATP(null)
					.build());
		}

		return result;
	}

	private static IntegerLookupValue createProductLookupValue(final ProductWithATP productWithATP, final String adLanguage)
	{
		return IntegerLookupValue.builder()
				.id(productWithATP.getProductId().getRepoId())
				.displayName(createDisplayName(productWithATP, adLanguage))
				.attribute(ATTRIBUTE_ASI, toValuesByAttributeIdMap(productWithATP.getAttributes()))
				.build();
	}

	private static Map<String, Object> toValuesByAttributeIdMap(final ImmutableAttributeSet attributes)
	{
		final ImmutableMap.Builder<String, Object> attributeMapBuilder = ImmutableMap.builder();
		for (final AttributeId attributeId : attributes.getAttributeIds())
		{
			final String attributeIdStr = Integer.toString(attributeId.getRepoId());
			final Object value = attributes.getValue(attributeId);
			attributeMapBuilder.put(attributeIdStr, value);
		}
		return attributeMapBuilder.build();

	}

	private static ITranslatableString createDisplayName(final ProductWithATP productWithATP, final String adLanguage)
	{
		final ITranslatableString productDisplayName = productWithATP.getProductDisplayName();
		final Quantity qtyATP = productWithATP.getQtyATP();

		//
		// ATP is not available => return only the product display name
		if (qtyATP == null)
		{
			return productDisplayName;
		}
		//
		// ATP is available:
		else
		{
			final TranslatableStringBuilder builder = TranslatableStrings.builder();

			// Product Name
			builder.append(productDisplayName);

			// ATY Qty:
			builder.append(": ");
			builder.append(qtyATP.toBigDecimal(), DisplayType.Quantity)
					.append(" ")
					.append(createUomSymbolDisplayString(qtyATP));

			// Attributes
			final ITranslatableString attributesAsDisplayString = createAttributesDisplayString(
					productWithATP.getAttributesType(),
					productWithATP.getAttributes(),
					adLanguage);
			if (!TranslatableStrings.isBlank(attributesAsDisplayString))
			{
				builder.append(" (").append(attributesAsDisplayString).append(")");
			}

			//
			return builder.build();
		}
	}

	private static ITranslatableString createAttributesDisplayString(
			final Group.Type type,
			final ImmutableAttributeSet attributes,
			final String adLanguage)
	{
		if (type == Group.Type.ALL_STORAGE_KEYS)
		{
			final IMsgBL msgBL = Services.get(IMsgBL.class);
			return msgBL.getTranslatableMsgText(AttributesKey.MSG_ATTRIBUTES_KEY_ALL);
		}
		else if (type == Group.Type.OTHER_STORAGE_KEYS)
		{
			final IMsgBL msgBL = Services.get(IMsgBL.class);
			return msgBL.getTranslatableMsgText(AttributesKey.MSG_ATTRIBUTES_KEY_OTHER);
		}
		else // if (type == Group.Type.ATTRIBUTE_SET)
		{
			return new AttributeSetDescriptionBuilderCommand(attributes, adLanguage).execute();
		}
	}

	private static ITranslatableString createUomSymbolDisplayString(final Quantity qty)
	{
		final I_C_UOM uom = qty.getUOM();
		return getModelTranslationMap(uom)
				.getColumnTrl(I_C_UOM.COLUMNNAME_UOMSymbol, uom.getUOMSymbol());
	}

	@Value
	@Builder
	private static class ProductWithATP
	{
		@NonNull
		ProductId productId;
		@NonNull
		ITranslatableString productDisplayName;

		Quantity qtyATP;

		@NonNull
		@Default
		private Group.Type attributesType = Group.Type.ALL_STORAGE_KEYS;

		@NonNull
		@Default
		ImmutableAttributeSet attributes = ImmutableAttributeSet.EMPTY;
	}

	private boolean isDisplayATPOnlyIfPositive()
	{
		final Properties ctx = Env.getCtx();

		return Services.get(ISysConfigBL.class).getBooleanValue(
				SYSCONFIG_DISPLAY_ATP_ONLY_IF_POSITIVE,
				true,
				Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx));
	}

	public static ProductAndAttributes toProductAndAttributes(@NonNull final LookupValue lookupValue)
	{
		final ProductId productId = lookupValue.getIdAs(ProductId::ofRepoId);

		final Map<Object, Object> valuesByAttributeIdMap = lookupValue.getAttribute(ATTRIBUTE_ASI);
		final ImmutableAttributeSet attributes = ImmutableAttributeSet.ofValuesByAttributeIdMap(valuesByAttributeIdMap);

		return ProductAndAttributes.builder()
				.productId(productId)
				.attributes(attributes)
				.build();
	}

	private boolean isFullTextSearchEnabled()
	{
		final boolean disabled = Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_DisableFullTextSearch, false);
		return !disabled;
	}

	@Value
	@Builder(toBuilder = true)
	public static class ProductAndAttributes
	{
		@NonNull
		private final ProductId productId;

		@Default
		@NonNull
		private final ImmutableAttributeSet attributes = ImmutableAttributeSet.EMPTY;

		public ProductAndAttributes withProductId(@NonNull final ProductId productId)
		{
			return toBuilder().productId(productId).build();
		}
	}

	private interface I_M_Product_Lookup_V
	{
		String Table_Name = "M_Product_Lookup_V";

		String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
		String COLUMNNAME_IsActive = "IsActive";
		String COLUMNNAME_M_Product_ID = "M_Product_ID";
		String COLUMNNAME_Value = "Value";
		String COLUMNNAME_Name = "Name";
		String COLUMNNAME_UPC = "UPC";
		String COLUMNNAME_BPartnerProductNo = "BPartnerProductNo";
		String COLUMNNAME_BPartnerProductName = "BPartnerProductName";
		String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";
		String COLUMNNAME_Discontinued = "Discontinued";

		String COLUMNNAME_IsBOM = "IsBOM";
	}
}
