/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.payment_allocation.process;

import de.metas.banking.payment.paymentallocation.PaymentAllocationRepository;
import de.metas.bpartner.BPartnerId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult.ViewOpenTarget;
import de.metas.process.ProcessExecutionResult.WebuiViewToOpen;
import de.metas.ui.web.payment_allocation.PaymentsViewFactory;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.util.Services;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;

public class PaymentView_Launcher_From_C_Invoice_SingleDocument extends JavaProcess
{
	private final IViewsRepository viewsFactory;
	final IInvoiceDAO invoiceDAO;
	final PaymentAllocationRepository allocationRepository;

	public PaymentView_Launcher_From_C_Invoice_SingleDocument()
	{
		viewsFactory = SpringContextHolder.instance.getBean(IViewsRepository.class);
		allocationRepository = SpringContextHolder.instance.getBean(PaymentAllocationRepository.class);
		invoiceDAO = Services.get(IInvoiceDAO.class);
	}

	@Override
	protected String doIt()
	{
		final I_C_Invoice invoice = invoiceDAO.getByIdInTrx(InvoiceId.ofRepoId(getRecord_ID()));
		final BPartnerId bPartnerId = BPartnerId.ofRepoId(invoice.getC_BPartner_ID());

		final ViewId viewId = viewsFactory.createView(CreateViewRequest.builder(PaymentsViewFactory.WINDOW_ID)
				.setParameter(PaymentsViewFactory.PARAMETER_TYPE_BPARTNER_ID, bPartnerId)
				.build())
				.getViewId();

		getResult().setWebuiViewToOpen(WebuiViewToOpen.builder()
				.viewId(viewId.getViewId())
				.target(ViewOpenTarget.ModalOverlay)
				.build());

		return MSG_OK;
	}
}
