package de.metas.ui.web.pickingslot;

import java.util.List;

import org.adempiere.util.Services;

import com.google.common.collect.ImmutableList;

import de.metas.picking.api.IPickingSlotDAO;
import de.metas.picking.api.IPickingSlotDAO.PickingSlotQuery;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;

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
 * Browse Picking slots
 * 
 * @author metas-dev <dev@metasfresh.com>
 * @task https://github.com/metasfresh/metasfresh/issues/518
 */
@ViewFactory(windowId = BrowsePickingSlotsViewFactory.WINDOW_ID_STRING)
public class BrowsePickingSlotsViewFactory implements IViewFactory
{
	static final String WINDOW_ID_STRING = "540206";
	public static final WindowId WINDOW_ID = WindowId.fromJson(WINDOW_ID_STRING);

	@Override
	public ViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType)
	{
		// TODO: cache it

		return ViewLayout.builder()
				.setWindowId(WINDOW_ID)
				.setCaption("Browse picking slots")
				.setHasIncludedViewSupport(true)
				.setHasIncludedViewOnSelectSupport(true)
				.addElementsFromViewRowClass(BrowsePickingSlotRow.class, viewDataType)
				.build();
	}

	@Override
	public BrowsePickingSlotsView createView(final CreateViewRequest request)
	{
		// Preconditions.checkArgument(request.getParentViewId() == null, "ParentViewId not supported: %s", request);
		// Preconditions.checkArgument(request.getParentRowId() == null, "ParentRowId not supported: %s", request);

		final ViewId viewId = ViewId.random(BrowsePickingSlotsViewFactory.WINDOW_ID);

		return BrowsePickingSlotsView.builder()
				.viewId(viewId)
				.rowsSupplier(() -> retrieveAllPickingSlotRows(viewId))
				.build();
	}

	private static List<BrowsePickingSlotRow> retrieveAllPickingSlotRows(final ViewId viewId)
	{
		final IPickingSlotDAO pickingSlotDAO = Services.get(IPickingSlotDAO.class);
		final List<I_M_PickingSlot> pickingSlots = pickingSlotDAO.retrivePickingSlots(PickingSlotQuery.ALL);

		return pickingSlots.stream()
				.map(pickingSlot -> createPickingSlotRow(viewId, pickingSlot))
				.collect(ImmutableList.toImmutableList());
	}

	private static BrowsePickingSlotRow createPickingSlotRow(final ViewId viewId, final I_M_PickingSlot pickingSlot)
	{
		return BrowsePickingSlotRow.builder()
				.viewId(viewId)
				.pickingSlotId(pickingSlot.getM_PickingSlot_ID())
				.name(pickingSlot.getPickingSlot())
				.build();
	}
}
