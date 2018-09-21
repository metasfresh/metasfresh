package de.metas.payment.esr.api.impl;

import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;

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


import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.payment.esr.api.IESRBL;
import de.metas.util.Check;
import de.metas.util.Services;

public class ESRBL implements IESRBL
{
	private final transient Logger logger = LogManager.getLogger(getClass());
	
	@Override
	public boolean appliesForESRDocumentRefId(final Object sourceModel)
	{
		
		
		final String sourceTableName = InterfaceWrapperHelper.getModelTableName(sourceModel);
		Check.assume(I_C_Invoice.Table_Name.equals(sourceTableName), "Document " + sourceModel + " not supported");

	
		final I_C_Invoice invoice = InterfaceWrapperHelper.create(sourceModel, I_C_Invoice.class);
		
		if (!invoice.isSOTrx())
		{
			logger.debug("Skip generating because invoice is purchase invoice: {}", invoice);
			return false;
		}
		if (invoice.getReversal_ID() > 0)
		{
			logger.debug("Skip generating because invoice is a reversal: {}", invoice);
			return false;
		}
		if (Services.get(IInvoiceBL.class).isCreditMemo(invoice))
		{
			logger.debug("Skip generating because invoice is a credit memo: {}", invoice);
			return false;
		}
		
		return true;
	}

}
