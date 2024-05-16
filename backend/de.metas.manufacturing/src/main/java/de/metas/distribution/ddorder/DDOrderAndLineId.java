package de.metas.distribution.ddorder;

import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class DDOrderAndLineId
{
	@NonNull DDOrderId ddOrderId;
	@NonNull DDOrderLineId ddOrderLineId;
}
