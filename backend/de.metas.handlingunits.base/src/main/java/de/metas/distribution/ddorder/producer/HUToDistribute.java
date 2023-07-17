package de.metas.distribution.ddorder.producer;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.product.LotNumberQuarantine;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class HUToDistribute
{
	public static HUToDistribute ofHU(final I_M_HU hu)
	{
		return builder().hu(hu).build();
	}

	@NonNull I_M_HU hu;
	@Nullable LotNumberQuarantine quarantineLotNo;
	@Nullable BPartnerLocationId bpartnerLocationId;

	@Builder
	private HUToDistribute(
			@NonNull final I_M_HU hu,
			@Nullable LotNumberQuarantine quarantineLotNo,
			@Nullable BPartnerLocationId bpartnerLocationId)
	{
		this.hu = hu;
		this.quarantineLotNo = quarantineLotNo;
		this.bpartnerLocationId = bpartnerLocationId;
	}
}
