package de.metas.distribution.workflows_api.facets;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacet;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetGroup;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetGroup.WorkflowLaunchersFacetGroupBuilder;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetGroupId;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetGroupList;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetId;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

@EqualsAndHashCode
@ToString
public class DistributionFacetsCollection implements Iterable<DistributionFacet>
{
	private static final DistributionFacetsCollection EMPTY = new DistributionFacetsCollection(ImmutableSet.of());
	private final ImmutableSet<DistributionFacet> set;

	private DistributionFacetsCollection(@NonNull final ImmutableSet<DistributionFacet> set)
	{
		this.set = set;
	}

	public static DistributionFacetsCollection ofCollection(final Collection<DistributionFacet> collection)
	{
		return !collection.isEmpty() ? new DistributionFacetsCollection(ImmutableSet.copyOf(collection)) : EMPTY;
	}

	@Override
	@NonNull
	public Iterator<DistributionFacet> iterator() {return set.iterator();}

	public boolean isEmpty() {return set.isEmpty();}

	public WorkflowLaunchersFacetGroupList toWorkflowLaunchersFacetGroupList(@Nullable ImmutableSet<WorkflowLaunchersFacetId> activeFacetIds)
	{
		if (isEmpty())
		{
			return WorkflowLaunchersFacetGroupList.EMPTY;
		}

		final HashMap<WorkflowLaunchersFacetGroupId, WorkflowLaunchersFacetGroupBuilder> groupBuilders = new HashMap<>();
		for (final DistributionFacet distributionFacet : set)
		{
			final WorkflowLaunchersFacet facet = distributionFacet.toWorkflowLaunchersFacet(activeFacetIds);
			groupBuilders.computeIfAbsent(facet.getGroupId(), k -> distributionFacet.newWorkflowLaunchersFacetGroupBuilder())
					.facet(facet);
		}

		final ImmutableList<WorkflowLaunchersFacetGroup> groups = groupBuilders.values()
				.stream()
				.map(WorkflowLaunchersFacetGroupBuilder::build)
				.sorted(orderByGroupSeqNo())
				.collect(ImmutableList.toImmutableList());

		return WorkflowLaunchersFacetGroupList.ofList(groups);
	}

	private static Comparator<? super WorkflowLaunchersFacetGroup> orderByGroupSeqNo()
	{
		return Comparator.comparingInt(DistributionFacetsCollection::getGroupSeqNo);
	}

	private static int getGroupSeqNo(final WorkflowLaunchersFacetGroup group)
	{
		return DistributionFacetGroupType.ofWorkflowLaunchersFacetGroupId(group.getId()).getSeqNo();
	}
}
