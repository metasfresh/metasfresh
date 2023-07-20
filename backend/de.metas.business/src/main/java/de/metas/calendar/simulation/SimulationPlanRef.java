package de.metas.calendar.simulation;

import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.time.Instant;

@Value
@Builder
public class SimulationPlanRef
{
	@NonNull SimulationPlanId id;
	@NonNull String name;
	@NonNull UserId responsibleUserId;

	@NonNull SimulationPlanDocStatus docStatus;
	boolean processed;
	boolean isMainSimulation;

	@NonNull Instant created;

	public boolean isEditable() {return getDocStatus().isDrafted();}

	public void assertEditable()
	{
		if (!isEditable())
		{
			throw new AdempiereException("Simulation plan is not editable");
		}
	}
}
