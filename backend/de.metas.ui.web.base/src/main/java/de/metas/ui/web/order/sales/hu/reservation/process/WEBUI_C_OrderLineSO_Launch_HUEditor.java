package de.metas.ui.web.order.sales.hu.reservation.process;

import de.metas.document.engine.DocStatus;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult.ViewOpenTarget;
import de.metas.process.ProcessExecutionResult.WebuiViewToOpen;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.material.cockpit.v2.MaterialCockpitV2Service;
import de.metas.ui.web.order.sales.hu.reservation.HUReservationDocumentFilterService;
import de.metas.ui.web.order.sales.hu.reservation.HUsReservationViewFactory;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

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
	@NonNull private final IOrderBL orderBL = Services.get(IOrderBL.class);
	@NonNull private final HUReservationService huReservationService = SpringContextHolder.instance.getBean(HUReservationService.class);
	@NonNull private final HUReservationDocumentFilterService huReservationDocumentFilterService = SpringContextHolder.instance.getBean(HUReservationDocumentFilterService.class);
	@NonNull private final IViewsRepository viewsRepo = SpringContextHolder.instance.getBean(IViewsRepository.class);
	@NonNull private final MaterialCockpitV2Service materialCockpitV2Service = SpringContextHolder.instance.getBean(MaterialCockpitV2Service.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		final OrderId salesOrderId = context.getSingleSelectedRecordId(OrderId.class);
		final I_C_Order salesOrder = orderBL.getById(salesOrderId);
		if (salesOrder == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No C_Order selected");
		}
		if (!salesOrder.isSOTrx())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("only sales orders are allowed");
		}

		final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(salesOrder.getDocStatus());
		if (!huReservationService.isReservationAllowedForDocStatus(docStatus))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("C_Order.DocStatus=" + docStatus);
		}

		final Set<TableRecordReference> salesOrderLines = context.getSelectedIncludedRecords();
		if (salesOrderLines.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		else if (salesOrderLines.size() != 1)
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final ViewId viewId = createView();

		getResult().setWebuiViewToOpen(WebuiViewToOpen.builder()
				.viewId(viewId.getViewId())
				.target(ViewOpenTarget.ModalOverlay)
				.build());

		return MSG_OK;
	}

	@NonNull
	private OrderId getOrderId()
	{
		return getRecordIdAssumingTableName(I_C_Order.Table_Name, OrderId::ofRepoId);
	}

	private OrderLineId getSingleOrderLineId()
	{
		return CollectionUtils.singleElement(getSelectedIncludedRecordIds(I_C_OrderLine.class, OrderLineId::ofRepoId));
	}

	private ViewId createView()
	{
		final ViewId viewId = materialCockpitV2Service.createMaterialCockpitView(createMaterialCockpitViewContext());
		if (viewId != null)
		{
			return viewId;
		}

		// fallback
		return createHUEditorView();
	}

	/**
	 * Original behavior: open the HU Reservation Editor view.
	 */
	private ViewId createHUEditorView()
	{
		final OrderLineId orderLineId = getSingleOrderLineId();
		final DocumentFilter stickyFilters = huReservationDocumentFilterService.createOrderLineDocumentFilter(orderLineId);

		final IView view = viewsRepo
				.createView(CreateViewRequest
						.builder(HUsReservationViewFactory.WINDOW_ID)
						.addStickyFilters(stickyFilters)
						.setParameter(MaterialCockpitViewContext.VIEW_PARAMETER_NAME, createMaterialCockpitViewContext())
						.build());
		return view.getViewId();
	}

	private MaterialCockpitViewContext createMaterialCockpitViewContext()
	{
		return MaterialCockpitViewContext.builder()
				.sourceSelectionId(getPinstanceId())
				.salesOrderAndLineId(getSalesOrderAndLineId())
				.build();
	}

	private @NotNull OrderAndLineId getSalesOrderAndLineId()
	{
		return OrderAndLineId.of(getOrderId(), getSingleOrderLineId());
	}
}
