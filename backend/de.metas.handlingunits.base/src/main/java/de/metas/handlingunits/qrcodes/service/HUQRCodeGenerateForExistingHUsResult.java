package de.metas.handlingunits.qrcodes.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import de.metas.common.util.Check;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

@EqualsAndHashCode
@ToString
public class HUQRCodeGenerateForExistingHUsResult
{
	private final ImmutableSetMultimap<HuId, HUQRCode> huQRCodes;

	HUQRCodeGenerateForExistingHUsResult(@NonNull final SetMultimap<HuId, HUQRCode> huQRCodes)
	{
		this.huQRCodes = ImmutableSetMultimap.copyOf(huQRCodes);
	}

	public HUQRCode getSingleQRCode(@NonNull final HuId huId)
	{
		final ImmutableSet<HUQRCode> qrCodes = huQRCodes.get(huId);
		if (qrCodes.size() != 1)
		{
			throw new AdempiereException("Expected only one QR code to be generated for " + huId + " but found " + qrCodes);
		}

		return qrCodes.iterator().next();
	}

	public ImmutableList<HUQRCode> toList()
	{
		return huQRCodes.values().asList();
	}

	@NonNull
	public HUQRCode getFirstQRCode(final @NonNull HuId huId)
	{
		if (Check.isEmpty(huQRCodes.get(huId)))
		{
			throw new AdempiereException("No QRCode was generated for HuId=" + huId.getRepoId());
		}

		return huQRCodes.get(huId).iterator().next();
	}
}
