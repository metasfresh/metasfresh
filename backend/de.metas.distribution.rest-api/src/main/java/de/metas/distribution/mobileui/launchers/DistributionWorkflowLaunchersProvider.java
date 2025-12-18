package de.metas.distribution.mobileui.launchers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.time.SystemTime;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.distribution.mobileui.DistributionMobileApplication;
import de.metas.distribution.mobileui.config.MobileUIDistributionConfig;
import de.metas.distribution.mobileui.config.MobileUIDistributionConfigRepository;
import de.metas.distribution.mobileui.external_services.product.DistributionProductService;
import de.metas.distribution.mobileui.external_services.sourcedoc.DistributionSourceDocService;
import de.metas.distribution.mobileui.external_services.warehouse.DistributionWarehouseService;
import de.metas.distribution.mobileui.job.model.DDOrderReference;
import de.metas.distribution.mobileui.job.service.DDOrderReferenceQuery;
import de.metas.distribution.mobileui.job.service.DDOrderReferenceQuery.DDOrderReferenceQueryBuilder;
import de.metas.distribution.mobileui.job.service.DistributionJobLoaderSupportingServices;
import de.metas.distribution.mobileui.job.service.DistributionJobQueries;
import de.metas.distribution.mobileui.launchers.facets.DistributionFacetIdsCollection;
import de.metas.distribution.mobileui.launchers.facets.DistributionFacetsCollection;
import de.metas.distribution.mobileui.launchers.facets.DistributionFacetsCollector;
import de.metas.rest_workflows.facets.WorkflowLaunchersFacetGroupList;
import de.metas.rest_workflows.facets.WorkflowLaunchersFacetQuery;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLauncher;
import de.metas.workflow.rest_api.model.WorkflowLauncherCaption;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import de.metas.workflow.rest_api.model.WorkflowLaunchersQuery;
import de.metas.workplace.Workplace;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.qrcode.LocatorQRCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistributionWorkflowLaunchersProvider
{
	@NonNull private final MobileUIDistributionConfigRepository configRepository;
	@NonNull private final DistributionJobLoaderSupportingServices loadingSupportServices;
	@NonNull private final DistributionLauncherCaptionProvider captionProvider;
	@NonNull private final DistributionWarehouseService warehouseService;
	@NonNull private final DDOrderService ddOrderService;
	@NonNull private final DistributionProductService productService;
	@NonNull private final DistributionSourceDocService sourceDocService;

	private static final String ACTION_DROP_ALL = "dropAll";
	private static final String ACTION_PRINT_IN_TRANSIT_REPORT = "printMaterialInTransitReport";

	public WorkflowLaunchersList provideLaunchers(@NonNull WorkflowLaunchersQuery query)
	{
		query.assertNoFilterByQRCode();

		final MobileUIDistributionConfig config = getConfig();

		final List<DDOrderReference> jobReferences = getJobReferences(
				newDDOrderReferenceQuery(query.getUserId())
						.suggestedLimit(query.getLimit().orElse(config.getMaxLaunchers()))
						.activeFacetIds(DistributionFacetIdsCollection.ofWorkflowLaunchersFacetIds(query.getFacetIds()))
						.excludeAlreadyStarted(query.isExcludeAlreadyStarted())
						.build()
		);

		return WorkflowLaunchersList.builder()
				.timestamp(SystemTime.asInstant())
				.launchers(jobReferences.stream().map(this::toWorkflowLauncher).collect(ImmutableList.toImmutableList()))
				.orderByFields(config.getSorting().toWorkflowLauncherCaptionOrderBys())
				.actions(query.isComputeActions()
						? computeActions(jobReferences, query.getUserId())
						: null)
				.build();
	}

	private MobileUIDistributionConfig getConfig()
	{
		return configRepository.getConfig();
	}

	private List<DDOrderReference> getJobReferences(@NonNull final DDOrderReferenceQuery query)
	{
		final DDOrderReferenceCollector collector = DDOrderReferenceCollector.builder()
				.loadingSupportServices(loadingSupportServices)
				.build();

		collect(query, collector);

		return collector.getCollectedItems();
	}

	private DDOrderReferenceQueryBuilder newDDOrderReferenceQuery(@NonNull final UserId userId)
	{
		final MobileUIDistributionConfig config = getConfig();
		final Workplace workplace = warehouseService.getWorkplaceByUserId(userId).orElse(null);

		return DDOrderReferenceQuery.builder()
				.sorting(config.getSorting())
				.responsibleId(userId)
				.warehouseToId(workplace != null ? workplace.getWarehouseId() : null)
				.locatorToId(workplace != null ? workplace.getPickFromLocatorId() : null);
	}

	private WorkflowLauncher toWorkflowLauncher(@NonNull final DDOrderReference ddOrderReference)
	{
		return WorkflowLauncher.builder()
				.applicationId(DistributionMobileApplication.APPLICATION_ID)
				.caption(computeCaption(ddOrderReference))
				.startedWFProcessId(ddOrderReference.isJobStarted()
						? WFProcessId.ofIdPart(DistributionMobileApplication.APPLICATION_ID, ddOrderReference.getDdOrderId())
						: null)
				.wfParameters(DistributionWFProcessStartParams.builder()
						.ddOrderId(ddOrderReference.getDdOrderId())
						.isInTransit(ddOrderReference.isInTransit())
						.build()
						.toParams())
				.testId(ddOrderReference.getTestId())
				.build();
	}

	@NonNull
	private WorkflowLauncherCaption computeCaption(@NonNull final DDOrderReference ddOrderReference)
	{
		return captionProvider.compute(ddOrderReference);
	}

	public WorkflowLaunchersFacetGroupList getFacets(@NonNull final WorkflowLaunchersFacetQuery query)
	{
		return getFacets(
				newDDOrderReferenceQuery(query.getUserId())
						.activeFacetIds(DistributionFacetIdsCollection.ofWorkflowLaunchersFacetIds(query.getActiveFacetIds()))
						.build()
		)
				.toWorkflowLaunchersFacetGroupList(query.getActiveFacetIds());
	}

	private DistributionFacetsCollection getFacets(@NonNull final DDOrderReferenceQuery query)
	{
		final DistributionFacetsCollector collector = DistributionFacetsCollector.builder()
				.ddOrderService(ddOrderService)
				.productService(productService)
				.warehouseService(warehouseService)
				.sourceDocService(sourceDocService)
				.build();

		collect(query, collector);

		return collector.toFacetsCollection();
	}

	private <T> void collect(
			@NonNull final DDOrderReferenceQuery query,
			@NonNull final DistributionOrderCollector<T> collector)
	{
		//
		// Already started jobs
		if (!query.isExcludeAlreadyStarted())
		{
			ddOrderService.streamDDOrders(DistributionJobQueries.ddOrdersAssignedToUser(query))
					.forEach(collector::collect);
		}

		//
		// New possible jobs
		@NonNull final QueryLimit suggestedLimit = query.getSuggestedLimit();
		if (suggestedLimit.isNoLimit() || !suggestedLimit.isLimitHitOrExceeded(collector.getCollectedItems()))
		{
			ddOrderService.streamDDOrders(DistributionJobQueries.toActiveNotAssignedDDOrderQuery(query))
					.limit(suggestedLimit.minusSizeOf(collector.getCollectedItems()).toIntOr(Integer.MAX_VALUE))
					.forEach(collector::collect);
		}
	}

	@NonNull
	private ImmutableSet<String> computeActions(
			@NonNull final List<DDOrderReference> jobReferences,
			@NonNull final UserId userId)
	{
		final ImmutableSet.Builder<String> actions = ImmutableSet.builder();

		if (hasInTransitSchedules(jobReferences, userId))
		{
			actions.add(ACTION_DROP_ALL);

			if (warehouseService.getTrolleyByUserId(userId).isPresent())
			{
				actions.add(ACTION_PRINT_IN_TRANSIT_REPORT);
			}
		}

		return actions.build();
	}

	private boolean hasInTransitSchedules(
			@NonNull final List<DDOrderReference> jobReferences,
			@NonNull final UserId userId)
	{
		final LocatorId inTransitLocatorId = warehouseService.getTrolleyByUserId(userId)
				.map(LocatorQRCode::getLocatorId)
				.orElse(null);

		if (inTransitLocatorId != null)
		{
			return loadingSupportServices.hasInTransitSchedules(inTransitLocatorId);
		}
		else
		{
			return jobReferences.stream().anyMatch(DDOrderReference::isInTransit);
		}
	}
}
