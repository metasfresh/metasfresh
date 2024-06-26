package de.metas.picking.workflow.handlers;

import com.google.common.collect.ImmutableList;
import de.metas.cache.CCache;
import de.metas.common.util.time.SystemTime;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.handlingunits.picking.job.model.PickingJobCandidate;
import de.metas.handlingunits.picking.job.model.PickingJobFacetGroup;
import de.metas.handlingunits.picking.job.model.PickingJobFacets;
import de.metas.handlingunits.picking.job.model.PickingJobQuery;
import de.metas.handlingunits.picking.job.model.PickingJobReference;
import de.metas.handlingunits.picking.job.model.PickingJobReferenceList;
import de.metas.handlingunits.picking.job.model.PickingJobReferenceQuery;
import de.metas.handlingunits.picking.job.model.RenderedAddressProvider;
import de.metas.picking.config.MobileUIPickingUserProfile;
import de.metas.picking.config.MobileUIPickingUserProfileRepository;
import de.metas.picking.config.PickingJobFieldType;
import de.metas.picking.workflow.DisplayValueProvider;
import de.metas.picking.workflow.DisplayValueProviderService;
import de.metas.picking.workflow.PickingJobRestService;
import de.metas.picking.workflow.PickingWFProcessStartParams;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLauncher;
import de.metas.workflow.rest_api.model.WorkflowLauncherCaption;
import de.metas.workflow.rest_api.model.WorkflowLauncherCaption.OrderBy;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import de.metas.workflow.rest_api.model.WorkflowLaunchersQuery;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetGroupList;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetQuery;
import de.metas.workplace.WorkplaceService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.util.lang.SynchronizedMutable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static de.metas.picking.workflow.handlers.PickingMobileApplication.APPLICATION_ID;

@RequiredArgsConstructor
class PickingWorkflowLaunchersProvider
{
	@NonNull private final PickingJobRestService pickingJobRestService;
	@NonNull private final MobileUIPickingUserProfileRepository mobileUIPickingUserProfileRepository;
	@NonNull private final WorkplaceService workplaceService;
	@NonNull private final DisplayValueProviderService displayValueProviderService;
	@NonNull private final IDocumentLocationBL documentLocationBL;

	private final CCache<UserId, SynchronizedMutable<WorkflowLaunchersList>> launchersCache = CCache.<UserId, SynchronizedMutable<WorkflowLaunchersList>>builder()
			.build();

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
		final PickingJobQuery.Facets facets = PickingJobFacetsUtils.toPickingJobFacetsQuery(query.getFacetIds());

		final ArrayList<WorkflowLauncher> currentResult = new ArrayList<>();

		//
		// Already started launchers
		final MobileUIPickingUserProfile profile = mobileUIPickingUserProfileRepository.getProfile();
		final PickingJobReferenceList existingPickingJobs = pickingJobRestService.streamDraftPickingJobReferences(
						PickingJobReferenceQuery.builder()
								.pickerId(userId)
								.onlyBPartnerIds(profile.getOnlyBPartnerIds())
								.warehouseId(workplaceService.getWarehouseIdByUserId(userId).orElse(null))
								.salesOrderDocumentNo(query.getFilterByDocumentNo())
								.build())
				.filter(facets::isMatching)
				.collect(PickingJobReferenceList.collect());

		final DisplayValueProvider displayValueProvider = displayValueProviderService.newDisplayValueProvider(profile);
		displayValueProvider.cacheWarmUpForPickingJobReferences(existingPickingJobs);

		existingPickingJobs.streamNotInProcessing()
				.map(pickingJobReference -> toExistingWorkflowLauncher(pickingJobReference, displayValueProvider))
				.forEach(currentResult::add);

		//
		// New launchers
		if (!limit.isLimitHitOrExceeded(currentResult))
		{
			final ImmutableList<PickingJobCandidate> newPickingJobCandidates = pickingJobRestService.streamPickingJobCandidates(PickingJobQuery.builder()
							.userId(userId)
							.excludeShipmentScheduleIds(existingPickingJobs.getShipmentScheduleIds())
							.facets(facets)
							.onlyBPartnerIds(profile.getOnlyBPartnerIds())
							.warehouseId(workplaceService.getWarehouseIdByUserId(userId).orElse(null))
							.salesOrderDocumentNo(query.getFilterByDocumentNo())
							.build())
					.limit(limit.minusSizeOf(currentResult).toIntOr(Integer.MAX_VALUE))
					.collect(ImmutableList.toImmutableList());

			displayValueProvider.cacheWarmUpForPickingJobCandidates(newPickingJobCandidates);

			newPickingJobCandidates.stream()
					.map(pickingJobCandidate -> toNewWorkflowLauncher(pickingJobCandidate, displayValueProvider))
					.forEach(currentResult::add);
		}

		return newWorkflowLaunchersList(currentResult);
	}

	private static WorkflowLaunchersList newWorkflowLaunchersList(final List<WorkflowLauncher> currentResult)
	{
		return WorkflowLaunchersList.builder()
				.launchers(ImmutableList.copyOf(currentResult))
				.orderByField(OrderBy.descending(PickingJobFieldType.RUESTPLATZ_NR))
				.timestamp(SystemTime.asInstant())
				.build();
	}

	@NonNull
	private static WorkflowLauncher toNewWorkflowLauncher(
			@NonNull final PickingJobCandidate pickingJobCandidate,
			@NonNull final DisplayValueProvider displayValueProvider)
	{
		return WorkflowLauncher.builder()
				.applicationId(APPLICATION_ID)
				.caption(displayValueProvider.computeLauncherCaption(pickingJobCandidate))
				.startedWFProcessId(null)
				.wfParameters(PickingWFProcessStartParams.of(pickingJobCandidate).toParams())
				.partiallyHandledBefore(pickingJobCandidate.isPartiallyPickedBefore())
				.build();
	}

	@NonNull
	private static WorkflowLauncher toExistingWorkflowLauncher(
			@NonNull final PickingJobReference pickingJobReference,
			@NonNull final DisplayValueProvider displayValueProvider)
	{
		final WorkflowLauncherCaption caption = displayValueProvider.computeLauncherCaption(pickingJobReference);

		return WorkflowLauncher.builder()
				.applicationId(APPLICATION_ID)
				.caption(caption)
				.startedWFProcessId(WFProcessId.ofIdPart(APPLICATION_ID, pickingJobReference.getPickingJobId()))
				.build();
	}

	public WorkflowLaunchersFacetGroupList getFacets(@NonNull final WorkflowLaunchersFacetQuery query)
	{
		final UserId userId = query.getUserId();
		final PickingJobQuery.Facets activeFacets = PickingJobFacetsUtils.toPickingJobFacetsQuery(query.getActiveFacetIds());

		final MobileUIPickingUserProfile profile = mobileUIPickingUserProfileRepository.getProfile();
		final ImmutableList<PickingJobFacetGroup> groups = profile.getFilterGroupsInOrder();
		if (groups.isEmpty())
		{
			return WorkflowLaunchersFacetGroupList.EMPTY;
		}

		final PickingJobFacets pickingFacets = pickingJobRestService.getFacets(
				PickingJobQuery.builder()
						.userId(userId)
						.onlyBPartnerIds(profile.getOnlyBPartnerIds())
						.warehouseId(workplaceService.getWarehouseIdByUserId(userId).orElse(null))
						.salesOrderDocumentNo(query.getFilterByDocumentNo())
						//.facets(activeFacets) // IMPORTANT: don't filter by active facets because we want to collect all facets, not only the active ones
						.build(),
				PickingJobFacets.CollectingParameters.builder()
						.addressProvider(RenderedAddressProvider.newInstance(documentLocationBL))
						.groupsInOrder(groups)
						.activeFacets(activeFacets)
						.build());

		return PickingJobFacetsUtils.toWorkflowLaunchersFacetGroupList(pickingFacets, profile);
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
