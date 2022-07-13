package de.metas.project.workorder.conflicts;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.project.workorder.WOProjectResourceId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
// pimp json serialization which is needed for snapshot testing
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class ResourceAllocationConflict
{
	@NonNull ProjectResourceIdsPair projectResourceIdsPair;
	@Nullable SimulationPlanId simulationId;
	@NonNull ResourceAllocationConflictStatus status;

	@Builder
	private ResourceAllocationConflict(
			@NonNull final ProjectResourceIdsPair projectResourceIdsPair,
			@Nullable final SimulationPlanId simulationId,
			@NonNull ResourceAllocationConflictStatus status)
	{
		if (status.isResolved() && simulationId == null)
		{
			throw new AdempiereException("Status RESOLVED is legit only in simulation");
		}

		this.projectResourceIdsPair = projectResourceIdsPair;
		this.simulationId = simulationId;
		this.status = status;
	}

	public boolean isConflict()
	{
		return status.isConflict();
	}

	public boolean isMatching(@NonNull final WOProjectResourceId projectResourceId) {return projectResourceIdsPair.isMatching(projectResourceId);}
}
