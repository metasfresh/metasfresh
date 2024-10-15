package de.metas.material.planning.impl;

import com.google.common.collect.ImmutableSet;
import de.metas.cache.annotation.CacheCtx;
import de.metas.i18n.IModelTranslationMap;
import de.metas.material.planning.IResourceDAO;
import de.metas.material.planning.ResourceType;
import de.metas.material.planning.ResourceTypeId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ResourceId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_S_Resource;
import org.compiere.model.I_S_ResourceType;
import org.compiere.model.X_M_Product;
import org.compiere.model.X_S_Resource;
import org.compiere.util.TimeUtil;

import java.time.DayOfWeek;
import java.time.temporal.TemporalUnit;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

// TODO: merge it with modern de.metas.resource.ResourceRepository (so far not available in this branch)
public class ResourceDAO implements IResourceDAO
{
	@Override
	public ResourceType getResourceTypeById(@NonNull final ResourceTypeId resourceTypeId)
	{
		final I_S_ResourceType record = loadOutOfTrx(resourceTypeId, I_S_ResourceType.class);
		return toResourceType(record);
	}

	@Override
	public ResourceType getResourceTypeByResourceId(final ResourceId resourceId)
	{
		final ResourceTypeId resourceTypeId = getResourceTypeIdByResourceId(resourceId);
		return getResourceTypeById(resourceTypeId);
	}

	@Override
	public ResourceTypeId getResourceTypeIdByResourceId(final ResourceId resourceId)
	{
		final I_S_Resource resource = getResourceById(resourceId);
		return ResourceTypeId.ofRepoId(resource.getS_ResourceType_ID());
	}

	public I_S_Resource getResourceById(@NonNull final ResourceId resourceId)
	{
		return loadOutOfTrx(resourceId, I_S_Resource.class);
	}

	private static ResourceType toResourceType(I_S_ResourceType record)
	{
		final IModelTranslationMap trls = InterfaceWrapperHelper.getModelTranslationMap(record);

		final UomId durationUomId = UomId.ofRepoId(record.getC_UOM_ID());
		final TemporalUnit durationUnit = Services.get(IUOMDAO.class).getTemporalUnitByUomId(durationUomId);

		return ResourceType.builder()
				.id(ResourceTypeId.ofRepoId(record.getS_ResourceType_ID()))
				.caption(trls.getColumnTrl(I_S_ResourceType.COLUMNNAME_Name, record.getName()))
				.active(record.isActive())
				.productCategoryId(ProductCategoryId.ofRepoId(record.getM_Product_Category_ID()))
				.durationUomId(durationUomId)
				.durationUnit(durationUnit)
				.availableDaysOfWeek(extractAvailableDaysOfWeek(record))
				.timeSlot(record.isTimeSlot())
				.timeSlotStart(TimeUtil.asLocalTime(record.getTimeSlotStart()))
				.timeSlotEnd(TimeUtil.asLocalTime(record.getTimeSlotEnd()))
				.build();
	}

	private static ImmutableSet<DayOfWeek> extractAvailableDaysOfWeek(@NonNull final I_S_ResourceType resourceType)
	{
		if (resourceType.isDateSlot())
		{
			final ImmutableSet.Builder<DayOfWeek> days = ImmutableSet.builder();
			if (resourceType.isOnMonday())
			{
				days.add(DayOfWeek.MONDAY);
			}
			if (resourceType.isOnTuesday())
			{
				days.add(DayOfWeek.TUESDAY);
			}
			if (resourceType.isOnWednesday())
			{
				days.add(DayOfWeek.WEDNESDAY);
			}
			if (resourceType.isOnThursday())
			{
				days.add(DayOfWeek.THURSDAY);
			}
			if (resourceType.isOnFriday())
			{
				days.add(DayOfWeek.FRIDAY);
			}
			if (resourceType.isOnSaturday())
			{
				days.add(DayOfWeek.SATURDAY);
			}
			if (resourceType.isOnSunday())
			{
				days.add(DayOfWeek.SUNDAY);
			}

			return days.build();
		}
		else
		{
			return ImmutableSet.copyOf(DayOfWeek.values());
		}
	}

	@Override
	public I_S_Resource getById(@NonNull final ResourceId resourceId)
	{
		return loadOutOfTrx(resourceId, I_S_Resource.class);
	}

	@Override
	public List<I_S_Resource> getByIds(@NonNull final Set<ResourceId> resourceIds)
	{
		return InterfaceWrapperHelper.loadByRepoIdAwaresOutOfTrx(resourceIds, I_S_Resource.class);
	}

	@Override
	@Cached(cacheName = I_S_Resource.Table_Name + "#by"
			+ "#" + I_S_Resource.COLUMNNAME_AD_Client_ID
			+ "#" + I_S_Resource.COLUMNNAME_IsManufacturingResource)
	public List<I_S_Resource> retrievePlants(final @CacheCtx Properties ctx)
	{
		final IQueryBuilder<I_S_Resource> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_S_Resource.class, ctx, ITrx.TRXNAME_None);

		final ICompositeQueryFilter<I_S_Resource> filters = queryBuilder.getCompositeFilter();

		//
		// Only manufacturing resources
		filters.addEqualsFilter(I_S_Resource.COLUMNNAME_IsManufacturingResource, true);

		//
		// Only Plant resources
		filters.addEqualsFilter(I_S_Resource.COLUMNNAME_ManufacturingResourceType, X_S_Resource.MANUFACTURINGRESOURCETYPE_Plant);

		//
		// Only for current AD_Client_ID
		filters.addOnlyContextClient(ctx);

		//
		// Only active ones
		filters.addOnlyActiveRecordsFilter();

		queryBuilder.orderBy()
				.addColumn(I_S_Resource.COLUMNNAME_S_Resource_ID);

		return queryBuilder
				.create()
				.list(I_S_Resource.class);
	}

	@Override
	public I_S_Resource retrievePlant(final Properties ctx, final int resourceId)
	{
		for (final I_S_Resource plant : retrievePlants(ctx))
		{
			if (plant.getS_Resource_ID() == resourceId)
			{
				return plant;
			}
		}

		return null;
	}

	@Override
	public void onResourceChanged(@NonNull final I_S_Resource resource)
	{
		final ResourceId resourceId = ResourceId.ofRepoId(resource.getS_Resource_ID());
		final ResourceTypeId resourceTypeId = ResourceTypeId.ofRepoId(resource.getS_ResourceType_ID());

		final IProductDAO productsRepo = Services.get(IProductDAO.class);

		productsRepo.updateProductsByResourceIds(ImmutableSet.of(resourceId), (resourceId1, existingProduct) -> {
			final I_M_Product productToUpdate;
			if (existingProduct == null)
			{
				final ResourceType fromResourceType = getResourceTypeById(resourceTypeId);
				productToUpdate = InterfaceWrapperHelper.newInstance(I_M_Product.class);

				updateProductFromResourceType(productToUpdate, fromResourceType);
			}
			else
			{
				productToUpdate = existingProduct;
			}

			updateProductFromResource(productToUpdate, resource);
		});
	}

	private void updateProductFromResource(@NonNull final I_M_Product product, @NonNull final I_S_Resource from)
	{
		product.setProductType(X_M_Product.PRODUCTTYPE_Resource);
		product.setS_Resource_ID(from.getS_Resource_ID());
		product.setIsActive(from.isActive());

		product.setValue("PR" + from.getValue()); // the "PR" is a QnD solution to the possible problem that if the production resource's value is set to its ID (like '1000000") there is probably already a product with the same value.
		product.setName(from.getName());
		product.setDescription(from.getDescription());
	}

	@Override
	public void onResourceTypeChanged(final I_S_ResourceType resourceTypeRecord)
	{
		final Set<ResourceId> resourceIds = Services.get(IQueryBL.class)
				.createQueryBuilder(I_S_Resource.class) // in trx!
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_S_Resource.COLUMNNAME_S_ResourceType_ID, resourceTypeRecord.getS_ResourceType_ID())
				.create()
				.listIds(ResourceId::ofRepoId);
		if (resourceIds.isEmpty())
		{
			return;
		}

		final ResourceType resourceType = toResourceType(resourceTypeRecord);
		final IProductDAO productsRepo = Services.get(IProductDAO.class);
		productsRepo.updateProductsByResourceIds(resourceIds, product -> updateProductFromResourceType(product, resourceType));
	}

	private void updateProductFromResourceType(final I_M_Product product, final ResourceType from)
	{
		product.setProductType(X_M_Product.PRODUCTTYPE_Resource);
		product.setC_UOM_ID(from.getDurationUomId().getRepoId());
		product.setM_Product_Category_ID(ProductCategoryId.toRepoId(from.getProductCategoryId()));
	}

	@Override
	public ImmutableSet<ResourceId> getResourceIdsByUserId(@NonNull final UserId userId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(I_S_Resource.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_S_Resource.COLUMNNAME_AD_User_ID, userId)
				.create()
				.listIds(ResourceId::ofRepoId);
	}

	@Override
	public ImmutableSet<ResourceId> getResourceIdsByResourceTypeIds(@NonNull final Collection<ResourceTypeId> resourceTypeIds)
	{
		if (resourceTypeIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(I_S_Resource.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_S_Resource.COLUMNNAME_S_ResourceType_ID, resourceTypeIds)
				.create()
				.listIds(ResourceId::ofRepoId);
	}

}
