package de.metas.manufacturing.workflows_api.activity_handlers.json;

import com.google.common.collect.ImmutableList;
import de.metas.manufacturing.job.model.FinishedGoodsReceiveLine;
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
	@NonNull ImmutableList<JsonReceiveToNewPackingMaterialTarget> availablePackingMaterials;

	public static JsonFinishedGoodsReceiveLine of(
			final FinishedGoodsReceiveLine from,
			final ImmutableList<JsonReceiveToNewPackingMaterialTarget> availablePackingMaterials,
			final JsonOpts jsonOpts)
	{
		return builder()
				.productName(from.getProductName().translate(jsonOpts.getAdLanguage()))
				.uom(from.getQtyToReceive().getUOMSymbol())
				.qtyToReceive(from.getQtyToReceive().toBigDecimal())
				.qtyReceived(from.getQtyReceived().toBigDecimal())
				.availablePackingMaterials(availablePackingMaterials)
				.build();
	}
}
