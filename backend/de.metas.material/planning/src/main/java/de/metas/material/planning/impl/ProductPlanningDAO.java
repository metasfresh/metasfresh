package de.metas.material.planning.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.material.commons.attributes.AttributesKeyPatternsUtil;
import de.metas.material.commons.attributes.AttributesKeyQueryHelper;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.exception.NoPlantForWarehouseException;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ProductPlanningSchemaId;
import de.metas.product.ResourceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributesKeys;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.eevolution.api.ProductBOMVersionsId;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.model.X_PP_Product_Planning;

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
				// TODO implement
				.build();
	}

	private static void updateRecord(@NonNull final I_PP_Product_Planning record, @NonNull final ProductPlanning from)
	{
		// TODO impl
	}

	@Override
	public Optional<ProductPlanning> find(@NonNull final ProductPlanningQuery productPlanningQuery)
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
		if (productPlanningData == null)
		{
			return Optional.empty();
		}

		return Optional.of(fromRecord(productPlanningData));
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
				return plantIds.get(0);
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
	public ProductPlanning save(@NonNull final ProductPlanning productPlanning)
	{
		if (productPlanning.isDisallowSaving())
		{
			throw new AdempiereException("Save is disabled for " + productPlanning);
		}

		final I_PP_Product_Planning record = InterfaceWrapperHelper.loadOrNew(productPlanning.getId(), I_PP_Product_Planning.class);
		updateRecord(record, productPlanning);
		save(record);
		return productPlanning.withId(ProductPlanningId.ofRepoId(record.getPP_Product_Planning_ID()));
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
	public List<ProductPlanning> retrieveProductPlanningForBomVersions(@NonNull final ProductBOMVersionsId bomVerisonId)
	{
		return queryBL.createQueryBuilder(I_PP_Product_Planning.class)
				.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_PP_Product_BOMVersions_ID, bomVerisonId)
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

}
