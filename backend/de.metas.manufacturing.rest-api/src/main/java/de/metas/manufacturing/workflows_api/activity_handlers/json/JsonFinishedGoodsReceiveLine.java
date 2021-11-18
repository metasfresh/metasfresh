package de.metas.manufacturing.workflows_api.activity_handlers.json;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HUBarcode;
import de.metas.manufacturing.job.model.FinishedGoodsReceiveLine;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

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

	@Nullable JsonAggregateToLU aggregateToLU;
	@NonNull ImmutableList<JsonAggregateToNewLU> aggregateToNewLUTargets;

	public static JsonFinishedGoodsReceiveLine of(
			@NonNull final FinishedGoodsReceiveLine from,
			@NonNull final List<JsonAggregateToNewLU> aggregateToNewLUTargets,
			@NonNull final JsonOpts jsonOpts)
	{
		return builder()
				.id(from.getId().toJson())
				.productName(from.getProductName().translate(jsonOpts.getAdLanguage()))
				.uom(from.getQtyToReceive().getUOMSymbol())
				.qtyToReceive(from.getQtyToReceive().toBigDecimal())
				.qtyReceived(from.getQtyReceived().toBigDecimal())
				.aggregateToLU(extractJsonAggregateToLU(from))
				.aggregateToNewLUTargets(ImmutableList.copyOf(aggregateToNewLUTargets))
				.build();
	}

	private static JsonAggregateToLU extractJsonAggregateToLU(final FinishedGoodsReceiveLine line)
	{
		if (line.getAggregateToLUId() != null)
		{
			return JsonAggregateToLU.builder()
					.existingLUBarcode(HUBarcode.ofHuId(line.getAggregateToLUId()).getAsString())
					.build();
		}
		else
		{
			return null;
		}
	}
}
