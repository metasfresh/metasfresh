package de.metas.calendar.plan_optimizer.solver.weekly_capacities;

import com.google.common.base.MoreObjects;
import de.metas.calendar.plan_optimizer.domain.HumanResourceCapacity;
import lombok.NonNull;
import lombok.Value;
import org.threeten.extra.YearWeek;

@Value(staticConstructor = "of")
public class ResourceGroupYearWeek
{
	@NonNull HumanResourceCapacity capacity;
	@NonNull YearWeek yearWeek;

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper("")
				.add("capacity", capacity)
				.add("yearWeek", yearWeek)
				.toString();
	}
}
