package de.metas.externalsystem;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ExternalSystemCreateRequest
{
	@NonNull ExternalSystemType type;
	@NonNull String name;
}
