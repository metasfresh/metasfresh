package org.adempiere.invoice.async.spi.impl;

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

import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Invoice;

import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;

/**
 * This WP is enqueued if {@link IInvoiceBL#updateBPartnerStats(List, boolean)} is called with <code>async == true</code>, and when it is executed, it calls the same method with
 * <code>async == false</code>.
 * 
 * @author ts
 * @task http://dewiki908/mediawiki/index.php/08999_Lieferdisposition_a.frieden_%28104263801724%29
 */
public class C_BPartner_UpdateStatsFromInvoice extends WorkpackageProcessorAdapter
{
	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workpackage, final String localTrxName)
	{
		final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

		final List<I_C_Invoice> invoices = queueDAO.retrieveItems(workpackage, I_C_Invoice.class, localTrxName);
		final boolean async = false; // because *this* is already the async invocation
		invoiceBL.updateBPartnerStats(invoices, async);

		return Result.SUCCESS;
	}

}
