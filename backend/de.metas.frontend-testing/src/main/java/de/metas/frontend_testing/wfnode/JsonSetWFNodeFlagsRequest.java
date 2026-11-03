package de.metas.frontend_testing.wfnode;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonSetWFNodeFlagsRequest
{
	int adWfNodeId;

	/** {@code AD_WF_Node.IsActive}; {@code null} leaves it unchanged. */
	@Nullable Boolean isActive;

	/**
	 * {@code AD_WF_Node.PP_AlwaysAvailableToUser} ({@code "Y"} / {@code "N"}); an empty string clears it
	 * (JSON {@code null} is indistinguishable from "omitted" here), {@code null} leaves it unchanged.
	 */
	@Nullable String alwaysAvailableToUser;
}
