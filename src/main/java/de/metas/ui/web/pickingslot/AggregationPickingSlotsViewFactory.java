package de.metas.ui.web.pickingslot;

import org.springframework.beans.factory.annotation.Autowired;

import de.metas.ui.web.picking.pickingslot.PickingSlotRow;
import de.metas.ui.web.picking.pickingslot.PickingSlotViewRepository;
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
@ViewFactory(windowId = AggregationPickingSlotsViewFactory.WINDOW_ID_STRING)
public class AggregationPickingSlotsViewFactory implements IViewFactory
{
	static final String WINDOW_ID_STRING = "540206";
	public static final WindowId WINDOW_ID = WindowId.fromJson(WINDOW_ID_STRING);

	@Autowired
	private PickingSlotViewRepository pickingSlotRepo;

	@Override
	public ViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType)
	{
		// TODO: cache it

		return ViewLayout.builder()
				.setWindowId(WINDOW_ID)
				.setCaption("Picking slots")
				.addElementsFromViewRowClass(PickingSlotRow.class, viewDataType)
				.setHasTreeSupport(true)
				.setHasIncludedViewSupport(true)
				.setHasIncludedViewOnSelectSupport(true)
				.build();

	}

	@Override
	public AggregationPickingSlotView createView(final CreateViewRequest request)
	{
		request.assertNoParentViewOrRow();

		final ViewId viewId = ViewId.random(AggregationPickingSlotsViewFactory.WINDOW_ID);

		return AggregationPickingSlotView.builder()
				.viewId(viewId)
				.rows(() -> pickingSlotRepo.retrieveAllPickingSlotsRows())
				.build();
	}
}
