package de.metas.picking.workflow.handlers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.picking.job.model.PickingJobCandidate;
import de.metas.handlingunits.picking.job.model.PickingJobFacets;
import de.metas.handlingunits.picking.job.model.PickingJobReference;
import de.metas.i18n.TranslatableStrings;
import de.metas.inout.ShipmentScheduleId;
import de.metas.picking.workflow.PickingJobRestService;
import de.metas.picking.workflow.PickingWFProcessStartParams;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLauncher;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import de.metas.workflow.rest_api.model.WorkflowLaunchersQuery;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacet;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetGroup;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetGroupId;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetGroupList;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.util.lang.SynchronizedMutable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Set;

import static de.metas.picking.workflow.handlers.PickingMobileApplication.APPLICATION_ID;

class PickingWorkflowLaunchersProvider
{
	private final PickingJobRestService pickingJobRestService;

	private final CCache<UserId, SynchronizedMutable<WorkflowLaunchersList>> launchersCache = CCache.<UserId, SynchronizedMutable<WorkflowLaunchersList>>builder()
			.build();

	private static final WorkflowLaunchersFacetGroupId CUSTOMERS_FACET_GROUP_ID = WorkflowLaunchersFacetGroupId.ofString("customer");
	private static final WorkflowLaunchersFacetGroupId DELIVERY_DAY_GROUP_ID = WorkflowLaunchersFacetGroupId.ofString("deliveryDay");

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

	public WorkflowLaunchersFacetGroupList getFacets(final UserId userId)
	{
		final PickingJobFacets pickingFacets = pickingJobRestService.getFacets(userId, ImmutableSet.of());
		return toWorkflowLaunchersFacets(pickingFacets);
	}

	private static WorkflowLaunchersFacetGroupList toWorkflowLaunchersFacets(@NonNull final PickingJobFacets pickingFacets)
	{
		return WorkflowLaunchersFacetGroupList.of(
				WorkflowLaunchersFacetGroup.builder()
						.id(CUSTOMERS_FACET_GROUP_ID)
						.caption(TranslatableStrings.adElementOrMessage("Customer_ID"))
						.facets(pickingFacets.getCustomers().stream()
								.map(PickingWorkflowLaunchersProvider::toWorkflowLaunchersFacet)
								.distinct()
								.collect(ImmutableList.toImmutableList()))
						.build(),
				WorkflowLaunchersFacetGroup.builder()
						.id(DELIVERY_DAY_GROUP_ID)
						.caption(TranslatableStrings.adElementOrMessage("DeliveryDate"))
						.facets(pickingFacets.getDeliveryDays().stream()
								.map(PickingWorkflowLaunchersProvider::toWorkflowLaunchersFacet)
								.distinct()
								.collect(ImmutableList.toImmutableList()))
						.build()
		);
	}

	private static WorkflowLaunchersFacet toWorkflowLaunchersFacet(@NonNull final PickingJobFacets.CustomerFacet customer)
	{
		return WorkflowLaunchersFacet.builder()
				.value(String.valueOf(customer.getBpartnerId().getRepoId()))
				.caption(TranslatableStrings.anyLanguage(customer.getCustomerBPValue() + " " + customer.getCustomerName()))
				.build();
	}

	private static WorkflowLaunchersFacet toWorkflowLaunchersFacet(@NonNull final PickingJobFacets.DeliveryDayFacet deliveryDay)
	{
		return WorkflowLaunchersFacet.builder()
				.value(deliveryDay.getDeliveryDate().toString())
				.caption(TranslatableStrings.date(deliveryDay.getDeliveryDate()))
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
