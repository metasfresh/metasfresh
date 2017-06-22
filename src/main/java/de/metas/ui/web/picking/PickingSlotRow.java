package de.metas.ui.web.picking;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.handlingunits.model.I_M_PickingSlot;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.IViewRowAttributes;
import de.metas.ui.web.view.IViewRowType;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;

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

@ToString
public final class PickingSlotRow implements IViewRow
{
	private final DocumentId id;
	private final IViewRowType type;
	private final boolean processed;
	private final DocumentPath documentPath;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Text, captionKey = I_M_PickingSlot.COLUMNNAME_PickingSlot, seqNo = 10)
	private final String name;
	@ViewColumn(widgetType = DocumentFieldWidgetType.Lookup, captionKey = I_M_PickingSlot.COLUMNNAME_M_Warehouse_ID, seqNo = 20)
	private final LookupValue warehouse;
	@ViewColumn(widgetType = DocumentFieldWidgetType.Lookup, captionKey = I_M_PickingSlot.COLUMNNAME_C_BPartner_ID, seqNo = 30)
	private final LookupValue bpartner;
	@ViewColumn(widgetType = DocumentFieldWidgetType.Lookup, captionKey = I_M_PickingSlot.COLUMNNAME_C_BPartner_Location_ID, seqNo = 40)
	private final LookupValue bpartnerLocation;

	private transient ImmutableMap<String, Object> _fieldNameAndJsonValues;

	@Builder
	private PickingSlotRow(@NonNull final DocumentId id,
			final IViewRowType type,
			final boolean processed,
			@NonNull final DocumentPath documentPath,
			//
			final String name,
			final LookupValue warehouse,
			final LookupValue bpartner,
			final LookupValue bpartnerLocation)
	{
		this.id = id;
		this.type = type;
		this.processed = processed;
		this.documentPath = documentPath;

		this.name = name;
		this.warehouse = warehouse;
		this.bpartner = bpartner;
		this.bpartnerLocation = bpartnerLocation;
	}

	@Override
	public DocumentId getId()
	{
		return id;
	}

	@Override
	public IViewRowType getType()
	{
		return type;
	}

	@Override
	public boolean isProcessed()
	{
		return processed;
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
	public IViewRowAttributes getAttributes() throws EntityNotFoundException
	{
		throw new EntityNotFoundException("Row does not support attributes");
	}

	@Override
	public boolean hasIncludedView()
	{
		return false;
	}
}
