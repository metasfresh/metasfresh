package de.metas.project.workorder;

import de.metas.calendar.simulation.SimulationPlanId;
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
	@NonNull WOProjectAndResourceId projectAndResourceId;

	@NonNull CalendarDateRange dateRange;

	public WOProjectResourceId getProjectResourceId()
	{
		return getProjectAndResourceId().getProjectResourceId();
	}

	public static WOProjectResourceSimulation reduce(@Nullable WOProjectResourceSimulation simulation, @NonNull UpdateRequest updateRequest)
	{
		if (simulation == null)
		{
			return builder()
					.projectAndResourceId(updateRequest.getProjectAndResourceId())
					.dateRange(updateRequest.getDateRange())
					.build();
		}
		else
		{
			Check.assumeEquals(simulation.getProjectAndResourceId(), updateRequest.getProjectAndResourceId(), "expected same projectAndResourceId: {}, {}", simulation, updateRequest);

			return simulation.toBuilder()
					.dateRange(updateRequest.getDateRange())
					.build();
		}
	}

	public WOProjectResource applyOn(@NonNull final WOProjectResource resource)
	{
		Check.assumeEquals(resource.getWOProjectAndResourceId(), projectAndResourceId, "expected same project and projectResourceId: {}, {}", resource, this);

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
		@NonNull SimulationPlanId simulationId;
		@NonNull WOProjectAndResourceId projectAndResourceId;

		@NonNull CalendarDateRange dateRange;
	}
}
