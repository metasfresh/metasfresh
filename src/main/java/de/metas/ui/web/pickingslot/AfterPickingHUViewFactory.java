package de.metas.ui.web.pickingslot;

import org.adempiere.util.Services;
import org.springframework.beans.factory.annotation.Autowired;

import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
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

@ViewFactory(windowId = AfterPickingHUViewFactory.WINDOW_ID_STRING)
public class AfterPickingHUViewFactory implements IViewFactory
{
	static final String WINDOW_ID_STRING = "afterPickingHUs";
	static final WindowId WINDOW_ID = WindowId.fromJson(WINDOW_ID_STRING);

	@Autowired
	private HUEditorViewFactory huEditorViewFactory;

	@Override
	public ViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType)
	{
		return huEditorViewFactory.getViewLayout(windowId, viewDataType);
	}

	@Override
	@Deprecated // shall not be called directly
	public HUEditorView createView(final CreateViewRequest request)
	{
		throw new UnsupportedOperationException();
	}

	public HUEditorView createViewForAggregationPickingSlotView(final AggregationPickingSlotView pickingSlotsView)
	{
		final IHUQueryBuilder huQuery = Services.get(IHandlingUnitsDAO.class)
				.createHUQueryBuilder()
				.setIncludeAfterPickingLocator(true);

		final ViewId viewId = pickingSlotsView.getIncludedViewId();
		final CreateViewRequest request = CreateViewRequest.builder(viewId, JSONViewDataType.includedView)
				.setParentViewId(pickingSlotsView.getViewId())
				.addStickyFilters(HUIdsFilterHelper.createFilter(huQuery))
				.build();

		return huEditorViewFactory.createView(request);
	}
}
