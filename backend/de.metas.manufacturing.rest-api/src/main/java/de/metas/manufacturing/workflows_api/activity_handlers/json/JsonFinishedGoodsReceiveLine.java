package de.metas.manufacturing.workflows_api.activity_handlers.json;

import de.metas.manufacturing.job.model.CurrentReceivingHU;
import de.metas.manufacturing.job.model.FinishedGoodsReceiveLine;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
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

	@Nullable JsonAggregateToExistingLU currentReceivingHU;

	@NonNull JsonAggregateToNewLUList availableReceivingTargets;

	public static JsonFinishedGoodsReceiveLine of(
			@NonNull final FinishedGoodsReceiveLine from,
			@NonNull final JsonAggregateToNewLUList aggregateToNewLUTargets,
			@NonNull final JsonOpts jsonOpts)
	{
		return builder()
				.id(from.getId().toJson())
				.productName(from.getProductName().translate(jsonOpts.getAdLanguage()))
				.uom(from.getQtyToReceive().getUOMSymbol())
				.qtyToReceive(from.getQtyToReceive().toBigDecimal())
				.qtyReceived(from.getQtyReceived().toBigDecimal())
				.currentReceivingHU(extractJsonAggregateToExistingLU(from))
				.availableReceivingTargets(aggregateToNewLUTargets)
				.build();
	}

	public static JsonAggregateToExistingLU extractJsonAggregateToExistingLU(final FinishedGoodsReceiveLine line)
	{
		final CurrentReceivingHU currentReceivingHU = line.getCurrentReceivingHU();
		if (currentReceivingHU != null)
		{
			return JsonAggregateToExistingLU.builder()
					.huBarcode(currentReceivingHU.getAggregateToLUBarcode().getAsString())
					.tuPIItemProductId(currentReceivingHU.getTuPIItemProductId().getRepoId())
					.build();
		}
		else
		{
			return null;
		}
	}
}
