package de.metas.ui.web.order.products_proposal.process;

import de.metas.adempiere.model.I_C_Order;
import de.metas.document.engine.DocStatus;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.order.products_proposal.view.OrderProductsProposalViewFactory;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;

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

public class WEBUI_Order_ProductsProposal_Launcher extends WEBUI_ProductsProposal_Launcher_Template implements IProcessPrecondition
{
	private final OrderProductsProposalViewFactory productsProposalViewFactory = SpringContextHolder.instance.getBean(OrderProductsProposalViewFactory.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("one and only one order shall be selected");
		}

		final OrderId orderId = OrderId.ofRepoId(context.getSingleSelectedRecordId());
		final I_C_Order salesOrder = orderDAO.getById(orderId, I_C_Order.class);
		final DocStatus docStatus = DocStatus.ofCode(salesOrder.getDocStatus());
		if (!docStatus.isDraftedOrInProgress())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("only Drafted or InProgress orders are allowed");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected CreateViewRequest createViewRequest(final TableRecordReference recordRef)
	{
		return productsProposalViewFactory.createViewRequest(recordRef);
	}
}
