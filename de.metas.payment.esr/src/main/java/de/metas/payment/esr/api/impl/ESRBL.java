package de.metas.payment.esr.api.impl;

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


import java.util.logging.Level;

import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.PO;
import org.compiere.util.CLogger;

import de.metas.payment.esr.api.IESRBL;

public class ESRBL implements IESRBL
{
	private final transient CLogger logger = CLogger.getCLogger(getClass());
	
	@Override
	public boolean appliesForESRDocumentRefId(final PO source)
	{
		Check.assume(I_C_Invoice.Table_Name.equals(source.get_TableName()), "Document " + source + " not supported");

		final I_C_Invoice invoice = InterfaceWrapperHelper.create(source, I_C_Invoice.class);
		
		if (!invoice.isSOTrx())
		{
			logger.log(Level.FINE, "Skip generating because invoice is purchase invoice: {0}", invoice);
			return false;
		}
		if (invoice.getReversal_ID() > 0)
		{
			logger.log(Level.FINE, "Skip generating because invoice is a reversal: {0}", invoice);
			return false;
		}
		if (Services.get(IInvoiceBL.class).isCreditMemo(invoice))
		{
			logger.log(Level.FINE, "Skip generating because invoice is a credit memo: {0}", invoice);
			return false;
		}
		
		return true;
	}

}
