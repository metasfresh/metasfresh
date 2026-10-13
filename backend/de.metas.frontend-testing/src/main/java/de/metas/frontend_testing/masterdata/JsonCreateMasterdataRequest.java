package de.metas.frontend_testing.masterdata;

import de.metas.frontend_testing.masterdata.bpartner.JsonCreateBPartnerRequest;
import de.metas.frontend_testing.masterdata.externalreference.JsonCreateExternalReferenceRequest;
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

	/** External references to create; they refer to partners from {@code bpartners} (or the {@code context}). */
	@Nullable List<JsonCreateExternalReferenceRequest> externalReferences;
}
