package de.metas.project.workorder.conflicts;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.util.OptionalBoolean;
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

	OptionalBoolean approved;

	@Builder(toBuilder = true)
	private ResourceAllocationConflict(
			@NonNull final ProjectResourceIdsPair projectResourceIdsPair,
			@Nullable final SimulationPlanId simulationId,
			@NonNull ResourceAllocationConflictStatus status,
			@Nullable final OptionalBoolean approved)
	{
		if (status.isResolved() && simulationId == null)
		{
			throw new AdempiereException("Status RESOLVED is legit only in simulation");
		}

		this.projectResourceIdsPair = projectResourceIdsPair;
		this.simulationId = simulationId;
		this.status = status;
		this.approved = approved != null ? approved : OptionalBoolean.UNKNOWN;
	}

	public boolean isSimulation()
	{
		return this.simulationId != null;
	}

	public boolean isSimulation(@NonNull final SimulationPlanId expectedSimulationId)
	{
		return this.simulationId != null && SimulationPlanId.equals(this.simulationId, expectedSimulationId);
	}

	public boolean isConflict()
	{
		return status.isConflict();
	}

	public boolean isMatching(@NonNull final WOProjectResourceId projectResourceId) {return projectResourceIdsPair.isMatching(projectResourceId);}

	public boolean isApproved()
	{
		return getApproved().isTrue();
	}
}
