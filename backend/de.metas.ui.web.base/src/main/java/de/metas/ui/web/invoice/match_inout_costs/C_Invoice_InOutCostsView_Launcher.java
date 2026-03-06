/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.ui.web.invoice.match_inout_costs;

import de.metas.document.engine.DocStatus;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.lang.SOTrx;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;

public class C_Invoice_InOutCostsView_Launcher extends JavaProcess implements IProcessPrecondition
{
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IViewsRepository viewsRepo = SpringContextHolder.instance.getBean(IViewsRepository.class);
	private final InOutCostsViewFactory inOutCostsViewFactory = SpringContextHolder.instance.getBean(InOutCostsViewFactory.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("one invoice only shall be selected");
		}
		if (!context.isSingleIncludedRecordSelected())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("one invoice line only shall be selected");
		}

		final InvoiceId invoiceId = context.getSingleSelectedRecordId(InvoiceId.class);
		final I_C_Invoice invoice = invoiceBL.getById(invoiceId);
		if (!DocStatus.ofNullableCodeOrUnknown(invoice.getDocStatus()).isCompleted())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("only completed invoices");
		}

		// TODO: check if the invoice line has something to allocate

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final InvoiceId invoiceId = InvoiceId.ofRepoId(getRecord_ID());
		final InvoiceAndLineId invoiceAndLineId = InvoiceAndLineId.ofRepoId(invoiceId, getSingleSelectedIncludedRecordIds(I_C_InvoiceLine.class));

		final I_C_Invoice invoice = invoiceBL.getById(invoiceId);
		final SOTrx soTrx = SOTrx.ofBoolean(invoice.isSOTrx());

		final IView view = viewsRepo.createView(inOutCostsViewFactory.createViewRequest(soTrx, invoiceAndLineId));
		final ViewId viewId = view.getViewId();

		getResult().setWebuiViewToOpen(ProcessExecutionResult.WebuiViewToOpen.builder()
				.viewId(viewId.toJson())
				.target(ProcessExecutionResult.ViewOpenTarget.ModalOverlay)
				.build());

		return MSG_OK;
	}
}
