package de.metas.project.budget;

import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
@Builder(toBuilder = true)
public class BudgetProjectResourceSimulation
{
	@NonNull BudgetProjectResourceId projectResourceId;

	@NonNull CalendarDateRange dateRange;

	boolean isAppliedOnActualData;
	CalendarDateRange dateRangeBeforeApplying;

	public static BudgetProjectResourceSimulation reduce(@Nullable BudgetProjectResourceSimulation simulation, @NonNull UpdateRequest updateRequest)
	{
		final BudgetProjectResourceSimulationBuilder builder;
		if (simulation == null)
		{
			builder = builder()
					.projectResourceId(updateRequest.getProjectResourceId());
		}
		else
		{
			Check.assumeEquals(simulation.getProjectResourceId(), updateRequest.getProjectResourceId(), "expected same projectResourceId: {}, {}", simulation, updateRequest);

			builder = simulation.toBuilder();
		}

		return builder
				.dateRange(updateRequest.getDateRange())
				.build();

	}

	public BudgetProjectResource applyOn(@NonNull final BudgetProjectResource resource)
	{
		Check.assumeEquals(resource.getId(), projectResourceId, "expected same project and projectResourceId: {}, {}", resource, this);

		return resource.toBuilder()
				.dateRange(dateRange)
				.build();
	}

	public BudgetProjectResourceSimulation markingAsApplied(@NonNull final CalendarDateRange dateRangeBeforeApplying)
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
		@NonNull BudgetProjectResourceId projectResourceId;

		@NonNull CalendarDateRange dateRange;
	}
}
