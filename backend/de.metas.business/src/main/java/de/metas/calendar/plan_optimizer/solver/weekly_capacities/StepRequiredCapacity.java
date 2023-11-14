package de.metas.calendar.plan_optimizer.solver.weekly_capacities;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;
import de.metas.calendar.plan_optimizer.domain.HumanResourceCapacity;
import de.metas.calendar.plan_optimizer.domain.StepAllocation;
import de.metas.calendar.plan_optimizer.domain.StepAllocationId;
import de.metas.calendar.plan_optimizer.domain.StepDef;
import de.metas.resource.ResourceAvailabilityRanges;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.threeten.extra.YearWeek;

import java.time.Duration;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

@EqualsAndHashCode
public final class StepRequiredCapacity
{
	public static final StepRequiredCapacity ZERO = new StepRequiredCapacity(ImmutableListMultimap.of());

	@NonNull private final ImmutableListMultimap<ResourceGroupYearWeek, StepItemRequiredCapacity> map;

	public static StepRequiredCapacity ofStep(final StepAllocation step)
	{
		final StepDef stepDef = step.getStepDef();
		final HumanResourceCapacity humanResourceCapacity = Check.assumeNotNull(stepDef.getResource().getHumanResourceCapacity(), "humanResourceCapacity should not be null at this stage: {}", step);
		final Duration requiredHumanCapacity = stepDef.getRequiredHumanCapacity();
		final StepAllocationId stepAllocationId = step.getId();

		final ResourceAvailabilityRanges scheduleRange = Check.assumeNotNull(step.getHumanResourceScheduledRange(), "HumanResourceScheduledRange should be set for {}", step);
		final Map<YearWeek, Duration> durationByWeek = scheduleRange.getDurationByWeek();

		final ArrayListMultimap<ResourceGroupYearWeek, StepItemRequiredCapacity> requiredCapacities = ArrayListMultimap.create();
		for (final YearWeek yearWeek : durationByWeek.keySet())
		{
			final ResourceGroupYearWeek resourceGroupYearWeek = ResourceGroupYearWeek.of(humanResourceCapacity, yearWeek);
			final StepItemRequiredCapacity capacityItem = StepItemRequiredCapacity.builder()
					.stepAllocationId(stepAllocationId)
					.humanResourceDuration(requiredHumanCapacity)
					.build();
			requiredCapacities.put(resourceGroupYearWeek, capacityItem);
		}

		return ofMultimap(requiredCapacities);
	}

	public static StepRequiredCapacity ofMultimap(@NonNull final ListMultimap<ResourceGroupYearWeek, StepItemRequiredCapacity> multimap)
	{
		if (multimap.isEmpty())
		{
			return ZERO;
		}

		return new StepRequiredCapacity(ImmutableListMultimap.copyOf(multimap));
	}

	private StepRequiredCapacity(@NonNull final ImmutableListMultimap<ResourceGroupYearWeek, StepItemRequiredCapacity> multimap)
	{
		this.map = multimap;
	}

	@Override
	public String toString()
	{
		final MoreObjects.ToStringHelper toStringHelper = MoreObjects.toStringHelper(this);
		map.asMap().forEach((groupYearWeek, items) -> {
			final Duration requiredDuration = items.stream()
					.map(StepItemRequiredCapacity::getHumanResourceDuration)
					.reduce(Duration.ZERO, Duration::plus);

			toStringHelper.add(groupYearWeek.toString(), requiredDuration);
		});
		return toStringHelper.toString();
	}

	public StepRequiredCapacity add(final StepRequiredCapacity other)
	{
		if (other.map.isEmpty())
		{
			return this;
		}
		else if (this.map.isEmpty())
		{
			return other;
		}
		else
		{
			final ImmutableListMultimap<ResourceGroupYearWeek, StepItemRequiredCapacity> mapNew = ImmutableListMultimap.<ResourceGroupYearWeek, StepItemRequiredCapacity>builder()
					.putAll(this.map)
					.putAll(other.map)
					.build();
			return ofMultimap(mapNew);
		}
	}

	public StepRequiredCapacity subtract(final StepRequiredCapacity other)
	{
		if (other.map.isEmpty())
		{
			return this;
		}
		else if (this.map.isEmpty())
		{
			return ZERO;
		}
		else
		{
			final ArrayListMultimap<ResourceGroupYearWeek, StepItemRequiredCapacity> mapNew = ArrayListMultimap.create(this.map);
			other.map.forEach(mapNew::remove);

			if (mapNew.size() == this.map.size())
			{
				return this; // no changes
			}

			return ofMultimap(mapNew);
		}
	}

	public void forEach(final BiConsumer<ResourceGroupYearWeek, Collection<StepItemRequiredCapacity>> consumer)
	{
		this.map.asMap().forEach(consumer);
	}

	public Set<HumanResourceCapacity> getCapacities()
	{
		return map.keySet()
				.stream()
				.map(ResourceGroupYearWeek::getCapacity)
				.collect(ImmutableSet.toImmutableSet());
	}
}
