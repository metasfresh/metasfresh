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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Payment;
import org.compiere.model.ModelValidator;

import de.metas.adempiere.model.I_C_PaySelectionLine;
import de.metas.banking.payment.IPaySelectionDAO;
import de.metas.i18n.IMsgBL;
import de.metas.payment.model.I_C_Payment_Request;

@Interceptor(I_C_Payment_Request.class)
public class C_Payment_Request
{
	private static final String MSG_C_PAYMENT_REQUEST_C_INVOICE_STILL_REFERENCED_FROM_C_PAY_SELECTION_LINE = "C_Payment_Request_C_Invoice_Still_referenced_from_C_PaySelectionLine";

	public static final C_Payment_Request instance = new C_Payment_Request();

	private C_Payment_Request()
	{
	}

	/**
	 * Checks if is is OK to deactivate the given paymentRequest.
	 * 
	 * @param paymentRequest
	 * @task 08596
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE }
			, ifColumnsChanged = { I_C_Payment.COLUMNNAME_IsActive })
	public void checkDeactivationAllowed(final I_C_Payment_Request paymentRequest)
	{
		if (paymentRequest.isActive() || paymentRequest.getC_Invoice_ID() <= 0)
		{
			return; // nothing to do
		}

		final List<I_C_PaySelectionLine> paySelectionLines = Services.get(IPaySelectionDAO.class).retrievePaySelectionLines(paymentRequest.getC_Invoice());
		if (paySelectionLines.isEmpty())
		{
			return; // nothing to do
		}

		final I_C_PaySelectionLine paySelectionLine = paySelectionLines.get(0); // only showing the first, there shouldn't be >1 anyways
		throw new AdempiereException(
				Services.get(IMsgBL.class).getMsg(InterfaceWrapperHelper.getCtx(paymentRequest),
						MSG_C_PAYMENT_REQUEST_C_INVOICE_STILL_REFERENCED_FROM_C_PAY_SELECTION_LINE,
						new Object[] { paySelectionLine.getC_PaySelection().getName(), paySelectionLine.getLine() }
						));
	}
}
