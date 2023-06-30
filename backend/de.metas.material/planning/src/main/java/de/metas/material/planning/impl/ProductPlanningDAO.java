package de.metas.material.planning.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.material.commons.attributes.AttributesKeyPatternsUtil;
import de.metas.material.commons.attributes.AttributesKeyQueryHelper;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.IResourceDAO;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.exception.NoPlantForWarehouseException;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributesKeys;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.eevolution.api.ProductBOMVersionsId;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.model.X_PP_Product_Planning;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class ProductPlanningDAO implements IProductPlanningDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IResourceDAO resourcesRepo = Services.get(IResourceDAO.class);

	@Override
	public I_PP_Product_Planning getById(@NonNull final ProductPlanningId ppProductPlanningId)
	{
		return loadOutOfTrx(ppProductPlanningId, I_PP_Product_Planning.class);
	}

	@Override
	public Optional<I_PP_Product_Planning> find(@NonNull final ProductPlanningQuery productPlanningQuery)
	{
		final IQueryBuilder<I_PP_Product_Planning> queryBuilder = createQueryBuilder(
				productPlanningQuery.getOrgId(),
				productPlanningQuery.getWarehouseId(),
				productPlanningQuery.getPlantId(),
				productPlanningQuery.getProductId(),
				productPlanningQuery.getAttributeSetInstanceId());

		//
		// Fetch first matching product planning data
		final I_PP_Product_Planning productPlanningData = queryBuilder.create().first();
		return Optional.ofNullable(productPlanningData);
	}

	@Override
	public I_S_Resource findPlant(
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
				return resourcesRepo.getById(plantId);
			}
		}

		//
		// Try searching for a product planning file and get the warehouse from there
		{
			final OrgId orgId = OrgId.ofRepoIdOrAny(orgRepoId);
			final WarehouseId warehouseId = warehouse != null ? WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID()) : null;
			final ProductId productId = ProductId.ofRepoId(productRepoId);
			final AttributeSetInstanceId attributeSetInstanceId = AttributeSetInstanceId.ofRepoIdOrNone(attributeSetInstanceRepoId);
			final IQueryBuilder<I_PP_Product_Planning> queryBuilder = createQueryBuilder(
					orgId,
					warehouseId,
					(ResourceId)null,  // any plant
					productId,
					attributeSetInstanceId);

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
				// we found more then one Plant => consider it as no plant was found
				throw new NoPlantForWarehouseException(orgId, warehouseId, productId);
			}
			else
			{
				return resourcesRepo.getById(plantIds.get(0));
			}
		}
	}

	private IQueryBuilder<I_PP_Product_Planning> createQueryBuilder(
			final OrgId orgId,
			final WarehouseId warehouseId,
			final ResourceId resourceId,
			final ProductId productId,
			final AttributeSetInstanceId attributeSetInstanceId)
	{
		final IQueryBuilder<I_PP_Product_Planning> queryBuilder = queryBL
				.createQueryBuilder(I_PP_Product_Planning.class)
				.addOnlyActiveRecordsFilter();

		// Filter by context #AD_Client_ID
		queryBuilder.addOnlyContextClient();

		// Filter by AD_Org_ID: given AD_Org_ID or 0/null
		queryBuilder.addInArrayFilter(I_PP_Product_Planning.COLUMNNAME_AD_Org_ID, orgId, OrgId.ANY, null);

		// Filter by Warehouse: given M_Warehouse_ID or 0/null
		queryBuilder.addInArrayFilter(I_PP_Product_Planning.COLUMNNAME_M_Warehouse_ID, warehouseId, null);

		// Filter by Plant: given S_Resource_ID or 0/null
		if (resourceId != null)
		{
			queryBuilder.addInArrayFilter(I_PP_Product_Planning.COLUMNNAME_S_Resource_ID, resourceId, null);
		}

		// Filter by Product if provided

		queryBuilder.addInArrayFilter(I_PP_Product_Planning.COLUMNNAME_M_Product_ID, productId, null);

		// Filter by ASI
		final ICompositeQueryFilter<I_PP_Product_Planning> attributesFilter = createAttributesFilter(attributeSetInstanceId);
		queryBuilder.filter(attributesFilter);

		return queryBuilder.orderBy()
				.addColumn(I_PP_Product_Planning.COLUMN_SeqNo, Direction.Ascending, Nulls.First)
				.addColumnDescending(I_PP_Product_Planning.COLUMNNAME_IsAttributeDependant) // prefer results with IsAttributeDependant='Y'
				.addColumn(I_PP_Product_Planning.COLUMNNAME_AD_Org_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_PP_Product_Planning.COLUMNNAME_M_Warehouse_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_PP_Product_Planning.COLUMN_S_Resource_ID, Direction.Descending, Nulls.Last)
				.endOrderBy();
	}

	private ICompositeQueryFilter<I_PP_Product_Planning> createAttributesFilter(final AttributeSetInstanceId attributeSetInstanceId)
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

	private static IQueryFilter<I_PP_Product_Planning> createStorageAttributeKeyFilter(final AttributeSetInstanceId attributeSetInstanceId)
	{
		final AttributesKey attributesKey = AttributesKeys
				.createAttributesKeyFromASIStorageAttributes(attributeSetInstanceId)
				.orElse(AttributesKey.ALL);

		return AttributesKeyQueryHelper
				.createFor(I_PP_Product_Planning.COLUMN_StorageAttributesKey)
				.createFilter(AttributesKeyPatternsUtil.ofAttributeKey(attributesKey));
	}

	@Override
	public void save(final I_PP_Product_Planning productPlanningRecord)
	{
		saveRecord(productPlanningRecord);
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
	public List<I_PP_Product_Planning> retrieveProductPlanningForBomVersions(@NonNull final ProductBOMVersionsId bomVerisonId)
	{
		return queryBL.createQueryBuilder(I_PP_Product_Planning.class)
				.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_PP_Product_BOMVersions_ID, bomVerisonId)
				.addOnlyActiveRecordsFilter()
				.create()
				.listImmutable(I_PP_Product_Planning.class);
	}
}
