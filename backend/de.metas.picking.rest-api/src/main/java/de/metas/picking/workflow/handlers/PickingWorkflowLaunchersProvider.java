package de.metas.picking.workflow.handlers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.picking.job.model.PickingJobCandidate;
import de.metas.handlingunits.picking.job.model.PickingJobFacets;
import de.metas.handlingunits.picking.job.model.PickingJobFacetsQuery;
import de.metas.handlingunits.picking.job.model.PickingJobQuery;
import de.metas.handlingunits.picking.job.model.PickingJobReference;
import de.metas.inout.ShipmentScheduleId;
import de.metas.picking.workflow.PickingJobRestService;
import de.metas.picking.workflow.PickingWFProcessStartParams;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLauncher;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import de.metas.workflow.rest_api.model.WorkflowLaunchersQuery;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetGroupList;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.util.lang.SynchronizedMutable;

import javax.annotation.Nullable;
import java.util.ArrayList;

import static de.metas.picking.workflow.handlers.PickingMobileApplication.APPLICATION_ID;

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

	public WorkflowLaunchersList provideLaunchers(@NonNull WorkflowLaunchersQuery query)
	{
		return launchersCache.getOrLoad(query.getUserId(), SynchronizedMutable::empty)
				.compute(previousLaunchers -> checkStateAndComputeLaunchers(query, previousLaunchers));
	}

	private WorkflowLaunchersList checkStateAndComputeLaunchers(
			@NonNull WorkflowLaunchersQuery query,
			@Nullable WorkflowLaunchersList previousLaunchers)
	{
		if (previousLaunchers == null || previousLaunchers.isStaled(query.getMaxStaleAccepted()))
		{
			return computeLaunchers(query);
		}
		else
		{
			return previousLaunchers;
		}
	}

	private WorkflowLaunchersList computeLaunchers(@NonNull WorkflowLaunchersQuery query)
	{
		final UserId userId = query.getUserId();
		final QueryLimit limit = query.getLimit().orElse(QueryLimit.NO_LIMIT);
		final PickingJobFacetsQuery facets = PickingJobFacetsUtils.toPickingJobFacetsQuery(query.getFacetIds());

		final ArrayList<WorkflowLauncher> currentResult = new ArrayList<>();

		//
		// Already started launchers
		final ImmutableList<PickingJobReference> existingPickingJobs = pickingJobRestService.streamDraftPickingJobReferences(userId)
				.filter(facets::isMatching)
				.collect(ImmutableList.toImmutableList());
		existingPickingJobs.stream()
				.map(PickingWorkflowLaunchersProvider::toExistingWorkflowLauncher)
				.forEach(currentResult::add);

		//
		// New launchers
		if (!limit.isLimitHitOrExceeded(existingPickingJobs))
		{
			final ImmutableSet<ShipmentScheduleId> shipmentScheduleIdsAlreadyInPickingJobs = existingPickingJobs.stream()
					.flatMap(existingPickingJob -> existingPickingJob.getShipmentScheduleIds().stream())
					.collect(ImmutableSet.toImmutableSet());

			pickingJobRestService.streamPickingJobCandidates(PickingJobQuery.builder()
							.userId(userId)
							.excludeShipmentScheduleIds(shipmentScheduleIdsAlreadyInPickingJobs)
							.facets(facets)
							.build())
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
				.applicationId(APPLICATION_ID)
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
				.applicationId(APPLICATION_ID)
				.caption(PickingWFProcessUtils.workflowCaption()
						.salesOrderDocumentNo(pickingJobReference.getSalesOrderDocumentNo())
						.customerName(pickingJobReference.getCustomerName())
						.build())
				.startedWFProcessId(WFProcessId.ofIdPart(APPLICATION_ID, pickingJobReference.getPickingJobId()))
				.build();
	}

	public WorkflowLaunchersFacetGroupList getFacets(@NonNull final UserId userId)
	{
		final PickingJobFacets pickingFacets = pickingJobRestService.getFacets(PickingJobQuery.ofUserId(userId));
		return PickingJobFacetsUtils.toWorkflowLaunchersFacetGroupList(pickingFacets);
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
