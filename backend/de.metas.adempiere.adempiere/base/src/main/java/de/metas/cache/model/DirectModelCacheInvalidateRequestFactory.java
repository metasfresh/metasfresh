package de.metas.cache.model;

import java.util.List;

import com.google.common.collect.ImmutableList;

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

public final class DirectModelCacheInvalidateRequestFactory implements ModelCacheInvalidateRequestFactory
{
	public static final transient DirectModelCacheInvalidateRequestFactory instance = new DirectModelCacheInvalidateRequestFactory();

	private DirectModelCacheInvalidateRequestFactory()
	{
	}

	@Override
	public List<CacheInvalidateRequest> createRequestsFromModel(
			final ICacheSourceModel model,
			final ModelCacheInvalidationTiming timing_NOTUSED)
	{
		final int recordId = model.getRecordId();
		if (recordId < 0)
		{
			return ImmutableList.of();
		}

		final String tableName = model.getTableName();
		return ImmutableList.of(CacheInvalidateRequest.rootRecord(tableName, recordId));
	}
}
