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
import de.metas.common.util.EmptyUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.material.cockpit.availableforsales.AvailableForSalesConfigRepo;
import de.metas.material.event.commons.AttributesKey;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.ProductId;
import de.metas.product.model.I_M_Product;
import de.metas.quantity.Quantity;
import de.metas.ui.web.document.filter.sql.SqlParamsCollector;
import de.metas.ui.web.material.adapter.AvailabilityInfoResultForWebui.Group;
import de.metas.ui.web.material.adapter.AvailableForSaleAdapter;
import de.metas.ui.web.material.adapter.AvailableToPromiseAdapter;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.sql.productLookup.AFSProductLookupEnricher;
import de.metas.ui.web.window.descriptor.sql.productLookup.ATPProductLookupEnricher;
import de.metas.ui.web.window.model.lookup.IdsToFilter;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFetcher;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.warehouseassignment.ProductWarehouseAssignmentService;
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
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MLookupFactory.LanguageInfo;
import org.compiere.util.CtxName;
import org.compiere.util.CtxNames;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
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
 * It is searching by product's Value, Name, UPC and partner's ProductNo.
 *
 * @author metas-dev <dev@metasfresh.com>
 * task https://github.com/metasfresh/metasfresh/issues/2484
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class ProductLookupDescriptor implements LookupDescriptor, LookupDataSourceFetcher
{
	private final MLookupFactory lookupFactory = MLookupFactory.newInstance();
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private static final String SYSCONFIG_AVAILABILITY_INFO_QUERY_TYPE = //
			"de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor.AvailabilityInfo.QueryType";

	private static final String SYSCONFIG_AVAILABILITY_INFO_QUERY_TYPE_CONSTANT_ATP = "ATP";
	private static final String SYSCONFIG_AVAILABILITY_INFO_QUERY_TYPE_CONSTANT_AFS = "AFS";

	private static final String SYSCONFIG_DISPLAY_AVAILABILITY_INFO_ONLY_IF_POSITIVE = //
			"de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor.AvailabilityInfo.DisplayOnlyPositive";

	private static final String SYSCONFIG_FILTER_OUT_PRODUCTS_BASED_ON_SECTION_CODE = //
			"de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor.FilterProductsBasedOnSectionCode";

	private static final String SYSCONFIG_DisableFullTextSearch = //
			"de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor.DisableFullTextSearch";

	private static final Optional<String> LookupTableName = Optional.of(I_M_Product.Table_Name);
	private static final String CONTEXT_LookupTableName = LookupTableName.get();

	private static final String COLUMNNAME_ProductDisplayName = "ProductDisplayName";

	private final CtxName param_C_BPartner_ID;
	private final CtxName param_PricingDate;
	private final CtxName param_AvailableStockDate;
	@Nullable
	private final CtxName param_M_SectionCode_ID;
	private static final CtxName param_AD_Client_ID = CtxNames.ofNameAndDefaultValue(WindowConstants.FIELDNAME_AD_Client_ID, "-1");

	private static final CtxName param_M_PriceList_ID = CtxNames.ofNameAndDefaultValue("M_PriceList_ID", "-1");
	private static final CtxName param_AD_Org_ID = CtxNames.ofNameAndDefaultValue(WindowConstants.FIELDNAME_AD_Org_ID, "-1");
	private static final CtxName param_M_Warehouse_ID = CtxNames.ofNameAndDefaultValue(WindowConstants.FIELDNAME_M_Warehouse_ID, "-1");
	@Getter
	private final boolean hideDiscontinued;

	private final Set<CtxName> ctxNamesNeededForQuery;

	private final AvailableToPromiseAdapter availableToPromiseAdapter;
	private final AvailableForSaleAdapter availableForSaleAdapter;
	private final AvailableForSalesConfigRepo availableForSalesConfigRepo;
	private final ProductWarehouseAssignmentService productWarehouseAssignmentService;

	private static final String ATTRIBUTE_ASI = "asi";

	private final boolean excludeBOMProducts;

	@Getter
	private final int searchStringMinLength;

	@Builder(builderClassName = "BuilderWithStockInfo", builderMethodName = "builderWithStockInfo")
	private ProductLookupDescriptor(
			@NonNull final String bpartnerParamName,
			@NonNull final String pricingDateParamName,
			@NonNull final String availableStockDateParamName,
			@Nullable final String sectionCodeParamName,
			@NonNull final AvailableToPromiseAdapter availableToPromiseAdapter,
			@NonNull final AvailableForSaleAdapter availableForSaleAdapter,
			@NonNull final AvailableForSalesConfigRepo availableForSalesConfigRepo,
			@Nullable final ProductWarehouseAssignmentService productWarehouseAssignmentService,
			final boolean hideDiscontinued,
			final boolean excludeBOMProducts)
	{
		param_C_BPartner_ID = CtxNames.ofNameAndDefaultValue(bpartnerParamName, "-1");
		param_PricingDate = CtxNames.ofNameAndDefaultValue(pricingDateParamName, "NULL");
		param_M_SectionCode_ID = CtxNames.ofNullableNameAndDefaultValue(sectionCodeParamName, "-1");

		this.hideDiscontinued = hideDiscontinued;

		param_AvailableStockDate = CtxNames.ofNameAndDefaultValue(availableStockDateParamName, "NULL");
		this.availableToPromiseAdapter = availableToPromiseAdapter;
		this.availableForSaleAdapter = availableForSaleAdapter;
		this.availableForSalesConfigRepo = availableForSalesConfigRepo;
		this.productWarehouseAssignmentService = productWarehouseAssignmentService;

		this.excludeBOMProducts = excludeBOMProducts;

		final ImmutableSet.Builder<CtxName> ctxNamesNeededForQuerySetBuilder = ImmutableSet.builder();

		ctxNamesNeededForQuerySetBuilder.add(param_C_BPartner_ID, param_M_PriceList_ID, param_PricingDate, param_AvailableStockDate, param_M_Warehouse_ID, param_AD_Org_ID, param_AD_Client_ID);
		Optional.ofNullable(param_M_SectionCode_ID).ifPresent(ctxNamesNeededForQuerySetBuilder::add);

		ctxNamesNeededForQuery = ctxNamesNeededForQuerySetBuilder.build();

		final IADTableDAO adTablesRepo = Services.get(IADTableDAO.class);
		searchStringMinLength = adTablesRepo.getTypeaheadMinLength(org.compiere.model.I_M_Product.Table_Name);
	}

	@Builder(builderClassName = "BuilderWithoutStockInfo", builderMethodName = "builderWithoutStockInfo")
	private ProductLookupDescriptor(
			@NonNull final String bpartnerParamName,
			@NonNull final String pricingDateParamName,
			@Nullable final ProductWarehouseAssignmentService productWarehouseAssignmentService,
			@Nullable final String sectionCodeParamName,
			final boolean hideDiscontinued,
			final boolean excludeBOMProducts)
	{
		param_C_BPartner_ID = CtxNames.ofNameAndDefaultValue(bpartnerParamName, "-1");
		param_PricingDate = CtxNames.ofNameAndDefaultValue(pricingDateParamName, "NULL");
		param_M_SectionCode_ID = CtxNames.ofNullableNameAndDefaultValue(sectionCodeParamName, "-1");
		this.hideDiscontinued = hideDiscontinued;

		param_AvailableStockDate = null;
		availableToPromiseAdapter = null;
		availableForSaleAdapter = null;
		availableForSalesConfigRepo = null;

		this.excludeBOMProducts = excludeBOMProducts;

		final ImmutableSet.Builder<CtxName> ctxNamesNeededForQuerySetBuilder = ImmutableSet.builder();

		ctxNamesNeededForQuerySetBuilder.add(param_C_BPartner_ID, param_M_PriceList_ID, param_PricingDate, param_AD_Org_ID);
		Optional.ofNullable(param_M_SectionCode_ID).ifPresent(ctxNamesNeededForQuerySetBuilder::add);

		ctxNamesNeededForQuery = ctxNamesNeededForQuerySetBuilder.build();

		final IADTableDAO adTablesRepo = Services.get(IADTableDAO.class);
		searchStringMinLength = adTablesRepo.getTypeaheadMinLength(org.compiere.model.I_M_Product.Table_Name);

		this.productWarehouseAssignmentService = productWarehouseAssignmentService;
	}

	@Override
	public LookupDataSourceContext.Builder newContextForFetchingById(final Object id)
	{
		return LookupDataSourceContext.builder(CONTEXT_LookupTableName)
				.requiresAD_Language()
				.putFilterById(IdsToFilter.ofSingleValue(id));
	}

	@Override
	public LookupValue retrieveLookupValueById(final @NonNull LookupDataSourceContext evalCtx)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public LookupDataSourceContext.Builder newContextForFetchingList()
	{
		return LookupDataSourceContext.builder(CONTEXT_LookupTableName)
				.setRequiredParameters(ctxNamesNeededForQuery)
				.requiresAD_Language();
	}

	private static void appendFilterBySearchString(
			final StringBuilder sqlWhereClause,
			final SqlParamsCollector sqlWhereClauseParams,
			final String filter,
			final boolean fullTextSearchEnabled)
	{
		if (LookupDataSourceContext.FILTER_Any.equals(filter))
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

	private static String convertFilterToSql(final String filter)
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

	@VisibleForTesting
	static LookupValuesList explodeLookupValuesByAvailableStockGroups(
			@NonNull final LookupValuesList initialLookupValues,
			@NonNull final List<Group> availableStockGroups,
			final boolean displayOnlyIfQtyPositive,
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
		final ArrayList<ProductWithAvailabilityInfo> productWithAvailabilityInfos = new ArrayList<>();

		for (final LookupValue productLookupValue : initialLookupValues)
		{
			final ProductId productId = productLookupValue.getIdAs(ProductId::ofRepoId);
			final ImmutableList<Group> groups = groupsByProductId.get(productId);

			productWithAvailabilityInfos.addAll(createProductWithAvailabilityInfos(productLookupValue, groups, displayOnlyIfQtyPositive));
		}

		return productWithAvailabilityInfos.stream()
				.map(productWithAvailabilityInfo -> createProductLookupValue(productWithAvailabilityInfo, adLanguage))
				.collect(LookupValuesList.collect());
	}

	private static List<ProductWithAvailabilityInfo> createProductWithAvailabilityInfos(
			@NonNull final LookupValue productLookupValue,
			@NonNull final ImmutableList<Group> availabilityInfoGroups,
			final boolean displayAvailabilityInfoOnlyIfPositive)
	{
		final Set<ProductWithAvailabilityInfo> result = new LinkedHashSet<>();

		ProductWithAvailabilityInfo productWithAvailabilityInfo_ALL = null;
		ProductWithAvailabilityInfo productWithAvailabilityInfo_OTHERS = null;
		for (final Group availabilityInfoGroup : availabilityInfoGroups)
		{
			final ProductWithAvailabilityInfo productWithAvailabilityInfo = ProductWithAvailabilityInfo.builder()
					.productId(productLookupValue.getIdAs(ProductId::ofRepoId))
					.productDisplayName(productLookupValue.getDisplayNameTrl())
					.qty(availabilityInfoGroup.getQty())
					.attributesType(availabilityInfoGroup.getType())
					.attributes(availabilityInfoGroup.getAttributes())
					.build();

			result.add(productWithAvailabilityInfo);

			if (productWithAvailabilityInfo.getAttributesType() == Group.Type.ALL_STORAGE_KEYS)
			{
				productWithAvailabilityInfo_ALL = productWithAvailabilityInfo;
			}
			else if (productWithAvailabilityInfo.getAttributesType() == Group.Type.OTHER_STORAGE_KEYS)
			{
				productWithAvailabilityInfo_OTHERS = productWithAvailabilityInfo;
			}
		}

		//
		// If OTHERS has the same Qty as ALL, remove OTHERS because it's pointless
		if (productWithAvailabilityInfo_ALL != null
				&& productWithAvailabilityInfo_OTHERS != null
				&& Objects.equals(productWithAvailabilityInfo_OTHERS.getQty(), productWithAvailabilityInfo_ALL.getQty()))
		{
			result.remove(productWithAvailabilityInfo_OTHERS);
		}

		//
		// Remove non-positive quantities if asked
		if (displayAvailabilityInfoOnlyIfPositive)
		{
			result.removeIf(productWithAvailabilityInfo -> productWithAvailabilityInfo.getQty().signum() <= 0);
		}

		//
		// Make sure we have at least one entry for each product
		if (result.isEmpty())
		{
			result.add(ProductWithAvailabilityInfo.builder()
							   .productId(productLookupValue.getIdAs(ProductId::ofRepoId))
							   .productDisplayName(productLookupValue.getDisplayNameTrl())
							   .qty(null)
							   .build());
		}

		return ImmutableList.copyOf(result);
	}

	private static void appendFilterByIsActive(final StringBuilder sqlWhereClause, final SqlParamsCollector sqlWhereClauseParams)
	{
		sqlWhereClause.append("\n p.").append(I_M_Product_Lookup_V.COLUMNNAME_IsActive).append("=").append(sqlWhereClauseParams.placeholder(true));
	}

	private void appendFilterByDiscontinued(@NonNull final StringBuilder sqlWhereClause, @NonNull final SqlParamsCollector sqlWhereClauseParams, @NonNull final LookupDataSourceContext evalCtx)
	{
		final Timestamp priceDate = TimeUtil.asTimestamp(getEffectivePricingDate(evalCtx));

		sqlWhereClause.append("\n AND ")
				//@formatter:off
				.append(" ( ")
					.append(" p.").append(I_M_Product_Lookup_V.COLUMNNAME_Discontinued).append(" = ").append(sqlWhereClauseParams.placeholder(false))
				.append(" OR ")
					.append(" p.").append(I_M_Product_Lookup_V.COLUMNNAME_Discontinued).append(" = ").append(sqlWhereClauseParams.placeholder(true))
					// note: if DiscontinuedFrom='Y' and DiscontinuedFrom is null, then the SQL does not match, which is what we want
				.append(" AND ")
					.append(" p.").append(I_M_Product_Lookup_V.COLUMNNAME_DiscontinuedFrom).append(" > ").append(sqlWhereClauseParams.placeholder(priceDate))
				.append(" ) ");
		//@formatter:on
	}

	private static IntegerLookupValue createProductLookupValue(final ProductWithAvailabilityInfo productWithAvailabilityInfo, final String adLanguage)
	{
		return IntegerLookupValue.builder()
				.id(productWithAvailabilityInfo.getProductId().getRepoId())
				.displayName(createDisplayName(productWithAvailabilityInfo, adLanguage))
				.attribute(ATTRIBUTE_ASI, toValuesByAttributeIdMap(productWithAvailabilityInfo.getAttributes()))
				.build();
	}

	private static void appendFilterById(final StringBuilder sqlWhereClause, final SqlParamsCollector sqlWhereClauseParams, final LookupDataSourceContext evalCtx)
	{
		final Integer idToFilter = evalCtx.getIdToFilterAsInt(-1);
		if (idToFilter != null && idToFilter > 0)
		{
			sqlWhereClause.append("\n AND p.").append(I_M_Product_Lookup_V.COLUMNNAME_M_Product_ID).append("=").append(sqlWhereClauseParams.placeholder(idToFilter));
		}
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

	private static ITranslatableString createDisplayName(final ProductWithAvailabilityInfo productWithAvailabilityInfo, final String adLanguage)
	{
		final ITranslatableString productDisplayName = productWithAvailabilityInfo.getProductDisplayName();
		final Quantity availabilityInfoQty = productWithAvailabilityInfo.getQty();

		//
		// AvailabilityInfo is not available => return only the product display name
		if (availabilityInfoQty == null)
		{
			return productDisplayName;
		}
		//
		// AvailabilityInfo is available:
		else
		{
			final TranslatableStringBuilder builder = TranslatableStrings.builder();

			// Product Name
			builder.append(productDisplayName);

			// ATY Qty:
			builder.append(": ");
			builder.append(availabilityInfoQty.toBigDecimal(), DisplayType.Quantity)
					.append(" ")
					.append(createUomSymbolDisplayString(availabilityInfoQty));

			// Attributes
			final ITranslatableString attributesAsDisplayString = createAttributesDisplayString(
					productWithAvailabilityInfo.getAttributesType(),
					productWithAvailabilityInfo.getAttributes(),
					adLanguage);
			if (!TranslatableStrings.isBlank(attributesAsDisplayString))
			{
				builder.append(" (").append(attributesAsDisplayString).append(")");
			}

			//
			return builder.build();
		}
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

	private void appendFilterByBPartner(final StringBuilder sqlWhereClause, final SqlParamsCollector sqlWhereClauseParams, final LookupDataSourceContext evalCtx)
	{
		final int bpartnerId = param_C_BPartner_ID.getValueAsInteger(evalCtx);
		if (bpartnerId > 0)
		{
			sqlWhereClause.append("\n AND (p." + I_M_Product_Lookup_V.COLUMNNAME_C_BPartner_ID + "=").append(sqlWhereClauseParams.placeholder(bpartnerId))
					.append(" OR p." + I_M_Product_Lookup_V.COLUMNNAME_C_BPartner_ID + " IS NULL)");
		}
	}

	private void appendFilterBySectionCode(
			@NonNull final StringBuilder sqlWhereClause,
			@NonNull final SqlParamsCollector sqlWhereClauseParams,
			@NonNull final LookupDataSourceContext evalCtx)
	{
		if (!isFilterProductsBasedOnSectionCodeEnabled())
		{
			return;
		}

		Optional.ofNullable(param_M_SectionCode_ID)
				.map(param -> param.getValueAsInteger(evalCtx))
				.filter(sectionCodeId -> sectionCodeId > 0)
				.ifPresent(sectionCodeId -> sqlWhereClause
						.append("\n AND p." + I_M_Product_Lookup_V.COLUMNNAME_M_SectionCode_ID + "=")
						.append(sqlWhereClauseParams.placeholder(sectionCodeId)));
	}

	private void appendFilterByWarehouse(
			@NonNull final StringBuilder sqlWhereClause,
			@NonNull final SqlParamsCollector sqlWhereClauseParams,
			@NonNull final LookupDataSourceContext evalCtx)
	{
		if (!isEnforceProductWarehouseAssignment())
		{
			return;
		}

		Optional.ofNullable(WarehouseId.ofRepoIdOrNull(param_M_Warehouse_ID.getValueAsInteger(evalCtx)))
				.ifPresent(warehouseId -> sqlWhereClause
						.append("\n AND p." + I_M_Product_Lookup_V.COLUMNNAME_M_WAREHOUSE_ID + "=")
						.append(sqlWhereClauseParams.placeholder(warehouseId)));
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

	@Override
	public LookupValuesPage retrieveEntities(final LookupDataSourceContext evalCtx)
	{
		if (!isStartSearchForString(evalCtx.getFilter()))
		{
			return LookupValuesPage.EMPTY;
		}

		final int offset = 0;
		final int limit = evalCtx.getLimit(100);
		final SqlParamsCollector sqlParams = SqlParamsCollector.newInstance();
		final String sql = buildSql(sqlParams,
									evalCtx,
									offset,
									limit + 1); // +1 is needed to recognize if we have more results

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams.toList());
			rs = pstmt.executeQuery();

			boolean hasMoreResults = false;
			final LinkedHashMap<Integer, LookupValue> valuesById = new LinkedHashMap<>();
			while (rs.next())
			{
				if (valuesById.size() < limit)
				{
					final LookupValue value = loadLookupValue(rs);
					valuesById.putIfAbsent(value.getIdAsInt(), value);
				}
				else
				{
					hasMoreResults = true;
					break;
				}
			}

			final LookupValuesList unexplodedLookupValues = LookupValuesList.fromCollection(valuesById.values());

			final ZonedDateTime stockDateOrNull = getEffectiveStockDateOrNull(evalCtx);
			if (stockDateOrNull == null || availableToPromiseAdapter == null)
			{
				return LookupValuesPage.ofValuesAndHasMoreFlag(unexplodedLookupValues, hasMoreResults);
			}

			final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(param_C_BPartner_ID.getValueAsInteger(evalCtx));

			final String adLanguage = evalCtx.getAD_Language();
			final WarehouseId warehouseId = WarehouseId.ofRepoIdOrNull(param_M_Warehouse_ID.getValueAsInteger(evalCtx));

			final LookupValuesList explodedLookupValues = explodeRecordsWithStockQuantities(
					unexplodedLookupValues,
					bpartnerId,
					stockDateOrNull,
					warehouseId,
					adLanguage,
					ClientId.ofRepoId(param_AD_Client_ID.getValueAsInteger(evalCtx)),
					OrgId.ofRepoId(param_AD_Org_ID.getValueAsInteger(evalCtx)));

			return LookupValuesPage.ofValuesAndHasMoreFlag(explodedLookupValues, hasMoreResults);
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

		if (EmptyUtil.isBlank(filter))
		{
			return false;
		}

		if (filter.equals(LookupDataSourceContext.FILTER_Any))
		{
			return false;
		}

		return filter.trim().length() >= searchMinLength;
	}

	@Nullable
	private ZonedDateTime getEffectiveStockDateOrNull(final LookupDataSourceContext evalCtx)
	{
		return param_AvailableStockDate != null
				? param_AvailableStockDate.getValueAsZonedDateTime(evalCtx)
				: null;
	}

	@Override
	public boolean isCached()
	{
		return true;
	}

	private String buildSql(
			@NonNull final SqlParamsCollector sqlParams,
			@NonNull final LookupDataSourceContext evalCtx,
			@SuppressWarnings("SameParameterValue") final int offset,
			final int limit)
	{
		//
		// Build the SQL filter
		final StringBuilder sqlWhereClause = new StringBuilder();
		final SqlParamsCollector sqlWhereClauseParams = SqlParamsCollector.newInstance();
		appendFilterByIsActive(sqlWhereClause, sqlWhereClauseParams);
		if (this.isHideDiscontinued())
		{
			appendFilterByDiscontinued(sqlWhereClause, sqlWhereClauseParams, evalCtx);
		}
		appendFilterBySearchString(sqlWhereClause, sqlWhereClauseParams, evalCtx.getFilter(), isFullTextSearchEnabled());
		appendFilterById(sqlWhereClause, sqlWhereClauseParams, evalCtx);
		appendFilterByBPartner(sqlWhereClause, sqlWhereClauseParams, evalCtx);
		appendFilterByPriceList(sqlWhereClause, sqlWhereClauseParams, evalCtx);
		appendFilterByNotFreightCostProduct(sqlWhereClause, sqlWhereClauseParams, evalCtx);
		appendFilterByOrg(sqlWhereClause, sqlWhereClauseParams, evalCtx);
		appendFilterBySectionCode(sqlWhereClause, sqlWhereClauseParams, evalCtx);
		appendFilterBOMProducts(sqlWhereClause, sqlWhereClauseParams);
		appendFilterByWarehouse(sqlWhereClause, sqlWhereClauseParams, evalCtx);

		//
		// SQL: SELECT ... FROM
		final String sqlDisplayName = lookupFactory.getLookup_TableDirEmbed(
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
															+ "\n, p." + I_M_Product_Lookup_V.COLUMNNAME_DiscontinuedFrom
															+ "\n, p." + I_M_Product_Lookup_V.COLUMNNAME_IsBOM
															+ "\n, p." + I_M_Product_Lookup_V.COLUMNNAME_Value
															+ "\n, p." + I_M_Product_Lookup_V.COLUMNNAME_Name
															+ "\n, p." + I_M_Product_Lookup_V.COLUMNNAME_M_SectionCode_ID
															+ "\n, p." + I_M_Product_Lookup_V.COLUMNNAME_M_WAREHOUSE_ID
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
		sql.append("\n LIMIT ").append(sqlParams.placeholder(limit));
		sql.append("\n OFFSET ").append(sqlParams.placeholder(evalCtx.getOffset(offset)));

		return sql.toString();
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

	private void appendFilterBOMProducts(final StringBuilder sqlWhereClause, final SqlParamsCollector sqlWhereClauseParams)
	{
		if (!excludeBOMProducts)
		{
			return;
		}

		sqlWhereClause.append("\n AND p." + I_M_Product_Lookup_V.COLUMNNAME_IsBOM + "=").append(sqlWhereClauseParams.placeholder(false));
	}

	@Nullable
	private PriceListVersionId getPriceListVersionId(final LookupDataSourceContext evalCtx)
	{
		final PriceListId priceListId = PriceListId.ofRepoIdOrNull(param_M_PriceList_ID.getValueAsInteger(evalCtx));
		if (priceListId == null)
		{
			return null;
		}

		final ZonedDateTime date = getEffectivePricingDate(evalCtx);
		return Services.get(IPriceListDAO.class).retrievePriceListVersionIdOrNull(priceListId, date);
	}

	@Nullable
	private ZonedDateTime getEffectivePricingDate(@NonNull final LookupDataSourceContext evalCtx)
	{
		return CoalesceUtil.coalesceSuppliers(
				() -> param_PricingDate.getValueAsZonedDateTime(evalCtx),
				SystemTime::asZonedDateTime);
	}

	@Nullable
	@Override
	public String getCachePrefix()
	{
		return null; // not relevant
	}

	private LookupValuesList explodeRecordsWithStockQuantities(
			@NonNull final LookupValuesList productLookupValues,
			@Nullable final BPartnerId bpartnerId,
			@NonNull final ZonedDateTime dateOrNull,
			@Nullable final WarehouseId warehouseId,
			@NonNull final String adLanguage,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId)
	{
		final boolean afsQueryActivatedInSysConfig = isAFSQueryActivatedInSysConfig();
		final boolean atpQueryActivatedInSysConfig = isATPQueryActivatedInSysConfig();
		if (productLookupValues.isEmpty() || !afsQueryActivatedInSysConfig && !atpQueryActivatedInSysConfig)
		{
			return productLookupValues;
		}
		final List<Group> availableStockGroups;
		if (atpQueryActivatedInSysConfig)
		{
			availableStockGroups = getATPAvailabilityInfoGroups(productLookupValues, bpartnerId, dateOrNull);
		}
		else
		{
			availableStockGroups = getAFSAvailabilityInfoGroups(productLookupValues, dateOrNull, warehouseId, clientId, orgId);
		}

		return explodeLookupValuesByAvailableStockGroups(
				productLookupValues,
				availableStockGroups,
				isDisplayAvailabilityInfoOnlyIfPositive(),
				adLanguage);
	}

	private List<Group> getATPAvailabilityInfoGroups(final @NonNull LookupValuesList productLookupValues,
			final @Nullable BPartnerId bpartnerId,
			final @NonNull ZonedDateTime dateOrNull)
	{
		return ATPProductLookupEnricher.builder()
				.availableToPromiseAdapter(availableToPromiseAdapter)
				.bpartnerId(bpartnerId)
				.dateOrNull(dateOrNull)
				.build()
				.getAvailabilityInfoGroups(productLookupValues);
	}

	private List<Group> getAFSAvailabilityInfoGroups(final @NonNull LookupValuesList productLookupValues,
			final @NonNull ZonedDateTime dateOrNull,
			final @Nullable WarehouseId warehouseId,
			final @NonNull ClientId clientId,
			final @NonNull OrgId orgId)
	{
		return AFSProductLookupEnricher.builder()
				.availableForSaleAdapter(availableForSaleAdapter)
				.availableForSalesConfigRepo(availableForSalesConfigRepo)
				.dateOrNull(dateOrNull)
				.warehouseId(warehouseId)
				.clientId(clientId)
				.orgId(orgId)
				.build()
				.getAvailabilityInfoGroups(productLookupValues);
	}

	private boolean isAFSQueryActivatedInSysConfig()
	{
		return getAvailableStockQueryActivatedInSysConfig().equals(SYSCONFIG_AVAILABILITY_INFO_QUERY_TYPE_CONSTANT_AFS);
	}

	private boolean isATPQueryActivatedInSysConfig()
	{
		return getAvailableStockQueryActivatedInSysConfig().equals(SYSCONFIG_AVAILABILITY_INFO_QUERY_TYPE_CONSTANT_ATP);
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

	private String getAvailableStockQueryActivatedInSysConfig()
	{
		final int clientId = Env.getAD_Client_ID(Env.getCtx());
		final int orgId = Env.getAD_Org_ID(Env.getCtx());

		return sysConfigBL.getValue(
				SYSCONFIG_AVAILABILITY_INFO_QUERY_TYPE,
				"AFS", clientId, orgId);
	}

	private boolean isFilterProductsBasedOnSectionCodeEnabled()
	{
		final int clientId = Env.getAD_Client_ID(Env.getCtx());
		final int orgId = Env.getAD_Org_ID(Env.getCtx());

		return sysConfigBL.getBooleanValue(SYSCONFIG_FILTER_OUT_PRODUCTS_BASED_ON_SECTION_CODE, false, clientId, orgId);
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

	private boolean isDisplayAvailabilityInfoOnlyIfPositive()
	{
		final Properties ctx = Env.getCtx();

		return sysConfigBL.getBooleanValue(
				SYSCONFIG_DISPLAY_AVAILABILITY_INFO_ONLY_IF_POSITIVE,
				true,
				Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx));
	}

	private boolean isEnforceProductWarehouseAssignment()
	{
		return Optional.ofNullable(productWarehouseAssignmentService)
				.map(ProductWarehouseAssignmentService::enforceWarehouseAssignmentsForProducts)
				.orElse(false);
	}
	

	@Value
	@Builder
	private static class ProductWithAvailabilityInfo
	{
		@NonNull
		ProductId productId;
		@NonNull
		ITranslatableString productDisplayName;

		Quantity qty;

		@NonNull
		@Default
		Group.Type attributesType = Group.Type.ALL_STORAGE_KEYS;

		@NonNull
		@Default
		ImmutableAttributeSet attributes = ImmutableAttributeSet.EMPTY;
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
		final boolean disabled = sysConfigBL.getBooleanValue(SYSCONFIG_DisableFullTextSearch, false);
		return !disabled;
	}

	@Value
	@Builder(toBuilder = true)
	public static class ProductAndAttributes
	{
		@NonNull
		ProductId productId;

		@Default
		@NonNull
		ImmutableAttributeSet attributes = ImmutableAttributeSet.EMPTY;
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
		String COLUMNNAME_M_SectionCode_ID = "M_SectionCode_ID";
		String COLUMNNAME_M_WAREHOUSE_ID = "M_Warehouse_ID";
		String COLUMNNAME_Discontinued = "Discontinued";
		String COLUMNNAME_DiscontinuedFrom = "DiscontinuedFrom";

		String COLUMNNAME_IsBOM = "IsBOM";
	}
}
