package de.metas.frontend_testing.masterdata.picking_slot;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonGetFreePickingSlotRequest
{
	@Nullable String bpartnerCode;
}
