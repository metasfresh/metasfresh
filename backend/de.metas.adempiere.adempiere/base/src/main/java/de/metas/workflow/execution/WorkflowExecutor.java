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

import de.metas.logging.LogManager;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.workflow.WFState;
import de.metas.workflow.Workflow;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.Mutable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.TrxRunnableAdapter;
import org.slf4j.Logger;

import java.util.List;

public class WorkflowExecutor
{
	private static final Logger log = LogManager.getLogger(WorkflowExecutor.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final WFProcessRepository wfProcessRepository;

	private final WorkflowExecutionContext context;

	@Builder
	private WorkflowExecutor(
			@NonNull final Workflow workflow,
			@NonNull final ClientId clientId,
			@NonNull final String adLanguage,
			@NonNull final TableRecordReference documentRef,
			@NonNull final UserId userId)
	{
		this.context = WorkflowExecutionContext.builder()
				.workflow(workflow)
				.clientId(clientId)
				.adLanguage(adLanguage)
				.documentRef(documentRef)
				.userId(userId)
				.build();

		wfProcessRepository = context.getWfProcessRepository();
	}

	public WorkflowExecutionResult start()
	{
		//
		// First, abort all existing processes
		for (final WFProcessId activeProcessId : wfProcessRepository.getActiveProcessIds(context.getDocumentRef()))
		{
			abort(activeProcessId);
		}

		final WFProcess wfProcess = new WFProcess(context);
		final Mutable<Throwable> exceptionHolder = new Mutable<>();

		trxManager.runInThreadInheritedTrx(new TrxRunnableAdapter()
		{
			@Override
			public void run(final String localTrxName)
			{
				wfProcess.startWork();
			}

			@Override
			public boolean doCatch(final Throwable ex)
			{
				//log.warn("Failed starting workflow for {}", context, ex);
				exceptionHolder.setValue(ex);
				return ROLLBACK;
			}

			@Override
			public void doFinally()
			{
				context.saveAll(wfProcess);
			}
		});

		if (exceptionHolder.getValue() != null)
		{
			return WorkflowExecutionResult.builder()
					.summary(AdempiereException.extractMessage(exceptionHolder.getValue()))
					.error(true)
					.exception(exceptionHolder.getValue())
					.build();
		}

		return WorkflowExecutionResult.builder()
				.summary(extractSummary(wfProcess))
				.error(wfProcess.getState().isError())
				.build();
	}

	@NonNull
	private static String extractSummary(@NonNull final WFProcess wfProcess)
	{
		final WFState state = wfProcess.getState();
		String summary = wfProcess.getProcessMsg();
		if (Check.isBlank(summary))
		{
			final WFActivity activity = wfProcess.getFirstActivityByWFState(state).orElse(null);
			if (activity != null)
			{
				summary = activity.getTextMsg();
			}
		}
		if (summary == null || Check.isBlank(summary))
		{
			summary = state.toString();
		}

		return summary;
	}

	public void abort(@NonNull final WFProcessId wfProcessId)
	{
		final WFProcessState wfProcessState = wfProcessRepository.getWFProcessStateById(wfProcessId);
		final List<WFActivityState> wfActivityStates = wfProcessRepository.getWFActivityStates(wfProcessId);
		final WFProcess wfProcess = new WFProcess(context, wfProcessState, wfActivityStates);

		trxManager.runInThreadInheritedTrx(new TrxRunnableAdapter()
		{
			@Override
			public void run(final String localTrxName)
			{
				wfProcess.changeWFStateTo(WFState.Aborted);
			}

			@Override
			public void doFinally()
			{
				context.saveAll(wfProcess);
			}
		});
	}
}
