package de.metas.ui.web.pickingslot;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.IViewRowAttributes;
import de.metas.ui.web.view.IViewRowType;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewRow.DefaultRowType;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn.ViewColumnLayout;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
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
public class BrowsePickingSlotRow implements IViewRow
{
	private final ViewId viewId;
	private final DocumentId rowId;
	private final int pickingSlotId;
	private final DocumentPath documentPath;
	private final ViewId includedHUsViewId;

	@ViewColumn(captionKey = "Name", widgetType = DocumentFieldWidgetType.Text, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 10)
	})
	private final String name;

	private transient ImmutableMap<String, Object> _fieldNameAndJsonValues; // lazy

	@Builder
	private BrowsePickingSlotRow(@NonNull final ViewId viewId, final int pickingSlotId, @NonNull final String name)
	{
		this.viewId = viewId;
		this.pickingSlotId = pickingSlotId;
		rowId = DocumentId.of(pickingSlotId);
		documentPath = DocumentPath.rootDocumentPath(BrowsePickingSlotsViewFactory.WINDOW_ID, rowId);
		includedHUsViewId = BrowsePickingSlotsHUViewIndexStorage.createHUsViewId(viewId, rowId);
		this.name = name;
	}

	public ViewId getViewId()
	{
		return viewId;
	}

	@Override
	public DocumentId getId()
	{
		return rowId;
	}

	public int getPickingSlotId()
	{
		return pickingSlotId;
	}

	@Override
	public IViewRowType getType()
	{
		return DefaultRowType.Row;
	}

	@Override
	public boolean isProcessed()
	{
		return false;
	}

	@Override
	public DocumentPath getDocumentPath()
	{
		return documentPath;
	}

	@Override
	public Map<String, Object> getFieldNameAndJsonValues()
	{
		if (_fieldNameAndJsonValues == null)
		{
			_fieldNameAndJsonValues = ViewColumnHelper.extractJsonMap(this);
		}
		return _fieldNameAndJsonValues;
	}

	@Override
	public List<? extends IViewRow> getIncludedRows()
	{
		return ImmutableList.of();
	}

	@Override
	public boolean hasAttributes()
	{
		return false;
	}

	@Override
	public IViewRowAttributes getAttributes()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasIncludedView()
	{
		return true;
	}

	@Override
	public ViewId getIncludedViewId()
	{
		return includedHUsViewId;
	}
}
