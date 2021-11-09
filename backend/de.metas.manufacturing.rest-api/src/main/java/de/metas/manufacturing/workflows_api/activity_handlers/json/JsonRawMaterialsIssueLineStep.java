package de.metas.manufacturing.workflows_api.activity_handlers.json;

import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.manufacturing.job.RawMaterialsIssueStep;
import de.metas.manufacturing.order.PPOrderIssueSchedule;
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
public class JsonRawMaterialsIssueLineStep
{
	@NonNull String id;
	@NonNull String productName;
	@NonNull String locatorName;
	@NonNull String huBarcode;
	@NonNull String uom;
	@NonNull BigDecimal qtyToIssue;
	@NonNull BigDecimal qtyIssued;
	@Nullable String qtyRejectedReasonCode;

	public static JsonRawMaterialsIssueLineStep of(RawMaterialsIssueStep step, JsonOpts jsonOpts)
	{
		final PPOrderIssueSchedule.Issued issued = step.getIssued();
		
		return builder()
				.id(String.valueOf(step.getId().getRepoId()))
				.productName(step.getProductName().translate(jsonOpts.getAdLanguage()))
				.locatorName(step.getIssueFromLocator().getCaption())
				.huBarcode(step.getIssueFromHU().getBarcode().getAsString())
				.uom(step.getQtyToIssue().getUOMSymbol())
				.qtyToIssue(step.getQtyToIssue().toBigDecimal())
				.qtyIssued(issued != null ? issued.getQtyIssued().toBigDecimal() : BigDecimal.ZERO)
				.qtyRejectedReasonCode(issued != null ? QtyRejectedReasonCode.toCode(issued.getQtyRejectedReasonCode()) : null)
				.build();
	}
}
