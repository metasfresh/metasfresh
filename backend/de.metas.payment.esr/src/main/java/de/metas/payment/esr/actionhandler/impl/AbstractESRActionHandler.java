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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.invoice.service.IInvoiceDAO;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentDAO;
import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.actionhandler.IESRActionHandler;
import de.metas.payment.esr.api.IESRImportBL;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;

import java.math.BigDecimal;

/**
 * This handler allocates the payment against the invoice
 * 
 */
public class AbstractESRActionHandler implements IESRActionHandler
{
	protected final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);

	@Override
	public boolean process(final I_ESR_ImportLine line, final String message)
	{
		Check.assumeNotNull(line.getESR_Payment_Action(), "@" + ESRConstants.ERR_ESR_LINE_WITH_NO_PAYMENT_ACTION + "@");

		// 08500: allocate when process
		final I_C_Invoice invoice = line.getC_Invoice();

		final PaymentId paymentId = PaymentId.ofRepoIdOrNull(line.getC_Payment_ID());
		final I_C_Payment payment = paymentId == null ? null
				: paymentDAO.getById(paymentId);

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
			}
		}
		return true;
	}

}
