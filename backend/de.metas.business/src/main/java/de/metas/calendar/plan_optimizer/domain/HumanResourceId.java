package de.metas.calendar.plan_optimizer.domain;

import de.metas.resource.HumanResourceTestGroupId;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.annotation.Nullable;

@EqualsAndHashCode
public final class HumanResourceId
{
	@NonNull private final HumanResourceTestGroupId humanResourceTestGroupId;

	public HumanResourceId(@NonNull final HumanResourceTestGroupId humanResourceTestGroupId)
	{
		this.humanResourceTestGroupId = humanResourceTestGroupId;
	}

	@NonNull
	public static HumanResourceId of(@NonNull final HumanResourceTestGroupId humanResourceTestGroupId)
	{
		return new HumanResourceId(humanResourceTestGroupId);
	}

	@Nullable
	public static HumanResourceId ofNullable(@Nullable final HumanResourceTestGroupId humanResourceTestGroupId)
	{
		return humanResourceTestGroupId != null ? of(humanResourceTestGroupId) : null;
	}

	@Override
	public String toString() {return String.valueOf(humanResourceTestGroupId.getRepoId());}
}
