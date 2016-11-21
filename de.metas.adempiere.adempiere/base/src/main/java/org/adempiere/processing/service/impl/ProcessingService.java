package org.adempiere.processing.service.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.IssueReportableExceptions;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.processing.exception.ProcessingException;
import org.adempiere.processing.interfaces.IProcessablePO;
import org.adempiere.processing.model.MADProcessablePO;
import org.adempiere.processing.service.IProcessingService;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.MIssue;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.X_AD_Issue;
import org.compiere.util.DB;
import org.compiere.util.TrxRunnableAdapter;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.JavaProcess;

public class ProcessingService implements IProcessingService
{
	private static final Logger logger = LogManager.getLogger(ProcessingService.class);

	private static final ProcessingService instance = new ProcessingService();

	public static ProcessingService get()
	{
		return instance;
	}

	private ProcessingService()
	{
	}

	@Override
	public void handleProcessingException(
			final Properties ctx,
			final IProcessablePO poToProcess,
			final ProcessingException e,
			final String trxName)
	{
		final int processId;
		if (e.getAdPInstanceId() != NO_AD_PINSTANCE_ID)
		{
			final I_AD_PInstance pInstance = Services.get(IADPInstanceDAO.class).retrieveAD_PInstance(ctx, e.getAdPInstanceId());
			processId = pInstance.getAD_Process_ID();
		}
		else
		{
			processId = 0;
		}

		final Throwable cause;
		if (e.getCause() == null)
		{
			cause = e;
		}
		else
		{
			cause = e.getCause();
		}
		if (LogManager.isLevelFine())
			logger.warn(e.getLocalizedMessage(), e);

		final LogRecord logRecord = new LogRecord(Level.SEVERE, e.getMessage());
		logRecord.setThrown(cause);
		logRecord.setSourceClassName(getClass().getCanonicalName());
		logRecord.setSourceMethodName("invokeCommissionType");

		// 02367 create our issue withing alocal trx temporarily relax the trx constraints accordingly
		DB.saveConstraints(); 
		try
		{
			Services.get(ITrxManager.class).run(new TrxRunnableAdapter()
			{
				@Override
				public void run(final String trxName)
				{
					DB.getConstraints().incMaxTrx(1).addAllowedTrxNamePrefix(trxName);

					final MIssue issue = new MIssue(logRecord);
					issue.set_TrxName(trxName);
					issue.setName("CandidateProcessingError");
					issue.setIssueSource(X_AD_Issue.ISSUESOURCE_Process);
					issue.setIssueSummary(e.getMessage());
					issue.setLoggerName(logger.getName());
					issue.setComments(e.getMessage());
					issue.setAD_Process_ID(processId);

					final StringBuilder errorTrace = new StringBuilder();
					for (final StackTraceElement ste : cause.getStackTrace())
					{
						errorTrace.append(ste.toString() + "\n");
					}
					issue.setErrorTrace(errorTrace.toString());

					final StringBuilder stackTrace = new StringBuilder();
					for (final StackTraceElement ste : Thread.currentThread().getStackTrace())
					{
						stackTrace.append(ste.toString() + "\n");
					}
					issue.setStackTrace(stackTrace.toString());

					issue.saveEx();
					IssueReportableExceptions.markReportedIfPossible(cause, issue.getAD_Issue_ID());

					poToProcess.setIsError(true);
					poToProcess.setAD_Issue_ID(issue.get_ID());

					InterfaceWrapperHelper.save(poToProcess);
				}
			});
		}
		finally
		{
			DB.restoreConstraints();
		}
	}

	@Override
	public void process(final MADProcessablePO processablePOPointer, final JavaProcess parent)
	{
		final int adPInstanceId;
		if (parent == null)
		{
			adPInstanceId = NO_AD_PINSTANCE_ID;
		}
		else
		{
			adPInstanceId = parent.getAD_PInstance_ID();
		}

		try
		{
			final PO poToProcess = processablePOPointer.retrievePO(false);

			if (poToProcess == null)
			{
				logger.info("Deleting " + processablePOPointer + ", as the refenced PO doesn't exist anymore");
				processablePOPointer.deleteEx(true); // metas-ts 1076
				return;
			}

			final ModelValidator mv = processablePOPointer.getModelValidator();

			Services.get(ITrxManager.class).run(processablePOPointer.getTrxName(), new TrxRunnableAdapter()
			{
				@Override
				public void run(final String trxName)
				{
					try
					{
						// here is the actual subsequent invocation!
						final String result = mv.modelChange(poToProcess, ModelValidator.TYPE_SUBSEQUENT);
						// assume is processed if Processed field is missing and return message is null
						final boolean processed = isProcessed(poToProcess, result == null);

						if (result == null)
						{
							if (processed)
							{
								logger.info(processablePOPointer + " has been successfully processed");
								processablePOPointer.setProcessed(true);
								processablePOPointer.saveEx();
							}
							else
							{
								logger.info(processablePOPointer + " has been postponed");
								processablePOPointer.setProcessed(false);
								processablePOPointer.saveEx();
							}
						}
						else
						{
							final ProcessingException pe = new ProcessingException(result, null, adPInstanceId);
							handleError(processablePOPointer, parent, result, pe);
						}
					}
					catch (final Exception e)
					{
						throw new RuntimeException(e);
					}
				}
			});
		}
		catch (final RuntimeException e)
		{
			final String summary = e.getMessage();

			final ProcessingException pe = new ProcessingException(summary, e, adPInstanceId);
			handleError(processablePOPointer, parent, summary, pe);
		}
	}

	private void handleError(final MADProcessablePO processablePOPointer, final JavaProcess parent, final String summary, final ProcessingException pe)
	{
		handleProcessingException(
				processablePOPointer.getCtx(),
				processablePOPointer,
				pe,
				processablePOPointer.get_TrxName());

		if (parent != null)
		{
			parent.addLog(processablePOPointer.get_ID(), new Timestamp(System.currentTimeMillis()), null, summary);
		}
	}

	/**
	 * Check if the PO is processed
	 * 
	 * @param po
	 * @param defaultValue
	 *            default value that should be returned if the PO does not have the Processed column
	 * @return true if processed
	 */
	private boolean isProcessed(PO po, boolean defaultValue)
	{
		int idx = po.get_ColumnIndex("Processed");
		if (idx < 0)
			return defaultValue;
		return po.get_ValueAsBoolean(idx);
	}
}
