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

	@Nullable @JsonInclude(JsonInclude.Include.NON_EMPTY) Map<String, String> previousSysconfigs;
}
