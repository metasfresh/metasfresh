package de.metas.ui.web.order.sales.hu.reservation.process;

import java.util.List;
import java.util.Set;

import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.util.Services;
import org.adempiere.util.collections.ListUtils;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.Adempiere;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Warehouse;

import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.reservation.HuReservationService;
import de.metas.order.OrderLineId;
import de.metas.printing.esb.base.util.Check;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.purchasecandidate.SalesOrderLine;
import de.metas.purchasecandidate.SalesOrderLineRepository;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.handlingunits.HUIdsFilterHelper;
import de.metas.ui.web.order.sales.hu.reservation.HUsReservationViewFactory;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
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

public class WEBUI_C_OrderLineSO_Launch_HUEditor
		extends JavaProcess
		implements IProcessPrecondition
{
	public static final String VIEW_PARAM_PARENT_SALES_ORDER_LINE_ID = "WEBUI_C_OrderLineSO_ID";

	private final transient HuReservationService huReservationService = Adempiere.getBean(HuReservationService.class);
	private final transient SalesOrderLineRepository salesOrderLineRepository = Adempiere.getBean(SalesOrderLineRepository.class);
	private final transient IViewsRepository viewsRepo = Adempiere.getBean(IViewsRepository.class);
	private final transient IWarehouseAdvisor warehouseAdvisor = Services.get(IWarehouseAdvisor.class);
	private final transient IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		final I_C_Order salesOrder = context.getSelectedModel(I_C_Order.class);

		if (!salesOrder.isSOTrx())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("only sales orders are allowed");
		}

		final String docStatus = salesOrder.getDocStatus();
		final boolean reservationIsPossible = huReservationService
				.getDocstatusesThatAllowReservation()
				.contains(docStatus);
		if (!reservationIsPossible)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("C_Order.DocStatus=" + docStatus);
		}

		final Set<TableRecordReference> salesOrderLines = context.getSelectedIncludedRecords();
		if (salesOrderLines.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		if (salesOrderLines.size() != 1)
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final ViewId viewId = createHUEditorView();

		getResult().setWebuiIncludedViewIdToOpen(viewId.getViewId());

		return MSG_OK;
	}

	private ViewId createHUEditorView()
	{
		final Integer singleElement = ListUtils.singleElement(getSelectedIncludedRecordIds(I_C_OrderLine.class));

		final SalesOrderLine salesOrderLine = salesOrderLineRepository.getById(OrderLineId.ofRepoId(singleElement));

		final IView view = viewsRepo
				.createView(CreateViewRequest
						.builder(HUsReservationViewFactory.WINDOW_ID)
						.addStickyFilters(createDocumentFilter(salesOrderLine))
						.setParameter(VIEW_PARAM_PARENT_SALES_ORDER_LINE_ID, salesOrderLine.getId().getOrderLineId())
						.build());
		return view.getViewId();
	}

	private DocumentFilter createDocumentFilter(@NonNull final SalesOrderLine salesOrderLine)
	{
		final I_M_Warehouse //
		orderLineWarehouse = warehouseAdvisor.evaluateWarehouse(salesOrderLine.getId().getOrderLineId());
		Check.assumeNotNull(orderLineWarehouse, "For currently selected sales order line, there needs to be a warehouse; salesOrderLine={}", salesOrderLine);

		final List<WarehouseId> //
		warehouseIds = warehouseDAO.getWarehouseIdsOfSamePickingGroup(WarehouseId.ofRepoId(orderLineWarehouse.getM_Warehouse_ID()));

		final ImmutableAttributeSet //
		attributeSet = ImmutableAttributeSet.ofAttributesetInstanceId(salesOrderLine.getAsiId());

		final IHUQueryBuilder huQuery = handlingUnitsDAO
				.createHUQueryBuilder()
				.addOnlyWithProductId(salesOrderLine.getProductId())
				.addOnlyInWarehouseIds(warehouseIds)
				.addHUStatusToInclude(X_M_HU.HUSTATUS_Active)
				.addOnlyWithAttributes(attributeSet)
				.setExcludeReservedToOtherThan(salesOrderLine.getId().getOrderLineId());

		return HUIdsFilterHelper.createFilter(huQuery);
	}
}
