/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.invoice.proforma.process;

import de.metas.invoice.InvoiceId;
import de.metas.invoice.proforma.ProformaOrderAllocService;
import de.metas.invoice.proforma.ProformaOrderAllocateRequest;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.order.OrderId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;

public class C_Invoice_Proforma_Allocate_Order extends JavaProcess implements IProcessPrecondition
{
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

	@NonNull private final ProformaOrderAllocService proformaOrderAllocService = SpringContextHolder.instance.getBean(ProformaOrderAllocService.class);

	private static final String PARAM_C_Order_ID = "C_Order_ID";
	@Param(parameterName = PARAM_C_Order_ID, mandatory = true)
	private int p_C_Order_ID;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		if (!I_C_Invoice.Table_Name.equals(context.getTableName()))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Process must be run from Invoice window");
		}

		final I_C_Invoice invoice = invoiceBL.getById(context.getSingleSelectedRecordId(InvoiceId.class));
		if (!invoiceBL.isPurchaseProforma(invoice))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Invoice must be a Purchase Proforma Invoice (APF)");
		}

		if (!invoiceBL.isCompletedOrClosed(invoice) || invoiceBL.isReversal(invoice))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Cannot allocate reversed or voided invoice");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final InvoiceId proformaInvoiceId = InvoiceId.ofRepoId(getRecord_ID());
		final OrderId purchaseOrderId = OrderId.ofRepoIdOrNull(p_C_Order_ID);

		Check.assumeNotNull(purchaseOrderId, "Purchase Order Para should be set");

		final ProformaOrderAllocateRequest request = ProformaOrderAllocateRequest.builder()
				.proformaInvoiceId(proformaInvoiceId)
				.purchaseOrderId(purchaseOrderId)
				.build();

		proformaOrderAllocService.allocate(request);

		return MSG_OK;
	}
}
