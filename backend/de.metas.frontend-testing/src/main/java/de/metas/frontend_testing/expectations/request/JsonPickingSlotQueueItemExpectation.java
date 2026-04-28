package de.metas.frontend_testing.expectations.request;

import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonPickingSlotQueueItemExpectation
{
	@NonNull Identifier hu;
}
