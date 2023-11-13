package de.metas.calendar.plan_optimizer.solver.weekly_capacities;

import com.google.common.base.MoreObjects;
import de.metas.resource.HumanResourceTestGroupId;
import de.metas.util.time.YearWeek;
import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class ResourceGroupYearWeek
{
	@NonNull HumanResourceTestGroupId groupId;
	@NonNull YearWeek yearWeek;

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper("")
				.add("groupId", groupId.getRepoId())
				.add("yearWeek", yearWeek)
				.toString();
	}
}
