package de.metas.hu_consolidation.mobile.rest_api.json;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonHUConsolidationJobAvailableTargets
{
	@NonNull ImmutableList<JsonHUConsolidationTarget> targets;
}
