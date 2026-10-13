package de.metas.frontend_testing.masterdata;

import de.metas.frontend_testing.masterdata.bpartner.JsonCreateBPartnerRequest;
import de.metas.frontend_testing.masterdata.externalreference.JsonCreateExternalReferenceRequest;
import de.metas.frontend_testing.masterdata.inlinefilter.JsonSetupInlineFilterRequest;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

@Value
@Builder
@Jacksonized
public class JsonCreateMasterdataRequest
{
	@Nullable Map<String, Object> context;

	@Nullable Map<String, String> sysconfigs;

	/** Business partners to create, by identifier. */
	@Nullable Map<String, JsonCreateBPartnerRequest> bpartners;

	/** External references to create; each refers to a partner by identifier: a key of {@code bpartners} or a {@code context} identifier. Not present in the later (new_dawn-based) releases. */
	@Nullable List<JsonCreateExternalReferenceRequest> externalReferences;

	/** Inline filters to set up, applied in order. Not present in the later (new_dawn-based) releases. */
	@Nullable List<JsonSetupInlineFilterRequest> inlineFilters;
}
