package de.metas.frontend_testing.masterdata.bpartner;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

/**
 * Creates a per-run {@code C_BP_Group}. A business partner joins it through {@link JsonCreateBPartnerRequest#getBpGroup()}.
 */
@Value
@Builder
@Jacksonized
public class JsonBPGroupRequest
{
	/**
	 * {@code C_BP_Group.Name}/{@code Value}, uniquified per run. Defaults to the request's map-key identifier.
	 */
	@Nullable String name;
}
