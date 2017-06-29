package de.metas.ui.web.picking;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Evaluatee;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import de.metas.i18n.ITranslatableString;
import de.metas.inoutcandidate.model.I_M_Packageable_V;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.exceptions.EntityNotFoundException;
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

public class PickingSlotView implements IView
{
	public static PickingSlotView cast(final IView pickingSlotView)
	{
		return (PickingSlotView)pickingSlotView;
	}

	private final ViewId viewId;
	private final ITranslatableString description;
	private final Map<DocumentId, PickingSlotRow> rows;

	@Builder
	private PickingSlotView(@NonNull final ViewId viewId,
			final ITranslatableString description,
			final List<PickingSlotRow> rows)
	{
		this.viewId = viewId;
		this.description = description != null ? description : ITranslatableString.empty();
		this.rows = Maps.uniqueIndex(rows, PickingSlotRow::getId);
	}

	@Override
	public ViewId getViewId()
	{
		return viewId;
	}

	@Override
	public ITranslatableString getDescription()
	{
		return description;
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
	public String getTableName()
	{
		return I_M_Packageable_V.Table_Name;
	}

	@Override
	public ViewId getParentViewId()
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
	public ViewResult getPage(final int firstRow, final int pageLength, final List<DocumentQueryOrderBy> orderBys)
	{
		final List<PickingSlotRow> pageRows = rows.values().stream()
				.skip(firstRow >= 0 ? firstRow : 0)
				.limit(pageLength > 0 ? pageLength : 30)
				.collect(ImmutableList.toImmutableList());

		return ViewResult.ofViewAndPage(this, firstRow, pageLength, orderBys, pageRows);
	}

	@Override
	public PickingSlotRow getById(final DocumentId rowId) throws EntityNotFoundException
	{
		final PickingSlotRow row = rows.get(rowId);
		if (row == null)
		{
			throw new EntityNotFoundException("Row not found").setParameter("rowId", rowId);
		}
		return row;
	}

	@Override
	public LookupValuesList getFilterParameterDropdown(final String filterId, final String filterParameterName, final Evaluatee ctx)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public LookupValuesList getFilterParameterTypeahead(final String filterId, final String filterParameterName, final String query, final Evaluatee ctx)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public List<DocumentFilter> getStickyFilters()
	{
		// TODO Auto-generated method stub
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
	public String getSqlWhereClause(final DocumentIdsSelection rowIds)
	{
		// TODO Auto-generated method stub
		return null;
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
	public Stream<? extends IViewRow> streamByIds(final DocumentIdsSelection rowIds)
	{
		return rowIds.stream().map(this::getById);
	}

	@Override
	public void notifyRecordsChanged(final Set<TableRecordReference> recordRefs)
	{
		// TODO Auto-generated method stub

	}
}
