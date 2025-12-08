package de.metas.distribution.ddorder;

import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class DDOrderAndLineId
{
	@NonNull DDOrderId ddOrderId;
	@NonNull DDOrderLineId ddOrderLineId;

	public static DDOrderAndLineId ofRepoIds(final int ddOrderId, final int ddOrderLineId)
	{
		return DDOrderAndLineId.of(DDOrderId.ofRepoId(ddOrderId), DDOrderLineId.ofRepoId(ddOrderLineId));
	}

}
