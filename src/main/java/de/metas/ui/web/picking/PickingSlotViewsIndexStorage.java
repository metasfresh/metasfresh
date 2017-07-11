package de.metas.ui.web.picking;

import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsIndexStorage;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.event.ViewChangesCollector;
import de.metas.ui.web.view.json.JSONViewDataType;
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
 * {@link PickingSlotView}s index storage.
 * 
 * It's not actually a storage. It just forwards all calls to {@link PickingView} where the {@link PickingSlotView}s are storaged, one per each row.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Component
public class PickingSlotViewsIndexStorage implements IViewsIndexStorage
{
	// NOTE: avoid using @Autowired because might introduce cyclic dependency.
	// We have a setter which will be called when this instance will be registered.
	private IViewsRepository viewsRepository;

	@Autowired
	private PickingSlotViewFactory pickingSlotViewFactory;

	@Override
	public void setViewsRepository(@NonNull final IViewsRepository viewsRepository)
	{
		this.viewsRepository = viewsRepository;
	}

	@NonNull
	private IViewsRepository getViewsRepository()
	{
		return viewsRepository;
	}

	@Override
	public WindowId getWindowId()
	{
		return PickingConstants.WINDOWID_PickingSlotView;
	}

	@Override
	public void put(final IView pickingSlotView)
	{
		final ViewId pickingSlotViewId = pickingSlotView.getViewId();
		final PickingView pickingView = getPickingViewByPickingSlotViewId(pickingSlotViewId);

		final DocumentId rowId = extractRowId(pickingSlotViewId);

		pickingView.setPickingSlotView(rowId, PickingSlotView.cast(pickingSlotView));
	}

	public static ViewId createViewId(final ViewId pickingViewId, final DocumentId pickingRowId)
	{
		if (!PickingConstants.WINDOWID_PickingView.equals(pickingViewId.getWindowId()))
		{
			throw new AdempiereException("Invalid pickingViewId '" + pickingViewId + "'. WindowId not matching.")
					.setParameter("expectedWindowId", PickingConstants.WINDOWID_PickingView);
		}

		return ViewId.ofParts(PickingConstants.WINDOWID_PickingSlotView, pickingViewId.getViewIdPart(), pickingRowId.toJson());
	}

	private ViewId extractPickingViewId(final ViewId pickingSlotViewId)
	{
		final String viewIdPart = pickingSlotViewId.getViewIdPart();
		return ViewId.ofParts(PickingConstants.WINDOWID_PickingView, viewIdPart);
	}

	private DocumentId extractRowId(final ViewId pickingSlotViewId)
	{
		final String rowIdStr = pickingSlotViewId.getPart(2);
		return DocumentId.of(rowIdStr);
	}

	private PickingView getPickingViewByPickingSlotViewId(final ViewId pickingSlotViewId)
	{
		final ViewId pickingViewId = extractPickingViewId(pickingSlotViewId);
		final PickingView view = PickingView.cast(getViewsRepository().getView(pickingViewId));
		return view;
	}

	@Override
	public PickingSlotView getByIdOrNull(final ViewId pickingSlotViewId)
	{
		final boolean create = true;
		return getOrCreatePickingSlotView(pickingSlotViewId, create);
	}

	private PickingSlotView getOrCreatePickingSlotView(final ViewId pickingSlotViewId, final boolean create)
	{
		final PickingView pickingView = getPickingViewByPickingSlotViewId(pickingSlotViewId);
		final DocumentId pickingSlotRowId = extractRowId(pickingSlotViewId);

		if (create)
		{
			return pickingView.computePickingSlotViewIfAbsent(pickingSlotRowId, () -> {
				final PickingRow pickingRow = pickingView.getById(pickingSlotRowId);
				return pickingSlotViewFactory.createView(CreateViewRequest.builder(PickingConstants.WINDOWID_PickingSlotView, JSONViewDataType.includedView)
						.setParentViewId(pickingView.getViewId())
						.setReferencingDocumentPath(pickingRow.getDocumentPath())
						.build());

			});
		}
		else
		{
			return pickingView.getPickingSlotViewOrNull(pickingSlotRowId);
		}
	}

	@Override
	public void removeById(final ViewId pickingSlotViewId)
	{
		final DocumentId rowId = extractRowId(pickingSlotViewId);
		getPickingViewByPickingSlotViewId(pickingSlotViewId).removePickingSlotView(rowId);
	}

	@Override
	public void invalidateView(ViewId pickingSlotViewId)
	{
		final PickingSlotView pickingSlotView = getOrCreatePickingSlotView(pickingSlotViewId, false/* create */);
		if (pickingSlotView == null)
		{
			return;
		}

		pickingSlotView.invalidateAll();

		ViewChangesCollector.getCurrentOrAutoflush()
				.collectFullyChanged(pickingSlotView);
	}

	@Override
	public Stream<IView> streamAllViews()
	{
		// Do we really have to implement this?
		return Stream.empty();
	}

}
