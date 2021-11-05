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
public class JsonRawMaterialsIssueLine
{
	@NonNull String productName;
	@NonNull String uom;
	@NonNull BigDecimal qtyToIssue;
	@NonNull BigDecimal qtyIssued;

	public static JsonRawMaterialsIssueLine of(
			final ManufacturingJobActivity.RawMaterialsIssueLine from,
			final JsonOpts jsonOpts)
	{
		return builder()
				.productName(from.getProductName().translate(jsonOpts.getAdLanguage()))
				.uom(from.getQtyToIssue().getUOMSymbol())
				.qtyToIssue(from.getQtyToIssue().toBigDecimal())
				.qtyIssued(from.getQtyIssued().toBigDecimal())
				.build();
	}
}
