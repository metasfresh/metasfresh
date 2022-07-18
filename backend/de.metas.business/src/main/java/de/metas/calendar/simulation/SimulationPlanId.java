package de.metas.calendar.simulation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class SimulationPlanId implements RepoIdAware
{
	public static SimulationPlanId ofRepoId(final int repoId) {return new SimulationPlanId(repoId);}

	@JsonCreator
	public static SimulationPlanId ofObject(@NonNull final Object repoIdObj)
	{
		final Integer repoId = NumberUtils.asIntegerOrNull(repoIdObj);
		if (repoId == null)
		{
			throw new AdempiereException("Cannot convert `" + repoIdObj + "` (" + repoIdObj.getClass() + ") to " + SimulationPlanId.class.getSimpleName());
		}
		return ofRepoId(repoId);
	}

	public static SimulationPlanId ofNullableObject(@Nullable final Object repoIdObj)
	{
		return repoIdObj != null ? ofObject(repoIdObj) : null;
	}

	@Nullable
	public static SimulationPlanId ofRepoIdOrNull(final int repoId) {return repoId > 0 ? new SimulationPlanId(repoId) : null;}

	public static int toRepoId(@Nullable final SimulationPlanId id) {return id != null ? id.getRepoId() : -1;}

	int repoId;

	private SimulationPlanId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_SimulationPlan_ID");
	}

	@Override
	@JsonValue
	public int getRepoId() {return repoId;}

	public static boolean equals(@Nullable final SimulationPlanId id1, @Nullable final SimulationPlanId id2) {return Objects.equals(id1, id2);}
}
