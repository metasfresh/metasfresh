package de.metas.cache.model.view_source;

import com.google.common.base.MoreObjects;
import com.google.common.base.Stopwatch;
import com.google.common.collect.HashMultimap;
import de.metas.cache.CCache;
import de.metas.cache.CacheMgt;
import de.metas.cache.TableNamesGroup;
import de.metas.cache.model.IModelCacheInvalidateRequestFactoryGroup;
import de.metas.cache.model.ImmutableModelCacheInvalidateRequestFactoriesList;
import de.metas.cache.model.ModelCacheInvalidateRequestFactory;
import de.metas.cache.model.ModelCacheInvalidationTiming;
import de.metas.cache.model.WindowBasedModelCacheInvalidateRequestFactoryGroup;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.table.api.ViewSourceDescriptor;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_ViewSource;
import org.compiere.model.I_AD_ViewSource_Column;
import org.slf4j.Logger;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Set;

@Component
// NOTE: the only reason we need Adempiere as dependency is that CacheMgt is using "SpringContextHolder.getBean" to get the EventBusFactory,
// By asking for Adempiere dependency here we make sure that the spring context is set to SpringContextHolder
@DependsOn(Adempiere.BEAN_NAME)
public class ViewSourceCacheInvalidateRequestFactoryGroup implements IModelCacheInvalidateRequestFactoryGroup
{
	private static final Logger logger = LogManager.getLogger(ViewSourceCacheInvalidateRequestFactoryGroup.class);
	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final WindowBasedModelCacheInvalidateRequestFactoryGroup windowBasedModelCacheInvalidateRequestFactoryGroup;

	private static final TableNamesGroup CONFIG_TABLENAMES = TableNamesGroup.builder()
			.groupId(ViewSourceCacheInvalidateRequestFactoryGroup.class.getSimpleName() + ".CONFIG")
			.tableName(I_AD_ViewSource.Table_Name)
			.tableName(I_AD_ViewSource_Column.Table_Name)
			.build();

	private static final int DUMMY_CACHE_ID = 0;
	private final CCache<Integer, ImmutableModelCacheInvalidateRequestFactoriesList> cache = CCache.<Integer, ImmutableModelCacheInvalidateRequestFactoriesList>builder()
			.tableName(I_AD_ViewSource.Table_Name)
			.additionalTableNameToResetFor(I_AD_ViewSource_Column.Table_Name)
			.initialCapacity(1)
			.expireMinutes(CCache.EXPIREMINUTES_Never)
			.build();

	public ViewSourceCacheInvalidateRequestFactoryGroup(
			@NonNull final WindowBasedModelCacheInvalidateRequestFactoryGroup windowBasedModelCacheInvalidateRequestFactoryGroup)
	{
		this.windowBasedModelCacheInvalidateRequestFactoryGroup = windowBasedModelCacheInvalidateRequestFactoryGroup;

		CacheMgt.get().enableRemoteCacheInvalidationForTableNamesGroup(CONFIG_TABLENAMES);
		logger.info("Enabled remote cache invalidation for {}", CONFIG_TABLENAMES);
	}

	@Override
	public String toString()
	{
		final ImmutableModelCacheInvalidateRequestFactoriesList delegate = getDelegateIfLoaded();
		return MoreObjects.toStringHelper(this)
				.add("size", delegate != null ? delegate.size() : "NOT LOADED")
				.toString();
	}

	private ImmutableModelCacheInvalidateRequestFactoriesList getDelegate()
	{
		return cache.getOrLoad(DUMMY_CACHE_ID, this::retrieveFromDB);
	}

	@Nullable
	private ImmutableModelCacheInvalidateRequestFactoriesList getDelegateIfLoaded()
	{
		return cache.get(DUMMY_CACHE_ID);
	}

	private ImmutableModelCacheInvalidateRequestFactoriesList retrieveFromDB()
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();

		final HashMultimap<String, ModelCacheInvalidateRequestFactory> factoriesByTableName = HashMultimap.create();

		for (final ViewSourceDescriptor descriptor : adTableDAO.retrieveViewSourceDescriptors())
		{
			final ViewSourceCacheInvalidateRequestFactory factory = ViewSourceCacheInvalidateRequestFactory.builder()
					.queryBL(queryBL)
					.windowBasedModelCacheInvalidateRequestFactoryGroup(windowBasedModelCacheInvalidateRequestFactoryGroup)
					.descriptor(descriptor)
					.build();

			factoriesByTableName.put(factory.getSourceTableName(), factory);
		}

		final ImmutableModelCacheInvalidateRequestFactoriesList result = ImmutableModelCacheInvalidateRequestFactoriesList.builder()
				.factoriesByTableName(factoriesByTableName)
				.tableNamesToEnableRemoveCacheInvalidation(TableNamesGroup.builder()
						.groupId(ViewSourceCacheInvalidateRequestFactoryGroup.class.getSimpleName())
						.tableNames(factoriesByTableName.keySet())
						.build())
				.build();

		stopwatch.stop();
		logger.info("Loaded and initialized in {}: {}", stopwatch, result);

		return result;
	}

	@Override
	public TableNamesGroup getTableNamesToEnableRemoveCacheInvalidation()
	{
		return getDelegate().getTableNamesToEnableRemoveCacheInvalidation();
	}

	@Override
	public Set<ModelCacheInvalidateRequestFactory> getFactoriesByTableName(@NonNull String tableName, @NonNull final ModelCacheInvalidationTiming timing_NOTUSED)
	{
		return getDelegate().getFactoriesByTableName(tableName);
	}
}
