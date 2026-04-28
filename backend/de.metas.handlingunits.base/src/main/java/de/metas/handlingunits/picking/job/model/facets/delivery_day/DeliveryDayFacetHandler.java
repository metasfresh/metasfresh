package de.metas.handlingunits.picking.job.model.facets.delivery_day;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.picking.job.model.PickingJobQuery;
import de.metas.handlingunits.picking.job.model.facets.CollectingParameters;
import de.metas.handlingunits.picking.job.model.facets.PickingJobFacet;
import de.metas.handlingunits.picking.job.model.facets.PickingJobFacetGroup;
import de.metas.handlingunits.picking.job.model.facets.PickingJobFacetHandler;
import de.metas.handlingunits.picking.job.model.facets.PickingJobFacets;
import de.metas.i18n.TranslatableStrings;
import de.metas.organization.InstantAndOrgId;
import de.metas.picking.api.Packageable;
import de.metas.rest_workflows.facets.WorkflowLaunchersFacet;
import de.metas.rest_workflows.facets.WorkflowLaunchersFacetGroup;
import de.metas.rest_workflows.facets.WorkflowLaunchersFacetId;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class DeliveryDayFacetHandler implements PickingJobFacetHandler
{
	@Override
	public PickingJobFacetGroup getHandledGroup() {return PickingJobFacetGroup.DELIVERY_DATE;}

	@Override
	public boolean isMatching(@NonNull final PickingJobFacet facet, final PickingJobQuery.@NonNull Facets queryFacets)
	{
		return queryFacets.getDeliveryDays().contains(facet.asType(DeliveryDayFacet.class).getDeliveryDate());
	}

	@Override
	public void collectHandled(final PickingJobQuery.Facets.FacetsBuilder collector, final PickingJobQuery.Facets from)
	{
		collector.deliveryDays(from.getDeliveryDays());
	}

	@Override
	public void collectFromFacetId(@NonNull final PickingJobQuery.Facets.FacetsBuilder collector, @NonNull WorkflowLaunchersFacetId facetId)
	{
		collector.deliveryDay(facetId.getAsLocalDate());
	}

	@Override
	public WorkflowLaunchersFacetGroup toWorkflowLaunchersFacetGroup(@NonNull final PickingJobFacets facets)
	{
		return WorkflowLaunchersFacetGroup.builder()
				.id(PickingJobFacetGroup.DELIVERY_DATE.getWorkflowGroupFacetId())
				.caption(TranslatableStrings.adRefList(PickingJobFacetGroup.PICKING_JOB_FILTER_OPTION_REFERENCE_ID, PickingJobFacetGroup.DELIVERY_DATE))
				.facets(facets.toList(DeliveryDayFacet.class, DeliveryDayFacetHandler::toWorkflowLaunchersFacet))
				.build();
	}

	@NonNull
	private static WorkflowLaunchersFacet toWorkflowLaunchersFacet(@NonNull final DeliveryDayFacet facet)
	{
		return WorkflowLaunchersFacet.builder()
				.facetId(WorkflowLaunchersFacetId.ofLocalDate(PickingJobFacetGroup.DELIVERY_DATE.getWorkflowGroupFacetId(), facet.getDeliveryDate()))
				.caption(TranslatableStrings.date(facet.getDeliveryDate()))
				.sortNo(facet.getDeliveryDate().atStartOfDay(facet.getTimeZone()).toInstant().toEpochMilli())
				.isActive(facet.isActive())
				.build();
	}

	@Override
	public List<DeliveryDayFacet> extractFacets(@NonNull final Packageable packageable, @NonNull final CollectingParameters parameters)
	{
		final InstantAndOrgId deliveryDate = packageable.getDeliveryDate();
		//final ZoneId timeZone = orgDAO.getTimeZone(deliveryDate.getOrgId());
		final ZoneId timeZone = SystemTime.zoneId();
		final LocalDate deliveryDay = deliveryDate.toZonedDateTime(timeZone).toLocalDate();
		return ImmutableList.of(DeliveryDayFacet.of(false, deliveryDay, timeZone));
	}
}
