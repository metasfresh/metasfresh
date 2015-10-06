package de.metas.edi.spi.impl;

/*
 * #%L
 * de.metas.edi
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


import org.adempiere.document.service.ICopyHandlerBL;
import org.adempiere.document.service.IDocCopyHandler;
import org.adempiere.document.service.IDocLineCopyHandler;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.edi.model.I_C_Invoice;



public class EdiInvoiceCopyHandler implements IDocCopyHandler<org.compiere.model.I_C_Invoice, org.compiere.model.I_C_InvoiceLine>
{

	@Override
	public void copyPreliminaryValues (org.compiere.model.I_C_Invoice from, org.compiere.model.I_C_Invoice to)
	{
		// nothing to do
		
	}

	@Override
	public void copyValues(org.compiere.model.I_C_Invoice from, org.compiere.model.I_C_Invoice to)
	{
		final I_C_Invoice fromToUse = InterfaceWrapperHelper.create(from, I_C_Invoice.class);
		final I_C_Invoice toToUse = InterfaceWrapperHelper.create(to, I_C_Invoice.class);
		
		// task 08926
		// Make sure the EdiEnabled flag is inherited if the invoice is created form another invoice
		toToUse.setIsEdiEnabled(fromToUse.isEdiEnabled());
		
	}

	@Override
	public Class<org.compiere.model.I_C_Invoice> getSupportedItemsClass()
	{
		return org.compiere.model.I_C_Invoice.class;
	}

	@Override
	public IDocLineCopyHandler<org.compiere.model.I_C_InvoiceLine> getDocLineCopyHandler()
	{
		return Services.get(ICopyHandlerBL.class).getNullDocLineCopyHandler();
	}

}
