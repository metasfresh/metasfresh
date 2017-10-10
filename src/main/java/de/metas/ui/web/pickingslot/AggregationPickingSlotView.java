package de.metas.ui.web.pickingslot;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Evaluatee;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.i18n.ITranslatableString;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.picking.pickingslot.PickingSlotRow;
import de.metas.ui.web.picking.pickingslot.PickingSlotRowsCollection;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewRow;
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

public class AggregationPickingSlotView implements IView
{
	private final ViewId viewId;
	private final ITranslatableString description;
	private final PickingSlotRowsCollection rows;

	@Builder
	private AggregationPickingSlotView(
			@NonNull final ViewId viewId,
			@Nullable final ITranslatableString description,
			@NonNull final Supplier<List<PickingSlotRow>> rows)
	{
		this.viewId = viewId;
		this.description = ITranslatableString.nullToEmpty(description);
		this.rows = PickingSlotRowsCollection.ofSupplier(rows);
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
	public ITranslatableString getDescription()
	{
		return description;
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
		return rows.size();
	}

	@Override
	public void close()
	{
		// nothing
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
		rows.invalidateAll();
	}

	@Override
	public ViewResult getPage(final int firstRow, final int pageLength, final List<DocumentQueryOrderBy> orderBys)
	{
		final List<PickingSlotRow> pageRows = rows.getPage(firstRow, pageLength);

		return ViewResult.ofViewAndPage(this, firstRow, pageLength, orderBys, pageRows);
	}

	@Override
	public IViewRow getById(final DocumentId rowId) throws EntityNotFoundException
	{
		return rows.getById(rowId);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> retrieveModelsByIds(final DocumentIdsSelection rowIds, final Class<T> modelClass)
	{
		return ImmutableList.of();
	}

	@Override
	public Stream<? extends IViewRow> streamByIds(final DocumentIdsSelection rowIds)
	{
		return rows.streamByIds(rowIds);
	}

	@Override
	public void notifyRecordsChanged(final Set<TableRecordReference> recordRefs)
	{
		// TODO Auto-generated method stub

	}

}
