package de.metas.ui.web.pickingslot;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Evaluatee;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import de.metas.picking.model.I_M_PickingSlot;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewResult;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.sql.SqlOptions;
import lombok.Builder;
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
 * @author metas-dev <dev@metasfresh.com>
 * @task https://github.com/metasfresh/metasfresh/issues/518
 */
class BrowsePickingSlotsView implements IView
{
	private final ViewId viewId;
	private final ExtendedMemorizingSupplier<Map<DocumentId, BrowsePickingSlotRow>> rowsByIdSupplier;

	private final ConcurrentHashMap<DocumentId, HUEditorView> husViewByPickingSlotRowId = new ConcurrentHashMap<>();

	@Builder
	public BrowsePickingSlotsView(@NonNull final ViewId viewId, @NonNull final Supplier<List<BrowsePickingSlotRow>> rowsSupplier)
	{
		this.viewId = viewId;
		rowsByIdSupplier = ExtendedMemorizingSupplier.of(() -> Maps.uniqueIndex(rowsSupplier.get(), BrowsePickingSlotRow::getId));
	}

	@Override
	public ViewId getViewId()
	{
		return viewId;
	}

	@Override
	public JSONViewDataType getViewType()
	{
		return JSONViewDataType.grid;
	}

	@Override
	public Set<DocumentPath> getReferencingDocumentPaths()
	{
		return ImmutableSet.of();
	}

	@Override
	public String getTableNameOrNull(final DocumentId documentId)
	{
		return I_M_PickingSlot.Table_Name;
	}

	@Override
	public ViewId getParentViewId()
	{
		return null;
	}

	@Override
	public DocumentId getParentRowId()
	{
		return null;
	}

	@Override
	public long size()
	{
		return rowsByIdSupplier.get().size();
	}

	@Override
	public void close()
	{
	}

	@Override
	public int getQueryLimit()
	{
		return -1;
	}

	@Override
	public boolean isQueryLimitHit()
	{
		return false;
	}

	@Override
	public void invalidateAll()
	{
		rowsByIdSupplier.forget();
	}

	@Override
	public ViewResult getPage(final int firstRow, final int pageLength, final List<DocumentQueryOrderBy> orderBys)
	{
		final List<BrowsePickingSlotRow> pageRows = rowsByIdSupplier.get().values().stream()
				.skip(firstRow >= 0 ? firstRow : 0)
				.limit(pageLength > 0 ? pageLength : 30)
				.collect(ImmutableList.toImmutableList());

		return ViewResult.ofViewAndPage(this, firstRow, pageLength, orderBys, pageRows);
	}

	@Override
	public BrowsePickingSlotRow getById(final DocumentId rowId) throws EntityNotFoundException
	{
		final BrowsePickingSlotRow row = rowsByIdSupplier.get().get(rowId);
		if (row == null)
		{
			throw new EntityNotFoundException("Row not found").setParameter("rowId", rowId);
		}
		return row;
	}

	@Override
	public LookupValuesList getFilterParameterDropdown(final String filterId, final String filterParameterName, final Evaluatee ctx)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public LookupValuesList getFilterParameterTypeahead(final String filterId, final String filterParameterName, final String query, final Evaluatee ctx)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public List<DocumentFilter> getStickyFilters()
	{
		return ImmutableList.of();
	}

	@Override
	public List<DocumentFilter> getFilters()
	{
		return ImmutableList.of();
	}

	@Override
	public List<DocumentQueryOrderBy> getDefaultOrderBys()
	{
		return ImmutableList.of();
	}

	@Override
	public String getSqlWhereClause(final DocumentIdsSelection rowIds, final SqlOptions sqlOpts)
	{
		return null;
		// throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasAttributesSupport()
	{
		return false;
	}

	@Override
	public <T> List<T> retrieveModelsByIds(final DocumentIdsSelection rowIds, final Class<T> modelClass)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Stream<BrowsePickingSlotRow> streamByIds(final DocumentIdsSelection rowIds)
	{
		if (rowIds.isAll())
		{
			return rowsByIdSupplier.get().values().stream();
		}
		else
		{
			return rowIds.stream().map(this::getById);
		}
	}

	@Override
	public void notifyRecordsChanged(final Set<TableRecordReference> recordRefs)
	{
		if (recordRefs.isEmpty())
		{
			return;
		}

		invalidateAll();
	}

	public HUEditorView getHUsViewOrCreate(@NonNull final DocumentId pickingSlotRowId, @NonNull final Function<BrowsePickingSlotRow, HUEditorView> huEditorViewFactory)
	{
		return husViewByPickingSlotRowId.computeIfAbsent(pickingSlotRowId, id -> huEditorViewFactory.apply(getById(id)));
	}

	public HUEditorView getHUsViewOrNull(final DocumentId pickingSlotRowId)
	{
		return husViewByPickingSlotRowId.get(pickingSlotRowId);
	}

	public void removeHUsView(final DocumentId pickingSlotRowId)
	{
		husViewByPickingSlotRowId.remove(pickingSlotRowId);
	}

}
