package de.metas.calendar.simulation;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@EqualsAndHashCode
@ToString
public class SimulationIdsPredicate
{
	public static SimulationIdsPredicate ACTUAL_DATA_ONLY = new SimulationIdsPredicate(Collections.singletonList(null));

	public static SimulationIdsPredicate only(@Nullable final SimulationPlanId simulationId)
	{
		if (simulationId == null)
		{
			return ACTUAL_DATA_ONLY;
		}

		return new SimulationIdsPredicate(Collections.singletonList(simulationId));
	}

	public static SimulationIdsPredicate actualDataAnd(@Nullable final SimulationPlanId simulationId)
	{
		if (simulationId == null)
		{
			return ACTUAL_DATA_ONLY;
		}

		return new SimulationIdsPredicate(Collections.unmodifiableList(Arrays.asList(null, simulationId)));
	}

	private final Collection<SimulationPlanId> simulationIds;

	private SimulationIdsPredicate(@NonNull final Collection<SimulationPlanId> simulationIds)
	{
		this.simulationIds = simulationIds;
	}

	public Collection<SimulationPlanId> toCollection()
	{
		return simulationIds;
	}
}
