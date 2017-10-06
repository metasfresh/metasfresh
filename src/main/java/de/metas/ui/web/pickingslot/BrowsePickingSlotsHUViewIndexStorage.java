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
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.WindowId;
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
 * Index storage of those {@link HUEditorView}s created by {@link BrowsePickingSlotsHUViewFactory}.
 * 
 * It's not an actual storage. It just forwards the calls to {@link BrowsePickingSlotsView} where the {@link HUEditorView}s are stored, one for each row.
 * 
 * @author metas-dev <dev@metasfresh.com>
 * @task https://github.com/metasfresh/metasfresh/issues/518
 */
@Component
public class BrowsePickingSlotsHUViewIndexStorage implements IViewsIndexStorage
{
	@Autowired
	private BrowsePickingSlotsHUViewFactory husViewFactory;

	private IViewsRepository viewsRepository;

	@Override
	public void setViewsRepository(final IViewsRepository viewsRepository)
	{
		this.viewsRepository = viewsRepository;
	}

	private IViewsRepository getViewsRepository()
	{
		return viewsRepository;
	}

	@Override
	public WindowId getWindowId()
	{
		return BrowsePickingSlotsHUViewFactory.WINDOW_ID;
	}

	@Override
	public void put(final IView view)
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
		final BrowsePickingSlotsView pickingSlotsView = getPickingSlotsView(huViewId);
		final DocumentId pickingSlotRowId = extractPickingSlotRowId(huViewId);
		pickingSlotsView.removeHUsView(pickingSlotRowId);
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
		final BrowsePickingSlotsView pickingSlotsView = getPickingSlotsView(huViewId);
		final DocumentId pickingSlotRowId = extractPickingSlotRowId(huViewId);
		return pickingSlotsView.getHUsViewOrCreate(pickingSlotRowId, pickingSlotRow -> husViewFactory.createViewForPickingSlotRow(pickingSlotRow));
	}

	private HUEditorView getHUsViewOrNull(final ViewId huViewId)
	{
		final BrowsePickingSlotsView pickingSlotsView = getPickingSlotsView(huViewId);
		final DocumentId pickingSlotRowId = extractPickingSlotRowId(huViewId);
		return pickingSlotsView.getHUsViewOrNull(pickingSlotRowId);
	}

	private BrowsePickingSlotsView getPickingSlotsView(final ViewId huViewId)
	{
		final ViewId pickingSlotsViewId = extractPickingSlotsViewId(huViewId);
		return getViewsRepository().getView(pickingSlotsViewId, BrowsePickingSlotsView.class);
	}

	static ViewId createHUsViewId(@NonNull final ViewId pickingSlotsViewId, @NonNull final DocumentId pickingSlotRowId)
	{
		return ViewId.ofParts(BrowsePickingSlotsHUViewFactory.WINDOW_ID, pickingSlotsViewId.getViewIdPart(), pickingSlotRowId.toJson());
	}

	private static ViewId extractPickingSlotsViewId(final ViewId huViewId)
	{
		final String viewIdPart = huViewId.getViewIdPart();
		return ViewId.ofParts(BrowsePickingSlotsViewFactory.WINDOW_ID, viewIdPart);
	}

	private static DocumentId extractPickingSlotRowId(final ViewId huViewId)
	{
		final String rowIdStr = huViewId.getPart(2);
		return DocumentId.of(rowIdStr);
	}

}
