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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.document.engine.DocStatus;
import de.metas.i18n.AdMessageKey;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.workflow.WFState;
import de.metas.workflow.WorkflowId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.Mutable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.TrxRunnableAdapter;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WorkflowExecutor
{
	private static final AdMessageKey MSG_DocumentStatusChanged = AdMessageKey.of("DocumentStatusChangedNotification");

	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final WFProcessRepository wfProcessRepository;

	private final WorkflowExecutionContext context;

	@Builder
	private WorkflowExecutor(
			@Nullable final ClientId clientId,
			@NonNull final TableRecordReference documentRef,
			@NonNull final UserId userId)
	{
		this.context = WorkflowExecutionContext.builder()
				.clientId(clientId)
				.documentRef(documentRef)
				.userId(userId)
				.build();

		wfProcessRepository = context.getWfProcessRepository();
	}

	public WorkflowExecutionResult start(@NonNull WorkflowId workflowId)
	{
		final HashSet<UserId> previousActiveInvokers = new HashSet<>();

		//
		// Check current active processes for our document.
		// => collect the invokers so be will be able to notify them later
		// => abort them
		for (final WFProcess activeProcess : getActiveProcesses())
		{
			previousActiveInvokers.add(activeProcess.getUserId());
			previousActiveInvokers.add(activeProcess.getInitialUserId());

			abort(activeProcess);
		}

		//
		// Start a new process
		final WFProcess wfProcess = new WFProcess(context, workflowId);
		final Mutable<Throwable> exceptionHolder = new Mutable<>();
		trxManager.runInThreadInheritedTrx(new TrxRunnableAdapter()
		{
			@Override
			public void run(final String localTrxName)
			{
				wfProcess.startWork();
				exceptionHolder.setValue(wfProcess.getProcessingResultException());
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
				context.save(wfProcess);
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

		//
		// Notify
		notifyDocumentStatusChanged(wfProcess, previousActiveInvokers);

		return WorkflowExecutionResult.builder()
				.summary(extractSummary(wfProcess))
				.error(wfProcess.getState().isError())
				.build();
	}

	private void notifyDocumentStatusChanged(
			@NonNull final WFProcess wfProcess,
			@NonNull final Set<UserId> usersToNotify)
	{
		// Notify only if WF process was Completed (and not aborted or suspended)
		if (!wfProcess.getState().isCompleted())
		{
			return;
		}

		final UserId invokerId = wfProcess.getUserId();
		final ImmutableSet<UserId> usersToNotifyExcludingInvoker = usersToNotify
				.stream()
				.filter(userId -> !UserId.equals(userId, invokerId))
				.collect(ImmutableSet.toImmutableSet());
		if (usersToNotifyExcludingInvoker.isEmpty())
		{
			return;
		}

		// don't notify if we are not dealing with a real document
		final DocStatus docStatus = wfProcess.getDocumentStatus().orElse(null);
		if (docStatus == null)
		{
			return;
		}

		final String invokerName = context.getUserFullnameById(invokerId);
		final TableRecordReference documentRef = wfProcess.getDocumentRef();

		for (final UserId userId : usersToNotifyExcludingInvoker)
		{
			context.sendNotification(WFUserNotification.builder()
					.userId(userId)
					.content(MSG_DocumentStatusChanged, invokerName, documentRef, docStatus)
					.documentToOpen(documentRef)
					.build());
		}
	}

	@NonNull
	private static String extractSummary(@NonNull final WFProcess wfProcess)
	{
		final WFState state = wfProcess.getState();
		String summary = wfProcess.getProcessingResultMessage();
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

	private void abort(@NonNull final WFProcess wfProcess)
	{
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
				context.save(wfProcess);
			}
		});
	}

	private List<WFProcess> getActiveProcesses()
	{
		final Set<WFProcessId> wfProcessIds = wfProcessRepository.getActiveProcessIds(context.getDocumentRef());
		return getWFProcesses(wfProcessIds);
	}

	private List<WFProcess> getWFProcesses(final Collection<WFProcessId> wfProcessIds)
	{
		if (wfProcessIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final ImmutableMap<WFProcessId, WFProcessState> wfProcessStates = wfProcessRepository.getWFProcessStateByIds(wfProcessIds);
		final ImmutableListMultimap<WFProcessId, WFActivityState> wfActivityStates = wfProcessRepository.getWFActivityStates(wfProcessIds);

		return wfProcessIds
				.stream()
				.map(wfProcessId -> new WFProcess(
						context,
						wfProcessStates.get(wfProcessId),
						wfActivityStates.get(wfProcessId)))
				.collect(ImmutableList.toImmutableList());
	}

	public void resume()
	{
		getActiveProcesses().forEach(this::resume);
	}

	private void resume(@NonNull final WFProcess wfProcess)
	{
		trxManager.runInThreadInheritedTrx(new TrxRunnableAdapter()
		{
			@Override
			public void run(final String localTrxName)
			{
				wfProcess.resumeWork();
			}

			@Override
			public void doFinally()
			{
				context.save(wfProcess);
			}
		});
	}

}
