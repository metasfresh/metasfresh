package de.metas.banking.payment.impl;

/*
 * #%L
 * de.metas.banking.base
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

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Invoice;

import de.metas.adempiere.model.I_C_PaySelectionLine;
import de.metas.banking.payment.IPaymentRequestBL;
import de.metas.banking.payment.IPaymentRequestDAO;
import de.metas.banking.service.IBankingBPBankAccountDAO;
import de.metas.interfaces.I_C_BP_BankAccount;
import de.metas.payment.model.I_C_Payment_Request;

/**
 * @author al
 */
public class PaymentRequestBL implements IPaymentRequestBL
{
	private static final String DYNATTR_UpdatedFromPaymentRequest = PaymentRequestBL.class.getName() + "#UpdatedFromPaymentRequest";

	@Override
	public I_C_Payment_Request createNewFromTemplate(final I_C_Payment_Request request)
	{
		final I_C_Payment_Request clone = InterfaceWrapperHelper.newInstance(I_C_Payment_Request.class, request);
		clone.setC_BP_BankAccount(request.getC_BP_BankAccount());
		clone.setAmount(request.getAmount());
		clone.setReference(request.getReference());
		return clone;
	}

	@Override
	public boolean isUpdatedFromPaymentRequest(final I_C_PaySelectionLine paySelectionLine)
	{
		if (paySelectionLine == null)
		{
			return false;
		}

		final Boolean updated = InterfaceWrapperHelper.getDynAttribute(paySelectionLine, DYNATTR_UpdatedFromPaymentRequest);
		return updated == null ? false : updated;
	}

	@Override
	public boolean updatePaySelectionLineFromPaymentRequestIfExists(final I_C_PaySelectionLine paySelectionLine)
	{
		Check.assumeNotNull(paySelectionLine, "paySelectionLine not null");

		final I_C_Invoice invoice = paySelectionLine.getC_Invoice();
		Check.assumeNotNull(invoice, "invoice not null");

		final IPaymentRequestDAO paymentRequestDAO = Services.get(IPaymentRequestDAO.class);
		final I_C_Payment_Request paymentRequest = paymentRequestDAO.retrieveSingularRequestOrNull(invoice);
		if (paymentRequest == null)
		{
			return false;
		}
		
		final org.compiere.model.I_C_BP_BankAccount payRequestBPBankAcct = paymentRequest.getC_BP_BankAccount();
		final org.compiere.model.I_C_BP_BankAccount paySelBPBankAcct =  paySelectionLine.getC_PaySelection().getC_BP_BankAccount();
	
		// 09500: In case we area dealing with 2 bank accounts we have to make sure they are of the same currency
		if(payRequestBPBankAcct != null && paySelBPBankAcct != null)
		{

			// do not update the line from the request if they don't match
			if(payRequestBPBankAcct.getC_Currency_ID() != paySelBPBankAcct.getC_Currency_ID())
			{
				return false;
			}
		}

		//
		// Primarily, try to use the pay amount from the payment request
		if (paymentRequest.getAmount().signum() != 0)
		{
			// task 08406: ...but only apply the payment request's amount if it actually has one specified
			paySelectionLine.setPayAmt(paymentRequest.getAmount());
			final BigDecimal differenceAmt = paymentRequest.getAmount().subtract(invoice.getGrandTotal());
			paySelectionLine.setDifferenceAmt(differenceAmt);
		}

		paySelectionLine.setC_BP_BankAccount_ID(paymentRequest.getC_BP_BankAccount_ID());
		paySelectionLine.setReference(paymentRequest.getReference());

		InterfaceWrapperHelper.setDynAttribute(paySelectionLine, DYNATTR_UpdatedFromPaymentRequest, true);

		return true;
	}

	@Override
	public I_C_Payment_Request createPaymentRequest(final I_C_Invoice invoice, final I_C_Payment_Request paymentRequestTemplate)
	{
		final I_C_Payment_Request requestForInvoice;
		if (paymentRequestTemplate != null)
		{
			requestForInvoice = createNewFromTemplate(paymentRequestTemplate);
		}
		else
		{
			requestForInvoice = InterfaceWrapperHelper.newInstance(I_C_Payment_Request.class, invoice);

			//
			// Find a default partner account
			final IBankingBPBankAccountDAO bpBankAccountDAO = Services.get(IBankingBPBankAccountDAO.class);
			final I_C_BP_BankAccount bpBankAccount = bpBankAccountDAO.retrieveDefaultBankAccount(invoice.getC_BPartner());
			requestForInvoice.setC_BP_BankAccount(bpBankAccount);

			//
			// Assume the invoice grand total for payment request amount
			requestForInvoice.setAmount(invoice.getGrandTotal());
		}

		requestForInvoice.setC_Invoice(invoice);
		InterfaceWrapperHelper.save(requestForInvoice);
		return requestForInvoice;
	}

}
