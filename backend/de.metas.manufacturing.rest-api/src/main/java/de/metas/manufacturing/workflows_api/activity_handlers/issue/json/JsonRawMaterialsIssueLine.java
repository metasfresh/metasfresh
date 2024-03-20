package de.metas.manufacturing.workflows_api.activity_handlers.issue.json;

import com.google.common.collect.ImmutableList;
import de.metas.manufacturing.job.model.RawMaterialsIssueLine;
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
public class JsonRawMaterialsIssueLine
{
	@NonNull String productName;
	@NonNull String productValue;
	@NonNull String uom;
	@NonNull List<JsonHazardSymbol> hazardSymbols;
	@NonNull List<JsonAllergen> allergens;

	boolean isWeightable;
	@NonNull BigDecimal qtyToIssue;
	@Nullable BigDecimal qtyToIssueMin;
	@Nullable BigDecimal qtyToIssueMax;
	@Nullable JsonQtyToleranceSpec qtyToIssueTolerance;
	@Nullable String userInstructions;

	@NonNull BigDecimal qtyIssued;
	int seqNo;
	@NonNull List<JsonRawMaterialsIssueLineStep> steps;

	public static JsonRawMaterialsIssueLineBuilder builderFrom(
			@NonNull final RawMaterialsIssueLine from,
			@NonNull final JsonOpts jsonOpts)
	{
		return builder()
				.productName(from.getProductName().translate(jsonOpts.getAdLanguage()))
				.productValue(from.getProductValue())
				.uom(from.getQtyToIssue().getUOMSymbol())
				.isWeightable(from.isWeightable())
				.qtyToIssue(from.getQtyToIssue().toBigDecimal())
				.qtyToIssueMin(from.getQtyToIssueMin().map(qty -> qty.toBigDecimal()).orElse(null))
				.qtyToIssueMax(from.getQtyToIssueMax().map(qty -> qty.toBigDecimal()).orElse(null))
				.qtyToIssueTolerance(JsonQtyToleranceSpec.ofNullable(from.getIssuingToleranceSpec()))
				.qtyIssued(from.getQtyIssued().toBigDecimal())
				.userInstructions(from.getUserInstructions())
				.seqNo(from.getSeqNo())
				.steps(from.getSteps()
						.stream()
						.map(step -> JsonRawMaterialsIssueLineStep.of(step, jsonOpts))
						.collect(ImmutableList.toImmutableList()));
	}
}
