package de.metas.ui.web.order.sales.hu.reservation.process;

import java.util.List;

import org.adempiere.util.Services;
import org.adempiere.util.collections.ListUtils;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.model.WarehousePickingGroup;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Warehouse;
import org.springframework.beans.factory.annotation.Autowired;

import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.reservation.HuReservationService;
import de.metas.printing.esb.base.util.Check;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUIdsFilterHelper;
import de.metas.ui.web.picking.husToPick.HUsToPickViewFactory;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.json.JSONViewDataType;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class WEBUI_C_OrderLineSO_Make_HuReservation
		extends ViewBasedProcessTemplate
		implements IProcessPrecondition
{
	@Autowired
	private HuReservationService huReservationService;

	@Autowired
	private IViewsRepository viewsRepo;

	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final List<I_C_OrderLine> selectedOrderLineRecords = getSelectedIncludedRecords(I_C_OrderLine.class);
		if (selectedOrderLineRecords.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		if (selectedOrderLineRecords.size() != 1)
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final I_C_OrderLine orderLineRecord = ListUtils.singleElement(selectedOrderLineRecords);

		final String docStatus = orderLineRecord.getC_Order().getDocStatus();
		final boolean reservationIsPossible = huReservationService
				.getDocstatusesThatAllowReservation()
				.contains(docStatus);
		if (!reservationIsPossible)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("C_Order.DocStatus=" + docStatus);
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final List<Integer> availableHUIdsToPick = retrieveAvailableHuIdsForCurrentSalesOrderLine();

		final IView husToPickView = createHUEditorView(availableHUIdsToPick);

		getResult().setWebuiIncludedViewIdToOpen(husToPickView.getViewId().getViewId());
		getResult().setWebuiViewProfileId("availableHUsForSalesOrderLine");

		return MSG_OK;
	}

	private List<Integer> retrieveAvailableHuIdsForCurrentSalesOrderLine()
	{
		final I_C_OrderLine orderLineRecord = ListUtils.singleElement(getSelectedIncludedRecords(I_C_OrderLine.class));


		final I_M_Warehouse orderLineWarehouse = Check.assumeNotNull(
				Services.get(IWarehouseAdvisor.class).evaluateWarehouse(orderLineRecord),
				"For currently selected sales order line, there needs to be a warehouse; C_OrderLine={}",orderLineRecord);
		final WarehouseId warehouseId = WarehouseId.ofRepoId(orderLineWarehouse.getM_Warehouse_ID());

		final WarehousePickingGroup group = Services.get(IWarehouseDAO.class).getWarehousePickingGroupContainingWarehouseId(warehouseId);

		final IHUQueryBuilder query = handlingUnitsDAO
				.createHUQueryBuilder()
				.addOnlyWithProductId(orderLineRecord.getM_Product_ID())
				.addOnlyInWarehouseIds(group.getWarehouseIds());


		// TODO: add picking warehouse, falling back to orderLine's/order's warehouse
		// TODO: consider adding ASI-Id if set, or filter in memory

		return query
				.createQuery()
				.listIds();
	}

	private IView createHUEditorView(@NonNull final List<Integer> availableHUIdsToPick)
	{
		final IViewRow pickingSlotRow = getSingleSelectedRow();
		final ViewId pickingSlotViewId = getView().getViewId();

		final IView husToPickView = viewsRepo.createView(
				CreateViewRequest.builder(HUsToPickViewFactory.WINDOW_ID, JSONViewDataType.includedView)
						.setParentViewId(pickingSlotViewId)
						.setParentRowId(pickingSlotRow.getId())
						.addStickyFilters(HUIdsFilterHelper.createFilter(availableHUIdsToPick))
						.build());
		return husToPickView;
	}

}
