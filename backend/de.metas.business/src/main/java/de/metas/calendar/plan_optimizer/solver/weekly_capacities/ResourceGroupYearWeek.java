package de.metas.calendar.plan_optimizer.solver.weekly_capacities;

import de.metas.resource.HumanResourceTestGroupId;
import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class ResourceGroupYearWeek
{
	@NonNull HumanResourceTestGroupId groupId;
	@NonNull YearWeek yearWeek;
}
