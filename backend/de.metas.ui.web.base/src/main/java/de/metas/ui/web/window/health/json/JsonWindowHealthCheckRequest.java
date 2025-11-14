package de.metas.ui.web.window.health.json;

import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.ad.element.api.AdWindowId;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

@Value
@Builder
@Jacksonized
public class JsonWindowHealthCheckRequest
{
	@Nullable ImmutableSet<AdWindowId> onlyAdWindowIds;
	
	@Nullable Set<String> knownContextVariables;
	@Nullable Map<String, Set<String>> knownMissingContextVariables;
}
