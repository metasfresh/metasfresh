package de.metas.handlingunits.qrcodes.service;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.qrcodes.model.HUOrAggregatedTUItemId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.Set;

@Value
public class HUQRCodeGenerateForExistingHUsRequest
{
	@NonNull ImmutableSet<HUOrAggregatedTUItemId> huOrAggregatedTUItemIds;

	@Builder
	private HUQRCodeGenerateForExistingHUsRequest(@NonNull final Set<HUOrAggregatedTUItemId> huOrAggregatedTUItemIds)
	{
		Check.assumeNotEmpty(huOrAggregatedTUItemIds, "huOrAggregatedTUItemIds is not empty");
		this.huOrAggregatedTUItemIds = ImmutableSet.copyOf(huOrAggregatedTUItemIds);
	}
}
