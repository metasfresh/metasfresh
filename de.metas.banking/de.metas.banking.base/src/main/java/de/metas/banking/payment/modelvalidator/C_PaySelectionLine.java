package de.metas.banking.payment.modelvalidator;

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

import java.util.Properties;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.ModelValidator;

import de.metas.adempiere.model.I_C_PaySelectionLine;
import de.metas.banking.interfaces.I_C_BankStatementLine;
import de.metas.banking.model.I_C_BP_BankAccount;
import de.metas.banking.payment.IPaySelectionBL;
import de.metas.banking.payment.IPaymentRequestBL;
import de.metas.payment.model.I_C_Payment_Request;

@Interceptor(I_C_PaySelectionLine.class)
public class C_PaySelectionLine
{
	public static final transient C_PaySelectionLine instance = new C_PaySelectionLine();

	private static final String MSG_PaySelectionLine_Invoice_InvalidCurrency = "PaySelectionLine.Invoice.InvalidCurrency";

	private C_PaySelectionLine()
	{
		super();
	}

	/**
	 * Updates given pay selection line from invoice's {@link I_C_Payment_Request}.
	 *
	 * @param paySelectionLine
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_C_BankStatementLine.COLUMNNAME_C_Invoice_ID)
	public void updateFromPaymentRequestOrInvoice(final I_C_PaySelectionLine paySelectionLine)
	{
		final IPaymentRequestBL paymentRequestBL = Services.get(IPaymentRequestBL.class);
		final IPaySelectionBL paySelectionBL = Services.get(IPaySelectionBL.class);

		if (paymentRequestBL.isUpdatedFromPaymentRequest(paySelectionLine))
		{
			return; // already updated, nothing to do
		}

		// see if we can update from a payment request
		final boolean updated = paymentRequestBL.updatePaySelectionLineFromPaymentRequestIfExists(paySelectionLine);
		if (!updated)
		{
			// fallback and update from the invoice
			paySelectionBL.updateFromInvoice(paySelectionLine);
		}
	}

	/**
	 * Remove line if currency does not match.
	 *
	 * @param paySelectionLine
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void validateInvoiceCurrency(final I_C_PaySelectionLine paySelectionLine)
	{
		final I_C_PaySelection paySelection = paySelectionLine.getC_PaySelection();
		final I_C_BP_BankAccount bankAccount = InterfaceWrapperHelper.create(paySelection.getC_BP_BankAccount(), I_C_BP_BankAccount.class);

		final I_C_Invoice invoice = paySelectionLine.getC_Invoice();

		//
		// Match currency
		if (invoice.getC_Currency_ID() == bankAccount.getC_Currency_ID())
		{
			return;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(paySelectionLine);

		final I_C_Currency iCurrency = invoice.getC_Currency();
		final I_C_Currency baCurrency = bankAccount.getC_Currency();

		throw new AdempiereException(ctx, MSG_PaySelectionLine_Invoice_InvalidCurrency, new Object[] {
				invoice.getDocumentNo(),     // invoice
				iCurrency.getISO_Code(),      // Actual
				baCurrency.getISO_Code() }); // Expected
	}
}
