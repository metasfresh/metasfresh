package de.metas.project.workorder.resource;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.project.ProjectId;
import de.metas.project.workorder.step.WOProjectStepId;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public class WOProjectResourcesCollection
{
	public static final WOProjectResourcesCollection EMPTY = new WOProjectResourcesCollection(ImmutableMap.of());

	private final ImmutableMap<ProjectId, WOProjectResources> map;

	private WOProjectResourcesCollection(
			@NonNull final ImmutableMap<ProjectId, WOProjectResources> map)
	{
		this.map = map;
	}

	public static WOProjectResourcesCollection ofMap(@NonNull final Map<ProjectId, WOProjectResources> map)
	{
		return !map.isEmpty() ? new WOProjectResourcesCollection(ImmutableMap.copyOf(map)) : EMPTY;
	}

	public static WOProjectResourcesCollection ofCollection(@NonNull final Collection<WOProjectResources> list)
	{
		return !list.isEmpty() ? new WOProjectResourcesCollection(Maps.uniqueIndex(list, WOProjectResources::getProjectId)) : EMPTY;
	}

	public static Collector<WOProjectResources, ?, WOProjectResourcesCollection> collect()
	{
		return GuavaCollectors.collectUsingMapAccumulator(
				WOProjectResources::getProjectId,
				WOProjectResourcesCollection::ofMap);
	}

	public int size()
	{
		return map.values()
				.stream()
				.mapToInt(WOProjectResources::size)
				.sum();
	}

	public WOProjectResources getByProjectId(@NonNull final ProjectId projectId)
	{
		final WOProjectResources projectResources = map.get(projectId);
		if (projectResources == null)
		{
			throw new AdempiereException("No project resources found for " + projectId + " in " + this);
		}
		return projectResources;
	}

	public Collection<WOProjectResource> getByStepId(final WOProjectStepId stepId)
	{
		return getByProjectId(stepId.getProjectId()).streamByStepId(stepId).collect(ImmutableList.toImmutableList());
	}

	public Stream<WOProjectResource> streamByStepId(final WOProjectStepId stepId)
	{
		return getByProjectId(stepId.getProjectId()).streamByStepId(stepId);
	}

	public WOProjectResource getById(final WOProjectResourceId projectResourceId)
	{
		return getByProjectId(projectResourceId.getProjectId()).getById(projectResourceId);
	}

	public Collection<WOProjectResources> toCollection() {return map.values();}
}
