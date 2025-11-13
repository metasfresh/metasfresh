package de.metas.handlingunits.picking.job.model.facets;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfile;
import de.metas.handlingunits.picking.job.model.PickingJobQuery;
import de.metas.handlingunits.picking.job.model.facets.customer.CustomerFacetHandler;
import de.metas.handlingunits.picking.job.model.facets.delivery_day.DeliveryDayFacetHandler;
import de.metas.handlingunits.picking.job.model.facets.handover_location.HandoverLocationFacetHandler;
import de.metas.picking.api.Packageable;
import de.metas.rest_workflows.facets.WorkflowLaunchersFacetGroup;
import de.metas.rest_workflows.facets.WorkflowLaunchersFacetGroupList;
import de.metas.rest_workflows.facets.WorkflowLaunchersFacetId;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@UtilityClass
public class PickingJobFacetHandlers
{
	private static final ImmutableMap<PickingJobFacetGroup, PickingJobFacetHandler> handlersByGroup = Maps.uniqueIndex(
			ImmutableList.of(
					new CustomerFacetHandler(),
					new DeliveryDayFacetHandler(),
					new HandoverLocationFacetHandler()),
			PickingJobFacetHandler::getHandledGroup
	);

	private static PickingJobFacetHandler getBy(final PickingJobFacetGroup group)
	{
		final PickingJobFacetHandler handler = handlersByGroup.get(group);
		if (handler == null)
		{
			throw new AdempiereException("No handler found for group: " + group);
		}
		return handler;
	}

	public static ImmutableSet<PickingJobFacet> extractFacets(@NonNull Packageable packageable, @NonNull final CollectingParameters parameters)
	{
		final ImmutableList<PickingJobFacetGroup> groups = parameters.getGroupsInOrder();
		if (groups.isEmpty()) {return ImmutableSet.of();}

		final ImmutableSet.Builder<PickingJobFacet> facets = ImmutableSet.builder();
		groups.forEach(group -> {
			final List<? extends PickingJobFacet> facetsPerGroup = getBy(group).extractFacets(packageable, parameters);
			facets.addAll(facetsPerGroup);
		});

		return facets.build();
	}

	public static PickingJobQuery.Facets retainFacetsOfGroups(
			@NonNull final PickingJobQuery.Facets queryFacets,
			@NonNull final Collection<PickingJobFacetGroup> groups)
	{
		if (groups.isEmpty())
		{
			return PickingJobQuery.Facets.EMPTY;
		}

		final PickingJobQuery.Facets.FacetsBuilder builder = PickingJobQuery.Facets.builder();
		for (final PickingJobFacetGroup group : groups)
		{
			PickingJobFacetHandlers.getBy(group).collectHandled(builder, queryFacets);
		}
		final PickingJobQuery.Facets queryFacetsNew = builder.build();

		if (queryFacetsNew.isEmpty())
		{
			return PickingJobQuery.Facets.EMPTY;
		}
		else if (queryFacetsNew.equals(queryFacets))
		{
			return queryFacets;
		}
		else
		{
			return queryFacetsNew;
		}
	}

	public static boolean isMatching(@NonNull PickingJobFacet facet, @NonNull PickingJobQuery.Facets queryFacets)
	{
		final PickingJobFacetGroup group = facet.getGroup();
		return getBy(group).isMatching(facet, queryFacets);
	}

	public static PickingJobQuery.Facets toPickingJobFacetsQuery(@Nullable final Set<WorkflowLaunchersFacetId> facetIds)
	{
		if (facetIds == null || facetIds.isEmpty())
		{
			return PickingJobQuery.Facets.EMPTY;
		}

		final PickingJobQuery.Facets.FacetsBuilder builder = PickingJobQuery.Facets.builder();
		facetIds.forEach(facetId -> collectFromFacetId(builder, facetId));
		return builder.build();
	}

	private static void collectFromFacetId(@NonNull PickingJobQuery.Facets.FacetsBuilder collector, @NonNull WorkflowLaunchersFacetId facetId)
	{
		final PickingJobFacetGroup group = PickingJobFacetGroup.of(facetId.getGroupId());
		getBy(group).collectFromFacetId(collector, facetId);
	}

	public static WorkflowLaunchersFacetGroupList toWorkflowLaunchersFacetGroupList(
			@NonNull final PickingJobFacets pickingFacets,
			@NonNull final MobileUIPickingUserProfile profile)
	{
		final ArrayList<WorkflowLaunchersFacetGroup> groups = new ArrayList<>();
		for (final PickingJobFacetGroup filterOption : profile.getFilterGroupsInOrder())
		{
			final WorkflowLaunchersFacetGroup group = PickingJobFacetHandlers.getBy(filterOption).toWorkflowLaunchersFacetGroup(pickingFacets);

			// NOTE: if a group has only one facet that's like not filtering at all, so having that facet in the result is pointless.
			if (group != null && !group.isEmpty())
			{
				groups.add(group);
			}
		}

		return WorkflowLaunchersFacetGroupList.ofList(groups);
	}

}
