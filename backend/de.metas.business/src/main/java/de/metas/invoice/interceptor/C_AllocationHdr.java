/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.invoice.interceptor;

import de.metas.allocation.api.IAllocationDAO;
import de.metas.allocation.api.PaymentAllocationId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.List;

@Interceptor(I_C_AllocationHdr.class)
@Component
public class C_AllocationHdr
{
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IAllocationDAO allocationDAO = Services.get(IAllocationDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	/**
	 * Updates C_Invoice.IsPaid on all referenced invoices each time an allocation is completed, reversed etc.
	 * Background: when a credit memo was allocated against a zpayment and a service-invoice, the check was not done, so the credit memo remained with Paid=N.
	 * TODO: see if we can get rid of all other calls to {@link IInvoiceBL#testAllocation(I_C_Invoice, boolean)}.
	 */
	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE,
			ModelValidator.TIMING_AFTER_REACTIVATE,
			ModelValidator.TIMING_AFTER_REVERSECORRECT,
			ModelValidator.TIMING_AFTER_VOID })
	public void testInvoiceAllocation(@NonNull final I_C_AllocationHdr allocationHdr)
	{
		trxManager.runAfterCommit(() -> testInvoiceAllocation0(PaymentAllocationId.ofRepoId(allocationHdr.getC_AllocationHdr_ID())));
	}

	private void testInvoiceAllocation0(@NonNull final PaymentAllocationId allocationId)
	{
		final I_C_AllocationHdr allocationHdrRecord = allocationDAO.getById(allocationId);
		final List<I_C_AllocationLine> allocationLineRecords = allocationDAO.retrieveLines(allocationHdrRecord);
		for (final I_C_AllocationLine allocationLineRecord : allocationLineRecords)
		{
			if (allocationLineRecord.getC_Invoice_ID() > 0)
			{
				final boolean ignoreProcessed = false;
				invoiceBL.testAllocation(allocationLineRecord.getC_Invoice(), ignoreProcessed);
			}
		}
	}
}
