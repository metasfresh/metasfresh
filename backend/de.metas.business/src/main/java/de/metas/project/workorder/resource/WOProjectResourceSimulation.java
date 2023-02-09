package de.metas.project.workorder.resource;

import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.project.ProjectId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
@Builder(toBuilder = true)
public class WOProjectResourceSimulation
{
	@NonNull WOProjectResourceId projectResourceId;

	@NonNull CalendarDateRange dateRange;

	boolean isAppliedOnActualData;
	CalendarDateRange dateRangeBeforeApplying;

	public ProjectId getProjectId()
	{
		return getProjectResourceId().getProjectId();
	}

	public static WOProjectResourceSimulation reduce(@Nullable WOProjectResourceSimulation simulation, @NonNull UpdateRequest updateRequest)
	{
		if (simulation == null)
		{
			return builder()
					.projectResourceId(updateRequest.getProjectResourceId())
					.dateRange(updateRequest.getDateRange())
					.build();
		}
		else
		{
			Check.assumeEquals(simulation.getProjectResourceId(), updateRequest.getProjectResourceId(), "expected same projectResourceId: {}, {}", simulation, updateRequest);

			return simulation.toBuilder()
					.dateRange(updateRequest.getDateRange())
					.build();
		}
	}

	@NonNull
	public WOProjectResource applyOn(@NonNull final WOProjectResource resource)
	{
		Check.assumeEquals(resource.getWoProjectResourceId(), projectResourceId, "expected same project and projectResourceId: {}, {}", resource, this);

		return resource.toBuilder()
				.dateRange(dateRange)
				.duration(dateRange.getDuration())
				.build();
	}

	@NonNull
	public WOProjectResourceSimulation markingAsApplied(@Nullable final CalendarDateRange dateRangeBeforeApplying)
	{
		if (isAppliedOnActualData)
		{
			throw new AdempiereException("Already applied");
		}

		return toBuilder()
				.isAppliedOnActualData(true)
				.dateRangeBeforeApplying(dateRangeBeforeApplying)
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
		@NonNull WOProjectResourceId projectResourceId;

		@NonNull CalendarDateRange dateRange;
	}
}
