package de.metas.externalsystem;

import de.metas.util.lang.ExternalHeaderIdWithExternalLineIds;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ExternalSystemIdWithExternalIds
{
	@NonNull ExternalSystemId externalSystemId;

	@NonNull ExternalHeaderIdWithExternalLineIds externalHeaderIdWithExternalLineIds;
}
