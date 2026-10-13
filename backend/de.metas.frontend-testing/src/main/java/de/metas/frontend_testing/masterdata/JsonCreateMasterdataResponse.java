package de.metas.frontend_testing.masterdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.metas.frontend_testing.masterdata.bpartner.JsonCreateBPartnerResponse;
import de.metas.frontend_testing.masterdata.inlinefilter.JsonSetupInlineFilterResponse;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

@Value
@Builder
@Jacksonized
public class JsonCreateMasterdataResponse
{
	@Nullable @JsonInclude(JsonInclude.Include.NON_EMPTY) Map<String, Object> context;

	/**
	 * Sysconfig name to its value before the request. A {@code null} value means the sysconfig did not exist before.
	 * Deletion is not supported: to undo such a sysconfig, callers reset it to a neutral value.
	 */
	@Nullable @JsonInclude(JsonInclude.Include.NON_EMPTY) Map<String, String> previousSysconfigs;

	@Nullable @JsonInclude(JsonInclude.Include.NON_EMPTY) Map<String, JsonCreateBPartnerResponse> bpartners;

	/** One entry per request {@code inlineFilters} entry, in request order. */
	@Nullable @JsonInclude(JsonInclude.Include.NON_EMPTY) List<JsonSetupInlineFilterResponse> inlineFilters;
}
