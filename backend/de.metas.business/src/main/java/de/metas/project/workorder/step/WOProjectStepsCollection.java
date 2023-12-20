package de.metas.project.workorder.step;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.project.ProjectId;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collector;

public class WOProjectStepsCollection
{
	public static final WOProjectStepsCollection EMPTY = new WOProjectStepsCollection(ImmutableMap.of());

	private final ImmutableMap<ProjectId, WOProjectSteps> map;

	private WOProjectStepsCollection(
			@NonNull final ImmutableMap<ProjectId, WOProjectSteps> map)
	{
		this.map = map;
	}

	public static WOProjectStepsCollection ofMap(@NonNull final Map<ProjectId, WOProjectSteps> map)
	{
		return !map.isEmpty() ? new WOProjectStepsCollection(ImmutableMap.copyOf(map)) : EMPTY;
	}

	public static WOProjectStepsCollection ofCollection(@NonNull final Collection<WOProjectSteps> list)
	{
		return !list.isEmpty() ? new WOProjectStepsCollection(Maps.uniqueIndex(list, WOProjectSteps::getProjectId)) : EMPTY;
	}

	public static Collector<WOProjectSteps, ?, WOProjectStepsCollection> collect()
	{
		return GuavaCollectors.collectUsingMapAccumulator(WOProjectSteps::getProjectId, WOProjectStepsCollection::ofMap);
	}

	public WOProjectSteps getByProjectId(@NonNull final ProjectId projectId)
	{
		final WOProjectSteps projectSteps = map.get(projectId);
		if (projectSteps == null)
		{
			throw new AdempiereException("No project steps found for " + projectId + " in " + this);
		}
		return projectSteps;
	}

	public WOProjectStep getById(final WOProjectStepId stepId)
	{
		return getByProjectId(stepId.getProjectId()).getById(stepId);
	}

	public Collection<WOProjectSteps> toCollection() {return map.values();}
}
