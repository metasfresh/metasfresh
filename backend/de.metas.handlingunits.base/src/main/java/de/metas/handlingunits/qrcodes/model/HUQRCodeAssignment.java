package de.metas.handlingunits.qrcodes.model;

import de.metas.handlingunits.HuId;
import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class HUQRCodeAssignment
{
	@NonNull HUQRCodeUniqueId id;
	@NonNull HUOrAggregatedTUItemId huOrAggregatedTUItemId;


	public static HUQRCodeAssignment ofHuId(@NonNull final HuId huId, @NonNull final HUQRCodeUniqueId id)
	{
		return new HUQRCodeAssignment(id, HUOrAggregatedTUItemId.ofHuId(huId));
	}
}
