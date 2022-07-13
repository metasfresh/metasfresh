package de.metas.project.workorder;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.project.ProjectId;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Stream;

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

	public static Collector<WOProjectResources, ?, WOProjectResourcesCollection> collect()
	{
		return GuavaCollectors.collectUsingMapAccumulator(
				WOProjectResources::getProjectId,
				WOProjectResourcesCollection::ofMap);
	}

	public WOProjectResources get(@NonNull final ProjectId projectId)
	{
		return map.get(projectId);
	}

	public ImmutableSet<WOProjectResourceId> getProjectResourceIds()
	{
		return map.values().stream().flatMap(WOProjectResources::streamIds).collect(ImmutableSet.toImmutableSet());
	}

	public Stream<WOProjectResource> streamProjectResources()
	{
		return map.values().stream().flatMap(WOProjectResources::stream);
	}

}
