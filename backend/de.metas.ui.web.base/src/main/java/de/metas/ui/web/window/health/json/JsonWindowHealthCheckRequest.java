package de.metas.ui.web.window.health.json;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.ad.element.api.AdWindowId;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class JsonWindowHealthCheckRequest
{
	@Nullable Set<AdWindowId> onlyAdWindowIds;

	@Nullable Boolean checkContextVariables;
	@Nullable Set<String> knownContextVariables;
	@Nullable Map<String, String> knownMissingContextVariables;
}
