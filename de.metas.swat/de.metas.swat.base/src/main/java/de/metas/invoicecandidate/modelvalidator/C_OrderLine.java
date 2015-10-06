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


import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

@Interceptor(I_C_OrderLine.class)
public class C_OrderLine
{
	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE
			, ifColumnsChanged = {
					I_C_OrderLine.COLUMNNAME_QtyDelivered // Using C_OrderLine.QtyDelivered to get notified of changes in the delivery status.
					, I_C_OrderLine.COLUMNNAME_QtyOrdered // task 08452: make sure the IC gets invalidated when we sort of "close" a single line
					, I_C_OrderLine.COLUMNNAME_QtyOrderedOverUnder
					, I_C_OrderLine.COLUMNNAME_IsPackagingMaterial
			})
	public void invalidateInvoiceCandidates(final I_C_OrderLine ol)
	{
		Services.get(IInvoiceCandidateHandlerBL.class).invalidateCandidatesFor(ol);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteInvoiceCandidates(final I_C_OrderLine ol)
	{
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
		final List<I_C_Invoice_Candidate> icsToDelete = invoiceCandDAO.retrieveReferencing(ol);

		for (final I_C_Invoice_Candidate icToDelete : icsToDelete)
		{
			InterfaceWrapperHelper.delete(icToDelete);
		}

	}
}
