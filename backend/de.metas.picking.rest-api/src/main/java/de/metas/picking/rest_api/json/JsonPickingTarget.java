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

	//
	// New HU
	@Nullable HuPackingInstructionsId luPIId;

	//
	// Created HU
	@Nullable HuId huId;

	public static JsonPickingTarget of(@NonNull final PickingTarget target)
	{
		return builder()
				.id(target.getId())
				.caption(target.getCaption())
				.luPIId(target.getLuPIId())
				.huId(target.getHuId())
				.build();
	}

	public PickingTarget unbox()
	{
		return PickingTarget.builder()
				.caption(caption)
				.luPIId(luPIId)
				.huId(huId)
				.build();
	}
}
