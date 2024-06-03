package de.metas.common.handlingunits;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonDisposalReason
{
	@NonNull String key;
	@NonNull String caption;
}
