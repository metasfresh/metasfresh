package de.metas.picking.rest_api.json;

import de.metas.handlingunits.picking.config.mobileui.CUQtyEntryPolicy;
import com.fasterxml.jackson.annotation.JsonProperty;
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
	// the JSON property names are read by the mobile frontend: they stay without the "is"
	@JsonProperty("labelRequired") boolean isLabelRequired;
	@JsonProperty("packIdRequired") boolean isPackIdRequired;
	@JsonProperty("scanOnce") boolean isScanOnce;

	public static JsonCUQtyEntryPolicy of(@NonNull final CUQtyEntryPolicy policy)
	{
		return builder()
				.isLabelRequired(policy.isLabelRequired())
				.isPackIdRequired(policy.isPackIdRequired())
				.isScanOnce(policy.isScanOnce())
				.build();
	}
}
