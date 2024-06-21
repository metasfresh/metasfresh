package de.metas.picking.rest_api.json;

import de.metas.handlingunits.picking.job.model.PickingTarget;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.stream.Collectors;

@Value
@Builder
@Jacksonized
public class JsonPickingJobAvailableTargets
{
	@NonNull @Singular List<JsonPickingTarget> targets;

	public static JsonPickingJobAvailableTargets of(final List<PickingTarget> targets)
	{
		return builder()
				.targets(targets.stream()
						.map(JsonPickingTarget::of)
						.collect(Collectors.toList()))
				.build();
	}
}
