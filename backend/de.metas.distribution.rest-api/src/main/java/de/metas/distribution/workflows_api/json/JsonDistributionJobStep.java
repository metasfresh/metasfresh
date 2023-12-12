package de.metas.distribution.workflows_api.json;

import de.metas.distribution.workflows_api.DistributionJobLine;
import de.metas.distribution.workflows_api.DistributionJobStep;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class JsonDistributionJobStep
{
	@NonNull String id;

	@NonNull String productName;

	@NonNull String uom;
	@NonNull BigDecimal qtyToMove;

	//
	// Pick From
	@NonNull JsonLocatorInfo pickFromLocator;
	@NonNull JsonHUInfo pickFromHU;
	@NonNull BigDecimal qtyPicked;

	//
	// Drop To
	@NonNull JsonLocatorInfo dropToLocator;
	boolean droppedToLocator;

	public static JsonDistributionJobStep of(
			@NonNull final DistributionJobStep step,
			@NonNull final DistributionJobLine line,
			@NonNull final JsonOpts jsonOpts)
	{
		final String adLanguage = jsonOpts.getAdLanguage();

		return builder()
				.id(String.valueOf(step.getId().toJson()))
				.productName(line.getProduct().getCaption().translate(adLanguage))
				.uom(step.getQtyToMoveTarget().getUOMSymbol())
				.qtyToMove(step.getQtyToMoveTarget().toBigDecimal())
				//
				// Pick From
				.pickFromLocator(JsonLocatorInfo.of(line.getPickFromLocator()))
				.pickFromHU(JsonHUInfo.of(step.getPickFromHU().getQrCode().toRenderedJson()))
				.qtyPicked(step.getQtyPicked().toBigDecimal())
				//
				// Drop To
				.dropToLocator(JsonLocatorInfo.of(line.getDropToLocator()))
				.droppedToLocator(step.isDroppedToLocator())
				//
				.build();
	}

}
