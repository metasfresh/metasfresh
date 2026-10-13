package de.metas.frontend_testing.masterdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Map;

@Value
@Builder
@Jacksonized
public class JsonCreateMasterdataResponse
{
	@Nullable @JsonInclude(JsonInclude.Include.NON_EMPTY) Map<String, Object> context;

	/**
	 * Sysconfig name to its value before the request; a sysconfig that did not exist before is present with a {@code null} value
	 * (so the caller can tell "restore" from "delete").
	 */
	@Nullable @JsonInclude(JsonInclude.Include.NON_EMPTY) Map<String, String> previousSysconfigs;
}
