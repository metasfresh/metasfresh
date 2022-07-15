package de.metas.project.budget;

import de.metas.project.ProjectId;
import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class BudgetProjectAndResourceId
{
	@NonNull ProjectId projectId;
	@NonNull BudgetProjectResourceId projectResourceId;
}
