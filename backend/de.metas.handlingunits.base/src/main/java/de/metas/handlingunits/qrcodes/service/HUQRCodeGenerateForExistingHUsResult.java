package de.metas.handlingunits.qrcodes.service;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.qrcodes.model.HUOrAggregatedTUItemId;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

@EqualsAndHashCode
@ToString
public class HUQRCodeGenerateForExistingHUsResult
{
	private final ImmutableSetMultimap<HUOrAggregatedTUItemId, HUQRCode> huQRCodes;

	HUQRCodeGenerateForExistingHUsResult(@NonNull final SetMultimap<HUOrAggregatedTUItemId, HUQRCode> huQRCodes)
	{
		this.huQRCodes = ImmutableSetMultimap.copyOf(huQRCodes);
	}

	public ImmutableSetMultimap<HUOrAggregatedTUItemId, HUQRCode> toSetMultimap() {return huQRCodes;}

	public HUQRCode getSingleQRCode(@NonNull final HuId huId)
	{
		final HUOrAggregatedTUItemId huOrAggregatedTUItemId = HUOrAggregatedTUItemId.ofHuId(huId);
		final ImmutableSet<HUQRCode> qrCodes = huQRCodes.get(huOrAggregatedTUItemId);
		if (qrCodes.size() != 1)
		{
			throw new AdempiereException("Expected only one QR code to be generated for " + huId + " but found " + qrCodes);
		}

		return qrCodes.iterator().next();
	}
}
