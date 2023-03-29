package de.metas.cache.model;

import com.google.common.base.MoreObjects;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.cache.TableNamesGroup;
import de.metas.logging.LogManager;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static de.metas.util.Check.isBlank;

@Component
public class WindowBasedModelCacheInvalidateRequestFactoryGroup implements IModelCacheInvalidateRequestFactoryGroup
{
	private static final Logger logger = LogManager.getLogger(WindowBasedModelCacheInvalidateRequestFactoryGroup.class);

	private static final int DUMMY_CACHE_ID = 0;
	private final CCache<Integer, ImmutableModelCacheInvalidateRequestFactoriesList> cache = CCache.<Integer, ImmutableModelCacheInvalidateRequestFactoriesList>builder()
			.initialCapacity(1)
			.cacheName("WindowBasedModelCacheInvalidateRequestFactoryGroup.cache")
			// NOTE: don't reset on each window/tab/table change because that's too. React just on full cache reset.
			// .tableName(I_AD_Window.Table_Name)
			// .additionalTableNameToResetFor(I_AD_Tab.Table_Name)
			// .additionalTableNameToResetFor(I_AD_Table.Table_Name)
			.build();

	public String toString()
	{
		final ImmutableModelCacheInvalidateRequestFactoriesList delegate = getDelegateIfLoaded();
		return MoreObjects.toStringHelper(this)
				.addValue(delegate != null ? delegate : "NOT LOADED")
				.toString();
	}

	@Override
	public TableNamesGroup getTableNamesToEnableRemoveCacheInvalidation()
	{
		return getDelegate().getTableNamesToEnableRemoveCacheInvalidation();
	}

	@Override
	public Set<ModelCacheInvalidateRequestFactory> getFactoriesByTableName(@NonNull final String tableName, @NonNull final ModelCacheInvalidationTiming timing)
	{
		// Provide factories only for AFTER events.
		// BEFORE events are useful for a few special cases (e.g. implementations that have to invalidate/refresh a live database view)
		if (!timing.isAfter())
		{
			return ImmutableSet.of();
		}

		return getFactoriesByTableName(tableName);
	}

	public Set<ModelCacheInvalidateRequestFactory> getFactoriesByTableName(@NonNull final String tableName)
	{
		return getDelegate().getFactoriesByTableName(tableName);
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
		return new ImmutableFactoryGroupBuilder()
				.addAll(retrieveParentChildInfos())
				.build();
	}

	private ImmutableSet<ParentChildInfo> retrieveParentChildInfos()
	{
		final ImmutableSet.Builder<ParentChildInfo> infos = ImmutableSet.builder();
		DB.forEachRow(
				"select * from AD_Window_ParentChildTableNames_v1 order by ParentTableName, ChildTableName",
				ImmutableList.of(),
				rs -> infos.add(retrieveParentChildInfo(rs)));

		return infos.build();
	}

	private static ParentChildInfo retrieveParentChildInfo(final ResultSet rs) throws SQLException
	{
		return ParentChildInfo.builder()
				.parentTableName(rs.getString("ParentTableName"))
				.parentNeedsRemoteCacheInvalidation(StringUtils.toBoolean(rs.getString("Parent_Table_IsEnableRemoteCacheInvalidation")))
				.childTableName(rs.getString("ChildTableName"))
				.childNeedsRemoteCacheInvalidation(StringUtils.toBoolean(rs.getString("Child_Table_IsEnableRemoteCacheInvalidation")))
				.childKeyColumnName(rs.getString("ChildKeyColumnName"))
				.childLinkColumnName(rs.getString("ChildLinkColumnName"))
				.build();
	}

	@Value
	@Builder
	private static class ParentChildInfo
	{
		@NonNull
		String parentTableName;

		boolean parentNeedsRemoteCacheInvalidation;

		@Nullable
		String childTableName;

		boolean childNeedsRemoteCacheInvalidation;

		@Nullable
		String childKeyColumnName;

		@Nullable
		String childLinkColumnName;

		private ParentChildModelCacheInvalidateRequestFactory toGenericModelCacheInvalidateRequestFactoryOrNull()
		{
			if (childTableName == null || isBlank(childTableName))
			{
				logger.warn("Cannot create parent/child cache invalidate request factory because childTableName is not set: {}", this);
				return null;
			}
			if (childLinkColumnName == null || isBlank(childLinkColumnName))
			{
				logger.warn("Cannot create parent/child cache invalidate request factory because childLinkColumnName is not set: {}", this);
				return null;
			}

			try
			{
				return ParentChildModelCacheInvalidateRequestFactory.builder()
						.rootTableName(parentTableName)
						.childTableName(childTableName)
						.childKeyColumnName(childKeyColumnName)
						.childLinkColumnName(childLinkColumnName)
						.build();
			}
			catch (final Exception ex)
			{
				throw new AdempiereException("Failed creating " + ParentChildModelCacheInvalidateRequestFactory.class.getSimpleName() + " for " + this, ex);
			}
		}
	}

	private static class ImmutableFactoryGroupBuilder
	{
		private final HashSet<String> tableNamesToEnableRemoveCacheInvalidation = new HashSet<>();
		private final HashMultimap<String, ModelCacheInvalidateRequestFactory> factoriesByTableName = HashMultimap.create();

		public ImmutableModelCacheInvalidateRequestFactoriesList build()
		{
			return ImmutableModelCacheInvalidateRequestFactoriesList.builder()
					.factoriesByTableName(factoriesByTableName)
					.tableNamesToEnableRemoveCacheInvalidation(
							TableNamesGroup.builder()
									.groupId(WindowBasedModelCacheInvalidateRequestFactoryGroup.class.getSimpleName())
									.tableNames(tableNamesToEnableRemoveCacheInvalidation)
									.build())
					.build();
		}

		public ImmutableFactoryGroupBuilder addAll(@NonNull final Set<ParentChildInfo> parentChildInfos)
		{
			parentChildInfos.forEach(this::add);
			return this;
		}

		public ImmutableFactoryGroupBuilder add(@NonNull final ParentChildInfo info)
		{
			addForParentTable(info);
			addForChildTable(info);
			return this;
		}

		private void addForParentTable(final ParentChildInfo info)
		{
			final String parentTableName = info.getParentTableName();

			factoriesByTableName.put(parentTableName, DirectModelCacheInvalidateRequestFactory.instance);

			// NOTE: always invalidate parent table name, even if info.isParentNeedsRemoteCacheInvalidation() is false
			tableNamesToEnableRemoveCacheInvalidation.add(parentTableName);
		}

		private void addForChildTable(final ParentChildInfo info)
		{
			final String childTableName = info.getChildTableName();
			if (childTableName == null || isBlank(childTableName))
			{
				return;
			}

			try
			{
				final ParentChildModelCacheInvalidateRequestFactory factory = info.toGenericModelCacheInvalidateRequestFactoryOrNull();
				if (factory != null)
				{
					factoriesByTableName.put(childTableName, factory);
				}
			}
			catch (final Exception ex)
			{
				logger.warn("Failed to create model cache invalidate for {}: {}", childTableName, info, ex);
			}

			if (info.isChildNeedsRemoteCacheInvalidation())
			{
				tableNamesToEnableRemoveCacheInvalidation.add(childTableName);
			}
		}
	}

}
