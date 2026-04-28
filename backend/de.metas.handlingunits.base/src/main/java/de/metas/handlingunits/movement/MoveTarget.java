package de.metas.handlingunits.movement;

import de.metas.handlingunits.qrcodes.model.HUQRCode;
import lombok.Builder;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;

@Value
@Builder
class MoveTarget
{
	@Nullable LocatorId locatorId;
	@Nullable HUQRCode huQRCode;

	boolean isPlaceAggTUsOnNewLUAfterMove;
}
