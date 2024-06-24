package de.metas.cache.model;

 import com.google.common.base.MoreObjects;
import com.google.common.base.Stopwatch;
import com.google.common.collect.HashMultimap;
import de.metas.cache.CCache;
import de.metas.cache.CacheMgt;
import de.metas.cache.TableNamesGroup;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.table.api.ColumnSqlSourceDescriptor;
import org.adempiere.ad.table.api.IADTableDAO;
import org.compiere.model.I_AD_SQLColumn_SourceTableColumn;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class ColumnSqlCacheInvalidateRequestInitializer
{
	public static void setup()
	{
		new ColumnSqlCacheInvalidateRequestInitializer().initialize();
	}

	private static final String TABLENAMES_GROUP_CONFIG = ColumnSqlCacheInvalidateRequestInitializer.class.getSimpleName() + ".CONFIG";
	private static final String TABLENAMES_GROUP_FACTORY_TABLES = ColumnSqlCacheInvalidateRequestInitializer.class.getSimpleName();

	private static final Logger logger = LogManager.getLogger(ColumnSqlCacheInvalidateRequestInitializer.class);
	private final IModelCacheInvalidationService registry = Services.get(IModelCacheInvalidationService.class);
	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
	private final CacheMgt cacheMgt = CacheMgt.get();

	private ColumnSqlCacheInvalidateRequestInitializer()
	{
	}

	private void initialize()
	{
		cacheMgt.enableRemoteCacheInvalidationForTableNamesGroup(TableNamesGroup.builder()
				.groupId(TABLENAMES_GROUP_CONFIG)
				.tableName(I_AD_SQLColumn_SourceTableColumn.Table_Name)
				.build());

		final ColumnSqlFactoryGroup factoryGroup = ColumnSqlFactoryGroup.builder()
				.adTableDAO(adTableDAO)
				.cacheMgt(cacheMgt)
				.build();
		factoryGroup.getActualFactoryGroup(); // load it eagerly

		registry.registerFactoryGroup(factoryGroup);
	}

	private static class ColumnSqlFactoryGroup implements IModelCacheInvalidateRequestFactoryGroup
	{
		private static final Logger logger = LogManager.getLogger(ColumnSqlFactoryGroup.class);
		private final IADTableDAO adTableDAO;
		private final CacheMgt cacheMgt;

		private static final int DUMMY_CACHE_ID = 0;
		private final CCache<Integer, ImmutableModelCacheInvalidateRequestFactoryGroup> cache = CCache.<Integer, ImmutableModelCacheInvalidateRequestFactoryGroup>builder()
				.tableName(I_AD_SQLColumn_SourceTableColumn.Table_Name)
				.initialCapacity(1)
				.expireMinutes(CCache.EXPIREMINUTES_Never)
				.build();

		@Builder
		private ColumnSqlFactoryGroup(
				@NonNull final IADTableDAO adTableDAO,
				@NonNull final CacheMgt cacheMgt)
		{
			this.adTableDAO = adTableDAO;
			this.cacheMgt = cacheMgt;
		}

		public String toString()
		{
			final ImmutableModelCacheInvalidateRequestFactoryGroup actualFactoryGroup = cache.get(DUMMY_CACHE_ID);
			return MoreObjects.toStringHelper(this)
					.addValue(actualFactoryGroup != null ? actualFactoryGroup : "NOT LOADED")
					.toString();
		}

		private ImmutableModelCacheInvalidateRequestFactoryGroup getActualFactoryGroup()
		{
			return cache.getOrLoad(DUMMY_CACHE_ID, this::loadAndInit);
		}

		private ImmutableModelCacheInvalidateRequestFactoryGroup loadAndInit()
		{
			final Stopwatch stopwatch = Stopwatch.createStarted();

			final ImmutableModelCacheInvalidateRequestFactoryGroup actualFactoryGroup = new ImmutableFactoryGroupBuilder()
					.addAll(adTableDAO.retrieveColumnSqlSourceDescriptors())
					.build();

			// NOTE: we have to do this right-away in case of a configuration change
			cacheMgt.enableRemoteCacheInvalidationForTableNamesGroup(actualFactoryGroup.getTableNamesToEnableRemoveCacheInvalidation());
			logger.info("Enabled remote cache invalidation for {}", actualFactoryGroup.getTableNamesToEnableRemoveCacheInvalidation());

			stopwatch.stop();
			logger.info("Loaded and initialized in {}: {}", stopwatch, actualFactoryGroup);

			return actualFactoryGroup;
		}

		@Override
		public TableNamesGroup getTableNamesToEnableRemoveCacheInvalidation()
		{
			return getActualFactoryGroup().getTableNamesToEnableRemoveCacheInvalidation();
		}

		@Override
		public Set<ModelCacheInvalidateRequestFactory> getFactoriesByTableName(@NonNull String tableName)
		{
			return getActualFactoryGroup().getFactoriesByTableName(tableName);
		}

	}

	private static class ImmutableFactoryGroupBuilder
	{
		private final HashSet<String> tableNamesToEnableRemoveCacheInvalidation = new HashSet<>();
		private final HashMultimap<String, ModelCacheInvalidateRequestFactory> factoriesByTableName = HashMultimap.create();

		public ImmutableModelCacheInvalidateRequestFactoryGroup build()
		{
			return ImmutableModelCacheInvalidateRequestFactoryGroup.builder()
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

		@SuppressWarnings("UnusedReturnValue")
		public ImmutableFactoryGroupBuilder add(final ColumnSqlSourceDescriptor descriptor)
		{
			final ModelCacheInvalidateRequestFactory requestFactory = createRequestFactoryNoFail(descriptor);
			if (requestFactory != null)
			{
				tableNamesToEnableRemoveCacheInvalidation.add(descriptor.getSourceTableName());
				tableNamesToEnableRemoveCacheInvalidation.add(descriptor.getTargetTableName());
				factoriesByTableName.put(descriptor.getSourceTableName(), requestFactory);
			}

			return this;
		}

		@Nullable
		private static ModelCacheInvalidateRequestFactory createRequestFactoryNoFail(final ColumnSqlSourceDescriptor descriptor)
		{
			try
			{
				return ColumnSqlCacheInvalidateRequestFactories.ofDescriptorOrNull(descriptor);
			}
			catch (Exception ex)
			{
				logger.warn("Failed creating {} from {}. Ignored.", ModelCacheInvalidateRequestFactory.class, descriptor, ex);
				return null;
			}
		}
	}
}
