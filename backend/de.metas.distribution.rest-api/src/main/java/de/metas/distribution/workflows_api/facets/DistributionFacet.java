package de.metas.distribution.workflows_api.facets;

import com.google.common.collect.ImmutableSet;
import de.metas.i18n.ITranslatableString;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacet;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetGroup;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetId;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value(staticConstructor = "of")
public class DistributionFacet
{
	@NonNull DistributionFacetId facetId;
	@NonNull ITranslatableString caption;

	public WorkflowLaunchersFacetGroup.WorkflowLaunchersFacetGroupBuilder newWorkflowLaunchersFacetGroupBuilder()
	{
		return facetId.getGroup().newWorkflowLaunchersFacetGroupBuilder();
	}

	public WorkflowLaunchersFacet toWorkflowLaunchersFacet(@Nullable ImmutableSet<WorkflowLaunchersFacetId> activeFacetIds)
	{
		final WorkflowLaunchersFacetId facetId = this.facetId.toWorkflowLaunchersFacetId();

		return WorkflowLaunchersFacet.builder()
				.facetId(facetId)
				.caption(caption)
				.isActive(activeFacetIds != null && activeFacetIds.contains(facetId))
				.build();
	}

}
