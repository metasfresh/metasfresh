package de.metas.calendar.plan_optimizer.solver.weekly_capacities;

import com.google.common.collect.ImmutableMap;
import de.metas.calendar.plan_optimizer.domain.Step;
import de.metas.resource.HumanResourceTestGroupId;
import lombok.NonNull;
import lombok.Value;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@Value(staticConstructor = "of")
public class StepRequiredCapacity
{
	public static StepRequiredCapacity ZERO = new StepRequiredCapacity(ImmutableMap.of());

	@NonNull ImmutableMap<ResourceGroupYearWeek, Duration> map;

	public static StepRequiredCapacity ofStep(final Step step)
	{
		final HumanResourceTestGroupId humanResourceTestGroupId = step.getResource().getHumanResourceTestGroupId();
		if (humanResourceTestGroupId == null)
		{
			// TODO: shall we return Optional or something which says "infinite capacity"
			return null;
		}

		// TODO: make sure the duration is in one week
		// TODO compute and return the required capacity
		return null;
	}

	private StepRequiredCapacity(@NonNull final Map<ResourceGroupYearWeek, Duration> map)
	{
		this.map = ImmutableMap.copyOf(map);
	}

	private StepRequiredCapacity(@NonNull final ResourceGroupYearWeek resourceGroupYearWeek, @NonNull final Duration duration)
	{
		this.map = ImmutableMap.of(resourceGroupYearWeek, duration);
	}

	public StepRequiredCapacity add(final StepRequiredCapacity other)
	{
		final HashMap<ResourceGroupYearWeek, Duration> map = new HashMap<>(this.map);
		other.map.forEach((resourceGroupYearWeek, duration) -> map.merge(resourceGroupYearWeek, duration, Duration::plus));

		return new StepRequiredCapacity(map);
	}

	public StepRequiredCapacity subtract(final StepRequiredCapacity other)
	{
		final HashMap<ResourceGroupYearWeek, Duration> map = new HashMap<>(this.map);
		other.map.forEach((resourceGroupYearWeek, duration) -> map.merge(resourceGroupYearWeek, duration, Duration::minus));

		return new StepRequiredCapacity(map);
	}

	public void forEach(final BiConsumer<ResourceGroupYearWeek, Duration> consumer)
	{
		this.map.forEach(consumer);
	}

}
