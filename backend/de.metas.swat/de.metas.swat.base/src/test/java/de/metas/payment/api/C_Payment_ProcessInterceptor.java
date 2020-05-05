/**
 * 
 */
package de.metas.payment.api;

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


import java.math.BigDecimal;

import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;

import de.metas.allocation.api.C_AllocationHdr_Builder;
import de.metas.allocation.api.C_AllocationLine_Builder;
import de.metas.allocation.api.IAllocationBL;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.impl.PlainDocumentBL.IProcessInterceptor;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.payment.PaymentId;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * @author cg
 *
 * Interceptor for processing payment
 */
public class C_Payment_ProcessInterceptor implements IProcessInterceptor
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

		final I_C_Payment payment = InterfaceWrapperHelper.create(wrapper.getCtx(), wrapper.getId(), I_C_Payment.class, trxName);

		// if the invoice is set in payment, create allocation for that invoice in this payment
		if (payment.getC_Invoice_ID() > 0)
		{
			final I_C_Invoice invoice = payment.getC_Invoice();
			
			C_AllocationHdr_Builder allocBuilder = Services.get(IAllocationBL.class)
					.newBuilder()
					.currencyId(invoice.getC_Currency_ID())
					.dateAcct(invoice.getDateAcct())
					.dateTrx(invoice.getDateInvoiced());

			BigDecimal sumAmt = BigDecimal.ZERO;

			final PaymentId paymentId = PaymentId.ofRepoId(payment.getC_Payment_ID());
			final BigDecimal currentAmt = Services.get(IPaymentDAO.class).getAvailableAmount(paymentId);

			sumAmt = sumAmt.add(currentAmt);

			Check.assume(invoice.getC_BPartner_ID() == payment.getC_BPartner_ID(), "{} and {} have the same C_BPartner_ID", invoice, payment);

			final C_AllocationLine_Builder lineBuilder = allocBuilder.addLine()
					.orgId(invoice.getAD_Org_ID())
					.bpartnerId(invoice.getC_BPartner_ID())
					.invoiceId(invoice.getC_Invoice_ID())
					.paymentId(payment.getC_Payment_ID());

			final BigDecimal invoiceOpenAmt = Services.get(IInvoiceDAO.class).retrieveOpenAmt(invoice);

			if (sumAmt.compareTo(invoiceOpenAmt) < 0)
			{
				allocBuilder = lineBuilder
						.amount(currentAmt)
						.lineDone();
			}
			else
			{
				// make sure the allocated amt is not bigger than the open amt of the invoice
				allocBuilder = lineBuilder
						.amount(invoiceOpenAmt.subtract(sumAmt.subtract(currentAmt)))
						.lineDone();
			}

			allocBuilder.create(true);

		}
		
		Services.get(IPaymentBL.class).testAllocation(payment);
		
		// IMPORTANT: we need to save 'payment' and refresh the document wrapper so that become aware of the changes; otherwise, changes will be lost
		InterfaceWrapperHelper.save(payment);
		InterfaceWrapperHelper.refresh(wrapper, true);
		
		return true;
	}

}
