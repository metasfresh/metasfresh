package de.metas.manufacturing.workflows_api.activity_handlers.receive.json;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class JsonFinishedGoodsReceiveLine
{
	@NonNull String id;

	@NonNull String productName;
	@NonNull String uom;
	@NonNull BigDecimal qtyToReceive;
	@NonNull BigDecimal qtyReceived;

	@Nullable JsonHUQRCodeTarget currentReceivingHU;

	@NonNull JsonNewLUTargetsList availableReceivingTargets;

}
