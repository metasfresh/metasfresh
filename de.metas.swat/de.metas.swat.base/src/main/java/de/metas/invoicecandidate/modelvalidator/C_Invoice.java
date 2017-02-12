package de.metas.invoicecandidate.modelvalidator;

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

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.invoicecandidate.api.IInvoiceCandBL;

@Interceptor(I_C_Invoice.class)
public class C_Invoice
{
	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE, ModelValidator.TIMING_AFTER_VOID, ModelValidator.TIMING_AFTER_CLOSE })
	public void handleCompleteForInvoice(final I_C_Invoice invoice)
	{
		// FIXME 06162: Save invoice before processing (e.g DocStatus needs to be accurate)
		InterfaceWrapperHelper.save(invoice);

		Services.get(IInvoiceCandBL.class).handleCompleteForInvoice(invoice);
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REVERSECORRECT })
	public void handleReversalForInvoice(final I_C_Invoice invoice)
	{
		Services.get(IInvoiceCandBL.class).handleReversalForInvoice(invoice);
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_COMPLETE })
	public void closePartiallyInvoiced_InvoiceCandidates(final I_C_Invoice invoice)
	{
		Services.get(IInvoiceCandBL.class).closePartiallyInvoiced_InvoiceCandidates(invoice);
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_REVERSECORRECT, ModelValidator.TIMING_BEFORE_REVERSEACCRUAL })
	public void candidatesUnProcess(final I_C_Invoice invoice)
	{
		Services.get(IInvoiceCandBL.class).candidates_unProcess(invoice);
	}
}
