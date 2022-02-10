package de.metas.manufacturing.workflows_api.activity_handlers.issue.json;

import com.google.common.collect.ImmutableList;
import de.metas.manufacturing.job.model.RawMaterialsIssueLine;
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
public class JsonRawMaterialsIssueLine
{
	@NonNull String productName;
	@NonNull String uom;
	@NonNull BigDecimal qtyToIssue;
	@NonNull BigDecimal qtyIssued;

	@NonNull List<JsonRawMaterialsIssueLineStep> steps;

	public static JsonRawMaterialsIssueLine of(
			final RawMaterialsIssueLine from,
			final JsonOpts jsonOpts)
	{
		return builder()
				.productName(from.getProductName().translate(jsonOpts.getAdLanguage()))
				.uom(from.getQtyToIssue().getUOMSymbol())
				.qtyToIssue(from.getQtyToIssue().toBigDecimal())
				.qtyIssued(from.getQtyIssued().toBigDecimal())
				.steps(from.getSteps()
						.stream()
						.map(step -> JsonRawMaterialsIssueLineStep.of(step, jsonOpts))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}
}
