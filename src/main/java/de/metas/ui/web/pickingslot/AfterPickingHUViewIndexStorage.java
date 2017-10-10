package de.metas.ui.web.pickingslot;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsIndexStorage;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.event.ViewChangesCollector;
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

@Component
public class AfterPickingHUViewIndexStorage implements IViewsIndexStorage
{
	//@formatter:off
	@Override
	public WindowId getWindowId() { return AfterPickingHUViewFactory.WINDOW_ID; }
	//@formatter:on

	@Autowired
	private AfterPickingHUViewFactory afterPickingHUsViewFactory;

	private IViewsRepository viewsRepository;

	@Override
	public void setViewsRepository(final IViewsRepository viewsRepository)
	{
		this.viewsRepository = viewsRepository;
	}

	@Override
	public void put(final IView husView)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public HUEditorView getByIdOrNull(final ViewId huViewId)
	{
		return getHUsViewOrCreate(huViewId);
	}

	@Override
	public void removeById(final ViewId huViewId)
	{
		getPickingSlotsView(huViewId).clearAfterPickingHUView();
	}

	@Override
	public Stream<IView> streamAllViews()
	{
		return Stream.empty();
	}

	@Override
	public void invalidateView(final ViewId huViewId)
	{
		final HUEditorView husView = getHUsViewOrNull(huViewId);
		if (husView == null)
		{
			return;
		}

		husView.invalidateAll();
		ViewChangesCollector.getCurrentOrAutoflush()
				.collectFullyChanged(husView);
	}

	private HUEditorView getHUsViewOrCreate(final ViewId huViewId)
	{
		final AggregationPickingSlotView pickingSlotsView = getPickingSlotsView(huViewId);
		return pickingSlotsView.getAfterPickingHUViewOrCreate(() -> afterPickingHUsViewFactory.createViewForAggregationPickingSlotView(pickingSlotsView));
	}

	private HUEditorView getHUsViewOrNull(final ViewId huViewId)
	{
		final AggregationPickingSlotView pickingSlotsView = getPickingSlotsView(huViewId);
		return pickingSlotsView.getAfterPickingHUViewOrNull();
	}

	private AggregationPickingSlotView getPickingSlotsView(final ViewId huViewId)
	{
		final ViewId pickingSlotsViewId = extractPickingSlotsViewId(huViewId);
		return viewsRepository.getView(pickingSlotsViewId, AggregationPickingSlotView.class);
	}

	private static ViewId extractPickingSlotsViewId(final ViewId huViewId)
	{
		return huViewId.deriveWithWindowId(AggregationPickingSlotsViewFactory.WINDOW_ID);
	}

	static ViewId extractAfterPickingHUsViewId(final ViewId pickingSlotViewId)
	{
		return pickingSlotViewId.deriveWithWindowId(AfterPickingHUViewFactory.WINDOW_ID);
	}

}
