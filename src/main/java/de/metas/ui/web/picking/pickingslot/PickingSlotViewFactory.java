package de.metas.ui.web.picking.pickingslot;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.metas.printing.esb.base.util.Check;
import de.metas.process.IADProcessDAO;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.picking.PickingConstants;
import de.metas.ui.web.picking.process.WEBUI_Picking_HUEditor_Launcher;
import de.metas.ui.web.picking.process.WEBUI_Picking_M_Picking_Candidate_Process;
import de.metas.ui.web.picking.process.WEBUI_Picking_M_Picking_Candidate_Unprocess;
import de.metas.ui.web.picking.process.WEBUI_Picking_M_Source_HU_Delete;
import de.metas.ui.web.picking.process.WEBUI_Picking_PickQtyToExistingHU;
import de.metas.ui.web.picking.process.WEBUI_Picking_PickQtyToNewHU;
import de.metas.ui.web.picking.process.WEBUI_Picking_RemoveHUFromPickingSlot;
import de.metas.ui.web.picking.process.WEBUI_Picking_ReturnQtyToSourceHU;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.descriptor.ViewLayout;
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
 * Factory to create {@link PickingSlotView}s instances. This includes assigning a number of picking related processed to the view.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@ViewFactory(windowId = PickingConstants.WINDOWID_PickingSlotView_String, viewTypes = { JSONViewDataType.grid, JSONViewDataType.includedView })
public class PickingSlotViewFactory implements IViewFactory
{
	@Autowired
	private PickingSlotViewRepository pickingSlotRepo;

	@Override
	public ViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType)
	{
		if (!PickingConstants.WINDOWID_PickingSlotView.equals(windowId))
		{
			throw new AdempiereException("windowId shall be " + PickingConstants.WINDOWID_PickingSlotView);
		}

		return ViewLayout.builder()
				.setWindowId(windowId)
				.setCaption("Picking slots")
				.addElementsFromViewRowClass(PickingSlotRow.class, viewDataType)
				.setHasTreeSupport(true)
				.build();
	}

	/**
	 * This method is called once for each shipment schedule (left-hand side) and creates the respective picking view (right-hand side).
	 */
	@Override
	public PickingSlotView createView(@NonNull final CreateViewRequest request)
	{
		return createView(request, null);
	}

	/**
	 * This method is called once for each shipment schedule (left-hand side) and creates the respective picking view (right-hand side)
	 * 
	 * @param request
	 * @param allShipmentScheduleIds the shipment schedule IDs to display picking slots for; <br>
	 *            may be {@code null} or empty, in this case we assume that only the given {@code request}'s {@code shipmentScheduleId} is available.
	 * @return
	 */
	public PickingSlotView createView(
			@NonNull final CreateViewRequest request,
			@Nullable final List<Integer> allShipmentScheduleIds)
	{
		final ViewId pickingViewId = request.getParentViewId();
		final DocumentId pickingRowId = request.getParentRowId();
		final ViewId pickingSlotViewId = PickingSlotViewsIndexStorage.createViewId(pickingViewId, pickingRowId);

		final int shipmentScheduleId = pickingRowId.toInt(); // TODO make it more obvious/explicit

		//
		// setup the picking slot query and the rowsSupplier which uses the query to retrieve the PickingSlotView's rows.
		final PickingSlotRepoQuery query;
		if (allShipmentScheduleIds == null || allShipmentScheduleIds.isEmpty())
		{
			query = PickingSlotRepoQuery.of(shipmentScheduleId);
		}
		else
		{
			Check.errorUnless(allShipmentScheduleIds.contains(shipmentScheduleId),
					"The given allShipmentScheduleIds has to include the given request's shipmentScheduleId; shipmentScheduleId={}; allShipmentScheduleIds={}; request={}",
					shipmentScheduleId, allShipmentScheduleIds, request);

			query = PickingSlotRepoQuery.of(allShipmentScheduleIds);
		}
		// notice for noobs such as me: this is executed each time the view is revalidated. and it's not executed when this createView method runs
		final Supplier<List<PickingSlotRow>> rowsSupplier = () -> pickingSlotRepo.retrieveRows(query);

		return PickingSlotView.builder()
				.viewId(pickingSlotViewId)
				.parentViewId(pickingViewId)
				.parentRowId(pickingRowId)
				.currentShipmentScheduleId(shipmentScheduleId)
				.rows(rowsSupplier)
				.additionalRelatedProcessDescriptors(createAdditionalRelatedProcessDescriptors())
				.build();
	}

	private List<RelatedProcessDescriptor> createAdditionalRelatedProcessDescriptors()
	{
		return ImmutableList.of(
				// allow to open the HU-editor for various picking related purposes
				createProcessDescriptorForPickingSlotView(WEBUI_Picking_HUEditor_Launcher.class),

				// fine-picking related processes
				createProcessDescriptorForPickingSlotView(WEBUI_Picking_PickQtyToNewHU.class),
				createProcessDescriptorForPickingSlotView(WEBUI_Picking_PickQtyToExistingHU.class),
				createProcessDescriptorForPickingSlotView(WEBUI_Picking_ReturnQtyToSourceHU.class),

				// note that WEBUI_Picking_M_Source_HU_Create is called from the HU-editor
				createProcessDescriptorForPickingSlotView(WEBUI_Picking_M_Source_HU_Delete.class),

				// complete-HU-picking related processes; note that the add to-slot-process is called from the HU-editor
				createProcessDescriptorForPickingSlotView(WEBUI_Picking_RemoveHUFromPickingSlot.class),

				// "picking-lifecycle" processes
				createProcessDescriptorForPickingSlotView(WEBUI_Picking_M_Picking_Candidate_Process.class),
				createProcessDescriptorForPickingSlotView(WEBUI_Picking_M_Picking_Candidate_Unprocess.class));
	}

	private static RelatedProcessDescriptor createProcessDescriptorForPickingSlotView(@NonNull final Class<?> processClass)
	{
		final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

		final int processId = adProcessDAO.retriveProcessIdByClassIfUnique(Env.getCtx(), processClass);
		Preconditions.checkArgument(processId > 0, "No AD_Process_ID found for %s", processClass);

		return RelatedProcessDescriptor.builder()
				.processId(processId)
				.windowId(PickingConstants.WINDOWID_PickingSlotView.toInt())
				.anyTable()
						.webuiQuickAction(true)
				.build();
	}
}
