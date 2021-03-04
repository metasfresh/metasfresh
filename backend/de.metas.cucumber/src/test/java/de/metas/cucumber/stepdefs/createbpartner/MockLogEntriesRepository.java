/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.cucumber.stepdefs.createbpartner;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import lombok.NonNull;
import org.adempiere.ad.table.LogEntriesRepository;
import org.adempiere.ad.table.RecordChangeLogEntry;
import org.adempiere.util.lang.impl.TableRecordReference;

import java.util.List;

public class MockLogEntriesRepository implements LogEntriesRepository
{
	private final ListMultimap<TableRecordReference, RecordChangeLogEntry> returnValues = MultimapBuilder.ListMultimapBuilder.hashKeys().arrayListValues().build();

	@Override
	public ImmutableListMultimap<TableRecordReference, RecordChangeLogEntry> getLogEntriesForRecordReferences(
			@NonNull final LogEntriesRepository.LogEntriesQuery logEntriesQuery)
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
