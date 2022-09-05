package de.metas.project.workorder.resource;

import com.google.common.collect.ImmutableMap;
import de.metas.project.ProjectId;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

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

	public static Collector<WOProjectResources, ?, WOProjectResourcesCollection> collect()
	{
		return GuavaCollectors.collectUsingMapAccumulator(
				WOProjectResources::getProjectId,
				WOProjectResourcesCollection::ofMap);
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

	public Stream<WOProjectResource> streamProjectResources()
	{
		return map.values().stream().flatMap(WOProjectResources::stream);
	}
}
