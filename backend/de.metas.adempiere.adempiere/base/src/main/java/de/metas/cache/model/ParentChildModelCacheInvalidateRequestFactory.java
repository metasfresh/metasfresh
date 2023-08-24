package de.metas.cache.model;

import com.google.common.collect.ImmutableList;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.List;

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

@EqualsAndHashCode
@ToString
final class ParentChildModelCacheInvalidateRequestFactory implements ModelCacheInvalidateRequestFactory
{
	private final String rootTableName;
	private final String childTableName;
	private final String childKeyColumnNameOrNull;
	private final String childLinkColumnName;

	@Builder
	private ParentChildModelCacheInvalidateRequestFactory(
			@NonNull final String rootTableName,
			@NonNull final String childTableName,
			@Nullable final String childKeyColumnName,
			@NonNull final String childLinkColumnName)
	{
		this.rootTableName = rootTableName;
		this.childTableName = childTableName;
		this.childKeyColumnNameOrNull = StringUtils.trimBlankToNull(childKeyColumnName);
		this.childLinkColumnName = childLinkColumnName;
	}

	@Override
	public List<CacheInvalidateRequest> createRequestsFromModel(
			final ICacheSourceModel model,
			final ModelCacheInvalidationTiming timing_NOTUSED)
	{
		final int rootRecordId = model.getValueAsInt(childLinkColumnName, -1);
		if (rootRecordId < 0)
		{
			return ImmutableList.of();
		}

		final int childRecordId = childKeyColumnNameOrNull != null
				? model.getValueAsInt(childKeyColumnNameOrNull, -1)
				: -1;
		if (childRecordId >= 0)
		{
			return ImmutableList.of(CacheInvalidateRequest.builder()
					.rootRecord(rootTableName, rootRecordId)
					.childRecord(childTableName, childRecordId)
					.build());
		}
		else
		{
			return ImmutableList.of(CacheInvalidateRequest.rootRecord(rootTableName, rootRecordId));
		}
	}
}
