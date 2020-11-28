package de.metas.ui.web.shipment_candidates_editor;

import java.util.Set;

import de.metas.i18n.IMsgBL;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.ViewCloseAction;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

@ViewFactory(windowId = ShipmentCandidatesViewFactory.WINDOWID_String)
public class ShipmentCandidatesViewFactory implements IViewFactory
{
	public static final String WINDOWID_String = "540674"; // FIXME: HARDCODED
	public static final WindowId WINDOWID = WindowId.fromJson(WINDOWID_String);

	private final IShipmentScheduleBL shipmentScheduleBL;

	private final ShipmentCandidateRowsRepository rowsRepo;

	public ShipmentCandidatesViewFactory(
			@NonNull final IShipmentScheduleBL shipmentScheduleBL)
	{
		this.shipmentScheduleBL = shipmentScheduleBL;
		this.rowsRepo = ShipmentCandidateRowsRepository.builder()
				.shipmentScheduleBL(shipmentScheduleBL)
				.build();
	}

	@Override
	public ViewLayout getViewLayout(WindowId windowId, JSONViewDataType viewDataType, ViewProfileId profileId)
	{
		return ViewLayout.builder()
				.setWindowId(WINDOWID)
				.setCaption(Services.get(IMsgBL.class).translatable("M_ShipmentSchedule_ID"))
				.setAllowOpeningRowDetails(false)
				.allowViewCloseAction(ViewCloseAction.CANCEL)
				.allowViewCloseAction(ViewCloseAction.DONE)
				.addElementsFromViewRowClass(ShipmentCandidateRow.class, viewDataType)
				.build();
	}

	@Override
	public ShipmentCandidatesView createView(@NonNull final CreateViewRequest request)
	{
		final ViewId viewId = request.getViewId();
		viewId.assertWindowId(WINDOWID);

		final Set<ShipmentScheduleId> shipmentScheduleIds = ShipmentScheduleId.fromIntSet(request.getFilterOnlyIds());
		final ShipmentCandidateRows rows = rowsRepo.getByShipmentScheduleIds(shipmentScheduleIds);

		return ShipmentCandidatesView.builder()
				.shipmentScheduleBL(shipmentScheduleBL)
				//
				.viewId(viewId)
				.rows(rows)
				.build();
	}
}
