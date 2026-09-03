package de.metas.ui.web.window.health.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Map;

@Value
@Builder
@Jacksonized
public class JsonContextVariablesResponse
{
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Nullable Map<String, String> missing;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Nullable Map<String, String> reportedMissingButNotUsed;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Nullable Map<String, String> reportedMissingButExists;
}
