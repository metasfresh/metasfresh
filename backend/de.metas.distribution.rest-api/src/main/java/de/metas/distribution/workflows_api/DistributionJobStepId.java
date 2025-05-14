package de.metas.distribution.workflows_api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleId;
import de.metas.util.lang.RepoIdAwares;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Objects;

@EqualsAndHashCode
public final class DistributionJobStepId
{
	@NonNull private final DDOrderMoveScheduleId scheduleId;

	private DistributionJobStepId(@NonNull final DDOrderMoveScheduleId scheduleId)
	{
		this.scheduleId = scheduleId;
	}

	public static DistributionJobStepId ofScheduleId(final @NonNull DDOrderMoveScheduleId scheduleId)
	{
		return new DistributionJobStepId(scheduleId);
	}

	@JsonCreator
	public static DistributionJobStepId ofJson(final @NonNull Object json)
	{
		final DDOrderMoveScheduleId scheduleId = RepoIdAwares.ofObject(json, DDOrderMoveScheduleId.class, DDOrderMoveScheduleId::ofRepoId);
		return ofScheduleId(scheduleId);
	}

	@JsonValue
	public String toString()
	{
		return toJson();
	}

	@NonNull
	private String toJson()
	{
		return String.valueOf(scheduleId.getRepoId());
	}

	public DDOrderMoveScheduleId toScheduleId() {return scheduleId;}

	public static boolean equals(@Nullable DistributionJobStepId id1, @Nullable DistributionJobStepId id2) {return Objects.equals(id1, id2);}
}
