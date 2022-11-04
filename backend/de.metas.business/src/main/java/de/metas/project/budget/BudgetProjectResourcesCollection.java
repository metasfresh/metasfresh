package de.metas.project.budget;

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
public class BudgetProjectResourcesCollection
{
	public static final BudgetProjectResourcesCollection EMPTY = new BudgetProjectResourcesCollection(ImmutableMap.of());

	private final ImmutableMap<ProjectId, BudgetProjectResources> map;

	private BudgetProjectResourcesCollection(
			@NonNull final ImmutableMap<ProjectId, BudgetProjectResources> map)
	{
		this.map = map;
	}

	public static BudgetProjectResourcesCollection ofMap(@NonNull final Map<ProjectId, BudgetProjectResources> map)
	{
		return !map.isEmpty() ? new BudgetProjectResourcesCollection(ImmutableMap.copyOf(map)) : EMPTY;
	}

	public static Collector<BudgetProjectResources, ?, BudgetProjectResourcesCollection> collect()
	{
		return GuavaCollectors.collectUsingMapAccumulator(
				BudgetProjectResources::getProjectId,
				BudgetProjectResourcesCollection::ofMap);
	}

	public BudgetProjectResources getByProjectId(@NonNull final ProjectId projectId)
	{
		final BudgetProjectResources projectResources = map.get(projectId);
		if (projectResources == null)
		{
			throw new AdempiereException("No project resources found for " + projectId + " in " + this);
		}
		return projectResources;
	}

	public Stream<BudgetProjectResource> streamBudgets()
	{
		return map.values().stream().flatMap(BudgetProjectResources::stream);
	}
}
