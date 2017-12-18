package de.metas.ui.web.pickingslotsClearing.process;

import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.picking.pickingslot.PickingSlotRow;
import de.metas.ui.web.pickingslotsClearing.PickingSlotsClearingView;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;

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

public abstract class PickingSlotsClearingViewBasedProcess extends ViewBasedProcessTemplate
{
	public final PickingSlotsClearingView getPickingSlotsClearingView()
	{
		return getView(PickingSlotsClearingView.class);
	}

	public final void invalidatePickingSlotsClearingView()
	{
		invalidateView(getPickingSlotsClearingView().getViewId());
	}

	protected final boolean isSingleSelectedPickingSlotRow()
	{
		return getSelectedRowIds().isSingleDocumentId();
	}

	protected final PickingSlotRow getSingleSelectedPickingSlotRow()
	{
		return PickingSlotRow.cast(super.getSingleSelectedRow());
	}

	/** @return the actual picking slow row (the top level row) */
	protected final PickingSlotRow getRootSelectedPickingSlotRow()
	{
		final PickingSlotRow row = getSingleSelectedPickingSlotRow();
		return getPickingSlotsClearingView().getRootRowWhichIncludesRowId(row.getPickingSlotRowId());
	}

	protected final HUEditorView getPackingHUsView()
	{
		return getChildView(HUEditorView.class);
	}

	protected final DocumentIdsSelection getSelectedPackingHUsRowIds()
	{
		return getChildViewSelectedRowIds();
	}

	protected final boolean isSingleSelectedPackingHUsRow()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedPackingHUsRowIds();
		return selectedRowIds.isSingleDocumentId();
	}

	protected final HUEditorRow getSingleSelectedPackingHUsRow()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedPackingHUsRowIds();
		final DocumentId rowId = selectedRowIds.getSingleDocumentId();
		return getPackingHUsView().getById(rowId);
	}
}
