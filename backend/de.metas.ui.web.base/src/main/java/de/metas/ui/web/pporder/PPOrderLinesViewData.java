package de.metas.ui.web.pporder;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.view.ViewHeaderProperties;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.eevolution.api.PPOrderPlanningStatus;

import javax.annotation.concurrent.Immutable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

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
 */
@Immutable
final class PPOrderLinesViewData
{
	@Getter
	private final ITranslatableString description;
	@Getter
	private final PPOrderPlanningStatus planningStatus;

	private final ImmutableList<PPOrderLineRow> topLevelRows;
	@Getter
	private final PPOrderLineRow finishedGoodRow;

	@Getter
	private final ViewHeaderProperties headerProperties;

	/**
	 * All records (included ones too) indexed by DocumentId
	 */
	private final ImmutableMap<DocumentId, PPOrderLineRow> allRowsById;

	@Builder
	private PPOrderLinesViewData(
			@NonNull final ITranslatableString description,
			@NonNull final PPOrderPlanningStatus planningStatus,
			@NonNull final PPOrderLineRow finishedGoodRow,
			@NonNull final List<PPOrderLineRow> bomLineRows,
			@NonNull final List<PPOrderLineRow> sourceHURows,
			@NonNull final ViewHeaderProperties headerProperties)
	{
		this.description = description;
		this.planningStatus = planningStatus;
		this.finishedGoodRow = finishedGoodRow;
		this.headerProperties = headerProperties;

		this.topLevelRows = ImmutableList.<PPOrderLineRow>builder()
				.add(finishedGoodRow)
				.addAll(bomLineRows)
				.addAll(sourceHURows)
				.build();

		allRowsById = buildRecordsByIdMap(this.topLevelRows);
	}

	private static ImmutableMap<DocumentId, PPOrderLineRow> buildRecordsByIdMap(@NonNull final List<PPOrderLineRow> rows)
	{
		if (rows.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableMap.Builder<DocumentId, PPOrderLineRow> rowsById = ImmutableMap.builder();
		rows.forEach(row -> indexByIdRecursively(rowsById, row));
		return rowsById.build();
	}

	private static void indexByIdRecursively(
			@NonNull final ImmutableMap.Builder<DocumentId, PPOrderLineRow> collector,
			@NonNull final PPOrderLineRow row)
	{
		collector.put(row.getRowId().toDocumentId(), row);
		row.getIncludedRows().forEach(includedRow -> indexByIdRecursively(collector, includedRow));
	}

	public PPOrderLineRow getById(@NonNull final PPOrderLineRowId rowId)
	{
		final DocumentId documentId = rowId.toDocumentId();

		final PPOrderLineRow row = allRowsById.get(documentId);
		if (row == null)
		{
			throw new EntityNotFoundException("No document found for rowId=" + rowId);
		}
		return row;
	}

	public Stream<PPOrderLineRow> streamByIds(@NonNull final DocumentIdsSelection documentIds)
	{
		if (documentIds == null || documentIds.isEmpty())
		{
			return Stream.empty();
		}
		else if (documentIds.isAll())
		{
			return topLevelRows.stream();
		}
		else
		{
			return documentIds.stream()
					.distinct()
					.map(allRowsById::get)
					.filter(Objects::nonNull);
		}
	}

	public Stream<PPOrderLineRow> stream()
	{
		return topLevelRows.stream();
	}

	public long size()
	{
		return topLevelRows.size();
	}
}
