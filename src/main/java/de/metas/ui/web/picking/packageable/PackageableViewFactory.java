package de.metas.ui.web.picking.packageable;

import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.ui.web.picking.PickingConstants;
import de.metas.ui.web.view.AbstractCustomView.IRowsData;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.IncludedViewLayout;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.factory.standard.LayoutFactory;
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
 * Factory class for {@link PackageableView} intances.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@ViewFactory(windowId = PickingConstants.WINDOWID_PickingView_String, viewTypes = { JSONViewDataType.grid, JSONViewDataType.includedView })
public class PackageableViewFactory implements IViewFactory
{
	private final PackageableViewRepository pickingViewRepo;

	private final PickingCandidateService pickingCandidateService;

	/**
	 *
	 * @param pickingViewRepo
	 * @param pickingCandidateService when a new view is created, this stateless instance is given to that view
	 */
	public PackageableViewFactory(
			@NonNull final PackageableViewRepository pickingViewRepo,
			@NonNull final PickingCandidateService pickingCandidateService)
	{
		this.pickingViewRepo = pickingViewRepo;
		this.pickingCandidateService = pickingCandidateService;
	}

	@Override
	public ViewLayout getViewLayout(
			@NonNull final WindowId windowId,
			@NonNull final JSONViewDataType viewDataType,
			@Nullable final ViewProfileId profileId)
	{
		return ViewLayout.builder()
				.setWindowId(PickingConstants.WINDOWID_PickingView)
				.setCaption("Picking")
				.setEmptyResultText(LayoutFactory.HARDCODED_TAB_EMPTY_RESULT_TEXT)
				.setEmptyResultHint(LayoutFactory.HARDCODED_TAB_EMPTY_RESULT_HINT)
				//
				.setHasAttributesSupport(false)
				.setHasTreeSupport(false)
				.setIncludedViewLayout(IncludedViewLayout.builder()
						.openOnSelect(true)
						.build())
				//
				.addElementsFromViewRowClass(PackageableRow.class, viewDataType)
				//
				.build();
	}

	/**
	 * @param request its {@code windowId} has to me {@link PickingConstants#WINDOWID_PickingView}
	 */
	@Override
	public IView createView(@NonNull final CreateViewRequest request)
	{
		final ViewId viewId = request.getViewId();
		if (!PickingConstants.WINDOWID_PickingView.equals(viewId.getWindowId()))
		{
			throw new IllegalArgumentException("Invalid request's windowId: " + request);
		}

		final Set<Integer> shipmentScheduleIds = extractShipmentScheduleIds(request);
		final IRowsData<PackageableRow> rowsData = pickingViewRepo.createRowsData(viewId, shipmentScheduleIds);

		return PackageableView.builder()
				.viewId(viewId)
				.rowsData(rowsData)
				.pickingCandidateService(pickingCandidateService)
				.build();
	}
	
	private static Set<Integer> extractShipmentScheduleIds(final CreateViewRequest request)
	{
		return request.getFilterOnlyIds()
				.stream()
				.filter(shipmentScheduleId -> shipmentScheduleId > 0)
				.collect(ImmutableSet.toImmutableSet());
	}

}
