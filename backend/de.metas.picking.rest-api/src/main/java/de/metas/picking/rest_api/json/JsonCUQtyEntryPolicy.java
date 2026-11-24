package de.metas.picking.rest_api.json;

import de.metas.handlingunits.picking.config.mobileui.CUQtyEntryPolicy;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * What the CU quantity entry mode of a picking line means for the screens. Published by the backend so the mobile app only reads it
 * (the rules live in {@link CUQtyEntryPolicy}).
 * <p>
 * Mobile app counterpart: {@code misc/services/mobile-webui/mobile-webui-frontend/src/apps/picking/cuQtyEntry/cuQtyEntryPolicy.js} reads these fields. Change both together.
 */
@Value
@Builder
@Jacksonized
public class JsonCUQtyEntryPolicy
{
	/** a CU label is read: lot / best-before come from it and the TU is open before booking */
	boolean labelRequired;
	/** the label must carry a pack ID and one scan books one CU */
	boolean packIdRequired;
	/** one label is scanned, then the quantity is typed */
	boolean scanOnce;

	public static JsonCUQtyEntryPolicy of(@NonNull final CUQtyEntryPolicy policy)
	{
		return builder()
				.labelRequired(policy.isLabelRequired())
				.packIdRequired(policy.isPackIdRequired())
				.scanOnce(policy.isScanOnce())
				.build();
	}
}
