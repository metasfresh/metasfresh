package de.metas.frontend_testing.masterdata;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Map;

@Value
@Builder
@Jacksonized
public class JsonCreateMasterdataRequest
{
	@Nullable Map<String, Object> context;

	@Nullable Map<String, String> sysconfigs;
}
