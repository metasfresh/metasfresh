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
import de.metas.handlingunits.picking.job.model.PickingJobReferenceQuery;
import de.metas.i18n.ITranslatableString;
import de.metas.inout.ShipmentScheduleId;
import de.metas.picking.config.MobileUIPickingUserProfile;
import de.metas.picking.config.MobileUIPickingUserProfileRepository;
import de.metas.picking.workflow.BPLocationIndex;
import de.metas.picking.workflow.DisplayValueProvider;
import de.metas.picking.workflow.PickingJobRestService;
import de.metas.picking.workflow.PickingWFProcessStartParams;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLauncher;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import de.metas.workflow.rest_api.model.WorkflowLaunchersQuery;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetGroupList;
import de.metas.workplace.Workplace;
import de.metas.workplace.WorkplaceService;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.util.lang.SynchronizedMutable;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.util.ArrayList;

import static de.metas.picking.workflow.handlers.PickingMobileApplication.APPLICATION_ID;

class PickingWorkflowLaunchersProvider
{
	private final PickingJobRestService pickingJobRestService;
	private final MobileUIPickingUserProfileRepository mobileUIPickingUserProfileRepository;
	private final WorkplaceService workplaceService;
	private final DisplayValueProvider displayValueProvider;

	private final CCache<UserId, SynchronizedMutable<WorkflowLaunchersList>> launchersCache = CCache.<UserId, SynchronizedMutable<WorkflowLaunchersList>>builder()
			.build();

	PickingWorkflowLaunchersProvider(
			@NonNull final PickingJobRestService pickingJobRestService,
			@NonNull final MobileUIPickingUserProfileRepository mobileUIPickingUserProfileRepository,
			@NonNull final WorkplaceService workplaceService,
			@NonNull final DisplayValueProvider displayValueProvider)
	{
		this.pickingJobRestService = pickingJobRestService;
		this.mobileUIPickingUserProfileRepository = mobileUIPickingUserProfileRepository;
		this.workplaceService = workplaceService;
		this.displayValueProvider = displayValueProvider;
	}

	public WorkflowLaunchersList provideLaunchers(@NonNull final WorkflowLaunchersQuery query)
	{
		return launchersCache.getOrLoad(query.getUserId(), SynchronizedMutable::empty)
				.compute(previousLaunchers -> checkStateAndComputeLaunchers(query, previousLaunchers));
	}

	private WorkflowLaunchersList checkStateAndComputeLaunchers(
			@NonNull final WorkflowLaunchersQuery query,
			@Nullable final WorkflowLaunchersList previousLaunchers)
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

	private WorkflowLaunchersList computeLaunchers(@NonNull final WorkflowLaunchersQuery query)
	{
		final UserId userId = query.getUserId();
		final QueryLimit limit = query.getLimit().orElse(QueryLimit.NO_LIMIT);
		final PickingJobFacetsQuery facets = PickingJobFacetsUtils.toPickingJobFacetsQuery(query.getFacetIds());
		final BPLocationIndex<String> locationCaptionIndex = new BPLocationIndex<>();

		final ArrayList<WorkflowLauncher> currentResult = new ArrayList<>();

		final MobileUIPickingUserProfile profile = mobileUIPickingUserProfileRepository.getProfile();
		final WarehouseId workplaceWarehouseId = workplaceService.getWorkplaceByUserId(userId)
				.map(Workplace::getWarehouseId)
				.orElse(null);
		//
		// Already started launchers
		final ImmutableList<PickingJobReference> existingPickingJobs = pickingJobRestService.streamDraftPickingJobReferences(
						PickingJobReferenceQuery.builder()
								.pickerId(userId)
								.onlyBPartnerIds(profile.getOnlyBPartnerIds())
								.warehouseId(workplaceWarehouseId)
								.build())
				.filter(facets::isMatching)
				.collect(ImmutableList.toImmutableList());
		existingPickingJobs.stream()
				.map(pickingJobReference -> this.toExistingWorkflowLauncher(pickingJobReference, profile, locationCaptionIndex))
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
																	 .onlyBPartnerIds(profile.getOnlyBPartnerIds())
																	 .warehouseId(workplaceWarehouseId)
																	 .build())
					.limit(limit.minusSizeOf(currentResult).toIntOr(Integer.MAX_VALUE))
					.map(pickingJobCandidate -> toNewWorkflowLauncher(pickingJobCandidate, profile, locationCaptionIndex))
					.forEach(currentResult::add);
		}

		return WorkflowLaunchersList.builder()
				.launchers(ImmutableList.copyOf(currentResult))
				.timestamp(SystemTime.asInstant())
				.build();
	}

	@NonNull
	private WorkflowLauncher toNewWorkflowLauncher(
			@NonNull final PickingJobCandidate pickingJobCandidate,
			@NonNull final MobileUIPickingUserProfile profile,
			@NonNull final BPLocationIndex<String> locationCaptionIndex)
	{
		final ITranslatableString caption = displayValueProvider.computeSummaryCaption(
				profile,
				displayValueProvider.toUIDescriptor(pickingJobCandidate),
				locationCaptionIndex);

		return WorkflowLauncher.builder()
				.applicationId(APPLICATION_ID)
				.caption(caption)
				.startedWFProcessId(null)
				.wfParameters(PickingWFProcessStartParams.of(pickingJobCandidate).toParams())
				.partiallyHandledBefore(pickingJobCandidate.isPartiallyPickedBefore())
				.build();
	}

	@NonNull
	private WorkflowLauncher toExistingWorkflowLauncher(
			@NonNull final PickingJobReference pickingJobReference,
			@NonNull final MobileUIPickingUserProfile profile,
			@NonNull final BPLocationIndex<String> locationIndex)
	{
		final ITranslatableString caption = displayValueProvider.computeSummaryCaption(
				profile,
				DisplayValueProvider.toUIDescriptor(pickingJobReference),
				locationIndex);

		return WorkflowLauncher.builder()
				.applicationId(APPLICATION_ID)
				.caption(caption)
				.startedWFProcessId(WFProcessId.ofIdPart(APPLICATION_ID, pickingJobReference.getPickingJobId()))
				.build();
	}

	public WorkflowLaunchersFacetGroupList getFacets(@NonNull final UserId userId)
	{
		final MobileUIPickingUserProfile profile = mobileUIPickingUserProfileRepository.getProfile();
		final WarehouseId workplaceWarehouseId = workplaceService.getWorkplaceByUserId(userId)
				.map(Workplace::getWarehouseId)
				.orElse(null);

		final PickingJobFacets pickingFacets = pickingJobRestService.getFacets(PickingJobQuery.builder()
																					   .userId(userId)
																					   .onlyBPartnerIds(profile.getOnlyBPartnerIds())
																					   .warehouseId(workplaceWarehouseId)
																					   .build(),
																			   profile);

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
