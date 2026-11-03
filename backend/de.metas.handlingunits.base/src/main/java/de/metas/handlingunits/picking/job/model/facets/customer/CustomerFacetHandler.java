package de.metas.handlingunits.picking.job.model.facets.customer;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
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
import lombok.NonNull;

import java.util.List;

public class CustomerFacetHandler implements PickingJobFacetHandler
{
	@Override
	public PickingJobFacetGroup getHandledGroup() {return PickingJobFacetGroup.CUSTOMER;}

	@Override
	public boolean isMatching(@NonNull final PickingJobFacet facet, @NonNull final PickingJobQuery.Facets queryFacets)
	{
		return queryFacets.getCustomerIds().contains(facet.asType(CustomerFacet.class).getBpartnerId());
	}

	@Override
	public void collectHandled(final PickingJobQuery.Facets.FacetsBuilder collector, final PickingJobQuery.Facets from)
	{
		collector.customerIds(from.getCustomerIds());
	}

	@Override
	public void collectFromFacetId(@NonNull final PickingJobQuery.Facets.FacetsBuilder collector, @NonNull WorkflowLaunchersFacetId facetId)
	{
		collector.customerId(facetId.getAsId(BPartnerId.class));
	}

	@Override
	public WorkflowLaunchersFacetGroup toWorkflowLaunchersFacetGroup(@NonNull final PickingJobFacets facets)
	{
		return WorkflowLaunchersFacetGroup.builder()
				.id(PickingJobFacetGroup.CUSTOMER.getWorkflowGroupFacetId())
				.caption(TranslatableStrings.adRefList(PickingJobFacetGroup.PICKING_JOB_FILTER_OPTION_REFERENCE_ID, PickingJobFacetGroup.CUSTOMER))
				.facets(facets.toList(CustomerFacet.class, CustomerFacetHandler::toWorkflowLaunchersFacet))
				.build();
	}

	@NonNull
	private static WorkflowLaunchersFacet toWorkflowLaunchersFacet(@NonNull final CustomerFacet facet)
	{
		return WorkflowLaunchersFacet.builder()
				.facetId(WorkflowLaunchersFacetId.ofId(PickingJobFacetGroup.CUSTOMER.getWorkflowGroupFacetId(), facet.getBpartnerId()))
				.caption(TranslatableStrings.anyLanguage(facet.getCustomerBPValue() + " " + facet.getCustomerName()))
				.isActive(facet.isActive())
				.build();
	}

	@Override
	public List<CustomerFacet> extractFacets(@NonNull final Packageable packageable, @NonNull final CollectingParameters parameters)
	{
		return ImmutableList.of(
				CustomerFacet.of(
						false,
						packageable.getCustomerId(),
						packageable.getCustomerBPValue(),
						packageable.getCustomerName())
		);
	}
}
