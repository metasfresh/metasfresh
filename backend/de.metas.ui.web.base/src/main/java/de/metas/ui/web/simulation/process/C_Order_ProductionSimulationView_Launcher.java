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

import de.metas.common.util.Check;
import de.metas.material.dispo.service.simulation.ProductionSimulationService;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.OrderLineDescriptor;
import de.metas.material.event.simulation.DeactivateAllSimulatedCandidatesEvent;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
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
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.Env;

public class C_Order_ProductionSimulationView_Launcher extends JavaProcess implements IProcessPrecondition
{
	public static final String VIEW_FACTORY_PARAM_DOCUMENT_LINE_DESCRIPTOR = "OrderLineDescriptor";

	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	private final ProductionSimulationService productionSimulationService = SpringContextHolder.instance.getBean(ProductionSimulationService.class);
	private final PostMaterialEventService postMaterialEventService = SpringContextHolder.instance.getBean(PostMaterialEventService.class);
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
			productionSimulationService.processSimulatedDemand(orderLineId, salesOrder, orderLineDescriptor);

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
}