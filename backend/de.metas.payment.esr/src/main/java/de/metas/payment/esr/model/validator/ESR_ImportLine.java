package de.metas.payment.esr.model.validator;

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


import java.sql.Timestamp;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.model.ModelValidator;

import de.metas.bpartner.BPartnerId;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentDAO;
import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.api.IESRImportBL;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.util.Services;

@Validator(I_ESR_ImportLine.class)
public class ESR_ImportLine
{
	private static final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);
	
	public static final String ERR_Wrong_Org_For_Invoice = "ESR_Wrong_Org_For_Manually_Entered_Invoice";
	public static final String ERR_Wrong_Org_For_Partner = "ESR_Wrong_Org_For_Manually_Entered_Partner";
	
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE,
			ifColumnsChanged = I_ESR_ImportLine.COLUMNNAME_C_Invoice_ID)
	public void onChangeInvoice(I_ESR_ImportLine esrImportLine)
	{
		if (!InterfaceWrapperHelper.isUIAction(esrImportLine))
		{
			// do nothing if the modification was triggered from the application, not by the user
			return;
		}

		// if we have a payment. then the invoice entered can not have a different partner
		if (esrImportLine.getC_Payment_ID() > 0 &&
				esrImportLine.getC_BPartner_ID() > 0
				&& esrImportLine.getC_Invoice_ID() > 0
				&& esrImportLine.getC_Invoice().getC_BPartner_ID() != esrImportLine.getC_BPartner_ID())
		{
			throw new AdempiereException("@" + ESRConstants.ESR_DIFF_INV_PARTNER + "@");
		}
		
		esrImportLine.setIsManual(true);

		// Case when invoice is manually deleted from ESR import line
		if (esrImportLine.getC_Invoice() == null)
		{
			return;
		}

		// note that setInvoice doesn't actually save the given esrImportLine, so we are fine to call it from here
		Services.get(IESRImportBL.class).setInvoice(esrImportLine, esrImportLine.getC_Invoice());
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE,
			ifColumnsChanged = I_ESR_ImportLine.COLUMNNAME_C_BPartner_ID)
	public void onChangePartner(I_ESR_ImportLine esrImportLine)
	{
		if (!InterfaceWrapperHelper.isUIAction(esrImportLine))
		{
			// do nothing if the modification was triggered from the application, not by the user
			return;
		}
		
		// the esr line will have the flag IsManual on true
		esrImportLine.setIsManual(true);

		// if the partner was changed, the invoice and payment have to also change

		final I_C_Invoice invoice = esrImportLine.getC_Invoice();

		if (invoice != null)
		{
			if (invoice.getC_BPartner_ID() != esrImportLine.getC_BPartner_ID())
			{
				esrImportLine.setC_Invoice_ID(-1);
			}

			// In case the bpartner was unset then set again to the same value, there's nothing to do
			else
			{
				return;
			}
		}

		final PaymentId esrImportLinePaymentId = PaymentId.ofRepoIdOrNull(esrImportLine.getC_Payment_ID());
		final I_C_Payment payment = esrImportLinePaymentId == null ? null
				: paymentDAO.getById(esrImportLinePaymentId);
		
	

		handlePayment(
				BPartnerId.ofRepoIdOrNull(esrImportLine.getC_BPartner_ID()),
				payment);

	}

	/**
	 * Modify payment set in esr line
	 * 
	 * @param newPartner
	 * @param payment
	 */
	private void handlePayment(final BPartnerId newPartnerId, final I_C_Payment payment)
	{
		if (payment == null)
		{
			// nothing to do
			return;
		}

		// Reverse all allocation from the payment
		Services.get(IESRImportBL.class).reverseAllocationForPayment(payment);
		InterfaceWrapperHelper.save(payment);

		if (newPartnerId == null)
		{
			// We never let payments without BPartner set because we use the traceability of payments
			return;
		}

		// If the BPartner was changes we also change the BPartner of the payment
		payment.setC_BPartner_ID(newPartnerId.getRepoId());
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE,
			ifColumnsChanged = I_ESR_ImportLine.COLUMNNAME_PaymentDate)
	public void onChangePaymentDate(I_ESR_ImportLine esrImportLine)
	{
		if (!InterfaceWrapperHelper.isUIAction(esrImportLine))
		{
			// do nothing if the modification was triggered from the application, not by the user
			return;
		}
		esrImportLine.setIsManual(true);

		final Timestamp paymentDate = esrImportLine.getPaymentDate();

		if (paymentDate == null)
		{
			// nothing to do.
		}
		else
		{
			esrImportLine.setAccountingDate(paymentDate);
		}

	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE,
			ifColumnsChanged = I_ESR_ImportLine.COLUMNNAME_C_Payment_ID)
	public void onChangePayment(I_ESR_ImportLine esrImportLine)
	{
		if (InterfaceWrapperHelper.isUIAction(esrImportLine))
		{
			// do nothing if the modification was triggered by the user
			return;
		}

		// if invoice is not set, do nothing
		if (esrImportLine.getC_Invoice_ID() <= 0)
		{
			return;
		}

		// note that setInvoice doesn't actually save the given esrImportLine, so we are fine to call it from here
		Services.get(IESRImportBL.class).setInvoice(esrImportLine, esrImportLine.getC_Invoice());
	}
}
