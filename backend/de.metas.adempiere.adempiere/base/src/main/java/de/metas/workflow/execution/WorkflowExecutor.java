/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.workflow.execution;

import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessInfo;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.TrxCallable;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_WF_Activity;
import de.metas.workflow.WFState;
import org.compiere.util.Env;
import org.compiere.wf.MWFProcess;
import de.metas.workflow.Workflow;
import org.slf4j.Logger;

import javax.annotation.Nullable;

public class WorkflowExecutor
{
	private static final Logger log = LogManager.getLogger(WorkflowExecutor.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	private final Workflow workflow;
	private final ProcessInfo processInfo;
	private final String adLanguage;

	@Builder
	private WorkflowExecutor(
			@NonNull final Workflow workflow,
			@NonNull final ProcessInfo processInfo)
	{
		this.workflow = workflow;
		this.processInfo = processInfo;
		this.adLanguage = Env.getADLanguageOrBaseLanguage();
	}

	public MWFProcess start()
	{

		return trxManager.callInThreadInheritedTrx(new TrxCallable<MWFProcess>()
		{
			@Override
			public MWFProcess call()
			{
				final MWFProcess wfProcess = new MWFProcess(workflow, processInfo);
				InterfaceWrapperHelper.save(wfProcess);

				processInfo.getResult().setSummary(msgBL.translatable("Processing").translate(adLanguage));

				wfProcess.startWork();

				return wfProcess;
			}

			@Override
			public boolean doCatch(final Throwable ex)
			{
				log.error("Failed starting workflow {} for {}", workflow, processInfo, ex);
				final ProcessExecutionResult result = processInfo.getResult();
				result.markAsError(ex);
				return ROLLBACK;
			}
		});
	}

	@Nullable
	public MWFProcess startAndWait()
	{
		final int SLEEP = 500;        //	1/2 sec
		final int MAXLOOPS = 30;    //	15 sec
		//
		final MWFProcess process = start();
		if (process == null)
		{
			return null;
		}

		Thread.yield();
		int loops = 0;
		WFState state = process.getState();
		while (!state.isClosed() && !state.isSuspended())
		{
			if (loops > MAXLOOPS)
			{
				log.warn("startAndWait: Timeout after {} seconds", ((SLEEP * MAXLOOPS) / 1000));
				final ProcessExecutionResult result = processInfo.getResult();
				result.setSummary(msgBL.translatable("ProcessRunning").translate(adLanguage));
				result.setTimeout(true);
				return process;
			}

			try
			{
				Thread.sleep(SLEEP);
				loops++;
			}
			catch (final InterruptedException e)
			{
				log.warn("startAndWait: interrupted", e);
				final ProcessExecutionResult result = processInfo.getResult();
				result.markAsError("Interrupted", e);
				return process;
			}
			Thread.yield();

			state = process.getState();
		}

		updateProcessExecutionResult(processInfo.getResult(), process);

		return process;
	}

	private void updateProcessExecutionResult(
			@NonNull final ProcessExecutionResult result,
			@NonNull final MWFProcess fromWFProcess)
	{
		final WFState state = fromWFProcess.getState();
		String summary = fromWFProcess.getProcessMsg();
		if (Check.isBlank(summary))
		{
			final I_AD_WF_Activity activity = fromWFProcess.getFirstActivityByWFState(state).orElse(null);
			if (activity != null)
			{
				summary = activity.getTextMsg();
			}
		}
		if (Check.isBlank(summary))
		{
			summary = state.toString();
		}

		if (state.isTerminated() || state.isAborted())
		{
			result.markAsError(summary);
		}
		else
		{
			result.markAsSuccess(summary);
		}
		log.debug("startAndWait done: {}", summary);
	}
}
