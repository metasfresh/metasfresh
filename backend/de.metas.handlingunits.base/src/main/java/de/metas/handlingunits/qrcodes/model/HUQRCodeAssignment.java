package de.metas.handlingunits.qrcodes.model;

import de.metas.common.util.CoalesceUtil;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuItemId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
public class HUQRCodeAssignment
{
	@NonNull HUQRCodeUniqueId id;
	HuId huId;
	HuItemId aggregateHUItemId;

	@Builder
	public HUQRCodeAssignment(
			@NonNull final HUQRCodeUniqueId id,
			@Nullable final HuId huId,
			@Nullable final HuItemId aggregateHUItemId)
	{
		this.id = id;

		if (CoalesceUtil.countNotNulls(huId, aggregateHUItemId) != 1)
		{
			throw new AdempiereException("huId and aggregateHUItemId are mutually exclusive");
		}

		this.huId = huId;
		this.aggregateHUItemId = aggregateHUItemId;
	}

	public static HUQRCodeAssignment ofHuId(@NonNull final HuId huId, @NonNull final HUQRCodeUniqueId id)
	{
		return builder().id(id).huId(huId).build();
	}
}
