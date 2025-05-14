package de.metas.cache.model.view_source;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import de.metas.cache.model.CacheInvalidateRequest;
import de.metas.cache.model.CacheSourceModelFactory;
import de.metas.cache.model.ICacheSourceModel;
import de.metas.cache.model.ModelCacheInvalidateRequestFactory;
import de.metas.cache.model.ModelCacheInvalidationTiming;
import de.metas.cache.model.WindowBasedModelCacheInvalidateRequestFactoryGroup;
import de.metas.logging.LogManager;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.table.api.ColumnName;
import org.adempiere.ad.table.api.ViewSourceDescriptor;
import org.slf4j.Logger;

import java.util.List;
import java.util.Set;

public class ViewSourceCacheInvalidateRequestFactory implements ModelCacheInvalidateRequestFactory
{
	private static final Logger logger = LogManager.getLogger(ViewSourceCacheInvalidateRequestFactory.class);

	private final IQueryBL queryBL;
	private final WindowBasedModelCacheInvalidateRequestFactoryGroup windowBasedModelCacheInvalidateRequestFactoryGroup;

	private final ViewSourceDescriptor descriptor;

	@Builder
	private ViewSourceCacheInvalidateRequestFactory(
			final IQueryBL queryBL,
			final WindowBasedModelCacheInvalidateRequestFactoryGroup windowBasedModelCacheInvalidateRequestFactoryGroup,
			//
			@NonNull final ViewSourceDescriptor descriptor)
	{
		this.queryBL = queryBL;
		this.windowBasedModelCacheInvalidateRequestFactoryGroup = windowBasedModelCacheInvalidateRequestFactoryGroup;
		this.descriptor = descriptor;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(descriptor)
				.toString();
	}

	public String getSourceTableName() {return descriptor.getSourceTableName().getAsString();}

	@Override
	public final ImmutableList<CacheInvalidateRequest> createRequestsFromModel(@NonNull final ICacheSourceModel model, @NonNull final ModelCacheInvalidationTiming timing)
	{
		final Set<ModelCacheInvalidateRequestFactory> factories = windowBasedModelCacheInvalidateRequestFactoryGroup.getFactoriesByTableName(descriptor.getViewName().getAsString());
		if (factories.isEmpty())
		{
			logger.debug("Skip cache invalidation because no factories found");
			return ImmutableList.of();
		}

		if (!isEligibleForInvalidation(model, timing))
		{
			logger.debug("Skip cache invalidation because not eligible: model={}, timing={}, factory={}", model, timing, this);
			return ImmutableList.of();
		}

		final IQueryBuilder<Object> query = createQuery(model);
		if (query == null)
		{
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

	private boolean isEligibleForInvalidation(final ICacheSourceModel model, final ModelCacheInvalidationTiming timing)
	{
		if (!descriptor.getInvalidateOnTimings().contains(timing))
		{
			return false;
		}

		if (timing.isChange()
				&& !descriptor.getInvalidateOnChangeOnlyForColumnNames().isEmpty()
				&& !isValueChanged(model, descriptor.getInvalidateOnChangeOnlyForColumnNames()))
		{
			return false;
		}

		return true;
	}

	private static boolean isValueChanged(final ICacheSourceModel model, final Set<ColumnName> columnNames)
	{
		for (final ColumnName columnName : columnNames)
		{
			if (model.isValueChanged(columnName.getAsString()))
			{
				return true;
			}
		}

		return false;
	}

	private IQueryBuilder<Object> createQuery(final ICacheSourceModel model)
	{
		final int linkRecordId = model.getValueAsInt(descriptor.getSourceLinkColumnName().getAsString(), -1);
		if (linkRecordId < 0)
		{
			logger.debug("Skip cache invalidation because model link column is negative: {} - {}", model, this);
			return null;
		}

		return queryBL.createQueryBuilder(descriptor.getViewName().getAsString())
				.addEqualsFilter(descriptor.getViewLinkColumnName().getAsString(), linkRecordId);

	}

}
