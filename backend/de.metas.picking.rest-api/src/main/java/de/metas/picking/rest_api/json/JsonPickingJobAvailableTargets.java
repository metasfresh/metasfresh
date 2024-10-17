package de.metas.picking.rest_api.json;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonPickingJobAvailableTargets
{
	@NonNull @Singular List<JsonLUPickingTarget> targets;
	@NonNull @Singular List<JsonTUPickingTarget> tuTargets;
}
