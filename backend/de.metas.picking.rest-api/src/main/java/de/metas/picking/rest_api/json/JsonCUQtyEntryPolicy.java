package de.metas.picking.rest_api.json;

import de.metas.handlingunits.picking.config.mobileui.CUQtyEntryPolicy;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/** What the CU quantity entry mode of a picking line means for the screens; the rules live in {@link CUQtyEntryPolicy}. */
// only the explicitly named fields are JSON properties: Lombok's isXxx() getters would add the names without the "is" a second time
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@Value
@Builder
@Jacksonized
public class JsonCUQtyEntryPolicy
{
	// explicit names: the mobile frontend reads exactly these
	@JsonProperty("isLabelRequired") boolean isLabelRequired;
	@JsonProperty("isPackIdRequired") boolean isPackIdRequired;
	@JsonProperty("isScanOnce") boolean isScanOnce;

	public static JsonCUQtyEntryPolicy of(@NonNull final CUQtyEntryPolicy policy)
	{
		return builder()
				.isLabelRequired(policy.isLabelRequired())
				.isPackIdRequired(policy.isPackIdRequired())
				.isScanOnce(policy.isScanOnce())
				.build();
	}
}
