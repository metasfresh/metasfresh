package de.metas.frontend_testing.masterdata.picking_slot;

import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonPickingSlotCreateRequest
{
	/** Picking slot code to use as is, e.g. a short one that fits a phone screen; the request fails if a slot with this code already exists. Default = the identifier plus a timestamp (unique). */
	@Nullable String code;
	@Nullable Identifier locator;
	@Nullable Identifier bpartnerLocation;
}
