package de.metas.ui.web.picking.pickingslot;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.metas.ui.web.picking.PickingConstants;
import de.metas.ui.web.picking.packageable.PackageableRow;
import de.metas.ui.web.picking.packageable.PackageableView;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsIndexStorage;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewCloseReason;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.event.ViewChangesCollector;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
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
 * It's not actually a storage. It just forwards all calls to {@link PackageableView} where the {@link PickingSlotView}s are stored, one per each row.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Component
public class PickingSlotViewsIndexStorage implements IViewsIndexStorage
{
	//@formatter:off
	@Override
	public WindowId getWindowId() { return PickingConstants.WINDOWID_PickingSlotView; }
	//@formatter:off
	
	@Autowired
	private PickingSlotViewFactory pickingSlotViewFactory;

	// NOTE: avoid using @Autowired because might introduce cyclic dependency.
	// We have a setter which will be called when this instance will be registered.
	private IViewsRepository viewsRepository;

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
	public void put(final IView pickingSlotView)
	{
		final ViewId pickingSlotViewId = pickingSlotView.getViewId();
		final PackageableView packageableView = getPackageableViewByPickingSlotViewId(pickingSlotViewId);

		final DocumentId rowId = extractRowId(pickingSlotViewId);

		packageableView.setPickingSlotView(rowId, PickingSlotView.cast(pickingSlotView));
	}

	public static ViewId createViewId(@NonNull final ViewId pickingViewId, @NonNull final DocumentId pickingRowId)
	{
		if (!PickingConstants.WINDOWID_PickingView.equals(pickingViewId.getWindowId()))
		{
			throw new AdempiereException("Invalid pickingViewId '" + pickingViewId + "'. WindowId not matching.")
					.setParameter("expectedWindowId", PickingConstants.WINDOWID_PickingView);
		}

		return ViewId.ofParts(PickingConstants.WINDOWID_PickingSlotView, pickingViewId.getViewIdPart(), pickingRowId.toJson());
	}

	private static ViewId extractPickingViewId(final ViewId pickingSlotViewId)
	{
		final String viewIdPart = pickingSlotViewId.getViewIdPart();
		return ViewId.ofParts(PickingConstants.WINDOWID_PickingView, viewIdPart);
	}

	private static DocumentId extractRowId(@NonNull final ViewId pickingSlotViewId)
	{
		final String rowIdStr = pickingSlotViewId.getPart(2);
		return DocumentId.of(rowIdStr);
	}

	private PackageableView getPackageableViewByPickingSlotViewId(final ViewId pickingSlotViewId)
	{
		final ViewId pickingViewId = extractPickingViewId(pickingSlotViewId);
		final PackageableView view = PackageableView.cast(getViewsRepository().getView(pickingViewId));
		return view;
	}

	@Override
	public PickingSlotView getByIdOrNull(final ViewId pickingSlotViewId)
	{
		final boolean create = true;
		return getOrCreatePickingSlotView(pickingSlotViewId, create);
	}

	private PickingSlotView getOrCreatePickingSlotView(@NonNull final ViewId pickingSlotViewId, final boolean create)
	{
		final PackageableView packageableView = getPackageableViewByPickingSlotViewId(pickingSlotViewId);
		final DocumentId packageableRowId = extractRowId(pickingSlotViewId);

		if (create)
		{
			return packageableView.computePickingSlotViewIfAbsent(
					packageableRowId,
					() -> {
						final PackageableRow packageableRow = packageableView.getById(packageableRowId);
						final CreateViewRequest createViewRequest = CreateViewRequest
								.builder(PickingConstants.WINDOWID_PickingSlotView, JSONViewDataType.includedView)
								.setParentViewId(packageableView.getViewId())
								.setParentRowId(packageableRow.getId())
								.build();

						// provide all pickingView's M_ShipmentSchedule_IDs to the factory, because we want to show the same picking slots and picked HU-rows for all of them.
						final List<Integer> allShipmentScheduleIds = packageableView
								.streamByIds(DocumentIdsSelection.ALL)
								.map(PackageableRow::cast)
								.map(PackageableRow::getShipmentScheduleId)
								.collect(Collectors.toList());

						return pickingSlotViewFactory.createView(createViewRequest, allShipmentScheduleIds);
					});
		}
		else
		{
			return packageableView.getPickingSlotViewOrNull(packageableRowId);
		}
	}

	@Override
	public void removeById(@NonNull final ViewId pickingSlotViewId)
	{
		final DocumentId rowId = extractRowId(pickingSlotViewId);
		final PackageableView packageableView = getPackageableViewByPickingSlotViewId(pickingSlotViewId);
		packageableView.removePickingSlotView(rowId, ViewCloseReason.USER_REQUEST);
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
