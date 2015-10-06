package de.metas.printing.spi.impl;

/*
 * #%L
 * de.metas.printing.base
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


import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Util;

import de.metas.adempiere.form.IClientUIInstance;
import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.model.I_C_Print_Job_Instructions;
import de.metas.printing.spi.IPrintJobBatchMonitor;

public class UserConfirmationPrintJobBatchMonitor implements IPrintJobBatchMonitor
{
	public static final String MSG_CREATE_NEXT_JOB_0P = "CreatePrintJobs_Create_Next_Job?";

	private int jobsCount = 0;
	private I_C_Print_Job_Instructions firstPrintJobInstructionsNotConfirmed = null;

	private final IClientUIInstance clientUI;

	public UserConfirmationPrintJobBatchMonitor(final IClientUIInstance clientUI)
	{
		super();
		this.clientUI = clientUI;
	}

	@Override
	public boolean printJobBeforeCreate()
	{
		if (jobsCount == 0)
		{
			// First job, let the processing continue
			return true;
		}
		else if (jobsCount == 1)
		{
			// Second job, let's confirm the first job before
			final boolean confirmed = confirm(firstPrintJobInstructionsNotConfirmed, false); // autoConfirm=false
			firstPrintJobInstructionsNotConfirmed = null;

			// ... and let the processing continue only if this one was confirmed
			return confirmed;
		}
		else
		{
			// Next jobs, let the processing continue
			return true;
		}
	}

	@Override
	public boolean printJobCreated(final I_C_Print_Job_Instructions printJobInstructions)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(printJobInstructions);
		Check.assume(Util.same(trxName, ITrx.TRXNAME_None), "printJobInstructions expected to be out of transaction");

		final boolean continueProcessing;
		if (jobsCount == 0)
		{
			// First job, just schedule it
			firstPrintJobInstructionsNotConfirmed = printJobInstructions;
			continueProcessing = true; // continue processing
		}
		else
		{
			// Next jobs, let try to confirm them right away
			final boolean confirmed = confirm(printJobInstructions, false); // autoConfirm=false
			// ... and let the processing continue only if this one was confirmed
			continueProcessing = confirmed;
		}

		jobsCount++;
		return continueProcessing;
	}

	@Override
	public void finish()
	{
		if (firstPrintJobInstructionsNotConfirmed != null)
		{
			// Auto-confirm the remaining one because for sure it was the only one from this batch
			confirm(firstPrintJobInstructionsNotConfirmed, true);
			firstPrintJobInstructionsNotConfirmed = null;
		}
	}

	private boolean confirm(final I_C_Print_Job_Instructions printJobInstructions, final boolean autoConfirm)
	{
		final boolean[] result = new boolean[] { false };

		Services.get(IPrintingDAO.class).runWithTrxName(ITrx.TRXNAME_None, new Runnable()
		{
			@Override
			public void run()
			{
				result[0] = confirm0(printJobInstructions, autoConfirm);
			}
		});

		return result[0];
	}

	private boolean confirm0(final I_C_Print_Job_Instructions printJobInstructions, final boolean autoConfirm)
	{
		boolean confirmed = autoConfirm;
		if (!confirmed)
		{
			confirmed = clientUI.ask(0, MSG_CREATE_NEXT_JOB_0P);
		}

		if (confirmed)
		{
			printJobInstructions.setUserOK(true);
			InterfaceWrapperHelper.save(printJobInstructions);
		}
		else
		{
			printJobInstructions.setUserOK(false);
			InterfaceWrapperHelper.save(printJobInstructions);
		}

		return confirmed;
	}
}
