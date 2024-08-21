package de.metas.picking.rest_api.json;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.picking.job.model.PickingTarget;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonPickingTarget
{
	@NonNull String id;
	@NonNull String caption;
	@Nullable HuPackingInstructionsId luPIId;
	@Nullable HuId luId;

	public static JsonPickingTarget of(@NonNull final PickingTarget target)
	{
		return builder()
				.id(target.getId())
				.caption(target.getCaption())
				.luPIId(target.getLuPIId())
				.luId(target.getLuId())
				.build();
	}

	public PickingTarget unbox()
	{
		return PickingTarget.builder()
				.caption(caption)
				.luPIId(luPIId)
				.luId(luId)
				.build();
	}
}
