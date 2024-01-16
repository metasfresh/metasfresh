package org.adempiere.mmovement;

import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value(staticConstructor = "of")
public class MovementAndLineId
{
	@NonNull MovementId movementId;
	@NonNull MovementLineId movementLineId;

	@Nullable
	public static MovementAndLineId ofRepoIdsOrNull(final int movementRepoId, final int movementLineRepoId)
	{
		final MovementId movementId = MovementId.ofRepoIdOrNull(movementRepoId);
		final MovementLineId movementLineId = MovementLineId.ofRepoIdOrNull(movementLineRepoId);
		return movementId != null && movementLineId != null ? of(movementId, movementLineId) : null;
	}

	public static MovementAndLineId ofRepoId(final int movementRepoId, final int movementLineRepoId)
	{
		final MovementId movementId = MovementId.ofRepoId(movementRepoId);
		final MovementLineId movementLineId = MovementLineId.ofRepoId(movementLineRepoId);
		return of(movementId, movementLineId);
	}
}
