package de.metas.distribution.workflows_api.json;

import com.google.common.collect.ImmutableList;
import de.metas.distribution.workflows_api.DistributionJob;
import de.metas.distribution.workflows_api.DistributionJobLine;
import de.metas.distribution.workflows_api.DistributionJobLineId;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonDistributionJobLine
{
	@NonNull DistributionJobLineId lineId;
	@NonNull String caption;
	@NonNull String productId;
	@NonNull String productName;
	@NonNull String uom;
	@NonNull BigDecimal qtyToMove;

	//
	// Pick From
	@NonNull JsonLocatorInfo pickFromLocator;

	//
	// Drop To
	@NonNull JsonLocatorInfo dropToLocator;

	@NonNull List<JsonDistributionJobStep> steps;
	boolean allowPickingAnyHU;

	static JsonDistributionJobLine of(
			@NonNull final DistributionJobLine line,
			@NonNull final DistributionJob job,
			@NonNull final JsonOpts jsonOpts)
	{
		final String adLanguage = jsonOpts.getAdLanguage();

		final String productName = line.getProduct().getCaption().translate(adLanguage);

		return builder()
				.lineId(line.getId())
				.caption(productName)
				.productId(line.getProduct().getProductId().getAsString())
				.productName(productName)
				.uom(line.getQtyToMove().getUOMSymbol())
				.qtyToMove(line.getQtyToMove().toBigDecimal())
				.pickFromLocator(JsonLocatorInfo.of(line.getPickFromLocator()))
				.dropToLocator(JsonLocatorInfo.of(line.getDropToLocator()))
				.steps(line.getSteps()
						.stream()
						.map(step -> JsonDistributionJobStep.of(step, line, jsonOpts))
						.collect(ImmutableList.toImmutableList()))
				.allowPickingAnyHU(job.isAllowPickingAnyHU())
				.build();
	}
}
