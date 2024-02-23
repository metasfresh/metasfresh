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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.allocation.api.IAllocationDAO;
import de.metas.banking.api.IBPBankAccountDAO;
import de.metas.banking.model.I_C_Payment_Request;
import de.metas.banking.payment.IPaymentRequestBL;
import de.metas.banking.payment.IPaymentRequestDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_PaySelectionLine;

import java.math.BigDecimal;

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
		clone.setC_BP_BankAccount_ID(request.getC_BP_BankAccount_ID());
		clone.setAmount(request.getAmount());
		clone.setReference(request.getReference());
		clone.setFullPaymentString(request.getFullPaymentString()); // FRESH-318
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
	public boolean updatePaySelectionLineFromPaymentRequestIfExists(@NonNull final I_C_PaySelectionLine paySelectionLine)
	{
		final I_C_Invoice invoice = Check.assumeNotNull(paySelectionLine.getC_Invoice(),
														"C_Invoice not null for C_PaySelectionLine_ID={}", paySelectionLine.getC_PaySelectionLine_ID());

		final IPaymentRequestDAO paymentRequestDAO = Services.get(IPaymentRequestDAO.class);
		final IAllocationDAO allocationDAO = Services.get(IAllocationDAO.class);

		final I_C_Payment_Request paymentRequest = paymentRequestDAO.retrieveSingularRequestOrNull(invoice);
		if (paymentRequest == null)
		{
			return false;
		}

		final org.compiere.model.I_C_BP_BankAccount payRequestBPBankAcct = paymentRequest.getC_BP_BankAccount();
		final org.compiere.model.I_C_BP_BankAccount paySelBPBankAcct = paySelectionLine.getC_PaySelection().getC_BP_BankAccount();

		// 09500: In case we area dealing with 2 bank accounts we have to make sure they are of the same currency
		if (payRequestBPBankAcct != null && paySelBPBankAcct != null)
		{
			// do not update the line from the request if they don't match
			if (payRequestBPBankAcct.getC_Currency_ID() != paySelBPBankAcct.getC_Currency_ID())
			{
				return false;
			}
		}

		//
		// Primarily, try to use the pay amount from the payment request
		final BigDecimal requestAmount = paymentRequest.getAmount();

		// task 08406: ...but only apply the payment request's amount if it actually has one specified
		if (requestAmount.signum() != 0)
		{
			// task 09698: don't apply more than the amount which is actually still open, even if the paymentRequest's amount is bigger.
			final boolean creditMemoAdjusted = true;
			final BigDecimal openAmt = allocationDAO.retrieveOpenAmt(invoice, creditMemoAdjusted);

			// make sure to also subtract the discount (that's coming from the payment-term)
			final BigDecimal payAmt = requestAmount.min(openAmt.subtract(paySelectionLine.getDiscountAmt()));
			paySelectionLine.setPayAmt(payAmt);

			final BigDecimal differenceAmt = payAmt.subtract(openAmt);
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

			final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
			final BPartnerId bpartnerId = BPartnerId.ofRepoId(invoice.getC_BPartner_ID());
			final I_C_BPartner bpartner = bpartnerDAO.getById(bpartnerId);

			//
			// Find a default partner account
			final IBPBankAccountDAO bankAccountDAO = Services.get(IBPBankAccountDAO.class);
			final I_C_BP_BankAccount bpBankAccount = bankAccountDAO.retrieveDefaultBankAccountInTrx(bpartnerId)
					.orElseThrow(() -> new AdempiereException("No default bank account found for " + bpartner.getName()));
			requestForInvoice.setC_BP_BankAccount_ID(bpBankAccount.getC_BP_BankAccount_ID());

			//
			// Assume the invoice grand total for payment request amount
			requestForInvoice.setAmount(invoice.getGrandTotal());
		}

		requestForInvoice.setC_Invoice(invoice);
		InterfaceWrapperHelper.save(requestForInvoice);
		return requestForInvoice;
	}

}
