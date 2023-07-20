package de.metas.handlingunits.qrcodes.service;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.Set;

@Value
public class HUQRCodeGenerateForExistingHUsRequest
{
	@NonNull ImmutableSet<HuId> huIds;

	@Builder
	private HUQRCodeGenerateForExistingHUsRequest(@NonNull final Set<HuId> huIds)
	{
		Check.assumeNotEmpty(huIds, "huIds is not empty");
		this.huIds = ImmutableSet.copyOf(huIds);
	}

	public static HUQRCodeGenerateForExistingHUsRequest ofHuIds(@NonNull final Set<HuId> huIds)
	{
		return builder().huIds(huIds).build();
	}

	public static HUQRCodeGenerateForExistingHUsRequest ofHuId(@NonNull final HuId huId)
	{
		return builder().huIds(ImmutableSet.of(huId)).build();
	}
}
