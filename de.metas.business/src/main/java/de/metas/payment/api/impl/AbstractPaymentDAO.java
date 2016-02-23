package de.metas.payment.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_Payment;

import de.metas.adempiere.model.I_C_PaySelectionLine;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.payment.api.IPaymentDAO;

public abstract class AbstractPaymentDAO implements IPaymentDAO
{
	@Override
	public BigDecimal getInvoiceOpenAmount(I_C_Payment payment,final boolean creditMemoAdjusted)
	{
		final I_C_Invoice invoice = payment.getC_Invoice();
		Check.assumeNotNull(invoice, "Invoice available for {0}", payment);
		
		// NOTE: we are not using C_InvoicePaySchedule_ID. It shall be a column in C_Payment
		
		return Services.get(IAllocationDAO.class).retrieveOpenAmt(invoice, creditMemoAdjusted);
	}
	
	@Override
	public List<I_C_PaySelectionLine> getProcessedLines(final I_C_PaySelection paySelection)
	{
		Check.assumeNotNull(paySelection, "Pay selection not null");

		return Services.get(IQueryBL.class).createQueryBuilder(I_C_PaySelectionLine.class, paySelection)
				.addEqualsFilter(I_C_PaySelectionLine.COLUMNNAME_C_PaySelection_ID, paySelection.getC_PaySelection_ID())
				.addEqualsFilter(I_C_PaySelectionLine.COLUMNNAME_Processed, true)
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_C_PaySelectionLine.class);
	}
}
