package de.metas.cache.model;

import static de.metas.util.Check.isEmpty;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.slf4j.Logger;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.cache.TableNamesGroup;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class WindowBasedCacheInvalidateRequestInitializer
{
	public static void setup()
	{
		new WindowBasedCacheInvalidateRequestInitializer().initialize();
	}

	private static final Logger logger = LogManager.getLogger(WindowBasedCacheInvalidateRequestInitializer.class);
	private final IModelCacheInvalidationService registry = Services.get(IModelCacheInvalidationService.class);

	private WindowBasedCacheInvalidateRequestInitializer()
	{
	}

	private void initialize()
	{
		final ImmutableModelCacheInvalidateRequestFactoryGroup factoryGroup = new ImmutableFactoryGroupBuilder()
				.addAll(retrieveParentChildInfos())
				.build();

		registry.registerFactoryGroup(factoryGroup);
	}

	private final Set<ParentChildInfo> retrieveParentChildInfos()
	{
		final ImmutableSet.Builder<ParentChildInfo> infos = ImmutableSet.builder();
		DB.forEachRow(
				"select * from AD_Window_ParentChildTableNames_v1 order by ParentTableName, ChildTableName",
				ImmutableList.of(),
				rs -> infos.add(retrieveParentChildInfo(rs)));

		return infos.build();
	}

	private ParentChildInfo retrieveParentChildInfo(final ResultSet rs) throws SQLException
	{
		return ParentChildInfo.builder()
				.parentTableName(rs.getString("ParentTableName"))
				.parentNeedsRemoteCacheInvalidation(StringUtils.toBoolean(rs.getString("Parent_Table_IsEnableRemoteCacheInvalidation"), false))
				.childTableName(rs.getString("ChildTableName"))
				.childNeedsRemoteCacheInvalidation(StringUtils.toBoolean(rs.getString("Child_Table_IsEnableRemoteCacheInvalidation"), false))
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
			if (isEmpty(childTableName, true))
			{
				logger.warn("Cannot create parent/child cache invalidate request factory because childTableName is not set: {}", this);
				return null;
			}
			if (isEmpty(childLinkColumnName, true))
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

		public ImmutableModelCacheInvalidateRequestFactoryGroup build()
		{
			return ImmutableModelCacheInvalidateRequestFactoryGroup.builder()
					.factoriesByTableName(factoriesByTableName)
					.tableNamesToEnableRemoveCacheInvalidation(TableNamesGroup.builder()
							.groupId(WindowBasedCacheInvalidateRequestInitializer.class.getSimpleName())
							.tableNames(tableNamesToEnableRemoveCacheInvalidation)
							.build())
					.build();
		}

		public ImmutableFactoryGroupBuilder addAll(@NonNull final Set<ParentChildInfo> parentChildInfos)
		{
			for (final ParentChildInfo info : parentChildInfos)
			{
				add(info);
			}
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
			if (isEmpty(childTableName, true))
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
