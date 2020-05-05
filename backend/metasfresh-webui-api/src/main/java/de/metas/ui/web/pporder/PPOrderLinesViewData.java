package de.metas.ui.web.pporder;

import java.util.List;
import java.util.stream.Stream;

import javax.annotation.concurrent.Immutable;

import org.eevolution.api.PPOrderPlanningStatus;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * Contains {@link PPOrderLinesView}'s data
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Immutable
/* package */final class PPOrderLinesViewData
{
	private final ITranslatableString description;
	private final PPOrderPlanningStatus planningStatus;

	/** Top level records list */
	private final ImmutableList<PPOrderLineRow> records;
	/** All records (included ones too) indexed by DocumentId */
	private final ImmutableMap<DocumentId, PPOrderLineRow> allRecordsById;

	PPOrderLinesViewData(
			@NonNull final ITranslatableString description,
			@NonNull final PPOrderPlanningStatus planningStatus,
			@NonNull final List<PPOrderLineRow> records)
	{
		this.description = description;
		this.planningStatus = planningStatus;
		this.records = ImmutableList.copyOf(records);

		allRecordsById = buildRecordsByIdMap(this.records);
	}

	public ITranslatableString getDescription()
	{
		return description;
	}

	public PPOrderPlanningStatus getPlanningStatus()
	{
		return planningStatus;
	}

	public PPOrderLineRow getById(final PPOrderLineRowId rowId)
	{

		final DocumentId documentId = rowId.toDocumentId();

		final PPOrderLineRow row = allRecordsById.get(documentId);
		if (row == null)
		{
			throw new EntityNotFoundException("No document found for rowId=" + rowId);
		}
		return row;
	}

	public Stream<PPOrderLineRow> streamByIds(final DocumentIdsSelection documentIds)
	{
		if (documentIds == null || documentIds.isEmpty())
		{
			return Stream.empty();
		}

		if (documentIds.isAll())
		{
			return records.stream();
		}

		return documentIds.stream()
				.distinct()
				.map(documentId -> allRecordsById.get(documentId))
				.filter(document -> document != null);
	}

	public Stream<PPOrderLineRow> stream()
	{
		return records.stream();
	}

	public Stream<PPOrderLineRow> streamRecursive()
	{
		return records.stream()
				.map(row -> streamRecursive(row))
				.reduce(Stream::concat)
				.orElse(Stream.of());
	}

	private static Stream<PPOrderLineRow> streamRecursive(final PPOrderLineRow row)
	{
		return row.getIncludedRows()
				.stream()
				.map(includedRow -> streamRecursive(includedRow))
				.reduce(Stream.of(row), Stream::concat);
	}

	public long size()
	{
		return records.size();
	}

	private static ImmutableMap<DocumentId, PPOrderLineRow> buildRecordsByIdMap(final List<PPOrderLineRow> rows)
	{
		if (rows.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableMap.Builder<DocumentId, PPOrderLineRow> rowsById = ImmutableMap.builder();
		rows.forEach(row -> indexByIdRecursively(rowsById, row));
		return rowsById.build();
	}

	private static final void indexByIdRecursively(final ImmutableMap.Builder<DocumentId, PPOrderLineRow> collector, final PPOrderLineRow row)
	{
		collector.put(row.getRowId().toDocumentId(), row);
		row.getIncludedRows()
				.forEach(includedRow -> indexByIdRecursively(collector, includedRow));
	}
}
