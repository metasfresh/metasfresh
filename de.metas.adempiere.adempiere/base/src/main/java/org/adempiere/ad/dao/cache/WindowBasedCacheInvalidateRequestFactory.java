package org.adempiere.ad.dao.cache;

import javax.annotation.Nullable;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.util.DB;

import com.google.common.collect.ImmutableList;

import lombok.Builder;
import lombok.NonNull;

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

public class WindowBasedCacheInvalidateRequestFactory implements ModelCacheInvalidateRequestFactory
{
	public static final transient WindowBasedCacheInvalidateRequestFactory instance = new WindowBasedCacheInvalidateRequestFactory();

	private WindowBasedCacheInvalidateRequestFactory()
	{
	}

	private static final void retrieve()
	{
		final String sql = "select * from AD_Window_ParentChildTableNames_v1";
		DB.forEachRow(sql, ImmutableList.of(), rs -> {
			final String parentTableName = rs.getString("ParentTableName");
			final String childTableName = rs.getString("ChildTableName");
			final String childLinkColumnName = rs.getString("ChildLinkColumnName");
			create(parentTableName, childTableName, childLinkColumnName);
		});
	}

	private static void create(final String parentTableName, final String childTableName, final String childLinkColumnName)
	{
		if (Check.isEmpty(childLinkColumnName))
		{
			return;
		}
		// TODO Auto-generated method stub

	}

	@Override
	public CacheInvalidateRequest createRequestFromModel(final Object model, final ModelCacheInvalidationTiming timing)
	{
		// TODO Auto-generated method stub
		return null;
	}

	private static class GenericModelCacheInvalidateRequestFactory implements ModelCacheInvalidateRequestFactory
	{
		private String rootTableName;
		private String childTableName;
		private String childKeyColumnName;
		private String childLinkColumnName;

		@Builder
		private GenericModelCacheInvalidateRequestFactory(
				@NonNull final String rootTableName,
				@NonNull final String childTableName,
				@Nullable final String childKeyColumnName,
				@NonNull final String childLinkColumnName)
		{
			this.rootTableName = rootTableName;
			this.childTableName = childTableName;
			this.childKeyColumnName = !Check.isEmpty(childKeyColumnName, true) ? childKeyColumnName : null;
			this.childLinkColumnName = childLinkColumnName;
		}

		@Override
		public CacheInvalidateRequest createRequestFromModel(final Object model, final ModelCacheInvalidationTiming timing)
		{
			final int rootRecordId = getValueAsInt(model, childLinkColumnName);
			if (rootRecordId < 0)
			{
				return null;
			}

			final int childRecordId = getValueAsInt(model, childKeyColumnName);
			if (childRecordId >= 0)
			{
				return CacheInvalidateRequest.builder()
						.rootRecord(rootTableName, rootRecordId)
						.childRecord(childTableName, childRecordId)
						.build();
			}
			else
			{
				return CacheInvalidateRequest.rootRecord(rootTableName, rootRecordId);
			}
		}

		private static final int getValueAsInt(@NonNull final Object model, @Nullable final String columnName)
		{
			if (columnName == null)
			{
				return -1;
			}
			final Object valueObj = InterfaceWrapperHelper.getValueOrNull(model, columnName);
			if (valueObj == null)
			{
				return -1;
			}
			else if (valueObj instanceof Integer)
			{
				return ((Integer)valueObj).intValue();
			}
			else
			{
				return -1;
			}
		}
	}

}
