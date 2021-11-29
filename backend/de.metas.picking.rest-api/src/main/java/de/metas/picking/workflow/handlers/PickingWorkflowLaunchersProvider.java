package de.metas.picking.workflow.handlers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.picking.job.model.PickingJobCandidate;
import de.metas.handlingunits.picking.job.model.PickingJobReference;
import de.metas.inout.ShipmentScheduleId;
import de.metas.picking.workflow.PickingJobRestService;
import de.metas.picking.workflow.PickingWFProcessStartParams;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLauncher;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.util.lang.SynchronizedMutable;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Set;

import static de.metas.picking.workflow.handlers.PickingMobileApplication.HANDLER_ID;

class PickingWorkflowLaunchersProvider
{
	private final PickingJobRestService pickingJobRestService;

	private final CCache<UserId, SynchronizedMutable<WorkflowLaunchersList>> launchersCache = CCache.<UserId, SynchronizedMutable<WorkflowLaunchersList>>builder()
			.build();

	PickingWorkflowLaunchersProvider(
			@NonNull final PickingJobRestService pickingJobRestService)
	{
		this.pickingJobRestService = pickingJobRestService;
	}

	public WorkflowLaunchersList provideLaunchers(
			@NonNull final UserId userId,
			@NonNull final QueryLimit suggestedLimit,
			@NonNull final Duration maxStaleAccepted)
	{
		return launchersCache.getOrLoad(userId, SynchronizedMutable::empty)
				.compute(previousLaunchers -> checkStateAndComputeLaunchers(userId, suggestedLimit, maxStaleAccepted, previousLaunchers));
	}

	private WorkflowLaunchersList checkStateAndComputeLaunchers(
			final @NonNull UserId userId,
			final @NonNull QueryLimit suggestedLimit,
			final @NonNull Duration maxStaleAccepted,
			final @Nullable WorkflowLaunchersList previousLaunchers)
	{
		if (previousLaunchers == null)
		{
			//System.out.println("*** No previous value. A new value will be computed!");
			return computeLaunchers(userId, suggestedLimit);
		}
		else if (previousLaunchers.isStaled(maxStaleAccepted))
		{
			//System.out.println("*** Value staled. A new value will be computed!");
			return computeLaunchers(userId, suggestedLimit);
		}
		else
		{
			//System.out.println("*** Value NOT staled");
			return previousLaunchers;
		}
	}

	private WorkflowLaunchersList computeLaunchers(
			final @NonNull UserId userId,
			final @NonNull QueryLimit limit)
	{
		final ArrayList<WorkflowLauncher> currentResult = new ArrayList<>();

		//
		// Already started launchers
		final ImmutableList<PickingJobReference> existingPickingJobs = pickingJobRestService.streamDraftPickingJobReferences(userId)
				.collect(ImmutableList.toImmutableList());
		existingPickingJobs.stream()
				.map(PickingWorkflowLaunchersProvider::toExistingWorkflowLauncher)
				.forEach(currentResult::add);

		//
		// New launchers
		if (!limit.isLimitHitOrExceeded(existingPickingJobs))
		{
			final Set<ShipmentScheduleId> shipmentScheduleIdsAlreadyInPickingJobs = existingPickingJobs.stream()
					.flatMap(existingPickingJob -> existingPickingJob.getShipmentScheduleIds().stream())
					.collect(ImmutableSet.toImmutableSet());

			pickingJobRestService.streamPickingJobCandidates(userId, shipmentScheduleIdsAlreadyInPickingJobs)
					.limit(limit.minusSizeOf(currentResult).toIntOr(Integer.MAX_VALUE))
					.map(PickingWorkflowLaunchersProvider::toNewWorkflowLauncher)
					.forEach(currentResult::add);
		}

		return WorkflowLaunchersList.builder()
				.launchers(ImmutableList.copyOf(currentResult))
				.timestamp(SystemTime.asInstant())
				.build();
	}

	private static WorkflowLauncher toNewWorkflowLauncher(@NonNull final PickingJobCandidate pickingJobCandidate)
	{
		return WorkflowLauncher.builder()
				.applicationId(HANDLER_ID)
				.caption(PickingWFProcessUtils.workflowCaption()
						.salesOrderDocumentNo(pickingJobCandidate.getSalesOrderDocumentNo())
						.customerName(pickingJobCandidate.getCustomerName())
						.build())
				.startedWFProcessId(null)
				.wfParameters(PickingWFProcessStartParams.of(pickingJobCandidate).toParams())
				.partiallyHandledBefore(pickingJobCandidate.isPartiallyPickedBefore())
				.build();
	}

	private static WorkflowLauncher toExistingWorkflowLauncher(@NonNull final PickingJobReference pickingJobReference)
	{
		return WorkflowLauncher.builder()
				.applicationId(HANDLER_ID)
				.caption(PickingWFProcessUtils.workflowCaption()
						.salesOrderDocumentNo(pickingJobReference.getSalesOrderDocumentNo())
						.customerName(pickingJobReference.getCustomerName())
						.build())
				.startedWFProcessId(WFProcessId.ofIdPart(HANDLER_ID, pickingJobReference.getPickingJobId()))
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
