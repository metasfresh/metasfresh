package de.metas.commission.invoice.spi.impl;

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


import org.adempiere.document.service.ICopyHandlerBL;
import org.adempiere.document.service.IDocCopyHandler;
import org.adempiere.document.service.IDocLineCopyHandler;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.commission.interfaces.I_C_Invoice;

/**
 * This is mainly a copy and paste of the former jboss-aop aspect <code>de.metas.commission.aopp.MInvoiceLineAsp</code>. The purpose of that aspect was:
 * 
 * <pre>
 * Copy the C_Sponsor_ID after a new order, invoice or inout has been
 * created from an existing order or inout.
 * </pre>
 * 
 * @author ts
 * @task http://dewiki908/mediawiki/index.php/07286_get_rid_of_jboss-aop_for_good_%28104432455599%29
 */
public class CommissionInvoiceCopyHandler implements IDocCopyHandler<org.compiere.model.I_C_Invoice, org.compiere.model.I_C_InvoiceLine>
{
	@Override
	public Class<org.compiere.model.I_C_Invoice> getSupportedItemsClass()
	{
		return org.compiere.model.I_C_Invoice.class;
	}

	@Override
	public void copyValues(org.compiere.model.I_C_Invoice from, org.compiere.model.I_C_Invoice to)
	{
		final I_C_Invoice fromToUse = InterfaceWrapperHelper.create(from, I_C_Invoice.class);
		final I_C_Invoice toToUse = InterfaceWrapperHelper.create(to, I_C_Invoice.class);

		// (2009_0027_G8)
		toToUse.setC_Sponsor_ID(fromToUse.getC_Sponsor_ID());

		// (2009_0027_G25)
		toToUse.setIsCommissionLock(fromToUse.isCommissionLock());
	}

	@Override
	public void copyPreliminaryValues(org.compiere.model.I_C_Invoice from, org.compiere.model.I_C_Invoice to)
	{
		// nothing to do
	}

	@Override
	public IDocLineCopyHandler<org.compiere.model.I_C_InvoiceLine> getDocLineCopyHandler()
	{
		return Services.get(ICopyHandlerBL.class).getNullDocLineCopyHandler();
	}
}
