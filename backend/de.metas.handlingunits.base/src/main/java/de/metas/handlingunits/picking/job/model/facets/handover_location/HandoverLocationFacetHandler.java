package de.metas.handlingunits.picking.job.model.facets.handover_location;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.location.RenderedAddressProvider;
import de.metas.handlingunits.picking.job.model.PickingJobQuery;
import de.metas.handlingunits.picking.job.model.facets.CollectingParameters;
import de.metas.handlingunits.picking.job.model.facets.PickingJobFacet;
import de.metas.handlingunits.picking.job.model.facets.PickingJobFacetGroup;
import de.metas.handlingunits.picking.job.model.facets.PickingJobFacetHandler;
import de.metas.handlingunits.picking.job.model.facets.PickingJobFacets;
import de.metas.i18n.TranslatableStrings;
import de.metas.picking.api.Packageable;
import de.metas.rest_workflows.facets.WorkflowLaunchersFacet;
import de.metas.rest_workflows.facets.WorkflowLaunchersFacetGroup;
import de.metas.rest_workflows.facets.WorkflowLaunchersFacetId;
import de.metas.util.JSONObjectMapper;
import lombok.NonNull;

import java.util.List;

public class HandoverLocationFacetHandler implements PickingJobFacetHandler
{
	@Override
	public PickingJobFacetGroup getHandledGroup() {return PickingJobFacetGroup.HANDOVER_LOCATION;}

	@Override
	public boolean isMatching(@NonNull final PickingJobFacet facet, final PickingJobQuery.@NonNull Facets queryFacets)
	{
		return queryFacets.getHandoverLocationIds().contains(facet.asType(HandoverLocationFacet.class).getBPartnerLocationId());
	}

	@Override
	public void collectHandled(final PickingJobQuery.Facets.FacetsBuilder collector, final PickingJobQuery.Facets from)
	{
		collector.handoverLocationIds(from.getHandoverLocationIds());
	}

	@Override
	public void collectFromFacetId(final PickingJobQuery.Facets.@NonNull FacetsBuilder collector, @NonNull final WorkflowLaunchersFacetId facetId)
	{
		collector.handoverLocationId(facetId.deserializeTo(BPartnerLocationId.class));
	}

	@Override
	public WorkflowLaunchersFacetGroup toWorkflowLaunchersFacetGroup(@NonNull final PickingJobFacets facets)
	{
		return WorkflowLaunchersFacetGroup.builder()
				.id(PickingJobFacetGroup.HANDOVER_LOCATION.getWorkflowGroupFacetId())
				.caption(TranslatableStrings.adRefList(PickingJobFacetGroup.PICKING_JOB_FILTER_OPTION_REFERENCE_ID, PickingJobFacetGroup.HANDOVER_LOCATION))
				.facets(facets.toList(HandoverLocationFacet.class, HandoverLocationFacetHandler::toWorkflowLaunchersFacet))
				.build();
	}

	@NonNull
	private static WorkflowLaunchersFacet toWorkflowLaunchersFacet(@NonNull final HandoverLocationFacet facet)
	{
		return WorkflowLaunchersFacet.builder()
				.facetId(WorkflowLaunchersFacetId.ofString(
						PickingJobFacetGroup.HANDOVER_LOCATION.getWorkflowGroupFacetId(),
						JSONObjectMapper.forClass(BPartnerLocationId.class).writeValueAsString(facet.getBPartnerLocationId())))
				.caption(TranslatableStrings.anyLanguage(facet.getRenderedAddress()))
				.isActive(facet.isActive())
				.build();
	}

	@Override
	public List<HandoverLocationFacet> extractFacets(@NonNull final Packageable packageable, @NonNull final CollectingParameters parameters)
	{
		final RenderedAddressProvider renderedAddressProvider = parameters.getAddressProvider();
		final String renderedAddress = renderedAddressProvider.getAddress(packageable.getHandoverLocationId());
		return ImmutableList.of(HandoverLocationFacet.of(false, packageable.getHandoverLocationId(), renderedAddress));
	}
}
