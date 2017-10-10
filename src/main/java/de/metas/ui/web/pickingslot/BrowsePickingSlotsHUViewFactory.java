package de.metas.ui.web.pickingslot;

import java.util.Set;

import org.adempiere.util.Services;
import org.springframework.beans.factory.annotation.Autowired;

import de.metas.handlingunits.picking.IHUPickingSlotDAO;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.handlingunits.HUEditorViewFactory;
import de.metas.ui.web.handlingunits.HUIdsFilterHelper;
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
 * Browse selected picking slot's HUs
 * 
 * @author metas-dev <dev@metasfresh.com>
 * @see AggregationPickingSlotsViewFactory
 * @task https://github.com/metasfresh/metasfresh/issues/518
 */
@ViewFactory(windowId = BrowsePickingSlotsHUViewFactory.WINDOW_ID_STRING)
public class BrowsePickingSlotsHUViewFactory implements IViewFactory
{
	static final String WINDOW_ID_STRING = "pickingSlotHUs";
	static final WindowId WINDOW_ID = WindowId.fromJson(WINDOW_ID_STRING);

	@Autowired
	private HUEditorViewFactory huEditorViewFactory;

	@Override
	public ViewLayout getViewLayout(WindowId windowId, JSONViewDataType viewDataType)
	{
		return huEditorViewFactory.getViewLayout(windowId, viewDataType);
	}

	@Override
	@Deprecated // shall not be called directly
	public HUEditorView createView(CreateViewRequest request)
	{
		throw new UnsupportedOperationException();
	}

	public HUEditorView createViewForPickingSlotRow(final BrowsePickingSlotRow pickingSlotRow)
	{
		final int pickingSlotId = pickingSlotRow.getPickingSlotId();
		final Set<Integer> huIds = Services.get(IHUPickingSlotDAO.class).retrieveAllHUIds(pickingSlotId);

		final ViewId viewId = BrowsePickingSlotsHUViewIndexStorage.createHUsViewId(pickingSlotRow.getViewId(), pickingSlotRow.getId());
		final CreateViewRequest request = CreateViewRequest.builder(viewId, JSONViewDataType.includedView)
				.setParentViewId(pickingSlotRow.getViewId())
				.setParentRowId(pickingSlotRow.getId())
				.addStickyFilters(HUIdsFilterHelper.createFilter(huIds))
				.build();

		return huEditorViewFactory.createView(request);
	}

}
