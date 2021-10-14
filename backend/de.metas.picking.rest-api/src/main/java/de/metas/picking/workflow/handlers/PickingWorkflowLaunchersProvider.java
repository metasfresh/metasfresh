package de.metas.picking.workflow.handlers;

import com.google.common.collect.ImmutableList;
import de.metas.cache.CCache;
import de.metas.common.util.time.SystemTime;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.picking.workflow.PickingJobService;
import de.metas.picking.workflow.model.PickingJobCandidate;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.model.WorkflowLauncher;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import de.metas.workflow.rest_api.service.WFProcessesIndex;
import lombok.NonNull;
import org.adempiere.util.lang.SynchronizedMutable;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Set;

import static de.metas.picking.workflow.handlers.activity_handlers.PickingWFActivityHelper.extractShipmentScheduleIds;

class PickingWorkflowLaunchersProvider
{
	private final PickingJobService pickingJobService;
	private final WFProcessesIndex wfProcesses;

	private final CCache<UserId, SynchronizedMutable<WorkflowLaunchersList>> launchersCache = CCache.<UserId, SynchronizedMutable<WorkflowLaunchersList>>builder()
			.build();

	PickingWorkflowLaunchersProvider(
			@NonNull final PickingJobService pickingJobService,
			@NonNull final WFProcessesIndex wfProcesses)
	{
		this.pickingJobService = pickingJobService;
		this.wfProcesses = wfProcesses;
	}

	public WorkflowLaunchersList provideLaunchers(
			@NonNull final UserId userId,
			@NonNull final Duration maxStaleAccepted)
	{
		return launchersCache.getOrLoad(userId, SynchronizedMutable::empty)
				.compute(previousLaunchers -> checkStateAndComputeLaunchers(userId, maxStaleAccepted, previousLaunchers));
	}

	private WorkflowLaunchersList checkStateAndComputeLaunchers(
			final @NonNull UserId userId,
			final @NonNull Duration maxStaleAccepted,
			final @Nullable WorkflowLaunchersList previousLaunchers)
	{
		if (previousLaunchers == null)
		{
			//System.out.println("*** No previous value. A new value will be computed!");
			return computeLaunchers(userId);
		}
		else if (previousLaunchers.isStaled(maxStaleAccepted))
		{
			//System.out.println("*** Value staled. A new value will be computed!");
			return computeLaunchers(userId);
		}
		else
		{
			//System.out.println("*** Value NOT staled");
			return previousLaunchers;
		}
	}

	private WorkflowLaunchersList computeLaunchers(final @NonNull UserId userId)
	{
		final ArrayList<WorkflowLauncher> result = new ArrayList<>();

		//
		// Already started launchers
		final ImmutableList<WFProcess> existingWFProcesses = wfProcesses.getByInvokerId(userId);
		existingWFProcesses.stream()
				.map(PickingWorkflowLaunchersProvider::toExistingWorkflowLauncher)
				.forEach(result::add);

		//
		// New launchers
		final Set<ShipmentScheduleId> shipmentScheduleIdsAlreadyInPickingJobs = extractShipmentScheduleIds(existingWFProcesses);
		pickingJobService.streamPickingJobCandidates(userId, shipmentScheduleIdsAlreadyInPickingJobs)
				.map(PickingWorkflowLaunchersProvider::toNewWorkflowLauncher)
				.forEach(result::add);

		return WorkflowLaunchersList.builder()
				.launchers(ImmutableList.copyOf(result))
				.timestamp(SystemTime.asInstant())
				.build();
	}

	private static WorkflowLauncher toNewWorkflowLauncher(@NonNull final PickingJobCandidate pickingJobCandidate)
	{
		return WorkflowLauncher.builder()
				.handlerId(PickingWFProcessHandler.HANDLER_ID)
				.caption(PickingWFProcessUtils.workflowCaption()
						.salesOrderDocumentNo(pickingJobCandidate.getSalesOrderDocumentNo())
						.customerName(pickingJobCandidate.getCustomerName())
						.build())
				.startedWFProcessId(null)
				.wfParameters(pickingJobCandidate.getWfProcessStartParams().toParams())
				.build();
	}

	private static WorkflowLauncher toExistingWorkflowLauncher(@NonNull final WFProcess wfProcess)
	{
		return WorkflowLauncher.builder()
				.handlerId(PickingWFProcessHandler.HANDLER_ID)
				.caption(wfProcess.getCaption())
				.startedWFProcessId(wfProcess.getId())
				.build();
	}

	public void invalidateCacheByUserId(@NonNull final UserId invokerId)
	{
		final SynchronizedMutable<WorkflowLaunchersList> userCachedWorkflows = launchersCache.get(invokerId);
		if (userCachedWorkflows != null)
		{
			userCachedWorkflows.setValue(null);
		}
	}
}
