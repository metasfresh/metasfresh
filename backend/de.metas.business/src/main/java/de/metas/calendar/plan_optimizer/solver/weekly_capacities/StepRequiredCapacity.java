package de.metas.calendar.plan_optimizer.solver.weekly_capacities;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.calendar.plan_optimizer.domain.Step;
import de.metas.resource.HumanResourceTestGroupId;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.Value;
import org.apache.commons.collections4.ListUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

@Value(staticConstructor = "of")
public class StepRequiredCapacity
{
	public static StepRequiredCapacity ZERO = new StepRequiredCapacity(ImmutableMap.of());

	@NonNull ImmutableMap<ResourceGroupYearWeek, List<StepItemRequiredCapacity>> map;

	public static StepRequiredCapacity ofStep(final Step step)
	{
		final HumanResourceTestGroupId humanResourceTestGroupId = Check.assumeNotNull(step.getResource().getHumanResourceTestGroupId(), "humanResourceTestGroupId should not be null at this stage!");

		final ResourceGroupYearWeek resourceGroupYearWeek = ResourceGroupYearWeek.of(humanResourceTestGroupId, YearWeek.from(step.getStartDate()));

		final StepItemRequiredCapacity capacityItem = StepItemRequiredCapacity.builder()
				.stepId(step.getId())
				.startDate(step.getStartDate())
				.humanResourceDuration(step.getHumanResourceTestGroupDuration())
				.build();
		return new StepRequiredCapacity(resourceGroupYearWeek, capacityItem);
	}

	private StepRequiredCapacity(@NonNull final Map<ResourceGroupYearWeek, List<StepItemRequiredCapacity>> map)
	{
		this.map = ImmutableMap.copyOf(map);
	}

	private StepRequiredCapacity(@NonNull final ResourceGroupYearWeek resourceGroupYearWeek, @NonNull final StepItemRequiredCapacity capacityItem)
	{
		this.map = ImmutableMap.of(resourceGroupYearWeek, ImmutableList.of(capacityItem));
	}

	public StepRequiredCapacity add(final StepRequiredCapacity other)
	{
		final HashMap<ResourceGroupYearWeek, List<StepItemRequiredCapacity>> map = new HashMap<>(this.map);
		other.map.forEach((resourceGroupYearWeek, capacityItems) -> map.merge(resourceGroupYearWeek,
																			  capacityItems,
																			  (items1, items2) -> items2 != null
																					  ? Stream.concat(items1.stream(), items2.stream()).toList()
																					  : items1));

		return new StepRequiredCapacity(map);
	}

	public StepRequiredCapacity subtract(final StepRequiredCapacity other)
	{
		final HashMap<ResourceGroupYearWeek, List<StepItemRequiredCapacity>> map = new HashMap<>(this.map);
		other.map.forEach((resourceGroupYearWeek, capacityItems) -> map.merge(resourceGroupYearWeek,
																			  capacityItems,
																			  (items1, items2) -> items2 != null
																					  ? ListUtils.subtract(items1, items2)
																					  : items1));

		return new StepRequiredCapacity(map);
	}

	public void forEach(final BiConsumer<ResourceGroupYearWeek, List<StepItemRequiredCapacity>> consumer)
	{
		this.map.forEach(consumer);
	}

}
