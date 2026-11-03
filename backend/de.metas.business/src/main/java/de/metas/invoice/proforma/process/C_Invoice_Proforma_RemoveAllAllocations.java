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
import de.metas.invoice.service.IInvoiceBL;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;

/**
 * Removes every active {@code C_Proforma_Order_Alloc} row linked to the proforma invoice the
 * process runs on. Hidden when the invoice is not a purchase proforma, when it has been reversed
 * or voided, or when it has no active allocations.
 */
public class C_Invoice_Proforma_RemoveAllAllocations extends JavaProcess implements IProcessPrecondition
{
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final ProformaOrderAllocService proformaOrderAllocService = SpringContextHolder.instance.getBean(ProformaOrderAllocService.class);

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
			return ProcessPreconditionsResolution.rejectWithInternalReason("Cannot remove allocations from a reversed or voided proforma invoice");
		}

		final InvoiceId proformaInvoiceId = InvoiceId.ofRepoId(invoice.getC_Invoice_ID());
		if (!proformaOrderAllocService.hasAllocations(proformaInvoiceId))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Proforma invoice has no active allocations");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final InvoiceId proformaInvoiceId = getRecordIdAssumingTableName(I_C_Invoice.Table_Name, InvoiceId::ofRepoId);
		proformaOrderAllocService.deallocateAll(proformaInvoiceId);
		return MSG_OK;
	}
}
