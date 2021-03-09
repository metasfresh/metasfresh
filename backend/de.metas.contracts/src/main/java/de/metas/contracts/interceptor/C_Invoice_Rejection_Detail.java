/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.contracts.interceptor;

import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Invoice_Rejection_Detail;
import org.compiere.model.ModelValidator;

@Interceptor(I_C_Invoice_Rejection_Detail.class)
public class C_Invoice_Rejection_Detail
{
	final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_C_Invoice_Rejection_Detail.COLUMNNAME_IsDone })
	public void onUpdateIsDone(final I_C_Invoice_Rejection_Detail invoiceRejectionDetail)
	{
		final I_C_Invoice invoice = invoiceDAO.getByIdInTrx(InvoiceId.ofRepoId(invoiceRejectionDetail.getC_Invoice_ID()));
		invoice.setIsInDispute(!invoiceRejectionDetail.isDone());
		InterfaceWrapperHelper.saveRecord(invoice);
	}
}
