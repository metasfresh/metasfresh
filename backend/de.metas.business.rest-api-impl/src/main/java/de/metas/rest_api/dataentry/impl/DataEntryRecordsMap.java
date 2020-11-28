package de.metas.rest_api.dataentry.impl;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.dataentry.DataEntrySubTabId;
import de.metas.dataentry.data.DataEntryRecord;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.business.rest-api
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

@ToString
final class DataEntryRecordsMap
{
	public static DataEntryRecordsMap of(@NonNull final Collection<DataEntryRecord> records)
	{
		if (records.isEmpty())
		{
			return EMPTY;
		}

		return new DataEntryRecordsMap(records);
	}

	private static final DataEntryRecordsMap EMPTY = new DataEntryRecordsMap();

	private final ImmutableMap<DataEntrySubTabId, DataEntryRecord> map;

	private DataEntryRecordsMap(@NonNull final Collection<DataEntryRecord> records)
	{
		map = Maps.uniqueIndex(records, DataEntryRecord::getDataEntrySubTabId);
	}

	private DataEntryRecordsMap()
	{
		map = ImmutableMap.of();
	}

	public Set<DataEntrySubTabId> getSubTabIds()
	{
		return map.keySet();
	}

	public Optional<DataEntryRecord> getBySubTabId(final DataEntrySubTabId id)
	{
		final DataEntryRecord record = map.get(id);
		return Optional.ofNullable(record);
	}

}
