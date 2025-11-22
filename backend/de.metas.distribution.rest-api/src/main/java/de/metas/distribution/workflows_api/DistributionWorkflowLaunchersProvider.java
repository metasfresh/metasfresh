package de.metas.distribution.workflows_api;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.distribution.config.MobileUIDistributionConfig;
import de.metas.distribution.service.external.DistributionWarehouseService;
import de.metas.distribution.workflows_api.DDOrderReferenceQuery.DDOrderReferenceQueryBuilder;
import de.metas.distribution.workflows_api.facets.DistributionFacetIdsCollection;
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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DistributionWorkflowLaunchersProvider
{
	@NonNull private final DistributionRestService distributionRestService;
	@NonNull private final DistributionLauncherCaptionProvider captionProvider;
	@NonNull private final DistributionWarehouseService warehouseService;

	public WorkflowLaunchersList provideLaunchers(@NonNull WorkflowLaunchersQuery query)
	{
		query.assertNoFilterByQRCode();

		final MobileUIDistributionConfig config = distributionRestService.getConfig();

		final ImmutableList<WorkflowLauncher> launchers = distributionRestService.streamJobReferencesForUser(
						newDDOrderReferenceQuery(query.getUserId())
								.suggestedLimit(query.getLimit().orElse(config.getMaxLaunchers()))
								.activeFacetIds(DistributionFacetIdsCollection.ofWorkflowLaunchersFacetIds(query.getFacetIds()))
								.build()
				)
				.map(this::toWorkflowLauncher)
				.collect(ImmutableList.toImmutableList());

		return WorkflowLaunchersList.builder()
				.launchers(launchers)
				.timestamp(SystemTime.asInstant())
				.build();
	}

	private DDOrderReferenceQueryBuilder newDDOrderReferenceQuery(@NonNull final UserId userId)
	{
		final MobileUIDistributionConfig config = distributionRestService.getConfig();
		final Workplace workplace = warehouseService.getWorkplaceByUserId(userId).orElse(null);

		return DDOrderReferenceQuery.builder()
				.sorting(config.getSorting())
				.responsibleId(userId)
				.warehouseToId(workplace != null ? workplace.getWarehouseId() : null)
				.locatorToId(workplace != null ? workplace.getPickFromLocatorId() : null);
	}

	private WorkflowLauncher toWorkflowLauncher(@NonNull final DDOrderReference ddOrderReference)
	{
		if (ddOrderReference.isJobStarted())
		{
			return WorkflowLauncher.builder()
					.applicationId(DistributionMobileApplication.APPLICATION_ID)
					.caption(computeCaption(ddOrderReference))
					.startedWFProcessId(WFProcessId.ofIdPart(DistributionMobileApplication.APPLICATION_ID, ddOrderReference.getDdOrderId()))
					.testId(ddOrderReference.getTestId())
					.build();
		}
		else
		{
			return WorkflowLauncher.builder()
					.applicationId(DistributionMobileApplication.APPLICATION_ID)
					.caption(computeCaption(ddOrderReference))
					.wfParameters(DistributionWFProcessStartParams.builder()
							.ddOrderId(ddOrderReference.getDdOrderId())
							.build()
							.toParams())
					.testId(ddOrderReference.getTestId())
					.build();
		}
	}

	@NonNull
	private WorkflowLauncherCaption computeCaption(@NonNull final DDOrderReference ddOrderReference)
	{
		return captionProvider.compute(ddOrderReference);
	}

	public WorkflowLaunchersFacetGroupList getFacets(@NonNull final WorkflowLaunchersFacetQuery query)
	{
		return distributionRestService.getFacets(
						newDDOrderReferenceQuery(query.getUserId())
								.activeFacetIds(DistributionFacetIdsCollection.ofWorkflowLaunchersFacetIds(query.getActiveFacetIds()))
								.build()
				)
				.toWorkflowLaunchersFacetGroupList(query.getActiveFacetIds());
	}

}
