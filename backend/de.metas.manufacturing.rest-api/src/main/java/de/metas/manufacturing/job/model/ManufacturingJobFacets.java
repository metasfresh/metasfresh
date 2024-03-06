package de.metas.manufacturing.job.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.i18n.ITranslatableString;
import de.metas.resource.ResourceTypeId;
import de.metas.util.Check;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacet;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetGroup;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetGroupId;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetGroupList;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

@UtilityClass
public class ManufacturingJobFacets
{
	@Value
	public static class FacetId
	{
		@NonNull ManufacturingJobFacetGroup group;
		@Nullable ResourceTypeId resourceTypeId;

		@Builder
		private FacetId(@NonNull final ManufacturingJobFacetGroup group, @Nullable final ResourceTypeId resourceTypeId)
		{
			this.group = group;
			if (group == ManufacturingJobFacetGroup.RESOURCE_TYPE)
			{
				this.resourceTypeId = Check.assumeNotNull(resourceTypeId, "resourceTypeId shall not be null");
			}
			else
			{
				throw new AdempiereException("Unknown group: " + group);
			}
		}

		public static FacetId ofResourceTypeId(@NonNull final ResourceTypeId resourceTypeId)
		{
			return builder().group(ManufacturingJobFacetGroup.RESOURCE_TYPE).resourceTypeId(resourceTypeId).build();
		}

		public static FacetId ofWorkflowLaunchersFacetId(@NonNull WorkflowLaunchersFacetId facetId)
		{
			final ManufacturingJobFacetGroup group = ManufacturingJobFacetGroup.ofWorkflowLaunchersFacetGroupId(facetId.getGroupId());
			if (group == ManufacturingJobFacetGroup.RESOURCE_TYPE)
			{
				return FacetId.ofResourceTypeId(facetId.getAsId(ResourceTypeId.class));
			}
			else
			{
				throw new AdempiereException("Unknown group: " + group);
			}
		}

		public WorkflowLaunchersFacetId toWorkflowLaunchersFacetId()
		{
			if (group == ManufacturingJobFacetGroup.RESOURCE_TYPE)
			{
				final ResourceTypeId resourceTypeId = Check.assumeNotNull(this.resourceTypeId, "resourceTypeId shall not be null");
				return WorkflowLaunchersFacetId.ofId(group.toWorkflowLaunchersFacetGroupId(), resourceTypeId);
			}
			else
			{
				throw new AdempiereException("Unknown group: " + group);
			}
		}

		public ResourceTypeId getResourceTypeIdNotNull()
		{
			return Check.assumeNotNull(resourceTypeId, "resourceTypeId not null for {}", this);
		}
	}

	public static class FacetIdsCollection implements Iterable<FacetId>
	{
		public static final FacetIdsCollection EMPTY = new FacetIdsCollection(ImmutableSet.of());
		private final ImmutableSet<FacetId> set;

		private FacetIdsCollection(@NonNull final ImmutableSet<FacetId> set)
		{
			this.set = set;
		}

		public static FacetIdsCollection ofWorkflowLaunchersFacetIds(@Nullable Collection<WorkflowLaunchersFacetId> facetIds)
		{
			if (facetIds == null || facetIds.isEmpty())
			{
				return EMPTY;
			}

			return ofCollection(facetIds.stream().map(FacetId::ofWorkflowLaunchersFacetId).collect(ImmutableSet.toImmutableSet()));
		}

		public static FacetIdsCollection ofCollection(final Collection<FacetId> collection)
		{
			return !collection.isEmpty() ? new FacetIdsCollection(ImmutableSet.copyOf(collection)) : EMPTY;
		}

		@Override
		@NonNull
		public Iterator<FacetId> iterator() {return set.iterator();}

		public boolean isEmpty() {return set.isEmpty();}

		public ImmutableSet<ResourceTypeId> getResourceTypeIds()
		{
			final ImmutableSet.Builder<ResourceTypeId> result = ImmutableSet.builder();
			for (final ManufacturingJobFacets.FacetId facetId : set)
			{
				if (ManufacturingJobFacetGroup.RESOURCE_TYPE.equals(facetId.getGroup()))
				{
					result.add(facetId.getResourceTypeIdNotNull());
				}
			}
			return result.build();
		}
	}

	@Value(staticConstructor = "of")
	public static class Facet
	{
		@NonNull FacetId facetId;
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

	@EqualsAndHashCode
	@ToString
	public static class FacetsCollection implements Iterable<Facet>
	{
		private static final FacetsCollection EMPTY = new FacetsCollection(ImmutableSet.of());
		private final ImmutableSet<Facet> set;

		private FacetsCollection(@NonNull final ImmutableSet<Facet> set)
		{
			this.set = set;
		}

		public static FacetsCollection ofCollection(final Collection<Facet> collection)
		{
			return !collection.isEmpty() ? new FacetsCollection(ImmutableSet.copyOf(collection)) : EMPTY;
		}

		@Override
		@NonNull
		public Iterator<Facet> iterator() {return set.iterator();}

		public boolean isEmpty() {return set.isEmpty();}

		public WorkflowLaunchersFacetGroupList toWorkflowLaunchersFacetGroupList(@Nullable ImmutableSet<WorkflowLaunchersFacetId> activeFacetIds)
		{
			if (isEmpty())
			{
				return WorkflowLaunchersFacetGroupList.EMPTY;
			}

			final HashMap<WorkflowLaunchersFacetGroupId, WorkflowLaunchersFacetGroup.WorkflowLaunchersFacetGroupBuilder> groupBuilders = new HashMap<>();
			for (final ManufacturingJobFacets.Facet manufacturingFacet : set)
			{
				final WorkflowLaunchersFacet facet = manufacturingFacet.toWorkflowLaunchersFacet(activeFacetIds);
				groupBuilders.computeIfAbsent(facet.getGroupId(), k -> manufacturingFacet.newWorkflowLaunchersFacetGroupBuilder())
						.facet(facet);
			}

			final ImmutableList<WorkflowLaunchersFacetGroup> groups = groupBuilders.values()
					.stream()
					.map(WorkflowLaunchersFacetGroup.WorkflowLaunchersFacetGroupBuilder::build)
					.collect(ImmutableList.toImmutableList());

			return WorkflowLaunchersFacetGroupList.ofList(groups);
		}

	}

}
