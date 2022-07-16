package de.metas.project.budget;

import com.google.common.collect.ImmutableSet;
import de.metas.project.ProjectId;
import lombok.NonNull;
import lombok.Value;

import java.util.Set;

@Value(staticConstructor = "of")
public class BudgetProjectAndResourceId
{
	@NonNull ProjectId projectId;
	@NonNull BudgetProjectResourceId projectResourceId;

	public static ImmutableSet<BudgetProjectResourceId> unbox(final Set<BudgetProjectAndResourceId> projectAndResourceIds)
	{
		if (projectAndResourceIds.isEmpty())
		{
			return ImmutableSet.of();
		}
		else
		{
			return projectAndResourceIds.stream().map(BudgetProjectAndResourceId::getProjectResourceId).collect(ImmutableSet.toImmutableSet());
		}
	}
}
