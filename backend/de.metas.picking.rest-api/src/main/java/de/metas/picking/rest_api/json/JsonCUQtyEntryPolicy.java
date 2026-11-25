package de.metas.picking.rest_api.json;

import de.metas.handlingunits.picking.config.mobileui.CUQtyEntryPolicy;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/** What the CU quantity entry mode of a picking line means for the screens; the rules live in {@link CUQtyEntryPolicy}. */
@Value
@Builder
@Jacksonized
public class JsonCUQtyEntryPolicy
{
	boolean labelRequired;
	boolean packIdRequired;
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
