package de.metas.project.workorder.step;

import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.project.ProjectId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder(toBuilder = true)
public class WOProjectStepSimulation
{
	@NonNull ProjectId projectId;
	@NonNull WOProjectStepId stepId;

	@NonNull CalendarDateRange dateRange;
	
	public static WOProjectStepSimulation reduce(@Nullable WOProjectStepSimulation simulation, @NonNull UpdateRequest updateRequest)
	{
		if (simulation == null)
		{
			return builder()
					.projectId(updateRequest.getProjectId())
					.stepId(updateRequest.getStepId())
					.dateRange(updateRequest.getDateRange())
					.build();
		}
		else
		{
			Check.assumeEquals(simulation.getProjectId(), updateRequest.getProjectId(), "expected same projectId: {}, {}", simulation, updateRequest);
			Check.assumeEquals(simulation.getStepId(), updateRequest.getStepId(), "expected same stepId: {}, {}", simulation, updateRequest);

			return simulation.toBuilder()
					.dateRange(updateRequest.getDateRange())
					.build();
		}
	}

	@NonNull
	public WOProjectStep applyOn(@NonNull final WOProjectStep step)
	{
		Check.assumeEquals(step.getProjectId(), projectId, "expected same projectId: {}, {}", step, this);
		Check.assumeEquals(step.getWoProjectStepId(), stepId, "expected same stepId: {}, {}", step, this);

		return step.toBuilder()
				.dateRange(dateRange)
				.build();
	}

	@Value
	@Builder
	public static class UpdateRequest
	{
		@NonNull SimulationPlanId simulationId;
		@NonNull ProjectId projectId;
		@NonNull WOProjectStepId stepId;

		@NonNull CalendarDateRange dateRange;
	}

}
