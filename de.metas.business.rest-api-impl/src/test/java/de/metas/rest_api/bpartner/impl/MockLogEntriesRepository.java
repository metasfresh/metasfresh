package de.metas.rest_api.bpartner.impl;

import java.util.List;

import org.adempiere.ad.table.LogEntriesRepository;
import org.adempiere.ad.table.RecordChangeLogEntry;
import org.adempiere.util.lang.impl.TableRecordReference;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder.ListMultimapBuilder;

import lombok.NonNull;

/*
 * #%L
 * de.metas.business.rest-api-impl
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

public class MockLogEntriesRepository implements LogEntriesRepository
{
	private final ListMultimap<TableRecordReference, RecordChangeLogEntry> returnValues = ListMultimapBuilder.hashKeys().arrayListValues().build();

	@Override
	public ImmutableListMultimap<TableRecordReference, RecordChangeLogEntry> getLogEntriesForRecordReferences(
			@NonNull final LogEntriesQuery logEntriesQuery)
	{
		final ImmutableListMultimap.Builder<TableRecordReference, RecordChangeLogEntry> result = ImmutableListMultimap.builder();

		for (final TableRecordReference tableRecordReference : logEntriesQuery.getTableRecordReferences())
		{
			final List<RecordChangeLogEntry> logEntries = returnValues.get(tableRecordReference);
			result.putAll(tableRecordReference, logEntries);
		}

		return result.build();
	}

	public void add(
			@NonNull final TableRecordReference tableRecordReference,
			@NonNull final RecordChangeLogEntry recordChangeLogEntry)
	{
		returnValues.put(tableRecordReference, recordChangeLogEntry);
	}
}
