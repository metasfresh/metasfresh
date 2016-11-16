/**
 * 
 */
package de.metas.payment.esr.process;

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


import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Invoice;

import de.metas.allocation.api.IAllocationBL;
import de.metas.banking.model.I_C_Payment;
import de.metas.process.ProcessInfoParameter;

/**
 * @author cg
 *
 */
public class AllocatePayment extends de.metas.process.SvrProcess
{

	private int p_C_Payment_ID;
	private int p_C_Invoice_ID;

	@Override
	protected void prepare()
	{
		if (I_C_Payment.Table_Name.equals(getTableName()))
		{
			p_C_Payment_ID = getRecord_ID();
		}

		for (ProcessInfoParameter para : getParametersAsArray())
		{
			String name = para.getParameterName();
			if (para.getParameter() == null)
				;
			else if (I_C_Payment.COLUMNNAME_C_Invoice_ID.equals(name))
			{
				p_C_Invoice_ID = para.getParameterAsInt();
			}
		}
	}
	@Override
	protected String doIt() throws Exception
	{

		if (p_C_Payment_ID <= 0)
		{
			throw new FillMandatoryException(I_C_Payment.COLUMNNAME_C_Payment_ID);
		}

		if (p_C_Invoice_ID <= 0)
		{
			throw new FillMandatoryException(I_C_Payment.COLUMNNAME_C_Invoice_ID);
		}

		final I_C_Payment payment = InterfaceWrapperHelper.create(getCtx(), p_C_Payment_ID, I_C_Payment.class, get_TrxName());
		final I_C_Invoice invoice = InterfaceWrapperHelper.create(getCtx(), p_C_Invoice_ID, I_C_Invoice.class, get_TrxName());
		Services.get(IAllocationBL.class).autoAllocateSpecificPayment(invoice, payment, true);

		final boolean ignoreProcessed = false;
		Services.get(IInvoiceBL.class).testAllocation(invoice, ignoreProcessed);

		return "OK";
	}

}
