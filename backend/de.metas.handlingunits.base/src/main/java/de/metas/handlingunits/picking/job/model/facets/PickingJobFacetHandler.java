package de.metas.handlingunits.picking.job.model.facets;

import de.metas.handlingunits.picking.job.model.PickingJobQuery;
import de.metas.picking.api.Packageable;
import de.metas.rest_workflows.facets.WorkflowLaunchersFacetGroup;
import de.metas.rest_workflows.facets.WorkflowLaunchersFacetId;
import lombok.NonNull;

import java.util.List;

public interface PickingJobFacetHandler
{
	PickingJobFacetGroup getHandledGroup();

	boolean isMatching(@NonNull PickingJobFacet facet, @NonNull PickingJobQuery.Facets queryFacets);

	void collectHandled(PickingJobQuery.Facets.FacetsBuilder collector, PickingJobQuery.Facets from);

	void collectFromFacetId(@NonNull PickingJobQuery.Facets.FacetsBuilder collector, @NonNull WorkflowLaunchersFacetId facetId);

	WorkflowLaunchersFacetGroup toWorkflowLaunchersFacetGroup(@NonNull PickingJobFacets facets);

	List<? extends PickingJobFacet> extractFacets(@NonNull Packageable packageable, @NonNull final CollectingParameters parameters);
}
