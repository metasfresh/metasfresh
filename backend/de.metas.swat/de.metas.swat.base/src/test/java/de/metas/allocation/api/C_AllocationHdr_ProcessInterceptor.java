/**
 * 
 */
package de.metas.allocation.api;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.List;

import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;

import de.metas.document.engine.IDocument;
import de.metas.document.engine.impl.PlainDocumentBL.IProcessInterceptor;
import de.metas.payment.api.IPaymentBL;

/**
 * @author cg
 *
 *         Interceptor for processing allocation
 */
public class C_AllocationHdr_ProcessInterceptor implements IProcessInterceptor
{
	@Override
	public boolean processIt(IDocument doc, String action) throws Exception
	{
		final POJOWrapper wrapper = POJOWrapper.getWrapper(doc);
		final String trxName = InterfaceWrapperHelper.getTrxName(doc);
		doc.setDocStatus(IDocument.STATUS_Completed);
		wrapper.setValue("DocAction", IDocument.ACTION_Close);
		wrapper.setValue("Processed", true);
		wrapper.setValue("Processing", false);
		InterfaceWrapperHelper.save(wrapper);

		final I_C_AllocationHdr allocHdr = InterfaceWrapperHelper.create(wrapper.getCtx(), wrapper.getId(), I_C_AllocationHdr.class, trxName);
		InterfaceWrapperHelper.refresh(allocHdr, true);
		
		final List<I_C_AllocationLine> allocLines = Services.get(IAllocationDAO.class).retrieveLines(allocHdr);
		for (final I_C_AllocationLine line : allocLines)
		{
			final I_C_Invoice invoice = line.getC_Invoice();
			if (invoice != null
					&& line.getC_BPartner_ID() != invoice.getC_BPartner_ID())
			{
				line.setC_BPartner_ID(invoice.getC_BPartner_ID());
			}
			//
			int C_Payment_ID = line.getC_Payment_ID();

			// Update Payment
			if (C_Payment_ID > 0)
			{
				I_C_Payment payment = line.getC_Payment();
				if (Services.get(IPaymentBL.class).testAllocation(payment))
				{
					InterfaceWrapperHelper.save(payment);
					InterfaceWrapperHelper.refresh(payment, true);
				}
			}

			// Payment - Invoice
			if (C_Payment_ID != 0 && invoice != null)
			{
				// Link to Invoice
				if (invoice.isPaid())
				{
					invoice.setC_Payment_ID(C_Payment_ID);
				}

			}

			// Update Balance / Credit used - Counterpart of MInvoice.completeIt
			if (invoice != null)
			{
				final boolean ignoreProcessed = false;
				Services.get(IInvoiceBL.class).testAllocation(invoice, ignoreProcessed);
				InterfaceWrapperHelper.save(invoice);
			}
		}
		
		// IMPORTANT: we need to save 'allocHdr' and refresh the document wrapper so that become aware of the changes; otherwise, changes will be lost
		InterfaceWrapperHelper.refresh(wrapper, true);

		return true;
	}

}
