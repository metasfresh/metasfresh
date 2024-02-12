package de.metas.cache;

import org.adempiere.util.lang.impl.TableRecordReference;

import java.util.Collection;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

/**
 * Implementations should be thread-safe; so far, no implementation had to have any state, so I hope that won't be a problem.
 */
public interface CacheIndexDataAdapter<DataItemId, CacheKey, DataItem>
{
	DataItemId extractDataItemId(DataItem dataItem);

	Collection<CacheKey> extractCacheKeys(DataItem dataItem);

	/**
	 * @return all data records that make up the given dataIdem.
	 * Needed in order to know on which record-changes we would need to invalidate the given dataItem within the cache.
	 */
	Collection<TableRecordReference> extractRecordRefs(DataItem dataItem);

	default boolean isResetAll(final TableRecordReference recordRef)
	{
		return false;
	}
}
