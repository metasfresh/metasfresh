package de.metas.handlingunits.rest_api;

import de.metas.common.rest_api.v2.JsonQuantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.springframework.lang.Nullable;

@Value
@Builder
@Jacksonized
public class JsonHUQtyChangeRequest
{
	@NonNull String huQRCode;
	@NonNull JsonQuantity qty;
	@Nullable String description;
	boolean splitOneIfAggregated;
}
