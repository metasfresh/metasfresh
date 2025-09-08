package de.metas.manufacturing.workflows_api.activity_handlers.issue.json;

import de.metas.global_qrcodes.JsonDisplayableQRCode;
import de.metas.handlingunits.picking.QtyRejectedWithReason;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueSchedule;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.manufacturing.job.model.RawMaterialsIssueStep;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Optional;

@Value
@Builder
@Jacksonized
public class JsonRawMaterialsIssueLineStep
{
	@NonNull String id;
	boolean isAlternativeIssue;
	@NonNull String productId;
	@NonNull String productName;
	@NonNull String locatorName;
	@NonNull String locatorQrCode;
	@NonNull String huId;
	@NonNull String uom;
	@NonNull BigDecimal qtyHUCapacity;
	@NonNull BigDecimal qtyToIssue;
	@Nullable BigDecimal qtyIssued;
	@Nullable BigDecimal qtyRejected;
	@Nullable String qtyRejectedReasonCode;
	@Nullable JsonScaleTolerance scaleTolerance;
	@Nullable JsonDisplayableQRCode huQRCode;

	public static JsonRawMaterialsIssueLineStep of(RawMaterialsIssueStep step, JsonOpts jsonOpts)
	{
		final JsonRawMaterialsIssueLineStepBuilder builder = builder()
				.id(String.valueOf(step.getId().getRepoId()))
				.isAlternativeIssue(step.isAlternativeIssue())
				.productId(String.valueOf(step.getProductId().getRepoId()))
				.productName(step.getProductName().translate(jsonOpts.getAdLanguage()))
				.locatorName(step.getIssueFromLocator().getCaption())
				.locatorQrCode(step.getIssueFromLocator().getQrCode().toGlobalQRCodeJsonString())
				.huQRCode(Optional.ofNullable(step.getIssueFromHU().getBarcode())
								  .map(HUQRCode::toRenderedJson)
								  .orElse(null))
				.huId(step.getIssueFromHU().getId().toHUValue())
				.uom(step.getQtyToIssue().getUOMSymbol())
				.qtyHUCapacity(step.getIssueFromHU().getHuCapacity().toBigDecimal())
				.qtyToIssue(step.getQtyToIssue().toBigDecimal());

		final PPOrderIssueSchedule.Issued issued = step.getIssued();
		if (issued != null)
		{
			builder.qtyIssued(issued.getQtyIssued().toBigDecimal());

			final QtyRejectedWithReason qtyRejected = issued.getQtyRejected();
			if (qtyRejected != null)
			{
				builder.qtyRejected(qtyRejected.toBigDecimal());
				builder.qtyRejectedReasonCode(qtyRejected.getReasonCode().toJson());
			}
		}

		if (step.getScaleTolerance() != null)
		{
			final JsonScaleTolerance jsonScaleTolerance = JsonScaleTolerance.builder()
					.negativeTolerance(step.getScaleTolerance().getNegativeTolerance().toBigDecimal())
					.positiveTolerance(step.getScaleTolerance().getPositiveTolerance().toBigDecimal())
					.build();

			builder.scaleTolerance(jsonScaleTolerance);
		}

		return builder.build();
	}

	@Value
	@Builder
	public static class JsonScaleTolerance
	{
		@NonNull
		BigDecimal positiveTolerance;
		@NonNull
		BigDecimal negativeTolerance;
	}
}
