package de.metas.deliveryplanning.cache;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.TableNamesGroup;
import de.metas.cache.model.CacheInvalidateRequest;
import de.metas.cache.model.CacheSourceModelFactory;
import de.metas.cache.model.ICacheSourceModel;
import de.metas.cache.model.IModelCacheInvalidateRequestFactoryGroup;
import de.metas.cache.model.ModelCacheInvalidateRequestFactory;
import de.metas.cache.model.ModelCacheInvalidationTiming;
import de.metas.cache.model.WindowBasedModelCacheInvalidateRequestFactoryGroup;
import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.ShippingPackageId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.I_M_Delivery_Planning_Delivery_Instructions_V;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Component
public class DeliveryPlanningModelCacheInvalidateRequestFactoryGroup implements IModelCacheInvalidateRequestFactoryGroup, ModelCacheInvalidateRequestFactory
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final WindowBasedModelCacheInvalidateRequestFactoryGroup windowBasedModelCacheInvalidateRequestFactoryGroup;

	private static final TableNamesGroup tableNamesToEnableRemoveCacheInvalidation = TableNamesGroup.builder()
			.groupId(DeliveryPlanningModelCacheInvalidateRequestFactoryGroup.class.getSimpleName())
			.tableName(I_M_Delivery_Planning.Table_Name)
			.tableName(I_M_ShippingPackage.Table_Name)
			.build();

	public DeliveryPlanningModelCacheInvalidateRequestFactoryGroup(
			@NonNull final WindowBasedModelCacheInvalidateRequestFactoryGroup windowBasedModelCacheInvalidateRequestFactoryGroup)
	{
		this.windowBasedModelCacheInvalidateRequestFactoryGroup = windowBasedModelCacheInvalidateRequestFactoryGroup;
	}

	@Override
	public TableNamesGroup getTableNamesToEnableRemoveCacheInvalidation() {return tableNamesToEnableRemoveCacheInvalidation;}

	@Override
	public Set<ModelCacheInvalidateRequestFactory> getFactoriesByTableName(@NonNull final String tableName, @NonNull final ModelCacheInvalidationTiming timing_NOTUSED)
	{
		return tableNamesToEnableRemoveCacheInvalidation.containsTableName(tableName) ? ImmutableSet.of(this) : ImmutableSet.of();
	}

	@Override
	public List<CacheInvalidateRequest> createRequestsFromModel(final ICacheSourceModel model, final ModelCacheInvalidationTiming timing)
	{
		final String tableName = model.getTableName();
		if (I_M_Delivery_Planning.Table_Name.equals(tableName))
		{
			return createRequestsFromDeliveryPlanning(model, timing);
		}
		else if (I_M_ShippingPackage.Table_Name.equals(tableName))
		{
			return createRequestsFromShippingPackage(model, timing);
		}
		else
		{
			// shall not happen
			return ImmutableList.of();
		}
	}

	private Stream<CacheInvalidateRequest> explodeUsingWindowBasedCacheInvalidate(
			final I_M_Delivery_Planning_Delivery_Instructions_V record,
			final ModelCacheInvalidationTiming timing)
	{
		final Set<ModelCacheInvalidateRequestFactory> factories = windowBasedModelCacheInvalidateRequestFactoryGroup.getFactoriesByTableName(I_M_Delivery_Planning_Delivery_Instructions_V.Table_Name);
		if (factories.isEmpty())
		{
			return Stream.of();
		}

		final ICacheSourceModel sourceModel = CacheSourceModelFactory.ofObject(record);

		return factories.stream()
				.flatMap(factory -> factory.createRequestsFromModel(sourceModel, timing).stream());
	}

	private ImmutableList<CacheInvalidateRequest> createRequestsFromDeliveryPlanning(final ICacheSourceModel model, final ModelCacheInvalidationTiming timing)
	{
		if (timing.isChange() && model.isValueChanged(I_M_Delivery_Planning.COLUMNNAME_ReleaseNo))
		{
			final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(model.getValueAsInt(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, -1));

			return queryBL.createQueryBuilder(I_M_Delivery_Planning_Delivery_Instructions_V.class)
					.addEqualsFilter(I_M_Delivery_Planning_Delivery_Instructions_V.COLUMNNAME_M_Delivery_Planning_ID, deliveryPlanningId)
					.stream()
					.flatMap(record -> explodeUsingWindowBasedCacheInvalidate(record, timing))
					.collect(ImmutableList.toImmutableList());
		}
		else
		{
			return ImmutableList.of();
		}
	}

	private ImmutableList<CacheInvalidateRequest> createRequestsFromShippingPackage(final ICacheSourceModel model, final ModelCacheInvalidationTiming timing)
	{
		// NOTE: do not react for timing.isAfterChange() because we assume shipping package changes is not changing the values of M_Delivery_Planning_Delivery_Instructions_V
		if (timing.isAfterNew()
				|| timing.isAfterDelete())
		{
			final ShippingPackageId shippingPackageId = ShippingPackageId.ofRepoId(model.getValueAsInt(I_M_ShippingPackage.COLUMNNAME_M_ShippingPackage_ID, -1));

			return queryBL.createQueryBuilder(I_M_Delivery_Planning_Delivery_Instructions_V.class)
					.addEqualsFilter(I_M_Delivery_Planning_Delivery_Instructions_V.COLUMNNAME_M_ShippingPackage_ID, shippingPackageId)
					.stream()
					.flatMap(record -> explodeUsingWindowBasedCacheInvalidate(record, timing))
					.collect(ImmutableList.toImmutableList());
		}
		else
		{
			return ImmutableList.of();
		}
	}
}
