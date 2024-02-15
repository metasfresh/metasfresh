package de.metas.picking.rest_api.json;

import de.metas.common.rest_api.v2.JsonQuantity;
import de.metas.global_qrcodes.JsonDisplayableQRCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class JsonPickingJobStepPickFrom
{
	@NonNull String alternativeId;
	@NonNull String locatorName;
	@NonNull JsonDisplayableQRCode huQRCode;
	@NonNull BigDecimal qtyPicked;
	@Nullable BigDecimal qtyRejected;
	@Nullable String qtyRejectedReasonCode;
	@Nullable JsonQuantity pickedCatchWeight;
}
