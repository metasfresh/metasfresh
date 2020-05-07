package de.metas.payment.esr.actionhandler.impl;

/*
 * #%L
 * de.metas.payment.esr
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

import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;

import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.actionhandler.IESRActionHandler;
import de.metas.payment.esr.api.IESRImportBL;
import de.metas.payment.esr.model.I_ESR_ImportLine;

/**
 * This handler allocates the payment against the invoice
 * 
 */
public class AbstractESRActionHandler implements IESRActionHandler
{

	@Override
	public boolean process(final I_ESR_ImportLine line, final String message)
	{
		Check.assumeNotNull(line.getESR_Payment_Action(), "@" + ESRConstants.ERR_ESR_LINE_WITH_NO_PAYMENT_ACTION + "@");

		// 08500: allocate when process
		final I_C_Invoice invoice = line.getC_Invoice();
		final I_C_Payment payment = line.getC_Payment();
		if (invoice != null && payment != null)
		{
			if (!payment.isAllocated() && !invoice.isPaid())
			{
				payment.setC_Invoice_ID(invoice.getC_Invoice_ID());
				final BigDecimal invoiceOpenAmt = Services.get(IInvoiceDAO.class).retrieveOpenAmt(invoice);
				if (payment.getPayAmt().compareTo(invoiceOpenAmt) != 0)
				{
					final BigDecimal overUnderAmt = payment.getPayAmt().subtract(invoiceOpenAmt);
					payment.setOverUnderAmt(overUnderAmt);
				}

				InterfaceWrapperHelper.save(payment);
				Services.get(IESRImportBL.class).linkInvoiceToPayment(line);

				final boolean ignoreProcessed = false;
				Services.get(IInvoiceBL.class).testAllocation(invoice, ignoreProcessed);
			}
		}
		return true;
	}

}
