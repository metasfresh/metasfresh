package de.metas.commission.modelvalidator;

/*
 * #%L
 * de.metas.commission.base
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


import java.util.Properties;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.ModelValidator;

import de.metas.commission.interfaces.I_C_Invoice;
import de.metas.commission.interfaces.I_C_InvoiceLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;

@Validator(I_C_Invoice_Line_Alloc.class)
public class C_Invoice_Line_Alloc
{

	/**
	 * Make sure we set the sponsor in invoice, if the invoice line's candidate references an order line.
	 * 
	 * @param invoiceLineAlloc
	 */
	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void setSponsor(final I_C_Invoice_Line_Alloc invoiceLineAlloc)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(invoiceLineAlloc);
		final String trxName = InterfaceWrapperHelper.getTrxName(invoiceLineAlloc);
		if (invoiceLineAlloc.getC_InvoiceLine_ID() < 1)
		{
			// No invoice line set. Do nothing.
			return;
		}
		final I_C_InvoiceLine invoiceLine = InterfaceWrapperHelper.create(ctx, invoiceLineAlloc.getC_InvoiceLine_ID(), I_C_InvoiceLine.class, trxName);
		final I_C_Invoice invoice = InterfaceWrapperHelper.create(invoiceLine.getC_Invoice(), I_C_Invoice.class);
		if (invoice.getC_Sponsor_ID() > 0)
		{
			// We already have a sponsor set. Do nothing.
			return;
		}

		final I_C_Invoice_Candidate ic = InterfaceWrapperHelper.create(ctx, invoiceLineAlloc.getC_Invoice_Candidate_ID(), I_C_Invoice_Candidate.class, trxName);
		if (null == ic.getAD_Table())
		{
			return;
		}
		if (I_C_OrderLine.Table_Name.equals(ic.getAD_Table().getTableName()))
		{
			final I_C_OrderLine ol = InterfaceWrapperHelper.create(ctx, ic.getRecord_ID(), I_C_OrderLine.class, trxName);
			final int sponsorId = getSponsorForOl(ol);
			invoice.setC_Sponsor_ID(sponsorId);
			InterfaceWrapperHelper.save(invoice);
			return;
		}
	}

	private int getSponsorForOl(final I_C_OrderLine ol)
	{
		Check.assumeNotNull(ol, "Order line should not be null");

		final de.metas.commission.interfaces.I_C_Order order = InterfaceWrapperHelper.create(ol.getC_Order(), de.metas.commission.interfaces.I_C_Order.class);

		return order.getC_Sponsor_ID();
	}

}
