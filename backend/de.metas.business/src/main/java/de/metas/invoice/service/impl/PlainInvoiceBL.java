package de.metas.invoice.service.impl;

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

import de.metas.document.IDocLineCopyHandler;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.util.Services;
import org.compiere.model.I_C_Invoice;

import java.math.BigDecimal;

public class PlainInvoiceBL extends AbstractInvoiceBL
{
	@Override
	public String getSummary(final I_C_Invoice invoice)
	{
		throw new UnsupportedOperationException();
	}

	public boolean isInvoiceWroteOff(final org.compiere.model.I_C_Invoice invoice)
	{
		final PlainInvoiceDAO invoiceDAO = (PlainInvoiceDAO)Services.get(IInvoiceDAO.class);
		final BigDecimal writeOffAmt = invoiceDAO.retrieveWriteOffAmt(invoice);
		final BigDecimal grandTotalAmt = getGrandTotalAbs(invoice);

		return writeOffAmt.compareTo(grandTotalAmt) == 0;
	}

	@Override
	public int copyLinesFrom(final I_C_Invoice fromInvoice, final I_C_Invoice toInvoice, final boolean counter, final boolean setOrderRef, final boolean setInvoiceRef,
			final IDocLineCopyHandler<org.compiere.model.I_C_InvoiceLine> ilCopyHandler)
	{
		throw new UnsupportedOperationException();
	}
}
