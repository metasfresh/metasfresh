package de.metas.distribution.workflows_api;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.distribution.workflows_api.facets.DistributionFacetIdsCollection;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLauncher;
import de.metas.workflow.rest_api.model.WorkflowLauncherCaption;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import de.metas.workflow.rest_api.model.WorkflowLaunchersQuery;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetGroupList;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetQuery;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.exceptions.AdempiereException;

class DistributionWorkflowLaunchersProvider
{
	private final DistributionRestService distributionRestService;
	private final DistributionLauncherCaptionProvider captionProvider;

	public DistributionWorkflowLaunchersProvider(
			final @NonNull DistributionRestService distributionRestService)
	{
		this.distributionRestService = distributionRestService;
		this.captionProvider = new DistributionLauncherCaptionProvider();
	}

	public WorkflowLaunchersList provideLaunchers(@NonNull WorkflowLaunchersQuery query)
	{
		if (query.getFilterByQRCode() != null)
		{
			throw new AdempiereException("Invalid QR Code: " + query.getFilterByQRCode());
		}

		final ImmutableList<WorkflowLauncher> launchers = distributionRestService.streamJobReferencesForUser(
						DDOrderReferenceQuery.builder()
								.responsibleId(query.getUserId())
								.suggestedLimit(query.getLimit().orElse(QueryLimit.NO_LIMIT))
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
						DDOrderReferenceQuery.builder()
								.responsibleId(query.getUserId())
								.activeFacetIds(DistributionFacetIdsCollection.ofWorkflowLaunchersFacetIds(query.getActiveFacetIds()))
								.build()
				)
				.toWorkflowLaunchersFacetGroupList(query.getActiveFacetIds());
	}

}
