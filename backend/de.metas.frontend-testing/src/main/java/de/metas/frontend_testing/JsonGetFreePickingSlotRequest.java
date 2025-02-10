package de.metas.frontend_testing;

import lombok.Builder;
import lombok.NonNull;
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
