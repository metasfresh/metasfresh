package de.metas.ui.web.picking.pickingslot;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.util.CCache;
import org.compiere.util.Env;
import org.compiere.util.Util.ArrayKey;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.metas.printing.esb.base.util.Check;
import de.metas.process.IADProcessDAO;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.picking.PickingConstants;
import de.metas.ui.web.picking.pickingslot.PickingSlotRepoQuery.PickingCandidate;
import de.metas.ui.web.picking.pickingslot.PickingSlotRepoQuery.PickingSlotRepoQueryBuilder;
import de.metas.ui.web.picking.pickingslot.process.WEBUI_Picking_HUEditor_Launcher;
import de.metas.ui.web.picking.pickingslot.process.WEBUI_Picking_M_Picking_Candidate_Process;
import de.metas.ui.web.picking.pickingslot.process.WEBUI_Picking_M_Picking_Candidate_Unprocess;
import de.metas.ui.web.picking.pickingslot.process.WEBUI_Picking_M_Source_HU_Delete;
import de.metas.ui.web.picking.pickingslot.process.WEBUI_Picking_PickQtyToExistingHU;
import de.metas.ui.web.picking.pickingslot.process.WEBUI_Picking_PickQtyToNewHU;
import de.metas.ui.web.picking.pickingslot.process.WEBUI_Picking_RemoveHUFromPickingSlot;
import de.metas.ui.web.picking.pickingslot.process.WEBUI_Picking_ReturnQtyToSourceHU;
import de.metas.ui.web.picking.pickingslot.process.WEBUI_Picking_TU_Label;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
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

	private CCache<ArrayKey, ViewLayout> viewLayoutCache = CCache.newCache("PickingSlotViewLayout", 1, CCache.EXPIREMINUTES_Never);
	private CCache<Integer, DocumentFilterDescriptorsProvider> filterDescriptorsProviderCache = CCache.newCache("PickingSlotView.FilterDescriptorsProvider", 1, CCache.EXPIREMINUTES_Never);

	@Override
	public ViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType, @Nullable final ViewProfileId profileId_NOTUSED)
	{
		final ArrayKey cacheKey = ArrayKey.of(windowId, viewDataType);
		return viewLayoutCache.getOrLoad(cacheKey, () -> createViewLayout(windowId, viewDataType));
	}

	private ViewLayout createViewLayout(final WindowId windowId, final JSONViewDataType viewDataType)
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
				.setTreeCollapsible(true)
				.setTreeExpandedDepth(ViewLayout.TreeExpandedDepth_ExpandedFirstLevel)
				.setFilters(getFilterDescriptorsProvider().getAll())
				.build();
	}

	private DocumentFilterDescriptorsProvider getFilterDescriptorsProvider()
	{
		return filterDescriptorsProviderCache.getOrLoad(0, () -> PickingSlotViewFilters.createFilterDescriptorsProvider());
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
		final CreateViewRequest requestEffective = request.unwrapFiltersAndCopy(getFilterDescriptorsProvider());

		final ViewId pickingViewId = requestEffective.getParentViewId();
		final DocumentId pickingRowId = requestEffective.getParentRowId();
		final ViewId pickingSlotViewId = PickingSlotViewsIndexStorage.createViewId(pickingViewId, pickingRowId);
		final int shipmentScheduleId = extractCurrentShipmentScheduleId(requestEffective);

		final PickingSlotRepoQuery query = createPickingSlotRowsQuery(requestEffective, allShipmentScheduleIds);
		final Supplier<List<PickingSlotRow>> rowsSupplier = () -> pickingSlotRepo.retrieveRows(query);

		return PickingSlotView.builder()
				.viewId(pickingSlotViewId)
				.parentViewId(pickingViewId)
				.parentRowId(pickingRowId)
				.currentShipmentScheduleId(shipmentScheduleId)
				.rowsSupplier(rowsSupplier)
				.additionalRelatedProcessDescriptors(createAdditionalRelatedProcessDescriptors())
				.filters(requestEffective.getFilters().getFilters())
				.build();
	}

	private static final PickingSlotRepoQuery createPickingSlotRowsQuery(final CreateViewRequest request, final List<Integer> allShipmentScheduleIds)
	{
		final int currentShipmentScheduleId = extractCurrentShipmentScheduleId(request);

		//
		// setup the picking slot query and the rowsSupplier which uses the query to retrieve the PickingSlotView's rows.
		final PickingSlotRepoQueryBuilder queryBuilder = PickingSlotRepoQuery.builder()
				.pickingCandidates(PickingCandidate.ONLY_NOT_CLOSED_OR_NOT_RACK_SYSTEM)
				.currentShipmentScheduleId(currentShipmentScheduleId);
		if (allShipmentScheduleIds == null || allShipmentScheduleIds.isEmpty())
		{
			queryBuilder.shipmentScheduleId(currentShipmentScheduleId);
		}
		else
		{
			Check.errorUnless(allShipmentScheduleIds.contains(currentShipmentScheduleId),
					"The given allShipmentScheduleIds has to include the given request's shipmentScheduleId; shipmentScheduleId={}; allShipmentScheduleIds={}; request={}",
					currentShipmentScheduleId, allShipmentScheduleIds, request);

			queryBuilder.shipmentScheduleIds(allShipmentScheduleIds);
		}

		final String barcode = PickingSlotViewFilters.getPickingSlotBarcode(request.getFilters());
		if (!Check.isEmpty(barcode, true))
		{
			queryBuilder.pickingSlotBarcode(barcode);
		}

		return queryBuilder.build();
	}

	private static final int extractCurrentShipmentScheduleId(final CreateViewRequest request)
	{
		final DocumentId pickingRowId = request.getParentRowId();
		final int shipmentScheduleId = pickingRowId.toInt(); // TODO make it more obvious/explicit
		return shipmentScheduleId;
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
				createProcessDescriptorForPickingSlotView(WEBUI_Picking_M_Picking_Candidate_Unprocess.class),

				// label
				createProcessDescriptorForPickingSlotView(WEBUI_Picking_TU_Label.class));
	}

	private static RelatedProcessDescriptor createProcessDescriptorForPickingSlotView(@NonNull final Class<?> processClass)
	{
		final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

		final int processId = adProcessDAO.retriveProcessIdByClassIfUnique(Env.getCtx(), processClass);
		Preconditions.checkArgument(processId > 0, "No AD_Process_ID found for %s", processClass);

		return RelatedProcessDescriptor.builder()
				.processId(processId)
				.webuiQuickAction(true)
				.build();
	}
}
