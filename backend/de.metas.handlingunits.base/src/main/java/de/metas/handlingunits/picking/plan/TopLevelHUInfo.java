package de.metas.handlingunits.picking.plan;

import de.metas.handlingunits.HuId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
class TopLevelHUInfo
{
	@NonNull HuId topLevelHUId;
	boolean hasHUReservationsForRequestedDocument;
}
