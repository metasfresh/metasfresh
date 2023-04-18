package de.metas.cache.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Stopwatch;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.cache.CacheMgt;
import de.metas.cache.TableNamesGroup;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.table.api.ColumnSqlSourceDescriptor;
import org.adempiere.ad.table.api.IADTableDAO;
import org.compiere.model.I_AD_SQLColumn_SourceTableColumn;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
class ColumnSqlCacheInvalidateRequestFactoryGroup implements IModelCacheInvalidateRequestFactoryGroup
{
	private static final Logger logger = LogManager.getLogger(ColumnSqlCacheInvalidateRequestFactoryGroup.class);

	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
	private final CacheMgt cacheMgt = CacheMgt.get();

	private static final TableNamesGroup CONFIG_TABLENAMES = TableNamesGroup.builder()
			.groupId(ColumnSqlCacheInvalidateRequestFactoryGroup.class.getSimpleName() + ".CONFIG")
			.tableName(I_AD_SQLColumn_SourceTableColumn.Table_Name)
			.build();
	private static final String TABLENAMES_GROUP_FACTORY_TABLES = ColumnSqlCacheInvalidateRequestFactoryGroup.class.getSimpleName();
	private static final int DUMMY_CACHE_ID = 0;
	private final CCache<Integer, ImmutableModelCacheInvalidateRequestFactoriesList> cache = CCache.<Integer, ImmutableModelCacheInvalidateRequestFactoriesList>builder()
			.tableName(I_AD_SQLColumn_SourceTableColumn.Table_Name)
			.initialCapacity(1)
			.expireMinutes(CCache.EXPIREMINUTES_Never)
			.build();

	public ColumnSqlCacheInvalidateRequestFactoryGroup()
	{
	}

	public String toString()
	{
		final ImmutableModelCacheInvalidateRequestFactoriesList delegate = getDelegateIfLoaded();
		return MoreObjects.toStringHelper(this)
				.add("size", delegate != null ? delegate.size() : "NOT LOADED")
				.toString();
	}

	private ImmutableModelCacheInvalidateRequestFactoriesList getDelegate()
	{
		return cache.getOrLoad(DUMMY_CACHE_ID, this::loadAndInit);
	}

	@Nullable
	private ImmutableModelCacheInvalidateRequestFactoriesList getDelegateIfLoaded()
	{
		return cache.get(DUMMY_CACHE_ID);
	}

	private ImmutableModelCacheInvalidateRequestFactoriesList loadAndInit()
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();

		final ImmutableModelCacheInvalidateRequestFactoriesList actualFactoryGroup = new ImmutableFactoryGroupBuilder()
				.addAll(adTableDAO.retrieveColumnSqlSourceDescriptors())
				.build();

		// NOTE: we have to do this right-away in case of a configuration change
		cacheMgt.enableRemoteCacheInvalidationForTableNamesGroup(CONFIG_TABLENAMES);
		logger.info("Enabled remote cache invalidation for {}", CONFIG_TABLENAMES);
		cacheMgt.enableRemoteCacheInvalidationForTableNamesGroup(actualFactoryGroup.getTableNamesToEnableRemoveCacheInvalidation());
		logger.info("Enabled remote cache invalidation for {}", actualFactoryGroup.getTableNamesToEnableRemoveCacheInvalidation());

		stopwatch.stop();
		logger.info("Loaded and initialized in {}: {}", stopwatch, actualFactoryGroup);

		return actualFactoryGroup;
	}

	@Override
	public TableNamesGroup getTableNamesToEnableRemoveCacheInvalidation()
	{
		return getDelegate().getTableNamesToEnableRemoveCacheInvalidation();
	}

	@Override
	public Set<ModelCacheInvalidateRequestFactory> getFactoriesByTableName(@NonNull String tableName, @NonNull final ModelCacheInvalidationTiming timing)
	{
		// Provide factories only for AFTER events.
		// BEFORE events are useful for a few special cases (e.g. implementations that have to invalidate/refresh a live database view)
		if (!timing.isAfter())
		{
			return ImmutableSet.of();
		}

		return getDelegate().getFactoriesByTableName(tableName);
	}

	//
	//
	//
	//
	//

	private static class ImmutableFactoryGroupBuilder
	{
		private final HashSet<String> tableNamesToEnableRemoveCacheInvalidation = new HashSet<>();
		private final HashMultimap<String, ModelCacheInvalidateRequestFactory> factoriesByTableName = HashMultimap.create();

		public ImmutableModelCacheInvalidateRequestFactoriesList build()
		{
			return ImmutableModelCacheInvalidateRequestFactoriesList.builder()
					.factoriesByTableName(factoriesByTableName)
					.tableNamesToEnableRemoveCacheInvalidation(TableNamesGroup.builder()
							.groupId(TABLENAMES_GROUP_FACTORY_TABLES)
							.tableNames(tableNamesToEnableRemoveCacheInvalidation)
							.build())
					.build();
		}

		public ImmutableFactoryGroupBuilder addAll(final Collection<ColumnSqlSourceDescriptor> descriptors)
		{
			for (final ColumnSqlSourceDescriptor descriptor : descriptors)
			{
				add(descriptor);
			}

			return this;
		}

		public ImmutableFactoryGroupBuilder add(final ColumnSqlSourceDescriptor descriptor)
		{
			tableNamesToEnableRemoveCacheInvalidation.add(descriptor.getSourceTableName());
			tableNamesToEnableRemoveCacheInvalidation.add(descriptor.getTargetTableName());

			factoriesByTableName.put(descriptor.getSourceTableName(), ColumnSqlCacheInvalidateRequestFactories.ofDescriptor(descriptor));

			return this;
		}
	}

}
