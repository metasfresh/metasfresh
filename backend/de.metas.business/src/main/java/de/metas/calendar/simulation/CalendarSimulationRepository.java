package de.metas.calendar.simulation;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.user.UserId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CalendarSimulationRepository
{
	private final ConcurrentHashMap<CalendarSimulationId, CalendarSimulationRef> map = new ConcurrentHashMap<>();

	public CalendarSimulationRef createNewSimulation(
			@NonNull String name,
			@NonNull UserId createdByUserId)
	{
		final CalendarSimulationRef simulation = CalendarSimulationRef.builder()
				.id(CalendarSimulationId.random())
				.name(name)
				.processed(false)
				.created(SystemTime.asInstant())
				.createdByUserId(createdByUserId)
				.build();

		map.put(simulation.getId(), simulation);

		return simulation;
	}

	public CalendarSimulationRef getById(@NonNull final CalendarSimulationId id)
	{
		final CalendarSimulationRef simulationRef = map.get(id);
		if (simulationRef == null)
		{
			throw new AdempiereException("No simulation found for " + id);
		}
		return simulationRef;
	}

	public Collection<CalendarSimulationRef> getAll()
	{
		return ImmutableList.copyOf(map.values());
	}
}
