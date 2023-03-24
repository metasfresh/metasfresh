package de.metas.deliveryplanning.cache;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.cache.TableNamesGroup;
import de.metas.cache.model.CacheInvalidateRequest;
import de.metas.cache.model.CacheSourceModelFactory;
import de.metas.cache.model.ICacheSourceModel;
import de.metas.cache.model.IModelCacheInvalidateRequestFactoryGroup;
import de.metas.cache.model.ModelCacheInvalidateRequestFactory;
import de.metas.cache.model.ModelCacheInvalidationTiming;
import de.metas.cache.model.WindowBasedModelCacheInvalidateRequestFactoryGroup;
import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.logging.LogManager;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.ShippingPackageId;
import de.metas.util.Services;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.I_M_Delivery_Planning_Delivery_Instructions_V;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * Reacts on source table changes and invalidates M_Delivery_Planning_Delivery_Instructions_V records in all parent/child combinations.
 */
@Component
public class DeliveryPlanningModelCacheInvalidateRequestFactoryGroup implements IModelCacheInvalidateRequestFactoryGroup
{
	private static final Logger logger = LogManager.getLogger(DeliveryPlanningModelCacheInvalidateRequestFactoryGroup.class);

	private final ImmutableSetMultimap<String, ModelCacheInvalidateRequestFactory> factoriesByTableName;
	private final TableNamesGroup tableNamesToEnableRemoveCacheInvalidation;

	public DeliveryPlanningModelCacheInvalidateRequestFactoryGroup(
			@NonNull final WindowBasedModelCacheInvalidateRequestFactoryGroup windowBasedModelCacheInvalidateRequestFactoryGroup)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		this.factoriesByTableName = ImmutableSetMultimap.<String, ModelCacheInvalidateRequestFactory>builder()
				.put(I_M_Delivery_Planning.Table_Name, new M_Delivery_Planning_Factory(queryBL, windowBasedModelCacheInvalidateRequestFactoryGroup))
				.put(I_M_ShippingPackage.Table_Name, new M_ShippingPackage_Factory(queryBL, windowBasedModelCacheInvalidateRequestFactoryGroup))
				.build();

		this.tableNamesToEnableRemoveCacheInvalidation = TableNamesGroup.builder()
				.groupId(DeliveryPlanningModelCacheInvalidateRequestFactoryGroup.class.getSimpleName())
				.tableNames(factoriesByTableName.keySet())
				.build();

		// LogManager.setLoggerLevel(logger, Level.TRACE);
	}

	@Override
	public TableNamesGroup getTableNamesToEnableRemoveCacheInvalidation() {return tableNamesToEnableRemoveCacheInvalidation;}

	@Override
	public Set<ModelCacheInvalidateRequestFactory> getFactoriesByTableName(@NonNull final String tableName, @NonNull final ModelCacheInvalidationTiming timing_NOTUSED)
	{
		return factoriesByTableName.get(tableName);
	}

	//
	//
	//
	//
	//

	@AllArgsConstructor
	private static abstract class FactoryTemplate implements ModelCacheInvalidateRequestFactory
	{
		protected final IQueryBL queryBL;
		private final WindowBasedModelCacheInvalidateRequestFactoryGroup windowBasedModelCacheInvalidateRequestFactoryGroup;

		protected abstract boolean isEligibleForInvalidation(final ICacheSourceModel model, final ModelCacheInvalidationTiming timing);

		protected abstract IQueryBuilder<I_M_Delivery_Planning_Delivery_Instructions_V> createQuery(final ICacheSourceModel model);

		@Override
		public final ImmutableList<CacheInvalidateRequest> createRequestsFromModel(@NonNull final ICacheSourceModel model, @NonNull final ModelCacheInvalidationTiming timing)
		{
			if (isEligibleForInvalidation(model, timing))
			{
				return createRequestsFromQuery(timing, createQuery(model));
			}
			else
			{
				logger.debug("Skip cache invalidation because not eligible: model={}, timing={}, factory={}", model, timing, this);
				return ImmutableList.of();
			}
		}

		private ImmutableList<CacheInvalidateRequest> createRequestsFromQuery(
				@NonNull final ModelCacheInvalidationTiming timing,
				@NonNull final IQueryBuilder<I_M_Delivery_Planning_Delivery_Instructions_V> query
		)
		{
			final Set<ModelCacheInvalidateRequestFactory> factories = windowBasedModelCacheInvalidateRequestFactoryGroup.getFactoriesByTableName(I_M_Delivery_Planning_Delivery_Instructions_V.Table_Name);
			if (factories.isEmpty())
			{
				logger.debug("Skip cache invalidation because no factories found");
				return ImmutableList.of();
			}

			final ImmutableList<ICacheSourceModel> sourceModels = query.stream()
					.map(CacheSourceModelFactory::ofObject)
					.collect(ImmutableList.toImmutableList());
			if (sourceModels.isEmpty())
			{
				logger.debug("Skip cache invalidation because no models found for query: {}", query);
				return ImmutableList.of();
			}

			final ImmutableList.Builder<CacheInvalidateRequest> result = ImmutableList.builder();

			for (final ModelCacheInvalidateRequestFactory factory : factories)
			{
				for (final ICacheSourceModel sourceModel : sourceModels)
				{
					final List<CacheInvalidateRequest> requests = factory.createRequestsFromModel(sourceModel, timing);
					logger.debug("Created requests: {} \n factory={} \n sourceModel={} \n timing={} \n query={}", requests, factory, sourceModel, timing, query);

					result.addAll(requests);
				}
			}

			return result.build();
		}

	}

	private static class M_Delivery_Planning_Factory extends FactoryTemplate
	{
		public M_Delivery_Planning_Factory(final IQueryBL queryBL, final WindowBasedModelCacheInvalidateRequestFactoryGroup windowBasedModelCacheInvalidateRequestFactoryGroup)
		{
			super(queryBL, windowBasedModelCacheInvalidateRequestFactoryGroup);
		}

		@Override
		protected boolean isEligibleForInvalidation(final ICacheSourceModel model, final ModelCacheInvalidationTiming timing)
		{
			// NOTE: react only on ReleaseNo changes (before & after)
			// because in the M_Delivery_Planning_Delivery_Instructions_V view
			// we join "M_Delivery_Planning dp" to "M_ShipperTransportation di" by "di.documentno = dp.releaseno"
			//
			// NOTE 2: we have to react on BEFORE changes too because we have to get those view rows
			// which will disappear from the view AFTER changes.
			return timing.isChange() && model.isValueChanged(I_M_Delivery_Planning.COLUMNNAME_ReleaseNo);
		}

		@Override
		protected IQueryBuilder<I_M_Delivery_Planning_Delivery_Instructions_V> createQuery(final ICacheSourceModel model)
		{
			final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(model.getValueAsInt(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, -1));

			return queryBL.createQueryBuilder(I_M_Delivery_Planning_Delivery_Instructions_V.class)
					.addEqualsFilter(I_M_Delivery_Planning_Delivery_Instructions_V.COLUMNNAME_M_Delivery_Planning_ID, deliveryPlanningId);
		}
	}

	private static class M_ShippingPackage_Factory extends FactoryTemplate
	{
		public M_ShippingPackage_Factory(final IQueryBL queryBL, final WindowBasedModelCacheInvalidateRequestFactoryGroup windowBasedModelCacheInvalidateRequestFactoryGroup)
		{
			super(queryBL, windowBasedModelCacheInvalidateRequestFactoryGroup);
		}

		@Override
		protected boolean isEligibleForInvalidation(final ICacheSourceModel model, final ModelCacheInvalidationTiming timing)
		{
			// NOTE: do not react for timing.isAfterChange()
			// because we assume shipping package changes is not changing the values of M_Delivery_Planning_Delivery_Instructions_V
			return timing.isAfterNew() || timing.isAfterDelete();
		}

		@Override
		protected IQueryBuilder<I_M_Delivery_Planning_Delivery_Instructions_V> createQuery(final ICacheSourceModel model)
		{
			final ShippingPackageId shippingPackageId = ShippingPackageId.ofRepoId(model.getValueAsInt(I_M_ShippingPackage.COLUMNNAME_M_ShippingPackage_ID, -1));

			return queryBL.createQueryBuilder(I_M_Delivery_Planning_Delivery_Instructions_V.class)
					.addEqualsFilter(I_M_Delivery_Planning_Delivery_Instructions_V.COLUMNNAME_M_ShippingPackage_ID, shippingPackageId);
		}
	}
}
