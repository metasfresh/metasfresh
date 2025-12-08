package de.metas.picking.api;

import de.metas.bpartner.BPartnerLocationId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;

@Value
@Builder
public class PickingSlotCreateRequest
{
	@NonNull String pickingSlotCode;
	@NonNull LocatorId locatorId;
	boolean isDynamic;
	@Nullable BPartnerLocationId bpartnerAndLocationId;
}
