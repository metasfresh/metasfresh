package de.metas.handlingunits.picking.job.model.facets.preparation_day;

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

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class PreparationDayFacetHandler implements PickingJobFacetHandler
{
	@Override
	public PickingJobFacetGroup getHandledGroup() {return PickingJobFacetGroup.PREPARATION_DATE;}

	@Override
	public boolean isMatching(@NonNull final PickingJobFacet facet, final PickingJobQuery.@NonNull Facets queryFacets)
	{
		return queryFacets.getPreparationDays().contains(facet.asType(PreparationDayFacet.class).getPreparationDate());
	}

	@Override
	public void collectHandled(final PickingJobQuery.Facets.FacetsBuilder collector, final PickingJobQuery.Facets from)
	{
		collector.preparationDays(from.getPreparationDays());
	}

	@Override
	public void collectFromFacetId(@NonNull final PickingJobQuery.Facets.FacetsBuilder collector, @NonNull WorkflowLaunchersFacetId facetId)
	{
		collector.preparationDay(facetId.getAsLocalDate());
	}

	@Override
	public WorkflowLaunchersFacetGroup toWorkflowLaunchersFacetGroup(@NonNull final PickingJobFacets facets)
	{
		return WorkflowLaunchersFacetGroup.builder()
				.id(PickingJobFacetGroup.PREPARATION_DATE.getWorkflowGroupFacetId())
				.caption(TranslatableStrings.adRefList(PickingJobFacetGroup.PICKING_JOB_FILTER_OPTION_REFERENCE_ID, PickingJobFacetGroup.PREPARATION_DATE))
				.facets(facets.toList(PreparationDayFacet.class, PreparationDayFacetHandler::toWorkflowLaunchersFacet))
				.build();
	}

	@NonNull
	private static WorkflowLaunchersFacet toWorkflowLaunchersFacet(@NonNull final PreparationDayFacet facet)
	{
		return WorkflowLaunchersFacet.builder()
				.facetId(WorkflowLaunchersFacetId.ofLocalDate(PickingJobFacetGroup.PREPARATION_DATE.getWorkflowGroupFacetId(), facet.getPreparationDate()))
				.caption(TranslatableStrings.date(facet.getPreparationDate()))
				.sortNo(facet.getPreparationDate().atStartOfDay(facet.getTimeZone()).toInstant().toEpochMilli())
				.isActive(facet.isActive())
				.build();
	}

	@Override
	public List<PreparationDayFacet> extractFacets(@NonNull final Packageable packageable, @NonNull final CollectingParameters parameters)
	{
		// Nullable: PackagingDAO leaves this unset whenever the shipment schedule carries no preparation
		// date, so an order without one simply offers no option instead of taking the filter bar down.
		@Nullable final InstantAndOrgId preparationDate = packageable.getPreparationDate();
		if (preparationDate == null)
		{
			return ImmutableList.of();
		}

		final ZoneId timeZone = SystemTime.zoneId();
		final LocalDate preparationDay = preparationDate.toZonedDateTime(timeZone).toLocalDate();
		return ImmutableList.of(PreparationDayFacet.of(false, preparationDay, timeZone));
	}
}
