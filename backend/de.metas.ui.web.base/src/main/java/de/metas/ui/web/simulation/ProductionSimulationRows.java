/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.ui.web.simulation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import de.metas.ui.web.view.IEditableView;
import de.metas.ui.web.view.template.IEditableRowsData;
import de.metas.ui.web.view.template.IRowsData;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.util.GuavaCollectors;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProductionSimulationRows implements IEditableRowsData<ProductionSimulationRow>
{
	public static ProductionSimulationRows cast(final IRowsData<ProductionSimulationRow> rowsData)
	{
		return (ProductionSimulationRows)rowsData;
	}

	private final ImmutableList<DocumentId> rowIds;
	private final ConcurrentHashMap<DocumentId, ProductionSimulationRow> rowsById;

	@Builder
	private ProductionSimulationRows(@NonNull final List<ProductionSimulationRow> rows)
	{
		rowIds = rows.stream()
				.map(ProductionSimulationRow::getId)
				.collect(ImmutableList.toImmutableList());

		rowsById = new ConcurrentHashMap<>(Maps.uniqueIndex(rows, ProductionSimulationRow::getId));
	}

	@Override
	public void patchRow(final IEditableView.RowEditingContext ctx, final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		throw new UnsupportedOperationException("ProductionSimulationRows are read-only!");
	}

	@Override
	public Map<DocumentId, ProductionSimulationRow> getDocumentId2TopLevelRows()
	{
		return rowIds.stream()
				.map(rowsById::get)
				.collect(GuavaCollectors.toImmutableMapByKey(ProductionSimulationRow::getId));
	}

	@Override
	public DocumentIdsSelection getDocumentIdsToInvalidate(final TableRecordReferenceSet recordRefs)
	{
		return DocumentIdsSelection.EMPTY;
	}

	@Override
	public void invalidateAll()
	{
	}

	@NonNull
	public ImmutableList<DocumentId> getRowIds()
	{
		return rowIds;
	}
}