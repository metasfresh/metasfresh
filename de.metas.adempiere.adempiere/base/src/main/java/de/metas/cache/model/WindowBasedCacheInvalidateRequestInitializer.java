package de.metas.cache.model;

import static de.metas.util.Check.isEmpty;

import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.cache.CacheMgt;
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
	public static final transient WindowBasedCacheInvalidateRequestInitializer instance = new WindowBasedCacheInvalidateRequestInitializer();

	private static final Logger logger = LogManager.getLogger(WindowBasedCacheInvalidateRequestInitializer.class);

	private WindowBasedCacheInvalidateRequestInitializer()
	{
	}

	public void initialize()
	{
		final IModelCacheInvalidationService registry = Services.get(IModelCacheInvalidationService.class);
		final CacheMgt cacheMgt = CacheMgt.get();

		final Set<ParentChildInfo> parentChildInfos = retrieveParentChildInfos();
		logger.info("Found {} parentChildInfo instances to be registered", parentChildInfos.size());

		for (final ParentChildInfo info : parentChildInfos)
		{
			// parent
			final String parentTableName = info.getParentTableName();

			registry.register(parentTableName, DirectModelCacheInvalidateRequestFactory.instance);
			if (info.isParentNeedsRemoteCacheInvalidation())
			{
				cacheMgt.enableRemoteCacheInvalidationForTableName(parentTableName);
			}

			// child
			final String childTableName = info.getChildTableName();
			if (isEmpty(childTableName, true))
			{
				continue; // no child => are done
			}

			try
			{
				registry.register(childTableName, info.toGenericModelCacheInvalidateRequestFactory());
			}
			catch (final Exception ex)
			{
				logger.warn("Failed registering model cache invalidate for {}: {}", childTableName, info, ex);
			}

			if (info.isChildNeedsRemoteCacheInvalidation())
			{
				cacheMgt.enableRemoteCacheInvalidationForTableName(childTableName);
			}
		}

	}

	private final Set<ParentChildInfo> retrieveParentChildInfos()
	{
		final ImmutableSet.Builder<ParentChildInfo> infos = ImmutableSet.builder();
		DB.forEachRow(
				"select * from AD_Window_ParentChildTableNames_v1",
				ImmutableList.of(),
				rs -> infos.add(ParentChildInfo.builder()
						.parentTableName(rs.getString("ParentTableName"))
						.parentNeedsRemoteCacheInvalidation(StringUtils.toBoolean(rs.getString("Parent_Table_IsEnableRemoteCacheInvalidation"), false))
						.childTableName(rs.getString("ChildTableName"))
						.childNeedsRemoteCacheInvalidation(StringUtils.toBoolean(rs.getString("Child_Table_IsEnableRemoteCacheInvalidation"), false))
						.childKeyColumnName(rs.getString("ChildKeyColumnName"))
						.childLinkColumnName(rs.getString("ChildLinkColumnName"))
						.build()));

		return infos.build();
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

		private GenericModelCacheInvalidateRequestFactory toGenericModelCacheInvalidateRequestFactory()
		{
			try
			{
				return GenericModelCacheInvalidateRequestFactory.builder()
						.rootTableName(parentTableName)
						.childTableName(childTableName)
						.childKeyColumnName(childKeyColumnName)
						.childLinkColumnName(childLinkColumnName)
						.build();
			}
			catch (final Exception ex)
			{
				throw new AdempiereException("Failed creating " + GenericModelCacheInvalidateRequestFactory.class.getSimpleName() + " for " + this, ex);
			}
		}
	}
}
