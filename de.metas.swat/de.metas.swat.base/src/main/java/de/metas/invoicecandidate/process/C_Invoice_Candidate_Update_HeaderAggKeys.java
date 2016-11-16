/**
 *
 */
package de.metas.invoicecandidate.process;

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


import java.util.Iterator;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IAutoCloseable;
import org.apache.commons.collections4.IteratorUtils;
import org.compiere.model.ModelValidationEngine;

import de.metas.invoicecandidate.api.IAggregationBL;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.process.SvrProcess;

/**
 * @author ts
 *
 */
public class C_Invoice_Candidate_Update_HeaderAggKeys extends SvrProcess
{
	final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

	@Override
	protected void prepare()
	{
		// nothing to do
	}

	@Override
	protected String doIt() throws Exception
	{
		int counter = 0;

		final Iterator<I_C_Invoice_Candidate> unprocessedCands = invoiceCandDAO.retrieveNonProcessed(new PlainContextAware(getCtx(), ITrx.TRXNAME_None));

		try (final IAutoCloseable updateInProgressCloseable = invoiceCandBL.setUpdateProcessInProgress())
		{
			for (final I_C_Invoice_Candidate ic : IteratorUtils.asIterable(unprocessedCands))
			{
				Services.get(IAggregationBL.class)
						.getUpdateProcessor()
						.process(ic);

				// disable the model validation engine for this PO to gain performance
				InterfaceWrapperHelper.setDynAttribute(ic, ModelValidationEngine.DYNATTR_DO_NOT_INVOKE_ON_MODEL_CHANGE, true);
				InterfaceWrapperHelper.save(ic);
				counter++;
			}
		}
		return "@Updated@ " + counter + " @C_Invoice_Candidate@";
	}
}
