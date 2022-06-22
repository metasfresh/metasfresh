package de.metas.project.workorder;

import de.metas.calendar.simulation.CalendarSimulationId;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder(toBuilder = true)
public class WOProjectResourceSimulation
{
	@NonNull CalendarSimulationId simulationId;
	@NonNull WOProjectStepAndResourceId projectStepAndResourceId;

	@NonNull CalendarDateRange dateRange;

	public static WOProjectResourceSimulation reduce(@Nullable WOProjectResourceSimulation simulation, @NonNull UpdateRequest updateRequest)
	{
		if (simulation == null)
		{
			return builder()
					.simulationId(updateRequest.getSimulationId())
					.projectStepAndResourceId(updateRequest.getProjectStepAndResourceId())
					.dateRange(updateRequest.getDateRange())
					.build();
		}
		else
		{
			Check.assumeEquals(simulation.getSimulationId(), updateRequest.getSimulationId(), "expected same simulationId: {}, {}", simulation, updateRequest);
			Check.assumeEquals(simulation.getProjectStepAndResourceId(), updateRequest.getProjectStepAndResourceId(), "expected same projectStepAndResourceId: {}, {}", simulation, updateRequest);

			return simulation.toBuilder()
					.dateRange(updateRequest.getDateRange())
					.build();
		}
	}

	public WOProjectResource applyOn(@NonNull final WOProjectResource resource)
	{
		Check.assumeEquals(resource.getWOProjectStepAndResourceId(), projectStepAndResourceId, "expected same project and step and projectResourceId: {}, {}", resource, this);

		return resource.toBuilder()
				.dateRange(dateRange)
				.duration(dateRange.getDuration())
				.build();
	}

	//
	//
	//

	@Value
	@Builder
	public static class UpdateRequest
	{
		@NonNull CalendarSimulationId simulationId;
		@NonNull WOProjectStepAndResourceId projectStepAndResourceId;

		@NonNull CalendarDateRange dateRange;
	}
}
