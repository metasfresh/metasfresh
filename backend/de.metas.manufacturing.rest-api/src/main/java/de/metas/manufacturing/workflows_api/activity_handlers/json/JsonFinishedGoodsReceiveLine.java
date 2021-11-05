package de.metas.manufacturing.workflows_api.activity_handlers.json;

import de.metas.manufacturing.job.ManufacturingJobActivity;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class JsonFinishedGoodsReceiveLine
{
	@NonNull String productName;
	@NonNull String uom;
	@NonNull BigDecimal qtyToReceive;
	@NonNull BigDecimal qtyReceived;

	public static JsonFinishedGoodsReceiveLine of(
			final ManufacturingJobActivity.FinishedGoodsReceiveLine from,
			final JsonOpts jsonOpts)
	{
		return builder()
				.productName(from.getProductName().translate(jsonOpts.getAdLanguage()))
				.uom(from.getQtyToReceive().getUOMSymbol())
				.qtyToReceive(from.getQtyToReceive().toBigDecimal())
				.qtyReceived(from.getQtyReceived().toBigDecimal())
				.build();
	}
}
