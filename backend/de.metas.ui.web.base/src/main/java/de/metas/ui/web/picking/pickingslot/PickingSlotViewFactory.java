package de.metas.ui.web.picking.pickingslot;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.inout.ShipmentScheduleId;
import de.metas.picking.qrcode.PickingSlotQRCode;
import de.metas.printing.esb.base.util.Check;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.process.RelatedProcessDescriptor.DisplayPlace;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.picking.PickingConstants;
import de.metas.ui.web.picking.pickingslot.PickingSlotRepoQuery.PickingSlotRepoQueryBuilder;
import de.metas.ui.web.picking.pickingslot.process.WEBUI_Picking_ForcePickToComputedHU;
import de.metas.ui.web.picking.pickingslot.process.WEBUI_Picking_ForcePickToExistingHU;
import de.metas.ui.web.picking.pickingslot.process.WEBUI_Picking_ForcePickToNewHU;
import de.metas.ui.web.picking.pickingslot.process.WEBUI_Picking_HUEditor_Launcher;
import de.metas.ui.web.picking.pickingslot.process.WEBUI_Picking_M_Picking_Candidate_Process;
import de.metas.ui.web.picking.pickingslot.process.WEBUI_Picking_M_Picking_Candidate_Unprocess;
import de.metas.ui.web.picking.pickingslot.process.WEBUI_Picking_M_Source_HU_Delete;
import de.metas.ui.web.picking.pickingslot.process.WEBUI_Picking_PickQtyToComputedHU;
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
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.util.Util.ArrayKey;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static de.metas.ui.web.picking.PickingConstants.SYS_CONFIG_SHOW_ALL_PICKING_CANDIDATES_ON_PICKING_SLOTS;

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
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final PickingSlotViewRepository pickingSlotRepo;

	private final CCache<ArrayKey, ViewLayout> viewLayoutCache = CCache.newCache("PickingSlotViewLayout", 1, CCache.EXPIREMINUTES_Never);
	private final CCache<Integer, DocumentFilterDescriptorsProvider> filterDescriptorsProviderCache = CCache.newCache("PickingSlotView.FilterDescriptorsProvider", 1, CCache.EXPIREMINUTES_Never);

	public PickingSlotViewFactory(final PickingSlotViewRepository pickingSlotRepo) {this.pickingSlotRepo = pickingSlotRepo;}

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
		return filterDescriptorsProviderCache.getOrLoad(0, PickingSlotViewFilters::createFilterDescriptorsProvider);
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
	 * @param allShipmentScheduleIds the shipment schedule IDs to display picking slots for; <br>
	 *            may be {@code null} or empty, in this case we assume that only the given {@code request}'s {@code shipmentScheduleId} is available.
	 */
	public PickingSlotView createView(
			@NonNull final CreateViewRequest request,
			@Nullable final Set<ShipmentScheduleId> allShipmentScheduleIds)
	{
		final DocumentFilterList filters = request.getFiltersUnwrapped(getFilterDescriptorsProvider());

		final ViewId pickingViewId = request.getParentViewId();
		final DocumentId pickingRowId = request.getParentRowId();
		final ViewId pickingSlotViewId = PickingSlotViewsIndexStorage.createViewId(pickingViewId, pickingRowId);
		final ShipmentScheduleId currentShipmentScheduleId = extractCurrentShipmentScheduleId(request);
		final boolean includeAllShipmentSchedules = sysConfigBL.getBooleanValue(SYS_CONFIG_SHOW_ALL_PICKING_CANDIDATES_ON_PICKING_SLOTS, false);

		final CreatePickingSlotRepoQueryReq createQueryReq = CreatePickingSlotRepoQueryReq.builder()
				.allShipmentScheduleIds(allShipmentScheduleIds)
				.currentShipmentScheduleId(currentShipmentScheduleId)
				.filters(filters)
				.includeAllShipmentSchedules(includeAllShipmentSchedules)
				.build();

		final PickingSlotRepoQuery query = createPickingSlotRowsQuery(createQueryReq);

		final Supplier<List<PickingSlotRow>> rowsSupplier = () -> pickingSlotRepo.retrieveRows(query);

		return PickingSlotView.builder()
				.viewId(pickingSlotViewId)
				.parentViewId(pickingViewId)
				.parentRowId(pickingRowId)
				.currentShipmentScheduleId(currentShipmentScheduleId)
				.rowsSupplier(rowsSupplier)
				.additionalRelatedProcessDescriptors(createAdditionalRelatedProcessDescriptors())
				.filters(filters)
				.build();
	}

	private static PickingSlotRepoQuery createPickingSlotRowsQuery(@NonNull final CreatePickingSlotRepoQueryReq pickingSlotRepoQueryReq)
	{
		//
		// setup the picking slot query and the rowsSupplier which uses the query to retrieve the PickingSlotView's rows.
		final PickingSlotRepoQueryBuilder queryBuilder = PickingSlotRepoQuery.builder()
				.onlyNotClosedOrNotRackSystem(true)
				.currentShipmentScheduleId(pickingSlotRepoQueryReq.getCurrentShipmentScheduleId());

		final Set<ShipmentScheduleId> shipmentScheduleIds = pickingSlotRepoQueryReq.isIncludeAllShipmentSchedules()
				? ImmutableSet.of()
				: Check.isEmpty(pickingSlotRepoQueryReq.getAllShipmentScheduleIds())
					? ImmutableSet.of(pickingSlotRepoQueryReq.getCurrentShipmentScheduleId())
				    : pickingSlotRepoQueryReq.getAllShipmentScheduleIds();

		if (!Check.isEmpty(shipmentScheduleIds))
		{
			Check.errorUnless(shipmentScheduleIds.contains(pickingSlotRepoQueryReq.getCurrentShipmentScheduleId()),
					"The given allShipmentScheduleIds has to include the given request's shipmentScheduleId; request={}",
					pickingSlotRepoQueryReq);
		}

		queryBuilder.shipmentScheduleIds(shipmentScheduleIds);

		final PickingSlotQRCode pickingSlotQRCode = PickingSlotViewFilters.getPickingSlotQRCode(pickingSlotRepoQueryReq.getFilters());
		if (pickingSlotQRCode != null)
		{
			queryBuilder.pickingSlotQRCode(pickingSlotQRCode);
		}

		return queryBuilder.build();
	}

	private static ShipmentScheduleId extractCurrentShipmentScheduleId(final CreateViewRequest request)
	{
		final DocumentId pickingRowId = request.getParentRowId();
		return ShipmentScheduleId.ofRepoId(pickingRowId.toInt()); // TODO make it more obvious/explicit
	}

	private List<RelatedProcessDescriptor> createAdditionalRelatedProcessDescriptors()
	{
		return ImmutableList.of(
				// allow to open the HU-editor for various picking related purposes
				createProcessDescriptorForPickingSlotView(WEBUI_Picking_HUEditor_Launcher.class),

				// fine-picking related processes
				createProcessDescriptorForPickingSlotView(WEBUI_Picking_PickQtyToNewHU.class),
				createProcessDescriptorForPickingSlotView(WEBUI_Picking_PickQtyToComputedHU.class),
				createProcessDescriptorForPickingSlotView(WEBUI_Picking_PickQtyToExistingHU.class),
				createProcessDescriptorForPickingSlotView(WEBUI_Picking_ReturnQtyToSourceHU.class),
				createProcessDescriptorForPickingSlotView(WEBUI_Picking_ForcePickToNewHU.class),
				createProcessDescriptorForPickingSlotView(WEBUI_Picking_ForcePickToComputedHU.class),
				createProcessDescriptorForPickingSlotView(WEBUI_Picking_ForcePickToExistingHU.class),

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

		final AdProcessId processId = adProcessDAO.retrieveProcessIdByClassIfUnique(processClass);
		Preconditions.checkArgument(processId != null, "No AD_Process_ID found for %s", processClass);

		return RelatedProcessDescriptor.builder()
				.processId(processId)
				.displayPlace(DisplayPlace.ViewQuickActions)
				.build();
	}

	@Value
	@Builder
	private static class CreatePickingSlotRepoQueryReq
	{
		DocumentFilterList filters;

		@NonNull
		ShipmentScheduleId currentShipmentScheduleId;

		Set<ShipmentScheduleId> allShipmentScheduleIds;

		boolean includeAllShipmentSchedules;
	}
}
