package de.metas.dunning.invoice.api.impl;

/*
 * #%L
 * de.metas.dunning
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


import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.CLogger;
import org.compiere.util.TimeUtil;
import org.compiere.util.TrxRunnable2;

import de.metas.dunning.api.IDunningBL;
import de.metas.dunning.api.IDunningContext;
import de.metas.dunning.api.IDunningDAO;
import de.metas.dunning.api.IDunningEventDispatcher;
import de.metas.dunning.interfaces.I_C_Dunning;
import de.metas.dunning.invoice.api.IInvoiceSourceBL;
import de.metas.dunning.invoice.api.IInvoiceSourceDAO;
import de.metas.dunning.model.I_C_DunningDoc_Line_Source;
import de.metas.dunning.model.I_C_Dunning_Candidate;

public class InvoiceSourceBL implements IInvoiceSourceBL
{
	private final static transient CLogger logger = CLogger.getCLogger(InvoiceSourceBL.class);

	@Override
	public boolean setDunningGraceIfAutomatic(final I_C_Invoice invoice)
	{
		final I_C_Dunning dunning = getDunningForInvoiceOrNull(invoice);
		if (dunning == null)
		{
			// No dunning registered on either BPartner or org. Do nothing.
			return false;
		}

		if (!I_C_Dunning.DUNNINGTIMER_AutomaticGrace.equalsIgnoreCase(dunning.getDunningTimer()))
		{
			// Dunning is not set to update automatically. Skip it
			return false;
		}

		final Timestamp dueDate = Services.get(IInvoiceSourceDAO.class).retrieveDueDate(invoice);
		final Timestamp dunningGraceDate = TimeUtil.addDays(dueDate, dunning.getGraceDays());
		invoice.setDunningGrace(dunningGraceDate);

		return true;
	}

	@Override
	public I_C_Dunning getDunningForInvoiceOrNull(final I_C_Invoice invoice)
	{
		final IDunningDAO dunningDAO = Services.get(IDunningDAO.class);
		final Properties ctx = InterfaceWrapperHelper.getCtx(invoice);

		I_C_Dunning dunning = dunningDAO.retrieveDunningForBPartner(invoice.getC_BPartner());
		if (dunning != null)
		{
			return dunning;
		}

		dunning = dunningDAO.retrieveDunningByOrg(ctx, invoice.getAD_Org_ID());
		return dunning;
	}

	@Override
	public int writeOffDunningDocs(final Properties ctx, final String writeOffDescription, final ILoggable monitor)
	{
		final IDunningDAO dunningDAO = Services.get(IDunningDAO.class);
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		final int[] countWriteOff = new int[] { 0 };

		// NOTE: is very important to fetch candidates out of transaction in order to process each of them in a separate transaction
		final IDunningContext dunningContext = Services.get(IDunningBL.class).createDunningContext(ctx,
				null, // dunningLevel
				null, // dunningDate
				ITrx.TRXNAME_None // make sure we are fetching everything out of trx
				);

		final Iterator<I_C_DunningDoc_Line_Source> sources = dunningDAO.retrieveDunningDocLineSourcesToWriteOff(dunningContext);
		while (sources.hasNext())
		{
			final I_C_DunningDoc_Line_Source source = sources.next();

			trxManager.run(new TrxRunnable2()
			{
				@Override
				public void run(final String localTrxName)
				{
					// WORKAROUND: add subsequent methods called from here depends on source's trxName, so we are loading the candidate in this transaction
					InterfaceWrapperHelper.refresh(source, localTrxName);

					final I_C_Dunning_Candidate candidate = source.getC_Dunning_Candidate();
					writeOffDunningCandidate(candidate, writeOffDescription);

					source.setIsWriteOffApplied(true);
					InterfaceWrapperHelper.save(source);

					countWriteOff[0]++;
				}

				@Override
				public boolean doCatch(Throwable e) throws Throwable
				{
					final String errmsg = "Error processing: " + source;
					logger.log(Level.SEVERE, errmsg, e);

					// notify monitor too about this issue
					if (monitor != null)
					{
						monitor.addLog(errmsg);
					}

					return true; // rollback
				}

				@Override
				public void doFinally()
				{
					// nothing
				}
			});
		}

		return countWriteOff[0];
	}

	/**
	 * 
	 * @param candidate
	 * @param writeOffDescription
	 * @return true if candidate's invoice was wrote-off
	 */
	protected boolean writeOffDunningCandidate(final I_C_Dunning_Candidate candidate, final String writeOffDescription)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(candidate);
		final String trxName = InterfaceWrapperHelper.getTrxName(candidate);

		// services
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
		final IDunningEventDispatcher dunningEventDispatcher = Services.get(IDunningEventDispatcher.class);
		
		if (candidate.getAD_Table_ID() == adTableDAO.retrieveTableId(I_C_Invoice.Table_Name))
		{
			final I_C_Invoice invoice = InterfaceWrapperHelper.create(ctx, candidate.getRecord_ID(), I_C_Invoice.class, trxName);
			Check.assumeNotNull(invoice, "No invoice found for candidate {0}", candidate);
			
			invoiceBL.writeOffInvoice(invoice, candidate.getOpenAmt(), writeOffDescription);

			dunningEventDispatcher.fireDunningCandidateEvent(IInvoiceSourceBL.EVENT_AfterInvoiceWriteOff, candidate);

			return true;
		}

		// other document types are ignored for now

		return false;
	}

}
