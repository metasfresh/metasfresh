/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.ui.web.simulation.process;

import de.metas.bpartner.BPartnerId;
import de.metas.common.util.Check;
import de.metas.common.util.CoalesceUtil;
import de.metas.material.event.MaterialEventObserver;
import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.OrderLineDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.simulation.DeactivateAllSimulatedCandidatesEvent;
import de.metas.material.event.simulation.SimulatedDemandCreatedEvent;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.simulation.ProductionSimulationViewFactory;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

public class C_Order_ProductionSimulationView_Launcher extends JavaProcess implements IProcessPrecondition
{
	public static final String VIEW_FACTORY_PARAM_DOCUMENT_LINE_DESCRIPTOR = "OrderLineDescriptor";

	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final ModelProductDescriptorExtractor productDescriptorFactory = SpringContextHolder.instance.getBean(ModelProductDescriptorExtractor.class);
	private final PostMaterialEventService postMaterialEventService = SpringContextHolder.instance.getBean(PostMaterialEventService.class);
	private final MaterialEventObserver materialEventObserver = SpringContextHolder.instance.getBean(MaterialEventObserver.class);
	private final IViewsRepository viewsFactory = SpringContextHolder.instance.getBean(IViewsRepository.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.getSelectedIncludedRecords().size() != 1)
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoId(Check.singleElement(getSelectedIncludedRecordIds(I_C_OrderLine.class)));

		final I_C_Order salesOrder = orderDAO.getById(OrderId.ofRepoId(getRecord_ID()));

		final OrderLineDescriptor orderLineDescriptor = OrderLineDescriptor.builder()
				.orderId(salesOrder.getC_Order_ID())
				.orderLineId(orderLineId.getRepoId())
				.orderBPartnerId(salesOrder.getC_BPartner_ID())
				.docTypeId(salesOrder.getC_DocType_ID())
				.build();

		try
		{
			processSimulatedDemand(orderLineId, salesOrder, orderLineDescriptor);

			final ViewId viewId = viewsFactory.createView(CreateViewRequest.builder(ProductionSimulationViewFactory.WINDOWID)
																  .setParameter(VIEW_FACTORY_PARAM_DOCUMENT_LINE_DESCRIPTOR, orderLineDescriptor)
																  .build())
					.getViewId();

			getResult().setWebuiViewToOpen(ProcessExecutionResult.WebuiViewToOpen.builder()
												   .viewId(viewId.getViewId())
												   .target(ProcessExecutionResult.ViewOpenTarget.ModalOverlay)
												   .build());
		}
		catch (final Exception exception)
		{
			log.error("Error encountered while launching ProductionSimulationModal:", exception);

			postMaterialEventService.postEventNow(DeactivateAllSimulatedCandidatesEvent.builder()
														  .eventDescriptor(EventDescriptor.ofClientAndOrg(Env.getClientId(), Env.getOrgId()))
														  .build());

			throw AdempiereException.wrapIfNeeded(exception);
		}

		return MSG_OK;
	}

	private void processSimulatedDemand(
			@NonNull final OrderLineId orderLineId,
			@NonNull final I_C_Order salesOrder,
			@NonNull final OrderLineDescriptor orderLineDescriptor)
	{

		final String traceId = UUID.randomUUID().toString();

		materialEventObserver.observe(traceId);

		final MaterialDescriptor materialDescriptor = createMaterialDescriptor(salesOrder, orderLineId);

		final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(salesOrder.getAD_Client_ID(), salesOrder.getAD_Org_ID());

		final SimulatedDemandCreatedEvent simulatedDemandCreatedEvent = SimulatedDemandCreatedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientOrgAndTraceId(clientAndOrgId, traceId))
				.documentLineDescriptor(orderLineDescriptor)
				.materialDescriptor(materialDescriptor)
				.build();

		postMaterialEventService.postEventNow(simulatedDemandCreatedEvent);

		try
		{
			materialEventObserver.awaitProcessing(traceId);
		}
		catch (final Exception exception)
		{
			log.error("Error encountered while awaiting processing for traceId:" + traceId, exception);

			postMaterialEventService.postEventNow(DeactivateAllSimulatedCandidatesEvent.builder()
														  .eventDescriptor(EventDescriptor.ofClientAndOrg(Env.getClientId(), Env.getOrgId()))
														  .build());
		}
	}

	@NonNull
	private MaterialDescriptor createMaterialDescriptor(
			@NonNull final I_C_Order salesOrder,
			@NonNull final OrderLineId orderLineId)
	{
		final I_C_OrderLine orderLine = orderDAO.getOrderLineById(orderLineId);

		final ZoneId timeZone = orgDAO.getTimeZone(OrgId.ofRepoId(salesOrder.getAD_Org_ID()));

		final ZonedDateTime preparationDate = CoalesceUtil.coalesceSuppliersNotNull(
				() -> TimeUtil.asZonedDateTime(salesOrder.getPreparationDate(), timeZone),
				() -> TimeUtil.asZonedDateTime(salesOrder.getDatePromised(), timeZone));

		final ProductDescriptor productDescriptor = productDescriptorFactory.createProductDescriptor(orderLine);

		return MaterialDescriptor.builder()
				.date(preparationDate.toInstant())
				.productDescriptor(productDescriptor)
				.warehouseId(WarehouseId.ofRepoIdOrNull(orderLine.getM_Warehouse_ID()))
				.customerId(BPartnerId.ofRepoId(orderLine.getC_BPartner_ID()))
				.quantity(orderLine.getQtyOrdered())
				.build();
	}
}