/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.material.planning.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.maturing.MaturingConfigId;
import de.metas.material.maturing.MaturingConfigLineId;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.ddorder.DistributionNetworkId;
import de.metas.material.planning.exception.NoPlantForWarehouseException;
import de.metas.material.planning.pporder.PPRoutingId;
import de.metas.organization.OrgId;
import de.metas.product.OnMaterialReceiptWithDestWarehouse;
import de.metas.product.ProductId;
import de.metas.product.ProductPlanningSchemaId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.keys.AttributesKeyPatternsUtil;
import org.adempiere.mm.attributes.keys.AttributesKeyQueryHelper;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.eevolution.api.ProductBOMVersionsId;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.model.X_PP_Product_Planning;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class ProductPlanningDAO implements IProductPlanningDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public ProductPlanning getById(@NonNull final ProductPlanningId id)
	{
		return fromRecord(loadOutOfTrx(id, I_PP_Product_Planning.class));
	}

	@Override
	public void deleteById(@NonNull final ProductPlanningId id)
	{
		queryBL.createQueryBuilder(I_PP_Product_Planning.class)
				.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_PP_Product_Planning_ID, id)
				.create()
				.delete();
	}

	public static ProductPlanning fromRecord(@NonNull final I_PP_Product_Planning record)
	{
		return ProductPlanning.builder()
				.disallowSaving(false)
				.id(ProductPlanningId.ofRepoIdOrNull(record.getPP_Product_Planning_ID())) // accept not saved records
				.productPlanningSchemaId(ProductPlanningSchemaId.ofRepoIdOrNull(record.getM_Product_PlanningSchema_ID()))
				.productId(ProductId.ofRepoIdOrNull(record.getM_Product_ID()))
				.warehouseId(WarehouseId.ofRepoIdOrNull(record.getM_Warehouse_ID()))
				.orgId(OrgId.ofRepoIdOrAny(record.getAD_Org_ID()))
				.plantId(ResourceId.ofRepoIdOrNull(record.getS_Resource_ID()))
				.workflowId(PPRoutingId.ofRepoIdOrNull(record.getAD_Workflow_ID()))
				.isAttributeDependant(record.isAttributeDependant())
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNone(record.getM_AttributeSetInstance_ID()))
				.storageAttributesKey(StringUtils.trimBlankToNull(record.getStorageAttributesKey()))
				.plannerId(UserId.ofRepoIdOrNull(record.getPlanner_ID()))
				.isCreatePlan(record.isCreatePlan())
				.bomVersionsId(ProductBOMVersionsId.ofRepoIdOrNull(record.getPP_Product_BOMVersions_ID()))
				.isPickingOrder(record.isPickingOrder())
				.isMatured(record.isMatured())
				.maturingConfigId(MaturingConfigId.ofRepoIdOrNull(record.getM_Maturing_Configuration_ID()))
				.maturingConfigLineId(MaturingConfigLineId.ofRepoIdOrNull(record.getM_Maturing_Configuration_Line_ID()))
				.isPickDirectlyIfFeasible(record.isPickDirectlyIfFeasible())
				.isDocComplete(record.isDocComplete())
				.seqNo(record.getSeqNo())
				.transferTimeDays(record.getTransfertTime().intValueExact())
				.leadTimeDays(record.getDeliveryTime_Promised().intValueExact())
				.isManufactured(StringUtils.toBoolean(record.getIsManufactured()))
				.isPurchased(StringUtils.toBoolean(record.getIsPurchased()))
				.maxManufacturedQtyPerOrderDispo(extractMaxManufacturedQtyPerOrderDispo(record))
				.distributionNetworkId(DistributionNetworkId.ofRepoIdOrNull(record.getDD_NetworkDistribution_ID()))
				.onMaterialReceiptWithDestWarehouse(OnMaterialReceiptWithDestWarehouse.ofNullableCode(record.getOnMaterialReceiptWithDestWarehouse()))
				.build();
	}

	private static void updateRecord(@NonNull final I_PP_Product_Planning record, @NonNull final ProductPlanning from)
	{
		record.setM_Product_PlanningSchema_ID(ProductPlanningSchemaId.toRepoId(from.getProductPlanningSchemaId()));
		record.setM_Product_ID(ProductId.toRepoId(from.getProductId()));
		record.setM_Warehouse_ID(WarehouseId.toRepoId(from.getWarehouseId()));
		record.setAD_Org_ID(from.getOrgId().getRepoId());
		record.setS_Resource_ID(ResourceId.toRepoId(from.getPlantId()));
		record.setAD_Workflow_ID(PPRoutingId.toRepoId(from.getWorkflowId()));
		record.setIsAttributeDependant(from.isAttributeDependant());
		record.setM_AttributeSetInstance_ID(from.getAttributeSetInstanceId().getRepoId());
		record.setStorageAttributesKey(from.getStorageAttributesKey());
		record.setPlanner_ID(UserId.toRepoId(from.getPlannerId()));
		record.setIsCreatePlan(from.isCreatePlan());
		record.setPP_Product_BOMVersions_ID(ProductBOMVersionsId.toRepoId(from.getBomVersionsId()));
		record.setIsPickingOrder(from.isPickingOrder());
		record.setIsPickDirectlyIfFeasible(from.isPickDirectlyIfFeasible());
		record.setIsDocComplete(from.isDocComplete());
		record.setSeqNo(from.getSeqNo());
		record.setTransfertTime(BigDecimal.valueOf(from.getTransferTimeDays()));
		record.setDeliveryTime_Promised(BigDecimal.valueOf(from.getLeadTimeDays()));
		record.setIsManufactured(StringUtils.ofBoolean(from.isManufactured()));
		record.setIsPurchased(StringUtils.ofBoolean(from.isPurchased()));
		record.setMaxManufacturedQtyPerOrderDispo(from.getMaxManufacturedQtyPerOrderDispo() != null ? from.getMaxManufacturedQtyPerOrderDispo().toBigDecimal() : null);
		record.setMaxManufacturedQtyPerOrderDispo_UOM_ID(from.getMaxManufacturedQtyPerOrderDispo() != null ? from.getMaxManufacturedQtyPerOrderDispo().getUomId().getRepoId() : -1);
		record.setDD_NetworkDistribution_ID(DistributionNetworkId.toRepoId(from.getDistributionNetworkId()));
		record.setOnMaterialReceiptWithDestWarehouse(from.getOnMaterialReceiptWithDestWarehouse() != null ? from.getOnMaterialReceiptWithDestWarehouse().getCode() : null);
		record.setIsMatured(from.isMatured());
		record.setM_Maturing_Configuration_ID(MaturingConfigId.toRepoId(from.getMaturingConfigId()));
		record.setM_Maturing_Configuration_Line_ID(MaturingConfigLineId.toRepoId(from.getMaturingConfigLineId()));
	}

	@Nullable
	private static Quantity extractMaxManufacturedQtyPerOrderDispo(final I_PP_Product_Planning record)
	{
		final BigDecimal maxManufacturedQtyPerOrderDispo = record.getMaxManufacturedQtyPerOrderDispo();
		if (maxManufacturedQtyPerOrderDispo.signum() <= 0)
		{
			return null;
		}

		final UomId uomId = UomId.ofRepoIdOrNull(record.getMaxManufacturedQtyPerOrderDispo_UOM_ID());
		if (uomId == null)
		{
			return null;
		}

		return Quantitys.create(maxManufacturedQtyPerOrderDispo, uomId);
	}

	@Override
	public Optional<ProductPlanning> find(@NonNull final ProductPlanningQuery query)
	{
		return toSql(query)
				.create()
				.firstOptional(I_PP_Product_Planning.class)
				.map(ProductPlanningDAO::fromRecord);
	}

	@Override
	public ResourceId findPlant(
			final int orgRepoId,
			final I_M_Warehouse warehouse,
			final int productRepoId,
			final int attributeSetInstanceRepoId)
	{
		//
		// First: get the plant directly from Warehouse
		if (warehouse != null)
		{
			final ResourceId plantId = ResourceId.ofRepoIdOrNull(warehouse.getPP_Plant_ID());
			if (plantId != null)
			{
				return plantId;
			}
		}

		//
		// Try searching for a product planning file and get the warehouse from there
		{
			final OrgId orgId = OrgId.ofRepoIdOrAny(orgRepoId);
			final WarehouseId warehouseId = warehouse != null ? WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID()) : null;
			final ProductId productId = ProductId.ofRepoId(productRepoId);
			final IQueryBuilder<I_PP_Product_Planning> queryBuilder = toSql(ProductPlanningQuery.builder()
					.orgId(orgId)
					.warehouseId(warehouseId)
					.plantId(null) // any plant
					.attributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNone(attributeSetInstanceRepoId))
					.productId(productId)
					.build());

			final List<ResourceId> plantIds = queryBuilder
					.create()
					.stream(I_PP_Product_Planning.class)
					.map(I_PP_Product_Planning::getS_Resource_ID) // get plant
					.filter(plantId -> plantId > 0)
					.distinct()
					.map(ResourceId::ofRepoId)
					.collect(ImmutableList.toImmutableList());
			if (plantIds.isEmpty())
			{
				throw new NoPlantForWarehouseException(orgId, warehouseId, productId);
			}
			else if (plantIds.size() > 1)
			{
				// we found more than one Plant => consider it as no plant was found
				throw new NoPlantForWarehouseException(orgId, warehouseId, productId);
			}
			else
			{
				return plantIds.get(0);
			}
		}
	}

	private IQueryBuilder<I_PP_Product_Planning> toSql(@NonNull ProductPlanningQuery query)
	{
		final IQueryBuilder<I_PP_Product_Planning> sqlQueryBuilder = queryBL
				.createQueryBuilder(I_PP_Product_Planning.class)
				.addOnlyActiveRecordsFilter();

		// Filter by context #AD_Client_ID
		sqlQueryBuilder.addOnlyContextClient();

		// Filter by AD_Org_ID: given AD_Org_ID or 0/null
		sqlQueryBuilder.addInArrayFilter(I_PP_Product_Planning.COLUMNNAME_AD_Org_ID, query.getOrgId(), OrgId.ANY, null);

		// Filter by Warehouse: given M_Warehouse_ID or 0/null
		sqlQueryBuilder.addInArrayFilter(I_PP_Product_Planning.COLUMNNAME_M_Warehouse_ID, query.getWarehouseId(), null);

		// Filter by Plant: given S_Resource_ID or 0/null
		if (query.getPlantId() != null)
		{
			sqlQueryBuilder.addInArrayFilter(I_PP_Product_Planning.COLUMNNAME_S_Resource_ID, query.getPlantId(), null);
		}

		// Filter by Product if provided

		sqlQueryBuilder.addInArrayFilter(I_PP_Product_Planning.COLUMNNAME_M_Product_ID, query.getProductId(), null);

		if (query.getMaturingConfigLineId() != null)
		{
			sqlQueryBuilder.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_M_Maturing_Configuration_Line_ID, query.getMaturingConfigLineId());
		}
		
		// Filter by ASI
		sqlQueryBuilder.filter(createAttributesFilter(query.getAttributeSetInstanceId()));

		return sqlQueryBuilder.orderBy()
				.addColumn(I_PP_Product_Planning.COLUMN_SeqNo, Direction.Ascending, Nulls.First)
				.addColumnDescending(I_PP_Product_Planning.COLUMNNAME_IsAttributeDependant) // prefer results with IsAttributeDependant='Y'
				.addColumn(I_PP_Product_Planning.COLUMNNAME_AD_Org_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_PP_Product_Planning.COLUMNNAME_M_Warehouse_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_PP_Product_Planning.COLUMN_S_Resource_ID, Direction.Descending, Nulls.Last)
				.endOrderBy();
	}

	private ICompositeQueryFilter<I_PP_Product_Planning> createAttributesFilter(@NonNull final AttributeSetInstanceId attributeSetInstanceId)
	{
		final ICompositeQueryFilter<I_PP_Product_Planning> matchingAsiFilter = queryBL
				.createCompositeQueryFilter(I_PP_Product_Planning.class)
				.setJoinAnd()
				.addEqualsFilter(I_PP_Product_Planning.COLUMN_IsAttributeDependant, true)
				.addFilter(createStorageAttributeKeyFilter(attributeSetInstanceId));

		return queryBL.createCompositeQueryFilter(I_PP_Product_Planning.class)
				.setJoinOr()
				.addEqualsFilter(I_PP_Product_Planning.COLUMN_IsAttributeDependant, false)
				.addFilter(matchingAsiFilter);
	}

	private static IQueryFilter<I_PP_Product_Planning> createStorageAttributeKeyFilter(@NonNull final AttributeSetInstanceId attributeSetInstanceId)
	{
		final AttributesKey attributesKey = AttributesKeys
				.createAttributesKeyFromASIStorageAttributes(attributeSetInstanceId)
				.orElse(AttributesKey.ALL);

		return AttributesKeyQueryHelper
				.createFor(I_PP_Product_Planning.COLUMN_StorageAttributesKey)
				.createFilter(AttributesKeyPatternsUtil.ofAttributeKey(attributesKey));
	}

	@Override
	public ProductPlanning save(@NonNull final ProductPlanning productPlanning)
	{
		if (productPlanning.isDisallowSaving())
		{
			throw new AdempiereException("Save is disabled for " + productPlanning);
		}

		final I_PP_Product_Planning record = InterfaceWrapperHelper.loadOrNew(productPlanning.getId(), I_PP_Product_Planning.class);
		updateRecord(record, productPlanning);
		save(record);

		// Because we want to also load changes done by model interceptors like StorageAttributesKey,
		// we are fully reloading our POJO from record
		return fromRecord(record);
	}

	@VisibleForTesting
	public static void save(final I_PP_Product_Planning record)
	{
		saveRecord(record);
	}

	@Override
	public void setProductBOMVersionsIdIfAbsent(
			@NonNull final ProductId productId,
			@NonNull final ProductBOMVersionsId bomVersionsId)
	{
		final List<I_PP_Product_Planning> productPlanningRecords = queryBL
				.createQueryBuilder(I_PP_Product_Planning.class)
				.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_M_Product_ID, productId)
				.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_PP_Product_BOMVersions_ID, null)
				.create()
				.list();

		for (final I_PP_Product_Planning productPlanningRecord : productPlanningRecords)
		{
			productPlanningRecord.setPP_Product_BOMVersions_ID(bomVersionsId.getRepoId());
			save(productPlanningRecord);
		}
	}

	@Override
	@NonNull
	public Set<ProductBOMVersionsId> retrieveAllPickingBOMVersionsIds()
	{
		return queryBL.createQueryBuilderOutOfTrx(I_PP_Product_Planning.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_IsManufactured, X_PP_Product_Planning.ISMANUFACTURED_Yes)
				.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_IsPickingOrder, true)
				.addNotNull(I_PP_Product_Planning.COLUMNNAME_PP_Product_BOMVersions_ID)
				.create()
				.listDistinct(I_PP_Product_Planning.COLUMNNAME_PP_Product_BOMVersions_ID, Integer.class)
				.stream()
				.map(ProductBOMVersionsId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	public List<ProductPlanning> retrieveProductPlanningForBomVersions(@NonNull final ProductBOMVersionsId bomVersionsId)
	{
		return queryBL.createQueryBuilder(I_PP_Product_Planning.class)
				.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_PP_Product_BOMVersions_ID, bomVersionsId)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(ProductPlanningDAO::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public List<ProductPlanning> retrieveActiveProductPlanningsBySchemaId(@NonNull final ProductPlanningSchemaId schemaId)
	{
		return queryBL.createQueryBuilder(I_PP_Product_Planning.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_M_Product_PlanningSchema_ID, schemaId)
				.create()
				.stream()
				.map(ProductPlanningDAO::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public Optional<ProductPlanning> retrieveActiveProductPlanningByProductAndSchemaId(
			@NonNull final ProductId productId,
			@NonNull final ProductPlanningSchemaId schemaId)
	{
		return queryBL.createQueryBuilder(I_PP_Product_Planning.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_M_Product_ID, productId)
				.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_M_Product_PlanningSchema_ID, schemaId)
				.orderBy(I_PP_Product_Planning.COLUMN_SeqNo)
				.create()
				.firstOptional(I_PP_Product_Planning.class)
				.map(ProductPlanningDAO::fromRecord);
	}

	@Override
	public Stream<I_M_Product> streamProductsWithNoProductPlanningButWithSchemaSelector()
	{
		final IQuery<I_PP_Product_Planning> existentProductPlanning = queryBL.createQueryBuilder(I_PP_Product_Planning.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.create();

		// TODO move it to ProductDAO
		return queryBL.createQueryBuilder(I_M_Product.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addNotInSubQueryFilter(I_M_Product.COLUMNNAME_M_Product_ID, I_PP_Product_Planning.COLUMNNAME_M_Product_ID, existentProductPlanning)
				.addNotNull(I_M_Product.COLUMN_M_ProductPlanningSchema_Selector)
				.create()
				.iterateAndStream();
	}

	@Override
	public Optional<ProductPlanning> retrieveManufacturingOrTradingPlanning(@NonNull final ProductId productId, @NonNull final OrgId orgId)
	{
		final ICompositeQueryFilter<I_PP_Product_Planning> manufacturedOrTraded = queryBL.createCompositeQueryFilter(I_PP_Product_Planning.class)
				.setJoinOr()
				.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_IsManufactured, X_PP_Product_Planning.ISMANUFACTURED_Yes)
				.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_IsTraded, X_PP_Product_Planning.ISTRADED_Yes);

		final I_PP_Product_Planning record = queryBL.createQueryBuilder(I_PP_Product_Planning.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_M_Product_ID, productId)
				.addInArrayOrAllFilter(I_PP_Product_Planning.COLUMNNAME_AD_Org_ID, orgId, OrgId.ANY)
				.filter(manufacturedOrTraded)
				.orderBy()
				.addColumn(I_PP_Product_Planning.COLUMNNAME_AD_Org_ID, Direction.Descending, Nulls.Last) // specific org first
				.addColumn(I_PP_Product_Planning.COLUMNNAME_IsManufactured, Direction.Descending, Nulls.Last) // 'Y' first, NULL last
				.addColumn(I_PP_Product_Planning.COLUMNNAME_IsTraded, Direction.Descending, Nulls.Last) // 'Y' first, NULL last
				.addColumn(I_PP_Product_Planning.COLUMNNAME_M_Warehouse_ID, Direction.Descending, Nulls.Last)
				.endOrderBy()
				.create()
				.first();

		return record != null ? Optional.of(ProductPlanningDAO.fromRecord(record)) : Optional.empty();
	}

}
