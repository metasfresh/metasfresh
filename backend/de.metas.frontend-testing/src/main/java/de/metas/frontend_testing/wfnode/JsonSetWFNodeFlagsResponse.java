package de.metas.frontend_testing.wfnode;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

/** The values before the change, to restore them afterwards. */
@Value
@Builder
@Jacksonized
public class JsonSetWFNodeFlagsResponse
{
	int adWfNodeId;
	Boolean isActive;
	@Nullable String alwaysAvailableToUser;
}
