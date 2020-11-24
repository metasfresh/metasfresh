package de.metas.printing.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.logging.LogManager;
import de.metas.printing.api.IPrintJobLinesAggregator;
import de.metas.printing.api.IPrintPackageBL;
import de.metas.printing.api.IPrintPackageCtx;
import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.model.I_C_Print_Job_Instructions;
import de.metas.printing.model.I_C_Print_Job_Line;
import de.metas.printing.model.I_C_Print_Package;
import de.metas.printing.model.X_C_Print_Job_Instructions;
import de.metas.printing.printingdata.PrintingDataFactory;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.session.ISessionBL;
import org.adempiere.ad.session.MFSession;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.Mutable;
import org.apache.commons.collections4.IteratorUtils;
import org.compiere.util.TrxRunnable2;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Properties;

@Service
public class PrintPackageBL implements IPrintPackageBL
{
	private final Logger logger = LogManager.getLogger(getClass());

	private final PrintingDataFactory printingDataFactory;

	public PrintPackageBL(@NonNull final PrintingDataFactory printingDataFactory)
	{
		this.printingDataFactory = printingDataFactory;
	}

	@Override
	public boolean addPrintingDataToPrintPackage(
			@NonNull final I_C_Print_Package printPackage,
			@NonNull final I_C_Print_Job_Instructions jobInstructions,
			@NonNull final IPrintPackageCtx printPackageCtx)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(printPackage);

		final boolean[] packageCreated = new boolean[] { false };
		Services.get(ITrxManager.class).run(trxName, new TrxRunnable2()
		{
			Throwable ex = null;
			boolean created = false;

			@Override
			public void run(final String localTrxName)
			{
				createPrintPackage0(printPackage, jobInstructions, printPackageCtx, localTrxName);
				created = true;
			}

			@Override
			public boolean doCatch(final Throwable e)
			{
				ex = e;
				return true; // rollback
			}

			@Override
			public void doFinally()
			{
				// Update Status in job instructions
				// ... we have an exception
				if (ex != null)
				{
					Check.assume(!created, "On exception no package should be created/updated"); // internal error

					jobInstructions.setStatus(X_C_Print_Job_Instructions.STATUS_Error);
					jobInstructions.setErrorMsg(ex.getLocalizedMessage());
					InterfaceWrapperHelper.save(jobInstructions);
					logger.warn(ex.getLocalizedMessage(), ex);
					packageCreated[0] = false;
				}
				// ... package created/updated
				else if (created)
				{
					if (X_C_Print_Job_Instructions.STATUS_Done.equals(jobInstructions.getStatus()))
					{
						// This shall not happen: we create a confirmation for a job which was already comfirmed by user
						// Case: a user confirmation popup is displayed, user is not waiting for the document to come out of printer (which implies a print package created and sent to
						// printing client), and just confirms OK. At that moment the Status is set to Done. When actually the print package is generated the status is already done
						// Conclusion: do nothing, just log a warning
						logger.warn("Skip setting status to Send because the instuctions have already status Done: " + jobInstructions);
					}
					else
					{
						jobInstructions.setStatus(X_C_Print_Job_Instructions.STATUS_Send);
						jobInstructions.setErrorMsg(null);
						InterfaceWrapperHelper.save(jobInstructions);
					}
					packageCreated[0] = true;
				}
				// ... package not created/updated
				else
				// !created
				{
					packageCreated[0] = false;
				}
			}
		});
		return packageCreated[0];
	}

	private void createPrintPackage0(final I_C_Print_Package printPackage,
			final I_C_Print_Job_Instructions jobInstructions,
			final IPrintPackageCtx printPackageCtx,
			final String trxName)
	{
		final String jobInstructionsTrxName = InterfaceWrapperHelper.getTrxName(jobInstructions);
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		Check.assume(trxManager.isSameTrxName(trxName, jobInstructionsTrxName), "Same transaction (Param 'trxName': {}, jobInstructions' trxName: {}; jobInstructions={})",
				trxName, jobInstructionsTrxName, jobInstructions);

		printPackage.setCopies(jobInstructions.getCopies());

		final IPrintJobLinesAggregator aggregator = createPrintJobLinesAggregator(printPackageCtx, jobInstructions);
		aggregator.setPrintPackageToUse(printPackage);

		final Mutable<ArrayKey> lastKey = new Mutable<>();

		final Iterator<I_C_Print_Job_Line> jobLines = Services.get(IPrintingDAO.class).retrievePrintJobLines(jobInstructions);
		for (final I_C_Print_Job_Line jobLine : IteratorUtils.asIterable(jobLines))
		{
			aggregator.add(jobLine, lastKey);
		}

		final I_C_Print_Package printPackageCreated = aggregator.createPrintPackage();
		Check.assumeNotNull(printPackageCreated, "Print package created for {}", jobInstructions);

		InterfaceWrapperHelper.save(printPackage);
	}

	protected IPrintJobLinesAggregator createPrintJobLinesAggregator(
			@NonNull final IPrintPackageCtx printPackageCtx,
			@NonNull final I_C_Print_Job_Instructions jobInstructions)
	{
		return new PrintJobLinesAggregator(printingDataFactory, printPackageCtx, jobInstructions);
	}

	@Override
	public IPrintPackageCtx createEmptyInitialCtx()
	{
		return new PrintPackageCtx();
	}

	@Override
	public IPrintPackageCtx createInitialCtx(@NonNull final Properties ctx)
	{
		final PrintPackageCtx printCtx = new PrintPackageCtx();

		final MFSession session = Services.get(ISessionBL.class).getCurrentSession(ctx);
		if (session == null)
		{
			throw new AdempiereException("The given ctx has no session.")
					.appendParametersToMessage()
					.setParameter("ctx", ctx);
		}
		logger.debug("Session: {}", session);

		final String hostKey = session.getOrCreateHostKey(ctx);
		Check.assumeNotEmpty(hostKey, "{} has a hostKey", session);

		printCtx.setHostKey(hostKey);

		logger.debug("Print package context: {}", printCtx);
		return printCtx;
	}
}
